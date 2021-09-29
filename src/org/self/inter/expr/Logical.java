package org.self.inter.expr;

import org.self.inter.Expr;
import org.self.lexer.LexicalToken;
import org.self.symbols.Type;

/**
 * 逻辑运算符
 * 
 * @see Constant 常量
 * @see Id 标识符
 * @see Logical 逻辑运算符
 * @see Op
 * @see Temp
 */
public class Logical extends Expr {

	public Expr expr1, expr2;

	public Logical(LexicalToken tok, Expr expr1, Expr expr2) {
		super(tok, null);
		this.expr1 = expr1;
		this.expr2 = expr2;
		type = check(expr1.type, expr2.type);
		if (type == null)
			error("type error");
	}

	public Type check(Type type1, Type type2) {
		if (type1 == Type.Bool && type2 == Type.Bool)
			return Type.Bool;
		else
			return null;
	}

	@Override
	public Expr gen() {
		int f = newlabel();
		int a = newlabel();
		Temp temp = new Temp(type);
		this.jumping(0, f);
		emit(temp.toString() + " = true");
		emit("goto L" + a);
		emitlabel(f);
		emit(temp.toString() + " = false");
		emitlabel(a);
		return temp;
	}

	@Override
	public String toString() {
		return expr1.toString() + " " + op.toString() + " " + expr2.toString();
	}

}
