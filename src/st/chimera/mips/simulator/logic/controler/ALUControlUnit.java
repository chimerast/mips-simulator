/*
 * 作成日: 2003/08/02
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package st.chimera.mips.simulator.logic.controler;

import st.chimera.mips.simulator.gate.*;
import st.chimera.mips.simulator.logic.*;

/**
 * @author chimera
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
public class ALUControlUnit extends Gate {
	DataPath funct;
	DataPath op;
	DataPath operation;

	public ALUControlUnit() {
		super("ALUControlUnit");
		operation = new DataPath(3);
	}

	public DataPath getAluOperation() {
		return operation;
	}

	public void setAluOp(DataPath path) {
		op = path;
		path.addGate(this);
	}

	public void setFunct(DataPath path) {
		funct = path;
		path.addGate(this);
	}

	public void update() {
		if (op.get(1)) {
			if (funct.get() == ControlUnit.ADD) {
				operation.set(ALU.ADD);
			} else if (funct.get() == ControlUnit.SUB) {
				operation.set(ALU.SUB);
			} else if (funct.get() == ControlUnit.AND) {
				operation.set(ALU.AND);
			} else if (funct.get() == ControlUnit.OR) {
				operation.set(ALU.OR);
			} else if (funct.get() == ControlUnit.SLT) {
				operation.set(ALU.SLT);
			}
		} else if (op.get(0)) {
			operation.set(ALU.SUB);
		} else {
			operation.set(ALU.ADD);
		}
	}
}
