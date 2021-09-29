package org.self.lexer;

/**
 * 类<i>Word</i>用于管理
 * <ul>
 * <li>保留字</li>
 * <li>标识符</li>
 * <li>像<i>&&</i>这样的复合词法单元的词素</li>
 * </ul>
 * 
 */
public class Word extends LexicalToken {

	public String lexeme = "";

	public Word(String s, int tag) {
		super(tag);
		lexeme = s;
	}

	public static final Word and = new Word("&&", Tag.AND), or = new Word("||", Tag.OR),
			//
			eq = new Word("==", Tag.EQ), ne = new Word("!=", Tag.NE),
			//
			le = new Word("<=", Tag.LE), ge = new Word(">=", Tag.GE),

			//
			minus = new Word("minus", Tag.MINUS),
			//
			True = new Word("true", Tag.TRUE),
			//
			False = new Word("false", Tag.FALSE),
			//
			temp = new Word("t", Tag.TEMP);

	@Override
	public String toString() {
		return lexeme;
	}

}
