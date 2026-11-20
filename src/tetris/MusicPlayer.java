package tetris;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicPlayer {

	private Clip clip;

	// Charge un fichier audio depuis le classpath (donc aussi depuis le JAR) et le joue en boucle
	void playLoop(String resourcePath) {
		try (InputStream raw = MusicPlayer.class.getResourceAsStream(resourcePath)) {
			if (raw == null) {
				System.out.println("Musique introuvable : " + resourcePath);
				return;
			}
			AudioInputStream audio = AudioSystem.getAudioInputStream(new BufferedInputStream(raw));
			clip = AudioSystem.getClip();
			clip.open(audio);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			// Un problème audio ne doit jamais empêcher de jouer
			e.printStackTrace();
		}
	}

	void stop() {
		if (clip != null) {
			clip.stop();
			clip.close();
		}
	}
}