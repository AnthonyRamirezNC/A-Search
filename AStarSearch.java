import java.lang.Math;
import java.util.Scanner;
import java.util.InputMismatchException;


public class AStarSearch{
    //give color using ANSI escape code constants
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String WHITE = "\033[0;37m";   // WHITE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static Scanner input = new Scanner(System.in);




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
        //randomly block 10%(25) paths
        while(blockPathCount < 25){
            int randomInt = (int)(Math.random() * 225);
            int randCol = randomInt % 15;
            int randRow = randomInt / 15;
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
                }else{
                    System.out.print(WHITE + entry + " ");
                }
                //add more checks here if more colors are needed
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
                    System.out.println(startingRow);
                    if((startingRow < 0) || (startingRow > 14)){
                        System.out.println(RED + "Invalid starting row, needs to be between 0-14");
                    }

                    else break;
                }
                catch(InputMismatchException ex){
                    System.out.println(RED + "Invalid Input");
                    input.nextLine();
                }catch(Exception ex){
                    System.out.println(RED + "NEED TO ADD CATCH FOR " + ex.getLocalizedMessage());
                }
            }
            while(true){
                try{
                    System.out.println(WHITE + "Please input starting column of agent (0-14): ");
                    startingCol = input.nextInt();
                    if((startingCol < 0) || (startingCol > 14)){
                        System.out.println(RED + "Invalid starting column, needs to be between 0-14");
                    }else break;
                }
                catch(InputMismatchException ex){
                    System.out.println(RED + "Invalid Input");
                    input.nextLine();
                }catch(Exception ex){
                    System.out.println(RED + "NEED TO ADD CATCH FOR " + ex.getLocalizedMessage());
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
                    if((goalRow < 0) || (goalRow > 14)){
                        System.out.println(RED + "Invalid goal row, needs to be between 0-14");
                    }
                    else break;
                }
                catch(InputMismatchException ex){
                    System.out.println(RED + "Invalid Input");
                    input.nextLine();
                }catch(Exception ex){
                    System.out.println(RED + "NEED TO ADD CATCH FOR " + ex.getLocalizedMessage());
                }
            }
            while(true){
                try{
                    System.out.println(WHITE + "Please input cdolumn of goal (0-14): ");
                    goalCol = input.nextInt();
                    if((goalCol < 0) || (goalCol > 14)){
                        System.out.println(RED + "Invalid goal column, needs to be between 0-14");
                    }else break;
                }
                catch(InputMismatchException ex){
                    System.out.println(RED + "Invalid Input");
                    input.nextLine();
                }catch(Exception ex){
                    System.out.println(RED + "NEED TO ADD CATCH FOR " + ex.getLocalizedMessage());
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

    public static int calculateHeuristic(Node ){
        //calculate heuristic value using manhatten method

    }

}