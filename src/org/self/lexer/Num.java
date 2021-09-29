package org.self.lexer;

/**
 * 数值类型
 *  
 * @see Real 浮点类型
 * @see Word
 */
public class Num extends LexicalToken {

	// 存放数值的字段
	public final int value;

	public Num(int v) {
		super(Tag.NUM);
		this.value = v;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
