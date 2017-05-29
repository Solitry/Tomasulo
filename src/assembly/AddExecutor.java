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
	public void write(CDB cdb, int cycle) {
		// GJH: Auto-generated method stub
		if (--running.restTime < 0) {
			double val = running.value[0].V + running.value[1].V * (isAdd ? 1 : -1);
			cdb.receive(running, val);
			running = null;
		}
	}

	@Override
	public boolean full() {
		// GJH: Auto-generated method stub
		return running != null;
	}

	public static void main(String[] args) {
	}
}
