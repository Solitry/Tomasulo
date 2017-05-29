package assembly;

import type.Instruction;
import type.ResItem;

public class MemBuffer implements ResSta, CDBReceiver, CDBSender {
	public final static int LD_BUF_SIZE = 3;
	public final static int ST_BUF_SIZE = 3;
	public final static String LD = "Load";
	public final static String ST = "Store";

	private Memory mem = null;
	private Register reg = null;
	private ResItem[] loadBuf = new ResItem[LD_BUF_SIZE];
	private ResItem[] storeBuf = new ResItem[ST_BUF_SIZE];

	public MemBuffer(Memory _mem, Register _reg) {
		mem = _mem;
		reg = _reg;

		for (int i = 0; i < LD_BUF_SIZE; ++i) {
			loadBuf[i] = new ResItem();
			loadBuf[i].name = LD + i;
		}
		for (int i = 0; i < ST_BUF_SIZE; ++i) {
			storeBuf[i] = new ResItem();
			storeBuf[i].name = ST + i;
		}
	}

	@Override
	public void getIns(Instruction ins) {
		ResItem it = null;
		if (ins.opLabel == Instruction.INSTR_LD_ID) { // load
			for (ResItem x: loadBuf)
				if (!x.busy) {
					it = x;
					break;
				}
			it.ins = ins;
			it.busy = true;
			it.value[0] = null;
			it.value[1] = null;
			reg.setValue(ins.dst, it.name);
		} else { // store
			for (ResItem x: storeBuf)
				if (!x.busy) {
					it = x;
					break;
				}
			it.ins = ins;
			it.busy = true;
			it.value[0] = reg.getValue(ins.src0);
			it.value[1] = null;
		}
	}
	
	private boolean beHead(ResItem x) {
		/* use FIFO on the queue of same-address ResItem
		 * to use FIFO on all LD/ST instructions, use label only
		 */
		if(!x.busy)
			return false;
		
		int addr = x.ins.src1;
		int label = x.ins.insLabel;
		for (ResItem it: loadBuf)
			if (it.busy && it.ins.src1 == addr && it.ins.insLabel < label)
				return false;
		for (ResItem it: storeBuf)
			if (it.busy && it.ins.src1 == addr && it.ins.insLabel < label)
				return false;
		return true;
	}
	
	@Override
	public void send(int cycle) {
		ResItem ret = null;
		if (!mem.full()) {	
			for (int i = 0; i < ST_BUF_SIZE && ret == null; ++i)
				if (beHead(storeBuf[i]) && storeBuf[i].value[0].ready())
					ret = storeBuf[i];
			for (int i = 0; i < LD_BUF_SIZE && ret == null; ++i)
				if (beHead(loadBuf[i]))
					ret = loadBuf[i];
			if (ret != null)
				mem.get(ret);
		}
	}

	@Override
	public boolean full(int arg) {
		if (arg == 0) {
			for (ResItem it : loadBuf)
				if (!it.busy)
					return false;
		} else {
			for (ResItem it : storeBuf)
				if (!it.busy)
					return false;
		}
		return true;
	}

	@Override
	public void receive(ResItem item, double val) {
		for (ResItem it : storeBuf)
			if (it.busy && it.value[0].wait(item.name))
				it.value[0].setValue(val);
	}

	@Override
	public boolean write(CDB cdb, int cycle) {
		// TODO bypass of load instruction
		return false;
	}
}
