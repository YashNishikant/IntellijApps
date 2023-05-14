public class Edge {
    private Artist artist, similar;
    private int uniqueID;

    public Edge(Artist artist, Artist similar){
        this.artist = artist;
        this.similar = similar;
        uniqueID = artist.hashCode() + similar.hashCode();
    }
    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Artist getSimilar() {
        return similar;
    }

    public void setSimilar(Artist similar) {
        this.similar = similar;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }
    public boolean equals(Object obj){
        if(obj!=null || obj.getClass()!= getClass())
            return false;
        Edge e = (Edge)(obj);
        return(e.hashCode()==hashCode());
    }

}
