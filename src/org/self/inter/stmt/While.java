package org.self.inter.stmt;

import org.self.inter.Expr;
import org.self.inter.Stmt;
import org.self.symbols.Type;

/**
 * 
 * @see Break
 * @see DO
 * @see Else
 * @see If
 * @see Seq
 * @see Set
 * @see SetElem
 * @see While
 */
public class While extends Stmt {

	Expr expr;
	Stmt stmt;

	public While() {
		expr = null;
		stmt = null;
	}

	public void init(Expr x, Stmt s) {
		expr = x;
		stmt = s;
		if (expr.type != Type.Bool)
			expr.error("boolean required in while");
	}

	public void gen(int a, int b) {
		after = b;
		expr.jumping(0, b);
		int label = newlabel();
		emitlabel(label);
		stmt.gen(label, a);
		emit("goto L" + a);
	}
}
