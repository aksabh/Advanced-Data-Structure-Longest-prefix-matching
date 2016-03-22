
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Map;


public class ssp {
												             
    private static Hashtable<Integer, Integer> final_path;  			/* creates Hashtable to save the path of  
    										 *  shortest path from source node to 
    										 *  destination node in the graph and display it
  										 *  in the output file
    										 */
        
    public static int vertices;							//stores the number of vertices
    
    private int[] data;								// store data of fibonacci heap tree
    private int[] index;							// store index of fibonacci heap tree
    public int[] cost;								// store cost of fibonacci heap tree
    private int size;								// store size of fibonacci heap tree
    
    private static Map<String,Integer> graph;			       		// creates a graph based on the input values
    
    private Scanner sc; 							// Object of scanner class to read input file
    
    
    private String[] numbers;							/* To store the values in the input file which
     										 * are separated by blank spaces
     										 */
    
    
    private static int final_cost;						/* store the final cost of the shortest 
    										 * path from the source node to the
    										 * destination node
    										 */
    
    
    private static int destination_node;					 /* To store the value of destination
		 								  * node given in the input argument
		 								  * args[2]
		 								  */	 
    
    
    private static int source_node;						 /* To store the value of source
	  									  * node given in the input argument
	  									  * args[1]
	  									  */	
    
    

    
    /*  use readFile() method given below 
     *  to read the input file and store the values
     */

    
    public void readFile(String inputFile){
    	
    	File file = new File(inputFile);			//reads the input file

        try {
        	
            sc = new Scanner(file);				// Scanner class to read each of line
            
            String firstLine=sc.nextLine();			// read a new line
            
            numbers=firstLine.split(" ");	   	    	// split it to array wherever their is a blank space
            
            vertices=Integer.parseInt(numbers[0]);		//stores the number of vertices
               
            graph = new Hashtable<String, Integer>();		// stores the values for the graph
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
            
    }
    
    
    
    
    /*  use createGraph() method given below 
     *  to find the weights between two edges
     *  edge1 and edge 2 from the input
     *  file and store the values 
     *  in graph
     */
    
    public void createGraph(){	 
                
            
            for(int i=0;i<vertices;i++)			  	   	// here we intialize the graph to zero
             	for(int j=0;j<vertices;j++)							
		graph.put(i+"-"+j, 0);
            	
            		
          
            while(sc.hasNextLine())
            {
            	try
            	{
            		
	                String line=sc.nextLine();			// get the line and save to line
	                
	                numbers=line.split(" ");			// split the line wherever their is a blank space
	                
	                int edge1=Integer.parseInt(numbers[0]);		//get the edge 
	                
	                int edge2=Integer.parseInt(numbers[1]); 	//get the edge
	                
	                int weight=Integer.parseInt(numbers[2]);	//get the weight 
	                
	                String key=edge1+"-"+edge2;
	                graph.put(key, weight);
	                key=edge2+"-"+edge1;
	                graph.put(key, weight);				 //store weight to the graph
	                
            	}
            	catch(Exception ae){}
            }
            sc.close();
      
    }
   


    /*  use My_Fibonacci_heap() method given below 
     *  to initialize the values of a fibonacci heap
     *  tree with x
     */
    
    public void My_Fibonacci_Heap(int x) {  
        data = new int[x];
        index = new int[x];
        cost = new int[x];

        for (int i = 0; i < x; i++) {
            index[i] = -1;
            cost[i] = -1;
        }

        size = 0;
    }
    
    
    
    /*  use findCost() method given below 
     *  to find the cost of the shortest path
     *  from the source node to destination node
     *  given in the input file
     */
    
    public int findCost(int totalNodes, int source, int dest){
    	
    	final_path=new Hashtable<Integer, Integer>();				// insert a vertex into the list of shortest path 
    	
        push(source, 0);							// push a vertex into heap
        
        while (!(size == 0)) 							// check if size is empty will be true
        {
        	
        	
        	/*  The following lines are used to pop the 
             *  element from the top of the heap,
             *  The top element of the heap is poped out
             *  and stored in heap_top
             */
        	
            int heap_top = data[0];						// get the minimum of weight in the top of heap	
            data[0] = data[size - 1];
            index[data[0]] = 0;
            size--;
            moveDown(0);				
                    
            
            /*  The following lines are used to calculate the 
             *  final cost and return its value
             */
            
            if(!final_path.containsKey(heap_top))final_path.put(heap_top, -1);
            if (heap_top == dest)
                return cost[dest];
            for (int i = 0; i < totalNodes; i++) 
            {
                String key=heap_top+"-"+i;
                
            	int value=graph.get(key);
                if (value > 0)
                {
                	if(cost[i]>0)
                	{
                		// use dijkstra to update the next vertex
                		if(cost[i]>cost[heap_top] + value)
                		{
                			push(i, cost[heap_top] + value);
                			final_path.put(i, heap_top);
                		}
                	}
                	else
                		{
                			push(i, cost[heap_top] + value);
                			final_path.put(i, heap_top);
                		}
                }
            }
        }
        return -1;
    	
    }
    

    /*  use push() method given below 
     *  to insert values into heap
     */
    
    public void push(int x, int c) {
    	
        if (index[x] == -1) {
        	
            cost[x] = c;
            data[size] = x;
            index[x] = size;
            size++;
            moveUp(index[x]);
        } else {
        	
            if (c < cost[x]) {
            	
                cost[x] = c;
                moveUp(index[x]);
                moveDown(index[x]);
            }
        }
    }
   
    
    
    /*  use moveUp() method given below 
     *  to manage heap
     */
    
    private void moveUp(int i) {
        int j;
        while (i > 0) {
        	
            j = (i - 1) / 2;
            
            //if the cost of data i is less than the cost of data j
            if (cost[data[i]] < cost[data[j]]) {
            	
                // swap here
                int temp = index[data[i]];
                index[data[i]] = index[data[j]];
                index[data[j]] = temp;
                // swap here
                temp = data[i];
                data[i] = data[j];
                data[j] = temp;
                i = j;
            } else
                break;
        }
    }
    
    
    /*  use moveDown() method given below 
     *  to manage heap
     */
    
    private void moveDown(int i) {
        int j, k;
        
        while (2 * i + 1 < size) {
        	
            j = 2 * i + 1;
            k = j + 1;
            
            if (k < size && cost[data[k]] < cost[data[j]]
                    && cost[data[k]] < cost[data[i]]) {
            	
                // swap here
                int temp = index[data[k]];
                index[data[k]] = index[data[i]];
                index[data[i]] = temp;
                // swap here
                temp = data[k];
                data[k] = data[i];
                data[i] = temp;

                i = k;
                
            } else if (cost[data[j]] < cost[data[i]]) {
            	
                // swap here
                int temp = index[data[j]];
                index[data[j]] = index[data[i]];
                index[data[i]] = temp;
                // swap here
                temp = data[j];
                data[j] = data[i];
                data[i] = temp;

                i = j;
            } else
                break;
        }
    }
    
     public static int noOfVertices()
    {
    	return vertices;
    }

    
    /*  use writeFile() method to write
     *  the final result in the output 
     *  file and show output on console.
     */
    
    public void writeFile(){
    	
    	  
    	try {
       	 
    		 
			File file = new File("my_output_part1.txt");
 
			
			if (!file.exists()) {									// if file doesn't exists, then create it
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());					// open file for write the final_path
			
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(final_cost+"");								// write the minimum weight of shortest path on the first line
			bw.newLine();
			System.out.println(final_cost+"");
			
			Hashtable<Integer, Integer> final_path=ssp.final_path;					// all vertex in the shortest path is saved in final_path
			
			int vertex=destination_node;								// start from destination
			
			String pathofGraph=vertex+""; 								//loop and get the before vertex that start from it to this vertex
			
			while(vertex!=source_node)
			{
				vertex=final_path.get(vertex);
				
				pathofGraph=vertex+" "+pathofGraph;						// add to final_path
				
			}
			
			bw.write(pathofGraph);									// write the final path to output
			System.out.println(pathofGraph);
			bw.close();
 
			
 
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    


   
    /*  The Main method which creates an object 
     *  of class ssp. It stores the 
     *  values of source node and destination
     *  node taken from the input arguments.
     *  Then it calls the following methods:-
     *  
     *  1. First the readFile() to read input
     *     file.	 
     *  2. Then the createGraph() to find the 
     *     weights between two edges
     *     edge1 and edge 2 from the input
     *     file and store the values 
     *     in graph array.
     *  3. Then My_Fibonacci_Heap to initialize 
     *     the heap.
     *  4. Then findCost() to find the final cost
     *     and stores it in the int variable
     *     final_cost
     *  5. Then finally the writeFile() to write 
     *     the results in the output file	   	   	   
     */


    
  public static void main(String[] args) {
    	
    	
    	if(args.length<3)						// checks whether the input arguments are correct or not 
    	{
    		System.out.println("the input is incorrect!");
    		return;
    	}
    	
        ssp obj = new ssp();             				/* Declaring and instantiating the 
        								 * object of class ssp
        						                 */
       
        source_node=Integer.parseInt(args[1]);   		        // used to store source node from args[1] 
        
        destination_node=Integer.parseInt(args[2]); 		        // used to store destination node from args[2] 
        
        obj.readFile(args[0]);	                  			/* calls method to read input file
        								 * given in args[0]
        			              				 */
        
        obj.createGraph();					        // to find shortest path from the graph 
        														                       
        obj.My_Fibonacci_Heap(vertices);	        		// calls the method My_Fibonacci_Heap to create tree
        
        final_cost =obj.findCost(noOfVertices(), source_node, destination_node); // calls method to find final cost
        
        obj.writeFile();							 /*  calls the method to write result
         									  *   in output file	
        									  */
    
  }

}

