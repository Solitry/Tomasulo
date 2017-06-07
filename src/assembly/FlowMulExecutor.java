package assembly;

import java.util.ArrayList;

import tomasulodisplay.MoveLine;
import type.Instruction;
import type.ResItem;

public class FlowMulExecutor implements Executor {
	public final static int INSTR_MUL_TIME = 10;
	public final static int INSTR_DIV_TIME = 40;
	
	private ArrayList<ResItem> list = new ArrayList<ResItem>();
	private MoveLine[] lines;
	
	public FlowMulExecutor(MoveLine[] lines){
		this.lines = lines;
	}
	
	@Override
	public boolean write(CDB cdb, int cycle) {
		if (list.size() == 0)
			return false;
		
		for (int i = 0; i < list.size(); ++i) {
			ResItem last = list.get(i);
			if (last.restTime < 0) {
				boolean isMul = last.ins.opLabel == Instruction.INSTR_MUL_ID;
				double val = last.value[0].V * Math.pow(last.value[1].V, isMul ? 1 : -1);
				cdb.receive(last, val);
				last.ins.finish(Instruction.WB, cycle);
				list.remove(i);
				lines[7].play();
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void get(ResItem item) {
		list.add(item);
		boolean isMul = item.ins.opLabel == Instruction.INSTR_MUL_ID;
		item.restTime = isMul ? INSTR_MUL_TIME : INSTR_DIV_TIME;
	}

	@Override
	public boolean full() {
		for (ResItem item : list)
			if (item.restTime % 6 == 4)
				return true;
		return false;
	}

	@Override
	public void tick(int cycle) {
		boolean[] check = new boolean[list.size()];
		for (int i = 0; i < list.size(); ++i)
			check[i] = false;
		
		// check 0 -> -1
		for (int i = 0; i < list.size(); ++i)
			if (list.get(i).restTime == 0) {
				boolean pass = true;
				for (int j = 0; j < list.size(); ++j)
					if (i != j && list.get(j).restTime == -1) {
						pass = false;
						break;
					}
				if (pass)
					--list.get(i).restTime;
				check[i] = true;
				break;
			}
		
		// check 1 -> 0
		boolean block = false;
		for (int i = 0; i < list.size(); ++i)
			if (list.get(i).restTime == 1) {
				boolean pass = true;
				for (int j = 0; j < list.size(); ++j)
					if (i != j && list.get(j).restTime == 0) {
						pass = false;
						break;
					}
				if (pass) {
					--list.get(i).restTime;
					list.get(i).ins.finish(Instruction.EX, cycle);
				} else
					block = true;
				check[i] = true;
				break;
			}
		
		if (block) {
			for (int stage = 2; stage <= 6; ++stage)
				for (int i = 0; i < list.size(); ++i) {
					ResItem item = list.get(i);
					if (getStage(item) == stage && !check[i]) {
						int nxt = getNextState(item);
						boolean pass = true;
						for (ResItem x : list)
							if (getStage(x) == nxt) {
								pass = false;
								break;
							}
						if (pass)
							--item.restTime;
						check[i] = true;
						break;
					}
				}
		} else { // not block
			for (int i = 0; i < list.size(); ++i)
				if (!check[i])
					--list.get(i).restTime;
		}
	}
	
	private int getStage(ResItem it) {
		int rt = it.restTime;
		if (rt <= 0)
			return rt;
		return (rt - 1) % 6 + 1;
	}
	
	private int getNextState(ResItem it) {
		int rt = it.restTime;
		if (rt <= 1)
			return rt - 1;
		return (rt - 2) % 6 + 1;
	}
	
	void reset(){
		list.clear();
	}
}
