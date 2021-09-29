package org.self.symbols;

import org.self.lexer.Tag;

/**
 * 数组是这个源语言中唯一的构造类型
 * 
 * @author TungWang
 */
public class Array extends Type {

	// 数组的元素类型
	public Type of;
	// 数组的元素个数
	public int size;

	public Array(int sz, Type p) {
		super("[]", Tag.INDEX, sz * p.width);
		size = sz;
		of = p;
	}

	@Override
	public String toString() {
		return "[" + size + "]" + of.toString();
	}

}
