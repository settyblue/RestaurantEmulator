/**
 * 
 */

/**
 * @author Jhansi
 *
 */
public class DinerOrder {
	
	public int numberOfBurgers;
	public int numberOfFries;
	public int numberOfCokes;
	public int numberOfSundae;
	public boolean burgersReady = false;
	public boolean friesReady = false;
	public boolean cokeReady = false;
	public boolean sundaeReady = false;
	
	/**
	 * @param numberOfBurgers
	 * @param numberOfFries
	 * @param numberOfCokes
	 * @param numberOfSundae
	 */
	public DinerOrder(int numberOfBurgers, int numberOfFries, int numberOfCokes, int numberOfSundae) {
		
		this.numberOfBurgers = numberOfBurgers;
		this.numberOfFries = numberOfFries;
		this.numberOfCokes = numberOfCokes;
		this.numberOfSundae = numberOfSundae;
		
		if(numberOfFries > 0)
			friesReady = false;
		else
			friesReady = true;
		
		if(numberOfCokes > 0) 
			cokeReady = false;
		else
			cokeReady = true;
		
		if(numberOfSundae > 0) 
			sundaeReady = false;
		else
			sundaeReady = true;
	}
	
	/**
	 * @return boolean
	 */
	public boolean isComplete() {
		if(burgersReady && friesReady && cokeReady && sundaeReady)
			return true;
		else
			return false;
	}
}
