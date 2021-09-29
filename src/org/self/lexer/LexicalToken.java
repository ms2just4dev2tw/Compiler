package org.self.lexer;

/**
 * 词法单元
 */
public class LexicalToken {

	// 用于语法分析
	public final int tag;

	public LexicalToken(int t) {
		tag = t;
	}

	@Override
	public String toString() {
		return String.valueOf((char) tag);
	}

}
