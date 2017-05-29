package assembly;

import java.util.ArrayList;

public class Controller {
	private InstrQueue iq = null;
	private Register reg = null;
	
	private MemBuffer mb = null;
	private CalcResSta ar = null;
	private CalcResSta mr = null;
	
	private Memory mem = null;
	private AddExecutor adder = null;
	private MulExecutor multer = null;
	
	private CDB cdb = null;
	
	
	public Controller() {
		reg = new Register();
		cdb = new CDB();
		
		mem = new Memory();
		adder = new AddExecutor();
		multer = new MulExecutor();
		
		mb = new MemBuffer(mem, reg);
		ar = new CalcResSta(CalcResSta.ADDER, adder, reg);
		mr = new CalcResSta(CalcResSta.MULTER, multer, reg);
		
		iq = new InstrQueue(mb, ar, mr);
		
		cdb.addReceiver(mb);
		cdb.addReceiver(ar);
		cdb.addReceiver(mr);
		cdb.addReceiver(reg);
		
		cdb.addSender(mem);
		cdb.addSender(adder);
		cdb.addSender(multer);
		cdb.addSender(mb);
	}
	
	public void run(int cycle) {
		mem.tick(cycle);
		adder.tick(cycle);
		multer.tick(cycle);
		
		cdb.listen(cycle);
		
		iq.sendIns(cycle);
		
		mb.send(cycle);
		ar.send(cycle);
		mr.send(cycle);
	}
	
	public static void main(String[] args){
		ArrayList<String> list = new ArrayList<String>();
		list.add("DIVD F0,F2,F4");
		list.add("ADDD F6,F0,F8");
		list.add("ST F6,0");
		list.add("SUBD F8,F10,F8");
		list.add("MULD F6,F10,F8");
		
		Controller con = new Controller();
		con.iq.addIns(list);
		for(int i = 0; i < 50 && !con.iq.isFinish(); ++i){
			System.err.println("Tick " + i);
			con.run(i);
		}
	}
}
