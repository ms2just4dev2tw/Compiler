package org.self.inter.expr.logical;

import org.self.inter.Expr;
import org.self.inter.expr.Logical;
import org.self.lexer.LexicalToken;

/**
 * 
 * @see And 与运算
 * @see Not 非运算
 * @see Or 或运算
 * @see Rel
 */
public class Not extends Logical {

	public Not(LexicalToken tok, Expr expr) {
		super(tok, expr, expr);
	}

	@Override
	public void jumping(int t, int f) {
		expr2.jumping(t, f);
	}

	@Override
	public String toString() {
		return op.toString() + " " + expr2.toString();
	}

}
