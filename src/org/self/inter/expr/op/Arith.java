package org.self.inter.expr.op;

import org.self.inter.Expr;
import org.self.inter.expr.Op;
import org.self.lexer.LexicalToken;
import org.self.symbols.Type;

/**
 * 双目运算符
 * 
 * @see Access 数组访问
 * @see Unary 单目运算符
 */
public class Arith extends Op {

	public Expr expr1, expr2;

	public Arith(LexicalToken tok, Expr x1, Expr x2) {
		super(tok, null);
		expr1 = x1;
		expr2 = x2;
		this.type = Type.max(expr1.type, expr2.type);

		if (type == null)
			error("type error");
	}

	@Override
	public Expr gen() {
		return new Arith(op, expr1.reduce(), expr2.reduce());
	}

	@Override
	public String toString() {
		return expr1.toString() + " " + op.toString() + " " + expr2.toString();
	}

}
