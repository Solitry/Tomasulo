package assembly;

import java.util.ArrayList;
import java.util.HashMap;

import type.Instruction;

/**
 * Instruction Queue
 * maintain the instructions
 */
public class InstrQueue {
	private ResSta mem = null, add = null, mult = null;
	private ArrayList<String> strList = null;
	private ArrayList<Instruction> insList = null;
	private int pos = 0;
	
	public InstrQueue(ResSta _mem, ResSta _add, ResSta _mult) {
		mem = _mem;
		add = _add;
		mult = _mult;
		strList = new ArrayList<String>();
		insList = new ArrayList<Instruction>();
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
		if(empty())
			return;
		
		Instruction i = insList.get(pos);
		//System.err.println("SendIns " + i.opLabel);
		switch(i.opLabel){
		case Instruction.INSTR_ADD_ID:
		case Instruction.INSTR_SUB_ID:
			if(!add.full(0)){
				add.getIns(i);
				++pos;
				i.finish(Instruction.ID, cycle);
			}
			break;
		case Instruction.INSTR_MUL_ID:
		case Instruction.INSTR_DIV_ID:
			if(!mult.full(0)){
				mult.getIns(i);
				++pos;
				i.finish(Instruction.ID, cycle);
			}
			break;
		case Instruction.INSTR_LD_ID:
			if(!mem.full(0)){
				mem.getIns(i);
				++pos;
				i.finish(Instruction.ID, cycle);
			}
			break;
		case Instruction.INSTR_ST_ID:
			if(!mem.full(1)){
				mem.getIns(i);
				++pos;
				i.finish(Instruction.ID, cycle);
			}
			default:
		}
	}
	
	/**
	 * return if the instruction queue is empty
	 */
	public boolean empty() {
		// GJH check if the queue is empty and return
		return pos >= insList.size();
	}
	
	public void reset() {
		pos = 0;
		for(Instruction ins : insList)
			ins.reset();
	}
	
	public void addIns(ArrayList<String> newList){
		System.out.println(newList.size());
		insList.clear();
		strList.clear();
		for(String str : newList){
			try {
				Instruction ins = new Instruction(str, insList.size());
				insList.add(ins);
				strList.add(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getPos(){
		return pos;
	}
	
	public boolean isFinish(){
		for(Instruction i : insList)
			if(!i.isFinish()){
				//System.err.println("Unfinish " + i.insLabel);
				return false;
			}
		return true;
	}
	
	public void log(HashMap<String, String[][]> logs) {
		System.out.print("InstrQueue:");
		if (empty()) {
			System.out.println(" empty");
		} else
			System.out.println("");
		
		System.out.format("%-3s%-16s%-4s%-4s%-4s%-4s\n", "St", "Ins", "ID", "EN", "EX", "WB");
		for (int i = 0; i < insList.size(); ++i) {
			System.out.print(i == pos? " * " : "   ");
			System.out.format("%-16s", strList.get(i));
			for (int j = 0; j < Instruction.STAGE_NUM; ++j)
				if (insList.get(i).finishTime[j] != -1)
					System.out.format("%-4d", insList.get(i).finishTime[j]);
				else
					System.out.print("    ");
			System.out.println("");
		}
		
		String[][] datas = new String[insList.size()][6];
		for(int i = 0; i < insList.size(); ++i){
			datas[i][0] = i == pos ? "->" : "";
			datas[i][1] = strList.get(i);
			for (int j = 0; j < Instruction.STAGE_NUM; ++j)
				datas[i][j + 2] = insList.get(i).finishTime[j] != -1 ? Integer.toString(insList.get(i).finishTime[j]) : "";
		}
		logs.put("Inst", datas);
	}
}
