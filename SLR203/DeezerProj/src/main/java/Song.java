public class Song {
    private int id;
    private String preview;
    private String title;
    private Artist artist;


    public Artist getArtist() {
        return artist;
    }

    public String getPreview() {
        return preview;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
