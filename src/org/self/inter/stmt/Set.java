package org.self.inter.stmt;

import org.self.inter.Expr;
import org.self.inter.Stmt;
import org.self.inter.expr.Identifier;
import org.self.symbols.Type;

/**
 * 实现左部为标识符，右部为表达式的赋值语句
 * 
 * @author TungWang
 */
public class Set extends Stmt {

	public Identifier id;
	public Expr expr;

	public Set(Identifier id, Expr expr) {
		this.id = id;
		this.expr = expr;
		if (check(id.type, expr.type) == null)
			error("type error");
	}

	public Type check(Type p1, Type p2) {
		if (Type.numeric(p1) && Type.numeric(p2))
			return p2;
		else if (p1 == Type.Bool && p2 == Type.Bool)
			return p2;
		else
			return null;
	}

	@Override
	public void gen(int a, int b) {
		emit(id.toString() + " = " + expr.gen().toString());
	}

}
