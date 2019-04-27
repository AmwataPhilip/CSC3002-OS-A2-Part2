package dishWashS;

import java.util.concurrent.Semaphore;

/**
 * Modified by: Philip Amwata
 *
 * date: 27/04/2019
 */
public class WetDishRack {
	private static int count = 1; // Thread counter
	private final Semaphore sync; // Mutex lock
	private final Semaphore rackFull; // Semaphore to check if rack is not empty
	private final Semaphore rackEmpty; // Semaphore to check if rack is not full

	WetDishRack(int rackSize) {
		this.rackFull = new Semaphore(0);
		this.rackEmpty = new Semaphore(rackSize);
		this.sync = new Semaphore(1);
		;
	}

	/**
	 *
	 * @return void
	 * @param dish_id
	 * @throws InterruptedException
	 */
	public void addDish(int dish_id) throws InterruptedException {
		rackEmpty.acquire(); // Rack is not full
		sync.acquire(); // Lock rack for Washer
		count = dish_id; // Ser washer dish to current dish id
		sync.release(); // Unlock rack for dryer to use
		rackFull.release(); // Rack is not empty
	}

	/**
	 *
	 * @return int
	 * @throws InterruptedException
	 */
	public int removeDish() throws InterruptedException {
		rackFull.acquire(); // Rack is not empty
		sync.acquire(); // Lock rack for Dryer
		int number = count; // Set dryer dish to current dish id
		sync.release(); // Unlock rack for washer to use
		rackEmpty.release(); // Rack is not full
		return number; // Return current dish id that dryer is working on
	}
}
