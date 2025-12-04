package src.scrabble.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import src.scrabble.model.GameEvent;
import src.scrabble.model.GameListener;
import src.scrabble.model.Player;
import src.scrabble.model.ScrabbleModel;

/**
 * The ScrabbleView class represents the main GUI component
 * of the Scrabble game for Milestone 3.
 * It displays the board, player racks, and scores.
 *
 * This class implements the GameListener interface
 * so that it can respond to game state updates from the model.
 */
public class ScrabbleView extends JFrame implements GameListener {
    private BoardPanel boardPanel;
    private RackPanel rackPanel;
    private ScorePanel scorePanel;
    private ControlPanel controlPanel;
    private JLabel statusLabel;
    private ScrabbleModel model;

    /**
     * Constructs the main ScrabbleView.
     *
     * @param var1 model the ScrabbleModel instance representing game state.
     */
    public ScrabbleView(ScrabbleModel var1) {
        this.model = var1;
        this.setTitle("Scrabble - Milestone 3");
        this.setDefaultCloseOperation(3);
		this.setLayout(new BorderLayout(6, 6));
        
        // initializing the panels
        this.boardPanel = new BoardPanel(var1.getBoard());
        this.rackPanel = new RackPanel();
        this.scorePanel = new ScorePanel(var1.getPlayers());
        this.controlPanel = new ControlPanel();
        this.statusLabel = new JLabel("Welcome to Scrabble!", 0);
		this.statusLabel.setFont(new Font("Arial", 1, 15));
		this.statusLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        JPanel var2 = new JPanel(new BorderLayout());
        var2.add(this.boardPanel, "Center");
        var2.add(this.scorePanel, "East");
        this.add(this.statusLabel, "North");
        this.add(var2, "Center");
        this.add(this.rackPanel, "South");
        this.add(this.controlPanel, "West");
        this.setSize(1200, 800);
        this.setMinimumSize(new Dimension(1000, 700));
        this.setLocationRelativeTo((Component)null);
        this.setResizable(true);
        this.updateDisplay();
    }

    /**
     * Called whenever the model sends a GameEvent.
     * Updates the UI depending on the event type.
     *
     * @param var1 event the GameEvent describing the model change
     */
    public void gameStateChanged(GameEvent var1) {
        SwingUtilities.invokeLater(() -> {
            switch (var1.getType()) {
                case BOARD_UPDATED:
                    this.boardPanel.repaint();
                    break;
                case RACK_UPDATED:
                case TURN_CHANGED:
                    this.updateDisplay();
                    break;
                case SCORE_UPDATED:
                    this.scorePanel.updateScores(this.model.getPlayers());
                    this.controlPanel.appendLog(var1.getMessage());
                    break;
                case INVALID_MOVE:
                    JOptionPane.showMessageDialog(this, var1.getMessage(), "Invalid Move", 2);
                    break;
                case GAME_STARTED:
                case PLAYER_UPDATED:
                case GAME_ENDED:
                    this.updateDisplay();
                    if (var1.getType() == GameEvent.Type.GAME_ENDED) {
						this.controlPanel.getPlaceWordButton().setEnabled(false);
						this.controlPanel.getExchangeTilesButton().setEnabled(false);
						this.controlPanel.getPassButton().setEnabled(false);
                        GameStatsDialog var10002 = new GameStatsDialog(this, this.model.getPlayers());
                        var10002.setVisible(true);
					} else if (var1.getType() == GameEvent.Type.GAME_STARTED) {
						this.controlPanel.getPlaceWordButton().setEnabled(true);
						this.controlPanel.getExchangeTilesButton().setEnabled(true);
						this.controlPanel.getPassButton().setEnabled(true);
                    }
            }

            if (var1.getMessage() != null && !var1.getMessage().isEmpty()) {
                this.statusLabel.setText(var1.getMessage());
            }

        });
    }

    /**
     * Refreshes the content of the board, rack, score panel, and status bar.
     * Called frequently after model updates.
     */
    private void updateDisplay() {
        Player var1 = this.model.getCurrentPlayer();
        String var2 = var1.isAI() ? " (AI)" : "";
        this.rackPanel.setTiles(var1.getRack());
        JLabel var10000 = this.statusLabel;
        String var10001 = var1.getName();
        var10000.setText("Current Player: " + var10001 + var2 + " | Score: " + var1.getScore() + " | Tiles left: " + this.model.getTilesRemaining());
        this.scorePanel.updateScores(this.model.getPlayers());
        this.boardPanel.repaint();
        this.rackPanel.repaint();
    }

    /**
     * Provides access to the control panel (for controllers to attach listeners).
     *
     * @return the ControlPanel instance
     */
    public ControlPanel getControlPanel() {
        return this.controlPanel;
    }

    /**
     * Provides access to the board panel (useful for controllers).
     *
     * @return the BoardPanel instance
     */
    public BoardPanel getBoardPanel() {
        return this.boardPanel;
    }

	/**
	 * Provides access to the rack panel (for selection-aware placement).
	 *
	 * @return the RackPanel instance
	 */
	public RackPanel getRackPanel() {
		return this.rackPanel;
	}
}
