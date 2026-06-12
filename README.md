# SIT708_5.1C_MEDIA

## Overview

This project is an Android iStream Media Playlist App developed for SIT708 Task 5.1C. The app allows users to sign up, log in, enter a YouTube video URL, play the video inside the app, and save videos into a personal playlist.

The application uses fragments and Jetpack Navigation Component to manage different screens. It also uses Room Database to store user and playlist data locally.

## Features

- User sign up
- User login
- Stream home page
- Enter YouTube video URL
- Play YouTube video inside the app
- Add video to playlist
- View saved playlist
- Store user data locally
- Store playlist data locally
- Fragment-based navigation
- Room Database local storage
- WebView video playback

## Project Structure

```text
app/src/main/java/com.example.a51c_media/
├── MainActivity.java
├── LoginFragment.java
├── SignUpFragment.java
├── StreamHomeFragment.java
├── PlayerFragment.java
├── PlaylistFragment.java
├── User.java
├── UserDAO.java
├── Playlist.java
├── PlaylistDao.java
└── AppDatabase.java

app/src/main/res/layout/
├── activity_main.xml
├── fragment_login.xml
├── fragment_sign_up.xml
├── fragment_stream_home.xml
├── fragment_player.xml
└── fragment_playlist.xml
