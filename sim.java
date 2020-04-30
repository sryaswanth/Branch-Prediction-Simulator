
import java.io.File;
import java.io.FileNotFoundException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

import java.lang.Math;

public class sim {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		int K = 0;
		int M1 = 0;
		int N = 0;
		int M2 = 0;
		String filepath = new String();
		
		
		//what type of predictor it is (bimodal/gshare/hybrid)
		String predictor = args[0];
		
		if(predictor.contentEquals("bimodal"))
		{
				
		// 'm' value in the input for bimodal
		  M2 = Integer.parseInt(args[1]);
		
		// trace file type and path (eg: gcc_trace)
		filepath = args[2];
		}
		
		else if(predictor.contentEquals("gshare"))
		{
			// 'm' value in the input for gshare
			 M1 = Integer.parseInt(args[1]);
			
			// 'n' value in the input for gshare
			 N = Integer.parseInt(args[2]);	
			
			// trace file type and path (eg: gcc_trace)
			filepath = args[3];
		}
		
		else if(predictor.contentEquals("hybrid"))
		{
			// 'k' value in the input for gshare
			 K = Integer.parseInt(args[1]);
			
			// 'm' value in the input for gshare
			 M1 = Integer.parseInt(args[2]);
			
			// 'n' value in the input for gshare
			 N = Integer.parseInt(args[3]);	
			
			// 'm' value in the input for bimodal
			  M2 = Integer.parseInt(args[4]);
			
			// trace file type and path (eg: gcc_trace)
			filepath = args[5];
		}
		
		
		File validation_file = new File(filepath);
		
		//create class object for bimodal prediction
		Bimodal b = new Bimodal(M2); 
		
		//create class object for gshare prediction
		Gshare g = new Gshare(M1, N);
		
		//create class object for hybrid prediction
		Hybrid h = new Hybrid(K);

		
		//**********************
		// for the sake of hybrid 
			int count_bimodal =0;	
			int count_gshare =0;	
				
		//**********************
		
		//count variable for temporary result storage
		int count=0;
		
		int number_of_predictions =0; // total number of predictions
		
		int misprediction_count =0;
		
		double misprediction_rate = 0; 
		
		
		Scanner file = new Scanner(validation_file);
		
		
		while(file.hasNextLine())
		{

			// increment prediction count
			number_of_predictions++;
			
			// initializing index to find the correct index value for this iteration
			 int index = 0;
			 
			 //************
			 //just for hybrid
			 int index_bimodal =0;
			 int index_gshare =0;
			 String branch_predicted = new String();
			 //***************
			 
			 
			// splitting message and prediction
			String branch = file.nextLine();
			String[] seperated_content = branch.split(" ");
			
		    String message = seperated_content[0];
		    String prediction = seperated_content[1];
		    
		    
		    //bimodal predictor functions
		    if(predictor.contentEquals("bimodal"))
		    {
		    	//calling function to find index bit value
			   index =  Bimodal.find_index_bit_values_bimodal(message, M2);
			    
			    //check if there is a misprediction
			   count = b.check_misprediction_bimodal(index, prediction);
			   
			   misprediction_count = misprediction_count + count;
			    
			    //function to update prediction_table
			   b.update_prediction_table_bimodal( index, prediction);
		    } // end of bimodal predictor 'if' condition
		    
		    
		    //gshare predictor functions
		    else if(predictor.contentEquals("gshare"))
		    {
			    //calling function to find index bit value
			   index =  Gshare.find_index_bit_values_gshare(message, M1, N);
			    
			    //check if there is a misprediction
			   count = g.check_misprediction_gshare(index, prediction);
			   
			   misprediction_count = misprediction_count + count;
			    
			    //function to update prediction_table
			   g.update_prediction_table_gshare( index, prediction);
			   
			   g.update_global_branch_history_register(prediction);
			   
		    } // end of gshare predictor 'if' condition
		    
		  //hybrid predictor functions
		    if(predictor.contentEquals("hybrid"))
		    {
		    	//index to address chooser counter table
		    	 index =  Hybrid.find_index_bit_values_hybrid(message, K);
		    	
		    	 index_bimodal =  Bimodal.find_index_bit_values_bimodal(message, M2);
		    	
		    	 
		    	 //check if there is a misprediction
				 count_bimodal = b.check_misprediction_bimodal(index_bimodal, prediction);
				 
				 index_gshare =  Gshare.find_index_bit_values_gshare(message, M1, N);
				 				 
				 
				 //check if there is a misprediction
				 count_gshare = g.check_misprediction_gshare(index_gshare, prediction);
				 
				 //choose which predictor is best(bimodal/gshare)
				 branch_predicted = h.decide_which_predictor_to_choose(index);
				 
				 if(branch_predicted.contentEquals("bimodal"))
				 {
				
					 misprediction_count = misprediction_count + count_bimodal;
					    
					 //function to update prediction_table
					 b.update_prediction_table_bimodal( index_bimodal, prediction);
					 
				 }
				 
				 else  if(branch_predicted.contentEquals("gshare"))
				 {
					 misprediction_count = misprediction_count + count_gshare;
					    
					 //function to update prediction_table
					 g.update_prediction_table_gshare( index_gshare, prediction);
				 }
				 				 
				 //function to update chooser counter table in hybrid 
				 if(count_bimodal != count_gshare)
				 {
					 h.update_chooser_counter_table(index,count_gshare,count_bimodal);
				 }
				 
				 //update global branch history no matter what
				 g.update_global_branch_history_register(prediction);
		    } // end of hybrid predictor 'if' condition
		    
		} //end of tracing the trace file
		
	
	
