package edu.touro.mco243;

import java.util.Queue;
import java.util.Random;

public class Alice extends Thread {
	private String name;
	public boolean dinnerReady, isReading;
	Random rand = new Random();

	public Alice() {
		super("Alice");
		name = "Alice";
	}

	public void run() {
		try {
			makeMinionLunches();
			makeGnomeLunches();
			wakeBob();
			waitForMinions();
			waitForGnomes();
			notifyBob();
			makeDinner();
			waitForAllSleep();
			goToSleep();
		}
		catch(InterruptedException e) {
		}
	}

	private void makeMinionLunches() throws InterruptedException {
		for(int i = 0; i < 10; i++) {
			Minion temp = Main.minions[i];
			synchronized(temp) {
				System.out.println(name + " making " + temp.name + " lunch.");
				temp.gotLunch = true;
				temp.notify();
				temp.wait();
			}
		}
	}

	private void makeGnomeLunches() throws InterruptedException {
		for(int i = 0; i < 7; i++) {
			Gnome temp = Main.gnomes[i];
			synchronized(temp) {
				System.out.println(name + " making " + temp.name + " lunch.");
				temp.gotLunch = true;
				temp.notify();
				temp.wait();
			}
		}
	}

	private void wakeBob() throws InterruptedException {
		Bob temp = Main.bob;
		synchronized(temp) {
			System.out.println(name + " making " + temp.name + " lunch.");
			temp.gotLunch = true;
			temp.notify();
			temp.wait();
		}
	}

	private void waitForMinions() throws InterruptedException {
		Queue<Minion> temp = Main.minionQueue;
		synchronized(temp) {
			while(!Main.minionsDoneWork) {
				temp.wait();
			}
			System.out.println(name + " heard a knock at the door.");
			while(temp.peek() != null) {
				Minion minion = temp.poll();
				synchronized(minion) {
					minion.insideTurn = true;
					minion.notify();
					minion.wait();
				}
				Main.minionsInside = true;
			}
		}
	}

	private void waitForGnomes() throws InterruptedException {
		Queue<Gnome> temp = Main.gnomeQueue;
		synchronized(temp) {
			while(!Main.gnomesDoneWork) {
				temp.wait();
			}
			System.out.println(name + " heard a knock at the door.");
			while(temp.peek() != null) {
				Gnome gnome = temp.poll();
				synchronized(gnome) {
					gnome.insideTurn = true;
					gnome.notify();
					gnome.wait();
				}
				Main.gnomesInside = true;
			}
		}
	}

	private void notifyBob() {
		synchronized(Main.bob) {
			Main.bob.insideTurn = true;
			Main.bob.notify();
		}
	}

	private void makeDinner() throws InterruptedException {
		Thread.sleep((int) (rand.nextDouble() * 1000));
		System.out.println(name + " made dinner.");
		synchronized(this) {
			dinnerReady = true;
			this.notifyAll();
		}
	}

	private void waitForAllSleep() {
		while(Thread.activeCount() > 4) ;
		System.out.println(name+" is going to read.");
		isReading = true;
		synchronized(Main.bob) {
			Main.bob.sleepMonitor = true;
			Main.bob.notify();
		}
	}

	protected void goToSleep() throws InterruptedException {
		Thread.sleep((int) (rand.nextDouble() * 2000));
		System.out.println(name + " fell asleep.");
	}
}
