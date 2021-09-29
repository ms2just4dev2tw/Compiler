package org.self.inter.stmt;

import org.self.inter.Expr;
import org.self.inter.Stmt;
import org.self.symbols.Type;

/**
 * 语句: do-while
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
public class Do extends Stmt {

	Expr expr;
	Stmt stmt;

	public Do() {
		expr = null;
		stmt = null;
	}

	public void init(Expr x, Stmt s) {
		expr = x;
		stmt = s;
		if (expr.type != Type.Bool)
			expr.error("boolean required in do");
	}

	public void gen(int a, int b) {
		after = b;
		int label = newlabel();
		stmt.gen(a, label);
		emitlabel(label);
		expr.jumping(a, 0);
	}
}
