# CustomIntentApp

A modern Android application built with Kotlin and Jetpack Compose that handles custom intents for text processing. This app allows other applications to send text data for processing and receive structured responses.

## 🌟 Features

- **Custom Intent Handling**: Processes text sent from other applications via custom intents
- **Interactive UI**: Clean, modern interface built with Jetpack Compose and Material 3
- **Text Processing**: Accepts text input and allows users to provide custom responses
- **Inter-app Communication**: Seamlessly integrates with other apps through Android's intent system
- **Real-time Updates**: Reactive UI that updates based on received data

## 🛠️ Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Design System**: Material 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Async Programming**: Kotlin Coroutines
- **State Management**: StateFlow and sealed classes
- **Minimum SDK**: API 28 (Android 9.0)
- **Target SDK**: API 36

## 🏗️ Architecture

The app follows MVVM architecture pattern with the following key components:

- **MainActivity**: Entry point that handles intent processing and UI setup
- **MainViewModel**: Manages UI state and business logic using StateFlow
- **IntentScreen**: Jetpack Compose UI that displays received text and response input
- **State Management**: Uses sealed classes (`IntentUiState`, `IntentUiEvent`) for type-safe state handling

### Project Structure
```
app/src/main/java/org/dhis2/customintent/
├── MainActivity.kt                    # Main activity and intent handling
├── ui/
│   ├── screen/
│   │   └── IntentScreen.kt           # Main UI screen
│   ├── state/
│   │   └── IntentUiState.kt          # State management classes
│   ├── viewmodel/
│   │   └── MainViewModel.kt          # ViewModel for business logic
│   └── theme/                        # UI theming (Material 3)
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
```

## 🚀 Getting Started

### Prerequisites

- Android Studio (latest version recommended)
- JDK 11 or higher
- Android SDK with API level 28+

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/andresmr/CustomIntentApp.git
   cd CustomIntentApp
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory and select it

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run the application**
   - Connect an Android device or start an emulator
   - Click the "Run" button in Android Studio or use:
   ```bash
   ./gradlew installDebug
   ```

## 📱 Usage

### As a Standalone App
1. Launch the app directly from the launcher
2. The app will display a message indicating no text was received
3. You can enter response text and tap "Send Response" (though no calling app will receive it)

### As an Intent Handler
1. Other apps can send text to this app using the custom intent:
   ```kotlin
   val intent = Intent("org.dhis2.customintent.ACTION_PROCESS_TEXT").apply {
       putExtra(Intent.EXTRA_TEXT, "Your text here")
       putExtra("boolean", true)
       putExtra("integer", 42)
       putExtra("string2", "Additional data")
   }
   startActivityForResult(intent, REQUEST_CODE)
   ```

2. The app will display the received text and allow the user to provide a response
3. The response is returned to the calling app as a JSON string

### Example Response Format
By default, the app returns a JSON response:
```json
{"value1": "ejemplo1", "value2": "ejemplo2"}
```

Users can customize this response through the UI before sending it back to the calling application.

### Intent Filters
The app registers the following intent filters in AndroidManifest.xml:
- `org.dhis2.customintent.ACTION_PROCESS_TEXT` - Custom action for text processing
- Standard Android sharing intents (`ACTION_SEND` with `text/plain`)

### State Management
The app uses a clean state management approach with sealed classes:

```kotlin
sealed class IntentUiState {
    object Loading : IntentUiState()
    data class Success(
        val receivedText: String?,
        val responseText: String = ""
    ) : IntentUiState()
    data class Error(val message: String) : IntentUiState()
}

sealed class IntentUiEvent {
    data class UpdateResponseText(val text: String) : IntentUiEvent()
    object SendResponse : IntentUiEvent()
}
```

## 📸 Screenshots

| Main Screen | With Received Text | Response Input |
|-------------|-------------------|----------------|
| ![Main Screen](docs/images/main_screen.png) | ![Received Text](docs/images/received_text.png) | ![Response](docs/images/response_input.png) |

*Screenshots will be added in future releases*

## 🎨 UI Components

- **Header**: App title and description
- **Received Text Card**: Displays text sent from other apps (with selection support)
- **Response Input**: Text field for entering custom responses
- **Send Button**: Submits the response back to the calling app
- **Info Text**: Helpful description of the app's functionality

## 🧪 Testing

Run the unit tests:
```bash
./gradlew test
```

Run instrumented tests:
```bash
./gradlew connectedAndroidTest
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Guidelines
- Follow Kotlin coding conventions
- Use Jetpack Compose for UI development
- Implement MVVM architecture pattern
- Use Coroutines for asynchronous operations
- Write unit tests for business logic

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🔧 Configuration

### Build Configuration
- **Application ID**: `org.dhis2.customintent`
- **Version Code**: 1
- **Version Name**: 1.0
- **Compile SDK**: 36
- **Min SDK**: 28
- **Target SDK**: 36

### Dependencies
Key dependencies include:
- androidx.core:core-ktx
- androidx.lifecycle:lifecycle-runtime-ktx
- androidx.activity:activity-compose
- androidx.compose:compose-bom
- androidx.compose.material3:material3

### Build Troubleshooting

If you encounter build issues:

1. **AGP Version Compatibility**: Ensure Android Gradle Plugin version is compatible with your Gradle version
2. **SDK Requirements**: Make sure you have Android SDK API 28+ installed
3. **Clean Build**: Try cleaning the project:
   ```bash
   ./gradlew clean
   ./gradlew build
   ```

## 🐛 Known Issues

- Build configuration may need AGP version adjustment for different development environments
- Ensure proper Android SDK setup for successful compilation

## 📞 Support

For questions, issues, or contributions, please:
1. Check existing issues on GitHub
2. Create a new issue with detailed description
3. Follow the issue template for bug reports or feature requests

## 🎯 Future Enhancements

- Support for more data types (images, files)
- Enhanced response formatting options
- Intent validation and error handling
- Additional customization options
- Improved accessibility features