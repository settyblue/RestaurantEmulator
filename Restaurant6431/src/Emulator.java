/**
 * Emulator class to emulate the running of the restaurant.
 */

/**
 * @author Jhansi
 *
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Emulator {
	
	private final int MAX_TIME=120;
	private Diners diners;
	private Cooks cooks;
	private Tables tables;
	private Timer timer;
	private OutputLogger outputLogger;

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Input file name argument is missing. Please run in the following format : " +
					"java Emulator <InputFile name>");
			System.exit(0);
		}
		Emulator restaurant = parseInput(args[0]);
		restaurant.runSimulation();
		restaurant.printOutput();
	}

	/**
	 * @param string
	 */
	private static Emulator parseInput(String inputFilename) {
		// TODO Auto-generated method stub
		Emulator restaurant = null;
		try{
			FileReader inputFile = new FileReader(inputFilename);
			BufferedReader inputReader = new BufferedReader(inputFile);
			restaurant = new Emulator(Integer.parseInt(inputReader.readLine().trim()),
					Integer.parseInt(inputReader.readLine().trim()), Integer.parseInt(inputReader.readLine().trim()));
			String line;
			int orderNumber = 0;
			while((line = inputReader.readLine()) != null){
				String orderLine[] = line.split("\\s+");
				int arrivalTimeStamp = Integer.parseInt(orderLine[0].trim());
				DinerOrder newDinerOrder = new DinerOrder(Integer.parseInt(orderLine[1].trim()), 
						Integer.parseInt(orderLine[2].trim()), Integer.parseInt(orderLine[3].trim()), Integer.parseInt(orderLine[4].trim()));
				restaurant.diners.addDiner(orderNumber, new Diner(arrivalTimeStamp,newDinerOrder,orderNumber));
				orderNumber++;
			}
			inputReader.close();
		}catch(Exception ex){
			System.out.println("Issue with opening/reading the input file.");
		}
		return restaurant;
	}

	private void runSimulation() {
		// TODO Auto-generated method stub
		while(timer.getTime() <= MAX_TIME || Diners.getStaticInstance().getNumberOfCurrentDiners() > 0) {
			diners.startDinersArrivedNow();
			timer.increment(); 
			synchronized(timer) {
				timer.notifyAll();
			}
		}
	}
	
	/**
	 * return null
	 */
	private void printOutput() {
		String str;
		str = "Diner\t" +"Arrived" + "Seating\t" +"Table ID" +"Cook Num\t" +"Food\t" + 
				"Leaving\t"+"BTime\t"+"FTime\t"+"CTime\t"+"STime";
		System.out.println(str);
		DinerEntry[] entry = outputLogger.getOutputData();
		for(int i=0; i<entry.length; i++) {
			str = 	i + "\t" + entry[i].toString();
			System.out.println(str);
		}
	}
	/**
	 * @param numberOfDiners
	 * @param numberOfCooks
	 * @param numberOfTables
	 */
	public Emulator(int numberOfDiners, int numberOfTables, int numberOfCooks) {
		// TODO Auto-generated constructor stub
		timer = Timer.getStaticInstance();
		tables = Tables.getStaticInstance();
		tables.initialize(numberOfTables);
		diners = Diners.getStaticInstance();
		diners.initialize(numberOfDiners);
		cooks = Cooks.getStaticInstance();
		cooks.initialize(numberOfCooks);
		outputLogger = OutputLogger.getStaticInstance();
		outputLogger.initialize(numberOfDiners);
	}
}
