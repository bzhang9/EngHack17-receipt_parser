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
	List<Double> priceList = new ArrayList<Double>();
	private String text;
	private Date date = null;
	private String cost = null;
	private String element = null;

	public static void main(String[] args) {
		List<String> test = Arrays.asList("01-20-2017", "asdfklj$1.50", "asdajfkhasdkjfh0000015.66", "50", "500", "50000", "5.00");
		text_checker example = new text_checker (test);
		String tempString;
		for(Iterator<String> i = example.getList().iterator(); i.hasNext(); ) {
			tempString = i.next();
		    //example.checkCategory(tempString);
		    example.checkPrice(tempString);
		}		
		System.out.print(example.getPriceList());
	}

	public text_checker(List<String> input) {
		textList = input;
	}
	
	public List<String> getList () {
		return textList;
	}

	public List<Double> getPriceList () {
		return priceList;
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
				System.out.println(cost);
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
	
	private Boolean checkElement(String text) {
		return null;
		
	}
}
