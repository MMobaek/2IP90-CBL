# Snackademy — 2IP90-CBL

Welcome to **Snackademy**, a stealthy student game set in the Metaforum library.  
Your mission: sneak snacks or whisper to friends without getting caught by the ever-watchful librarian.

Inspired by “Red Light, Green Light”:
- When the librarian is watching: stay still.  
- When they look away: act fast!

---

## Learning Goals

| Area                     | Goal                                                                 |
|---------------------------|----------------------------------------------------------------------|
| Game Design / UX          | Make the game fun, clear, and balanced (feedback, timing, difficulty). |
| Version Control (Git)     | Improve collaboration and code sharing.                              |

---

## High-Priority Features

| # | Feature                  | How to Demo                                                  | Notes                                      | Learning Goal                                      |
|---|--------------------------|---------------------------------------------------------------|--------------------------------------------|----------------------------------------------------|
| 1 | Player Movement          | Start game → press arrow keys → player moves                 | Stays inside room, smooth movement         | Handle keyboard input and movement in Java         |
| 2 | Librarian Watching       | Librarian turns every few seconds                            | Icon/animation shows watching state        | Use timers and update screen with Swing            |
| 3 | Caught by Librarian      | Move while watched → “You got caught!” → game resets         | Basic game-over condition                  | Detect actions and show messages                   |
| 4 | Sneak / Whisper Action   | Press spacebar when not watched → earn points                | Adds fun interaction                       | Handle key events and update game logic            |
| 5 | Score Display            | Earn points → score shown on screen                          | Score label updates live                   | Use Swing labels and update during gameplay        |
| 6 | Win Condition            | Reach score or survive time → “You Win!” message             | Ends game and shows win screen             | Connect game logic with screen updates             |

---

## Medium-Priority Features

| # | Feature                  | How to Demo                                                  | Notes                                      | Learning Goal                                      |
|---|--------------------------|---------------------------------------------------------------|--------------------------------------------|----------------------------------------------------|
| 7 | Main Menu & Instructions | Start → menu with “Play”, “How to Play”, “Exit”              | Simple navigation                          | Build GUIs with Swing layouts and buttons          |
| 8 | Pause / Resume Game      | Press P → pause/resume                                       | Useful for testing                         | Stop/start timers                                  |
| 9 | Sound Effects            | Sneak or get caught → sound plays                            | Optional fun                               | Use Java sound libraries                           |
|10 | Simple Animations        | Smooth movement/turning of characters                        | Adds life to game                          | Repaint graphics and use images                    |

---

## Low-Priority / Stretch Features

| # | Feature                  | How to Demo                                                  | Notes                                      | Learning Goal                                      |
|---|--------------------------|---------------------------------------------------------------|--------------------------------------------|----------------------------------------------------|
|11 | Multiple Levels / Rooms  | Win → new library layout                                     | Adds replayability                         | Load backgrounds and reset game                    |
|12 | High Score List          | Finish → see best scores                                     | Save scores to file                        | File I/O in Java                                   |
|13 | Random Distractions      | Phone rings, coughs → random events                          | Adds variety                               | Use Java’s random class                            |
|14 | Lighting / Focus Effect  | Room darkens when watched                                    | Adds intensity                             | Graphics drawing with Graphics2D                   |

---

## Class Overview

| Class Name     | Responsibility |
|----------------|----------------|
| **Main**           | Launches the game and initializes core objects. |
| **UILayout**       | Builds and manages the graphical interface (Swing panels, labels, buttons). |
| **GameController** | Handles input, timing, and game state logic. |
| **Player**         | Manages player movement, actions, and rendering. |
| **Librarian**      | Controls the attention phases and determines whether the player is being watched. |

---

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/MMobaek/snackademy.git

2. Open the project in your Java IDE (IntelliJ IDEA, Eclipse, or VS Code with Java support).

3. Ensure you have Java 17 or later installed.

4. Run the Main.java file to start the game.

5. If you cannot run the Main.java, doing 'run java' might work instead

## Authors

| Name          | 
| ------------- | 
| Magnus Mobæk |  
| Eline Smit    | 
