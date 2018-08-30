package dancing;


//Brendan Raymond: braymon2
//Richard Wannall: rwannall

public class CheckBuffer {
	int     count;	// Number of Leaders who have finished inviting Followers.
	boolean ready;	// True if all Threads are ready to go, otherwise False
	
	// Constructor
	public CheckBuffer () {
		count = 0;
		ready = false;
	}
	
	// Called by Leaders when they have completed their run method
	public synchronized void finished () {
		count++;
	}
	
	// Called by Checker while count < 8
	public synchronized int getCount() {
		return count;
	}
	
}
