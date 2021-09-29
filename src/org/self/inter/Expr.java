package org.self.inter;

import org.self.lexer.LexicalToken;
import org.self.symbols.Type;

/**
 * 表达式
 *
 */
public class Expr extends Node {

	// 结点上的运算符
	public LexicalToken op;
	// 结点上的类型
	public Type type;

	protected Expr(LexicalToken tok, Type p) {
		this.op = tok;
		this.type = p;
	}

	/**
	 * 例如，E = E1+E2，方法gen返回x1+x2，其中x1和x2分别是E1，E2的地址
	 * <p>
	 * 子类通常会重写该方法
	 * 
	 * @return 返回一个项，该项可以作为一个三地址指令的右部
	 */
	public Expr gen() {
		return this;
	}

	/**
	 * 将表达式归纳为一个单一的地址
	 * 
	 * @return 返回表达式 E 的地址
	 */
	public Expr reduce() {
		return this;
	}

	public void jumping(int t, int f) {
		emitjumps(toString(), t, f);
	}

	/**
	 * boolean表达式的跳转代码
	 * 
	 * @param test
	 * @param t 表达式的true口
	 * @param f 表达式的false口
	 */
	public void emitjumps(String test, int t, int f) {
		if (t != 0 && f != 0) {
			emit("if" + test + "goto L" + t);
			emit("goto L" + f);
		} else if (t != 0)
			emit("if" + test + "goto L" + t);
		else if (f != 0)
			emit("iffalse" + test + "goto L" + f);
		else
			;
	}

	@Override
	public String toString() {
		return op.toString();
	}

}
