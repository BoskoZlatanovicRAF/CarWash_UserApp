# Payten Car Wash Integration System

## Overview
This project was developed for the Payten Hackathon Vol.3, creating an integrated system for car wash services. The system consists of two Android applications that work together: a POS terminal app for payment processing and a user app for customer engagement.

## Components

### POS Terminal App
The POS terminal app handles payment processing at car wash locations. Features include:
- QR code scanning for user identification
- Token-based payment system
- Membership discount support
- Washing time management
- Integration with payment processing hardware

### User App
The user app provides customers with a streamlined experience. Features include:
- User authentication and profile management
- QR code generation for terminal identification
- Membership benefits tracking
- Car wash location finder with map integration
- Transaction history
- Time savings for future visits

## Technology Stack
- Kotlin
- Jetpack Compose for modern UI
- MVVM architecture
- Room database for local storage
- Hilt for dependency injection
- DataStore for persisting user preferences
- Yandex Maps for location services
- ZXing for QR code generation/scanning

## Integration Flow
1. User generates a QR code in the User App
2. POS Terminal scans the QR code to identify the user and apply discounts
3. User selects washing duration and completes payment
4. Terminal processes the payment and starts the washing cycle
5. Transaction data is stored and bonus points are awarded

## Development Setup
### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 27+
- Gradle 8.7

### Build Instructions
1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Build and run either the POS terminal app or user app on compatible devices

## Team
This project was developed by a team of developers during the Payten Hackathon Vol.3, demonstrating integration capabilities between payment systems and user-facing applications.

## Notes
- The POS terminal app is designed to work with Payten payment processing systems
- The integration demonstrates a complete customer journey from finding a car wash to completing service
- Both apps feature an offline-first approach to ensure reliability in varying network conditions
