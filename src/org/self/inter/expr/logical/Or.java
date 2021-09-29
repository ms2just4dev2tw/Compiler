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
public class Or extends Logical {

	public Or(LexicalToken tok, Expr expr1, Expr expr2) {
		super(tok, expr1, expr2);
	}

	@Override
	public void jumping(int t, int f) {
		int label = t != 0 ? t : newlabel();
		expr1.jumping(label, 0);
		expr2.jumping(t, f);
		if (t == 0)
			emitlabel(label);
	}

}
