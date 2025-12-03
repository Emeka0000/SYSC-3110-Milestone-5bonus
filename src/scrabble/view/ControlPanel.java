package src.scrabble.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * The ControlPanel contains the main action buttons for the Scrabble UI: Place Word, Exchange Tiles, Pass Turn.
 *
 * @author Sahil Todeti, 101259541
 * @version Nov 21st, Milestone 3
 */
public class ControlPanel extends JPanel {
    private JButton placeWordButton;
    private JButton exchangeTilesButton;
    private JButton passButton;
    private JTextArea logArea;

    /**
     * Constructs the ControlPanel and initializes all components:
     * action buttons and a scrollable log display.
     */
    public ControlPanel() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(220, 600));
        JPanel var1 = new JPanel();
        var1.setLayout(new BoxLayout(var1, 1));
        var1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.placeWordButton = this.createButton("Place Word");
        this.exchangeTilesButton = this.createButton("Exchange Tiles");
        this.passButton = this.createButton("Pass Turn");
        var1.add(this.placeWordButton);
        var1.add(Box.createVerticalStrut(10));
        var1.add(this.exchangeTilesButton);
        var1.add(Box.createVerticalStrut(10));
        var1.add(this.passButton);
        this.logArea = new JTextArea(20, 15);
        this.logArea.setEditable(false);
        this.logArea.setLineWrap(true);
        this.logArea.setWrapStyleWord(true);
        JScrollPane var2 = new JScrollPane(this.logArea);
        var2.setBorder(BorderFactory.createTitledBorder("Game Log"));
        this.add(var1, "North");
        this.add(var2, "Center");
    }

    /**
     * Helper method to create a styled button for the control panel.
     *
     * @param label the button text
     * @return a configured JButton
     */
    private JButton createButton(String var1) {
        JButton var2 = new JButton(var1);
        var2.setAlignmentX(0.5F);
        var2.setMaximumSize(new Dimension(180, 40));
        return var2;
    }

    /** @return the button used to place a word */
    public JButton getPlaceWordButton() {
        return this.placeWordButton;
    }

    /** @return the button used to exchange tiles */
    public JButton getExchangeTilesButton() {
        return this.exchangeTilesButton;
    }

    /** @return the button used to pass the turn */
    public JButton getPassButton() {
        return this.passButton;
    }

    /**
     * Appends a message to the game log.
     *
     * @param var1 text to append
     */
    public void appendLog(String var1) {
        this.logArea.append(var1 + "\n");
        this.logArea.setCaretPosition(this.logArea.getDocument().getLength());
    }
}
