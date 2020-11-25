package edu.touro.mco243;

public class Bob extends MinionGnomeBobBase {
	public boolean sleepMonitor, isReading;

	public Bob() {
		super("Bob");
		gotLunchString = " got lunch.";
		playString = " is going inside.";
	}

	public void run() {
		try {
			getLunch();
			goToWork();
			play();
			eat();
			waitForSleepAlert();
			goToSleep();
		}
		catch(InterruptedException e) {
		}
	}

	private void waitForSleepAlert() throws InterruptedException {
		synchronized(this) {
			while(!sleepMonitor) {
				this.wait();
			}
			System.out.println(name+" is going to read.");
			isReading = true;
		}
	}
}
