package type;

public class Instruction {
	public final static String INSTR_ADD = "ADDD"; // 0
	public final static String INSTR_SUB = "SUBD"; // 1
	public final static String INSTR_MUL = "MULD"; // 2
	public final static String INSTR_DIV = "DIVD"; // 3
	public final static String INSTR_LD = "LD"; // 4
	public final static String INSTR_ST = "ST"; // 5

	public final static String[] INSTR_TYPE = { INSTR_ADD, INSTR_SUB, INSTR_MUL, INSTR_DIV, INSTR_LD, INSTR_ST };

	public final static int INSTR_ADD_ID = 0;
	public final static int INSTR_SUB_ID = 1;
	public final static int INSTR_MUL_ID = 2;
	public final static int INSTR_DIV_ID = 3;
	public final static int INSTR_LD_ID = 4;
	public final static int INSTR_ST_ID = 5;

	public String opName = null;
	public int opLabel = -1;

	public int insLabel = -1;
	
	public String raw = null;
	
	/*
	 * for normal instructions:
	 *   src0, src1	: the source-register's label
	 *   dst		: the destination-register's label
	 * for ST instructions:
	 *   src0		: the register's label
	 *   src1		: the memory address
	 *   dst set to -1
	 * for LD instructions:
	 *   dst		: the register's label
	 *   src1		: the memory address
	 *   src0 set to -1
	 */
	public int src0, src1, dst;
	
	public final static int STAGE_NUM = 4;
	public final static int ID = 0;
	public final static int EN = 1;
	public final static int EX = 2;
	public final static int WB = 3;
	public int[] finishTime = new int[STAGE_NUM]; // -1 means no value

	public void finish(int stage, int cycle) {
		//System.err.println("ins " + insLabel + " stage " + stage + " finish");
		finishTime[stage] = cycle;
	}

	public Instruction(String str, int count) throws Exception {
		// GJH: translate str into instruction-type
		//System.err.println(str + " " + count);
		int p0 = str.indexOf(' ');
		if(str.charAt(p0 + 1) != 'F')
			throw new Exception();
		
		opName = str.substring(0, p0);
		for (int i = 0; i < 6; ++i)
			if (opName.equals(INSTR_TYPE[i]))
				opLabel = i;
		int p1 = str.indexOf(',');
		if (opLabel < 4 && opLabel >= 0) {
			int p2 = str.indexOf(',', p1 + 1);
			if(str.charAt(p1 + 1) != 'F' || str.charAt(p2 + 1) != 'F')
				throw new Exception();
			dst = Integer.parseInt(str.substring(p0 + 2, p1));
			src0 = Integer.parseInt(str.substring(p1 + 2, p2));
			src1 = Integer.parseInt(str.substring(p2 + 2));
		} else if (opLabel == INSTR_LD_ID) {
			dst = Integer.parseInt(str.substring(p0 + 2, p1));
			src1 = Integer.parseInt(str.substring(p1 + 1));
			src0 = -1;
		} else if (opLabel == INSTR_ST_ID) {
			src0 = Integer.parseInt(str.substring(p0 + 2, p1));
			src1 = Integer.parseInt(str.substring(p1 + 1));
			dst = -1;
		} else
			throw new Exception();
		insLabel = count;
		
		for (int i = 0; i < STAGE_NUM; ++i)
			finishTime[i] = -1;
		raw = str;
	}

	public boolean isFinish(){
		return finishTime[WB] != -1;
	}
	
	public void reset(){
		for(int i = 0; i < STAGE_NUM; ++i)
			finishTime[i] = -1;
	}
	
	public static void main(String[] args) {
		try {
			Instruction ins = new Instruction("ADDD F6,F0,F8", 0);
			System.out.println(ins.opLabel + " " + ins.dst + " " + ins.src0 + " " + ins.src1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
