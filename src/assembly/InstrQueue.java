package assembly;

import type.Instruction;

/**
 * Instruction Queue
 * maintain the instructions
 */
public class InstrQueue {
	private ResSta mem = null, add = null, mult = null;
	
	public InstrQueue(ResSta _mem, ResSta _add, ResSta _mult) {
		mem = _mem;
		add = _add;
		mult = _mult;
		// TODO add window support, maybe change the type of ins
	}
	
	/**
	 * send a instruction
	 * do nothing if the queue is empty
	 */
	public void sendIns(int cycle) {
		// TODO check if the ResSta is full, for mem, arg = 0 for LD, arg = 1 for ST
		String instrStr = null;
		// TODO get first instruction, use offer/poll if ins is Queue
		Instruction i = new Instruction(instrStr, 0);
		// TODO send i to mem/add/mult depend on i.opLabel use xxx.getIns(i)
		
		// TODO set stage 0 of ins finished at cycle
	}
	
	/**
	 * return if the instruction queue is empty
	 */
	public boolean empty() {
		// TODO check if the queue is empty and return
		return true;
	}
	
	/**
	 * record the instruction's finish time of each stage
	 * @param ins
	 * @param stage
	 * @param cycle
	 *   the time
	 */
	public void compIns(Instruction ins, int stage, int cycle) {
		// TODO set time_table[ins.insLabel][stage] = cycle
	}
}
