public class City {

    private String name;
    private int uniqueID;

    public City(String name){
        this.name = name;
        uniqueID = name.hashCode();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String toString(){
        return name;
    }

    public boolean equals(Object obj){
        if(obj!=null || obj.getClass()!= getClass())
            return false;
        City a = (City)(obj);
        return(a.hashCode()==hashCode());
    }

}
