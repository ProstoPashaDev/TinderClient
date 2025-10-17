# 🎓 Innolove – Matchmaking App for Innopolis University (Client side)

## Description (Project in development)
**Innolove** is a matchmaking application designed for students and staff of **Innopolis University**.  
The system allows users to create personal profiles, set preferences, and find matches among other members of the university community.  
The app ensures data privacy and secure authentication while providing a simple, friendly interface similar to **Tinder**.

Users can:
- Create an account using their Innopolis email
- Set up a profile (photo, bio, interests)
- Swipe through other profiles
- Like or dislike other users
- Chat with matched users

---

## Requirements
To run the application, you must have:
- **Java 17** or higher
- Internet access (for server communication)
- (Optional) A running instance of the **Innolove Backend Server** if you are testing locally

---

## Running the Application
1. Run the application file:
   Innolove-1.0-RELEASE-shaded.jar
2. Enter the password you used when creating your keystore (steps 3–4 above).
3. The main window will open. You can now:
- Sign in with your Innopolis email
- Edit your profile
- Browse and match with other users
- Start chatting 💬

---

## Technologies
- **JavaFX** – for the desktop and mobile client interface
- **Spring Boot** – backend server and API layer
- **Hibernate / PostgreSQL** – user data and match storage
- **REST / JSON Web Tokens (JWT)** – secure communication between client and server
- **Maven** – project build and dependency management

---

## Security Notes
- All user data is encrypted before storage.
- Passwords and personal information are never stored in plain text.
- The system uses the **Java Keystore (JKS)** mechanism for secure credential management.

---
