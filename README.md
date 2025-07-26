# HexaRoll - Multi-Theme RPG Dice Rolling App

**Copyright © 2024 Brian Moler. All rights reserved.**

A modern Android dice rolling application with multiple theme options (Cyberpunk, Fantasy, SCI-FI), built using Jetpack Compose.

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

### 🎨 Customization (Second Tab)
- **Theme Selection**: Choose from three distinct visual themes:
  - **Cyberpunk**: Vibrant neon colors with electric glow effects
  - **Fantasy**: Warm earthy tones with magical golden accents
  - **SCI-FI**: Cool futuristic blues with holographic highlights
- **Persistent Preferences**: Your theme choice is automatically saved
- **Complete Visual Transformation**: All UI elements adapt to your chosen theme

### 📋 Preset Rolls (Third Tab)
- **Pre-configured Rolls**: Save and load common dice combinations
- **Sample Presets**: 
  - Pathfinder (1D20)
  - COC Stats (3D6)
  - Fireball (8D6)
- **Custom Presets**: Create your own named presets with descriptions

### 📊 Roll History (Fourth Tab)
- **Roll Log**: Track all dice rolls with timestamps
- **Detailed Results**: View individual dice results and totals
- **Preset Creation**: Convert any roll result into a preset

## Technical Features

- **Modern UI**: Built with Jetpack Compose
- **Multi-Theme System**: Three distinct visual themes with complete UI adaptation
- **Theme Persistence**: Automatic saving and loading of user theme preferences
- **State Management**: MVVM architecture with ViewModel
- **Responsive Design**: Adapts to different screen sizes
- **Theme-Aware Components**: All UI elements dynamically change with theme selection

## Theme System

The app features three distinct visual themes, each with its own color palette and aesthetic:

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

## Development

The app is built using:
- **Kotlin** with **Jetpack Compose**
- **Material 3** design system
- **MVVM** architecture pattern
- **StateFlow** for reactive state management
- **Theme System** with persistent user preferences
- **Custom Drawing** for theme-aware dice shapes

## License

**Copyright © 2024 Brian Moler. All rights reserved.**

This project is **NOT open source** and is privately owned by Brian Moler. 

- **Private Project**: This codebase is proprietary and confidential
- **No Public License**: No open source license is granted
- **All Rights Reserved**: All intellectual property rights are retained by Brian Moler
- **No Redistribution**: This code may not be copied, modified, or distributed without explicit permission

For licensing inquiries, please contact Brian Moler directly. 