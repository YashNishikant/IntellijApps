import java.util.HashSet;

public class Graph {

    HashSet<City> cities;
    HashSet<Edge> edges;

    public Graph(HashSet<City> cset, HashSet<Edge> eset){
        cities = cset;
        edges = eset;
    }

    public HashSet<City> getCities() {
        return cities;
    }

    public HashSet<Edge> getEdges() {
        return edges;
    }

}
