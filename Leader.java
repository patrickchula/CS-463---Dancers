package dancing;

//Richard Wannall: rwannall

import java.util.Random;

public class Leader implements Runnable {
	int myN;			// Index of this Leader
	int m;			// Number of Followers
	int currentDance;		// Index of dance for which a Partner is currently being searched
	int[] dances;		// Lists the Followers this Leader is dancing with
	int[] followers;		// Lists the number of times this Leader is dancing with each Follower
	LeaderBuffer myBox;	// Buffer used by this Leader
	FollowerBuffer[] fBox;	// Buffers used by Followers
	CheckBuffer cb;
	
	// Constructor
	public Leader (int myN, int m, LeaderBuffer myBox, FollowerBuffer[] fBox, CheckBuffer cb){
		Random rnd = new Random();
		
		this.myN     = myN;
		this.m       = m;
		this.cb      = cb;
		currentDance = rnd.nextInt(8);
		dances       = new int[8];
		followers    = new int[m];
		this.myBox = myBox;
		this.fBox = fBox;

		int val = rnd.nextInt(m);
		int i   = 0;
		
		// List of Followers to check, with randomized start
		while (i < m){
			followers[i] = val;
			if (val < m - 1)
				val++;
			else
				val = 0;
			i++;
		}
		
		new Thread(this, "Leader " + (myN + 1)).start(); // this leader's thread
	}
	
	// Run
	public void run() {
		//System.out.println("Leader  " + (myN + 1) + " Started");
		
		for (int i = 0; i < 8; i++){		// Loop through each dance
			for (int j = 0; j < m; j++){	// Check each Follower
				// Check if Follower j is available, send invite if yes, otherwise skip
				if (fBox[followers[j]].isAvailable())
					fBox[followers[j]].sendInvite(currentDance, myN);
				// Record accepted invitation, runs only if sendInvite was called and Follower accessed myBox
				if (myBox.getBox() != 0) {
					dances[currentDance] = myBox.getBox();
					myBox.setBox(0);	// Reset myBox
					break; // No longer need to search for Partners for this dance
				}
			}
			// Increment dance index being checked
			if (currentDance < 7)
				currentDance++;
			else
				currentDance = 0;
		}
		
		cb.finished();	// Increments count value in CheckBuffer, used to determine when all Leaders have finished
		//System.out.println("Leader  " + (myN + 1) + " Finished");
	}
	
	public void printCard() {
		String waltz;
		String tango;
		String foxtrot;
		String quickstep;
		String rumba;
		String samba;
		String chacha;
		String jive;
		
		if(dances[0] == 0)
			waltz = "------";
		else
			waltz = "with " + dances[0];
		if(dances[1] == 0)
			tango = "------";
		else
			tango = "with " + dances[1];
		if(dances[2] == 0)
			foxtrot = "------";
		else
			foxtrot = "with " + dances[2];
		if(dances[3] == 0)
			quickstep = "------";
		else
			quickstep = "with " + dances[3];
		if(dances[4] == 0)
			rumba = "------";
		else
			rumba = "with " + dances[4];
		if(dances[5] == 0)
			samba = "------";
		else
			samba = "with " + dances[5];
		if(dances[6] == 0)
			chacha = "------";
		else
			chacha = "with " + dances[6];
		if(dances[7] == 0)
			jive = "------";
		else
			jive = "with " + dances[7];
		
			
		System.out.println("Leader " + (myN + 1) + ":");
		System.out.println("Waltz      " + waltz);
		System.out.println("Tango      " + tango);
		System.out.println("Foxtrot    " + foxtrot);
		System.out.println("Quickstep  " + quickstep);
		System.out.println("Rumba      " + rumba);
		System.out.println("Samba      " + samba);
		System.out.println("Cha Cha    " + chacha);
		System.out.println("Jive       " + jive);
	}
	
}
