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
	}

	public void addDish(int dish_id) throws InterruptedException {
		Semaphore sync = new Semaphore(1);
		synchronized (sync) {
			sync.wait();
			count = dish_id;
			if (count == rackSize) {
				synchronized (barrier) {
					barrier2.wait();
					barrier.notifyAll();
				}
			}
			sync.notify();
		}
		synchronized (barrier) {
			barrier.wait();
			barrier.notify();
		}
		// ----------------------------
		synchronized (sync) {
			sync.wait();
			count -= 1;
			if (count == 0) {
				synchronized (barrier) {
					barrier.wait();
					barrier2.notifyAll();
				}
			}
			sync.notify();
		}
		synchronized (barrier) {
			barrier2.wait();
			barrier2.notify();
		}

	}

	public int removeDish() throws InterruptedException {
		return count - 1;
	}

}
