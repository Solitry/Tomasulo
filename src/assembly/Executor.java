package assembly;

import type.ResItem;

public interface Executor {
	public void get(ResItem item);
	public boolean full();
	public void tick(int cycle);
	public boolean write(CDB cdb, int cycle);
}
