package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.TimerTask;

import javax.swing.JPanel;
import java.util.Timer;

public class TetrisGamePanel extends JPanel {

	TetrisGame tetrisGame = new TetrisGame();
	
	Color getColor(int colorCode) {
		if (colorCode == 1)
			return Color.RED;
		else if (colorCode == 2)
			return Color.BLUE;
		else if (colorCode == 3)
			return Color.GREEN;
		else if (colorCode == 4)
			return Color.ORANGE;
		return Color.MAGENTA;   // couleur par défaut
	}

	int borderSize = 30;

	TetrisGamePanel() {
		setFocusable(true);

		// Écoute des touches : on modifie le modèle, puis on redessine
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					tetrisGame.moveLeft();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tetrisGame.moveRight();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					tetrisGame.moveDown();
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					tetrisGame.rotate();
;				}
				repaint();
			}
		});
		

		// Boucle de jeu : la pièce descend toutes les 500 ms
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				tetrisGame.moveDown();
				repaint();
				tetrisGame.killLine();
			}
		}, 1000, 500);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Taille d'une case carrée : la plus petite des deux possibilités
		int cellWidth = (getWidth() - 2 * borderSize) / tetrisGame.grid.nbColumns;
		int cellHeight = (getHeight() - 2 * borderSize) / tetrisGame.grid.nbLines;
		int cellSize = Math.min(cellWidth, cellHeight);

		// 1. La grille : cases vides et blocs déjà posés
		for (int line = 0; line < tetrisGame.grid.nbLines; line++) {
			for (int column = 0; column < tetrisGame.grid.nbColumns; column++) {
				Block block = tetrisGame.grid.cells[line][column];
				Color color = Color.DARK_GRAY;   // couleur d'une case vide
				if (block != null) {
					color = getColor(block.colorCode); // Adapter avec le vrai objet Color
				}
				drawCell(g, line, column, cellSize, color);
			}
		}
		// 2. La pièce qui tombe, dessinée par-dessus
		Position cell1 = tetrisGame.fallingTetramino.block1.position;
		Position cell2 = tetrisGame.fallingTetramino.block2.position;
		Position cell3 = tetrisGame.fallingTetramino.block3.position;
		Position cell4 = tetrisGame.fallingTetramino.block4.position;
		Color color = getColor(tetrisGame.fallingTetramino.color);
		
		drawCell(g,cell1.line, cell1.column, cellSize, color);
		drawCell(g,cell2.line, cell2.column, cellSize, color);
		drawCell(g,cell3.line, cell3.column, cellSize, color);
		drawCell(g,cell4.line, cell4.column, cellSize, color);

		// 3. Le score
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString("Score : " + tetrisGame.score, borderSize, borderSize - 10);
	}

	// Dessine une case de la grille avec la couleur demandée
	void drawCell(Graphics g, int line, int column, int cellSize, Color color) {
		int cellX = column * cellSize + borderSize;
		int cellY = line * cellSize + borderSize;

		g.setColor(color);
		g.fillRect(cellX, cellY, cellSize - 1, cellSize - 1);
	}
}