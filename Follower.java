package dancing;

//Richard Wannall: rwannall

public class Follower implements Runnable {
	int myM;			// Index of this Follower
	int n;			// Number of Leaders
	int[] dances;		// Lists the Leaders this Follower is dancing with
	int[] leaderCount;	// Lists the number of times this Follower is dancing with each Leader
	FollowerBuffer myBox;	// Buffer used by this Follower
	LeaderBuffer[] lBox;	// Buffers used by Leaders
	Checker mainChecker;	// Thread that keeps track of when Leaders have finished
	
	// Constructor
	public Follower (int myM, int n, FollowerBuffer myBox, LeaderBuffer[] lBox, Checker mainChecker){
		
		this.myM         = myM;
		this.n           = n;
		dances	     = new int[8];
		leaderCount	     = new int[n];
		this.myBox       = myBox;
		this.lBox        = lBox;
		this.mainChecker = mainChecker;
		
		new Thread(this, "Follower " + (myM + 1)).start();
	}
	
	// Run
	public void run() {
		//System.out.println("Follower " + (myM + 1) + " Started");
		
		// Runs until Checker determines that all Leaders have finished running
		while (!mainChecker.isDone()) {
			int[] invite = myBox.getInvite();	// First element is dance index, second element is Leader index
			if (dances[invite[0]] == 0) {			// If not currently dancing this dance with anyone
				if (leaderCount[invite[1]] < 2) {	// If currently dancing with this Leader less than twice
					lBox[invite[1]].setBox(myM + 1);		// Add myN to this Leader's Buffer
					dances[invite[0]] = invite[1] + 1;	// Add this Leader to dances
					leaderCount[invite[1]]++;		// Increment number of times dancing with this Leader
				}
			}
			myBox.respond();	// Notify Leader waiting on this Follower
		}
		//System.out.println("Follower " + (myM + 1) + " Finished");
	}
	
}
