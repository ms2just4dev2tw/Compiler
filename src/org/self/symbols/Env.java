package org.self.symbols;

import java.util.Hashtable;

import org.self.inter.expr.Identifier;
import org.self.lexer.LexicalToken;

/**
 * 包<i>symbols</i>实现了
 * <ul>
 * <li>符号表</li>
 * <li>类型</li>
 * </ul>
 * 类<i>Env</i>
 * 
 * 链接的符号表
 */
public class Env {

	// 上一层块作用域的符号表引用
	protected Env prev;
	// 符号表的存储结构 <对应于标识符的词法单元对象引用, 标识符>
	private Hashtable<LexicalToken, Identifier> symbolTable;

	public Env(Env env) {
		// 将先决环境变量压栈
		prev = env;
		symbolTable = new Hashtable<>();
	}

	/**
	 * 将标识符存入当前环境的符号表
	 * 
	 * @param w
	 * @param id 标识符
	 */
	public void put(LexicalToken w, Identifier id) {
		symbolTable.put(w, id);
	}

	/**
	 * 从当前环境开始搜索变量， 如果当前环境不存在该变量，开始出栈直到环境变量为空为止
	 * 
	 * @param w
	 * @return
	 */
	public Identifier get(LexicalToken w) {
		for (Env env = this; env != null; env = env.prev) {
			Identifier found = env.symbolTable.get(w);
			if (found != null)
				return found;
		}
		return null;
	}

}
