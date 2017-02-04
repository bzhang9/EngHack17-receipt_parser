import java.util.List;

public class receipt {
	protected String title;
	protected List<Double> prices;
	protected List<String> items;
	protected String file;
	public receipt (String title, List<Double> prices, List<String> items, String file)
	{
		this.title = title;
		this.prices = prices;
		this.items = items;
		this.file = file;
	}
	
	
	public String getTitle()
	{
		return title;
	}
	
	
	public void setTitle(String newTitle)
	{
		this.title = newTitle;
	}
	
	
	public List<Double> getPrices ()
	{
		return prices;
	}
	
	
	public List<String> getItems ()
	{
		return items;
	}
	
	
	public String getFile()
	{
		return file;
	}		
}
