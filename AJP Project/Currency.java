package converter;



import java.util.ArrayList;


import java.util.HashMap;



public class Currency

{
	

	private String name;


	private String shortName;


	private HashMap<String, Double> exchangeValues = new HashMap<String, Double>();
	


	// "Currency" Constructor


	public Currency(String nameValue, String shortNameValue)

	{
	
	
		this.name = nameValue;
	

		this.shortName = shortNameValue;


	}
	
	

// Getter for name
	

	public String getName()

 	{
		

		return this.name;


	}
	
	

// Setter for name

	
	public void setName(String name) 

	{
		

		this.name = name;


	}
	


// Getter for shortName


	public String getShortName() 

	{
	
	
		return this.shortName;
	

	}
	

	
// Setter for shortName
	

	public void setShortName(String shortName)

	{
	
	
		this.shortName = shortName;


	}

	
	
// Getter for exchangeValues


	public HashMap<String, Double> getExchangeValues()

 	{
		

		return this.exchangeValues;


	}

	
	
// Setter for exchangeValues


	public void setExchangeValues(String key, Double value)

 	{
		

		this.exchangeValues.put(key, value);


	}
	
	

// Set default values for a currency
	

	public void defaultValues()

 	{
		

		String currency = this.name;
	

	
		

	switch (currency) 

		{	
	
		
		case "US Dollar":


		this.exchangeValues.put("USD", 1.00);

	
		this.exchangeValues.put("EUR", 0.86);

				
		this.exchangeValues.put("PS", 0.77);

	
		this.exchangeValues.put("CHF", 0.92);
	

		this.exchangeValues.put("INR", 74.55);
	
	
		this.exchangeValues.put("JPY", 104.66);
	
	
		break;


		

		case "Euro":
	
			
		this.exchangeValues.put("USD", 1.17);
	
		
		this.exchangeValues.put("EUR", 1.00);

			
		this.exchangeValues.put("PS", 0.90);

		
		this.exchangeValues.put("CHF", 1.07);

	
		this.exchangeValues.put("INR", 87.05);
	

		this.exchangeValues.put("JPY", 122.21);


		break;
			


		case "Pound Sterling":
	
			
		this.exchangeValues.put("USD", 1.29);
	
	
		this.exchangeValues.put("EUR", 1.11);
	

		this.exchangeValues.put("PS", 1.00);

	
		this.exchangeValues.put("CHF", 1.19);

	
		this.exchangeValues.put("INR", 96.50);

		
		this.exchangeValues.put("JPY", 135.93);
		
	
		break;
	

		

		case "Swiss Franc":
	

		this.exchangeValues.put("USD", 1.09);
	

		this.exchangeValues.put("EUR", 0.93);

		
		this.exchangeValues.put("PS", 0.84);
	
	
		this.exchangeValues.put("CHF", 1.00);
	
	
		this.exchangeValues.put("INR", 81.21);
	
	
		this.exchangeValues.put("JPY", 114.01);
	
	
		break;
		



		case "Indian Rupees":

			
		this.exchangeValues.put("USD", 0.013);
	
	
		this.exchangeValues.put("EUR", 0.011);
	

		this.exchangeValues.put("PS", 0.010);


		this.exchangeValues.put("CHF", 0.012);

			
		this.exchangeValues.put("INR", 1.00);


		this.exchangeValues.put("JPY", 1.41);


		break;
	

	

		case "Japanese Yen":

		
		this.exchangeValues.put("USD", 0.0096);
	

		this.exchangeValues.put("EUR", 0.0082);
	

		this.exchangeValues.put("PS", 0.0074);
	
	
		this.exchangeValues.put("CHF", 0.0088);
	
	
		this.exchangeValues.put("INR", 0.71);
	
	
		this.exchangeValues.put("JPY", 1.000);
	
	
		break;
		

		}
	

	}
	
	
// Initialize currencies
	

	public static ArrayList<Currency> init()

	{
		
		ArrayList<Currency> currencies = new ArrayList<Currency>();

	
		currencies.add( new Currency("US Dollar", "USD") );

	
		currencies.add( new Currency("Euro", "EUR") );
	

		currencies.add( new Currency("Pound Sterling", "PS") );
	

		currencies.add( new Currency("Swiss Franc", "CHF") );
	

		currencies.add( new Currency("Indian Rupees", "INR") );
	

		currencies.add( new Currency("Japanese Yen", "JPY") );
	
	

	for (Integer i =0; i < currencies.size(); i++) 

		{

			
			currencies.get(i).defaultValues();


		}		
	

	
		return currencies;


	}
	
	

// Convert a currency to another


	public static Double convert(Double amount, Double exchangeValue)
 
	{
	
	
		Double price;
	
		price = amount * exchangeValue;
	
		price = Math.round(price * 100d) / 100d;
	
	
	return price;


	}


}