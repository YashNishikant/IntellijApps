import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Runner {

     HashMap<City, HashSet<Edge>> cityMap;
     HashSet<City> cities;
     HashSet<Edge> edges;
     City start;
     City end;

    public Runner(){
        cities = new HashSet<City>();
        ArrayList<String> cityList;
        edges = new HashSet<Edge>();
        cityMap = new HashMap<City, HashSet<Edge>>();
        File file = new File("C:\\github\\IntellijJavaApps\\cityGraph\\src\\City Distances.txt");

        try{
            BufferedReader input = new BufferedReader();
        }
        catch(IOException e){
            System.out.print(e);
        }

    }

    public static void main(String[] args) {
        new Runner();
    }
}