package dancing;

//Brendan Raymond: braymon2
//Richard Wannall: rwannall

public class Checker implements Runnable {
	int              n;	// Number of Leaders
	int              m;	// Number of Followers
	CheckBuffer      cb;	// Buffer used by this Checker
	FollowerBuffer[] fb;	// Buffers used by Followers
	LeaderBuffer[]   lb;	// Buffers used by Leaders
	boolean          done;	// False while Leaders are stil running, otherwise True
	
	// Constructor
	public Checker (int n, int m, CheckBuffer cb, FollowerBuffer[] fb, LeaderBuffer[] lb) {
		this.n  = n;
		this.m  = m;
		this.cb = cb;
		this.fb = fb;
		this.lb = lb;
		done = false;
		
		new Thread(this, "Checker").start();
	}
	
	// Run
	public void run () {
		// Loops until all Leaders are done
		int leadersCount = cb.getCount();
		while (leadersCount < n)
			leadersCount = cb.getCount();

		done = true;	// Leaders all done
		
		// Notifies all Followers
		for (int i = 0; i < m; i++)
			fb[i].stopFollower();
		cb.finished();
	}
	
	// Called by Followers and this Checker to determine if all Leaders have finished running
	public boolean isDone () {
		return done;
	}
	
}
