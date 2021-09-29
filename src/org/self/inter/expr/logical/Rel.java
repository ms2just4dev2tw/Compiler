package org.self.inter.expr.logical;

import org.self.inter.Expr;
import org.self.inter.expr.Logical;
import org.self.lexer.LexicalToken;
import org.self.symbols.Array;
import org.self.symbols.Type;

/**
 * 
 * @see And 与运算
 * @see Not 非运算
 * @see Or 或运算
 * @see Rel
 */
public class Rel extends Logical {

	public Rel(LexicalToken tok, Expr x1, Expr x2) {
		super(tok, x1, x2);
	}

	@Override
	public Type check(Type p1, Type p2) {
		if (p1 instanceof Array || p2 instanceof Array)
			return null;
		else if (p1 == p2)
			return Type.Bool;
		else
			return null;
	}

	@Override
	public void jumping(int t, int f) {
		Expr a = expr1.reduce();
		Expr b = expr2.reduce();
		String test = a.toString() + " " + op.toString() + " " + b.toString();
		emitjumps(test, t, f);
	}

}
