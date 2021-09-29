package org.self.inter.expr.op;

import org.self.inter.Expr;
import org.self.inter.expr.Op;
import org.self.lexer.LexicalToken;
import org.self.symbols.Type;

/**
 * 单目运算符
 * 
 * @see Access 数组访问
 * @see Arith 双目运算符
 */
public class Unary extends Op {

	public Expr expr;

	public Unary(LexicalToken tok, Expr expr) {
		super(tok, null);
		this.expr = expr;
		type = Type.max(Type.Int, expr.type);
		if (type == null)
			error("type error");
	}

	@Override
	public Expr gen() {
		return new Unary(op, expr.reduce());
	}

	@Override
	public String toString() {
		return op.toString() + " " + expr.toString();
	}

}
