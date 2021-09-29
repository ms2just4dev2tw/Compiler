package org.self.inter.expr;

import org.self.inter.Expr;
import org.self.lexer.Word;
import org.self.symbols.Type;

/**
 * 
 * @see Constant 常量
 * @see Id 标识符
 * @see Logical 逻辑运算符
 * @see Op
 * @see Temp
 */
public class Temp extends Expr {

	static int count = 0;
	int number = 0;

	public Temp(Type p) {
		super(Word.temp, p);
		number = ++count;
	}

	@Override
	public String toString() {
		return "t" + number;
	}
}
