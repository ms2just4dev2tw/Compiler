package org.self.inter.expr;

import org.self.inter.Expr;
import org.self.lexer.LexicalToken;
import org.self.lexer.Num;
import org.self.lexer.Word;
import org.self.symbols.Type;

/**
 * 表达式: 常量
 * 
 * @see Constant 常量
 * @see Id 标识符
 * @see Logical 逻辑运算符
 * @see Op
 * @see Temp
 */
public class Constant extends Expr {

	public static final Constant True = new Constant(Word.True, Type.Bool), False = new Constant(Word.False, Type.Bool);

	public Constant(LexicalToken tok, Type p) {
		super(tok, p);
	}

	public Constant(int i) {
		super(new Num(i), Type.Int);
	}

	public void jumoing(int t, int f) {
		if (this == True && t != 0)
			emit("goto L" + t);
		else if (this == False && f != 0)
			emit("goto L" + f);
	}

}
