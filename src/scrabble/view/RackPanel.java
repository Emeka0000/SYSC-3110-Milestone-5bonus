package src.scrabble.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * The RackPanel visually displays the current player's rack of Scrabble tiles.
 * 
 */
public class RackPanel extends JPanel {
    private List<Character> tiles = new ArrayList();
	private static final int TILE_SIZE = 50;
	private List<Integer> selected = new ArrayList();

    /**
     * Constructs a RackPanel with fixed preferred size, background color,
     * and a titled border.
     */
    public RackPanel() {
		this.setPreferredSize(new Dimension(800, 80));
        this.setBackground(new Color(139, 69, 19));
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), "Your Tiles", 0, 0, new Font("Arial", 1, 14), Color.WHITE));
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent var1) {
				int var2 = (var1.getX() - computeStartX()) / (TILE_SIZE + 10);
				if (var2 >= 0 && var2 < tiles.size()) {
					if (selected.contains(var2)) {
						selected.remove((Object)var2);
					} else {
						selected.add(var2);
					}
					repaint();
				}
			}
		});
    }

    /**
     * Updates the list of tiles to display and triggers a repaint.
     *
     * @param var1 the list of letters currently in the player's rack
     */
    public void setTiles(List<Character> var1) {
        this.tiles = new ArrayList(var1);
		this.selected.clear();
        this.repaint();
    }

    /**
     * Paints the rack background and the Scrabble tiles.
     * This is where each letter tile is drawn as a rounded square.
     *
     * @param var1 the Graphics context used for drawing
     */
    @Override
    protected void paintComponent(Graphics var1) {
        super.paintComponent(var1);
        Graphics2D var2 = (Graphics2D)var1;
        
        // Enable anti-aliasing for high quality graphics
        var2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (!this.tiles.isEmpty()) {
			int var3 = computeStartX();

            for(int var4 = 0; var4 < this.tiles.size(); ++var4) { // Draw each tile
				int var5 = var3 + var4 * (TILE_SIZE + 10);
				byte var6 = 12;
				if (this.selected.contains(var4)) {
					var2.setColor(new Color(255, 235, 150));
				} else {
					var2.setColor(new Color(255, 248, 220));
				}
				var2.fillRoundRect(var5, var6, TILE_SIZE, TILE_SIZE, 10, 10);
                var2.setColor(Color.BLACK);
                var2.setStroke(new BasicStroke(2.0F));
				var2.drawRoundRect(var5, var6, TILE_SIZE, TILE_SIZE, 10, 10);
				var2.setFont(new Font("Arial", 1, 26));
                String var7 = String.valueOf(this.tiles.get(var4));
                FontMetrics var8 = var2.getFontMetrics();
				int var9 = var5 + (TILE_SIZE - var8.stringWidth(var7)) / 2;
				int var10 = var6 + (TILE_SIZE + var8.getAscent()) / 2 - 5;
                var2.drawString(var7, var9, var10);
            }

        }
    }

	private int computeStartX() {
		return (this.getWidth() - this.tiles.size() * (TILE_SIZE + 10)) / 2;
	}

	public String getSelectedWord() {
		StringBuilder var1 = new StringBuilder();
		for (Integer var3 : this.selected) {
			var1.append(this.tiles.get(var3));
		}
		return var1.toString();
	}

	public void clearSelection() {
		this.selected.clear();
		this.repaint();
	}
}
