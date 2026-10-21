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
		this.fallingTetramino.spawn(this.grid);
		this.nextTetramino = new Tetramino();
		this.storedTetramino = new Tetramino();
		this.ghostTetramino = new Tetramino();
	}
	
	void update() {
		//isGameOver();
		moveDown();
		killLine();
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

		// We abandon the current fallingTetramino to the garbage collector
		this.fallingTetramino = this.nextTetramino;
		this.fallingTetramino.spawn(this.grid);
		this.isGameOver();
		this.nextTetramino = new Tetramino();
	}
	
	/*
	Tetramino getNexTetramino(Tetramino previousOne , Tetramino nextOne) {
		previousOne = nextOne;
		previousOne.spawn(this.grid.nbColumns);
		return previousOne;
	}
	*/
	
	void moveLeft() {
		System.out.println("moveLeft");
		if (this.fallingTetramino.canMoveLeft(this.grid))
			this.fallingTetramino.moveLeft();
	}

	void moveRight() {
		System.out.println("moveRight");
		if (this.fallingTetramino.canMoveRight(this.grid))
			this.fallingTetramino.moveRight();
	}
	
	void moveDown() {
		//System.out.println("moveDown");
		if (this.fallingTetramino.canMoveDown(this.grid))
			this.fallingTetramino.moveDown();
		else
			this.landTetramino();
	}
	
	void rotate() {
		if (fallingTetramino.canTetraminoRotate(this.grid)) {
			System.out.println("rotate");
			this.fallingTetramino.rotateTetramino();
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
	
	void isGameOver() {
		if (!this.fallingTetramino.isAllowedToExist) {
			isGameOver = true;
		}
	}
	
	
}