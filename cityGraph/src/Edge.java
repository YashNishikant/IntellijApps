public class Edge {

    private City start, end;
    private int dist;
    private int uniqueID;

    public Edge(City start, City end){
        this.start = start;
        this.end = end;
        uniqueID = start.hashCode() + end.hashCode();
    }
    public City getDestinationCity() {
        return end;
    }

    public City getStartCity() {
        return start;
    }

    public int getDist() {
        return dist;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public String toString(){
        return start.getName() + " to " + end.getName() + ": " + dist;
    }

    public boolean equals(Object obj){
        if(obj!=null || obj.getClass()!= getClass())
            return false;
        Edge e = (Edge)(obj);
        return(e.hashCode()==hashCode());
    }

}
