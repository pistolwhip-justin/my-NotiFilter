# NotiFilter DiagnosticWTF

**A diagnostic build for investigating Android notification-listener failures.**

NotiFilter DiagnosticWTF is a diagnostic-focused Android build of NotiFilter, maintained in this fork to investigate a specific reliability problem: notification filtering eventually stops working even though the app's filters remain saved. On the affected Samsung Galaxy A15 5G, the app then reports **“Waiting for OS to initialize notification listener service”**. Force-stopping and reopening the app restores operation.

The purpose of this build is to capture enough lifecycle and processing evidence to identify why the Android notification-listener service stops functioning. **This build gathers evidence; it does not claim to fix the underlying issue or automatically recover the service.**

## Current build

| Item | Value |
|---|---|
| App name | NotiFilter DiagnosticWTF |
| App version | 4.15.1 |
| Android package | `co.adityarajput.notifilter` |
| Minimum Android version | Android 10 (API 29) |
| Target SDK | 36 |
| Development branch | `diagnostic/samsung-a15-listener-persistence` |
| Primary test device | Samsung Galaxy A15 5G (SM-A156U) |

This is a test build, not a production release. The APK was built successfully by GitHub Actions. Installation and long-duration behavior still require validation on the target device.

## What this build monitors

- **Notification-listener lifecycle** — service creation and destruction, listener connection and disconnection, and unbind/rebind events.
- **Service state** — periodic snapshots of the app's view of listener state, including whether the listener instance is present and connected.
- **Processing continuity** — notification callback counters, recorded without capturing notification content.
- **Process health** — process uptime and periodic service heartbeats.
- **Listener permission state** — whether Android reports notification-listener access as granted.
- **Filter/database initialization** — diagnostic events retained from the earlier diagnostic build to help identify startup-ordering problems.

The instrumentation is intended to distinguish among an Android listener disconnect, a service/process lifecycle event, a stale in-app service state, and a service that remains connected but stops receiving callbacks.

## Privacy

Diagnostic events are limited to technical state and timestamps needed for this investigation.

- Notification text, titles, contents, and other notification payloads are **not** recorded.
- The diagnostic callback counter records counts, not notification details.
- The report does not collect installed-app lists or device identifiers.
- A GitHub token is not embedded in the APK or diagnostic report.
- The token is stored locally using Android Keystore encryption after import.

The report is uploaded only when the user explicitly requests an upload. The repository's [privacy notes](privacy.md) describe the underlying app's data handling; this diagnostic build adds the explicitly described diagnostic events and optional user-initiated report upload.

## Install and configure

1. Download the **NotiFilter DiagnosticWTF** APK from the repository's [GitHub Releases page](https://github.com/pistolwhip-justin/my-NotiFilter/releases). The APK and complete setup package are attached to each diagnostic release. Do not use the temporary Actions artifacts when a release asset is available.
2. If an earlier DiagnosticWTF build is installed, uninstall that diagnostic build before installing this one. This build uses a different signing identity. Your separate production NotiFilter installation can remain installed.
3. Install the APK and grant notification access when Android requests it.
4. Create a fine-grained GitHub personal access token at [GitHub token settings](https://github.com/settings/personal-access-tokens/new):
   - Resource owner: `pistolwhip-justin`
   - Repository access: only `my-NotiFilter`
   - Repository permission: **Contents — Read and write**
5. Save the token by itself in a file named `github_token.txt`. In the app, open **Settings → Diagnostic report → Import token file** and select that file.
6. Delete the plaintext token file from Downloads after importing it. Do not share the token in an issue, report, or chat.

The app stores the imported token encrypted using Android Keystore. The token is used only when you explicitly upload a diagnostic report.

## Reproduce and submit the issue

1. Confirm that DiagnosticWTF has notification access.
2. Use the phone normally and allow time for the notification-filtering failure to occur.
3. When the app stops processing notifications and displays **“Waiting for OS to initialize notification listener service”**, avoid force-stopping or reopening it before collecting the report. Doing so may erase the failed-state evidence this build is intended to capture.
4. Open **Settings → Diagnostic report → Upload diagnostics**.
5. Confirm that the report appears in the [diagnostic submissions folder](diagnostics/submissions/).

Reports are stored in this repository's `diagnostics/submissions/` directory. Review each report before sharing it elsewhere.

After testing, you can use **Forget token** in the app and revoke the fine-grained token in [GitHub token settings](https://github.com/settings/personal-access-tokens).

## What is not included

- No speculative listener-restart or recovery mechanism.
- No claim that the underlying Android/Samsung lifecycle issue has been fixed.
- No notification-content logging.
- No changes intended to alter the original filter rules or notification actions.

The next development step is to analyze a report captured while the listener is in the failed state, identify the supported cause from the evidence, and then decide on a narrowly scoped corrective change.

## Build and source

- Source code and diagnostic changes: [this repository](https://github.com/pistolwhip-justin/my-NotiFilter/tree/diagnostic/samsung-a15-listener-persistence)
- Installable APKs: [GitHub Releases](https://github.com/pistolwhip-justin/my-NotiFilter/releases)
- Build workflow: [Samsung A15 diagnostic APK](https://github.com/pistolwhip-justin/my-NotiFilter/actions/workflows/samsung_a15_diagnostic.yml)
- Diagnostic reports: [submissions](https://github.com/pistolwhip-justin/my-NotiFilter/tree/diagnostic/samsung-a15-listener-persistence/diagnostics/submissions)

This repository is a fork of [BURG3R5/NotiFilter](https://github.com/BURG3R5/NotiFilter). The upstream project provides the original notification-filtering application; this fork's current development focus is the DiagnosticWTF instrumentation and investigation documented above.
