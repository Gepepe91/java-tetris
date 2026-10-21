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
		return Color.WHITE;
	}

	int borderSize = 30;
	
	
	
	
	
	
	
	

	TetrisGamePanel() {
		setFocusable(true);

		// listening the keyboard
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
				} else if (e.getKeyCode() == KeyEvent.VK_E){
					tetrisGame.exchange();
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE){
					tetrisGame.hardDrop();
				} 
				
				repaint();
			}
		});

		final Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				tetrisGame.update();
				if (tetrisGame.isGameOver) {
					System.out.println("Game Over!");
					timer.cancel();
				}
				repaint();
			}
		}, 1000, 500);
	}
	
	
	
	
	
	
	

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Taille d'une case carrée : la plus petite des deux possibilités
		//int cellWidth = (getWidth() - 2 * borderSize) / tetrisGame.gameGrid.nbColumns;
		int cellWidth = (getWidth() - 2 * borderSize) / (tetrisGame.gameGrid.nbColumns + 5);
		int cellHeight = (getHeight() - 2 * borderSize) / tetrisGame.gameGrid.nbLines;
		int cellSize = Math.min(cellWidth, cellHeight);

		// 1. La grille : cases vides et blocs déjà posés
		for (int line = 0; line < tetrisGame.gameGrid.nbLines; line++) {
			for (int column = 0; column < tetrisGame.gameGrid.nbColumns; column++) {
				Block block = tetrisGame.gameGrid.cells[line][column];
				Color color = Color.LIGHT_GRAY;   // couleur d'une case vide
				if (block != null) {
					color = getColor(block.color); // Adapter avec le vrai objet Color
				}
				drawCell(g, line, column, cellSize, color);
			}
		}
		
		// 2a. Le fantôme, sous la pièce qui tombe
		Color ghostColor = getColor(tetrisGame.ghostTetramino.colorAndShape);
		g.setColor(ghostColor);
		Block[] ghostBlocks = {
			tetrisGame.ghostTetramino.block1, tetrisGame.ghostTetramino.block2,
			tetrisGame.ghostTetramino.block3, tetrisGame.ghostTetramino.block4
		};
		for (Block b : ghostBlocks) {
			int x = b.position.column * cellSize + borderSize;
			int y = b.position.line * cellSize + borderSize;
			g.drawRect(x, y, cellSize - 2, cellSize - 2);
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
		
		// Colonne de droite : à droite de la grille de jeu
		int sideX = borderSize + tetrisGame.gameGrid.nbColumns * cellSize + borderSize;
		int nextBoxY = borderSize + 20;
		int storedBoxY = nextBoxY + 4 * cellSize + 50;

		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 15));

		g.drawString("Suivante", sideX, nextBoxY - 6);
		drawTetraminoInBox(g, tetrisGame.nextTetramino, sideX, nextBoxY, cellSize);

		g.drawString("Stockée", sideX, storedBoxY - 6);
		drawTetraminoInBox(g, tetrisGame.storedTetramino, sideX, storedBoxY, cellSize);

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
	
	
	
	
	// Dessine un tetramino dans une boîte de 4x4 cases dont le coin haut-gauche est (boxX, boxY)
	void drawTetraminoInBox(Graphics g, Tetramino t, int boxX, int boxY, int cellSize) {
		// Fond de la boîte
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(boxX, boxY, 4 * cellSize, 4 * cellSize);

		if (t == null)   // rien de stocké pour l'instant
			return;

		Block[] blocks = { t.block1, t.block2, t.block3, t.block4 };

		int minLine = blocks[0].position.line;
		int maxLine = minLine;
		int minColumn = blocks[0].position.column;
		int maxColumn = minColumn;
		for (Block b : blocks) {
			minLine = Math.min(minLine, b.position.line);
			maxLine = Math.max(maxLine, b.position.line);
			minColumn = Math.min(minColumn, b.position.column);
			maxColumn = Math.max(maxColumn, b.position.column);
		}

		// Pour centrer la pièce dans la boîte
		int widthInCells = maxColumn - minColumn + 1;
		int heightInCells = maxLine - minLine + 1;
		int offsetX = (4 - widthInCells) * cellSize / 2;
		int offsetY = (4 - heightInCells) * cellSize / 2;

		g.setColor(getColor(t.colorAndShape));
		for (Block b : blocks) {
			int x = boxX + offsetX + (b.position.column - minColumn) * cellSize;
			int y = boxY + offsetY + (b.position.line - minLine) * cellSize;
			g.fillRect(x, y, cellSize - 1, cellSize - 1);
		}
	}
	
	
	
	
}