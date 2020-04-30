import java.io.File;
import java.util.Arrays;
import java.lang.Math;

public class Bimodal extends sim 
{
	
	int M2;
	int number_of_rows =0;
	int[] prediction_table;
	
	public Bimodal(int m2) 
	{
		this.M2 = m2;
		
		//calculation number of rows required in array
		number_of_rows = (int) Math.pow(2,M2);
				
		//creating the prediction table
		prediction_table = new int [number_of_rows];
		
		//filling all the values of array to 4 (since 3 bit counter - "0 to 7" and default value starts at 4 in this condition)
		Arrays.fill(prediction_table, 4);
	}
	
	
	
	// function to check if there is a misprediction
	public int check_misprediction_bimodal( int index, String prediction) 
	{
		int misprediction=0;
		// TODO Auto-generated method stub
		if(prediction.contentEquals("t"))
		{
				if(this.prediction_table[index] < 4)
				{
					misprediction++;
				}
		}
		
		else if(prediction.contentEquals("n"))
		{
				if(this.prediction_table[index] >= 4)
				{
					misprediction++;
				}
		}
		return misprediction;
	}


	//function to update the prediction table values
	public void update_prediction_table_bimodal( int index, String prediction) 
	{
		// TODO Auto-generated method stub
		if(prediction.contentEquals("t"))
		{
			if(this.prediction_table[index]!=7)
			{			
				this.prediction_table[index]++;
			}
		}
		
		else if(prediction.contentEquals("n"))
		{
			if(this.prediction_table[index]!=0)
			{
				this.prediction_table[index]--;
			}
		}
	}



	// function to calculate the index bit value and return it
	public static int find_index_bit_values_bimodal(String message, int M2) 
	{
		   String character_append ="0";
		   int decimal = Integer.parseInt(message,16);
		   String binary_string = Integer.toBinaryString(decimal);

		     int difference_in_bits = 32 -binary_string.length();
		     
		     if(difference_in_bits > 0)
		     {
		    	 for(int count = 0;count<difference_in_bits;count++)
		    	 {
		    		 binary_string = character_append + binary_string;
		    	 }
		     }

		     
		   //since last 2 bits are discarded
		     binary_string = binary_string.substring(0, binary_string.length()-2);
		     
		     //get the index string based on M value
		     String index_bits_string = binary_string.substring(binary_string.length() - M2);
		     
		     int index_bits_value =0;
		     
		     if(!index_bits_string.equals(""))
		     {
		     index_bits_value = Integer.parseInt(index_bits_string,2);
		     }
		   
		     int required_index_value = index_bits_value;
		     
		     return  required_index_value;
		     
	}

}
