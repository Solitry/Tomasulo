package assembly;

import java.util.HashMap;

import type.Instruction;
import type.ResItem;

public class MemBuffer implements ResSta, CDBReceiver, CDBSender {
	public final static int LD_BUF_SIZE = 3;
	public final static int ST_BUF_SIZE = 3;
	public final static String LD = "Load";
	public final static String ST = "Store";

	private FlowMemory mem = null;
	private Register reg = null;
	private ResItem[] loadBuf = new ResItem[LD_BUF_SIZE];
	private ResItem[] storeBuf = new ResItem[ST_BUF_SIZE];

	public MemBuffer(FlowMemory _mem, Register _reg) {
		mem = _mem;
		reg = _reg;

		for (int i = 0; i < LD_BUF_SIZE; ++i) {
			loadBuf[i] = new ResItem();
			loadBuf[i].name = LD + (i + 1);
		}
		for (int i = 0; i < ST_BUF_SIZE; ++i) {
			storeBuf[i] = new ResItem();
			storeBuf[i].name = ST + (i + 1);
		}
	}

	@Override
	public void getIns(Instruction ins) {
		ResItem it = null;
		if (ins.opLabel == Instruction.INSTR_LD_ID) { // load
			for (ResItem x : loadBuf)
				if (!x.busy) {
					it = x;
					break;
				}
			it.ins = ins;
			it.busy = true;
			it.in = false;
			it.restTime = -2;
			it.value[0] = null;
			it.value[1] = null;
			reg.setValue(ins.dst, it.name);
		} else { // store
			for (ResItem x : storeBuf)
				if (!x.busy) {
					it = x;
					break;
				}
			it.ins = ins;
			it.busy = true;
			it.in = false;
			it.restTime = -2;
			it.value[0] = reg.getValue(ins.src0);
			it.value[1] = null;
		}
	}

	private boolean beHead(ResItem x) {
		/*
		 * use FIFO on the queue of same-address ResItem to use FIFO on all
		 * LD/ST instructions, use label only
		 */
		if (!x.busy)
			return false;

		int addr = x.ins.src1;
		int label = x.ins.insLabel;
		for (ResItem it : loadBuf)
			if (it.busy && !it.in && it.ins.src1 == addr && it.ins.insLabel < label)
				return false;
		for (ResItem it : storeBuf)
			if (it.busy && !it.in && it.ins.src1 == addr && it.ins.insLabel < label)
				return false;
		return true;
	}

	@Override
	public void send(int cycle) {
		ResItem ret = null;
		if (!mem.full()) {
			for (int i = 0; i < ST_BUF_SIZE && ret == null; ++i)
				if (beHead(storeBuf[i]) && storeBuf[i].value[0].ready() && !storeBuf[i].in)
					ret = storeBuf[i];
			for (int i = 0; i < LD_BUF_SIZE && ret == null; ++i)
				if (beHead(loadBuf[i]) && !loadBuf[i].in)
					ret = loadBuf[i];
			if (ret != null) {
				ret.ins.finish(Instruction.EN, cycle);
				mem.get(ret);
				ret.in = true;
			}
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

	public void log(HashMap<String, String[][]> logs) {
		System.out.println("MemBuffer:");
		System.out.format("%-7s%-6s%-6s%-16s%-10s%-6s\n", "name", "busy", "time", "Ins", "reg", "addr");

		for (ResItem it : loadBuf) {
			System.out.format("%-7s", it.name);
			System.out.format("%-6s", it.busy ? " --" : " ");
			System.out.format("%-6s",
					it.restTime > -1 ? String.valueOf(it.restTime) : it.restTime == -1 && it.busy ? "wait" : " ");
			System.out.format("%-16s", it.ins != null ? it.ins.raw : " ");
			System.out.format("%-10s", " ");
			System.out.format("%-6s", it.ins != null ? it.ins.src1 : " ");
			System.out.println("");
		}

		for (ResItem it : storeBuf) {
			System.out.format("%-7s", it.name);
			System.out.format("%-6s", it.busy ? " --" : " ");
			System.out.format("%-6s",
					it.restTime > -1 ? String.valueOf(it.restTime) : it.restTime == -1 && it.busy ? "wait" : " ");
			System.out.format("%-16s", it.ins != null ? it.ins.raw : " ");
			System.out.format("%-10s", it.value[0] != null ? it.value[0].toString() : " ");
			System.out.format("%-6s", it.ins != null ? it.ins.src1 : " ");
			System.out.println("");
		}
		
		String[][] datas = new String[loadBuf.length][6];
		for(int i = 0; i < loadBuf.length; ++i){
			ResItem it = loadBuf[i];
			datas[i][0] = it.name;
			datas[i][1] = it.busy ? "--" : " ";
			datas[i][2] = it.restTime > -1 ? String.valueOf(it.restTime) : it.restTime == -1 && it.busy ? "wait" : "";
			datas[i][3] = it.ins != null ? it.ins.raw : "";
			datas[i][4] = "";
			datas[i][5] = it.ins != null ? Integer.toString(it.ins.src1) : "";
		}
		logs.put("Load", datas);
		
		datas = new String[storeBuf.length][6];
		for(int i = 0; i < storeBuf.length; ++i){
			ResItem it = storeBuf[i];
			datas[i][0] = it.name;
			datas[i][1] = it.busy ? "--" : " ";
			datas[i][2] = it.ins != null ? it.ins.raw : "";
			datas[i][3] = it.restTime > -1 ? String.valueOf(it.restTime) : it.restTime == -1 && it.busy ? "wait" : "";
			datas[i][4] = it.value[0] != null ? it.value[0].toString() : "";
			datas[i][5] = it.ins != null ? Integer.toString(it.ins.src1) : "";
		}
		logs.put("Store", datas);
	}
	
	public void reset(){
		for(ResItem i : loadBuf)
			i.reset();
		for(ResItem i : storeBuf)
			i.reset();
	}
}
