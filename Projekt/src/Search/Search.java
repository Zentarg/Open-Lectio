package Search;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import downloadLectio.GetGyms;

public class Search /*extends AppCompatActivity*/{

    public static String Gym(String[] args) throws MalformedURLException, IOException {
        String input = new String (Search.Input());
        TreeMap<String, String> gyms = new TreeMap<String, String> (GetGyms.Map(args)); 
        
        // Returner key, hvis value findes
        Iterator<Entry<String, String>> keys = gyms.entrySet().iterator();
        while (keys.hasNext()) {
        	Entry<String, String> entry = keys.next();
        	if (entry.getValue().equals(input)) {
        		return entry.getKey();
        	}
        }
        
        // Value blev ikke fundet
        
        
        // Returner value fra key
        return gyms.get(input);
    }

    private static String Input() {
        //SearchView login_Search = (SearchView) findViewById(R.id.login_Search);
        //String id = login_Search.getQuery().toString();
        return "aa";
    }

    public static void main(String[] args) throws MalformedURLException, IOException {
        System.out.println(Search.Gym(args));

    }

}
