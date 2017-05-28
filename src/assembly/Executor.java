package assembly;

import type.ResItem;

public interface Executor {
	public void get(ResItem item);
	public boolean full();
	public void write(CDB cdb, int cycle);
}
