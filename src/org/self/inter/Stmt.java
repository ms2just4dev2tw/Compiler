package org.self.inter;

/**
 * 语句
 */
public class Stmt extends Node {

	// 保存语句的下一条指令的标号
	public int after = 0;
	public static Stmt Null = new Stmt();
	// 用于break语句
	public static Stmt Enclosing = Stmt.Null;

	public Stmt() {
	}

	/**
	 * 
	 * @param a 语句开始处的标号
	 * @param b 语句下一条指令的标号
	 */
	public void gen(int a, int b) {
	}

}
