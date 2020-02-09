public class Playlist {
    public int id;
    private int nb_tracks;
    private Tracks tracks;

    public int getId() {
        return id;
    }

    public int getNb_tracks() {
        return nb_tracks;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNb_tracks(int nb_tracks) {
        this.nb_tracks = nb_tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
