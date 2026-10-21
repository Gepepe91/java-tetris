package tetris;

public class Block {
	
	Position position;

	int color;
	
	Block() {
		this.position = new Position(0,0);
		this.color = 1;
	}

	Block(Position position, int color) {
		this.position = new Position(position.line, position.column);
		this.color = color;
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
