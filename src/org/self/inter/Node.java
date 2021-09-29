package org.self.inter;

import org.self.lexer.Lexer;

/**
 * 抽象语法树中的节点
 * <p>
 * 
 * @see Expr 子类：表达式节点
 * @see Stmt 子类：语句节点
 * 
 */
public class Node {

	// 保存本结点的构造在源程序中的行号
	int lexline = 0;
	// 用于生成三地址码
	static int lables = 0;

	// 访问权限控制在同包
	Node() {
		lexline = Lexer.line;
	}

	// 打印错误信息
	public void error(String s) {
		throw new Error("near line " + lexline + ": " + s);
	}

	// lables，newlabel，emitlabel，emit用于生成三地址码
	public int newlabel() {
		return ++lables;
	}

	public void emitlabel(int i) {
		System.out.println("L" + i + ":");
	}

	public void emit(String s) {
		System.out.println("\t" + s);
	}

}
