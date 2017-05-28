package assembly;

import type.ResItem;

public class CalcExecutor implements Executor {
	private int[] insRunLabel = new int[2];
	private int[] insRunTime = new int[2];
	
	public CalcExecutor(int i1, int it1, int i2, int it2) {
		insRunLabel[0] = i1;
		insRunLabel[1] = i2;
		insRunTime[0] = it1;
		insRunTime[1] = it2;
	}
	
	@Override
	public void get(ResItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(CDB cdb, int cycle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean full() {
		// TODO Auto-generated method stub
		return false;
	}
}
