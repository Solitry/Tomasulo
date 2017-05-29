package assembly;

import type.Instruction;
import type.ResItem;
import type.Value;

public class CalcResSta implements ResSta, CDBReceiver {
	private String name;
	private int resSize = 0;
	private Executor exe = null;
	private Register reg = null;
	private ResItem[] res = null;
	
	public CalcResSta(String _name, int _resSize, Executor _exe, Register _reg) {
		resSize = _resSize;
		exe = _exe;
		reg = _reg;
		// GJH: alloc the resSize
		res = new ResItem[resSize];
		for(int i = 0; i < resSize; ++i)
			res[i].name = name + i;
	}
	
	@Override
	public void receive(ResItem item, double val) {
		// GJH: Auto-generated method stub
		for(ResItem x : res)
			for(Value y : x.value)
				if(y.Q == item.name)
					y.setValue(val);
	}

	@Override
	public boolean full(int arg) {
		// GJH: Auto-generated method stub
		for(ResItem x : res)
			if(!x.busy)
				return false;
		return true;
	}

	@Override
	public void getIns(Instruction ins) {
		// GJH: Auto-generated method stub
		for(ResItem x : res)
			if(!x.busy){
				x.ins = ins;
				x.busy = true;
				x.value[0] = reg.getValue(ins.src0);
				x.value[1] = reg.getValue(ins.src1);
				reg.setValue(ins.dst, x.name);
				break;
			}
	}

	@Override
	public void send(int cycle) {
		// TODO add clock
		for(int i = 0; i < resSize && !exe.full(); ++i)
			if(res[i].busy && res[i].value[0].ready() && res[i].value[1].ready())
				exe.get(res[i]);
	}
}
