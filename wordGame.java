/*
	Who worked on what:
	Adam  --> printGrid, userInput, updateGrid, printWrong, addWrong
	Oscar --> generateRandomGrid, pickWords, checkInputWord
	Both  --> main
*/

package main; //might need to comment out for __PEER REVIEW__ if your not using the Eclipse IDE because this is required for the IDE to run

import java.util.Random;
//imports
import java.util.Scanner;

public class wordGame {
	//globals
	public static final String[] words = { "Also", "Able", "Acid", "Aged", "Away", "Baby", "Back", "Bank", "Been", "Ball", "Base", "Busy", "Bend", "Bell", "Bird", "Come", "Came", "Calm", "Card", "Coat", "City", "Chat", "Cash", "Crow", "Cook", "Cool", "Dark", "Each", "Evil", "Even", "Ever", "Face", "Fact", "Four", "Five", "Fair", "Feel", "Fell", "Fire", "Fine", "Fish", "Game", "Gone", "Gold", "Girl", "Have", "Hair", "Here", "Hear", "Into", "Iron", "Jump", "Kick", "Kill", "Life", "Like", "Love", "Main", "Move", "Meet", "More", "Nose", "Near", "Open", "Only", "Push", "Pull", "Sell", "Sale" };
	public static final int gridRNum = 9;
	public static final int gridCNum = 9;
	public static char[][] grid = {
			{'-', '-', '-', '-', '-', '-', '-', '-', '-'},
			{'|', ' ', '|', ' ', '|', ' ', '|', ' ', '|'},
			{'-', '-', '-', '-', '-', '-', '-', '-', '-'},
			{'|', ' ', '|', ' ', '|', ' ', '|', ' ', '|'},
			{'-', '-', '-', '-', '-', '-', '-', '-', '-'},
			{'|', ' ', '|', ' ', '|', ' ', '|', ' ', '|'},
			{'-', '-', '-', '-', '-', '-', '-', '-', '-'},
			{'|', ' ', '|', ' ', '|', ' ', '|', ' ', '|'},
			{'-', '-', '-', '-', '-', '-', '-', '-', '-'}
	};
	
	public static void main(String[] args) {
		//initialize the grid
		String[] outputForGeneration = pickWords(wordGame.words);
		String[] output = outputForGeneration.clone();
		generateRandomGrid(wordGame.grid, outputForGeneration);
		
		//create game
		Scanner scanner = new Scanner(System.in);
	    int incorrectTurns = 3;
	    int correct = 0;
		String[] wrongInputs = {"", "", ""};
	    //play until turns run out or you guess all 4 correctly
		printGrid(wordGame.grid);
		System.out.println("You ownly have 3 wrong attempts!!!");
		System.out.println("You can input up to 4 words in 1 line but each Word counts as an attempt!!!");
		while(incorrectTurns >= 0) {
			//correct answers for testing
			System.out.println();
			System.out.print("Correct Words: ");
			System.out.print(output[0] + " ");
			System.out.print(output[1] + " ");
			System.out.print(output[2] + " ");
			System.out.println(output[3]);
			System.out.println();
			
			//prints wrong words
			if(incorrectTurns != 3)
			{
				printWrong(wrongInputs);
			}
			
			//gets players input
	        String[] input = userInput(scanner);
	        
	        //correct or wrong
	        for(int i = 0; i < input.length; i++) {
	        	if(input[i] == "_") {
	        		continue;
	        	} else if(checkInputWord(input[i], output)) {
	        		updateGrid(wordGame.grid, input[i]);
	        		printGrid(wordGame.grid);
	        		correct++;
	        		System.out.println(correct + " correct words!");
	        	} else {
	        		addWrong(wrongInputs, input[i]);
	        		printGrid(wordGame.grid);
	        		incorrectTurns--;
	        		System.out.println("Incorrect word!");
	        		System.out.println("Remaining wrong attempts: " + incorrectTurns);
	        	}
	        }
	        
	        //if win, exit
	        if (correct == 4) {
	            System.out.println("You Win!");
	            scanner.close();
	            System.exit(0);
	        }
		}
		
		scanner.close();
		System.out.println("You Lose!");
	}
	
