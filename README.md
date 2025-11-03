
# Guardian - Mobile Security App

**Guardian** is a free, open-source mobile security application designed to protect high-risk users (such as journalists and activists) from sophisticated spyware. Its primary goal is to simplify complex mobile security into easy-to-understand, one-tap solutions.

---

## ⚠️ ALPHA VERSION - FOR TESTING ONLY ⚠️

This is an early alpha version of Guardian. It is for testing and educational purposes only and **should NOT be relied upon for real-world security protection**. The app has not been thoroughly tested or audited and may contain bugs or security vulnerabilities. Use at your own risk.

---

## Features

*   **One-Tap Security Scan:** Scans your device for common security vulnerabilities.
*   **Guided Fixes:** Provides step-by-step guidance on how to fix identified security issues.
*   **Crisis Mode:** An interactive checklist to guide you through securing your device in an emergency.
*   **Education:** A library of articles to help you understand mobile security threats and how to protect yourself.

## Building and Running from Source

### Android

**Prerequisites:**
*   [Android Studio](https://developer.android.com/studio) (latest version recommended)
*   An Android device or emulator running Android 5.0 (API 21) or higher

**Instructions:**

1.  **Clone the Repository:**
    ```bash
    git clone <your-github-repository-url>
    ```
2.  **Open the Project:**
    *   Launch Android Studio.
    *   Select "Open an existing Android Studio project".
    *   Navigate to and select the `Guardian/android` directory.
3.  **Gradle Sync:**
    *   Allow Android Studio to sync the project with Gradle. This will download all the necessary dependencies.
4.  **Run the App:**
    *   Connect your Android device (with USB debugging enabled) or start an emulator.
    *   Click the "Run" button in the Android Studio toolbar.

### iOS

**Prerequisites:**
*   A macOS machine
*   [Xcode](https://developer.apple.com/xcode/) (latest version recommended)
*   An Apple Developer account (free or paid)
*   An iPhone or iOS Simulator running iOS 15.0 or higher

**Instructions:**

1.  **Clone the Repository:**
    ```bash
    git clone <your-github-repository-url>
    ```
2.  **Open the Project:**
    *   Launch Xcode.
    *   Select "Open a project or file".
    *   Navigate to and select the `Guardian/ios` directory.
3.  **Configure Signing:**
    *   In Xcode, select the `Guardian` project in the Project Navigator.
    *   Go to the "Signing & Capabilities" tab.
    *   Select your Apple Developer Team.
4.  **Run the App:**
    *   Select your target device or simulator from the scheme menu.
    *   Click the "Run" button in the Xcode toolbar.

## Development Conventions

*   **Architecture:** The project follows the MVVM (Model-View-ViewModel) architecture pattern.
*   **On-Device Processing:** All security scanning and analysis are performed directly on the device to ensure user privacy. No data is collected or sent to any servers.
*   **UI:**
    *   **Android:** Jetpack Compose
    *   **iOS:** SwiftUI
