package type;

public class Value {
	public String Q = null;
	public double V = 0;
	
	public Value(String _Q) {
		Q = _Q;
		V = -1;
	}
	
	public Value(double _V) {
		V = _V;
		Q = null;
	}
	
	public void setValue(String _Q) {
		Q = _Q;
		V = -1;
	}
	
	public void setValue(double _V) {
		V = _V;
		Q = null;
	}
	
	public boolean ready() {
		return Q != null;
	}
}
