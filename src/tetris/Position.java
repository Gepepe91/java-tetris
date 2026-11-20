package tetris;

public class Position {
	
	int line;
	int column;
	
	Position(){
		this.line=0;
		this.column=0;
	}
	
	Position(int line, int column){
		this.line = line;
		this.column = column;
	}
	
	void setPosition(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	boolean equals(Position other) {
		if (this.line == other.line && this.column == other.column)
			return true;
		return false;
	}
	
	void print() {
		System.out.println("(" + this.line + "," + this.column + ")");
	}

}
