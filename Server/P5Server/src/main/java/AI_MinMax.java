import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This class is used to read in a state of a tic tac toe board. It creates a MinMax object and passes the state to it. What returns is a list 
 * of possible moves for the player X that have been given min/max values by the method findMoves. The moves that can result in a win or a 
 * tie for X are printed out with the method printBestMoves()
 * 
 * @author Mark Hallenbeck
 *
 * Copyright© 2014, Mark Hallenbeck, All Rights Reservered.
 *
 */
public class AI_MinMax {
	
	private String[] init_board;
	
	private ArrayList<Node> movesList;
	
	AI_MinMax(String x)
	{
		init_board = getBoard(x);
		
		if(init_board.length != 9)
		{
			System.out.println("You have entered an invalid state for tic tac toe, exiting......");
			System.exit(-1);
		}
		
		MinMax sendIn_InitState = new MinMax(init_board);
		
		movesList = sendIn_InitState.findMoves();
		
		printBestMoves();
	}
	
	/**
	 * reads in a string from user and parses the individual letters into a string array
	 * @return String[]
	 */
	private String[] getBoard(String x)
	{
			String puzzle;
			String[] puzzleParsed;
			String delim = "[ ]+";
			
			//give input message
			
			Scanner userInput = new Scanner(x);		//open scanner
			
			puzzle = userInput.nextLine();					//scan in string
			
			puzzleParsed = puzzle.split(delim);
			userInput.close();   	  						//close scanner
			
			return puzzleParsed;
			
	}
	
	/**
	 * goes through a node list and prints out the moves with the best result for player X
	 * checks the min/max function of each state and only recomends a path that leads to a win or tie
	 */
	private void printBestMoves()
	{
		System.out.print("\n\nThe moves list is: < ");
		
		for(int x = 0; x < movesList.size(); x++)
		{
			Node temp = movesList.get(x);
			
			if(temp.getMinMax() == 10 || temp.getMinMax() == 0)
			{
				System.out.print(temp.getMovedTo() + " ");
			}
		}
		
		System.out.print(">");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	
	public int getEasyMove() {
		
		if (movesList.size() != 0) {
		return movesList.get(0).getMovedTo();
		
		}
		
		else {
			return -1;
		}
	}
	
	
	
	public int getNextMove(){

	  

        MinMax sendIn_InitState = new MinMax(init_board);

        movesList = sendIn_InitState.findMoves();
        int movedTo = -1;

        boolean hasTie = false;

        
        for (Node move : movesList){
            if (move.getMinMax() == 10){
                movedTo = move.getMovedTo();
                break;
            }
            else if (move.getMinMax() == 0){
                movedTo = move.getMovedTo();
                hasTie = true;
            }
            else {
                if (!hasTie){
                    movedTo = move.getMovedTo();
                }
            }
        }

        System.out.println("My next move is : " + movedTo);
       return movedTo;
    }
	
	public int getRandomNumber() {

		Random r = new Random();
		
		for(int x = 0; x < movesList.size(); x++)
		{
			Node temp = movesList.get(r.nextInt(movesList.size()));
			
			if (movesList.size() == 1) {
				temp = movesList.get(0);
			}
			
			if(temp.getMinMax() == 10 || temp.getMinMax() == 0)
			{
				return temp.getMovedTo();
			}
		}
		
		return getNextMove();
	}
	
	

}
