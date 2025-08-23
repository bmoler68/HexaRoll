# HexaRoll - Multi-Theme RPG Dice Rolling App

**Copyright © 2025 Brian Moler. All rights reserved.**

A modern Android dice rolling application with multiple theme options (Cyberpunk, Fantasy, SCI-FI, Western, Ancient), built using Jetpack Compose with a comprehensive achievement system, persistent roll history, and external app information links.

> **Note**: This is a private, proprietary project owned by Brian Moler. This codebase is not open source and may not be copied, modified, or distributed without explicit permission.

## Features

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

### ⚙️ Settings (Sixth Tab) - **Enhanced!**
- **About HexaRoll**: External link to detailed app documentation
- **Privacy Policy**: External link to privacy policy and data handling practices
- **App Information**: Centralized version details and copyright information from `AppInfoData.kt`
- **Sound Settings**: Control dice rolling sound effects with toggle switch
- **Reset Achievement Progress**: Moved from Achievements tab for better UX with confirmation dialog
- **External Browser Integration**: Opens links in user's preferred browser

## Technical Features

- **Modern UI**: Built with Jetpack Compose with ultra-compact spacing optimization
- **Multi-Theme System**: Five distinct visual themes with complete UI adaptation
- **Theme Persistence**: Automatic saving and loading of user theme preferences
- **State Management**: MVVM architecture with ViewModel and StateFlow
- **Responsive Design**: Full landscape and portrait support with orientation-aware layouts
  - **Landscape Optimization**: Horizontal split layout (65% dice grid + 35% controls)
  - **Side-by-Side Displays**: Total and Result displays optimized for landscape viewing
  - **Automatic Layout Switching**: Seamless transition between portrait and landscape modes
- **Theme-Aware Components**: All UI elements dynamically change with theme selection
- **Persistent Data**: Roll history, achievements, and preferences saved across sessions
- **Achievement System**: Comprehensive tracking and notification system with session management
- **Custom Dice Assets**: Theme-specific dice images for enhanced visual appeal
- **Ultra-Compact Design**: Minimized padding and spacing for maximum content density
- **Font Standardization**: Consistent typography hierarchy across all display components
- **Production Ready**: Code obfuscation, error handling, input validation, and performance monitoring
- **Accessibility**: Full accessibility support with content descriptions and screen reader compatibility
- **Security**: Input validation, error handling, and secure data storage practices
- **Internet Integration**: Permission for external browser links
- **Centralized App Data**: Dedicated data classes for maintainable app information management
- **Simplified Storage**: Clean, single-key storage system without legacy overhead
- **Audio System**: Immersive sound effects with user control and automatic resource management

## Recent Improvements

### 🔊 Latest Sound Effects Implementation (Latest)
- **Dice Rolling Sounds**: Added immersive audio feedback when rolling dice
  - High-quality WAV audio file for authentic dice rolling experience
  - Automatic playback when "Roll Dice" button is pressed
  - User control via Settings screen with toggle switch
  - Sound preference persistence across app sessions
- **Audio System Architecture**: Implemented comprehensive sound management
  - **SoundManager.kt**: Handles MediaPlayer lifecycle and resource cleanup
  - **SoundStorage.kt**: Persists user sound preferences using SharedPreferences
  - **Automatic Cleanup**: Prevents memory leaks with proper resource management
  - **Error Handling**: Graceful fallback for missing or corrupted audio files
- **Settings Integration**: Added dedicated Sound Settings section
  - New "Sound Settings" section above App Settings
  - Toggle switch for enabling/disabling dice rolling sounds
  - Theme-aware styling consistent with app design
  - Real-time sound control with immediate effect

### 🧹 Latest Code Simplification & Cleanup (Latest)
- **ThemeStorage Simplification**: Removed legacy theme storage key and unused functions
  - Eliminated `KEY_SELECTED_THEME` constant and `loadTheme()` function
  - Simplified storage to use only `KEY_CUSTOMIZATION` for complete data
  - Removed unused imports and dead code
  - Cleaner, more maintainable storage implementation
