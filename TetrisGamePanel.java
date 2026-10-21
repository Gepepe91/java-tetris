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
	
	Color getColor(int color) {
		if (color == 1)
			return Color.darkGray;
		else if (color == 2)
			return Color.YELLOW;
		else if (color == 3)
			return Color.RED;
		else if (color == 4)
			return Color.MAGENTA;
		else if (color == 5)
			return Color.GREEN;
		else if (color == 6)
			return Color.BLUE;
		else if (color == 7)
			return Color.ORANGE;
		return Color.WHITE;   // couleur par défaut
	}

	int borderSize = 30;
	
	
	
	
	
	
	
	

	TetrisGamePanel() {
		setFocusable(true);

		// Écoute des touches : on modifie le modèle, puis on redessine
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (tetrisGame.isGameOver) {
					return;
				}
				
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

		final Timer timer = new Timer();

		// dans le constructeur, à la place de l'ancien new Timer().scheduleAtFixedRate(...)
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				tetrisGame.update();
				if (tetrisGame.isGameOver) {
					System.out.println("Game Over!");
					timer.cancel();   // la boucle de jeu s'arrête, plus aucun message
				}
				repaint();
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
				Color color = Color.LIGHT_GRAY;   // couleur d'une case vide
				if (block != null) {
					color = getColor(block.color); // Adapter avec le vrai objet Color
				}
				drawCell(g, line, column, cellSize, color);
			}
		}
		// 2. La pièce qui tombe, dessinée par-dessus
		Position cell1 = tetrisGame.fallingTetramino.block1.position;
		Position cell2 = tetrisGame.fallingTetramino.block2.position;
		Position cell3 = tetrisGame.fallingTetramino.block3.position;
		Position cell4 = tetrisGame.fallingTetramino.block4.position;
		Color color = getColor(tetrisGame.fallingTetramino.colorAndShape);
		
		drawCell(g,cell1.line, cell1.column, cellSize, color);
		drawCell(g,cell2.line, cell2.column, cellSize, color);
		drawCell(g,cell3.line, cell3.column, cellSize, color);
		drawCell(g,cell4.line, cell4.column, cellSize, color);

		// 3. Le score
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString("Score : " + tetrisGame.score, borderSize, borderSize - 10);
		
		/*
		if (tetrisGame.isGameOver) {
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, 28));
			g.drawString("GAME OVER", borderSize, getHeight() / 2);
		}
		*/
	}
	
	
	
	
	
	
	
	

	// Dessine une case de la grille avec la couleur demandée
	void drawCell(Graphics g, int line, int column, int cellSize, Color color) {
		int cellX = column * cellSize + borderSize;
		int cellY = line * cellSize + borderSize;

		g.setColor(color);
		g.fillRect(cellX, cellY, cellSize - 1, cellSize - 1);
	}
}