package assembly;

import java.util.ArrayList;
import java.util.HashMap;

public class Controller {
	private InstrQueue iq = null;
	private Register reg = null;
	
	private MemBuffer mb = null;
	private CalcResSta ar = null;
	private CalcResSta mr = null;
	
	private FlowMemory mem = null;
	private AddExecutor adder = null;
	private MulExecutor multer = null;
	
	private CDB cdb = null;
	
	private int cycle = 0;
	
	public Controller() {
		reg = new Register();
		cdb = new CDB();
		
		mem = new FlowMemory();
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
	
	public void reset(){
		cycle = 0;
		iq.reset();
		reg.reset();
		ar.reset();
		mr.reset();
		mb.reset();
//		mem.reset();
	}
	
	public void addIns(ArrayList<String> list){
		iq.addIns(list);
	}
	
	public void run() {
		++cycle;
		mem.tick(cycle);
		adder.tick(cycle);
		multer.tick(cycle);
		
		cdb.listen(cycle);
		iq.sendIns(cycle);
		
		mb.send(cycle);
		ar.send(cycle);
		mr.send(cycle);
	}
	
	public boolean isFinish(){
		return iq.isFinish();
	}
	
	public int getCycle(){
		return cycle;
	}
	
	public void setMem(int addr, double val){
		mem.store(addr, val);
	}
	
	public void setReg(int num, double val){
		reg.userSetValue(num, val);
	}
	
	public HashMap<String, String[][]> log() {
		HashMap<String, String[][]> logs = new HashMap<String, String[][]>();
		System.out.println("Cycle " + cycle);
		iq.log(logs);
		System.out.println("");
		reg.log(logs);
		System.out.println("");
		ar.log(logs);
		System.out.println("");
		mr.log(logs);
		System.out.println("");
		mb.log(logs);
		System.out.println("");
		mem.log(logs);
		System.out.println("");
		System.out.println("\n\n\n");
		return logs;
	}
	
	public static void main(String[] args){
		ArrayList<String> list = new ArrayList<String>();
		/*
		list.add("LD F2,0");
		list.add("DIVD F0,F2,F4");
		list.add("ADDD F6,F0,F8");
		list.add("ST F6,0");
		list.add("SUBD F8,F10,F8");
		list.add("MULD F6,F10,F8");
		*/
		/*
		list.add("LD F6,34");
		list.add("LD F2,45");
		list.add("MULD F0,F2,F4");
		list.add("SUBD F8,F6,F2");
		list.add("DIVD F10,F0,F6");
		list.add("ADDD F6,F8,F2");
		*/
		
		list.add("LD F6,34");
		list.add("LD F2,45");
		list.add("MULD F0,F2,F4");
		list.add("SUBD F8,F6,F2");
		list.add("DIVD F10,F0,F6");
		list.add("ADDD F6,F8,F2");
		
		/*
		list.add("LD F0,80");
		list.add("MULD F4,F0,F2");
		list.add("ST F4,80");
		list.add("LD F0,80");
		list.add("MULD F4,F0,F2");
		list.add("ST F4,80");
		*/
		Controller con = new Controller();
		con.addIns(list);
		for(int i = 1; i <= 100 && !con.isFinish(); ++i){
			con.run();
			con.log();
		}
	}
}
