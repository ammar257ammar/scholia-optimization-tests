package ul.unimaas.bigcat.pages;

import java.util.ArrayList;
import java.util.List;

public class Author {
	
	public static ArrayList<String> iframes(){
		
		List<String> iframes = new ArrayList<String>();
		
		iframes.add("h3[id='Number of publications per year'] + div > iframe");
		iframes.add("h3[id='Number of pages per year'] + div > iframe");
		iframes.add("h2[id='Venue statistics'] + div > iframe");
		
		return null;
	}

}
