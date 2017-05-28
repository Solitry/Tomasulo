package assembly;

import java.util.ArrayList;

import type.ResItem;

public class CDB {
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
		for (Executor it: sed)
			it.write(this, cycle);
	}
}
