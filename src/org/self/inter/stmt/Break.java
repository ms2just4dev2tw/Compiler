package org.self.inter.stmt;

import org.self.inter.Stmt;

/**
 * 中断语句
 */
public class Break extends Stmt {

	Stmt stmt;

	public Break() {
		if (Stmt.Enclosing == Stmt.Null)
			error("unenclosed break");
		stmt = Stmt.Enclosing;
	}

	@Override
	public void gen(int a, int b) {
		stmt.emit("goto L" + stmt.after);
	}

}
