import com.google.gson.Gson;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static void main(String[] args) throws IOException{
        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.deezer.com/playlist/908622995")
                .build();

        try (Response response = client.newCall(request).execute()) {
            Playlist playlist = new Gson().fromJson(response.body().string(), Playlist.class);
            Tracks tracks = playlist.getTracks();
            ArrayList<Song> data = tracks.getData();

            //The random pick
            Collections.shuffle(data);

            for (int i = 0; i < playlist.getNb_tracks(); i++) {
                atomicBoolean.set(true);
                final Song song = data.get(i);
                final String artist = song.getArtist().getName();
                final String title = song.getTitle();
                System.out.println(title);
                System.out.println(artist);

                Thread a = new Thread("Play"){
                    @Override
                    public void run() {
                        JLayerPlayer jPlayer = new JLayerPlayer();
                        jPlayer.play(song.getPreview());
                        atomicBoolean.set(false);
                    }
                };

                Thread b = new Thread("Guess"){
                    @Override
                    public void run() {
                        String real_answer = title + " " + artist;
                        System.out.println("Guess the name of the artist and the name of the song, press Enter to confirm:");
                        while (true) {
                            if(atomicBoolean.get() == false) {
                                System.out.println("Time out! You lost! Better chance in the next song!");
                                break;
                            } else {
                                //Last Problem: cannot stop the scanner when there is no input.
                                Scanner sc = new Scanner(System.in);
                                String answer = sc.nextLine();
                                int ratio = FuzzySearch.tokenSortRatio(real_answer, answer);
                                if (ratio == 100) {
                                    System.out.println("Congratulations! You have found the answer!");
                                    break;
                                } else {
                                    System.out.println("Try again! Your current accuracy is " + ratio + "%:");
                                }
                            }
                        }
                    }
                };

                a.start();
                b.start();

                try {a.join();} catch (Exception e){ e.printStackTrace();}
                try {b.join();} catch (Exception e){ e.printStackTrace();}

            }
        }
    }


}
