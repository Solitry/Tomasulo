package assembly;

import type.Instruction;
import type.ResItem;

public class MulExecutor implements Executor{
	
	public final static int INSTR_MUL_TIME = 10;
	public final static int INSTR_DIV_TIME = 40;
	
	private ResItem running = null;
	private boolean isMul = false;
	
	@Override
	public void get(ResItem item) {
		// GJH: Auto-generated method stub
		running = item;
		isMul = running.ins.opLabel == Instruction.INSTR_MUL_ID;
		running.restTime = isMul ? INSTR_MUL_TIME : INSTR_DIV_TIME;
	}

	@Override
	public boolean write(CDB cdb, int cycle) {
		// GJH: Auto-generated method stub
		if (running != null && running.restTime < 0) {
			double val = running.value[0].V * Math.pow(running.value[1].V, isMul ? 1 : -1);
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
		if (running != null && running.restTime >= 0) {
			--running.restTime;
			if (running.restTime == 0)
				running.ins.finish(Instruction.EX, cycle);
		}
	}
}
