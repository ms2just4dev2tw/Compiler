package org.self.parser;

import java.io.IOException;

import org.self.inter.Expr;
import org.self.inter.Stmt;
import org.self.inter.expr.Constant;
import org.self.inter.expr.Identifier;
import org.self.inter.expr.logical.And;
import org.self.inter.expr.logical.Not;
import org.self.inter.expr.logical.Or;
import org.self.inter.expr.logical.Rel;
import org.self.inter.expr.op.Access;
import org.self.inter.expr.op.Arith;
import org.self.inter.expr.op.Unary;
import org.self.inter.stmt.Break;
import org.self.inter.stmt.Do;
import org.self.inter.stmt.Else;
import org.self.inter.stmt.If;
import org.self.inter.stmt.Seq;
import org.self.inter.stmt.Set;
import org.self.inter.stmt.SetElem;
import org.self.inter.stmt.While;
import org.self.lexer.Lexer;
import org.self.lexer.LexicalToken;
import org.self.lexer.Num;
import org.self.lexer.Tag;
import org.self.lexer.Word;
import org.self.symbols.Array;
import org.self.symbols.Env;
import org.self.symbols.Type;

/**
 * program -> block 
 * <p>
 * 		block -> {decls stmts}
 * <p>
 * 		decls -> decls decl | <b>EPSILON</b> 
 * <p>
 * 		decl ->type <b>id</b>; 
 * <p>
 * 		type -> type [<b>num</b>] | <b>basic</b> 
 * <p>
 * 		stmts -> stmts stmt | <b>EPSILON</b>
 * <p>
 * <h5>语句</h5>
 * <p>
 * stmt -> loc = bool; 
 * <p>
 * 			| <b>if</b> ( bool ) stmt 
 * <p>
 * 			| <b>if</b> ( bool ) stmt <b>else</b> stmt 
 * <p>
 * 			| <b>while</b> (bool ) stmt 
 * <p>
 * 			| <b>do</b> stmt <b>while</b> ( bool ); 
 * <p>
 * 			| <b>break</b>; 
 * <p>
 * 			| block 
 * <p>
 * 		loc -> loc [ bool ] | <b>id</b>
 * <p>
 * <h5>布尔表达式</h5>
 * <p>
 * bool -> bool || join | join 
 * <p>
 * join -> join && equality | equality 
 * <p>
 * equality -> equality == rel | equality != rel | rel 
 * <p>
 * rel -> expr < expr | expr <= expr |expr >= expr | expr > expr |expr 
 * <p>
 * expr -> expr + term | expr - term | term
 * <p>
 * term -> term * unary | term / unary | unary 
 * <p>
 * unary -> ! unary | - unary |factor 
 * <p>
 * factor -> ( bool ) | loc | <b>num</b> | <b>real</b> | <b>true</b> | <b>false</b>
 * <p>
 * 
 * @author TungWang
 */
public class Parser {

	int used = 0; // 用于变量声明的存储位置
	Env top = null; // 当前或顶层作用域的环境变量
	private Lexer lexer;// 词法分析器
	private LexicalToken look;// 向前看词法单元

	public Parser(Lexer lexer) throws IOException {
		this.lexer = lexer;
		move();
	}

	/**
	 *  词法分析器扫描一次, 使得向前看词法单元移动
	 *  
	 * @throws IOException
	 */
	private void move() throws IOException {
		look = lexer.scan();
	}

	/**
	 *  错误信息打印
	 *  
	 * @param s
	 */
	private void error(String s) {
		throw new Error("near line " + Lexer.line + ": " + s);
	}

	/**
	 * 如果向前看词法单元的 tag == t 向前看词法单元可以向前移动
	 * 
	 * @param t 匹配字符
	 * @throws IOException
	 */
	private void match(int t) throws IOException {
		if (look.tag == t)
			move();
		else
			error("syntax error");
	}

	/**
	 * program -> block
	 */
	public void program() throws IOException {
		Stmt stmt = block();

		// 生成中间代码
		int begin = stmt.newlabel();
		int after = stmt.newlabel();
		stmt.emitlabel(begin);
		stmt.gen(begin, after);
		stmt.emitlabel(after);
	}

	/**
	 * 块作用域
	 * <p>
	 * block -> {decls stmts}
	 * <p>
	 * {@link Parser#block()} 分解成 {@link Parser#decls() 多声明} 和 {@link Parser#stmts() 多语句}
	 * 
	 * @return Stmt 语句节点
	 * @throws IOException
	 */
	private Stmt block() throws IOException {
		// 进入块级作用域, 环境变量入栈
		match('{');
		Env preEnv = top;
		top = new Env(top);
		// 处理多声明
		decls();
		// 处理多语句
		Stmt stmt = stmts();
		// 退出块级作用域, 环境变量出栈
		match('}');
		top = preEnv;
		return stmt;
	}

