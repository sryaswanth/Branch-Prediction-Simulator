# Branch-Prediction-Simulator

The simulator reads a trace file in the following format:
<hex branch PC> t|n
<hex branch PC> t|n
...
  
Here,

<hex branch PC> is the address of the branch instruction in memory. This field is used to index the predictors.
  
“t” indicates the branch is actually taken (Note! Not that it is predicted taken!). Similarly, “n” indicates the branch is actually not-taken.

Input Formats:

To simulate a bimodal predictor: sim bimodal <M2> <tracefile>, where M2 is the number of PC bits used to index the bimodal table.
  
To simulate a gshare predictor: sim gshare <M1> <N> <tracefile>, where M1 and N are the number of PC bits and global branch history register bits used to index the gshare table, respectively.
  
To simulate a hybrid predictor: sim hybrid <K> <M1> <N> <M2> <tracefile>, where K is the number of PC bits used to index the chooser table, M1 and N are the number of PC bits and global branch history register bits used to index the gshare table (respectively), and M2 is the number of PC bits used to index the bimodal table.
