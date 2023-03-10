package adapter;

import java.io.IOException;

public abstract class MultiRotorDrone extends PhysicalDrone implements AdapterInterface {

	/***
	 * 
	 * @param back
	 * @throws IOException 
	 */
	public abstract void flyBackward(int back) throws IOException;

	/***
	 * 
	 * @param direction
	 * @throws IOException 
	 */
	public abstract void flip(String direction) throws IOException;

	/***
	 * 
	 * @param seconds
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public abstract void hoverInPlace(int seconds) throws InterruptedException, IOException;	

}
