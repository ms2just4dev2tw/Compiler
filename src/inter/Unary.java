package inter;

import lexer.Token;
import symbols.Type;

public class Unary extends Op {

	public Expr expr;

	public Unary(Token tok, Expr x) { // handles minus, for ! see Not
		super(tok, null);
		expr = x;
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
