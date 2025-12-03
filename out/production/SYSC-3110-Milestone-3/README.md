# SYSC 3110 Project - Scrabble

This is a GUI-based version of the Scrabble game developed for Milestone 3. The project builds upon the older versions from Milestones 1 and 2, using the Model-View-Controller (MVC) pattern and Java’s Event Model to handle user interactions.

This version allows for 2-4 AI players. The AI uses backtracking to generate all permutations (2-7 letters) from its rack, validates each permutation against dictionary.txt using HashSet lookup, evaluates every board position with both directions, calculates scores including premium squares, and selects the highest-scoring legal move. If no feasible move exists, AI chooses to pass as per Scrabble rules.

## Authors: 
Uday Arya - 101268848 <br>
Chukwuemeka Igwe - 101219454 <br>
Sahil Todeti - 101259541 <br>
Ashfaqul Alam - 101195158

## Milestones
### Milestone 1:
_Uday Arya: Implemented README, WordTest, TileBag, ScrabbleGame, Main <br><br>
Chukwuemeka Igwe: Implemented Tile Class, UML <br><br>
Sahil Todeti: Implemented Player class, UML <br><br>
Ashfaqul Alam: Implemented README, Board Class, UML_

### Milestone 2:
_Uday Arya: Implemented README, debugged code <br><br>
Chukwuemeka Igwe: Implemented README, Test Cases, Sequence Diagram <br><br>
Sahil Todeti: Implemented View for GUI. <br><br>
Ashfaqul Alam: Implemented Controller and Model for GUI, UML._

### Milestone 3: 
_Uday Arya: Implemented README, Testing, <br><br>
Chukwuemeka Igwe: Implemented Sequence diagram, UML modeling, <br><br>
Sahil Todeti: Implemented Model for MVC, and Documentation, <br><br>
Ashfaqul Alam: Implemented MVC 

### Milestone 4:

### Milestone 5 (Bonus): Feature 3 – Game Statistics Display
- Selected Feature: Game Statistics Display (Feature 3)
- Gameplay: At the end of the game, a statistics dialog automatically appears showing:
  - Number of turns taken by each player
  - Total points scored by each player
  - Words played by each player along with their individual scores
- Implementation:
  - Tracking statistics per player added in `src/scrabble/model/Player.java` (turn count and per-word scores).
  - Stats recorded in `src/scrabble/model/ScrabbleModel.java`:
    - On word placement, the word and its score are stored and the turn count increments.
    - On tile exchange and pass, the turn count increments.
  - End-of-game UI in `src/scrabble/view/GameStatsDialog.java`:
    - Summary table for Player, Turns, and Total Points.
    - Tabbed view listing words and scores per player.
  - Hooked into game flow in `src/scrabble/view/ScrabbleView.java`:
    - When a `GAME_ENDED` event is received, the Game Statistics dialog is displayed.
- Contributors:
  - Uday Arya: Integrated stats UI (GameStatsDialog) and README update
  - Chukwuemeka Igwe: View hook on game end and UI polish
  - Sahil Todeti: Player model extensions for stats (turns, words, scores)
  - Ashfaqul Alam: Model updates to record stats on moves/pass/exchange
