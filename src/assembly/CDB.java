package assembly;

import java.util.ArrayList;

import type.ResItem;

public class CDB {
	
	public final boolean MULTI_CDB = false;
	
	private ArrayList<CDBReceiver> rec = new ArrayList<CDBReceiver>();
	private ArrayList<Executor> sed = new ArrayList<Executor>();
	
	public void addReceiver(CDBReceiver _rec) {
		rec.add(_rec);
	}
	
	public void addSender(Executor _sed) {
		sed.add(_sed);
	}
	
	public void receive(ResItem item, double val) {
		for (CDBReceiver it: rec)
			it.receive(item, val);
	}
	
	public void listen(int cycle) {
		// TODO access clock
		for (Executor it: sed) {
			boolean ret = it.write(this, cycle);
			if (ret && !MULTI_CDB)
				break;
		}
	}
}