- **Comprehensive Testing**: Added complete test coverage for ThemeStorage utility
  - Tests for data persistence, error handling, and edge cases
  - Mock-based testing with proper verification
  - Coverage for all storage scenarios and fallback behaviors

### 📱 Landscape Orientation & Layout Optimization
- **Landscape Support**: Re-enabled landscape orientation for optimal tablet and phone rotation use
- **Optimized Landscape Layout**: Complete redesign of dice arena for landscape mode
  - **Horizontal Split Design**: 65% dice grid + 35% controls for perfect space utilization
  - **Side-by-Side Displays**: Total and Result displays appear side-by-side in landscape
  - **Landscape-Specific Components**: Custom compact buttons and controls for landscape
  - **Perfect Fit**: All UI elements properly contained without scrolling or overflow
- **Font Standardization**: Unified font sizes between Total and Result displays for consistency
- **Ultra-Compact Spacing**: Minimized padding and line spacing for maximum content density
  - Reduced outer padding from 8dp to 4dp (50% reduction)
  - Eliminated artificial spacing between text lines
  - Optimized breakdown section spacing for maximum information display
- **Equal Box Sizing**: Total and Result displays are perfectly equal in landscape mode
- **Responsive Design**: Automatic orientation detection and layout switching

### 🧹 Advanced Code Quality & Cleanup
- **Dead Code Removal**: Eliminated unused landscape-specific display functions (346+ lines removed)
- **App Information Centralization**: Created dedicated `AppInfoData.kt` for centralized app constants
  - Version information, URLs, and copyright details in single location
  - Improved maintainability and consistency across the app
- **Settings Screen Enhancement**: Moved achievement reset functionality to Settings menu for better UX
- **Code Deduplication**: Removed redundant references and cleaned up string resources

### 🎯 UI/UX & Visual Enhancements  
- **Compact UI Design**: Significantly reduced heading sizes and spacing across all screens
  - Main "HEXAROLL" header optimized for minimal space usage
  - Tab icons reduced in size and spacing for more compact navigation
  - Consistent padding reduction across all major screens
- **Achievement Bug Fix**: Fixed "History Buff" achievement tracking for viewing roll history 50 times
- **Visual Consistency**: Standardized font hierarchy across Total and Result displays
- **Perfect Mobile Optimization**: Ultra-compact layout ideal for mobile screens and landscape use

### 🚀 Achievement System Enhancements
- **Monthly Master**: Now tracks 30 unique days instead of calendar months
- **Weekend Warrior**: Counts total rolls on weekends (50 rolls required)
- **Lucky Hour**: Updated to specific times (7:11 AM/PM, 11:11 AM/PM)
- **Marathon Roller**: Fixed to track 5 hours of total in-app time with proper session management
- **Progress Bars**: Session-based achievements now show accurate progress on app restart
- **History Buff Fix**: Properly tracks roll history viewing for achievement progression

### 🔧 Technical Infrastructure
- **Gradle Configuration**: Updated to use stable APIs and resolve deprecation warnings
- **Dependency Management**: Added comprehensive testing dependencies with TOML version catalog
- **Modern Kotlin**: Updated to use `Enum.entries` instead of deprecated `Enum.values()`
- **Comprehensive Testing**: Complete test suite with unit tests, UI tests, and instrumented tests
- **Code Documentation**: Added extensive KDoc comments throughout the codebase

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

## Installation

1. Clone the repository
2. Open in Android Studio
3. Build and run on an Android device or emulator

## Requirements

- Android API 24+ (Android 7.0)
- Minimum 2GB RAM recommended
- Internet permission for external links (optional)

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

## Development

### Building for Production

```bash
# Build release APK
./gradlew assembleRelease

# Build release bundle (recommended for Play Store)
./gradlew bundleRelease
```

### Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

### Test Coverage

The project includes comprehensive testing:

