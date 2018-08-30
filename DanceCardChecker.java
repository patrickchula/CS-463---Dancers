package dancing;

//Richard Wannall: rwannall

import java.util.Scanner;

public class DanceCardChecker {
	public static void main (String[] args) {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Input N first, then M:");
		
		int n = in.nextInt();	// Number of Leaders
		int m = in.nextInt();	// Number of Followers
		in.close();
		
		System.out.println("Leaders:   " + n);
		System.out.println("Followers: " + m + "\n");
		
		CheckBuffer cb = new CheckBuffer();	// Manages interactions between Checker and Dancers
		
		LeaderBuffer[]   leaderBox   = new LeaderBuffer[n];	// Stores responses from Followers
		FollowerBuffer[] followerBox = new FollowerBuffer[m];	// Stores invitations from Leaders
		
		// Initialize Buffers (possibly unnecessary)
		for (int k = 0; k < m; k++)
			followerBox[k] = new FollowerBuffer();
		for (int k = 0; k < n; k++)
			leaderBox[k]   = new LeaderBuffer();
		
		// Thread that manages CheckBuffer
		Checker mainChecker = new Checker(n, m, cb, followerBox, leaderBox);
		
		Follower[] followers = new Follower[m];	// Array used to store Followers
		Leader[]   leaders   = new Leader[n];	// Array used to store Leaders
		
		// Initialize Dancer Threads
		for (int i = 0; i < m; i++)
			followers[i] = new Follower(i, n, followerBox[i], leaderBox, mainChecker);
		for (int j = 0; j < n; j++)
			leaders[j] = new Leader(j, m, leaderBox[j], followerBox, cb);
		
		// Wait until all other threads have ended
		while (cb.getCount() < (n + 1)) {}
		
		// Print Dance Cards
		for (int j = 0; j < n; j++)
		{
			leaders[j].printCard();
			System.out.println();
		}
		System.exit(0);
	}
	
}
