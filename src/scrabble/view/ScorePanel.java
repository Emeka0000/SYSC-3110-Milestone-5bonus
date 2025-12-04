package src.scrabble.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import src.scrabble.model.Player;

/**
 * The ScorePanel displays the list of players and their scores
 * in a scrollable text area on the right side of the Scrabble UI.
 * 
 */
public class ScorePanel extends JPanel {
    private JTextArea scoreArea;

    /**
     * Creates a ScorePanel that initially displays scores
     * for the provided list of players.
     *
     * @param var1 the list of players whose scores are shown
     */
    public ScorePanel(List<Player> var1) {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(200, 600));
        this.scoreArea = new JTextArea();
        this.scoreArea.setEditable(false);
        this.scoreArea.setFont(new Font("Monospaced", 0, 12));
        JScrollPane var2 = new JScrollPane(this.scoreArea);
        var2.setBorder(BorderFactory.createTitledBorder("Scores"));
        this.add(var2, "Center");
        this.updateScores(var1);
    }

    /**
     * Updates the score list shown in the text area.
     * Called whenever the model indicates that a score has changed.
     *
     * @param var1 the updated list of players
     */
    public void updateScores(List<Player> var1) {
        StringBuilder var2 = new StringBuilder();
        var2.append("=== SCORES ===\n\n");

        for (Player var4 : var1) { // loop thru all the players and print scores
            String var5 = var4.isAI() ? " (AI)" : "";
            var2.append(var4.getName()).append(var5).append(":\n");
			var2.append("  ").append(var4.getScore()).append(" pts\n");
			var2.append("  Turns: ").append(var4.getTurnCount()).append("\n");
			int var6 = var4.getWordsPlayed().size();
			if (var6 > 0) {
				String var7 = var4.getWordsPlayed().get(var6 - 1);
				int var8 = var4.getWordScores().get(var6 - 1);
				var2.append("  Last: ").append(var7).append(" (+").append(var8).append(")\n");
			}
			var2.append("\n");
        }

        this.scoreArea.setText(var2.toString());
    }
}
