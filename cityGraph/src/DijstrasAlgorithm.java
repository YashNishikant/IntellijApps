import javax.management.StandardEmitterMBean;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class DijstrasAlgorithm {

    ArrayList<City> cities;
    ArrayList<Edge> edges;
    HashSet<City> visitedCities;
    HashSet<City> unVisitedCities;
    HashMap<City, City> predecessors;
    HashMap<City, Integer> distance;

    public DijstrasAlgorithm(Graph graph){
        cities = new ArrayList<City>(graph.getCities());
        edges = new ArrayList<Edge>(graph.getEdges());
    }

    public void createTravelsPaths(City source){
        visitedCities = new HashSet<City>();
        unVisitedCities = new HashSet<City>();
        distance = new HashMap<City, Integer>();
        predecessors = new HashMap<City, City>();

        distance.put(source, 0);
        unVisitedCities.add(source);

        while(unVisitedCities.size()>0){
            City city = getMinimum(unVisitedCities);
            visitedCities.add(city);
            unVisitedCities.remove(city);
            findMinimalDistances(city);
        }
    }

    public HashMap<City, City> getPred(){
        return predecessors;
    }

    public void findMinimalDistances(City temp) {

        ArrayList<City> adjacentNodes = getNeighbors(temp);

        for(City targetCity : adjacentNodes){
            if(getShortestDistance(targetCity) > getShortestDistance(temp)){
                distance.put(targetCity, getShortestDistance(temp) + getDistance(temp, targetCity));
                predecessors.put(targetCity, temp);
                unVisitedCities.add(targetCity);
            }
        }
    }

    public int getDistance(City tempCity, City targetCity){
        for(Edge edge : edges){
            if((edge.getStartCity()==tempCity && edge.getDestinationCity() == targetCity) || (edge.getStartCity() == targetCity && edge.getDestinationCity()==tempCity)){
                return edge.getDist();
            }
        }
        throw new RuntimeException();
    }

    public ArrayList<City> getNeighbors(City tempCity) {
        ArrayList<City> neighbors = new ArrayList<City>();
        for(Edge e : edges){
            if(e.getStartCity()==tempCity && !wasVisited(e.getDestinationCity())){
                neighbors.add(e.getDestinationCity());
            }
            if(e.getDestinationCity()==tempCity && !wasVisited(e.getStartCity())){
                neighbors.add(e.getStartCity());
            }
        }
        return neighbors;
    }

    public City getMinimum (HashSet<City> cities) {
        City min = null;
        for(City city : cities){
            if(min==null){
                min=city;
            }
            else{
                if(getShortestDistance(city) < getShortestDistance(min)){
                    min=city;
                }
            }
        }
        return min;
    }

    public boolean wasVisited(City city){
        return visitedCities.contains(city);
    }

    public int getShortestDistance(City destination){
        Integer dist = distance.get(destination);
        if(dist==null){
            return Integer.MAX_VALUE;
        }
        return dist;
    }

    public ArrayList<City> getShortestPath(City target){
        ArrayList<City> connectingCities = new ArrayList<City>();
        City step = target;
        if(predecessors.get(step)==null){
            return null;
        }
        connectingCities.add(step);
        while(predecessors.get(step)!=null){
            step = predecessors.get(step);
            connectingCities.add(step);
        }
        Collections.reverse(connectingCities);
        return connectingCities;
    }

}
