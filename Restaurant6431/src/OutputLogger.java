/**
 * 
 */

/**
 * @author Jhansi
 *
 */
public class OutputLogger {
	private static OutputLogger instance;
	private DinerEntry[] outputData;
	
	private OutputLogger() {
		
	}
	
	/**
	 * @param numberOfDiners
	 */
	public void initialize(int numberOfDiners) {
		outputData = new DinerEntry[numberOfDiners];
		for(int i=0; i<outputData.length; i++) {
			outputData[i] = new DinerEntry();
		}
	}
	
	/**
	 * @return
	 */
	public static OutputLogger getStaticInstance() {
		if(instance == null) {
			instance = new OutputLogger();
		}
		return instance;
	}

	public DinerEntry[] getOutputData() {
		return outputData;
	}
}