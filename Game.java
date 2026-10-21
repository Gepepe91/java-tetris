package tetris;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Game {
	public static void main(String[] args) {

		System.out.println("This is Tetris!");

		JFrame frame = new JFrame("Tetris");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.add(new TetrisGamePanel());
		frame.setVisible(true);
	}
}