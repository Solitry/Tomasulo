package assembly;

import java.util.ArrayList;

import type.Instruction;
import type.ResItem;
import type.MemUnit;


public class Memory implements Executor {
	public final static int MEM_TIME = 2;
	
	private ResItem running = null;
	
	private ArrayList<MemUnit> storage = new ArrayList<MemUnit>();
	
	@Override
	public void get(ResItem item) {
		running = item;
		running.restTime = MEM_TIME;
	}

	@Override
	public boolean full() {
		return running != null;
	}

	@Override
	public void tick(int cycle) {
		if (running.restTime >= 0) {
			--running.restTime;
			if (running.restTime == 0)
				running.ins.finish(Instruction.EX, cycle);
		}
	}

	@Override
	public boolean write(CDB cdb, int cycle) {
		if (running.restTime < 0) {
			if (running.ins.opLabel == Instruction.INSTR_LD_ID) { // load
				double val = load(running.ins.src1);
				cdb.receive(running, val);
				running.ins.finish(Instruction.WB, cycle);
				running = null;
				return true;
			} else { // store
				store(running.ins.src1, running.value[0].V);
				running = null;
				return false;
			}
		}
		return false;
	}
	
	private void store(int addr, double val) {
		int change = -1;
		for (int i = 0; i < storage.size(); ++i)
			if (storage.get(i).addr == addr) {
				storage.get(i).value = val;
				change = i;
				break;
			}
		
		if (change == -1) {
			storage.add(new MemUnit(addr, val));
		}
	}
	
	private double load(int addr) {
		for (MemUnit unit : storage)
			if (unit.addr == addr)
				return unit.value;
		return 0.0;
	}
}
