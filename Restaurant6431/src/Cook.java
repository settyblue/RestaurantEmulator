/**
 * 
 */

/**
 * @author Jhansi
 *
 */
public class Cook implements Runnable{
	
	private int cookId;		
	private Table tableServing;
	private DinerOrder order;
	private Thread th;
	public int timeBurgerMacihineWasUsed;
	public int timeFriesMachineWasUsed;
	public int timeSodaMachineWasUsed;
	public int timeSundaeMachineWasUsed;
	
	public Cook(int cookId) {
		this.cookId = cookId;
		th = new Thread(this, "Cook - "+this.cookId);
		th.start();
	}
	
	public int getId() {
		return this.cookId;
	}
	
	public void run() {
		while(Timer.getStaticInstance().getTime() <= 120 || Diners.getStaticInstance().getNumberOfCurrentDiners() > 0) {
				tableServing = Tables.getStaticInstance().getTableForCook();
				if(tableServing != null) {
					this.timeFriesMachineWasUsed = -1;
					this.timeSodaMachineWasUsed = -1;
					this.timeSundaeMachineWasUsed = -1;
					tableServing.assignCook(this);
					tableServing.waitOnOrder();
					order = tableServing.getOrder();
					VendingMachine machine = VendingMachine.getInstance();
					while(!order.isComplete()) {
						PrepareItem fooditem = machine.getMachineFor(order);
						fooditem.prepare(order, this);
					}
					tableServing.timeBurgerMacihineWasUsed = this.timeBurgerMacihineWasUsed;
					tableServing.timeFriesMachineWasUsed = this.timeFriesMachineWasUsed;
					tableServing.timeSodaMachineWasUsed = this.timeSodaMachineWasUsed;
					tableServing.timeSundaeMachineWasUsed = this.timeSundaeMachineWasUsed;
					tableServing.serveFood();
				}
		}
	}
}
