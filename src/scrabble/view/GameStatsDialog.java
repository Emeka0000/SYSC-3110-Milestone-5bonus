package src.scrabble.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableModel;
import src.scrabble.model.Player;

public class GameStatsDialog extends JDialog {
    public GameStatsDialog(JFrame var1, List<Player> var2) {
        super(var1, "Game Statistics", true);
        this.setLayout(new BorderLayout(10, 10));
        JLabel var3 = new JLabel("Game Statistics", SwingConstants.CENTER);
        var3.setFont(new Font("Arial", 1, 20));
        var3.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        this.add(var3, "North");
        JPanel var4 = new JPanel(new BorderLayout(10, 10));
        var4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Summary table
        String[] var5 = new String[]{"Player", "Turns", "Total Points"};
        DefaultTableModel var6 = new DefaultTableModel(var5, 0) {
            public boolean isCellEditable(int var1, int var2) {
                return false;
            }
        };
        for (Player var8 : var2) {
            Object[] var9 = new Object[]{var8.getName(), var8.getTurnCount(), var8.getScore()};
            var6.addRow(var9);
        }
        JTable var7 = new JTable(var6);
        var7.setRowHeight(22);
		var7.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        var7.getTableHeader().setReorderingAllowed(false);
        var4.add(new JLabel("Summary", SwingConstants.LEFT), "North");
		JScrollPane var24 = new JScrollPane(var7);
		var24.setPreferredSize(new Dimension(800, 180));
		var4.add(var24, "Center");
        // Per-player words tab
        JTabbedPane var10 = new JTabbedPane();
		var10.setPreferredSize(new Dimension(800, 320));
        for (Player var12 : var2) {
            String[] var13 = new String[]{"Word", "Points"};
            DefaultTableModel var14 = new DefaultTableModel(var13, 0) {
                public boolean isCellEditable(int var1, int var2) {
                    return false;
                }
            };
            List<String> var15 = var12.getWordsPlayed();
            List<Integer> var16 = var12.getWordScores();
            for (int var17 = 0; var17 < var15.size(); ++var17) {
                Object[] var18 = new Object[]{var15.get(var17), var16.get(var17)};
                var14.addRow(var18);
            }
            JTable var19 = new JTable(var14);
            var19.setRowHeight(22);
			var19.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            JScrollPane var20 = new JScrollPane(var19);
            if (var15.isEmpty()) {
                var20.setColumnHeaderView(new JLabel("No words played", SwingConstants.CENTER));
            }
            var10.addTab(var12.getName(), var20);
        }
        JPanel var11 = new JPanel(new BorderLayout(5, 5));
        var11.add(new JLabel("Words by Player", SwingConstants.LEFT), "North");
        var11.add(var10, "Center");
		JSplitPane var25 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, var4, var11);
		var25.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		var25.setResizeWeight(0.4);
		var25.setDividerLocation(0.4);
		this.add(var25, "Center");
        JButton var22 = new JButton("Close");
        var22.addActionListener((var1x) -> this.dispose());
        JPanel var23 = new JPanel();
        var23.add(var22);
        this.add(var23, "South");
		this.setMinimumSize(new Dimension(850, 600));
        this.setLocationRelativeTo(var1);
		this.pack();
		this.setSize(new Dimension(900, 650));
    }
}


