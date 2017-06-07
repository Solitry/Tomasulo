package assembly;

import java.util.ArrayList;

import tomasulodisplay.MoveLine;
import type.Instruction;
import type.ResItem;

public class FlowAddExecutor implements Executor {
	public final static int INSTR_ADD_TIME = 2;
	
	private ArrayList<ResItem> list = new ArrayList<ResItem>();
	private MoveLine[] lines;
	
	public FlowAddExecutor(MoveLine[] lines){
		this.lines = lines;
	}
	
	@Override
	public boolean write(CDB cdb, int cycle) {
		if (list.size() == 0)
			return false;

		ResItem last = list.get(0);

		if (last.restTime < 0) {
			boolean isAdd = last.ins.opLabel == Instruction.INSTR_ADD_ID;
			double val = last.value[0].V + last.value[1].V * (isAdd ? 1 : -1);
			cdb.receive(last, val);
			last.ins.finish(Instruction.WB, cycle);
			list.remove(0);
			lines[6].play();
			return true;
		}
		
		return false;
	}

	@Override
	public void get(ResItem item) {
		list.add(item);
		item.restTime = INSTR_ADD_TIME;
	}

	@Override
	public boolean full() {
		return !list.isEmpty() && list.get(list.size() - 1).restTime >= INSTR_ADD_TIME;
	}

	@Override
	public void tick(int cycle) {
		for (int i = 0; i < list.size(); ++i) {
			int pre = i == 0 ? -2 : list.get(i - 1).restTime;
			if (list.get(i).restTime - pre > 1) {
				--list.get(i).restTime;
				if (list.get(i).restTime == 0)
					list.get(i).ins.finish(Instruction.EX, cycle);
			}
		}
	}

	void reset(){
		list.clear();
	}
}
