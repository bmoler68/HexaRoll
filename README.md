# HexaRoll - Multi-Theme RPG Dice Rolling App

**Copyright © 2024 Brian Moler. All rights reserved.**

A modern Android dice rolling application with multiple theme options (Cyberpunk, Fantasy, SCI-FI, Western, Ancient), built using Jetpack Compose with a comprehensive achievement system and persistent roll history.

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
- **Sample Presets**: 
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
  - **Rolling Milestones**: Track total rolls and session progress
  - **Dice Type Specialists**: Master specific dice types
  - **Result-Based**: Achieve specific roll outcomes
  - **Streak & Pattern**: Consecutive rolls and patterns
  - **Combination & Modifiers**: Complex dice combinations
  - **Theme-Based**: Explore all themes and stay loyal
  - **Favorites & History**: Preset and history management
  - **Special Events**: Weekend warriors and marathon rollers
- **Tier System**: Bronze, Silver, Gold, Platinum, Diamond achievements
- **Progress Tracking**: Visual progress bars for multi-step achievements
- **Achievement Popups**: Animated notifications for unlocked achievements
- **Statistics Integration**: Comprehensive tracking of all user actions
- **Reset Functionality**: Option to reset all achievement progress

## Technical Features

- **Modern UI**: Built with Jetpack Compose
- **Multi-Theme System**: Five distinct visual themes with complete UI adaptation
- **Theme Persistence**: Automatic saving and loading of user theme preferences
- **State Management**: MVVM architecture with ViewModel
- **Responsive Design**: Adapts to different screen sizes
- **Theme-Aware Components**: All UI elements dynamically change with theme selection
- **Persistent Data**: Roll history, achievements, and preferences saved across sessions
- **Achievement System**: Comprehensive tracking and notification system
- **Custom Dice Assets**: Theme-specific dice images for enhanced visual appeal
- **Production Ready**: Code obfuscation, error handling, input validation, and performance monitoring
- **Accessibility**: Full accessibility support with content descriptions and screen reader compatibility
- **Security**: Input validation, error handling, and secure data storage practices

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
- **Rolling Milestones**: Track progress from first roll to master roller
- **Dice Type Specialists**: Achieve mastery with specific dice types
- **Result-Based**: Achieve specific outcomes like "Snake Eyes" (2D6 = 2)
- **Streak & Pattern**: Consecutive rolls and pattern recognition
- **Combination & Modifiers**: Complex dice combinations and modifier usage
- **Theme-Based**: Explore all themes and demonstrate theme loyalty
- **Favorites & History**: Preset management and history engagement
- **Special Events**: Weekend activities and marathon rolling sessions

### Achievement Tiers
- **Bronze**: Basic achievements for new users
- **Silver**: Intermediate achievements for regular users
- **Gold**: Advanced achievements for experienced users
- **Platinum**: Expert-level achievements for dedicated users
- **Diamond**: Master-level achievements for elite users

## Installation

1. Clone the repository
2. Open in Android Studio
3. Build and run on an Android device or emulator

## Requirements

- Android API 24+ (Android 7.0)
- Minimum 2GB RAM recommended

## Usage

1. **Select Dice**: Use the +/- buttons to choose dice quantities
2. **Set Modifier**: Adjust the roll modifier as needed
3. **Roll**: Tap the ROLL button to roll your dice
4. **View Results**: Check the History tab for recent rolls
5. **Save Presets**: Create custom presets for frequently used combinations
6. **Customize Theme**: Visit the Customize tab to choose your preferred visual theme
7. **Track Achievements**: Monitor your progress in the Achievements tab
8. **Explore Themes**: Try all five themes to unlock theme-based achievements

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

### Code Quality

- **Error Handling**: Comprehensive error handling with ErrorHandler utility
- **Input Validation**: All user inputs validated with ErrorHandler
- **Accessibility**: Full accessibility support with AccessibilityHelper
- **Performance**: Performance monitoring with PerformanceMonitor
- **Testing**: Unit tests for utilities and data validation

## Development

The app is built using:
- **Kotlin** with **Jetpack Compose**
- **Material 3** design system
- **MVVM** architecture pattern
- **StateFlow** for reactive state management
- **Theme System** with persistent user preferences
- **Custom Drawing** for theme-aware dice shapes
- **Achievement System** with comprehensive tracking
- **Persistent Storage** using SharedPreferences and Gson
- **Custom Dice Assets** for enhanced visual themes

## License

**Copyright © 2024 Brian Moler. All rights reserved.**

This project is **NOT open source** and is privately owned by Brian Moler. 

- **Private Project**: This codebase is proprietary and confidential
- **No Public License**: No open source license is granted
- **All Rights Reserved**: All intellectual property rights are retained by Brian Moler
- **No Redistribution**: This code may not be copied, modified, or distributed without explicit permission

For licensing inquiries, please contact Brian Moler directly. 