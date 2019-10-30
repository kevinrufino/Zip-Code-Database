
/**
 * Defines and finds all information in a zipcode.
 * 
 * @author (Kevin Rufino)
 * @version 7/23/2019
 * CIS 162 SS2019
 */
public class ZipCode{
    // instance variables - replace the example below with your own
    private int zip;
    private String city;
    private String state;
    private double lat;
    private double lon;

    public ZipCode(int pZip){
        // initialise instance variables
        zip = pZip;
        city = "UNKNOWN";
        state = "ST";
        lat = 0.0;
        lon = 0.0;
    }
    
    public ZipCode(int pZip, String pCity, String pState, double pLat, double pLon){
        zip = pZip;
        city = pCity;
        state = pState;
        lat = pLat;
        lon = pLon;
    }
    
    public void setZip(int pZip){
        zip = pZip;
    }
    
    public void setCity(String pCity){
        city = pCity;
    }
    
    public void setState(String pState){
        state = pState;
    }
    
    public void setLatitude(double pLat){
        lat = pLat;
    }
    
    public void setLongitude(double pLon){
        lon = pLon;
    }
    
    public int getZip(){
        return zip;
    }
    
    public String getCity(){
        return city;
    }
    
    public String getState(){
        return state;
    }
    
    public double getLatitude(){
        return lat;
    }
    
    public double getLongitude(){
        return lon;
    }
    
    public String toString(){
        String a = city.toUpperCase() + ", " + state.toUpperCase() + " " + zip;
        return a;
    }
}
