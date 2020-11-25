package edu.touro.mco243;

import java.util.Random;

public class MinionGnomeBobBase extends Thread {
	public boolean gotLunch,
			insideTurn;
	public String name,
			gotLunchString,
			playString;
	Random rand;

	public MinionGnomeBobBase(String s) {
		super(s);
		name = s;
		rand = new Random();
	}

	protected void getLunch() throws InterruptedException {
		synchronized(this) {
			while(!gotLunch) {
				this.wait();
			}
			System.out.println(name + gotLunchString);
			System.out.println(name + " going to work.");
			this.notify();
		}
	}

	protected void goToWork() throws InterruptedException {
		Thread.sleep((int) (rand.nextDouble() * 1500));
		System.out.println(name + " finished work!");
	}

	protected void play() throws InterruptedException {
		synchronized(this) {
			while(!insideTurn) {
				this.wait();
			}
			System.out.println(name + playString);
			this.notify();
		}
	}

	protected void eat() throws InterruptedException {
		synchronized(Main.alice) {
			while(!Main.alice.dinnerReady) {
				Main.alice.wait();
			}
		}
		Thread.sleep((int) (rand.nextDouble() * 1500));

		try {
			Main.dinnerTable.acquire();
			System.out.println(name + " is eating.");
			Thread.sleep((int) (rand.nextDouble() * 1500));
			System.out.println(name + " finished dinner!");
			Main.dinnerTable.release();
		}
		catch(InterruptedException e) {
			Main.dinnerTable.release();
			throw e;
		}
	}

	protected void goToSleep() throws InterruptedException {
		Thread.sleep((int) (rand.nextDouble() * 1500));
		System.out.println(name + " fell asleep.");
	}
}