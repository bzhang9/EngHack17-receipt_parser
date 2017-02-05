import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class text_checker {
	List<String> textList = new ArrayList<String>();
	private String text;
	private Date date = null;
	private String cost = null;
	private String element = null;

	public static void main(String[] args) {
		List<String> test = Arrays.asList("01-20-2017", "asdfklj$1.50");
		text_checker example = new text_checker (test);
		String tempString;
		for(Iterator<String> i = example.getList().iterator(); i.hasNext(); ) {
			tempString = i.next();
		    example.checkCategory(tempString);
		}		
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
		if (text.contains("$")) {
			int index = text.indexOf("$");
			text = text.substring(index + 1, text.length());
			cost = "$";
			while (Character.isDigit(text.charAt(0)) || text.charAt(0) == '.') {
				cost = cost + text.charAt(0);
				if (text.length() > 1)
					text = text.substring(1, text.length());
				else {
					text = "";
					break;
				}
			}
			if (text.length() != 0) {
				if (text.charAt(0) != ' ') {
					text = null;
					return true;
				}
			}
			return true;
		}
		return false;
	}

	private Boolean checkSubtotal(String text) {
		text = text.toLowerCase();
		if (text.contains("total")) {
			// The next index will be the grand total
		}
		return false;
	}
	
	private Boolean checkElement(String text) {
		return null;
		
	}
        
        //Find purchased items and products
        private List<String> findItems(List<String> text) throws IndexOutOfBoundsException{
            List<String> items = new ArrayList<String>();
            for (int i = 0; i < text.size();i++){
                    if (checkPrice(text.get(i))){
                       items.add(text.get(i-1) + text.get(i-2));
                    }
            }
            return items;
        }
}