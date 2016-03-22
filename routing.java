

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Map;


public class routing {
	
	private static int destination_node;					 /* To store the value of destination
	  									  * node given in the input argument
	  									  * args[2]
	 									  */
	
	 
    private static int source_node;						 /* To store the value of source
		 								  * node given in the input argument
		  								  * args[1]				 											  			  			  */	
    
    private Scanner sc; 							  // Object of scanner class to read input file
    
    
    private static NodeStructure theBTRootNode;					  // Defines the structure of nodes
    
    private String[] numbers;							  /* To store the values in the input file which
		 								   * are separated by blank spaces
		 								   */
    
    
    public static int vertices;		  				  	  //stores the number of vertices
    
    
    private static Map<String,Integer> graph;					  // creates a graph based on the input values
    
    
    private int[] data;								  // store data of fibonacci heap tree
    private int[] index;					   		  // store index of fibonacci heap tree
    public int[] cost;								  // store cost of fibonacci heap tree
    private int size;								  // store size of fibonacci heap tree
    
    
    private static int final_cost;						  /* store the final cost of the shortest 
     										   * path from the source node to the
     										   * destination node
     										   */
    

    
    private static Hashtable<Integer, Integer> final_path;   			   /*  creates Hashtable to save the path of  
     										    *  shortest path from source node to 
     						       				    *  destination node in the graph and display it
     										    *  in the output file
     										    */
    

    private static Hashtable<Integer, String> my_IP;				   /*  creates Hashtable to save the IP address of  
     										    *  each vertex as read from the input file
     										    *  in args[1]
     										    */
    
  
    


