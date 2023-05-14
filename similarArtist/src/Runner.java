import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Runner {

    HashMap artistMap;
    Artist start, end;
    Graph graph;
    Stack currentPath;
    HashSet visited;

    public Runner(){

        artistMap = new HashMap<>();
        graph = new Graph();
        File f = new File("C:\\Users\\10016796\\IdeaProjects\\similarArtist\\src\\SimilarArtists.txt");

        try{
            BufferedReader input = new BufferedReader(new FileReader(f));
            String text;
            while((text=input.readLine())!=null){
                String[] pieces = text.split(",");
                Artist a1 = new Artist(pieces[0]);
                Artist a2 = new Artist(pieces[1]);
                graph.addArtist(a1);
                graph.addArtist(a2);
                graph.addEdge(a1, a2);
                graph.addEdge(a2, a1);

                if(!artistMap.containsKey(a1)){
                    artistMap.put(a1, new HashSet());
                }

                if(!artistMap.containsKey(a2)){
                    artistMap.put(a2, new HashSet());
                }

                System.out.println("Edges – Connecting artists with similar");

                for(Edge edge : graph.getEdges()){
                    System.out.println("\t"+edge);
                }
                for(Artist startartist : graph.getArtists()){
                    for(Artist endartist : graph.getArtists()){
                        if(startartist!=endartist){
                            Stack<Artist> currentPath = new Stack<Artist>();
                            HashSet<Artist> visited = new HashSet<Artist>();
                            dft(startartist, endartist);
                        }
                    }
                }

            }
        }catch(IOException e){

        }

    }

    public void dft(Artist currentArtist, Artist destination){
        currentPath.push(currentArtist);
        visited.add(currentArtist);

        if(currentArtist==destination){
            printCurrentPath();
        }
        else{
            for(Edge e : graph.getEdges()){
                Artist artist = e.getArtist();
                Artist similar = e.getSimilar();

                if(visited.contains(artist) && !visited.contains(similar)){
                    dft(similar, destination);
                }
                if(visited.contains(similar) && !visited.contains(artist)){
                    dft(artist, destination);
                }
            }
        }
    }

    public void printCurrentPath(){
        String output = "";

        while(!currentPath.isEmpty()){
            output = currentPath.pop() + output;

            if(!currentPath.isEmpty()){
                output = "->" + output;
            }
        }

        output = "\t" + output;
    }

    public static void main(String[] args) {

    }

}
