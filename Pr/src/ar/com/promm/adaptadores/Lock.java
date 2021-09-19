package ar.com.promm.adaptadores;

import ar.com.promm.enums.Enums.STATE;

public class Lock {
	
	
	private STATE state;
	
	public Lock() {
		this.state=STATE.IDLE;
	}
	
	public synchronized STATE getState() {
		return this.state;
	}
	
	public synchronized void setState(STATE s) {
		System.out.println("CHANGING STATE TO: "+s.toString());
		this.state=s;
	}

}
