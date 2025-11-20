# HexaRoll - Multi-Theme RPG Dice Rolling App

A modern Android dice rolling application with multiple theme options (Cyberpunk, Fantasy, SCI-FI, Western, Ancient), built using Jetpack Compose with a comprehensive achievement system, persistent roll history, and external app information links.

## 🤖 About This Project

This application was completely written and is maintained using AI-assisted development tools as a personal project in AI application development. The entire codebase, architecture, and documentation were created with the assistance of AI coding agents, demonstrating modern AI-powered software development workflows. This project serves as both a functional application for the Borderlands community and a personal project in exploring AI-assisted development practices.

**Contribution Policy:** Since this application is an experiment in AI development, contributions are not currently being accepted. This allows the project to remain completely AI-developed and maintained, which is central to the experimental nature of this project. However, you are free to clone the repository and create your own forks or modifications for personal use.

## 🎮 Features

### 🎲 Dice Arena (Main Tab)
- **8 Polyhedral Dice Types**: D4, D6, D8, D10, D12, D20, D30, and D100
- **3D Dice Representations**: Each die is displayed as its geometric shape
  - D4: Tetrahedron
  - D6: Cube
  - D8: Octahedron
  - D10: Trapezohedron
  - D12: Dodecahedron
  - D20: Icosahedron
  - D30: Rhombic Triacontahedron
  - D100: Two Trapezohedrons
- **Interactive Controls**: +/- buttons to adjust dice quantities
- **Roll Modifier**: Adjustable modifier with +/- controls
- **Total Display**: Shows total dice count and final total with modifier
- **Result Display**: Detailed breakdown of individual dice rolls with scrollable history
- **Landscape Optimization**: Horizontal split layout for perfect landscape viewing
  - **Side-by-Side Layout**: Total and Result displays appear side-by-side in landscape
  - **Compact Controls**: Space-optimized buttons and modifier controls
  - **Perfect Fit**: All elements contained without scrolling or overflow
- **Ultra-Compact Spacing**: Minimized padding for maximum information density
- **Roll Button**: Manual roll button with theme-appropriate styling
- **Sound Effects**: Immersive dice rolling sound effects with user control
- **Scroll Indicators**: Visual cues for scrollable dice selection areas

### 🎨 Customization (Second Tab)
- **Theme Selection**: Choose from five distinct visual themes:
  - **Fantasy**: Warm earthy tones with magical golden accents (Default)
  - **Cyberpunk**: Vibrant neon colors with electric glow effects
  - **SCI-FI**: Cool futuristic blues with holographic highlights
  - **Western**: Wood grain and sheriff stars with rustic charm
  - **Ancient**: Roman marble and golden laurels with classical elegance
- **Persistent Preferences**: Your theme choice is automatically saved
- **Complete Visual Transformation**: All UI elements adapt to your chosen theme
- **Automatic Background Settings**: Theme backgrounds always enabled with 100% opacity and stretch scaling

### 📋 Preset Rolls (Third Tab)
- **Pre-configured Rolls**: Save and load common dice combinations
- **Custom Presets**: Create your own named presets with descriptions
- **Preset Management**: Edit, delete, and organize your saved presets

### 📊 Roll History (Fourth Tab)
- **Persistent Roll Log**: Track all dice rolls with timestamps (up to 100 rolls)
- **Detailed Results**: View individual dice results and totals
- **Preset Creation**: Convert any roll result into a preset
- **Achievement Integration**: Viewing history contributes to achievement progress

### 🏆 Achievements (Fifth Tab)
- **Comprehensive Achievement System**: 50+ achievements across multiple categories
- **Achievement Categories**:
  - **Dice Specialists**: Master specific dice types (D6, D8, D10, D12, D20, D30, D100)
  - **Rolling Milestones**: Track total rolls and session progress
  - **Special Events**: Lucky hour rolls, weekend warrior, monthly master
  - **Session Achievements**: Marathon roller (5 hours), speed demon, session champion
