package co.adityarajput.notifilter.diagnostics

import android.content.Context
import android.os.Build
import android.util.Base64
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

/** Whitelisted diagnostics only. Never accepts notification text or arbitrary log messages. */
object DiagnosticReporter {
    private const val MAX_EVENTS = 250
    private const val REPO = "pistolwhip-justin/my-NotiFilter"
    private const val BRANCH = "diagnostic/samsung-a15-listener-persistence"
    private fun logFile(context: Context) = File(context.filesDir, "safe_diagnostic_events.jsonl")

    @Synchronized
    fun record(context: Context, event: String, detail: String = "") {
        val allowed = setOf(
            "service_create", "database_init_start", "database_init_success",
            "notifications_loaded", "filters_loaded", "listener_connected",
            "listener_disconnected", "service_destroy", "database_init_failure",
            "diagnostic_upload_success", "diagnostic_upload_failure"
        )
        if (event !in allowed) return
        val safeDetail = detail.filter { it == '\n' || it == '\t' || it >= ' ' }
            .replace(Regex("(?i)(token|authorization|password)\\s*[:=]\\s*[^ ,;]+"), "$1=[REDACTED]")
            .take(240)
        val line = JSONObject()
            .put("timestamp", timestamp())
            .put("event", event)
            .put("detail", safeDetail)
            .toString()
        try {
            logFile(context).appendText(line + "\n")
            val lines = logFile(context).readLines().takeLast(MAX_EVENTS)
            logFile(context).writeText(lines.joinToString("\n", postfix = if (lines.isEmpty()) "" else "\n"))
        } catch (_: Exception) { }
    }

    fun report(context: Context): String {
        val events = JSONArray()
        try {
            logFile(context).readLines().takeLast(MAX_EVENTS).forEach { line ->
                try { events.put(JSONObject(line)) } catch (_: Exception) { }
            }
        } catch (_: Exception) { }
        return JSONObject()
            .put("schema", "notifilter-diagnostic-v1")
            .put("generated_at", timestamp())
            .put("app_version", co.adityarajput.notifilter.BuildConfig.VERSION_NAME)
            .put("android_sdk", Build.VERSION.SDK_INT)
            .put("events", events)
            .toString(2)
    }

    /** Token is used only in the Authorization header and never added to report or URL. */
    fun upload(context: Context, token: String): String {
        require(token.startsWith("github_pat_") || token.startsWith("ghp_")) {
            "Token format does not look like a GitHub personal access token."
        }
        val path = "diagnostics/submissions/" +
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date()) + "_" +
            UUID.randomUUID().toString().take(8) + ".json"
        val body = JSONObject()
            .put("message", "Add sanitized NotiFilter diagnostic report")
            .put("branch", BRANCH)
            .put("content", Base64.encodeToString(report(context).toByteArray(Charsets.UTF_8), Base64.NO_WRAP))
            .toString()
        val connection = (URL("https://api.github.com/repos/" + REPO + "/contents/" + path).openConnection() as HttpURLConnection)
        try {
            connection.requestMethod = "PUT"
            connection.connectTimeout = 15000
            connection.readTimeout = 20000
            connection.doOutput = true
            connection.setRequestProperty("Accept", "application/vnd.github+json")
            connection.setRequestProperty("X-GitHub-Api-Version", "2022-11-28")
            connection.setRequestProperty("Authorization", "Bearer " + token.trim())
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
            connection.outputStream.use { it.write(body.toByteArray(Charsets.UTF_8)) }
            val code = connection.responseCode
            if (code !in 200..299) {
                val message = when (code) {
                    401 -> "GitHub rejected the token. Check that it is valid and not expired."
                    403 -> "GitHub denied access. Check repository access and Contents: Read and write permission."
                    404 -> "Repository or branch not found, or token lacks repository access."
                    else -> "GitHub upload failed with HTTP " + code + "."
                }
                throw IllegalStateException(message)
            }
            return "Uploaded sanitized report to public repository path: " + path
        } finally {
            connection.disconnect()
        }
    }

    private fun timestamp(): String = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US)
        .apply { timeZone = TimeZone.getDefault() }.format(Date())
}
