import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class text_checker {
	List<String> textList = new ArrayList<String>();
        List<Double> priceList = new ArrayList<Double>();
	private String text;
	private Date date = null;
	private String cost = null;
	private String element = null;

	public static void main(String[] args) {
                String str =  "xd xd 123 2.78 xd 123 xd 2.78 303 BUY ONE GET ONE FREE QUARTER POUNDER W/CHEESE OF E G MICMUFFIN Go to www.modvoi m within 7 days and tell us about your visit Validation Co Expires 30 days fter receipt date Valid at participating US McDonald's. 91-19 leen elvd Elllllurst 373 THANK YOLI TEL 718 478 123 Storet 23149 KSH 3 Ict .10 5 (Sat) 11:01 KVS Order 03 CITY ITEM TOTAL 2 2 Apple Pies 2.78 Tax 0.25 Take Cut Total 3.03 Cash Tendered 3.03 Change 0.00 McDonald's Restaurar t";
        
                List<String> test = Arrays.asList(str.split("\\s+"));
                
                /*
                for (String i : test){
                    System.out.println(i);
                }
                */                
                
                text_checker example = new text_checker (test);
                HashMap<String,String> map = new HashMap<String,String>();
                map = example.findItems(test);
                
                
                Set<Entry<String,String>> hashSet = map.entrySet();
                for (Entry entry : map.entrySet()){
                       System.out.println(entry.getKey()+ " " + entry.getValue());
                }
               
                
                
                /*
		String tempString;
		for(Iterator<String> i = example.getList().iterator(); i.hasNext(); ) {
			tempString = i.next();
		    example.checkCategory(tempString);
		}	
                */
	}

	public text_checker(List<String> input) {
		textList = input;
	}
	
	public List<String> getList () {
		return textList;
	}

	private void checkCategory(String inputText) {
			if (checkPrice(inputText)) {
					if (cost != null) {
						System.out.println(cost);
						cost = null;
					}
			} else if (checkDate(inputText)) {
				System.out.println(date);
			}
		//}
	}

	private Boolean checkDate(String text) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Pattern slashFormat = Pattern.compile("\\d\\d/\\d\\d/\\d\\d\\d\\d");
		Pattern underscoreFormat = Pattern
				.compile("\\d\\d_\\d\\d_\\d\\d\\d\\d");
		Pattern dashFormat = Pattern.compile("\\d\\d-\\d\\d-\\d\\d\\d\\d");
		Matcher slashMatcher = slashFormat.matcher(text);
		Matcher underscoreMatcher = underscoreFormat.matcher(text);
		Matcher dashMatcher = dashFormat.matcher(text);
		if (slashMatcher.find()) {
			try {
				date = sdf.parse(slashMatcher.group());
				return true;
			} catch (ParseException e) {
				System.out.println("failed");
			}
		} else if (underscoreMatcher.find()) {
			try {
				sdf.applyPattern("dd_MM_yyyy");
				date = sdf.parse(underscoreMatcher.group());
				return true;
			} catch (ParseException e) {
				System.out.println("failed");
			}
		} else if (dashMatcher.find()) {
			try {
				sdf.applyPattern("dd-MM-yyyy");
				date = sdf.parse(dashMatcher.group());
				return true;
			} catch (ParseException e) {
				System.out.println("failed");
			}
		}
		else {
			
			for (Locale locale : DateFormat.getAvailableLocales()) {
			    for (int style =  DateFormat.FULL; style <= DateFormat.SHORT; style ++) {
			        DateFormat df = DateFormat.getDateInstance(style, locale);
			        try {
			                date = df.parse(text);
			                return true;
			                // either return "true", or return the Date obtained Date object
			        } catch (ParseException ex) {
			            continue; // unparsable, try the next one
			        }
			    }
			}
			
			/*try {
				sdf.applyPattern("EEEE, MMMM dd, yyyy");
				date = sdf.parse(text);
				return true;
			} catch (ParseException e) {
				return false;
			} */
		}
		return false;
	}

	private Boolean checkPrice(String text) {
		//Pattern priceFormat = Pattern.compile("\\d\\p(.)\\d\\d");
		// text.length() - 4
		//Matcher priceSearch = priceFormat.matcher(text);
		int index = text.length() - 4; //old code
		
		//if (priceSearch.find())
		// ((text.charAt(index - 1) == ' ' && text.charAt(index - 2) == '$') || text.charAt(index - 1) == '$') && 
		if (text.length() >= 4 && Character.isDigit(text.charAt(index)) && Character.isDigit(text.charAt(index+2)) && Character.isDigit(text.charAt(index+3)) && text.charAt(index+1) == '.') 
		{
			for (; (index - 1) >= 0 && Character.isDigit(text.charAt(index - 1)); index--) 
			{
			}
				cost = text.substring(index, text.length());
				priceList.add(Double.parseDouble(cost));
				return true;	
		}
		else 
		{
			return false;
		}	
        }

	private Boolean checkSubtotal(String text) {
		text = text.toLowerCase();
		if (text.contains("total")) {
			// The next index will be the grand total
		}
		return false;
	}
	
        
        //Find purchased items and products
        private HashMap<String,String> findItems(List<String> text){
            HashMap<String,String> items = new HashMap<String,String>();
            for (int i = 0; i < text.size();i++){
                    if (checkPrice(text.get(i))){
                       try{
                       if (items.get(text.get(i)) == null){
                           items.put((text.get(i)),(text.get(i-2) + " " +  text.get(i-1)));
                       }
                       else{
                           items.put((text.get(i)),(text.get(i-2) + " " +  text.get(i-1) + "," + items.get(text.get(i))));
                       }
                       }catch(Exception IndexOutOfBoundsError){
                           
                       }
                       
                       
                    }
            }
            return items;
        }
}