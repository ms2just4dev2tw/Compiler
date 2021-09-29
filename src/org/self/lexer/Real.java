package org.self.lexer;

/**
 * 类<i>Real</i>用于处理浮点数
 * 
 * @see Num 数值类型
 * @see Real 浮点类型
 * @see Word
 */
public class Real extends LexicalToken {

	// 存放浮点数值
	public final float value;

	public Real(float v) {
		super(Tag.REAL);
		this.value = v;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
