import java.lang.Math;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Stack;
import java.lang.Comparable;
import java.util.concurrent.TimeUnit;

public class AStarSearch{
    //give color using ANSI escape code constants
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String WHITE = "\033[0;37m";   // WHITE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static Scanner input = new Scanner(System.in);
    public static int[] goalCoords = {0,0}; //at top left by default
    public static int[] agentCoords = {0,0}; //at top left by default
    public static Node[][] nodeArray = new Node[15][15];
    public static PriorityQueue openList = new PriorityQueue<>();
    public static ArrayList<Node> closedList = new ArrayList<Node>();
    public static char[][] holdState;
    

 


    public static void main(String[] args){
        AStarSearch search = new AStarSearch();
    }

    public AStarSearch(){
        char[][] newState = {
            {'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},
            {'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},
            {'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},
            {'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},
            {'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},
            {'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},
            {'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},
            {'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},
            {'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},
            {'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},
            {'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},
            {'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},
            {'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},
            {'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'},
            {'O','O','O','O','O','O','O','O','O','O','O','O','O','O','O'}};
        int blockPathCount = 0;
        int randRow;
        int randCol;
        //randomly block 10%(25) paths
        while(blockPathCount < 25){
            int randomInt = (int)(Math.random() * 225);
            if(randomInt == 0){
                randRow = 0;
                randCol = 0;
            }else{
                randCol = randomInt % 15;
                randRow = randomInt / 15;
            }
            char value = newState[randRow][randCol];
            if(value == 'O'){
                newState[randRow][randCol] = 'X';
                blockPathCount++;
            }
        }
        
        
        System.out.println(WHITE + "Starting Environment:");
            displayState(newState);
            getStartingPosition(newState);
            getGoalPosition(newState);
            initializeNodeArray(newState);
            //will run A* search here
            openList.add(nodeArray[agentCoords[0]][agentCoords[1]]);
            while(true){
                //if openList is empty return failure
                if(openList.isEmpty()){
                    System.out.println(RED + "Path could not be found");
                    System.exit(0);
                }
                Node node = (Node)openList.poll();
                closedList.add(node);
                checkNeighborNodes(node, newState);
            }

    }


    public static void displayState(char[][] state){
        
        for(char[] row : state){
            for(char entry : row){
                //check if char is X
                if(entry == 'X'){
                    System.out.print(RED + entry + " ");
                }else if(entry == 'A'){
                    System.out.print(CYAN + entry + " ");
                }else if(entry == 'G'){
                    System.out.print(YELLOW + entry + " ");
                }else if(entry == '*'){
                    System.out.print(GREEN + entry + " ");
                }else{
                    System.out.print(WHITE + entry + " ");
                }
            }
            System.out.println();
        }
    }

    public static void getStartingPosition(char[][] state){
        int startingRow = 0;
        int startingCol = 0;
        while(true){
            while(true){
                try{
                    System.out.println(WHITE + "Please input starting row of agent (0-14): ");
                    startingRow = input.nextInt();
                    agentCoords[0] = startingRow;
                    if((startingRow < 0) || (startingRow > 14)){
                        System.out.println(RED + "Invalid starting row, needs to be between 0-14");
                    }
                    else break;
                }
                catch(InputMismatchException ex){
                    System.out.println(RED + "Invalid Input");
                    input.nextLine();
                }
                catch(NoSuchElementException ex){
                    System.out.println(RED + "Program Terminated");
                    System.exit(0);
                }
                catch(Exception ex){
                    System.out.println(RED + "NEED TO ADD CATCH FOR " + ex.getClass());
                }
            }
            while(true){
                try{
                    System.out.println(WHITE + "Please input starting column of agent (0-14): ");
                    startingCol = input.nextInt();
                    agentCoords[1] = startingCol;
                    if((startingCol < 0) || (startingCol > 14)){
                        System.out.println(RED + "Invalid starting column, needs to be between 0-14");
                    }else break;
                }
                catch(InputMismatchException ex){
                    System.out.println(RED + "Invalid Input");
                    input.nextLine();
                }
                catch(NoSuchElementException ex){
                    System.out.println(RED + "Program Terminated");
                    System.exit(0);
                }
                catch(Exception ex){
                    System.out.println(RED + "NEED TO ADD CATCH FOR " + ex.getClass());
                }
            }
            if(state[startingRow][startingCol] == 'X'){
                System.out.println(RED + "invalid starting point, Cannot place agent in unpathable node");
            }else break;
        }
        state[startingRow][startingCol] = 'A';
        displayState(state);
    }

    public static void getGoalPosition(char[][] state){
        int goalRow = 0;
        int goalCol = 0;
        while(true){
            while(true){
                try{
                    System.out.println(WHITE + "Please input row of goal (0-14): ");
                    goalRow = input.nextInt();
                    goalCoords[0] = goalRow;
                    if((goalRow < 0) || (goalRow > 14)){
                        System.out.println(RED + "Invalid goal row, needs to be between 0-14");
                    }
                    else break;
                }
                catch(InputMismatchException ex){
                    System.out.println(RED + "Invalid Input");
                    input.nextLine();
                }
                catch(NoSuchElementException ex){
                    System.out.println(RED + "Program Terminated");
                    System.exit(0);
                }
                catch(Exception ex){
                    System.out.println(RED + "NEED TO ADD CATCH FOR " + ex.getClass());
                }
            }
            while(true){
                try{
                    System.out.println(WHITE + "Please input cdolumn of goal (0-14): ");
                    goalCol = input.nextInt();
                    goalCoords[1] = goalCol;
                    if((goalCol < 0) || (goalCol > 14)){
                        System.out.println(RED + "Invalid goal column, needs to be between 0-14");
                    }else break;
                }
                catch(InputMismatchException ex){
                    System.out.println(RED + "Invalid Input");
                    input.nextLine();
                }
                catch(NoSuchElementException ex){
                    System.out.println(RED + "Program Terminated");
                    System.exit(0);
                }
                catch(Exception ex){
                    System.out.println(RED + "NEED TO ADD CATCH FOR " + ex.getClass());
                }
            }
            if(state[goalRow][goalCol] == 'X'){
                System.out.println(RED + "invalid goal point, Cannot place goal in unpathable node");
            }else if(state[goalRow][goalCol] == 'A'){
                System.out.println(RED + "invalid goal point, Agent would already be at goal");
            }else break;
        }
        state[goalRow][goalCol] = 'G';
        displayState(state);
    }

    public static void initializeNodeArray(char[][] state){
        for(int row = 0; row < 15; row++){
            for(int col = 0; col < 15; col++){
                //check if path is traversable
                if((state[row][col] == 'O') || (state[row][col] == 'A' || (state[row][col] == 'G'))){
                    nodeArray[row][col] = new Node(row, col, 0);
                }else{
                    nodeArray[row][col] = new Node(row, col, 1);
                }
            }
        }

        for(int row = 0; row < 15; row++){
            for(int col = 0; col < 15; col++){
                //check if path is traversable
                if((state[row][col] == 'O') || (state[row][col] == 'A' || (state[row][col] == 'G'))){
                    nodeArray[row][col].setH(calculateHeuristic(nodeArray[row][col], nodeArray[goalCoords[0]][goalCoords[1]]));
                }
            }
        }
    }

    public static int calculateHeuristic(Node currentNode, Node goalNode){
        //calculate heuristic value using manhatten method
        return 10 * (Math.abs((currentNode.getRow() - goalNode.getRow())) + Math.abs((currentNode.getCol() - goalNode.getCol())));
    }

    public static void generateGoalPath(Node goalNode, char[][] state){
        Stack<Node> pathStack = new Stack<>();
        Node currentNode = goalNode;
        //System.out.println(goalNode.toString());
        //System.out.println(goalNode.getParent().toString());
        Node parentNode = null;
        
        while(true){
            //add currentNode to stack
            pathStack.add(currentNode);
            //get parent from current node
            parentNode = currentNode.getParent();
            //check if parentNode is null if it is its beginning of path
            if(parentNode == null){
                break;
            }else{
                currentNode = parentNode;
            }
        }
        //System.out.println("Generated pathStack");
        ArrayList<Node> pathList = new ArrayList<Node>();
        //generate path using stack
        pathStack.pop();
        while(!pathStack.isEmpty()){
            Node currentNodePathStep = pathStack.pop();
            pathList.add(currentNodePathStep);

            //convert node to *
            state[currentNodePathStep.getRow()][currentNodePathStep.getCol()] = '*';
        }
        //System.out.println("converted stack to list");
        //System.out.println(pathStack);
        displayState(state);
        System.out.println();
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch(Exception ex){
            System.out.println(ex.getLocalizedMessage());
        }

        char[][] initialPathState = deepCopyArray(state);
        initialPathState[agentCoords[0]][agentCoords[1]] = '*';
        //System.out.println(pathList);
        for(int i = 0; i < pathList.size(); i++){
            char[][] newState = deepCopyArray(initialPathState);
            newState[pathList.get(i).getRow()][pathList.get(i).getCol()] = 'A';
            displayState(newState);
            System.out.println();
            newState = initialPathState;
            try{
                TimeUnit.MILLISECONDS.sleep(750);
            }catch(Exception ex){
                System.out.println(ex.getLocalizedMessage());
            }
            holdState = newState;
        }
    }

    private static char[][] deepCopyArray(char[][] original) {
        if (original == null) return null;
        char[][] copy = new char[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    public static void resetState(char[][] state){
        for(char[] row : state){
            for(char col: row){
                if(col == '*'){
                    col = 'O';
                }
            }
        }
    }

    public static void checkNeighborNodes(Node currentNode, char[][] state){
        int[] colRange = {-1,1};
        int[] rowRange = {-1,1};
        //check if previous column is not valid(left column)
        if(currentNode.getCol() - 1 < 0){
            colRange[0] = 0;
        }
        //check if previous row is not valid(top row)
        if(currentNode.getRow() - 1 < 0){
            rowRange[0] = 0;
        }

        //check if next colum is not valid(right column)
        if(currentNode.getCol() + 1 > 14){
            colRange[1] = 0;
        }

        //check if next row is not valid(bottom right)
        if(currentNode.getRow() + 1 > 14){
            rowRange[1] = 0;
        }

        
        //loop through ranges adding each node to closed list
        for(int currentRow = currentNode.getRow() + rowRange[0]; currentRow <= currentNode.getRow() + rowRange[1]; currentRow++){
            for(int currentCol = currentNode.getCol() + colRange[0]; currentCol <= currentNode.getCol() + colRange[1]; currentCol++){
                //add node from nodeArray to openList using nodeArray[currentrow][currentCol]
                //print to see if working properly
                //skips agent 
                if((currentRow != agentCoords[0]) || (currentCol != agentCoords[1])){
                    //check if neighbor node is unpathable 


                    if(nodeArray[currentRow][currentCol].getType() == 1){
                        //neighbor node is not pathable
                       // System.out.println("Unpathable Node found at row "+ currentRow+" col "+ currentCol + " doing nothing");
                    }else if(state[currentRow][currentCol] == 'G'){
                        //start path generation
                        System.out.println(GREEN + "Goal Found, generating path");
                        try{
                            TimeUnit.MILLISECONDS.sleep(750);
                        }catch(Exception ex){
                            System.out.println(ex.getLocalizedMessage());
                        }
                        nodeArray[currentRow][currentCol].setParent(currentNode);
                        generateGoalPath(nodeArray[currentRow][currentCol], state);
                        System.exit(0);
                    }else{
                        //check if neighbor node is in openList
                        if(openList.contains(nodeArray[currentRow][currentCol])){
                            //check to see if we need to update path cost(if we can get there quicker)
                            int newG = nodeArray[currentRow][currentCol].getG();
                            if(newG < nodeArray[currentRow][currentCol].getPrevG()){
                                nodeArray[currentRow][currentCol].setParent(currentNode);
                                openList.remove(nodeArray[currentRow][currentCol]);
                                openList.add(nodeArray[currentRow][currentCol]);
                                if(closedList.contains(nodeArray[currentRow][currentCol])){
                                    closedList.remove(nodeArray[currentRow][currentCol]);
                                }

                            }


                        }else if(!closedList.contains(nodeArray[currentRow][currentCol])){

                            //adjust node path cost depending on orientation to agent
                            if((Math.abs(currentRow - currentNode.getRow()) == 1) && (Math.abs(currentCol - currentNode.getCol()) == 1)){
                                //current neighbor is a diagonal so set path cost to 14
                                nodeArray[currentRow][currentCol].setG(currentNode.getG() + 14);
                            }else{
                                //current neighbor is on row or col of agent so path cost is 10
                                nodeArray[currentRow][currentCol].setG(currentNode.getG() + 10);
                            }
                            
                            //set f cost 
                            nodeArray[currentRow][currentCol].setF();

                            nodeArray[currentRow][currentCol].setParent(currentNode);

                            //add to openList
                            openList.add(nodeArray[currentRow][currentCol]);
                        }

                        //if not check if its in closed list, if it is do nothing if it isn't add to openList
                    }
                    //System.out.println(nodeArray[currentRow][currentCol].toString());
                    //check if neighbor node is goal
                    //if it is see if we need to update parent



                }
            }
        }
    
    }

}

class Node implements Comparable<Node>{
	
	private int row, col, f, g, h, type, previousG;
	private Node parent;
   
	public Node(int r, int c, int t){
		row = r;
		col = c;
		type = t;
        previousG = 0;
		parent = null;
		//type 0 is traverseable, 1 is not
	}
	
	//mutator methods to set values
	public void setF(){
		f = g + h;
	}
	public void setG(int value){
        previousG = g;
		g = value;
	}
	public void setH(int value){
		h = value;
	}
	public void setParent(Node n){
		parent = n;
	}
	
	//accessor methods to get values
	public int getF(){
		return f;
	}
	public int getG(){
		return g;
	}
	public int getH(){
		return h;
	}
	public Node getParent(){
		return parent;
	}
	public int getRow(){
		return row;
	}
	public int getCol(){
		return col;
	}

    public int getType(){
        return type;
    }

    public int getPrevG(){
        return previousG;
    }
	
	public boolean equals(Object in){
		//typecast to Node
		Node n = (Node) in;
		
		return row == n.getRow() && col == n.getCol();
	}
   
	public String toString(){
		return "Node: " + row + "_" + col;
	}

    @Override
    public int compareTo(Node other){
        if(this.f < other.f){
            return -1;
        }else if (this.f > other.f){
            return 1;
        }else{
            return 0;
        }
    }
	
}
