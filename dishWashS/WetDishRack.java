package dishWashS;

import java.util.concurrent.Semaphore;

public class WetDishRack {
	private int rackSize;
	private static int count = 0;
	private final Semaphore sync;
	private final Semaphore barrierFull;
	private final Semaphore barrierEmpty;

	WetDishRack(int rackSize) {
		this.rackSize = rackSize;
		this.barrierFull = new Semaphore(0);
		this.barrierEmpty = new Semaphore(rackSize);
		this.sync = new Semaphore(1);
		;
	}

	public void addDish(int dish_id) throws InterruptedException {

		barrierEmpty.acquire();
		sync.acquire();
		count = dish_id;
		sync.release();
		barrierFull.release();
	}

	public int removeDish() throws InterruptedException {
		barrierFull.acquire();
		sync.acquire();
		count -= 1;
		sync.release();
		barrierEmpty.release();
		return count;
	}
}
