package tetris;

public class Grid {

	int nbLines;
	int nbColumns;
	Block[][] cells;   // cells[ligne][colonne] : un Block, ou null si la case est vide
	
	Grid(){
		this.nbLines = 1;
		this.nbColumns = 1;
		this.cells = new Block[1][1];
	}

	Grid(int nbLines, int nbColumns) {
		this.nbLines = nbLines;
		this.nbColumns = nbColumns;
		this.cells = new Block[nbLines][nbColumns];   // toutes les cases valent null au départ
	}

	boolean isInside(int line, int column) {
		if (line >= 0 && line < this.nbLines && column >= 0 && column < this.nbColumns)
			return true;
		return false;
	}
	
	boolean isStuck(int line, int column) {
	    if (!this.isInside(line, column))
	        return false;
	    return this.cells[line][column] != null;
	}

	// Une case hors de la grille compte comme occupée : c'est ça qui remplace les murs
	boolean isOcupied(int line, int column) {
		if (!this.isInside(line, column))
			return true;
		//if (this.cells[line][column] != null)
		//	return true;
		//return false;
		if (this.isStuck(line, column))
			return true;
		return false;
	}
	
	boolean isCellStuck(Block block) {
		return isStuck(block.position.line, block.position.column);
	}
	
	boolean isRightCellOcupied(Block block) {
		return isOcupied(block.position.line , block.position.column + 1);
	}
	
	boolean isLeftCellOcupied(Block block) {
		return isOcupied(block.position.line , block.position.column - 1);
	}
	
	boolean isBelowCellOcupied(Block block) {
		return isOcupied(block.position.line + 1 , block.position.column);
	}
	
	boolean isLineFull(int line) {
		boolean isFull = true;
		for (int index=0; index < this.nbColumns; index++) {
			if (this.cells[line][index] == null)
				isFull = false;
		}
		return isFull;
	}
	
	// Supprime la ligne 'level' et fait descendre d'un cran tout ce qui est au-dessus
	void dropUpperGrid(int level) {
		for (int line = level; line > 0; line--) {
			for (int column = 0; column < this.nbColumns; column++) {
				this.cells[line][column] = this.cells[line - 1][column];
				if (this.cells[line][column] != null)
					this.cells[line][column].position.line = line; // garde la position cohérente
			}
		}
		// La ligne du haut devient vide
		for (int column = 0; column < this.nbColumns; column++)
			this.cells[0][column] = null;
	}
	
	
}