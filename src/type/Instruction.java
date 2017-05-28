package type;

public class Instruction {
	public final static String INSTR_ADD = "ADDD";	// 0
	public final static String INSTR_SUB = "SUBD";	// 1
	public final static String INSTR_MUL = "MULD";	// 2
	public final static String INSTR_DIV = "DIVD";	// 3
	public final static String INSTR_LD = "LD";		// 4
	public final static String INSTR_ST = "ST";		// 5
	
	public final static String[] INSTR_TYPE = {INSTR_ADD, INSTR_SUB, INSTR_MUL, INSTR_DIV, INSTR_LD, INSTR_ST};
	
	public final static int INSTR_ADD_ID = 0;
	public final static int INSTR_SUB_ID = 1;
	public final static int INSTR_MUL_ID = 2;
	public final static int INSTR_DIV_ID = 3;
	public final static int INSTR_LD_ID = 4;
	public final static int INSTR_ST_ID = 5;
	
	public String opName = null;
	public int opLabel = -1;
	
	public int insLabel = -1;
	
	/*
	 * for normal instructions:
	 *   src0, src1	: the source-register's label
	 *   dst		: the destination-register's label
	 * for LD/ST instructions:
	 *   src0		: the register's label
	 *   src1		: the memory address
	 */
	public int src0, src1, dst;
	
	public Instruction(String str) {
		// TODO: translate str into instruction-type
	}
}
