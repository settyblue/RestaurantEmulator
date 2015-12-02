/**
 * Emulator class to run the running of the restaurant.
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
	
	/**
	 * @param numberOfDiners
	 * @param numberOfCooks
	 * @param numberOfTables
	 */
	public Emulator(int numberOfDiners, int numberOfCooks, int numberOfTables) {
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

	public static void main(String[] args) {
		int numberOfDiners,numberOfTables,numberOfCooks;
		int orderNumber = 0;

		if (args.length < 1) {
			System.out.println("Input file name argument is missing. Please run in the following format : " +
					"java Emulator <InputFile name>");
			System.exit(0);
		}

		try {
			FileReader inputFile = new FileReader(args[0]);
			BufferedReader inputReader = new BufferedReader(inputFile);
			numberOfDiners = Integer.parseInt(inputReader.readLine().trim());
			numberOfTables = Integer.parseInt(inputReader.readLine().trim());
			numberOfCooks = Integer.parseInt(inputReader.readLine().trim());
			System.out.println("number of diners : " + numberOfDiners);
			System.out.println("number of tables : " + numberOfTables);
			System.out.println("number of cooks : " + numberOfCooks);

			Emulator restaurant = new Emulator(numberOfDiners, numberOfCooks, numberOfTables);
			
			String line;
			while((line = inputReader.readLine()) != null){
				String orderLine[] = line.split("\\s+");
				int arrivalTimeStamp = Integer.parseInt(orderLine[0].trim());
				DinerOrder newDinerOrder = new DinerOrder(Integer.parseInt(orderLine[1].trim()), 
					 Integer.parseInt(orderLine[2].trim()), Integer.parseInt(orderLine[3].trim()), Integer.parseInt(orderLine[4].trim()));
				restaurant.diners.addDiner(orderNumber, new Diner(arrivalTimeStamp,newDinerOrder,orderNumber));
				orderNumber++;
			}
			inputReader.close();
			System.out.println("debug 1.");
			restaurant.runSimulation();
			System.out.println("debug 2.");
			restaurant.printOutput();
			System.out.println("debug 3.");
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void runSimulation() {
		// TODO Auto-generated method stub
		while(timer.getTime() <=120 || Diners.getStaticInstance().getNumberOfCurrentDiners() > 0) {
			diners.startDinersArrivedNow();
			timer.increment(); 
			synchronized(timer) {
				timer.notifyAll();
			}
		}
	}
	
	private void printOutput() {
		String str;
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("Final.txt"));
		} catch (IOException e) {
			System.out.println("Failed to open file for output");
		} 
		
		str = "Diner\t" +
				"Arrivaed\t" + 
				"Seating\t" +
				"Table ID\t" +
				"Cook Num\t" +
				"Food\t" + 
				"Leaving\t"+
                                "BTime\t"+
                                "FTime\t"+
                                "CTime\t"+
                                "STime";
		try {
			System.out.println(str);
			writer.write(str);
			writer.newLine();
		} catch (IOException e) {
			System.out.println("Failed to write to the file.");
		}
		
		
		DinerEntry[] entry = outputLogger.getOutputData();
		for(int i=0; i<entry.length; i++) {
			str = 	i + "\t" + 
					entry[i].arrivalTime + "\t\t" +
					entry[i].seatingTime + "\t" + 
					entry[i].tableNumber + "\t\t" + 
					entry[i].cookNumber + "\t\t" + 
					entry[i].foodServedTime + "\t" +
					entry[i].timeOfLeaving+ "\t" +
					entry[i].burgerMachineUsedTime+ "\t" +
					entry[i].friesMachineUsedTime+ "\t" +
					entry[i].sodaMachineUsedTime+ "\t" +
					entry[i].sundaeMachineUsedTime
                                        ;
			try {
				System.out.println(str);
				writer.write(str);
				writer.newLine();
			} catch (IOException e) {
				System.out.println("Failed to write to the file.");
			}
		}
		
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("Error closing output file writer.");
		}
	}
}
