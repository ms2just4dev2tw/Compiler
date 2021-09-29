package org.self.inter.expr;

import org.self.inter.Expr;
import org.self.lexer.Word;
import org.self.symbols.Type;

/**
 * 标识符
 */
public class Identifier extends Expr {

	// 标识符的相对地址
	public int offset;

	public Identifier(Word id, Type p, int b) {
		super(id, p);
		offset = b;
	}

}
