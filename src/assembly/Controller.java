package assembly;

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
}
