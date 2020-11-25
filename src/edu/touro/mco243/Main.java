package edu.touro.mco243;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Main {
	public static Minion[] minions = new Minion[10];
	public static Gnome[] gnomes = new Gnome[7];
	public static Alice alice;
	public static Bob bob;

	public static boolean minionsDoneWork, minionsInside,
			gnomesDoneWork, gnomesInside,
			aliceWasReading, bobWasReading;

	public static Queue<Minion> minionQueue = new LinkedList<>();
	public static Queue<Gnome> gnomeQueue = new LinkedList<>();

	public static Semaphore dinnerTable = new Semaphore(5);
	public static Semaphore bathroom = new Semaphore(1);

	public static void main(String[] args) {
		for(int i = 0; i < 10; i++) {
			minions[i] = new Minion("Minion " + (i + 1));
			minions[i].start();
		}
		for(int i = 0; i < 7; i++) {
			gnomes[i] = new Gnome("Gnome " + (i + 1));
			gnomes[i].start();
		}
		alice = new Alice();
		alice.start();
		bob = new Bob();
		bob.start();

		while(Thread.activeCount() > 2) {
			if(alice.isReading || bob.isReading) {
				if(!(aliceWasReading || bobWasReading)) {
					System.out.println("God: turning on lights.");
					aliceWasReading = alice.isReading;
					bobWasReading = bob.isReading;
				}
			}
		}
		System.out.println("God: turning off lights.");
	}
}
