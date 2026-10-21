package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class GameConsole extends JScrollPane {

	private final JTextArea textArea = new JTextArea();

	GameConsole() {
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.GREEN);
		textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

		// Important : la console ne doit jamais voler le focus clavier au jeu
		textArea.setFocusable(false);
		this.setFocusable(false);

		this.setViewportView(textArea);
		this.setPreferredSize(new Dimension(250, 0));
	}

	// Ajoute du texte dans la console (peut être appelé depuis n'importe quel thread)
	private void append(String text) {
		SwingUtilities.invokeLater(() -> {
			textArea.append(text);

			// On garde la console légère : on supprime le début si elle devient trop longue
			int length = textArea.getDocument().getLength();
			if (length > 20000)
				textArea.replaceRange("", 0, length - 10000);

			// Défilement automatique vers le bas
			textArea.setCaretPosition(textArea.getDocument().getLength());
		});
	}

	// Envoie tout ce qui est écrit dans System.out / System.err vers cette console
	// (et toujours aussi vers le vrai terminal, s'il y en a un)
	void redirectSystemOut() {
		PrintStream terminal = System.out;

		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) {
				write(new byte[] { (byte) b }, 0, 1);
			}

			@Override
			public void write(byte[] buffer, int offset, int length) {
				terminal.write(buffer, offset, length);
				append(new String(buffer, offset, length, StandardCharsets.UTF_8));
			}
		};

		PrintStream guiStream = new PrintStream(out, true, StandardCharsets.UTF_8);
		System.setOut(guiStream);
		System.setErr(guiStream);
	}
}