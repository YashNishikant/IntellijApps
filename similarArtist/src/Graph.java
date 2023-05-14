import java.util.HashSet;

public class Graph {

    HashSet<Artist> artists;
    HashSet<Edge> edges;

    public Graph(){
        artists = new HashSet<Artist>();
        edges = new HashSet<Edge>();
    }

    public HashSet<Artist> getArtists() {
        return artists;
    }

    public void setArtists(HashSet<Artist> artists) {
        this.artists = artists;
    }

    public HashSet<Edge> getEdges() {
        return edges;
    }

    public void setEdges(HashSet<Edge> edges) {
        this.edges = edges;
    }

    public void addArtist(Artist a){
        artists.add(a);
    }

    public void addEdge(Artist source, Artist dest){
        edges.add(new Edge(source, dest));
    }

}
