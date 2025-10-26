# Snackademy

**Authors:** Magnus Mobæk, Eline Smit  
**Course:** Eindhoven University of Technology - 2IP90 Programming

---

## Overview

Snackademy is a Java-based game set in the Metaforum library. You play as a student attempting to deliver snacks or whisper to friends without being caught by the librarian.  

The game emphasizes timing, strategy, and stealth, where the librarian's attention determines your freedom to move and act.

---

## Gameplay

- The librarian cycles between three states:
  1. **INATTENTIVE** – you can move freely.
  2. **TRANSITION** – the librarian is turning; act cautiously.
  3. **ATTENTIVE** – the librarian is watching; you must remain still or get caught.
- Deliver snacks from the **Snackstation** to the **Desk** while avoiding detection.
- Interact with various objects in the library:
  - **Bookshelves** – obstacles that are randomly positioned each round.
  - **Movable text** – guides and informs the player.
  - **Snack counter** – tracks successful deliveries.

---

## Project Structure

All Java classes are inside the `com.snackademy` package. Below is an overview of major classes and their roles:

### Main Classes

| Class | Description |
|-------|-------------|
| `Main` | Entry point of the game. Launches the `GameFrame` and shows the start menu. |
| `GameFrame` | The main JFrame container. Switches between start menu, game, leaderboard, and settings screens. |
| `UILayout` | Builds and displays the main game panel. Handles object positioning, resizing, snack counter, movable text, and debug overlay. |
| `GameController` | Connects UI with player and librarian. Handles input, movement logic, and game rules. |

### Player and Movement

| Class | Description |
|-------|-------------|
| `Player` | Represents the player character. Manages position, walking animation, and interactions. |
| `MovingPlayer` | Listens for keyboard input to move the player and triggers callbacks. |

### Librarian

| Class | Description |
|-------|-------------|
| `Librarian` | Implements a finite state machine for the librarian’s attention: INATTENTIVE, TRANSITION, ATTENTIVE. Updates the icon and behavior. |

### Objects in the Game

| Class | Description |
|-------|-------------|
| `Desk` | Represents the static desk object where snacks are delivered. |
| `Snackstation` | Static station where snacks are picked up. |
| `Bookshelf` | Randomly positioned obstacles in the library. |
| `DebugOverlayPanel` | Optional overlay for debug visualization of object positions. |

### UI and Screens

| Class | Description |
|-------|-------------|
| `StartMenuScreen` | Main start menu with buttons to start game, view leaderboard, or access settings. |
| `SettingsScreen` | Allows adjusting game settings such as bookshelf count and difficulty. |
| `LeaderboardScreen` | Displays high scores of players. |
| `CaughtScreen` | Shown when the player is caught by the librarian. |
| `SaveProgressScreen` | Dialog to save player’s snack count and name. |
| `HelpScreen` | Provides instructions and controls for the game. |

### Utilities

| Class | Description |
|-------|-------------|
| `ScoreEntry` | Stores player name and score for leaderboard. |
| `MusicPlayer` | Handles background music during gameplay. |

---

## Installation

1. Open the project in your favorite Java IDE (IntelliJ, Eclipse, NetBeans).
2. Ensure the project uses **Java 17** (or a compatible version).
3. Build the project to compile all classes.

---

## Running the Game

- Run `Main.java` to start the game.
- The start menu allows you to:
  - Start a new game
  - View the leaderboard
  - Change settings
  - Access help

---

## Controls

- **Movement:** `W`, `A`, `S`, `D` or arrow keys
- Avoid being seen by the librarian while delivering snacks
- Follow on-screen instructions and movable text

---

## Features to Test

| Feature | How to Access / Test |
|---------|--------------------|
| Snack delivery | Pick up snacks from the Snackstation and deliver them to the Desk while avoiding the librarian. Check if the snack counter updates. |
| Librarian attention states | Observe the librarian icon and test movement during **INATTENTIVE**, **TRANSITION**, and **ATTENTIVE** states to ensure correct behavior. |
| Random bookshelf placement | Start a new game multiple times and verify that bookshelf positions change each round. |
| Movable text | Look for tutorial text and instructions that appear dynamically on the screen. |
| Debug overlay | Enable the debug overlay via `UILayout` settings to see object positions and player coordinates. |
| Leaderboard | Complete a game and save progress via `SaveProgressScreen` to see entries appear on `LeaderboardScreen`. |
| Music and sound | Ensure background music plays when `MusicPlayer` is active. |

---

## Notes

- All UI follows the **2IP90 Java Coding Standard** from TU/e
- Snack counter and other interactive elements update dynamically
- Game supports random bookshelf placement for replayability
- Debug overlay can be enabled for testing

---

## Authors

- Magnus Mobæk
- Eline Smit

Eindhoven University of Technology — 2IP90 Programming
