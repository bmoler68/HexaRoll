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
- **Scroll Indicators**: Visual cues for scrollable dice selection areas

### 🎨 Customization (Second Tab)
- **Theme Selection**: Choose from five distinct visual themes:
  - **Cyberpunk**: Vibrant neon colors with electric glow effects
  - **Fantasy**: Warm earthy tones with magical golden accents
  - **SCI-FI**: Cool futuristic blues with holographic highlights
  - **Western**: Wood grain and sheriff stars with rustic charm
  - **Ancient**: Roman marble and golden laurels with classical elegance
- **Persistent Preferences**: Your theme choice is automatically saved
- **Complete Visual Transformation**: All UI elements adapt to your chosen theme

### 📋 Preset Rolls (Third Tab)
- **Pre-configured Rolls**: Save and load common dice combinations
- **Custom Presets**: Create your own named presets with descriptions
- **Preset Management**: Edit, delete, and organize your saved presets

### 📊 Roll History (Fourth Tab)
- **Persistent Roll Log**: Track all dice rolls with timestamps (up to 100 rolls)
- **Detailed Results**: View individual dice results and totals
- **Preset Creation**: Convert any roll result into a preset
- **Clear History**: Option to clear roll history with confirmation

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

## Recent Improvements

### 📱 Landscape Orientation & Layout Optimization (Latest)
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

### 🎭 Cyberpunk Theme (Default)
- **Background**: Deep blue-black with dark blue-gray cards
- **Primary Colors**: Bright neon yellows, electric cyan, bright neon greens and reds
- **Character**: High contrast, vibrant, electric, futuristic

### 🏰 Fantasy Theme
- **Background**: Deep brown wood with rich mahogany cards
- **Primary Colors**: Goldenrod (magical gold), royal blue (mystical), forest green (nature), crimson (dragon fire)
- **Character**: Warm, earthy, magical, medieval-inspired

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
9. **App Information**: Visit the Settings tab for app details and legal documents

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
- **UI Tests**: Compose UI testing with ComposeTestRule
- **Instrumented Tests**: Android environment testing
- **Test Dependencies**: Mockito, coroutines testing, architecture components testing

### Code Quality

- **Error Handling**: Comprehensive error handling with ErrorHandler utility
- **Input Validation**: All user inputs validated with ErrorHandler
- **Accessibility**: Full accessibility support with content descriptions
- **Performance**: Optimized code with proper state management
- **Testing**: Comprehensive test suite covering all major functionality

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

## Recent Commits

### Latest Landscape & UI Optimization (2025)
- **Landscape Layout Implementation**: Complete redesign for landscape orientation with horizontal split
- **Ultra-Compact Spacing**: Reduced padding from 8dp to 4dp and eliminated artificial line spacing
- **Font Standardization**: Unified font sizes between Total and Result displays for consistency
- **Equal Box Sizing**: Perfect symmetry between Total and Result displays in landscape mode
- **Code Cleanup**: Removed 346+ lines of unused landscape-specific display functions
- **App Data Centralization**: Created `AppInfoData.kt` for centralized app constants and URLs
- **Settings Enhancement**: Moved achievement reset functionality from Achievements to Settings screen
- **History Buff Fix**: Corrected achievement tracking for viewing roll history 50 times
- **Compact UI Design**: Reduced heading sizes and spacing across all screens for mobile optimization

### Previous Major Updates
- Added Settings tab with external links to About page and Privacy Policy
- Fixed Marathon Roller achievement session tracking and progress display
- Updated achievement logic for Monthly Master, Weekend Warrior, and Lucky Hour
- Comprehensive code cleanup and encapsulation improvements
- Added missing density variation drawable folders
- Updated Gradle configuration to use stable APIs
- Comprehensive test suite rewrite and modernization
- Added extensive code documentation and KDoc comments

## License

**Copyright © 2025 Brian Moler. All rights reserved.**

This project is **NOT open source** and is privately owned by Brian Moler. 

- **Private Project**: This codebase is proprietary and confidential
- **No Public License**: No open source license is granted
- **All Rights Reserved**: All intellectual property rights are retained by Brian Moler
- **No Redistribution**: This code may not be copied, modified, or distributed without explicit permission

For licensing inquiries, please contact Brian Moler directly. 