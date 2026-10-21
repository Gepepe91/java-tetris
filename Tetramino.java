package tetris;

public class Tetramino {
	
	Block block1, block2, block3, block4;
	
	String shape, orientation;
	
	int color;
	
	int idColor, idShape;
	
	boolean isAllowedToExist = true;
	
	Tetramino(){
		
		this.idColor = (int) (Math.random() * 5 + 1);
		this.idShape = (int) (Math.random() * 6 + 1);
		
		this.color = this.idColor;
		
		this.block1 = new Block();
		this.block2 = new Block();
		this.block3 = new Block();
		this.block4 = new Block();
		
		this.block1.colorCode = this.color;
		this.block2.colorCode = this.color;
		this.block3.colorCode = this.color;
		this.block4.colorCode = this.color;
		
		if (this.idShape == 1)
			this.setAsCube();
		if (this.idShape == 2)
			this.setAsHorizontalLine();
		if (this.idShape == 3)
			this.setAsL();
		if (this.idShape == 4)
			this.setAsInvertedL();
		if (this.idShape == 5)
			this.setAsZ();
		if (this.idShape == 6)
			this.setAsInvertedZ();
	}
	 
	void copy(Tetramino other) {
		this.block1 = new Block(other.block1.position, other.block1.colorCode);
		this.block2 = new Block(other.block2.position, other.block2.colorCode);
		this.block3 = new Block(other.block3.position, other.block3.colorCode);
		this.block4 = new Block(other.block4.position, other.block4.colorCode);

		this.shape = other.shape;
		this.orientation = other.orientation;

		this.idColor = other.idColor;
		this.idShape = other.idShape;
		this.color = other.color;
	}
	
	void spawn(int nbColumns) {
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
		int deltaLine = -minLine;                              // le haut de la pièce sur la ligne 0
		int deltaColumn = (nbColumns - width) / 2 - minColumn; // pièce centrée

		for (Block b : blocks)
			b.position.setPosition(b.position.line + deltaLine, b.position.column + deltaColumn);
	}
	
	void shouldExist() {
		
	}
	
	void setAsCube() {
		this.block2.placeRightOf(this.block1);
		this.block3.placeUnderOf(this.block1);
		this.block4.placeRightOf(this.block3);
		this.shape = "cube";
		this.orientation = "default";
	}
	
	void setAsVerticalLine() {
		this.block2.placeUnderOf(this.block1);
		this.block3.placeUnderOf(this.block2);
		this.block4.placeUnderOf(this.block3);
		this.shape = "line";
		this.orientation = "vertical";
	}
	
	void setAsHorizontalLine() {
		this.block2.placeRightOf(this.block1);
		this.block3.placeRightOf(this.block2);
		this.block4.placeRightOf(this.block3);
		this.shape = "line";
		this.orientation = "horizontal";
	}
	
	void setAsZ() {
		this.block2.placeRightOf(this.block1);
		this.block3.placeUnderOf(this.block2);
		this.block4.placeRightOf(this.block3);
		this.shape = "z";
		this.orientation = "default";
	}
	
	void setAsRotatedZ() {
		this.block2.placeOverOf(this.block1);
		this.block3.placeRightOf(this.block2);
		this.block4.placeOverOf(this.block3);
		this.shape = "z";
		this.orientation = "rotated";
	}
	
	void setAsInvertedZ() {
		this.block2.placeRightOf(this.block1);
		this.block3.placeOverOf(this.block2);
		this.block4.placeRightOf(this.block3);
		this.shape = "iz";
		this.orientation = "default";
	}
	
	void setAsRotatedIvertedZ() {
		this.block2.placeUnderOf(this.block1);
		this.block3.placeRightOf(this.block2);
		this.block4.placeUnderOf(this.block3);
		this.shape = "iz";
		this.orientation = "rotated";
	}
	
	void setAsL() {
		this.block2.placeUnderOf(this.block1);
		this.block3.placeUnderOf(this.block2);
		this.block4.placeRightOf(this.block3);
		this.shape = "l";
		this.orientation = "default";
		
	}
	
	void setAsRightRotatedL() {
		this.block2.placeRightOf(this.block1);
		this.block3.placeRightOf(this.block2);
		this.block4.placeOverOf(this.block3);
		this.shape = "l";
		this.orientation = "rightRotated";
	}
	
	void setAsReversedL() {
		this.block2.placeRightOf(this.block1);
		this.block3.placeUnderOf(this.block2);
		this.block4.placeUnderOf(this.block3);
		this.shape = "l";
		this.orientation = "reversed";
	}
	
	void setAsLeftRotatedL() {
		this.block2.placeOverOf(this.block1);
		this.block3.placeRightOf(this.block2);
		this.block4.placeRightOf(this.block3);
		this.shape = "l";
		this.orientation = "leftRotated";
	}
	
	void setAsInvertedL() {
		this.block2.placeUnderOf(this.block1);
		this.block3.placeUnderOf(this.block2);
		this.block4.placeLeftOf(this.block3);
		this.shape = "il";
		this.orientation = "default";
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
			if (this.orientation == "vertical")
				this.setAsHorizontalLine();
			else
				this.setAsVerticalLine();
		}
		
		if (this.shape == "z") {
			if (this.orientation == "default")
				this.setAsRotatedZ();
			else
				this.setAsZ();
		}
		
		if (this.shape == "iz") {
			if (this.orientation == "default")
				this.setAsRotatedIvertedZ();
			else
				this.setAsInvertedZ();
		}
		
		if (this.shape == "l") {
			if (this.orientation == "default")
				this.setAsRightRotatedL();
			else if (this.orientation == "rightRotated")
				this.setAsReversedL();
			else if (this.orientation == "reversed")
				this.setAsLeftRotatedL();
			else //if (this.orientation == "leftRotated")
				this.setAsL();
		}
		
		// Same with invertedL
		
		
		
	}


}
