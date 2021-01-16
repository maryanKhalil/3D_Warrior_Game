//------------------------------------------
//Written by: Maryan Khalil
// -----------------------------------------

/* Welcome to Nancy's warrior game! Here we will be programming an interactive 
 * video game which consists of two players racing their way to the top of
 * a board. The board is consisted of multiple levels and a size that
 * the user will select. There will be two dices that the players will roll
 * at each turn to advance their location on the game. When a player reaches the 
 * last location on a level, he will be automatically redirected to the next level
 * and continuing his path. Each player will commence with 10 energy units which will
 * allow him to move. Landing on certain squares will add or remove energy units.
 * .In addition, landing on an already occupied square will give you the possibility
 * to challenge the player there and potentially gain half of that person's energy units.
 * Whenever a player lands a double, his energy units go up by two.The minimal energy requirement is one energy unit, if a player
 * does not fulfill this requirement, then he is too weak to move.   */

import java.util.Scanner;// importing scanner class for user input 
import java.util.Random;//importing random class 

public class LetUsPlay {// Begginig of class player
	static Board playerBoard;// Creating a static board object

	public static void main(String[] args) {

		//print out Header with a series of print statements
		System.out.println("\t\t**************************************************\n"
				+ "\t\t--------------------------------------------------\n" + "\t\t*" + String.format("%50s", " *\n")
				+ String.format("%-8s%5s%8s", "\t\t*", "\tWELCOME to The 3D Warrior Game!", " *\n") + "\t\t*"
				+ String.format("%50s", " *\n") + "\t\t**************************************************\n"
				+ "\t\t--------------------------------------------------\n");
			
				
				//print out game board level and size requirements
				System.out.println("The default game  board has 3 levels and each level has "
						+ "a 4x4 board");
				System.out.println("You can use this default board size or change the size");
				System.out.println("\t 0 to use the default board size \n\t -1 to enter your own"
						+ "size");
				
				
				//Initialization and variable declaration
				int die1=0;
				int die2=0;
				int boardlevels=0;
				int boardtype=0;
				int boardsize=0;
				int randomnum=0;
				int dicesum=0; 
				int countdefnrg=0; //counter for deficient energy
				int countturn=0;
			
				
				//initialize Scanner object
				Scanner keyboard=new Scanner(System.in);
				
				//prompt user to use default or custom board & determine if user input is valid using 
				//a do while loop and a print statement
				System.out.print("==> What do you want to do? ");
				do {
					boardtype=keyboard.nextInt();
					if (boardtype!=0 && boardtype!=-1)
						System.out.println("Sorry but "+boardtype+" is not a legal choice");
				} while (boardtype!=0 && boardtype!=-1);
				
				//prompt user for number of levels and validate if user level input is correct
				
				if (boardtype==-1)
				System.out.print("How many levels would you like? (minimum size 3, max 10) ");
			
				while((boardlevels<3 || boardlevels>10) && boardtype==-1) {
					boardlevels=keyboard.nextInt();
					if (boardlevels<3 || boardlevels>10)
						System.out.println("Sorry but "+boardlevels+" is not a legal choice");
				}
				System.out.println();
				//prompt user for board size and determine if size is valid using previous steps
				if (boardtype==-1)
				{
				System.out.println("What size do you want the nxn boards on each level to be?");
				System.out.println("Minimum size is 3x3,max is 10x10.");
				System.out.print("==> Enter the value of n: ");
				}
				
				while ((boardsize<3||boardsize>10) && boardtype==-1)
				{
					boardsize=keyboard.nextInt();
					if (boardsize<3||boardsize>10)
						System.out.println("Sorry but "+boardlevels+" is not a legal choice");
				}
				
				
				//Object Declaration
				Dice dice=new Dice(die1,die2);
				Board board = null;
				String username0=null;
				String username1=null;
				Random random=new Random();
				
				
				//create default or custom board according to user's preference
				if (boardtype==0)
					board=new Board();
				else if (boardtype==-1)
					board=new Board(boardlevels,boardsize);
			
				System.out.println("Your 3D board has been set up and looks like this:");
				System.out.println();
				
				//print out the board while implicitly calling the toString()
				System.out.print(board);
				
				//generate random player using a random integer
				randomnum=random.nextInt(2);
				
				//prompt player names and storing them in an array using a for loop
				
				//create an array further containing player names
				Player []playernames=new Player[2];
				
				//prompt player names and storing them in an array of players using a for loop
				for (int i=0;i<playernames.length;i++)
				{
					System.out.print("What is player "+i+"'s name (one word only): ");
					playernames[i]=new Player(keyboard.next());
				}
				//we now have two players in []playernames
				
				//create new Player objects with the same reference to those in the playernames[] respectively
				Player playerturn=playernames[0];
				Player otherp=playernames[1];
				
				//create two booleans two determine if one of the players are are at the last location
				boolean p0haswon=(playerturn.getLevel()==board.getLevel() && playerturn.getX()==board.getSize() && playerturn.getY()==board.getSize());
				boolean p1haswon=(otherp.getLevel()==board.getLevel() && otherp.getX()==board.getSize() && otherp.getY()==board.getSize());
				//boolean to check if the dice roll is a double
				boolean rolldouble= die1==die2;
				//boolean for the end of the turn
				boolean endofturn=false;
				
				//swap the p0 and p1 and always make the first index of the playernames array to start, so that the turns of the players alternate
				//if randumnum is 1,then we swap the two players
				if (randomnum==1)
				Player.swapTwoPlayers(playerturn, otherp);
				
				//start the game
						System.out.println();
						System.out.println("The game has started "+playernames[0].getName()+" goes first"); //the player will always be the player of index 0
						System.out.println("==========================");
			
						
						
				while (!p0haswon)
				{
					//each player's turn
					do {
							
						//integer variables for calculations of new location
						int ansX,ansY,anslvl,newY,newX,startx,starty,startlvl,finalx,finaly,storex,storey;
						ansX=ansY=anslvl=newY=newX=finalx=finaly=storex=storey=0;
						
						if (playerturn.getEnergy()<=0) //if player has <=0 energy units, he is granted 3 dice rolls
						{
							while (countdefnrg<3)
							{
							System.out.println("It is "+playerturn.getName()+"'s turn");
							dice.rollDice();
							System.out.println("\t"+playerturn.getName()+" you rolled "+dice);
							if (dice.isDouble())
							{
								System.out.println("\tCongratulations you rolled double "+dice.getDie1()+". Your energy went"
										+ " up by 2 units");
								playerturn.setEnergy(playerturn.getEnergy()+2);
								countdefnrg++;
							}
							else
								break;
							
							}//end of while loop
						} //end of if statement
						else 
						{ //if the player has sufficient amount of energy units, then he rolls the dice once
							System.out.println("It is "+playerturn.getName()+"'s turn");
							dice.rollDice();
							System.out.println("\t"+playerturn.getName()+" you rolled "+dice);
							if (dice.isDouble())
							{
								System.out.println("\tCongratulations you rolled double "+dice.getDie1()+". Your energy went"
										+ " up by 2 units");
								playerturn.setEnergy(playerturn.getEnergy()+2);
							}
							
						} //end of else 
						dicesum=dice.getDie1()+dice.getDie2();
						startx=playerturn.getX();
						starty=playerturn.getY();
						startlvl=playerturn.getLevel();
						ansX=playerturn.getX()+dicesum/board.getSize();
						ansY=playerturn.getY()+dicesum%board.getSize();
						newY=ansY%board.getSize();
						newX=ansX%board.getSize();
						anslvl=playerturn.getLevel();
						
						///////////////////////////////////////////////////
						///////////////////////////////////////////////////
						
						if (playerturn.getLevel()==(board.getLevel()-1) && playerturn.getX()==(board.getSize()-1) && 
								playerturn.getY()==board.getSize()-2)
						{		
							System.out.println("!!! Sorry you need to stay where you are - "
									+ "that throw takes you off the grid and you loose 2 units"
									+ " of energy.");
							ansX=playerturn.getX()-(dicesum+1)/board.getSize(); /*we substract the current x location by dicesum+1/boardsize to determine how the 
							next position is shifted vertically from the the current position and since the current y is 1 less than the max coordinate, 
							so we add 1 to dicesum to balance the vertical change per size [(diceroll+1)/board.getSize()]
							*/
							ansY=playerturn.getY()-dicesum%board.getSize();
							newY=board.getSize()+ansY;
							newX=board.getSize()+ansX;
							//if x and y do not go out of the grid, then you we set x and y to ansX and ansY respectively
							playerturn.setX(ansX);
							playerturn.setY(ansY);
							//the calculation variables need to be revisited, so that they make the player go backwards
							
							if (ansY<0)
							{
								playerturn.setY(newY);
							}
							if (ansX<0)
							{
								playerturn.setX(newX);
							}
						}else
						{
						////////////////////////////////////////////////////////////
						////////////////////////////////////////////////////////////
						//end of calculations if player is at the before last square of the board
						
						playerturn.setX(ansX);
						playerturn.setY(ansY);
						playerturn.setLevel(anslvl);
					
						if (ansY>=board.getSize()) //check if y is out of bounds  and x is valid in the board
						{
							playerturn.setY(newY); //set p0 to new y location
							//adjust x according to y
							ansX=ansX+(ansY/board.getSize());
							playerturn.setX(ansX);
						}
						
						if (ansX>=board.getSize()) //check if x is out of bound y is valid
						{
							newX=ansX%board.getSize();
							playerturn.setX(newX); //new x location
				
							playerturn.setLevel(playerturn.getLevel()+1);
						}
						}
						
						
						
						if (playerturn.getLevel()>=board.getLevel())
						{
							playerturn.setX(startx);
							playerturn.setY(starty);
							playerturn.setLevel(startlvl);
							playerturn.setEnergy(playerturn.getEnergy()-2);
						}
						
						
						/* after all position calculations, we need to determine if the newly calculated
						 * position is not already taken by the other player. 				 */
						
						if (playerturn.equals(otherp)) //if they are at the same location
						{
							System.out.println(otherp.getName()+" is at your new location");
							System.out.println("What do you want to do?");
							System.out.println("\t0 - Challenge and risk loosing 50% of your energy units if you"
									+ " loose or move to new location and get 50% of other players energy units");
							System.out.println("\t1 - to move down one level or move to (o,o) if at level"
									+ " 0 and lose 2 energy");
							System.out.println("units");
							int userinput=0; //initialize userinput to 0
							do {
								userinput=keyboard.nextInt();
								if (userinput!=0&&userinput!=1)
									System.out.println("Sorry but "+userinput+" is not a legal choice");
							}while(userinput!=0 && userinput!=1);
							//challenge the opponent
							if (userinput==0)
							{
								int winnerdecider=0;
								winnerdecider=random.nextInt(10);
								if (winnerdecider<6) //player loses the challenge
								{
									
									System.out.println("\tUnfortunately, you lost the challenge...better luck next time!");
									playerturn.setX(startx);
									playerturn.setY(starty);
									playerturn.setLevel(startlvl);
									playerturn.setEnergy(playerturn.getEnergy()/2);
									
								}
								else //player wins
								{
									System.out.println("\tBravo!! You won the challenge.");
									//we make both players change location using the moveTo() method
									playerturn.setX(startx);
									playerturn.setY(starty);
									playerturn.setLevel(startlvl);
									playerturn.moveTo(otherp);
									
									//cut otherp's energy by half
									otherp.setEnergy(otherp.getEnergy()/2);
									playerturn.setEnergy(otherp.getEnergy()+playerturn.getEnergy());
								}
							}
							else if (userinput==1)
							{
								if (playerturn.getLevel()==0)
								{
									playerturn.setX(0);
									playerturn.setY(0);
									playerturn.setEnergy(playerturn.getEnergy()-2);
								}
								else
								{
									playerturn.setLevel(playerturn.getLevel()-1);
									playerturn.setEnergy(playerturn.getEnergy()-2);
								}
							}
							
							
						}
						else //if they are not at the same location
						{
							//set the player's position to that calculated 
							playerturn.setX(playerturn.getX());
							playerturn.setY(playerturn.getY());
							playerturn.setLevel(playerturn.getLevel());
							//adjust energy
							playerturn.setEnergy(playerturn.getEnergy()+
									board.getEnergyAdj(playerturn.getLevel(),
											playerturn.getX(), playerturn.getY()));	
						}
						
						System.out.println("\tYour energy is adjusted by "+board.getEnergyAdj(playerturn.getLevel(),
								playerturn.getX(), playerturn.getY())+" for landing at "
										+ "("+playerturn.getX()+","+playerturn.getY()+
										") at level "+playerturn.getLevel()+"\n");
						
						
						
						//increment countturn for each player's turn
						countturn++;
						if (countturn%2==0)
						{
						System.out.println("At the end of this round:");
						System.out.println("\t"+playerturn.getName()+" is on level "+playerturn.getLevel()
						+" at location ("+playerturn.getX()+","+playerturn.getY()+") and has "
								+playerturn.getEnergy()+" units of energy.");
						System.out.println("\t"+otherp.getName()+" is on level "+otherp.getLevel()
						+" at location ("+otherp.getX()+","+otherp.getY()+") and has "
								+otherp.getEnergy()+" units of energy.");
						System.out.print("Any key to continue to next round ...");
						String randomkey=null;
						randomkey=keyboard.next();
						System.out.println();
						}
						/*end of round, swap the contents of both players, so that the attributes
						 * of playerturn has those of otherp;therefore, otherp will be implicitly
						 * playing as playerturn which will cause an alternation between player turns
						*/
						if (playerturn.won(board))
						{
							endofturn=true;
							p0haswon=true;
						}
						Player.swapTwoPlayers(playerturn, otherp);
						countdefnrg=0;
					}
					while(!endofturn);
					
					Player.swapTwoPlayers(playerturn, otherp);
					System.out.println();
					System.out.println();
					System.out.println("The winner is player "+playerturn.getName()+". Well done!!!");
					p0haswon=true;
					
				} //end of while loop
						
				
				
				
				
			}
		}