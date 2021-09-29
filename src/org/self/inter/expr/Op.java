package org.self.inter.expr;

import org.self.inter.Expr;
import org.self.lexer.LexicalToken;
import org.self.symbols.Type;

/**
 * 
 * 
 * @see Constant 常量
 * @see Id 标识符
 * @see Logical 逻辑运算符
 * @see Op
 * @see Temp
 * 
 */
public class Op extends Expr {

	public Op(LexicalToken tok, Type p) {
		super(tok, p);
	}

	/**
	 * 重写Expr的reduce方法
	 * 
	 * @return 
	 */
	@Override
	public Expr reduce() {
		Expr x = gen();
		Temp t = new Temp(type);
		emit(t.toString() + "=" + x.toString());
		return t;
	}
}
