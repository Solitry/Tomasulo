package assembly;

import type.Instruction;
import type.ResItem;

public class CalcResSta implements ResSta, CDBReceiver {
	private int resSize = 0;
	private CalcExecutor exe = null;
	private Register reg = null;
	
	public CalcResSta(int _resSize, CalcExecutor _exe, Register _reg) {
		resSize = _resSize;
		exe = _exe;
		reg = _reg;
		// TODO alloc the res
	}
	
	@Override
	public void receive(ResItem item, double val) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean full(int arg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void getIns(Instruction ins) {
		// TODO Auto-generated method stub

	}

	@Override
	public void send(int cycle) {
		// TODO Auto-generated method stub

	}

}
