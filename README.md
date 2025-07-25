# HexaRoll - Cyberpunk RPG Dice Rolling App

**Copyright © 2024 Brian Moler. All rights reserved.**

A modern Android dice rolling application with a Cyberpunk RPG aesthetic, built using Jetpack Compose.

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
- **Shake to Roll**: Shake your device to trigger dice rolls
- **Roll Button**: Manual roll button with Cyberpunk styling

### 🎨 Customization (Second Tab)
- **Dice Color**: Customize dice appearance
- **Arena Background**: Choose from different background themes
- **Unique Dice**: Special dice customization options

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
- **Cyberpunk Theme**: Dark background with neon colors
- **Sensor Integration**: Accelerometer-based shake detection
- **State Management**: MVVM architecture with ViewModel
- **Responsive Design**: Adapts to different screen sizes

## Color Scheme

The app uses a Cyberpunk-inspired color palette:
- **Background**: Dark grays and blacks
- **Primary**: Neon cyan (#00FFFF)
- **Secondary**: Neon magenta (#FF00FF)
- **Accent**: Neon yellow (#FFD700)
- **Success**: Neon green (#00FF00)
- **Error**: Neon red (#FF0000)

## Installation

1. Clone the repository
2. Open in Android Studio
3. Build and run on an Android device or emulator

## Requirements

- Android API 24+ (Android 7.0)
- Device with accelerometer sensor
- Minimum 2GB RAM recommended

## Usage

1. **Select Dice**: Use the +/- buttons to choose dice quantities
2. **Set Modifier**: Adjust the roll modifier as needed
3. **Roll**: Either tap the ROLL button or shake your device
4. **View Results**: Check the sidebar for recent rolls
5. **Save Presets**: Create custom presets for frequently used combinations

## Development

The app is built using:
- **Kotlin** with **Jetpack Compose**
- **Material 3** design system
- **MVVM** architecture pattern
- **StateFlow** for reactive state management
- **Android Sensors** for shake detection

## License

**Copyright © 2024 Brian Moler. All rights reserved.**

This project is **NOT open source** and is privately owned by Brian Moler. 

- **Private Project**: This codebase is proprietary and confidential
- **No Public License**: No open source license is granted
- **All Rights Reserved**: All intellectual property rights are retained by Brian Moler
- **No Redistribution**: This code may not be copied, modified, or distributed without explicit permission

For licensing inquiries, please contact Brian Moler directly. 