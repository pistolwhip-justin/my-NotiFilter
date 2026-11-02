# 🔕 NotiFilter — Take Back Your Notifications

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)&nbsp;&nbsp;![Kotlin](https://img.shields.io/badge/Built%20with-Kotlin-7F52FF?logo=kotlin&logoColor=white)&nbsp;&nbsp;![License](https://img.shields.io/badge/License-GPL--3.0-blue.svg)&nbsp;&nbsp;![Fork](https://img.shields.io/badge/Repository-Custom%20Fork-1f425f.svg)

Your notification shade shouldn't feel like another inbox you have to manage. **NotiFilter** lets you decide which notifications deserve your attention and what should happen to the ones that don't.

This repository is a development fork of [BURG3R5/NotiFilter](https://github.com/BURG3R5/NotiFilter). The `dev` branch is the default application-development branch; the separate `diagnostic/samsung-a15-listener-persistence` branch contains the DiagnosticWTF investigation build and its own documentation.

## 🎭 What is NotiFilter?

NotiFilter is a free, open-source Android application that monitors device notifications and applies user-defined filters and actions. Rules can target notifications using regular expressions and can be scheduled to run at selected times.

The goal is simple: less notification noise, fewer interruptions, and more control over what reaches you.

## ⚙️ Features

- 🎯 **Notification filters** — Match notifications using regular expressions and target specific apps.
- 🛠️ **Configurable actions** — Choose what happens when a notification matches:
  - 🚫 Dismiss it.
  - ✅ Tap it or activate one of its buttons.
  - ⏳ Delay it, debounce it, or collect it into batches.
  - 🔇 Mute it or play an alert.
  - 🔊 Disable Do Not Disturb mode.
  - 📝 Replace it with a custom notification.
  - 🗣️ Read it aloud.
  - ⏲️ Remove it after a delay.
- ⏰ **Schedules and cooldowns** — Decide when filters run and how often each filter can apply.
- 🧾 **History** — Keep recently dismissed notifications available for reference and retrieval.
- 📱 **Home-screen widget** — Send selected notifications to a configurable widget.
- 📂 **Import and export** — Back up or transfer filters as JSON.
- 🔐 **Local-first privacy** — Filtering and app data stay on the device; the base application does not request Internet access.
- 🆓 **No ads or subscriptions** — Free and open source under GPLv3.

Feature availability can depend on Android version, granted permissions, and the specific action configured.

## 📸 Screenshots

<p align="left">
  <img alt="NotiFilter filters screen" src="https://github.com/BURG3R5/NotiFilter/blob/dev/metadata/en-US/images/phoneScreenshots/1.png" width="30%">
  &nbsp;
  <img alt="NotiFilter notification history" src="https://github.com/BURG3R5/NotiFilter/blob/dev/metadata/en-US/images/phoneScreenshots/2.png" width="30%">
</p>

## 🚀 Install

For ordinary day-to-day use, prefer the upstream project's established distribution channels:

- [F-Droid](https://f-droid.org/packages/co.adityarajput.notifilter/)
- [IzzyOnDroid](https://apt.izzysoft.de/packages/co.adityarajput.notifilter)
- [Obtainium](https://apps.obtainium.imranr.dev/redirect?r=obtainium://add/https://github.com/BURG3R5/NotiFilter)

This fork is used for development and testing. Any installable build produced specifically from this fork should be obtained from its [GitHub Releases](https://github.com/pistolwhip-justin/my-NotiFilter/releases) page. A diagnostic/test release is not a substitute for the normal production app.

After installation, Android must grant NotiFilter notification access for filtering to work. Some actions may require additional permissions or system settings.

## 🧰 Build from Source

The project uses Kotlin, Gradle, and the Android Gradle Plugin. The Gradle wrapper is included in the repository.

```bash
git clone https://github.com/pistolwhip-justin/my-NotiFilter.git
cd my-NotiFilter
./gradlew test
./gradlew assembleRelease
```

Open the cloned project in Android Studio for the full IDE workflow. Build outputs are generated under the relevant module's `build/outputs/apk/` directory.

The application configuration currently declares:

| Setting | Value |
|---|---|
| Application ID | `co.adityarajput.notifilter` |
| Version name | `4.15.1` |
| Minimum Android | Android 10 (API 29) |
| Compile / target SDK | 36 |
| Main language | Kotlin |

A successful local build does not by itself verify installation or behavior on a physical device.

## 🗂️ Repository Layout

```text
my-NotiFilter/
├── app/             # Android application
├── evaluator/       # Notification matching/evaluation logic
├── helper/          # Shared helper code
├── metadata/        # Store metadata, icons, and screenshots
├── gradle/          # Gradle wrapper configuration
├── privacy.md       # Application privacy notes
├── readme.md        # Legacy filename; see README.md
└── settings.gradle.kts
```

## 🔐 Privacy

The base application processes notifications and filter data locally.

- Features execute on-device.
- App data is stored on-device.
- Permissions are requested as needed for enabled features.
- The base app does not include advertising SDKs, trackers, or telemetry.
- If a crash occurs, the app may offer an optional ACRA crash report; the user chooses whether to send it.

See the project's [privacy notes](privacy.md) for the upstream privacy description. Diagnostic builds on other branches may add separate, explicitly documented instrumentation and upload behavior; those additions do not describe this default branch.

## 🧪 Development in This Fork

This repository tracks the upstream NotiFilter application while providing a place for focused development and testing.

- Default branch: `dev`
- Upstream project: [BURG3R5/NotiFilter](https://github.com/BURG3R5/NotiFilter)
- Diagnostic investigation: [DiagnosticWTF branch](https://github.com/pistolwhip-justin/my-NotiFilter/tree/diagnostic/samsung-a15-listener-persistence)
- Diagnostic documentation: [DiagnosticWTF README](https://github.com/pistolwhip-justin/my-NotiFilter/blob/diagnostic/samsung-a15-listener-persistence/readme.md)

Changes should stay scoped, preserve existing filtering behavior, and include relevant tests and documentation updates. Keep diagnostic-only code and instructions out of the normal application branch unless they are deliberately approved for production.

## 📜 License

This project is distributed under the [GNU General Public License v3.0](license), following the upstream project's license. See the license file for the complete terms.

## ⚠️ Important Notes

- NotiFilter requires Android notification access; without it, notification filtering cannot operate.
- Android and device-manufacturer background restrictions can affect long-running notification-listener services.
- This fork's development builds may differ from upstream releases. Check the release notes and build type before installing.
- Do not assume a diagnostic build fixes a reported issue unless the fix has been implemented and verified.

## 🙏 Acknowledgments

- [BURG3R5/NotiFilter](https://github.com/BURG3R5/NotiFilter) — upstream application and original implementation.
- The Android and open-source communities whose tools and libraries make this project possible.

---

**Last updated:** 2026-11-02
