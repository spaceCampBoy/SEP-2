package server.util;
import java.text.DecimalFormat;
import java.util.Random;
public class NemID {
	private static Random random = new Random();
	private static DecimalFormat formatKey = new DecimalFormat("0000");
	private static DecimalFormat formatValue = new DecimalFormat("000000");
	public static String[][] generateKeysValues()
	{
		//first Column has 4 digits(9999) interger, second column has 6 digits(999999) int
		String[] keys = new String[132]; 
		String[] values = new String[132];
		
		
		randomKeysGenerator(keys,9999, formatKey);
		randomValuesGenerator(values, 999999, formatValue);
		
		
		String[][] nemID = new String[132][2];
		
		for(int i = 0; i < nemID.length; i++)
		{
				nemID[i][0] = keys[i];
				nemID[i][1] = values[i];
		}
		
		return nemID;
	}
	
	private static void randomKeysGenerator(String[] keys, int bound, DecimalFormat df) {
		// TODO Auto-generated method stub
		int[]	intKeys = new int[132];
		for(int i = 0; i < intKeys.length; i++)
		{
			intKeys[i] = random.nextInt(bound);
		}
		
		Sorting.selectionSort(intKeys);
		
		for(int j = 0; j < keys.length; j++)
		{
			keys[j] = df.format(intKeys[j]);
		}
		
		
		
	}

	private static void randomValuesGenerator(String[] values, int bound, DecimalFormat df)
	{
		for(int i = 0; i < values.length; i++)
		{
			values[i] = df.format(random.nextInt(bound));
		}
		
	}
	

}