- **Tier System**: Bronze, Silver, Gold, and Platinum achievements
- **Progress Tracking**: Visual progress bars for multi-step achievements
- **Achievement Popups**: Animated notifications for unlocked achievements
- **Statistics Integration**: Comprehensive tracking of all user actions
- **Session Management**: Intelligent session tracking with 1-hour timeout

### ⚙️ Settings (Sixth Tab)
- **About HexaRoll**: External link to detailed app documentation
- **Privacy Policy**: External link to privacy policy and data handling practices
- **App Information**: Centralized version details and copyright information
- **Sound Settings**: Control dice rolling sound effects with toggle switch
- **Reset Achievement Progress**: Moved from Achievements tab for better UX with confirmation dialog
- **External Browser Integration**: Opens links in user's preferred browser

## Theme System

The app features five distinct visual themes, each with its own color palette and aesthetic:

### 🏰 Fantasy Theme (Default)
- **Background**: Deep brown wood with rich mahogany cards
- **Primary Colors**: Goldenrod (magical gold), royal blue (mystical), forest green (nature), crimson (dragon fire)
- **Character**: Warm, earthy, magical, medieval-inspired

### 🎭 Cyberpunk Theme
- **Background**: Deep blue-black with dark blue-gray cards
- **Primary Colors**: Bright neon yellows, electric cyan, bright neon greens and reds
- **Character**: High contrast, vibrant, electric, futuristic

### 🚀 SCI-FI Theme
- **Background**: Deep space black with dark metal cards
- **Primary Colors**: Electric blue, spring green, bright red, cyan (holographic), dark orchid (quantum)
- **Character**: Cool, high-tech, futuristic, space-themed

### 🤠 Western Theme
- **Background**: Wood grain textures with rustic card backgrounds
- **Primary Colors**: Sand tones, sheriff star gold, leather browns, desert reds
- **Character**: Rustic, frontier, cowboy-inspired, warm and earthy

### 🏛️ Ancient Theme
- **Background**: Roman marble with classical stone textures
- **Primary Colors**: Pure white marble text, bright gold laurels, Mediterranean blues, imperial purples
- **Character**: Classical, elegant, Roman-inspired, marble and gold aesthetic

## Sound System

### 🎵 Audio Features
- **Dice Rolling Sounds**: Immersive sound effects that play when rolling dice
- **User Control**: Toggle switch in Settings to enable/disable sound effects
- **Automatic Playback**: Sounds trigger automatically when "Roll Dice" button is pressed
- **Resource Management**: Automatic cleanup and memory management for optimal performance
- **Error Handling**: Graceful fallback if audio files are missing or corrupted

### 🎛️ Sound Settings
- **Location**: Settings tab → Sound Settings section
- **Control**: Toggle switch for "Dice Rolling Sound"
- **Default**: Sound effects are enabled by default
- **Persistence**: Sound preference is saved and restored across app sessions

## Achievement System

### Achievement Categories
- **Dice Specialists**: Master specific dice types with dedicated achievements
- **Rolling Milestones**: Track progress from first roll to master roller
- **Special Events**: Lucky hour rolls, weekend activities, and monthly challenges
- **Session Achievements**: Marathon roller (5 hours), speed demon, session champion

### Achievement Tiers
- **Bronze**: Basic achievements for new users
- **Silver**: Intermediate achievements for regular users
- **Gold**: Advanced achievements for experienced users
- **Platinum**: Expert-level achievements for dedicated users

### Session Management
- **Intelligent Tracking**: Sessions persist across app restarts with 1-hour timeout
- **Progress Accuracy**: Progress bars display correctly on app load
- **Cumulative Time**: Marathon Roller tracks total in-app time across valid sessions

## 📋 Release History

### v1.2.0 - Open Source Release

**Release Date:** November 2025  
**Type:** Latest Release

**Changes:** Open source release with MIT license adoption and improved configuration management.

- **MIT License Adoption**: Project licensed under MIT License for open source distribution
- **Secrets Configuration**: Implemented secrets.properties file for secure URL management
  - External URLs moved to gitignored secrets file
  - Template file (secrets.properties.example) provided for easy setup
  - Improved security by removing hardcoded URLs from source code
