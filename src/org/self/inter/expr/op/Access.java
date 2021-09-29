package org.self.inter.expr.op;

import org.self.inter.Expr;
import org.self.inter.expr.Identifier;
import org.self.inter.expr.Op;
import org.self.lexer.Tag;
import org.self.lexer.Word;
import org.self.symbols.Type;

/**
 * 数组访问
 * 
 * @see Arith 双目运算符
 * @see Unary 单目运算符
 */
public class Access extends Op {

	public Identifier array;
	public Expr index;

	public Access(Identifier id, Expr i, Type p) {
		super(new Word("[]", Tag.INDEX), p);
		array = id;
		index = i;
	}

	@Override
	public Expr gen() {
		return new Access(array, index.reduce(), type);
	}

	@Override
	public void jumping(int t, int f) {
		emitjumps(reduce().toString(), t, f);
	}

	@Override
	public String toString() {
		return array.toString() + "[" + index.toString() + "]";
	}

}
