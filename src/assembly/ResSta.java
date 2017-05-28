package assembly;

import type.Instruction;

public interface ResSta {
	public boolean full(int arg);
	public void getIns(Instruction ins);
	public void send(int cycle);
}