- **Documentation Updates**: Comprehensive README updates with accurate dependency versions and requirements
- **Copyright Updates**: Updated copyright notices throughout the application to reflect MIT license
- **Configuration Management**: Centralized app information and improved maintainability
- **No Breaking Changes**: All existing functionality preserved with improved security and documentation

### v1.1.0 - Build Configuration Update

**Release Date:** September 2025  
**Type:** Maintenance Release

**Changes:** Updated build configuration to improve management of version numbering and dependency management.

- **Version Catalog Integration**: Implemented TOML version catalog for centralized dependency management
- **Build System Enhancement**: Improved Gradle configuration for better version control
- **Dependency Management**: Streamlined dependency declarations using version catalog references
- **Maintenance Focus**: Internal improvements to build process and project structure
- **No Breaking Changes**: All existing functionality preserved with improved build reliability

### v1.0.0 - Initial Release

**Features:** Initial release with comprehensive dice rolling functionality, multi-theme system, achievement tracking, and persistent data management.

- **8 Polyhedral Dice Types**: Complete set from D4 to D100 with 3D geometric representations
- **Multi-Theme System**: Five distinct visual themes (Cyberpunk, Fantasy, SCI-FI, Western, Ancient)
- **Achievement System**: 50+ achievements across multiple categories with tier progression
- **Persistent Data**: Roll history, achievements, theme preferences, and preset rolls saved across sessions
- **Sound Effects**: Immersive dice rolling sound effects with user control
- **Landscape Optimization**: Horizontal split layout for perfect landscape viewing
- **Settings Integration**: Centralized app information, privacy policy, and sound controls

## 🚀 Getting Started

### 📋 Requirements

Before installing and running the application, ensure your device meets the following requirements:

#### Device Requirements
- **Minimum Android Version**: Android 7.0 (API level 24)
- **Target Android Version**: Android 14 (API level 36)
- **RAM**: Minimum 1GB RAM recommended (2GB for optimal performance)
- **Storage**: ~20MB free space for installation
- **Network**: Internet connection optional - only required for accessing external links (About page and Privacy Policy)

#### Required Permissions
The app requires the following permissions to function properly:
- **INTERNET**: Required for opening external browser links
  - *Used for*: Opening About page and Privacy Policy links in the user's preferred browser
  - *Privacy*: No personal data is transmitted; only opens URLs when user explicitly taps the links
  - *Note*: The app functions fully offline; internet is only needed when accessing external documentation links

#### Development Requirements (for developers)
- **Android Studio**: Iguana (2024.1.1) or later (required for Android Gradle Plugin 8.13.1)
- **Gradle**: 9.0.0 (as specified in gradle-wrapper.properties)
- **Java**: JDK 11 (as specified in compileOptions - sourceCompatibility and targetCompatibility)
- **Kotlin**: 2.2.10 (as specified in libs.versions.toml)
- **Android Gradle Plugin**: 8.13.1 (as specified in libs.versions.toml)
- **Compile SDK**: 36 (Android 14)

### 🔧 Installation Steps