- **Unit Tests**: Data models, utilities, ViewModels, and business logic
  - **DataModelsTest.kt**: Tests for dice and achievement data structures
  - **ThemeStorageTest.kt**: Complete coverage for theme storage utility
  - **AchievementManagerTest.kt**: Achievement logic and tracking tests
  - **ErrorHandlerTest.kt**: Error handling and validation tests
  - **DiceRollViewModelTest.kt**: Main ViewModel business logic tests
- **UI Tests**: Compose UI testing with ComposeTestRule
- **Instrumented Tests**: Android environment testing
- **Test Dependencies**: Mockito, MockK, coroutines testing, architecture components testing

### Code Quality

- **Error Handling**: Comprehensive error handling with ErrorHandler utility
- **Input Validation**: All user inputs validated with ErrorHandler
- **Accessibility**: Full accessibility support with content descriptions
- **Performance**: Optimized code with proper state management
- **Testing**: Comprehensive test suite covering all major functionality
- **Code Cleanup**: Regular removal of dead code and unused functions

## Architecture

The app is built using:
- **Kotlin** with **Jetpack Compose**
- **Material 3** design system
- **MVVM** architecture pattern
- **StateFlow** for reactive state management
- **Theme System** with persistent user preferences
- **Custom Drawing** for theme-aware dice shapes
- **Achievement System** with comprehensive tracking and session management
- **Persistent Storage** using SharedPreferences and Gson
- **Custom Dice Assets** for enhanced visual themes
- **External Integration** for app documentation and legal documents
- **Simplified Storage**: Clean, single-key storage system without legacy overhead

### Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── AndroidManifest.xml              # App configuration and permissions
│   │   ├── java/com/brianmoler/hexaroll/
│   │   │   ├── MainActivity.kt              # Main entry point
│   │   │   ├── data/                        # Data models and app information
│   │   │   │   ├── AchievementModels.kt     # Achievement data structures
│   │   │   │   ├── AppInfoData.kt           # Centralized app constants and URLs
│   │   │   │   ├── AppDefaultsData.kt       # App-wide default constants
│   │   │   │   └── DiceModels.kt            # Dice-related data classes
│   │   │   ├── ui/                          # User interface components
│   │   │   │   ├── components/              # Reusable UI components
│   │   │   │   │   ├── AchievementPopup.kt  # Achievement notification popup
│   │   │   │   │   ├── Dice3DRenderer.kt    # 3D dice shape rendering
│   │   │   │   │   ├── DiceArena.kt         # Main dice rolling interface
│   │   │   │   │   ├── DiceShapes.kt        # Polyhedral dice shape definitions
│   │   │   │   │   └── ThemedBackground.kt  # Theme-aware background component
│   │   │   │   ├── screens/                 # Main application screens
│   │   │   │   │   ├── AchievementScreen.kt # Achievements tab (5th tab)
│   │   │   │   │   ├── CustomizeScreen.kt   # Theme selection (2nd tab)
│   │   │   │   │   ├── HistoryScreen.kt     # Roll history (4th tab)
│   │   │   │   │   ├── MainScreen.kt        # Main navigation and app container
│   │   │   │   │   ├── PresetsScreen.kt     # Preset management (3rd tab)
│   │   │   │   │   ├── RollScreen.kt        # Dice arena wrapper (1st tab)
│   │   │   │   │   └── SettingsScreen.kt    # App settings (6th tab)
│   │   │   │   └── theme/                   # Theme system and styling
│   │   │   │       ├── Theme.kt             # Main theme definitions
│   │   │   │       ├── ThemeBackgrounds.kt  # Background patterns
│   │   │   │       ├── ThemeColors.kt       # Color palettes for 5 themes
│   │   │   │       ├── ThemeColorUtils.kt   # Color utility functions
│   │   │   │       └── Type.kt              # Typography definitions
│   │   │   ├── utils/                       # Utility classes and managers
│   │   │   │   ├── AchievementManager.kt    # Achievement logic and tracking
│   │   │   │   ├── AchievementStorage.kt    # Achievement persistence
│   │   │   │   ├── ErrorHandler.kt          # Error handling and validation
│   │   │   │   ├── PresetStorage.kt         # Preset save/load functionality
│   │   │   │   ├── RollHistoryStorage.kt    # Roll history persistence
│   │   │   │   ├── SoundManager.kt          # Audio playback and resource management
│   │   │   │   ├── SoundStorage.kt          # Sound preference persistence
│   │   │   │   └── ThemeStorage.kt          # Simplified theme preference storage
│   │   │   └── viewmodel/                   # MVVM ViewModels
│   │   │       └── DiceRollViewModel.kt     # Main app state management
│   │   └── res/                             # Android resources
│   │       ├── drawable/                    # Vector drawables and icons
│   │       ├── drawable-xhdpi/              # Theme-specific dice images
│   │       │   ├── bg_theme_*.png           # Background images for 5 themes
│   │       │   ├── bg_theme_*_landscape.png # Landscape background images
│   │       │   └── d*_*.png                 # Dice images (type_theme.png)
│   │       ├── raw/                         # Audio resources
│   │       │   └── dice_roll.wav            # Dice rolling sound effect
│   │       ├── mipmap-*/                    # App launcher icons
│   │       ├── values/                      # Resource values
│   │       │   ├── colors.xml               # Color definitions
│   │       │   ├── strings.xml              # String resources
│   │       │   └── themes.xml               # Material theme configurations
│   │       └── xml/                         # XML configurations
│   │           ├── backup_rules.xml         # Data backup configuration
│   │           └── data_extraction_rules.xml # Data extraction rules
│   ├── androidTest/                         # Instrumented tests
│   │   └── java/com/brianmoler/hexaroll/
│   │       ├── ExampleInstrumentedTest.kt   # Basic instrumented test
│   │       └── MainActivityTest.kt          # Main activity UI tests
│   └── test/                                # Unit tests
│       └── java/com/brianmoler/hexaroll/
│           ├── data/DataModelsTest.kt       # Data model unit tests
│           ├── ExampleUnitTest.kt           # Example unit test
│           ├── utils/                       # Utility class tests
│           │   ├── AchievementManagerTest.kt # Achievement logic tests
│           │   ├── ErrorHandlerTest.kt      # Error handling tests
│           │   └── ThemeStorageTest.kt      # Theme storage utility tests
│           └── viewmodel/                   # ViewModel tests
│               └── DiceRollViewModelTest.kt # Main ViewModel tests
├── build.gradle.kts                         # App-level build configuration
└── proguard-rules.pro                       # Code obfuscation rules

gradle/
├── libs.versions.toml                       # Version catalog for dependencies
└── wrapper/                                 # Gradle wrapper files

