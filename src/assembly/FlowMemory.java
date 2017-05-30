package assembly;

import java.util.ArrayList;

import type.Instruction;
import type.ResItem;
import type.MemUnit;


public class FlowMemory implements Executor {
	public final static int MEM_TIME = 2;
	
	private ArrayList<ResItem> list = new ArrayList<ResItem>();
	
	private ArrayList<MemUnit> storage = new ArrayList<MemUnit>();
	
	@Override
	public void get(ResItem item) {
		list.add(item);
		item.restTime = MEM_TIME;
	}

	@Override
	public boolean full() {
		return list.size() >= MEM_TIME + 1;
	}

	@Override
	public void tick(int cycle) {
		int start = 1;
		boolean visitMem = false;
		
		if (list.size() > 0) {
			ResItem it = list.get(0);
			if (it.restTime >= 0) {
				--it.restTime;
				if (it.restTime == 0)
					it.ins.finish(Instruction.EX, cycle);
				if (it.restTime < 0 && it.ins.opLabel == Instruction.INSTR_ST_ID) {
					it.ins.finish(Instruction.WB, cycle);
					it.busy = false;
					it.restTime = -1;
					store(it.ins.src1, it.value[0].V);
					list.remove(0);
					start = 0;
					visitMem = true;
				}
			}
		}
		
		for (int i = start; i < list.size(); ++i) {
			int pre = i == 0? -2 : list.get(i - 1).restTime;
			if (list.get(i).restTime - pre > 1) {
				if (list.get(i).restTime == 1 && list.get(i).ins.opLabel == Instruction.INSTR_LD_ID)
					continue;
				--list.get(i).restTime;
				if (list.get(i).restTime == 0)
					list.get(i).ins.finish(Instruction.EX, cycle);
			}
		}
	}

	@Override
	public boolean write(CDB cdb, int cycle) {
		if (list.size() == 0)
			return false;
		
		ResItem last = list.get(0);
		
		if (last.restTime < 0) {
			assert(last.ins.opLabel == Instruction.INSTR_LD_ID);
			
			double val = load(last.ins.src1);
			cdb.receive(last, val);
			last.ins.finish(Instruction.WB, cycle);
			list.remove(0);
			return true;
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
	
	public void log() {
		System.out.println("Memory:");
		
		for (ResItem it : list)
			System.out.println(it.ins.raw + " " + it.restTime);
		
		System.out.format("%-6s%-10s\n", "addr", "value");

		for (int i = 0; i < storage.size(); ++i) {
			System.out.format("%-6d", storage.get(i).addr);
			System.out.format("%-10s\n", storage.get(i).value);
		}
	}
}