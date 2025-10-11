# 2IP90-CBL

Snackademy first backlog 
In this game, you play as a student sitting in the Metaforum library. Your goal is to eat snacks or whisper to your friends without getting caught by the librarian. The game works a bit like “Red Light, Green Light”:
When the librarian is looking away, you can act. When the librarian is watching, you must stay still or you get caught!
Topics
-	Game Design / User Experience (UX)
We want to learn how to make the game fun, clear, and balanced for the player (for example, feedback, timing, and difficulty).
-	Version control (git/hg),
We want to be better at collaborating and sharing our code. 
1.	High-Priority Features (Main Game Features)
#	What?	How to Demo	Notes	Learning Goal
1	Player Movement	Start the game → press arrow keys → the player moves around the screen.	The player stays inside the room and moves smoothly.	Learn how to handle keyboard input and movement using Java.
2	Librarian Watching System	The librarian turns around every few seconds → sometimes watching, sometimes not.	A small animation or icon shows when the librarian is watching.	Learn about timers and how to update the screen with Swing.
3	Caught by Librarian	Move when the librarian is watching → a message appears (“You got caught!”) → game resets.	Basic game-over condition.	Learn how to detect actions and show simple messages.
4	Sneak / Whisper Action	Press the spacebar when the librarian isn’t watching → earn points.	Adds fun interaction for the player.	Learn how to handle key events and update game logic.
5	Score Display	Each successful action gives points → score is shown on screen.	Score label updates while playing.	Learn how to use Swing components (labels) and update them during the game.
6	Win Condition	Reach a target score or survive for a set time → “You Win!” message shows.	Ends the game and displays a win screen.	Learn how to connect game logic with screen updates.
Medium-Priority Features (Extra Polish)
#	Name	How to Demo	Notes	Learning Goal
7	Main Menu and Instructions	Start the program → menu shows buttons: “Play”, “How to Play”, and “Exit.”	Simple navigation with buttons and panels.	Practice building GUIs with Swing layouts and buttons.
8	Pause / Resume Game	Press P → the game pauses; press again → the game continues.	Useful for testing and gameplay control.	Learn how to stop and start timers
9	Sound Effects	When sneaking or getting caught → short sound plays.	Optional feature to make the game more fun.	Learn how to use simple Java sound libraries.
10	Simple Animations	The librarian and player move or turn smoothly.	Makes the game look more alive.	Learn how to repaint graphics and use images.
Low-Priority / Stretch Features (Optional)
#	Name	How to Demo	Notes	Learning Goal
11	Multiple Levels / Rooms	After winning → a new library layout appears.	Adds replayability.	Learn how to load different backgrounds and reset the game.
12	High Score List	After finishing → see your best scores.	Save scores in a text file.	Learn basic file input/output in Java.
13	Random Distractions (Events)	A phone rings or someone coughs → random events affect the player.	Adds variety to gameplay.	Learn how to create random events using Java’s random class.
14	Lighting or Focus Effect	The room becomes darker when the librarian watches.	Makes the game feel more intense.	Practice graphics drawing with Graphics2D.