build.gradle.kts                             # Project-level build configuration
settings.gradle.kts                          # Project settings
gradle.properties                            # Gradle configuration properties
```

### Key Architecture Components

#### **📱 UI Layer** (`ui/`)
- **Screens**: Six main tabs with complete functionality
- **Components**: Reusable, theme-aware UI components
- **Theme System**: Five distinct visual themes with complete UI adaptation
- **Responsive Design**: Portrait and landscape layouts with orientation detection

#### **🎯 Data Layer** (`data/`)
- **Models**: Data classes for dice, achievements, and app information
- **Centralized Constants**: `AppInfoData.kt` and `AppDefaultsData.kt` for maintainable app configuration
- **Type Safety**: Strongly-typed data structures throughout

#### **🔧 Business Logic** (`utils/`, `viewmodel/`)
- **MVVM Pattern**: Clear separation of concerns with ViewModel
- **State Management**: Reactive state using StateFlow
- **Persistence**: SharedPreferences with Gson for complex data
- **Achievement System**: Comprehensive tracking with session management
- **Simplified Storage**: Clean, single-key storage system without legacy overhead
- **Audio System**: Immersive sound effects with user control and automatic resource management

#### **🎨 Resources** (`res/`)
- **Theme Assets**: Custom dice images for each of the 5 themes
- **Landscape Support**: Orientation-specific background images
- **Scalable Graphics**: Vector drawables for icons and UI elements
- **Responsive Images**: Multiple density support for all screen sizes
- **Audio Resources**: Immersive sound effects for enhanced user experience
- **Material Design**: Complete Material 3 theme integration

## Recent Commits

### Latest Code Simplification & Cleanup (2025)
- **ThemeStorage Simplification**: Removed legacy theme storage key and unused functions
  - Eliminated `KEY_SELECTED_THEME` constant and `loadTheme()` function
  - Simplified storage to use only `KEY_CUSTOMIZATION` for complete data
  - Removed unused imports and dead code
  - Cleaner, more maintainable storage implementation
- **Comprehensive Testing**: Added complete test coverage for ThemeStorage utility
  - Tests for data persistence, error handling, and edge cases
  - Mock-based testing with proper verification
  - Coverage for all storage scenarios and fallback behaviors

### Previous Major Updates
- **Landscape Layout Implementation**: Complete redesign for landscape orientation with horizontal split
- **Ultra-Compact Spacing**: Reduced padding from 8dp to 4dp and eliminated artificial line spacing
- **Font Standardization**: Unified font sizes between Total and Result displays for consistency
- **Equal Box Sizing**: Perfect symmetry between Total and Result displays in landscape mode
- **Code Cleanup**: Removed 346+ lines of unused landscape-specific display functions
- **App Data Centralization**: Created `AppInfoData.kt` and `AppDefaultsData.kt` for centralized app constants
- **Settings Enhancement**: Moved achievement reset functionality from Achievements to Settings screen
- **History Buff Fix**: Corrected achievement tracking for viewing roll history 50 times
- **Compact UI Design**: Reduced heading sizes and spacing across all screens for mobile optimization
- **Theme Background Rotation**: Added support for landscape-specific background images
- **Default Background Settings**: Set theme backgrounds to always enabled with 100% opacity and stretch scaling

## License

**Copyright © 2025 Brian Moler. All rights reserved.**

This project is **NOT open source** and is privately owned by Brian Moler. 

- **Private Project**: This codebase is proprietary and confidential
- **No Public License**: No open source license is granted
- **All Rights Reserved**: All intellectual property rights are retained by Brian Moler
- **No Redistribution**: This code may not be copied, modified, or distributed without explicit permission

For licensing inquiries, please contact Brian Moler directly.

## Open Source Licenses

This app uses several open-source libraries under permissive licenses.

### Apache License 2.0

Licensed under [Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

Libraries:
- `androidx.core:core-ktx` (1.16.0)
- `androidx.lifecycle:lifecycle-runtime-ktx` (2.9.2)
- `androidx.activity:activity-compose` (1.10.1)
- `androidx.compose:compose-bom` (2025.07.00)
- `androidx.compose.ui:ui`
- `androidx.compose.ui:ui-graphics`
- `androidx.compose.ui:ui-tooling`
- `androidx.compose.ui:ui-test-manifest`
- `androidx.compose.material3:material3`
- `androidx.compose.material:material-icons-extended` (1.6.0)
- `androidx.test.ext:junit` (1.3.0)
- `com.android.tools:desugar_jdk_libs` (2.1.5)
- `org.jetbrains.kotlinx:kotlinx-coroutines-android` (1.10.2)
- Kotlin language features and compiler
- `com.squareup.okhttp3:okhttp` (5.1.0)

### Eclipse Public License 1.0

Licensed under [EPL 1.0](https://www.eclipse.org/legal/epl-v10.html)

Library:
- `junit:junit` (4.13.2)

### MIT License

Licensed under the [MIT License](https://opensource.org/licenses/MIT)

Library:
- `org.robolectric:robolectric` (4.15.1)

### Creative Commons 0 (CC0) - Public Domain

**Dice Rolling on Table** by Flem0527  
**Source**: [https://freesound.org/s/629982/](https://freesound.org/s/629982/)  
**License**: Creative Commons 0 (CC0) - Public Domain 