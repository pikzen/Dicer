package net.pikzen.rtd;

public class Dice {
	private int numRolls;
	private int maxRoll;
	
	public int getNumRolls() { return numRolls; }
	public int getMaxRoll() { return maxRoll; }
	
	public Dice(int n, int max)
	{
		numRolls = n;
		maxRoll = max;
	}
}
