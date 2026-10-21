package tetris;

public class Tetramino {
	
	Block block1, block2, block3, block4;
	// block1 is the rotation pivot
	
	String shape, orientation;
	
	// There is 7 shapes
	
	int colorAndShape;
	
	boolean isAllowedToExist = true;
	
	Tetramino(){
		
		colorAndShape = (int)(Math.random() * 7 +1); // random integer between 1 and 7 included
		
		block1 = new Block();
		block2 = new Block();
		block3 = new Block();
		block4 = new Block();
		
		block1.color = colorAndShape;
		block2.color = colorAndShape;
		block3.color = colorAndShape;
		block4.color = colorAndShape;
		
		if (colorAndShape == 1)
			setAsCube();
		if (colorAndShape == 2)
			setAsVerticalLine();
		if (colorAndShape == 3)
			setAsL();
		if (colorAndShape == 4)
			setAsInvertedL();
		if (colorAndShape == 5)
			setAsZ();
		if (colorAndShape == 6)
			setAsInvertedZ();
		if (colorAndShape == 7)
			setAsT();
	}
	 
	void copy(Tetramino other) {
		this.block1 = new Block(other.block1.position, other.block1.color);
		this.block2 = new Block(other.block2.position, other.block2.color);
		this.block3 = new Block(other.block3.position, other.block3.color);
		this.block4 = new Block(other.block4.position, other.block4.color);

		this.shape = other.shape;
		this.orientation = other.orientation;
		
		this.colorAndShape = other.colorAndShape;
	}
	
	void spawn(Grid grid) {
	    Block[] blocks = { block1, block2, block3, block4 };

	    int minLine = blocks[0].position.line;
	    int minColumn = blocks[0].position.column;
	    int maxColumn = blocks[0].position.column;
	    for (Block b : blocks) {
	        minLine = Math.min(minLine, b.position.line);
	        minColumn = Math.min(minColumn, b.position.column);
	        maxColumn = Math.max(maxColumn, b.position.column);
	    }

	    int width = maxColumn - minColumn + 1;
	    int deltaLine = -minLine;
	    int deltaColumn = (grid.nbColumns - width) / 2 - minColumn;

	    for (Block b : blocks)
	        b.position.setPosition(b.position.line + deltaLine, b.position.column + deltaColumn);

	    // Maintenant que la pièce est à sa vraie place, on vérifie qu'elle est libre
	    this.shouldSpawn(grid);
	}
	
	boolean shouldSpawn(Grid grid) {
		/*
		 * Check if the space of fallingTetramino is occupied.
		 * If true, update this.allowToExist as false.
		 * the Tetramino spawn, then check allowToExist
		 * If true, continue the game
		 * else, GameOver
		 */
		if ( grid.isCellStuck(this.block1)
			|| grid.isCellStuck(this.block2)
			|| grid.isCellStuck(this.block3)
			|| grid.isCellStuck(this.block4)
				) {
			this.isAllowedToExist = false;
		}
		return this.isAllowedToExist;
	}
	
	void setAsCube() {
		this.block2.placeRightOf(this.block1);
		this.block3.placeUnderOf(this.block1);
		this.block4.placeRightOf(this.block3);
		this.shape = "cube";
		this.orientation = "default";
	}
	
	void setAsVerticalLine() {
		block2.placeOverOf(block1);
		block3.placeUnderOf(block1);
		block4.placeUnderOf(block3);
		this.shape = "line";
		this.orientation = "vertical";
	}
	
	void setAsHorizontalLine() {
		block2.placeLeftOf(block1);
		block3.placeRightOf(block1);
		block4.placeRightOf(block3);
		this.shape = "line";
		this.orientation = "horizontal";
	}
	
	void setAsZ() {
		block2.placeLeftOf(block1);
		block3.placeUnderOf(block1);
		block4.placeRightOf(block3);
		shape = "z";
		this.orientation = "default";
	}
	
	void setAsRotatedZ() {
		block2.placeOverOf(block1);
		block3.placeLeftOf(block1);
		block4.placeUnderOf(block3);
		this.shape = "z";
		this.orientation = "rotated";
	}
	
	void setAsInvertedZ() {
		block2.placeRightOf(block1);
		block3.placeUnderOf(block1);
		block4.placeLeftOf(block3);
		this.shape = "iz";
		this.orientation = "default";
	}
	
	void setAsRotatedIvertedZ() {
		block2.placeOverOf(block1);
		block3.placeRightOf(block1);
		block4.placeUnderOf(block3);
		this.shape = "iz";
		this.orientation = "rotated";
	}
	
	void setAsL() {
		block2.placeOverOf(block1);
		block3.placeUnderOf(block1);
		block4.placeRightOf(block3);
		this.shape = "l";
		this.orientation = "default";
	}
	
	void setAsRightRotatedL() {
		block2.placeRightOf(block1);
		block3.placeLeftOf(block1);
		block4.placeUnderOf(block3);
		this.shape = "l";
		this.orientation = "rightRotated";
	}
	
	void setAsReversedL() {
		block2.placeUnderOf(block1);
		block3.placeOverOf(block1);
		block4.placeLeftOf(block3);
		this.shape = "l";
		this.orientation = "reversed";
	}
	
	void setAsLeftRotatedL() {
		block2.placeLeftOf(block1);
		block3.placeRightOf(block1);
		block4.placeOverOf(block3);
		this.shape = "l";
		this.orientation = "leftRotated";
	}
	
