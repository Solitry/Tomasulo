package type;

public class ResItem {
	public String name = null;
	
	public Instruction ins = null;
	
	public boolean busy = false;
	
	public Value[] value = new Value[2];
	
	public int addrRely = -1; // for LD/ST
	public Value loadRelyVal = null; // for LD
	
	public int restTime = -1; // for Executor
}
