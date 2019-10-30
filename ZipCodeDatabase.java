import java.util.ArrayList;
import java.util.*;
import java.io.*;
/**
 * Read a big collection of zip code entries. User can
 * make various queries.
 * 
 * @author (Kevin Rufino)
 * @version 7/16/2019
 * CIS 162 SS2019
 */
public class ZipCodeDatabase{
    
    /** a collection of zip code entries */
    private ArrayList <ZipCode> list;

    /*****************************************************
    Constructor for objects of class ZipCodeDatabase
    This is the default constructor
     ******************************************************/
    public ZipCodeDatabase(){
        list =  new ArrayList <ZipCode> ();
    }

    /*****************************************************
    Checks the size of list - this is an accessor method

    @return number of zip codes
     ******************************************************/
    public int getCount(){
        return list.size();
    }

    /*****************************************************
    Checks for zip code in list - this is an accessor method

    @param zip the zipcode
    @return the zipcodes location
     ******************************************************/
    public ZipCode findZip(int zip){
        ZipCode location = null;
        for (ZipCode z: list) {
            if(z.getZip() == zip)
                location = z;
        }
        return location;
    }

    /*****************************************************
    Searches for all zip codes that relate
    This is an accessor method

    @param str the string being compared
    @return the list of all zip codes that contain str
     ******************************************************/
    public ArrayList <ZipCode> search (String str){
        ArrayList <ZipCode> search = new ArrayList <ZipCode> ();
        //searches for a zip from list and adds to search if they match
        for (ZipCode z: list) {
            if (z.getCity().toLowerCase().contains(str.toLowerCase())
            || z.getState().toLowerCase().contains(str.toLowerCase()))
                search.add(z);
        }

        return search;
    }

    /*****************************************************
    Calculates distance between two zip codes
    This is an accessor method

    @param zip1, zip2 the two zip codes that will be used in calculations
    @retrun the distance if both valid
     ******************************************************/
    public int distance(int zip1, int zip2){
        final int EARTH_RADIUS = 3959;
        ZipCode z1 = findZip(zip1);
        ZipCode z2 = findZip(zip2);

        // checks if valid zips, then calculates
        if (z1 != null && z2 != null) {
            double lat1 = Math.toRadians(z1.getLatitude());
            double lat2 = Math.toRadians(z2.getLatitude());

            double lon1 = Math.toRadians(z1.getLongitude());
            double lon2 = Math.toRadians(z2.getLongitude());

            double p1 = Math.cos(lat1) * Math.cos(lon1) * Math.cos(lat2)
                * Math.cos(lon2);
            double p2 = Math.cos(lat1) * Math.sin(lon1) * Math.cos(lat2)
                * Math.sin(lon2);
            double p3 = Math.sin(lat1) * Math.sin(lat2);

            int d = (int) (Math.acos(p1+p2+p3) * EARTH_RADIUS);

            return d;
        } else {
            return -1;
        }
    }

    /*****************************************************
    Checks for all zipcodes within given radius
    This is an accessor method

    @param pZip, pRadius the zipcode and radius being checked
    @return list of zip codes within radius
     ******************************************************/
    public ArrayList <ZipCode> withinRadius (int pZip, int pRadius){
        ArrayList <ZipCode> close = new ArrayList <ZipCode> ();
        
        //adds and object to a list if the distance is <= radius
        for (ZipCode z: list){
            int zDis = distance(z.getZip(), pZip);
            if (zDis <= pRadius && zDis != 0)
                close.add(z);
        }
        return close;
    }

    /*****************************************************
    Looks for the furthest zipcode from given zip code
    This is an accessor method

    @param pZip the zipcode being checked
    @return the farthest zipcode
     ******************************************************/
    public ZipCode findFurthest (int pZip){
        int dst = 0;
        int fdst = 0;
        ZipCode fZip = null;
        
        //sets zip with largest distance to the furthest zip
        for(ZipCode z: list){
            dst = distance(z.getZip(), pZip);
            if(fdst < dst){
                fdst = dst;
                fZip = z;
            }
        }
        return fZip;
    }

    /*****************************************************
    Adds given zip code to the list
    This is a mutator method

    @param z zipcode with all information
     ******************************************************/
    public void addZipCode(ZipCode z){
        list.add(z);
    }

    /*****************************************************
    Reads zip codes from data sheet
    This is a mutator method

    @param filename the file of data
     ******************************************************/
    public void readZipCodeData(String filename){
        Scanner inFS = null;
        FileInputStream fileByteStream = null;

        // start with free arraylist
        list = new ArrayList <ZipCode>();

        try{
            // open tge File and set delimiters
            fileByteStream = new FileInputStream("zipcodes.txt");
            inFS = new Scanner(fileByteStream);
            inFS.useDelimiter("[,\r\n]+");

            // continue while there is more data to read
            while(inFS.hasNext()) {
                // read five data elements
                int zip = inFS.nextInt();
                String city = inFS.next();
                String state = inFS.next();
                double lat = inFS.nextDouble();
                double lon = inFS.nextDouble();

                ZipCode z = new ZipCode(zip, city, state, lat, lon);
                addZipCode(z);
            }
        }catch (IOException error1) {
            System.out.println("Ruh-roh raggy! There's and issue with "+filename);
        }
    }
}