> **Note:** Pre-built APK files for the current version are available for download at the [Android Application Releases page](https://bmoler68.github.io/Releases/). If you prefer to build from source, follow the steps below.

1. **Clone the repository**
   ```bash
   git clone https://github.com/bmoler68/HexaRoll.git
   ```

2. **Create the secrets configuration file**
   - Copy `app/src/main/assets/secrets.properties.example` to `app/src/main/assets/secrets.properties`
   - Edit `secrets.properties` and fill in your actual URL values:
     - `privacy.policy.url`: URL to your privacy policy page
     - `about.page.url`: URL to your about page
   - **Important**: The `secrets.properties` file is gitignored and will not be committed to the repository
   - See the [Secrets Configuration](#-secrets-configuration) section below for more details

3. **Open in Android Studio**
   - Launch Android Studio
   - Open the project folder
   - Sync Gradle files

4. **Run on device or emulator**
   - Connect Android device or start emulator
   - Click the Run button or use `Shift + F10`



## 🔐 Secrets Configuration

This application requires a `secrets.properties` file to store configuration URLs. This file is intentionally excluded from version control (via `.gitignore`) to keep sensitive URLs private.

### Setting Up Secrets

1. **Copy the example file**:
   ```bash
   cp app/src/main/assets/secrets.properties.example app/src/main/assets/secrets.properties
   ```

2. **Edit `secrets.properties`** with your actual values:
   ```properties
   # App URLs
   privacy.policy.url=https://your-privacy-policy-url-here
   about.page.url=https://your-about-page-url-here
   ```

3. **Required Properties**:
   - `privacy.policy.url` (required): URL to your privacy policy page
   - `about.page.url` (required): URL to your about page

### How It Works

- The `secrets.properties` file is loaded at app startup from `app/src/main/assets/`
- If the file is missing or incomplete, the app will fail to start with a clear error message
- The file is automatically excluded from git via `.gitignore`
- A template file (`secrets.properties.example`) is included in the repository for reference

## Usage

1. **Select Dice**: Use the +/- buttons to choose dice quantities
2. **Set Modifier**: Adjust the roll modifier as needed
3. **Roll**: Tap the ROLL button to roll your dice
4. **View Results**: Check the History tab for recent rolls
5. **Save Presets**: Create custom presets for frequently used combinations
6. **Customize Theme**: Visit the Customize tab to choose your preferred visual theme
7. **Track Achievements**: Monitor your progress in the Achievements tab
8. **Explore Themes**: Try all five themes to unlock theme-based achievements
9. **Control Sound**: Visit Settings to enable/disable dice rolling sound effects
10. **App Information**: Visit the Settings tab for app details and legal documents

## 📚 Open Source Licenses

This app uses several open-source libraries under permissive licenses.

### Apache License 2.0

Licensed under [Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

Libraries:
- `androidx.core:core-ktx` (1.17.0)
- `androidx.lifecycle:lifecycle-runtime-ktx` (2.9.2)
- `androidx.activity:activity-compose` (1.10.1)
- `androidx.compose:compose-bom` (2025.08.00)
- `androidx.compose.ui:ui` (managed by BOM)
- `androidx.compose.ui:ui-graphics` (managed by BOM)
- `androidx.compose.ui:ui-tooling` (managed by BOM)
- `androidx.compose.ui:ui-tooling-preview` (managed by BOM)
- `androidx.compose.ui:ui-test-manifest` (managed by BOM)
- `androidx.compose.ui:ui-test-junit4` (managed by BOM)
- `androidx.compose.material3:material3` (managed by BOM)
- `androidx.navigation:navigation-compose` (2.9.3)
- `androidx.lifecycle:lifecycle-viewmodel-compose` (2.9.2)
- `androidx.arch.core:core-testing` (2.2.0)
- `androidx.test.ext:junit` (1.3.0)
- `androidx.test.espresso:espresso-core` (3.7.0)
- `org.jetbrains.kotlinx:kotlinx-coroutines-test` (1.10.2)
- Kotlin language features and compiler (2.2.10)
- `com.google.code.gson:gson` (2.13.1)
- `io.coil-kt:coil-compose` (2.6.0)

### Eclipse Public License 1.0

Licensed under [EPL 1.0](https://www.eclipse.org/legal/epl-v10.html)

Library:
- `junit:junit` (4.13.2)

### MIT License

Licensed under the [MIT License](https://opensource.org/licenses/MIT)

Libraries:
- `org.mockito:mockito-core` (5.19.0)
- `org.mockito:mockito-inline` (5.2.0)

### Creative Commons 0 (CC0) - Public Domain

**Dice Rolling on Table** by Flem0527  
**Source**: [https://freesound.org/s/629982/](https://freesound.org/s/629982/)  
**License**: Creative Commons 0 (CC0) - Public Domain 

## 📜 License

This project is licensed under the MIT License.

Copyright (c) 2025 Brian Moler

See [LICENSE](LICENSE) file for the full license text.

---

This project is open source and available under the MIT License.