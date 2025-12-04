package src.scrabble.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import src.scrabble.model.Board;
import src.scrabble.model.PremiumSquare;

/**
 * The BoardPanel is responsible for visually displaying the 15x15 Scrabble board.
 *
 */
public class BoardPanel extends JPanel {
    private Board board;
    private static final int CELL_SIZE = 45;
    private static final int GRID_SIZE = 15;

    /**
     * Constructs the panel used to draw the Scrabble board.
     *
     * @param var1 the board model to read tile and premium information from
     */
    public BoardPanel(Board var1) {
        this.board = var1;
        this.setPreferredSize(new Dimension(676, 676));
        this.setBackground(Color.WHITE);
    }

    /**
     * Paints the Scrabble board grid, premium squares, and placed tiles.
     *
     * @param var1 the Graphics object used for drawing
     */
    @Override
    protected void paintComponent(Graphics var1) {
        super.paintComponent(var1);
        Graphics2D var2 = (Graphics2D)var1;
        var2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int var3 = 0; var3 < 15; ++var3) { // Loop through each grid cell
            for (int var4 = 0; var4 < 15; ++var4) {
                int var5 = var4 * 45;
                int var6 = var3 * 45;
                PremiumSquare var7 = this.board.getPremium(var3, var4);
                switch (var7) {
                    case TRIPLE_WORD -> var2.setColor(new Color(255, 100, 100));
                    case DOUBLE_WORD -> var2.setColor(new Color(255, 180, 180));
                    case TRIPLE_LETTER -> var2.setColor(new Color(100, 100, 255));
                    case DOUBLE_LETTER -> var2.setColor(new Color(180, 180, 255));
                    default -> var2.setColor(new Color(240, 240, 240));
                }

                var2.fillRect(var5, var6, 45, 45);
                var2.setColor(Color.GRAY);
                var2.drawRect(var5, var6, 45, 45);
                Character var8 = this.board.get(var3, var4);
                if (var8 != null && var8 != '_') {
                    var2.setColor(new Color(255, 248, 220));
                    var2.fillRoundRect(var5 + 3, var6 + 3, 39, 39, 8, 8);
                    var2.setColor(new Color(139, 69, 19));
                    var2.setStroke(new BasicStroke(2.0F));
                    var2.drawRoundRect(var5 + 3, var6 + 3, 39, 39, 8, 8);
                    var2.setStroke(new BasicStroke(1.0F));
                    var2.setColor(Color.BLACK);
                    var2.setFont(new Font("Arial", 1, 22));
                    FontMetrics var9 = var2.getFontMetrics();
                    String var10 = String.valueOf(var8);
                    int var11 = var5 + (45 - var9.stringWidth(var10)) / 2;
                    int var12 = var6 + (45 + var9.getAscent()) / 2 - 2;
                    var2.drawString(var10, var11, var12);
                }
            }
        }
    }
}
