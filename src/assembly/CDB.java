package assembly;

import java.util.ArrayList;

import type.ResItem;

public class CDB {
	
	public final boolean MULTI_CDB = false;
	
	private ArrayList<CDBReceiver> rec = new ArrayList<CDBReceiver>();
	private ArrayList<CDBSender> sed = new ArrayList<CDBSender>();
	
	public void addReceiver(CDBReceiver _rec) {
		rec.add(_rec);
	}
	
	public void addSender(CDBSender _sed) {
		sed.add(_sed);
	}
	
	public void receive(ResItem item, double val) {
		// set res free
		item.busy = false;
		// broadcast
		for (CDBReceiver it: rec)
			it.receive(item, val);
	}
	
	public void listen(int cycle) {
		// TODO add clock
		for (CDBSender it: sed) {
			boolean ret = it.write(this, cycle);
			if (ret && !MULTI_CDB)
				break;
		}
	}
}
