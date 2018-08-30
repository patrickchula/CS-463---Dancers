package dancing;

//Brendan Raymond: braymon2
//Richard Wannall: rwannall

public class FollowerBuffer {
	boolean available;	// Indicates if an invitation has been sent to this Buffer
	int dance;	// Index of dance invited to
	int leader;	// Index of Leader sending the invitation
	
	// Contructor
	public FollowerBuffer () {
		available = true;
		dance  = 0;
		leader = 0;
	}
	
	// Called by Leader when available = true to initiate invitation process
	public synchronized void sendInvite(int d, int l) {
		available = false;
		
		dance  = d;
		leader = l;
		
		// Notify Follower in charge of this Buffer
		notify();
		
		// Wait until Follower in charge of this Buffer has responded
		if (!available) {
			try {
				wait();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Called by Follower, when notified by Leader, to recieve incoming invitation
	public synchronized int[] getInvite() {
		
		// Wait while available = true
		if (available) {
			try {
				wait();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		int[] invite = {dance, leader};
		dance = 0;	// Reset dance index
		leader = 0;	// Reset leader index
		return invite;
	}
	
	// Called by Follower once it has decided to accept or reject invitation
	public synchronized void respond () {
		available = true;
		notify();
	}
	
	// Returns True if this Buffer is available, otherwise False
	public synchronized boolean isAvailable () {
		return available;
	}
	
	// Called by Checker on each FollowerBuffer to notify all Followers that all Leaders have finished running
	public synchronized void stopFollower () {
		notify();
	}
	
}
