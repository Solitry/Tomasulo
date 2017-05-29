package assembly;

import type.Instruction;
import type.ResItem;

public class AddExecutor implements Executor {
	
	public final static int INSTR_ADD_TIME = 2;
	
	private ResItem running = null;
	private boolean isAdd = false;
	
	@Override
	public void get(ResItem item) {
		// GJH: Auto-generated method stub
		running = item;
		running.restTime = INSTR_ADD_TIME;
		isAdd = running.ins.opLabel == Instruction.INSTR_ADD_ID;
	}

	@Override
	public boolean write(CDB cdb, int cycle) {
		// GJH: Auto-generated method stub
		if (running.restTime < 0) {
			double val = running.value[0].V + running.value[1].V * (isAdd ? 1 : -1);
			cdb.receive(running, val);
			running.ins.finish(Instruction.WB, cycle);
			running = null;
			return true;
		}
		return false;
	}

	@Override
	public boolean full() {
		// GJH: Auto-generated method stub
		return running != null;
	}

	@Override
	public void tick(int cycle) {
		// ZYF: Auto-generated method stub
		if (running.restTime >= 0) {
			--running.restTime;
			if (running.restTime == 0)
				running.ins.finish(Instruction.ID, cycle);
		}
	}
}
