package assembly;

import type.ResItem;

/**
 * Executor
 * the base class of Adder, Multer and Memory
 * for ResSta:
 *   use full() to check if a instruction can be accepted
 *   use get(item) to send a instruction into Executor
 * for Controller:
 *   Controller should use tick() before the CDB deal with the Bus-transaction
 */
public interface Executor extends CDBSender {
	public void get(ResItem item);
	public boolean full();
	public void tick(int cycle);
}
