package type;

public class Value {
	public String Q = null;
	public double V = 0;
	
	public Value(String _Q) {
		Q = _Q;
		V = 0;
	}
	
	public Value(double _V) {
		V = _V;
		Q = null;
	}
	
	public void setValue(String _Q) {
		Q = _Q;
		V = 0;
	}
	
	public void setValue(double _V) {
		V = _V;
		Q = null;
	}
	
	public boolean ready() {
		return Q == null;
	}
	
	public boolean wait(String name) {
		//System.err.println("wait " + Q + " " + name);
		return Q != null && Q.equals(name);
	}
	
	public String toString() {
		if (Q != null)
			return Q;
		else
			return String.format("%-8f", V);
	}
}
