package Search;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import downloadLectio.GetGyms;

public class Search /*extends AppCompatActivity*/{

    public static String[] Gym(String[] args) throws MalformedURLException, IOException {
        String input = new String (Search.Input());
        TreeMap<String, String> gyms = new TreeMap<String, String> (GetGyms.<String>Map(args));
        String[] length = input.split("");
        length[0] = length[0].toUpperCase();
        
        Iterator<Entry<String, String>> keys = gyms.entrySet().iterator();
        int q = 0;
        int res = 10;
        String[] hello = new String[res];
        while (keys.hasNext()) {
        	Entry<String, String> entry = keys.next();
        	String[] compare = entry.getKey().split("");
        	int k = 0;
        	if (length.length<=compare.length) {
        		for (int i=0;i<length.length;i++) {
        			if (compare[i].equals(length[i])) {
        				k++;
        				if (k == length.length) {
        					hello[q] = entry.getKey();
        					q++;
        					if (q==res) {
        						return hello;
        						}
        					}
        				}
        			}
        		}
        	}
    while (q<res) {
    	hello[q] = null;
    	q++;
    	if (q==res) {
			return hello;
    		}
    	}
    return hello;
    }

    private static String Input() {
        //SearchView login_Search = (SearchView) findViewById(R.id.login_Search);
        //String id = login_Search.getQuery().toString();
        return "a";
    }

    public static void main(String[] args) throws MalformedURLException, IOException {
        System.out.println(Search.Gym(args)[0]);
        System.out.println(Search.Gym(args)[1]);
        System.out.println(Search.Gym(args)[2]);
        System.out.println(Search.Gym(args)[3]);
        System.out.println(Search.Gym(args)[4]);
        System.out.println(Search.Gym(args)[5]);
        System.out.println(Search.Gym(args)[6]);
        System.out.println(Search.Gym(args)[7]);
        System.out.println(Search.Gym(args)[8]);
        System.out.println(Search.Gym(args)[9]);
        
    }
}