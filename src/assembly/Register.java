package assembly;

import type.ResItem;
import type.Value;

public class Register implements CDBReceiver {
	public final static int REG_NUM = 32;
	
	private Value[] reg = new Value[REG_NUM];
	
	public Register() {
		for (int i = 0; i < REG_NUM; ++i)
			reg[i] = new Value(0);
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
	}

	@Override
	public void receive(ResItem item, double val) {
		// GJH: update value
		if (item.ins.dst >= 0)
			reg[item.ins.dst].setValue(val);
	}
}
