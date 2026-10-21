package tetris;

public class Block {
	
	Position position;

	int colorCode;   // 1 = rouge, 2 = bleu, etc. (purement esthétique)
	
	Block() {
		this.position = new Position(0,0);
		this.colorCode = 1;
	}

	Block(Position position, int colorCode) {
		this.position = new Position(position.line, position.column);
		this.colorCode = colorCode;
	}
	
	void placeRightOf(Block other) {
		this.position.line = other.position.line;
		this.position.column = other.position.column + 1;
	}
	
	void placeLeftOf(Block other) {
		this.position.line = other.position.line;
		this.position.column = other.position.column - 1;
	}
	
	void placeUnderOf(Block other) {
		this.position.line = other.position.line + 1;
		this.position.column = other.position.column;
	}
	
	void placeOverOf(Block other) {
		this.position.line = other.position.line - 1;
		this.position.column = other.position.column;
	}
	
	void moveLeft() {
		this.position.setPosition(this.position.line, this.position.column - 1);
	}
	
	void moveRight() {
		this.position.setPosition(this.position.line, this.position.column + 1);
	}
	
	void moveDown() {
		this.position.setPosition(this.position.line + 1, this.position.column);
	}
	
	
	
	
}
