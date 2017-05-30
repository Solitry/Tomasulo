package assembly;

import java.util.HashMap;

import type.ResItem;
import type.Value;

public class Register implements CDBReceiver {
	public final static int REG_NUM = 32;
	
	private Value[] reg = new Value[REG_NUM];
	
	private boolean[] use = new boolean[REG_NUM];
	
	public Register() {
		for (int i = 0; i < REG_NUM; ++i)
			reg[i] = new Value(0);
		for (int i = 0; i < REG_NUM; ++i)
			use[i] = false;
	}
	
	public Value getValue(int num) {
		Value ret = null;
		if (reg[num].Q != null)
			ret = new Value(reg[num].Q);
		else
			ret = new Value(reg[num].V);
		return ret;
	}
	
	public void setValue(int num, String name) {
		reg[num].setValue(name);
		use[num] = true;
	}

	@Override
	public void receive(ResItem item, double val) {
		// GJH: update value
		if (item.ins.dst >= 0 && reg[item.ins.dst].wait(item.name))
			reg[item.ins.dst].setValue(val);
	}
	
	public void log(HashMap<String, String[][]> logs) {
		System.out.println("Register:");

		System.out.format("%-4s%-10s\n", "id", "value");

		for (int i = 0; i < REG_NUM; ++i)
			if (use[i]) {
				System.out.format("%-4d", i);
				System.out.format("%-10s\n", reg[i].toString());
			}
		
		String[][] datas = new String[REG_NUM][2];
		for(int i = 0; i < REG_NUM; ++i){
			datas[i][0] = "F" + i;
			datas[i][1] = use[i] ? reg[i].toString() : "";
		}
		logs.put("Reg", datas);
	}
	
	public void reset(){
		for(int i = 0; i < REG_NUM; ++i){
			reg[i].setValue(0);
			use[i] = false;
		}
	}
}
