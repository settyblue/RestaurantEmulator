/**
 * 
 */

/**
 * @author Jhansi
 *
 */
public class PrepareItem {
	public int timer;
	public boolean isOccupied;
	private String machine;
	
	public PrepareItem(String name) {
		this.machine = name;
		isOccupied = false;
    }
	
	public String getName() {
		return machine;
	}
	
	synchronized public void occupy() {
		isOccupied = true;
	}
	
	synchronized public boolean isOccupied() {
		return isOccupied;
	}
	
	public void prepare(DinerOrder order, Cook cook) {
		int timeForCooking = timer;
		switch(machine) {
		case "BurgerMachine":
			timeForCooking = order.numberOfBurgers * timer;
			break;
		case "FriesMachine":
			timeForCooking = order.numberOfFries * timer;
			break;
		case "CokeMachine":
			timeForCooking = order.numberOfCokes * timer;
			break;
		case "SundaeMachine":
			timeForCooking = order.numberOfSundae * timer;
			break;
		}
		int startTime = Timer.getStaticInstance().getTime();
		switch(machine) {
		case "BurgerMachine":
			cook.timeBurgerMacihineWasUsed = startTime;
			break;
		case "FriesMachine":
			cook.timeFriesMachineWasUsed = startTime;
			break;
		case "CokeMachine":
			cook.timeSodaMachineWasUsed = startTime;
			break;
		case "SundaeMachine":
			cook.timeSundaeMachineWasUsed = startTime;
			break;	
		}
		
		while(Timer.getStaticInstance().getTime() < startTime + timeForCooking) {
			//cooking
			try {
				synchronized(Timer.getStaticInstance()) {
					Timer.getStaticInstance().wait();
				}
			} catch(InterruptedException ie) {}
		}
		this.isOccupied = false;
		switch(machine) {
		case "BurgerMachine":
			order.burgersReady = true;
			System.out.println("Time : "+Timer.getStaticInstance().getTime()+" Burgers are ready by "+Thread.currentThread().getName());
			break;
		case "FriesMachine":
			order.friesReady = true;
			System.out.println("Time : "+Timer.getStaticInstance().getTime()+" Fries are ready by "+Thread.currentThread().getName());
			break;
		case "CokeMachine":
			order.cokeReady = true;
			System.out.println("Time : "+Timer.getStaticInstance().getTime()+" Coke is ready by "+Thread.currentThread());
			break;
		case "SundaeMachine":
			order.sundaeReady = true;
			System.out.println("Time : "+Timer.getStaticInstance().getTime()+" Sundae is ready by "+Thread.currentThread().getName());
			break;
		}
		
	}
}