	/**
	 * 声明会被处理为符号表中关于标识符的条目
	 * <p>
	 * decls -> decls decl | <b>EPSILON</b> 
	 * <p>
	 * decl ->type <b>id</b>; 
	 * 
	 * @throws IOException
	 */
	private void decls() throws IOException {
		// 如果是基础类型
		while (look.tag == Tag.BASIC) {
			Type p = type();
			LexicalToken tok = look;
			match(Tag.ID);
			match(';');
			Identifier id = new Identifier((Word) tok, p, used);
			top.put(tok, id);
			used += p.width;
		}
	}

	/**
	 * type -> type [<b>num</b>] | <b>basic</b> 
	 * 
	 * @return
	 * @throws IOException
	 */
	private Type type() throws IOException {
		Type p = (Type) look;
		match(Tag.BASIC);

		// 对于数组类型的处理
		if (look.tag != '[')
			return p; // T -> basic
		else
			return dims(p); // 返回数组类型
	}

	/**
	 * 数组类型
	 * 
	 * @param p
	 * @return
	 * @throws IOException
	 */
	private Type dims(Type p) throws IOException {
		match('[');
		LexicalToken tok = look;
		match(Tag.NUM);
		match(']');

		// 处理多维数组的情况
		if (look.tag == '[')
			p = dims(p);
		return new Array(((Num) tok).value, p);
	}

	/**
	 * 多语句分析
	 * <p>
	 * stmts -> stmts stmt | <b>EPSILON</b>
	 * 
	 * @return
	 * @throws IOException
	 */
	private Stmt stmts() throws IOException {
		if (look.tag == '}')
			return Stmt.Null;
		else
			return new Seq(stmt(), stmts());
	}

	/**
	 * 单语句分析
	 * <p>
	 * stmt -> loc = bool; 
	 *  <p>
	 * 			| <b>if</b> ( bool ) stmt 
	 * <p>
	 * 			| <b>if</b> ( bool ) stmt <b>else</b> stmt 
	 * <p>
	 * 			| <b>while</b> (bool ) stmt 
	 * <p>
	 * 			| <b>do</b> stmt <b>while</b> ( bool ); 
	 * <p>
	 * 			| <b>break</b>; 
	 * <p>
	 * 			| block 
	 * <p>
	 * 		loc -> loc [ bool ] | <b>id</b>
	 * 
	 * @return
	 * @throws IOException
	 */
	private Stmt stmt() throws IOException {
		Expr x;
		Stmt s, s1, s2;
		Stmt savedStmt;

		switch (look.tag) {
		case ';':
			move();
			return Stmt.Null;
		case Tag.IF:
			match(Tag.IF);
			match('(');
			x = bool();
			match(')');
			s1 = stmt();
			if (look.tag != Tag.ELSE)
				return new If(x, s1);
			match(Tag.ELSE);
			s2 = stmt();
			return new Else(x, s1, s2);
		case Tag.WHILE:
			While whilenode = new While();
			savedStmt = Stmt.Enclosing;
			Stmt.Enclosing = whilenode;
			match(Tag.WHILE);
			match('(');
			x = bool();
			match(')');
			s1 = stmt();
			whilenode.init(x, s1);
			Stmt.Enclosing = savedStmt;
			return whilenode;
		case Tag.DO:
			Do donode = new Do();
			savedStmt = Stmt.Enclosing;
			Stmt.Enclosing = donode;
			match(Tag.DO);
			s1 = stmt();
			match(Tag.WHILE);
			match('(');
			x = bool();
			match(')');
			match(';');
			donode.init(x, s1);
			Stmt.Enclosing = savedStmt;
			return donode;
		case Tag.BREAK:
			match(Tag.BREAK);
			match(';');
			return new Break();
		case '{':
			return block();
		default:
			return assign();
		}
	}

	/**
	 * 赋值语句
	 * 
	 * @return
	 * @throws IOException
	 */
	private Stmt assign() throws IOException {
		Stmt stmt;
		LexicalToken t = look;
		match(Tag.ID);
		Identifier id = top.get(t);
		if (id == null)
			error(t.toString() + " undeclared");
		if (look.tag == '=') {
			move();
			stmt = new Set(id, bool());
		} else {
			Access x = offset(id);
			match('=');
			stmt = new SetElem(x, bool());
		}

		match(';');
		return stmt;
	}

