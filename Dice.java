//------------------------------------------
//Written by: Maryan Khalil
// -----------------------------------------

/*Welcome to class dice, this class contains the methods necessary to manipulate the dice in the 3D Warrior 
 * Game and to check for when players obtain doubles*/

import java.util.Random;
public class Dice {//beginning of class 

	// the attributes that represents the data
	private int die1; //integer value of first dice
	private int die2; //integer value of second dice
	
	//Default constructor
	public Dice() 
	{
		die1=die2=0;
	}
	
	//Constructor method
	public Dice(int die1, int die2)
	{
		this.die1=die1;
		this.die2=die2;
	}
	
	//Accessor Methods
	public int getDie1()
	{
		return die1;
	}
	
	public int getDie2()
	{
		return die2;
	}
	
	//RollDice() with simulates the rolling of 2 dices
	public int rollDice()
	{
		Random random=new Random();
		die1=random.nextInt(5)+1; //add 1 from the random number generated restricted from 0 to 5, so that the numbers generate from 1 to 6
		die2=random.nextInt(5)+1;
		return die1+die2;
	}
	
	public boolean isDouble()
	{
		return die1==die2;
	}
	
	public String toString()
	{
		return "Die1: "+die1+" Die2: "+die2;
	}
}
