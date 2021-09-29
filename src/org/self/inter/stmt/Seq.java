package org.self.inter.stmt;

import org.self.inter.Stmt;

/**
 * 语句序列
 * 
 * @author TungWang
 */
public class Seq extends Stmt {

	Stmt stmt1, stmt2;

	public Seq(Stmt s1, Stmt s2) {
		stmt1 = s1;
		stmt2 = s2;
	}

	@Override
	public void gen(int a, int b) {
		if (stmt1 == Stmt.Null)
			stmt2.gen(a, b);
		else if (stmt2 == Stmt.Null)
			stmt1.gen(a, b);
		else {
			int label = newlabel();
			stmt1.gen(a, label);
			emitlabel(label);
			stmt2.gen(label, b);
		}
	}

}
