package assembly;

import java.util.HashMap;

import tomasulodisplay.MoveLine;
import type.Instruction;
import type.ResItem;
import type.Value;

public class CalcResSta implements ResSta, CDBReceiver {
	public static String ADDER = "Add";
	public static String MULTER = "Mult";
	public static int ADDER_RES_SIZE = 3;
	public static int MULTER_RES_SIZE = 2;

	private String name;
	private int resSize = 0;
	private Executor exe = null;
	private Register reg = null;
	private ResItem[] res = null;
	private MoveLine[] lines;

	public CalcResSta(String _name, Executor _exe, Register _reg, MoveLine[] lines) {
		// resSize = _resSize;
		if (_name.equals(ADDER))
			resSize = ADDER_RES_SIZE;
		else
			resSize = MULTER_RES_SIZE;
		name = _name;

		exe = _exe;
		reg = _reg;
		// GJH: allocate the resSize
		res = new ResItem[resSize];
		for (int i = 0; i < resSize; ++i) {
			res[i] = new ResItem();
			res[i].name = name + (i + 1);
		}
		
		this.lines = lines;
	}

	@Override
	public void receive(ResItem item, double val) {
		// GJH: Auto-generated method stub
		for (ResItem x : res)
			if (x.busy)
				for (Value y : x.value)
					if (y.wait(item.name))
						y.setValue(val);
	}

	@Override
	public boolean full(int arg) {
		// GJH: Auto-generated method stub
		for (ResItem x : res)
			if (!x.busy)
				return false;
		return true;
	}

	@Override
	public void getIns(Instruction ins) {
		// GJH: Auto-generated method stub
		// System.err.println("GetIns " + ins.insLabel);
		for (ResItem x : res)
			if (!x.busy) {
				x.busy = true;
				x.restTime = -2;
				x.ins = ins;
				x.value[0] = reg.getValue(ins.src0);
				x.value[1] = reg.getValue(ins.src1);
				x.in = false;
				reg.setValue(ins.dst, x.name);
				break;
			}
	}

	@Override
	public void send(int cycle) {
		// TODO add clock
		for (int i = 0; i < resSize && !exe.full(); ++i)
			if (!res[i].in && res[i].busy && res[i].value[0].ready() && res[i].value[1].ready()) {
				res[i].ins.finish(Instruction.EN, cycle);
				res[i].in = true;
				exe.get(res[i]);
				if(name.equals(ADDER)) {
					if (lines[2] != null)
						lines[2].play();
				} else {
					if (lines[3] != null)
						lines[3].play();
				}
			}
	}

	public void log(HashMap<String, String[][]> logs) {
		System.out.println(name + "ResSta:");

		System.out.format("%-6s%-6s%-6s%-16s%-10s%-10s\n", "name", "busy", "time", "Ins", "val1", "val2");

		for (ResItem it : res) {
			System.out.format("%-6s", it.name);
			System.out.format("%-6s", it.busy ? " --" : " ");
			System.out.format("%-6s",
					it.restTime > -1 ? String.valueOf(it.restTime) : it.restTime == -1 && it.busy ? "wait" : " ");
			System.out.format("%-16s", it.ins != null ? it.ins.raw : " ");
			System.out.format("%-10s", it.value[0] != null ? it.value[0].toString() : " ");
			System.out.format("%-10s", it.value[0] != null ? it.value[1].toString() : " ");
			System.out.println("");
		}
		
		String[][] datas = new String[res.length][6];
		for(int i = 0; i < res.length; ++i){
			ResItem it = res[i];
			datas[i][0] = it.name;
			datas[i][1] = it.busy ? "--" : " ";
			datas[i][2] = it.ins != null ? it.ins.raw : "";
			datas[i][3] = it.restTime > -1 ? String.valueOf(it.restTime) : it.restTime == -1 && it.busy ? "wait" : "";
			datas[i][4] = it.value[0] != null ? it.value[0].toString() : "";
			datas[i][5] = it.value[0] != null ? it.value[1].toString() : "";
		}
		logs.put(name, datas);
	}
	
	public void reset(){
		for(ResItem i : res)
			i.reset();
	}
}
