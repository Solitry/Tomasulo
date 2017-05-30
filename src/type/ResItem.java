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
		name = null;
		ins = null;
		busy = false;
		in = false;
		value[0] = null;
		value[1] = null;
		bypass = false;
		loadRelyVal = null;
		restTime = -1;
	}
}
