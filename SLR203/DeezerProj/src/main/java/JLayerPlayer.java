import javazoom.jl.player.Player;

import java.io.InputStream;
import java.net.URL;

public class JLayerPlayer {
    private String url;

    public JLayerPlayer(){
        this.url = null;
    }

    public void play(String url) {

        try {
            InputStream is = new URL(url).openStream();
            Player playMP3 = new Player(is);
            playMP3.play();

        } catch (Exception e){
            System.out.println(e);
        }
    }

}