		//output display functions
		
		//calculate misprediction rate
		System.out.println("COMMAND");
		 if(predictor.contentEquals("bimodal"))
		 {
			 System.out.println("./sim"+" "+predictor+" "+M2+" "+validation_file.getName());
			 System.out.println("OUTPUT");
		 }
		 if(predictor.contentEquals("gshare"))
		 {
			 System.out.println("./sim"+" "+predictor+" "+M1+" "+N+" "+validation_file.getName());
			 System.out.println("OUTPUT");
		 }
		 if(predictor.contentEquals("hybrid"))
		 {
			 System.out.println("./sim"+" "+predictor+" "+K+" "+M1+" "+N+" "+M2+" "+validation_file.getName());
			 System.out.println("OUTPUT");
		 }
		 
		misprediction_rate = ((double) misprediction_count / (double) number_of_predictions)*100;
		
		String final_misprediction_rate = String.format("%.2f" , misprediction_rate);

		System.out.println("number of predictions:"+ number_of_predictions);
		System.out.println("number of mispredictions: "+ misprediction_count);
		 System.out.println("misprediction rate:"+final_misprediction_rate+"%");
		 
		 
		 if(predictor.contentEquals("bimodal"))
		 {
			 System.out.println("FINAL BIMODAL CONTENTS");
		//displaying the prediction_table array contents
				for(int i=0; i< b.number_of_rows; i++)
				{
					System.out.println(i+" "+b.prediction_table[i]);
				}	
		 }
		 
		 
		 if(predictor.contentEquals("gshare"))
		 {
		System.out.println("FINAL GSHARE CONTENTS");
		//displaying the prediction_table array contents
				for(int i=0; i< g.number_of_rows; i++)
				{
					System.out.println(i+" "+g.prediction_table[i]);
				}	
		 }
		 
		 if(predictor.contentEquals("hybrid"))
		 {
		System.out.println("FINAL CHOOSER CONTENTS");
		//displaying the chooser_table array contents
				for(int i=0; i< h.number_of_rows; i++)
				{
					System.out.println(i+" "+h.chooser_counter_table[i]);
				}
		
		System.out.println("FINAL GSHARE CONTENTS");
		//displaying the prediction_table array contents
				for(int i=0; i< g.number_of_rows; i++)
				{
					System.out.println(i+" "+g.prediction_table[i]);
				}	
		System.out.println("FINAL BIMODAL CONTENTS");
		//displaying the prediction_table array contents
				for(int i=0; i< b.number_of_rows; i++)
				{
					System.out.println(i+" "+b.prediction_table[i]);
				}	
		
		 }
		 
	} // end of main function

} // end of java file
