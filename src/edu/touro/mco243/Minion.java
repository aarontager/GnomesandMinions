package edu.touro.mco243;

import java.util.Queue;

public class Minion extends MinionGnomeBobBase {

	public Minion(String s) {
		super(s);
		gotLunchString = ": Thank you Alice!";
		playString = " is going inside.";
	}

	public void run() {
		try {
			getLunch();
			goToWork();
			waitForMinions();
			play();
			eat();
			goToSleep();
		}
		catch(InterruptedException e) {
		}
	}

	private void waitForMinions() {
		Queue<Minion> temp = Main.minionQueue;
		synchronized(temp) {
			temp.add(this);
			if(temp.size() == 10) {
				Main.minionsDoneWork = true;
				temp.notify();
			}
		}
	}
}