	/**
	 * bool -> bool || join | join 
	 * 
	 * @return
	 * @throws IOException
	 */
	private Expr bool() throws IOException {
		Expr x = join();
		while (look.tag == Tag.OR) {
			LexicalToken tok = look;
			move();
			x = new Or(tok, x, join());
		}
		return x;
	}

	/**
	 * join -> join && equality | equality 
	 * 
	 * @return
	 * @throws IOException
	 */
	private Expr join() throws IOException {
		Expr x = equality();
		while (look.tag == Tag.AND) {
			LexicalToken tok = look;
			move();
			x = new And(tok, x, equality());
		}
		return x;
	}

	/**
	 * equality -> equality == rel | equality != rel | rel 
	 * 
	 * @return
	 * @throws IOException
	 */
	private Expr equality() throws IOException {
		Expr x = rel();
		while (look.tag == Tag.EQ || look.tag == Tag.NE) {
			LexicalToken tok = look;
			move();
			x = new Rel(tok, x, rel());
		}
		return x;
	}

	/**
	 *  rel -> expr < expr | expr <= expr |expr >= expr | expr > expr |expr 
	 *  
	 * @return
	 * @throws IOException
	 */
	private Expr rel() throws IOException {
		Expr x = expr();
		switch (look.tag) {
		case '<':
		case Tag.LE:
		case Tag.GE:
		case '>':
			LexicalToken tok = look;
			move();
			x = new Rel(tok, x, rel());
		default:
			return x;
		}
	}

	/**
	 * expr -> expr + term | expr - term | term
	 * 
	 * @return
	 * @throws IOException
	 */
	private Expr expr() throws IOException {
		Expr x = term();
		while (look.tag == '+' || look.tag == '-') {
			LexicalToken tok = look;
			move();
			x = new Arith(tok, x, term());
		}
		return x;
	}

	/**
	 * term -> term * unary | term / unary | unary 
	 * 
	 * @return
	 * @throws IOException
	 */
	private Expr term() throws IOException {
		Expr x = unary();
		while (look.tag == '*' || look.tag == '/') {
			LexicalToken tok = look;
			move();
			x = new Arith(tok, x, unary());
		}
		return x;
	}

	/**
	 * unary -> ! unary | - unary |factor 
	 * 
	 * @return
	 * @throws IOException
	 */
	private Expr unary() throws IOException {
		if (look.tag == '-') {
			move();
			return new Unary(Word.minus, unary());
		} else if (look.tag == '!') {
			LexicalToken tok = look;
			move();
			return new Not(tok, unary());
		} else
			return factor();
	}

	/**
	 * factor -> ( bool ) | loc | <b>num</b> | <b>real</b> | <b>true</b> | <b>false</b>
	 * 
	 * @return
	 * @throws IOException
	 */
	private Expr factor() throws IOException {
		Expr x = null;
		switch (look.tag) {
		case '(':
			move();
			x = bool();
			match(')');
			return x;
		case Tag.NUM:
			x = new Constant(look, Type.Int);
			move();
			return x;
		case Tag.REAL:
			x = new Constant(look, Type.Float);
			move();
			return x;
		case Tag.TRUE:
			x = Constant.True;
			move();
			return x;
		case Tag.FALSE:
			x = Constant.False;
			move();
			return x;
		default:
			error("syntax error");
			return x;
		case Tag.ID:
			String s = look.toString();
			Identifier id = top.get(look);
			if (id == null)
				error(look.toString() + " undeclared");
			move();
			if (look.tag == '[')
				return id;
			else
				return offset(id);
		}
	}

	/**
	 * 为数组计算生成代码
	 * 
	 * @param a
	 * @return
	 * @throws IOException
	 */
	private Access offset(Identifier id) throws IOException {
		Expr i;
		Expr w;
		Expr t1, t2;
		Expr loc;
		Type type = id.type;

		match('[');
		i = bool();
		match(']');
		type = ((Array) type).of;
		w = new Constant(type.width);
		t1 = new Arith(new LexicalToken('*'), i, w);
		loc = t1;

		while (look.tag == '[') {
			match('[');
			i = bool();
			match(']');
			type = ((Array) type).of;
			w = new Constant(type.width);
			t1 = new Arith(new LexicalToken('*'), i, w);
			t2 = new Arith(new LexicalToken('+'), loc, t1);
			loc = t2;
		}
		return new Access(id, loc, type);
	}

}
