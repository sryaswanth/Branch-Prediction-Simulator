
import java.util.Arrays;

public class Hybrid extends sim
{
	int K;
	int[] chooser_counter_table;
	int number_of_rows = 0;

	
	public Hybrid(int k) 
	{
		// TODO Auto-generated constructor stub
		this.K = k;
		
		//calculation number of rows required in chooser counter array
		number_of_rows = (int) Math.pow(2,K);
		
		//creating the chooser counter table
		chooser_counter_table = new int [number_of_rows];
		
		//filling all the values of  chooser counter array to 1
		Arrays.fill(chooser_counter_table, 1);
		
	}

	public static int find_index_bit_values_hybrid(String message, int K) 
	{
		// TODO Auto-generated method stub
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
		     String index_bits_string = binary_string.substring(binary_string.length() - K);
		     
		     int index_bits_value =0;
		     
		     if(!index_bits_string.equals(""))
		     {
		     index_bits_value = Integer.parseInt(index_bits_string,2);
		     }
		   
		     int required_index_value = index_bits_value;
		     
		     return  required_index_value;
	}
	
	
	
	
	
	// function to predict which branch predictor to use
	public String decide_which_predictor_to_choose(int index) 
	{	
		// TODO Auto-generated method stub
		String branch_predicted = new String();
		if(this.chooser_counter_table[index]>=2)
		{
			branch_predicted = "gshare";
		}
		else if(this.chooser_counter_table[index]<2)
		{
			branch_predicted = "bimodal";
		}
		
		return branch_predicted;
	}

	
	//function to update the chooser counter table
	public void update_chooser_counter_table(int index, int count_gshare, int count_bimodal) 
	{
		// TODO Auto-generated method stub
		
			 if(count_gshare < count_bimodal)
			 {
				if(this.chooser_counter_table[index]!=3)
				{
					this.chooser_counter_table[index]++;
				}
			 }
			 
			 else  if(count_bimodal < count_gshare)
			 {
				if(this.chooser_counter_table[index]!=0)
				{
					this.chooser_counter_table[index]--;
				}
			 }
	}// end of the update chooser counter table function

}
