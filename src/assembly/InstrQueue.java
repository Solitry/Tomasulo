package assembly;

import java.util.ArrayList;

import type.Instruction;

/**
 * Instruction Queue
 * maintain the instructions
 */
public class InstrQueue {
	private ResSta mem = null, add = null, mult = null;
	private ArrayList<String> list = null;
	private int pos = 0;
	
	public InstrQueue(ResSta _mem, ResSta _add, ResSta _mult) {
		mem = _mem;
		add = _add;
		mult = _mult;
		list = new ArrayList<String>();
	}
	
	/**
	 * send a instruction
	 * do nothing if the queue is empty
	 */
	public void sendIns(int cycle) {
		// GJH: check if the ResSta is full, for mem, arg = 0 for LD, arg = 1 for ST
		// get first instruction, use offer/poll if ins is Queue
		// send i to mem/add/mult depend on i.opLabel use xxx.getIns(i)
		// set stage 0 of ins finished at cycle
		String instrStr = list.get(pos);
		Instruction i = new Instruction(instrStr, pos);
		switch(i.opLabel){
		case Instruction.INSTR_ADD_ID:
		case Instruction.INSTR_SUB_ID:
			if(!add.full(0)){
				add.getIns(i);
				++pos;
			}
			break;
		case Instruction.INSTR_MUL_ID:
		case Instruction.INSTR_DIV_ID:
			if(!mult.full(0)){
				mult.getIns(i);
				++pos;
			}
			break;
		case Instruction.INSTR_LD_ID:
		case Instruction.INSTR_ST_ID:
			if(!mem.full(0)){
				mem.getIns(i);
				++pos;
			}
			default:
		}
	}
	
	/**
	 * return if the instruction queue is empty
	 */
	public boolean empty() {
		// GJH check if the queue is empty and return
		return pos < list.size();
	}
	
	public void reset() {
		pos = 0;
		list.clear();
	}
	
	public void addIns(ArrayList<String> newList){
		list.addAll(newList);
	}
	
	public int getPos(){
		return pos;
	}
}
