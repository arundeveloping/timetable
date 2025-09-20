# Timetable (Simple Base App)

Android app (Jetpack Compose + Room) to track your daily timetable.
- Today tab: checkboxes + progress
- History tab: switch to any of the last 14 days

## Build locally
- Open in Android Studio and press Run, or
- CLI: `./gradlew :app:assembleDebug` (if you add the wrapper jar) or use `gradle :app:assembleDebug` with Gradle installed.

## GitHub Actions (APK download)
- Push this repo to GitHub.
- Workflow builds a debug APK and uploads it as an artifact.
- Find it under **Actions → latest run → Artifacts**.

## Notes
- Min SDK 26, Target SDK 35, Kotlin 2.0.0, AGP 8.6.0.
- No network permissions or accounts.