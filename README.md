# 🧪 NotiFilter DiagnosticWTF

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)&nbsp;&nbsp;![Build](https://img.shields.io/badge/Build-Diagnostic-orange.svg)&nbsp;&nbsp;![Version](https://img.shields.io/badge/Version-4.15.1-blue.svg)&nbsp;&nbsp;![Privacy](https://img.shields.io/badge/Notification%20content-Not%20collected-green.svg)

**A diagnostic build for investigating Android notification-listener failures.**

NotiFilter DiagnosticWTF is a focused Android test build for one very specific headache: notification filtering eventually stops working even though the filters remain saved. On the affected Samsung Galaxy A15 5G, the app then displays **“Waiting for OS to initialize notification listener service.”** Force-stopping and reopening the app restores operation.

The point of this build is to collect enough lifecycle and processing evidence to identify what is actually failing. **It gathers evidence; it does not claim to fix the underlying issue or automatically recover the service.**

## 🎯 Investigation at a Glance

| Item | Value |
|---|---|
| App name | NotiFilter DiagnosticWTF |
| App version | 4.15.1 |
| Android package | `co.adityarajput.notifilter` |
| Minimum Android | Android 10 (API 29) |
| Target SDK | 36 |
| Development branch | `diagnostic/samsung-a15-listener-persistence` |
| Primary test device | Samsung Galaxy A15 5G (SM-A156U) |

This is a diagnostic test build, not a production release. A successful GitHub Actions build confirms compilation and packaging only; installation and long-duration behavior still need validation on the target phone.

## 🔍 What Gets Monitored

- 🔄 **Notification-listener lifecycle** — service creation/destruction, listener connection/disconnection, and unbind/rebind events.
- 🧭 **Service state** — snapshots of the app's view of listener state, including whether the listener instance exists and is connected.
- 🔢 **Processing continuity** — notification callback counters, without recording notification content.
- 💓 **Process health** — process uptime and periodic service heartbeats.
- 🔐 **Listener permission state** — whether Android reports notification-listener access as granted.
- 🗃️ **Filter/database initialization** — diagnostic events retained to help identify startup-ordering problems.

The instrumentation is intended to distinguish among an Android listener disconnect, a service/process lifecycle event, stale in-app state, and a service that remains connected but stops receiving callbacks.

## 🛡️ Privacy & Diagnostic Data

Diagnostic events are limited to technical state and timestamps needed for this investigation.

- Notification text, titles, contents, and other notification payloads are **not** recorded.
- Callback instrumentation records counts, not notification details.
- The report does not collect installed-app lists or device identifiers.
- A GitHub token is not embedded in the APK or diagnostic report.
- After import, the token is stored locally using Android Keystore encryption.
- A report is uploaded only when the user explicitly requests an upload.

The repository's [privacy notes](privacy.md) describe the base application's data handling. This diagnostic branch adds only the explicitly documented diagnostic events and optional, user-initiated report upload.

## 📲 Install & Configure

1. Download **NotiFilter DiagnosticWTF** from the repository's [GitHub Releases page](https://github.com/pistolwhip-justin/my-NotiFilter/releases). The APK and complete setup package are attached to a diagnostic release. Use the release assets—not temporary Actions artifacts.
2. If an earlier DiagnosticWTF build is installed, uninstall that diagnostic build before installing this one. This build uses a different signing identity. Your separate production NotiFilter installation can remain installed.
3. Install the APK and grant notification access when Android requests it.
4. Create a fine-grained GitHub personal access token at [GitHub token settings](https://github.com/settings/personal-access-tokens/new):
   - Resource owner: `pistolwhip-justin`
   - Repository access: only `my-NotiFilter`
   - Repository permission: **Contents — Read and write**
5. Save the token by itself in a file named `github_token.txt`. In the app, open **Settings → Diagnostic report → Import token file** and select that file.
6. Delete the plaintext token file from Downloads after importing it. Do not share the token in an issue, report, or chat.

The app stores the imported token encrypted using Android Keystore. It is used only when you explicitly upload a diagnostic report.

## 🧪 Reproduce & Submit the Failure

1. Confirm that DiagnosticWTF has notification access.
2. Use the phone normally and allow time for the notification-filtering failure to occur.
3. When filtering stops and the app displays **“Waiting for OS to initialize notification listener service”**, do not force-stop or reopen it before collecting the report. That may erase the failed-state evidence this build is meant to capture.
4. Open **Settings → Diagnostic report → Upload diagnostics**.
5. Confirm that the report appears in the [diagnostic submissions folder](diagnostics/submissions/).

Reports are stored in this repository's `diagnostics/submissions/` directory. Review each report before sharing it elsewhere.

After testing, use **Forget token** in the app and revoke the fine-grained token in [GitHub token settings](https://github.com/settings/personal-access-tokens).

## 🚧 What This Build Does *Not* Do

- It does not add a speculative listener-restart or recovery mechanism.
- It does not claim that the underlying Android/Samsung lifecycle issue has been fixed.
- It does not record notification contents.
- It does not intentionally change the original filter rules or notification actions.

The next step is to analyze a report captured while the listener is in the failed state, identify a cause supported by that evidence, and then decide whether a narrowly scoped corrective change is justified.

## 🧰 Source, Builds & Project Links

- 🧩 [Diagnostic source branch](https://github.com/pistolwhip-justin/my-NotiFilter/tree/diagnostic/samsung-a15-listener-persistence)
- 📦 [GitHub Releases — installable APK and setup package](https://github.com/pistolwhip-justin/my-NotiFilter/releases)
- ⚙️ [Samsung A15 diagnostic build workflow](https://github.com/pistolwhip-justin/my-NotiFilter/actions/workflows/samsung_a15_diagnostic.yml)
- 🗂️ [Diagnostic report submissions](https://github.com/pistolwhip-justin/my-NotiFilter/tree/diagnostic/samsung-a15-listener-persistence/diagnostics/submissions)
- 🌱 [Upstream NotiFilter project](https://github.com/BURG3R5/NotiFilter)

This repository is a fork of [BURG3R5/NotiFilter](https://github.com/BURG3R5/NotiFilter). The upstream project provides the original notification-filtering application; this branch is specifically for DiagnosticWTF instrumentation and investigation.

---

**Last updated:** 2026-11-02
