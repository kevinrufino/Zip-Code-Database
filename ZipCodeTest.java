import java.util.ArrayList;
import java.util.*;
import java.io.*;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Tests all methods of ZipCodeDatabase to check for errors. Goes through multiple
 * situations where users and make an error to see test classes scope.
 *
 * @author (Kevin Rufino)
 * @version (7/23/2019)
 */
public class ZipCodeTest{
    public static void main(){
        System.out.println("Testing started");

        // load database
        ZipCodeDatabase db = new ZipCodeDatabase();
        db.readZipCodeData("zipcodes.txt");

        // did file load?
        assert (db.getCount() > 29000) : "Should be over 29,000 items";

        // check search for 49401
        ZipCode zip = db.findZip(49401);
        assert (zip.getCity().contains("ALLENDALE")) : "Expected Allendale";

        // check search for Allendale
        ArrayList <ZipCode> list = db.search("allendale");
        assert (list.size() == 4) : "Expected 4 cities called Allendale";

        list = db.search("aLlEnDaLe");
        assert (list.size() == 4) : "Expected 4 cities called Allendale";

        list = db.search("4");
        assert (list.size() == 0) : "Expected 0 cities  ";

        // checks math for distance
        int d1 = db.distance(10453, 49507);
        assert (d1 > 618 && d1 < 622) : "Should be about 620";

        int d2 = db.distance(104530, 4950708);
        assert (d2 == -1) : "Should be -1";

        // checks for correct list within radius
        list = db.withinRadius(10453, 3);
        assert (list.size() == 29) : "Expected 29 zip codes";

        // checks for furthest zip
        zip = db.findFurthest(49507);
        assert (zip.getZip() == 96769) : "Expected Makaweli";

        System.out.println("Testing ended");
    }

}
