package edu.touro.mco243;

import java.util.Queue;

public class Gnome extends MinionGnomeBobBase {

	public Gnome(String s) {
		super(s);
		gotLunchString = ": Have a good day!";
		playString = " is playing outside.";
	}

	public void run() {
		try {
			getLunch();
			goToWork();
			waitForGnomes();
			play();
			eat();
			useBathroom();
			goToSleep();
		}
		catch(InterruptedException e) {
		}
	}

	private void waitForGnomes() {
		Queue<Gnome> temp = Main.gnomeQueue;
		synchronized(temp) {
			temp.add(this);
			if(temp.size() == 7) {
				Main.gnomesDoneWork = true;
				temp.notify();
			}
		}
	}

	private void useBathroom() throws InterruptedException {
		try {
			Main.bathroom.acquire();
			System.out.println(name + " is using the bathroom.");
			Thread.sleep((int) (rand.nextDouble() * 1500));
			System.out.println(name + " left the bathroom.");
			Main.bathroom.release();
		}
		catch(InterruptedException e) {
			Main.bathroom.release();
			throw e;
		}
	}
}
