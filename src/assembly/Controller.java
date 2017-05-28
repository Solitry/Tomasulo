package assembly;

import type.Instruction;

public class Controller {
	private InstrQueue iq = null;
	private Register reg = null;
	
	private MemBuffer mb = null;
	private CalcResSta ar = null;
	private CalcResSta mr = null;
	
	private Memory mem = null;
	private CalcExecutor adder = null;
	private CalcExecutor multer = null;
	
	private CDB cdb = null;
	
	
	public Controller() {
		reg = new Register();
		cdb = new CDB();
		
		mem = new Memory();
		adder = new CalcExecutor(Instruction.INSTR_ADD_ID, Instruction.INSTR_SUB_ID, 2, 2);
		multer = new CalcExecutor(Instruction.INSTR_MUL_ID, Instruction.INSTR_DIV_ID, 10, 40);
		
		mb = new MemBuffer(mem, reg);
		ar = new CalcResSta(3, adder, reg);
		mr = new CalcResSta(3, multer, reg);
		
		iq = new InstrQueue(mb, ar, mr);
		
		cdb.addReceiver(mb);
		cdb.addReceiver(ar);
		cdb.addReceiver(mr);
		cdb.addReceiver(reg);
		
		cdb.addSender(mem);
		cdb.addSender(adder);
		cdb.addSender(multer);
	}
	
	public void run(int cycle) {
		cdb.listen(cycle);
		
		mb.send(cycle);
		ar.send(cycle);
		mr.send(cycle);
		
		iq.sendIns(cycle);
	}
}
