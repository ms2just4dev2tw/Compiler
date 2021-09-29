package org.self.symbols;

import org.self.lexer.Tag;
import org.self.lexer.Word;

public class Type extends Word {

	/**
	 * 基本类型保留字, 由于Tag.BASIC, 语法分析器以同样方式处理
	 */
	public static final Type Int = new Type("int", Tag.BASIC, 4),
			//
			Float = new Type("float", Tag.BASIC, 8),
			//
			Char = new Type("char", Tag.BASIC, 1),
			//
			Bool = new Type("bool", Tag.BASIC, 1);

	// 用于存储分配的字节长度
	public int width;

	public Type(String s, int tag, int w) {
		super(s, tag);
		width = w;
	}

	/**
	 * 数字类型包括
	 * <ul>
	 * <li>Type.Char 字符</li>
	 * <li>Type.Int 整型</li>
	 * <li>Type.Float 浮点型</li>
	 * </ul>
	 * 
	 * @param p
	 * @return
	 */
	public static boolean numeric(Type p) {
		if (p == Type.Char || p == Type.Int || p == Type.Float)
			return true;
		return false;
	}

	/**
	 * <h4>类型转换 </h4>
	 * <p>
	 * 当一个算术运算符应用于两个数字类型时， 得到 p1,p2 中最大的数字类型
	 * 
	 * @param p1 数字类型
	 * @param p2 数字类型
	 * @return
	 */
	public static Type max(Type p1, Type p2) {
		if (!numeric(p1) || !numeric(p2))
			return null;
		else if (p1 == Type.Float || p2 == Type.Float)
			return Type.Float;
		else if (p1 == Type.Int || p2 == Type.Int)
			return Type.Int;
		else
			return Type.Char;
	}

}