    /*  use readFile() method given below 
     *  to read the input file and store the values
     */

    
       public void readFile(String inputFile){
    	
    	File file = new File(inputFile);					//reads the input file

        try {
        	
            sc = new Scanner(file);						// Scanner class to read each of line
            
            String firstLine=sc.nextLine();					// read a new line
            
            numbers=firstLine.split(" ");	   			        // split it to array wherever their is a blank space
            
            vertices=Integer.parseInt(numbers[0]);				//stores the number of vertices
          
            
            graph = new Hashtable<String, Integer>();				// stores the values for the graph
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
     
     
     for(int i=0;i<vertices;i++)
     	for(int j=0;j<vertices;j++)
	graph.put(i+"-"+j, 0);
     
     //loop until 
     while(sc.hasNextLine())
     {
     	try
     	{
     		
             String line=sc.nextLine();						// get the line and save to line
             
             numbers=line.split(" ");						// split the line wherever their is a blank space
             
             int edge1=Integer.parseInt(numbers[0]);				//get the edge
             
             int edge2=Integer.parseInt(numbers[1]); 				//get the edge
             
             int weight=Integer.parseInt(numbers[2]);				//get the weight 
             
             String key=edge1+"-"+edge2;
             graph.put(key, weight);
             key=edge2+"-"+edge1;
             graph.put(key, weight);						//store weight to the graph
             
     	}
     	catch(Exception ae){}
     }
     sc.close();

}
 


 
 /*  use My_Fibonacci_heap() method given below 
  *  to initialize the values of a fibonacci heap
  *  tree with x
  */
 
 public void My_Fibonacci_Heap(int n) {  
     data = new int[n];
     index = new int[n];
     cost = new int[n];

     for (int i = 0; i < n; i++) {
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

 
 public int findCost(int numOfNodes, int source, int dest){
 	
 	final_path=new Hashtable<Integer, Integer>();				// insert a vertex into the list of shortest path
 	
     push(source, 0);								// push a vertex into heap
     
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
         for (int i = 0; i < numOfNodes; i++) 
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

 
 /*  use findIp() method given below
  *  to save the IP address of  
  *  each vertex as read from the 
  *  input file in args[1]
  */
 

 public static Hashtable<Integer,String> findIp(String filename)
 {
 	Hashtable<Integer,String> myIPs=new Hashtable<Integer,String>();
 	int ids=0;
 	File file = new File(filename);
     try {

         Scanner sc = new Scanner(file);
         while(sc.hasNextLine())
         {
	            String Line=sc.nextLine();
	            if(Line.trim().length()>2)
	            {
		            myIPs.put(ids, Line);
		            ids++;
	            }
         }
         sc.close();
     } 
     catch (FileNotFoundException e) {
         e.printStackTrace();
     }
 	return myIPs;
 }
 
 

 /*  use toBinary() method given below 
  *  to return String in binary form.
  */
 
 public static String toBinary(int number)
 {
 	String binary="";
 	while(number>0)
 	{
 		binary=number%2+binary;
 		number=number/2;
 	}
 	int n=8-binary.length();
 	for(int i=0;i<n;i++)
 	{ 
 		binary="0"+binary;
 	}
 	return binary;
 }
 
 

 /*  use insertTo() method given below 
  *  to return String in binary form.
  */ 
 
 public void insertTo(String data) {
     NodeStructure anyClassBTNode = new NodeStructure(data);
     //calls insert above
     theBTRootNode = insertAB(theBTRootNode, anyClassBTNode,0);
 }
 
 protected NodeStructure insertAB(NodeStructure currentNode, NodeStructure newNode,int index) 
 {
 	
 	if(index==newNode.data.length()-1)
 	{
 		
 		if(currentNode==null)currentNode=newNode;
 		else
 			currentNode.data=newNode.data;
 	}
 	else
 	{
 		
 		NodeStructure emptyBTNode = new NodeStructure("*");
 	
	    		if(currentNode==null)currentNode=emptyBTNode;
	    		if(newNode.data.charAt(index)=='1')
	    		{
	    			if(currentNode.rightBNode==null&&currentNode.leftBNode!=null)
	    			{
	    				
	    				currentNode.leftBNode.data=newNode.data;
	    			}
	    			else currentNode.rightBNode = insertAB(currentNode.rightBNode, newNode,index+1);
	    		}
	        	else
	        	{
	        		if(currentNode.leftBNode==null&&currentNode.rightBNode!=null)
	    			{
	    				currentNode.rightBNode.data=newNode.data;
	    			}
	    			else currentNode.leftBNode = insertAB(currentNode.leftBNode, newNode,index+1);
	        	}
	        	
 	}
 
 	
     return currentNode;
 }
 


 
 /*  use removeFromTrie() method given below 
  *  to remove from the Subtrie with the same
  *  next hop.
  */ 
 
 public void removeFromTrie(String data)
	{
		NodeStructure noderemove=null;
		NodeStructure currentNode=theBTRootNode;
		for(int i=0;i<data.length();i++)
		{
			if(!currentNode.data.equals("*")&&!currentNode.data.equals(data))noderemove=currentNode;
			if(data.charAt(i)=='1')currentNode=currentNode.rightBNode;
			else currentNode=currentNode.leftBNode;
		}
		
		if(noderemove!=null)
		{
			
			noderemove.rightBNode=null;
			noderemove.leftBNode=null;
		}
	}
 


 /*  use postOrderTraverse() method given below 
  *  to return String in binary form.
  */ 
 
 protected String postOrderTraverse (NodeStructure root)
 {
  
   if(root == null) return "";
   
   return root.data+" "+postOrderTraverse( root.leftBNode )+" "+ postOrderTraverse( root.rightBNode );  

 }
 public String postOrderTraverse() {
 	return postOrderTraverse(theBTRootNode);
 }
 


 /*  use getString() method given below 
  *  to return String in binary form.
  */ 
 
 public String getString(String data) {
     NodeStructure anyClassBTNode = new NodeStructure(data);
     //calls insert above
     return getString(theBTRootNode, anyClassBTNode,0);
 }
 
	protected String getString(NodeStructure currentNode, NodeStructure newNode,int index) {
    	
		if(currentNode==null)return "";
    	else
        {
        	
        	if(currentNode.data.equals(newNode.data))return "";
        	if(currentNode.leftBNode!=null)
        	{
        		if(currentNode.leftBNode.data.equals(newNode.data))return "0";
        	}
        	if(currentNode.rightBNode!=null)
        	{
        		if(currentNode.rightBNode.data.equals(newNode.data))return "1";
        	}
        	if (newNode.data.charAt(index)=='0') {
        		if(currentNode.leftBNode==null)return "";
		            return  "0"+getString(currentNode.leftBNode, newNode,index+1);
		        } else {
		            // else if bigger appends to the right
		        	if(currentNode.rightBNode==null)return "";
		        	return  "1"+getString(currentNode.rightBNode, newNode,index+1);
		        }
        }
         
	 }


	
    /*  use writeFile() method to write
     *  the final result in the output 
     *  file and show output on console.
     */	
 
 public void writeFile(){
 	
	  
 	try {
    	 
 		 
			File file = new File("my_output_part2.txt");

			
			if (!file.exists()) {									// if file doesn't exists, then create it
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());					// open file for write the final_path
			
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(final_cost+"");								// write the minimum weight of shortest path on the first line
			bw.newLine();
			System.out.println(final_cost+"");
			
			Hashtable<Integer, Integer> final_path=routing.final_path;				// all vertex in the shortest path is saved in final_path
			
			int vertex=destination_node;								// start from destination
			
			String pathofGraph=vertex+""; 								//loop and get the before vertex that start from it to this vertex
			
			while(vertex!=source_node)
			{
				vertex=final_path.get(vertex);
				
				pathofGraph=vertex+" "+pathofGraph;						// add to final_path
				
			}
			
			String[] listVertex=pathofGraph.split(" ");
			//String binarysource_node=my_IP.get(source_node);
			String binarydestination_node=my_IP.get(destination_node);
			String IPvertex=my_IP.get(destination_node);
			String[] ips=IPvertex.split("\\.");
			int nip=Integer.parseInt(ips[0]);
			String binaryvertex=toBinary(nip);
			binaryvertex=binaryvertex+toBinary(Integer.parseInt(ips[1]));
			binaryvertex=binaryvertex+toBinary(Integer.parseInt(ips[2]));
			binaryvertex=binaryvertex+toBinary(Integer.parseInt(ips[3]));
			
			binarydestination_node=binaryvertex;
			insertTo(binaryvertex);
			//for each of vertex
			for(int i=1;i<listVertex.length;i++)
			{
				int nvertex=Integer.parseInt(listVertex[i]);
				 IPvertex=my_IP.get(nvertex);
				 ips=IPvertex.split("\\.");
				
				
				nip=Integer.parseInt(ips[0]);
				binaryvertex=toBinary(nip);
				binaryvertex=binaryvertex+toBinary(Integer.parseInt(ips[1]));
				binaryvertex=binaryvertex+toBinary(Integer.parseInt(ips[2]));
				binaryvertex=binaryvertex+toBinary(Integer.parseInt(ips[3]));
				//bw.write(getString(binaryvertex)+" ");
				
				// insert this ip into binary tree
				insertTo(binaryvertex);
			}
			removeFromTrie(binarydestination_node);
			// the next for each of vertex in the shortest path
			
			String thepath=postOrderTraverse();
			   String [] lstnode=thepath.split(" ");
			   
			   for(String str:lstnode)
			   {
				   if(str.length()>2) 
				   {
					   //System.out.println(str);
					   bw.write(getString(str)+" ");
					   System.out.print(getString(str)+" ");
				   }
			   }
			   bw.write(getString(binarydestination_node));
			   System.out.println(getString(binarydestination_node));
			
			bw.close();

			//System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
 }
 

 
 /*  The Main method which creates an object 
  *  of class routing. It stores the 
  *  values of source node and destination
  *  node taken from the input arguments.
  *  Then it calls the following methods:-
  *  
  *  1. First the readFile() to read input
  *     file.	 
  *  2. Then the createGraph() to find 
  *     the shortest path.
  *  3. Then My_Fibonacci_Heap to initialize 
  *     the heap.
  *  4. Then findIp() to find the IP address of
  *  	each corresponding node from the input
  *  	file given in args[1] and store them in
  *  	HashTable myIPs  
  *  5. Then findCost() to find the final cost
  *     and stores it in the integer variable
  *     final_cost
  *  6. Then finally the writeFile() to write 
  *     the results in the output file and print
  *     output on the console	   	   	   
  */
    


 
	 public static void main(String[] args) 
	    {
	    	
	    	if(args.length<4)								// checks whether the input arguments are correct or not
	    	{
	    		System.out.println("the input is wrong");
	    		return;
	    	}
	    	
	    	
	    	routing obj = new routing();							/* Declaring and instantiating the 
			   									 * object of class ssp
			   									 */
	    		    		    	
	    	
	    	source_node=Integer.parseInt(args[2]);						// used to store source node from args[1] 
	    	
	        destination_node=Integer.parseInt(args[3]);		 			// used to store destination node from args[2]
	        
	        	
	        obj.readFile(args[0]);								/* calls method to read input file
			   									 * given in args[0]
			   									 */
	        
	        
	        obj.createGraph();								 // to find shortest path from the graph
	        
	        
	        obj.My_Fibonacci_Heap(vertices);						 // calls the method My_Fibonacci_Heap to create tree
	        
	        my_IP=findIp(args[1]);								/*  calls method findIp to save the IP address of  
	         										 *  each vertex as read from the input file
	         										 *  in args[1]
	         										 */
	        
	        final_cost =obj.findCost(noOfVertices(), source_node, destination_node);	 // calls method to find final cost
	        
	        theBTRootNode = null;
	        
	        obj.insertTo("*");
	        
	        obj.writeFile();								/*  calls the method to write result
			   									 *  in output file	
			   									 */
	        
	    }
}



/* The following class is used
 * to define the structure of nodes
 */

class NodeStructure {

    public NodeStructure leftBNode,  rightBNode; // the nodes
    public String data; 

    public NodeStructure(String data ) {//constructor
        this.data= data;
        this.leftBNode = null;
        this.rightBNode = null;
    }
}

