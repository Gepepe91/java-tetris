package tetris;

public class TetrisGame {
	
	Grid grid;
	
	Tetramino fallingTetramino; // player
	Tetramino nextTetramino;    // upcoming Tetramino
	Tetramino storedTetramino;
	Tetramino ghostTetramino;   // projection on the ground, also useful for hardfall
	
	int score = 0;
	
	boolean isGameOver = false;
	
	TetrisGame(){
	
		this.grid = new Grid();
		
		this.fallingTetramino = new Tetramino();
		this.fallingTetramino.spawn(this.grid.nbColumns);
		this.nextTetramino = new Tetramino();
		this.storedTetramino = new Tetramino();
		this.ghostTetramino = new Tetramino();
	}
	
	void update() {
		//isGameOver();
		moveDown();
		killLine();
	}
	
	boolean canMoveLeft() {
		if (this.grid.isLeftCellOcupied(this.fallingTetramino.block1)
			|| this.grid.isLeftCellOcupied(this.fallingTetramino.block2)
			|| this.grid.isLeftCellOcupied(this.fallingTetramino.block3)
			|| this.grid.isLeftCellOcupied(this.fallingTetramino.block4))
			return false;
		return true;
	}
	
	boolean canMoveRight() {
		if (this.grid.isRightCellOcupied(this.fallingTetramino.block1)
			|| this.grid.isRightCellOcupied(this.fallingTetramino.block2)
			|| this.grid.isRightCellOcupied(this.fallingTetramino.block3)
			|| this.grid.isRightCellOcupied(this.fallingTetramino.block4))
			return false;
		return true;
	}
	
	boolean canMoveDown() {
		if (this.grid.isBelowCellOcupied(this.fallingTetramino.block1)
			|| this.grid.isBelowCellOcupied(this.fallingTetramino.block2)
			|| this.grid.isBelowCellOcupied(this.fallingTetramino.block3)
			|| this.grid.isBelowCellOcupied(this.fallingTetramino.block4))
			return false;
		return true;
	}
	
	void landTetramino() {
		Block b1 = this.fallingTetramino.block1;
		Block b2 = this.fallingTetramino.block2;
		Block b3 = this.fallingTetramino.block3;
		Block b4 = this.fallingTetramino.block4;

		this.grid.cells[b1.position.line][b1.position.column] = b1;
		this.grid.cells[b2.position.line][b2.position.column] = b2;
		this.grid.cells[b3.position.line][b3.position.column] = b3;
		this.grid.cells[b4.position.line][b4.position.column] = b4;

		this.fallingTetramino = this.nextTetramino;
		this.fallingTetramino.spawn(this.grid.nbColumns);   
		this.nextTetramino = new Tetramino();
	}
	
	boolean canRotate() {
		Tetramino rotateTest = new Tetramino();
		rotateTest.copy(this.fallingTetramino);
		rotateTest.rotate();   // ne touche que la copie

		Block[] blocks = { rotateTest.block1, rotateTest.block2, rotateTest.block3, rotateTest.block4 };
		for (Block b : blocks) {
			if (this.grid.isOcupied(b.position.line, b.position.column))
				return false;
		}
		return true;
	}
	
	void shouldSpawn(Tetramino tetramino) {
		/*
		 * Check if the space of fallingTetramino is occupied.
		 * If true, update fallingTetramino.allowToExist as false.
		 * the fallingTetramino spawn, then check allowToExist
		 * If true, continue the game
		 * else, GameOver
		 */
		if ( this.grid.isCellStuck(tetramino.block1)
			|| this.grid.isCellStuck(tetramino.block2)
			|| this.grid.isCellStuck(tetramino.block3)
			|| this.grid.isCellStuck(tetramino.block4)
				)
			tetramino.isAllowedToExist = false;
	}
	
	void isGameOver() {
		if (!this.fallingTetramino.isAllowedToExist) {
			isGameOver = true;
			System.out.println("Game Over!");
		}
	}
	
	void moveLeft() {
		System.out.println("moveLeft");
		if (this.canMoveLeft())
			this.fallingTetramino.moveLeft();
	}

	void moveRight() {
		System.out.println("moveRight");
		if (this.canMoveRight())
			this.fallingTetramino.moveRight();
	}
	
	void moveDown() {
		//System.out.println("moveDown");
		if (this.canMoveDown())
			this.fallingTetramino.moveDown();
		else
			this.landTetramino();
	}
	
	void rotate() {
		if (this.canRotate()) {
			System.out.println("rotate");
			this.fallingTetramino.rotate();
		}
		else
			System.out.println("Can not rotate!");
	}
	
	void killLine() {
		for (int indexLine = 0; indexLine < this.grid.nbLines; indexLine++) {
			if (this.grid.isLineFull(indexLine)) {
				this.grid.dropUpperGrid(indexLine);
				this.score += 10;   // ou ce que tu veux : un seul incrément par ligne
				// La ligne indexLine contient maintenant ce qui était au-dessus :
				// elle peut être pleine à son tour, il faut la retester
				indexLine--;
			}
		}
	}
	
	
}