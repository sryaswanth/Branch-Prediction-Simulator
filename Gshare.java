

import java.util.Arrays;
import java.lang.*;
import java.util.*;

public class Gshare extends sim 
{
	int M1;
	int N;
	int number_of_rows =0;
	int[] prediction_table;
	static String global_branch_history  = new String();
	
	static Gshare g1 = new Gshare();
	
	
	public Gshare() {
		// TODO Auto-generated constructor stub
	}
	
	public Gshare(int m1, int n) 
	{
		// TODO Auto-generated constructor stub
		this.M1 = m1;
		this.N = n;
		
		//calculation number of rows required in array
		number_of_rows = (int) Math.pow(2,M1);
						
		//creating the prediction table
		prediction_table = new int [number_of_rows];
				
		//filling all the values of array to 4 (since 3 bit counter - "0 to 7" and default value starts at 4 in this condition)
		Arrays.fill(prediction_table, 4);
		
		//initially append "0" to branch history register string
		global_branch_history =	append_0_to_branch_histroy_register(global_branch_history,N);
	}
	


	//function to append '0' to the branch history register for 'N' times
	private String append_0_to_branch_histroy_register(String branch_history_register, int n) 
	{
		// TODO Auto-generated method stub
		
		char value = '0';
		
		for(int i=0;i<n;i++)
		{
			branch_history_register += value;
		}
		return branch_history_register;
	}




	// function to check if there is a misprediction
	public int check_misprediction_gshare( int index, String prediction) 
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
	public void update_prediction_table_gshare( int index, String prediction) 
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
	public static int find_index_bit_values_gshare(String message, int M1, int N) 
	{
		  
		   String character_append ="0";
		   String new_substring = new String();
		   
		   String substring_to_split_m_and_n = new String();
		   
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
		     String index_bits_string = binary_string.substring(binary_string.length() - M1);
		     
		     substring_to_split_m_and_n = index_bits_string.substring(index_bits_string.length() - N);
		     
		     // function to perform XOR operation
		     new_substring = g1.performing_XOR_operation(substring_to_split_m_and_n);
		     
		     //modified index_bits_string
		     index_bits_string = (index_bits_string.substring(0,index_bits_string.length() - N)+ new_substring);
		     
		     
		     int index_bits_value =0;
		     
		     if(!index_bits_string.equals(""))
		     {
		     index_bits_value = Integer.parseInt(index_bits_string,2);
		     }
		   
		     int required_index_value = index_bits_value;
		     
		     return  required_index_value;     
	}


	public String performing_XOR_operation(String substring_to_split_m_and_n) 
	{
		// TODO Auto-generated method stub
		String new_substring = new String();
		
		for(int i=0; i<substring_to_split_m_and_n.length();i++)
		{
			//access each charatcter in the strings
			int XOR;
			
			//converting string to character_value_then_to_integer for 'substring_to_split_m_and_n'
			char char_in_substring_to_split_m_and_n = substring_to_split_m_and_n.charAt(i);
			int integer_char_in_substring_to_split_m_and_n =Integer.parseInt(String.valueOf(char_in_substring_to_split_m_and_n));
		//	System.out.println("substring_to_split_m_and_n"+integer_char_in_substring_to_split_m_and_n);
			
			//converting string to character_value_then_to_integer for 'BHR'
			char char_in_GBH = global_branch_history.charAt(i);
			int integer_char_in_BHR =Integer.parseInt(String.valueOf(char_in_GBH));
		//	System.out.println("branch history register"+integer_char_in_BHR);
			
			//XOR operation
			XOR = integer_char_in_substring_to_split_m_and_n^integer_char_in_BHR;
			
			new_substring += XOR;
			
		}
		return new_substring;
	}

	
	// function to update the Global branch history
	public void update_global_branch_history_register(String prediction) 
	{
		// TODO Auto-generated method stub
		
		global_branch_history = global_branch_history.substring(0,global_branch_history.length()-1);
	//	System.out.println(branch_history_register);
		
		if(prediction.contentEquals("t"))
		{
			global_branch_history = "1"+global_branch_history;
		}
		
		else if(prediction.contentEquals("n"))
		{
			global_branch_history = "0"+global_branch_history;
		}
		
	}
	

}
