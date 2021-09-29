package org.self.inter.expr.logical;

import org.self.inter.Expr;
import org.self.inter.expr.Logical;
import org.self.lexer.LexicalToken;

/**
 * 逻辑运算符: 与运算
 * 
 * @see And 与运算
 * @see Not 非运算
 * @see Or 或运算
 * @see Rel
 */
public class And extends Logical {

	public And(LexicalToken tok, Expr expr1, Expr expr2) {
		super(tok, expr1, expr2);
	}

	@Override
	public void jumping(int t, int f) {
		int label = f != 0 ? f : newlabel();
		expr1.jumping(label, 0);
		expr2.jumping(t, f);
		if (f == 0)
			emitlabel(label);
	}

}
