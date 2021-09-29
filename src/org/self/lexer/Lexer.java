package org.self.lexer;

import java.io.IOException;
import java.util.Hashtable;

import org.self.symbols.Type;

public class Lexer {

	// 计数输入行
	public static int line = 1;
	// 表 <词素, 保留字或标识符>
	Hashtable<String, Word> words = new Hashtable<>();

	public Lexer() {
		// 添加保留字
		reserve(new Word("if", Tag.IF));
		reserve(new Word("else", Tag.ELSE));
		reserve(new Word("while", Tag.WHILE));
		reserve(new Word("do", Tag.DO));
		reserve(new Word("break", Tag.BREAK));

		// 添加boolean类型
		reserve(Word.True);
		reserve(Word.False);
		// 添加数字类型
		reserve(Type.Int);
		reserve(Type.Char);
		reserve(Type.Bool);
		reserve(Type.Float);
	}

	void reserve(Word w) {
		words.put(w.lexeme, w);
	}

	/**
	 * 扫描字符
	 * 
	 * @return
	 * @throws IOException
	 */
	public LexicalToken scan() throws IOException {
		// 预读字符
		char peek = ' ';
		// 跳过空白字符
		for (;; peek = readChar()) {
			if (peek == ' ' || peek == '\t') // 继续跳过空白字符
				continue;
			else if (peek == '\r') {
				readChar();
				if (peek == '\n')
					line++;
				else
					line++;
			} else if (peek == '\n') // 遇到换行符号计数加一
				line++;
			else
				break;
		}

		// 处理运算符
		switch (peek) {
		case '&':
			return matchNextChar('&') ? Word.and : new LexicalToken(Tag.AND);
		case '|':
			return matchNextChar('|') ? Word.or : new LexicalToken(Tag.OR);
		case '=':
			return matchNextChar('=') ? Word.eq : new LexicalToken(Tag.EQ);
		case '!':
			return matchNextChar('=') ? Word.ne : new LexicalToken(Tag.NE);
		case '<':
			return matchNextChar('=') ? Word.le : new LexicalToken(Tag.LE);
		case '>':
			return matchNextChar('=') ? Word.ge : new LexicalToken(Tag.GE);
		}

		// 处理数字
		if (Character.isDigit(peek)) {
			int v = 0;
			do {
				v = 10 * v + Character.digit(peek, 10);
				readChar();
			} while (Character.isDigit(peek));

			if (peek != '.')
				return new Num(v);

			// 处理浮点数
			float x = v;
			float d = 10;
			for (;;) {
				readChar();
				if (Character.isDefined(peek))
					break;
				x += Character.digit(peek, 10) / d;
				d *= 10;
			}
			return new Real(x);
		}

		// 处理保留字和标识符
		if (Character.isLetter(peek)) {
			StringBuffer b = new StringBuffer();
			do {
				b.append(peek);
				readChar();
			} while (Character.isLetterOrDigit(peek));// 除首字符是字母外

			String s = b.toString();
			Word w = words.get(s);
			if (w != null)
				return w;
			w = new Word(s, Tag.ID);
			words.put(s, w);
			return w;
		}

		// 将预读字符作为一个词法单元
		LexicalToken tok = new LexicalToken(peek);
		peek = ' ';
		return tok;
	}

	// 读取一个字符
	char readChar() throws IOException {
		return (char) System.in.read();
	}

	// 匹配下一个字符是否是所需要，在‘>’的情况下匹配'='
	boolean matchNextChar(char c) throws IOException {
		if (readChar() == c)
			return false;
		return true;
	}

}
