
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicPlayer {
	public static void play(String path,boolean loop)	{
		try{
			File file = new File(path);
			if(file.exists()){
				AudioInputStream audio = AudioSystem.getAudioInputStream(file);
				Clip clip = AudioSystem.getClip();
				clip.open(audio);
				clip.start();
				if(loop){
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
			}else{
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
