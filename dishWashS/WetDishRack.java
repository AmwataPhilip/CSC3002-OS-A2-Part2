package dishWashS;

import java.util.concurrent.Semaphore;

public class WetDishRack {
	private int rackSize;
	private static int count = 1;
	private final Semaphore barrier;

	WetDishRack(int rackSize) {
		this.rackSize = rackSize;
		this.barrier = new Semaphore(0);
		;
	}

	public void addDish(int dish_id) throws InterruptedException {
		Semaphore sync = new Semaphore(1);
		synchronized (sync) {
			count = dish_id;
			if (count == rackSize) {
				synchronized (barrier) {
					barrier.wait();
				}
			}
		}

	}

	public int removeDish() throws InterruptedException {
		Semaphore sync = new Semaphore(1);
		synchronized (sync) {
			if (count == 0) {
				barrier.wait();
			}
			synchronized (barrier) {
				barrier.notify();
				return count;
			}
		}
	}
}