	//functions
	//makes a random grid
		public static void generateRandomGrid(char[][] s, String[] mainOutput) {
			
		    //copy of expected output
		    String[] output = mainOutput;
		   

		    //places random letter from one of the words into the grid
		    Random rand = new Random();
		    
		    for (int i = 1; i < gridRNum - 1; i += 2)   
		    {
		        for (int j = 1; j < gridCNum - 1; j += 2)
		        {
		            char curr = '-';
		            while (curr == '-') {
		                int sIndex = rand.nextInt(4);
		                int cIndex = rand.nextInt(4);
		                curr = output[sIndex].charAt(cIndex);
		                //once used, replace with '-' so its not used again
		                if (curr != '-') {
		                    s[i][j] = curr;
		                    StringBuilder newOutput = new StringBuilder(output[sIndex]);
		                    newOutput.setCharAt(cIndex, '-');
		                    output[sIndex] = String.valueOf(newOutput); 
		                }
		            }
		        }
		    }
		}

		//picks random words from the list to be the expected output
		public static String[] pickWords(String[] words) {
			
			Random rand = new Random();
		    String[] output = new String[4];
		    for (int i = 0; i < 4; i++) {
		        output[i] = words[rand.nextInt(68)+1];
		    }
		    return output;
		}

		//checks if the user input == one of the expected outputs
		public static boolean checkInputWord(String input, String[] output) {
			for (int i = 0; i < 4; i++)
		    {
		        if (input.equals(output[i])) {
		            output[i] = "-";
		            return true;
		        }      
		    }
		    return false;
		}
	
	//prints the game grid passed to it
	public static void printGrid(final char[][] arr) {
		for(int i = 0; i < 10; i++) { System.out.println(); } //prints out 10 blank lines for Style
		
		for(int i = 0; i < wordGame.gridRNum; i++) {
			for(int j = 0; j < wordGame.gridCNum; j++) {
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}
	}
	
	//updates game grid by removing letters from correct word guess
	public static void updateGrid(char[][] s, final String input) {
		for(int strC = 0; strC < input.length(); strC++)
		{
			for (int i = 0; i < gridRNum; i++)
			{
				for (int j = 0; j < gridCNum; j++)
				{
					if(s[i][j] == input.charAt(strC))
					{
						s[i][j] = ' ';
						i = gridRNum;
						j = gridCNum;
					}
				}
			}
		}
	}
	
	//saves wrong guess word for printing
	public static void addWrong(String[] sArr, final String wrong) {
		for(int i = 0; i < 3; i++) {
			if(sArr[i] == "") {
				sArr[i] = wrong;
				break;
			}
		}
	}
	
	//prints a list of wrong guesses
	public static void printWrong(final String[] sArr) {
		System.out.print("Previous wrong words: ");
		for(int i = 0; i < 3; i++) {
			System.out.print(sArr[i] + " ");
		}
		System.out.println();
	}
	
	//gets users inputs for game and returns it in a String array length of 4
	public static String[] userInput(Scanner sc) {
		String temp = "";
		String[] wordList = {"_", "_", "_", "_"};
		
		boolean isWord = true; //used to check if words are proper words
		boolean running = true;
		boolean isFourL = true; //used to make sure the words are length of 4
		int curS = 0;
		int prevS = 0;
		String holder = "";
		while(running) {
			try {
				wordList[0] = "_";
				wordList[1] = "_";
				wordList[2] = "_";
				wordList[3] = "_";
				
				System.out.print("Enter up to four 4 letter word: ");
				temp = sc.nextLine();
				if(temp.length() > 19) { //only allows up to 4 words with length of 4 and 3 spaces
					System.out.println("more than 4 words entered!!!");
					continue;
				}
				
				isWord = true;
				isFourL = true;
				curS = 0;
				prevS = 0;
				
				//puts inputs into wordList
				for(int i = 0; i < wordList.length; i++) {
					curS = temp.indexOf(' ', curS+1);
					
					if(prevS == 0 && curS == -1) { //first
						holder = temp.substring(prevS);
					} else if(prevS == 0) { //first but more
						holder = temp.substring(prevS, curS);
					} else if(curS != -1) { //middle
						holder = temp.substring(prevS+1, curS);
					} else { //everything else
						holder = temp.substring(prevS+1);
					}
					
					//makes sure that words are length of 4
					if(holder.length() != 4)
					{
						isFourL = false;
						break;
					}
					
					//checks for bad char's
					for(int j = 0; j < holder.length(); j++) {
						if(!Character.isAlphabetic(holder.charAt(j))) {
							isWord = false;
							break;
						}
					}
					
					wordList[i] = holder;
					
					prevS = curS;
					
					if(prevS == -1) {
						break;
					}
				}
				
				//if input is acceptable then done
				if(isWord && isFourL) {
					running = false;
				} else {
					System.out.println("Not a good input!!!");
					System.out.println("Please enter up to four 4 letter words");
				}
				
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("!!!Error with inputted word please enter again!!!");
			}
		}
		
		return wordList;
	}
}
