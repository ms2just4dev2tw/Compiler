package org.self.inter.stmt;

import org.self.inter.Expr;
import org.self.inter.Stmt;
import org.self.symbols.Type;

/**
 * 
 * @author TungWang
 */
public class Else extends Stmt {

	Expr expr;
	Stmt stmt1, stmt2;

	public Else(Expr x, Stmt s1, Stmt s2) {
		expr = x;
		stmt1 = s1;
		stmt2 = s2;
		if (expr.type != Type.Bool)
			expr.error("boolean required in if");
	}

	@Override
	public void gen(int a, int b) {
		int label1 = newlabel(); // label1用于语句stmt1
		int label2 = newlabel(); // label2用于语句stmt2
		// 为真时控制流穿越stmt1
		expr.jumping(0, label2);
		emitlabel(label1);
		stmt1.gen(label1, b);
		emit("goto L" + b);
		emitlabel(label2);
		stmt2.gen(label2, b);
	}
}