	void setAsInvertedL() {
		block2.placeOverOf(block1);
		block3.placeUnderOf(block1);
		block4.placeLeftOf(block3);
		this.shape = "il";
		this.orientation = "default";
	}
	
	void setAsRightRotatedInvertedL() {
		block2.placeLeftOf(block1);
		block3.placeRightOf(block1);
		block4.placeUnderOf(block3);
		this.shape = "il";
		this.orientation = "rightRotated";
	}
	
	void setAsReversedInvertedL() {
		block2.placeUnderOf(block1);
		block3.placeOverOf(block1);
		block4.placeRightOf(block3);
		this.shape = "il";
		this.orientation = "reversed";
	}
	
	void setAsLeftRotatedInvertedL() {
		block2.placeRightOf(block1);
		block3.placeLeftOf(block1);
		block4.placeOverOf(block3);
		this.shape = "il";
		this.orientation = "leftRotated";
	}
	
	void setAsT() {
		block2.placeLeftOf(block1);
		block3.placeUnderOf(block1);
		block4.placeRightOf(block1);
		this.shape = "t";
		this.orientation = "default";
	}
	
	void setAsRightRotatedT() {
		block2.placeOverOf(block1);
		block3.placeLeftOf(block1);
		block4.placeUnderOf(block1);
		this.shape = "t";
		this.orientation = "rightRotated";
	}
	
	void setAsReversedT() {
		block2.placeLeftOf(block1);
		block3.placeOverOf(block1);
		block4.placeRightOf(block1);
		this.shape = "t";
		this.orientation = "reversed";
	}
	
	void setAsLeftRotatedT() {
		block2.placeOverOf(block1);
		block3.placeRightOf(block1);
		block4.placeUnderOf(block1);
		this.shape = "t";
		this.orientation = "leftRotated";
	}
	
	boolean canMoveLeft(Grid grid) {
		if (grid.isLeftCellOcupied(this.block1)
			|| grid.isLeftCellOcupied(this.block2)
			|| grid.isLeftCellOcupied(this.block3)
			|| grid.isLeftCellOcupied(this.block4))
			return false;
		return true;
	}
	
	void moveLeft() {
		this.block1.moveLeft();
		this.block2.moveLeft();
		this.block3.moveLeft();
		this.block4.moveLeft();
	}
	
	boolean canMoveRight(Grid grid) {
		if (grid.isRightCellOcupied(this.block1)
			|| grid.isRightCellOcupied(this.block2)
			|| grid.isRightCellOcupied(this.block3)
			|| grid.isRightCellOcupied(this.block4))
			return false;
		return true;
	}
	
	void moveRight() {
		this.block1.moveRight();
		this.block2.moveRight();
		this.block3.moveRight();
		this.block4.moveRight();
	}
	
	boolean canMoveDown(Grid grid) {
		if (grid.isBelowCellOcupied(this.block1)
			|| grid.isBelowCellOcupied(this.block2)
			|| grid.isBelowCellOcupied(this.block3)
			|| grid.isBelowCellOcupied(this.block4))
			return false;
		return true;
	}
	
	void moveDown() {
		this.block1.moveDown();
		this.block2.moveDown();
		this.block3.moveDown();
		this.block4.moveDown();
	}
	
	boolean canTetraminoRotate(Grid grid) {
		Tetramino rotateTest = new Tetramino();
		rotateTest.copy(this);
		rotateTest.rotateTetramino();   // ne touche que la copie

		Block[] blocks = { rotateTest.block1, rotateTest.block2, rotateTest.block3, rotateTest.block4 };
		for (Block b : blocks) {
			if (grid.isOcupied(b.position.line, b.position.column))
				return false;
		}
		return true;
	}
	
	
	
	void rotateTetramino() {
		if (this.shape == "line") {
			rotateLine();
		}
		
		if (this.shape == "z") {
			rotateZ();
		}
		
		if (this.shape == "iz") {
			rotateInvertedZ();
		}
		
		if (this.shape == "l") {
			rotateL();
		}
		
		if (this.shape == "il") {
			rotateInvertedL();
		}
		
		if (this.shape == "t")
			rotateT();
		
	}
	
	void rotateLine() {
		if (orientation == "vertical")
			setAsHorizontalLine();
		else
			setAsVerticalLine();
	}
	
	void rotateZ() {
		if (orientation == "default")
			setAsRotatedZ();
		else
			setAsZ();
	}
	
	void rotateInvertedZ() {
		if (orientation == "default")
			setAsRotatedIvertedZ();
		else
			setAsInvertedZ();
	}
	
	void rotateL() {
		if (orientation == "default")
			setAsLeftRotatedL();
		else if (orientation == "leftRotated")
			setAsReversedL();
		else if (orientation == "reversed")
			setAsRightRotatedL();
		else //if (this.orientation == "leftRotated")
			setAsL();
	}
	
	void rotateInvertedL() {
		if (orientation == "default")
			setAsLeftRotatedInvertedL();
		else if (orientation == "leftRotated")
			setAsReversedInvertedL();
		else if (orientation == "reversed")
			setAsRightRotatedInvertedL();
		else
			setAsInvertedL();
	}
	
	void rotateT() {
		if (orientation == "default")
			setAsRightRotatedT();
		else if (orientation == "rightRotated")
			setAsReversedT();
		else if (orientation == "reversed")
			setAsLeftRotatedT();
		else
			setAsT();
	}


}
