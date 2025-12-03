package src.scrabble.controller;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import src.scrabble.model.ScrabbleModel;
import src.scrabble.view.ScrabbleView;

public class ScrabbleController {
    private ScrabbleModel model;
    private ScrabbleView view;

    public ScrabbleController(ScrabbleModel var1, ScrabbleView var2) {
        this.model = var1;
        this.view = var2;
        var1.addGameListener(var2);
        this.setupEventHandlers();
    }

    private void setupEventHandlers() {
        this.view.getControlPanel().getPlaceWordButton().addActionListener((var1) -> this.handlePlaceWord());
        this.view.getControlPanel().getExchangeTilesButton().addActionListener((var1) -> this.handleExchangeTiles());
        this.view.getControlPanel().getPassButton().addActionListener((var1) -> this.handlePass());
		this.view.getBoardPanel().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent var1) {
				int var2 = var1.getY() / 45;
				int var3 = var1.getX() / 45;
				if (var2 >= 0 && var2 < 15 && var3 >= 0 && var3 < 15) {
					handleQuickPlaceAt(var2, var3);
				}
			}
		});
    }

    private void handlePlaceWord() {
        JPanel var1 = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField var2 = new JTextField();
        JTextField var3 = new JTextField();
        JComboBox var4 = new JComboBox(new String[]{"Horizontal", "Vertical"});
        JTextField var5 = new JTextField();
        var1.add(new JLabel("Row (1-15):"));
        var1.add(var2);
        var1.add(new JLabel("Column (1-15):"));
        var1.add(var3);
        var1.add(new JLabel("Direction:"));
        var1.add(var4);
        var1.add(new JLabel("Word:"));
        var1.add(var5);
        int var6 = JOptionPane.showConfirmDialog(this.view, var1, "Place Word", 2, -1);
        if (var6 == 0) {
            try {
                int var7 = Integer.parseInt(var2.getText().trim()) - 1;
				if (var7 < 0 || var7 > 14) {
					JOptionPane.showMessageDialog(this.view, "Row must be between 1 and 15", "Invalid Input", 0);
					return;
				}
				int var8 = Integer.parseInt(var3.getText().trim()) - 1;
				if (var8 < 0 || var8 > 14) {
					JOptionPane.showMessageDialog(this.view, "Column must be between 1 and 15", "Invalid Input", 0);
					return;
				}
				boolean var9 = var4.getSelectedIndex() == 0;
				String var10 = var5.getText().trim().toUpperCase();
				if (var10.isEmpty()) {
					JOptionPane.showMessageDialog(this.view, "Word cannot be empty", "Invalid Input", 0);
					return;
				}
				if (var10.length() < 2) {
					JOptionPane.showMessageDialog(this.view, "Word must be at least 2 letters", "Invalid Input", 0);
					return;
				}
				if (this.model.getDictionary() != null && !this.model.getDictionary().isEmpty() && !this.model.getDictionary().isValid(var10)) {
					JOptionPane.showMessageDialog(this.view, "Word '" + var10 + "' is not in the dictionary.\nPlease use a valid English word.", "Invalid Word", 2);
					return;
				}
				this.model.placeWord(var7, var8, var9, var10);
				return;
            } catch (NumberFormatException var11) {
                JOptionPane.showMessageDialog(this.view, "Invalid input. Please enter numbers:\nRow: 1-15\nColumn: 1-15", "Input Error", 0);
			} catch (IllegalArgumentException var12) {
				// Model already fired an INVALID_MOVE event which the view handles.
				// Swallow to avoid duplicate popups.
				return;
			} catch (Exception var13) {
				// Unexpected error; show a single error dialog.
				JOptionPane.showMessageDialog(this.view, "Error: " + var13.getMessage(), "Error", 0);
            }
        }

    }

	private void handleQuickPlaceAt(int var1, int var2) {
		JPanel var3 = new JPanel(new GridLayout(2, 2, 5, 5));
		JComboBox var4 = new JComboBox(new String[]{"Horizontal", "Vertical"});
		JTextField var5 = new JTextField();
		String var6 = this.view.getRackPanel().getSelectedWord();
		if (!var6.isEmpty()) {
			var5.setText(var6);
		}
		var3.add(new JLabel("Direction:"));
		var3.add(var4);
		var3.add(new JLabel("Word:"));
		var3.add(var5);
		int var7 = JOptionPane.showConfirmDialog(this.view, var3, "Place at (" + (var1 + 1) + "," + (var2 + 1) + ")", 2, -1);
		if (var7 == 0) {
			try {
				boolean var8 = var4.getSelectedIndex() == 0;
				String var9 = var5.getText().trim().toUpperCase();
				if (var9.isEmpty()) {
					JOptionPane.showMessageDialog(this.view, "Word cannot be empty", "Invalid Input", 0);
					return;
				}
				if (var9.length() < 2) {
					JOptionPane.showMessageDialog(this.view, "Word must be at least 2 letters", "Invalid Input", 0);
					return;
				}
				if (this.model.getDictionary() != null && !this.model.getDictionary().isEmpty() && !this.model.getDictionary().isValid(var9)) {
					JOptionPane.showMessageDialog(this.view, "Word '" + var9 + "' is not in the dictionary.\nPlease use a valid English word.", "Invalid Word", 2);
					return;
				}
				this.model.placeWord(var1, var2, var8, var9);
				this.view.getRackPanel().clearSelection();
			} catch (IllegalArgumentException var9) {
				return;
			} catch (Exception var10) {
				JOptionPane.showMessageDialog(this.view, "Error: " + var10.getMessage(), "Error", 0);
			}
		}
	}

    private void handleExchangeTiles() {
        String var1 = JOptionPane.showInputDialog(this.view, "Enter letters to exchange (e.g., EDN):", "Exchange Tiles", -1);
        if (var1 != null && !var1.trim().isEmpty()) {
            try {
                this.model.exchangeTiles(var1.trim());
            } catch (Exception var3) {
                JOptionPane.showMessageDialog(this.view, "Error: " + var3.getMessage(), "Exchange Failed", 0);
            }
        }

    }

    private void handlePass() {
        this.model.passTurn();
    }
}