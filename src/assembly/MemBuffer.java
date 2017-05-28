package assembly;

import type.Instruction;
import type.ResItem;

public class MemBuffer implements ResSta, CDBReceiver {
	public final static int LD_BUF_SIZE = 3;
	public final static int ST_BUF_SIZE = 3;
	
	private Memory mem = null;
	private Register reg = null;
	private ResItem[] loadBuf = new ResItem[LD_BUF_SIZE];
	private ResItem[] storeBuf = new ResItem[ST_BUF_SIZE];
	
	public MemBuffer(Memory _mem, Register _reg) {
		mem = _mem;
		reg = _reg;
		
		for (int i = 0; i < LD_BUF_SIZE; ++i)
			loadBuf[i] = new ResItem();
		for (int i = 0; i < ST_BUF_SIZE; ++i)
			storeBuf[i] = new ResItem();
	}

	@Override
	public void getIns(Instruction ins) {
		// TODO get ins and save as ResItem
		// TODO maintain the reg
	}

	@Override
	public void send(int cycle) {
		// TODO check if the mem is full and send item to it
		// TODO set stage 1 of ins finished at cycle
	}

	@Override
	public boolean full(int arg) {
		//TODO check if buffer is full, arg = 0 for LD, arg = 1 for ST
		return false;
	}

	@Override
	public void receive(ResItem item, double val) {
		// TODO update value
	}
}
