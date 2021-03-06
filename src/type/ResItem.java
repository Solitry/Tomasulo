package type;

public class ResItem {
	public String name = null;
	
	public Instruction ins = null;
	
	public boolean busy = false;
	public boolean in = false;
	
	public Value[] value = new Value[2];
	
	public boolean bypass = false; // for LD
	public Value loadRelyVal = null; // for LD
	
	public int restTime = -1; // for Executor
	
	public void reset(){
		ins = null;
		in = busy = false;
		restTime = -2;
		value[0] = value[1] = null;
	}
}
