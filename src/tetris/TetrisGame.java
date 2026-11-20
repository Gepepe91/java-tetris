package tetris;

public class TetrisGame {
	
	// One grid for the game, one for viewing nextTetramino, one for storing a Tetramino
	Grid gameGrid;
	
	Tetramino fallingTetramino; // player
	Tetramino nextTetramino;    // upcoming Tetramino
	Tetramino storedTetramino;
	Tetramino ghostTetramino;   // projection on the ground, also useful for hardfall
	
	int score = 0;
	
	boolean isGameOver = false;
	
	String status = "Tetris!";
	
	TetrisGame(){
	
		gameGrid = new Grid(20,10);
		
		fallingTetramino = new Tetramino();
		fallingTetramino.spawn(this.gameGrid);
		nextTetramino = new Tetramino();
		storedTetramino = new Tetramino();
		ghostTetramino = new Tetramino();
		updateGhost();
	}
	
	void update() {
		moveDown();
		killLine();
	}
	
	void updateGhost() {
		ghostTetramino.copy(fallingTetramino);
		while (ghostTetramino.canMoveDown(gameGrid))
			ghostTetramino.moveDown();
	}
	
	void landTetramino() {
		Block b1 = this.fallingTetramino.block1;
		Block b2 = this.fallingTetramino.block2;
		Block b3 = this.fallingTetramino.block3;
		Block b4 = this.fallingTetramino.block4;

		this.gameGrid.cells[b1.position.line][b1.position.column] = b1;
		this.gameGrid.cells[b2.position.line][b2.position.column] = b2;
		this.gameGrid.cells[b3.position.line][b3.position.column] = b3;
		this.gameGrid.cells[b4.position.line][b4.position.column] = b4;

		// We abandon the current fallingTetramino to the garbage collector
		this.fallingTetramino = this.nextTetramino;
		this.fallingTetramino.spawn(this.gameGrid);
		updateGhost();
		this.isGameOver();
		this.nextTetramino = new Tetramino();
	}
	
	/*
	Tetramino getNexTetramino(Tetramino previousOne , Tetramino nextOne) {
		previousOne = nextOne;
		previousOne.spawn(this.gameGrid.nbColumns);
		return previousOne;
	}
	*/
	
	void moveLeft() {
		System.out.println("moveLeft");
		if (this.fallingTetramino.canMoveLeft(this.gameGrid))
			this.fallingTetramino.moveLeft();
		updateGhost();
	}

	void moveRight() {
		System.out.println("moveRight");
		if (this.fallingTetramino.canMoveRight(this.gameGrid))
			this.fallingTetramino.moveRight();
		updateGhost();
	}
	
	void moveDown() {
		//System.out.println("moveDown");
		if (this.fallingTetramino.canMoveDown(this.gameGrid))
			this.fallingTetramino.moveDown();
		else
			this.landTetramino();
		//updateGhost();
	}
	
	void rotate() {
		if (fallingTetramino.canTetraminoRotate(this.gameGrid)) {
			System.out.println("rotate");
			this.fallingTetramino.rotateTetramino();
		}
		else
			System.out.println("Can not rotate!");
		updateGhost();
	}
	
	void exchange() {
		System.out.println("Exchange");
		Tetramino temp = new Tetramino();
		temp.copy(fallingTetramino);
		fallingTetramino.copy(storedTetramino);
		storedTetramino.copy(temp);
		fallingTetramino.spawn(gameGrid);
	}
	
	void hardDrop() {
		System.out.println("Hard drop");
		fallingTetramino.copy(ghostTetramino);
		landTetramino();
		
	}
	
	void killLine() {
		for (int indexLine = 0; indexLine < this.gameGrid.nbLines; indexLine++) {
			if (this.gameGrid.isLineFull(indexLine)) {
				this.gameGrid.dropUpperGrid(indexLine);
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
			status = "Game Over!";
		}
	}
	
	
}