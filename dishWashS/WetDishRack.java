package dishWashS;

import java.util.concurrent.Semaphore;

public class WetDishRack {
	private int rackSize;
	private static int count = 0;
	private final Semaphore barrier;
	private final Semaphore barrier2;

	WetDishRack(int rackSize) {
		this.rackSize = rackSize;
		this.barrier = new Semaphore(0);
		this.barrier2 = new Semaphore(1);
		;
	}

	public void addDish(int dish_id) throws InterruptedException {
		Semaphore sync = new Semaphore(1);
		synchronized (barrier) {
			if (count == rackSize) {
				barrier.wait();
			} else {
				synchronized (sync) {
					count += 1;
					barrier.notify();
				}
			}
		}
	}

	public int removeDish() throws InterruptedException {
		Semaphore sync = new Semaphore(1);

		synchronized (barrier) {
			if (count == 0) {
				barrier.wait();
			} else {
				synchronized (sync) {
					count -= 1;
					barrier.notify();
				}
			}
			return count;
		}
	}
}
