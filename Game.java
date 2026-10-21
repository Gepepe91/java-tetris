package tetris;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Game {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				JFrame frame = new JFrame("Tetris");
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

				TetrisGamePanel gamePanel = new TetrisGamePanel();
				GameConsole console = new GameConsole();

				frame.setLayout(new BorderLayout());
				frame.add(gamePanel, BorderLayout.CENTER);
				frame.add(console, BorderLayout.EAST);

				frame.setSize(700, 640);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

				// La redirection se fait une fois la fenêtre visible
				console.redirectSystemOut();
				System.out.println("This is Tetris!");

				gamePanel.requestFocusInWindow();
			} catch (Throwable t) {
				t.printStackTrace();
				JOptionPane.showMessageDialog(null, t.toString(), "Erreur au démarrage",
						JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}