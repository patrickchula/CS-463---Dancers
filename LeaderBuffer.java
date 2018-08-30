package dancing;

//Brendan Raymond: braymon2
//Richard Wannall: rwannall

public class LeaderBuffer {
	int myBox;	// Stores index of Followers that accept this Buffer's Leader's invitation
	
	// Constructor
	public LeaderBuffer () {
		myBox = 0;
	}
	
	// Called by Followers to accept invitation, and by Leaders to reset value
	public synchronized void setBox (int n) {
		myBox = n;
	}
	
	// Called by Leaders to check if invitation has been accepted
	public synchronized int getBox () {
		return myBox;
	}
	
}
