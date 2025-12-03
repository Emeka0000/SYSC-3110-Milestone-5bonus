package src.scrabble;

import java.awt.Component;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import src.scrabble.controller.ScrabbleController;
import src.scrabble.model.AIPlayer;
import src.scrabble.model.Dictionary;
import src.scrabble.model.ScrabbleModel;
import src.scrabble.view.ScrabbleView;

/**
 * Main entry point for the Scrabble Game - Milestone 3.
 * Handles startup UI (player setup), initializes Model, View and Controller.
 *
 * @author Ashfaqul Alam, 101195158
 * @Version Nov 22nd, Milestone 3
 */
public class

Main {
    public Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                // Ask number of players
                String var0 = JOptionPane.showInputDialog((Component)null, "Enter number of players (2-4):", "Scrabble - Milestone 3", -1);
                if (var0 == null) {
                    System.exit(0);
                }

                int var1 = Integer.parseInt(var0);
                if (var1 < 2 || var1 > 4) {
                    JOptionPane.showMessageDialog((Component)null, "Must be 2-4 players", "Error", 0);
                    System.exit(0);
                }

                // Collect player names + type (Human / AI)
                ArrayList var2 = new ArrayList();
                ArrayList var3 = new ArrayList();

                for(int var4 = 1; var4 <= var1; ++var4) {
                    String var5 = JOptionPane.showInputDialog((Component)null, "Player " + var4 + " name:", "Player Setup", -1);
                    if (var5 == null) {
                        System.exit(0);
                    }

                    if (var5.trim().isEmpty()) {
                        var5 = "Player" + var4;
                    } else {
                        var5 = var5.trim();
                    }

                    String[] var6 = new String[]{"Human", "AI"};
                    int var7 = JOptionPane.showOptionDialog((Component)null, "Is " + var5 + " Human or AI?", "Player Type Selection", -1, 3, (Icon)null, var6, var6[0]);
                    boolean var8 = var7 == 1;
                    var2.add(var5);
                    var3.add(var8);
                }

                ScrabbleModel var11 = new ScrabbleModel(var2);
                System.out.println("Loading dictionary..."); // Load dictionary
                Dictionary var13 = new Dictionary("dictionary.txt");
                if (var13.isEmpty()) {
                    System.err.println(" Dictionary file not found or empty");
                    System.err.println("   Continuing without word validation");
                } else {
                    System.out.println(" Dictionary loaded successfully: " + var13.size() + " words");
                }

                var11.setDictionary(var13);
                
                List var14 = var11.getPlayersList(); // Replace model players with AI players if needed

                for (int var15 = 0; var15 < var14.size(); ++var15) {
                    if ((Boolean)var3.get(var15)) {
                        AIPlayer var17 = new AIPlayer((String)var2.get(var15));
                        var17.setDictionary(var13);
                        var14.set(var15, var17);
                        PrintStream var10000 = System.out;
                        Object var10001 = var2.get(var15);
                        var10000.println(" AI Player created: " + (String)var10001);
                    }
                }

                ScrabbleView var16 = new ScrabbleView(var11);
                new ScrabbleController(var11, var16);
                var16.setVisible(true);
                var11.startGame();
                System.out.println(" Game started successfully!");
                System.out.println("   Column input: 1-15 (not A-O)");
                System.out.println("   Words validated against dictionary");
            } catch (NumberFormatException var9) {
                JOptionPane.showMessageDialog((Component)null, "Invalid number format. Please enter 2-4.", "Error", 0);
                System.err.println(" Error: Invalid number format - " + var9.getMessage());
                System.exit(1);
            } catch (Exception var10) {
                JOptionPane.showMessageDialog((Component)null, "Error starting game: " + var10.getMessage(), "Error", 0);
                System.err.println(" Fatal error: " + var10.getMessage());
                var10.printStackTrace();
                System.exit(1);
            }
        });
    }
}
