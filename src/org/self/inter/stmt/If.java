package org.self.inter.stmt;

import org.self.inter.Expr;
import org.self.inter.Stmt;
import org.self.symbols.Type;

/**
 * 
 * @author TungWang
 */
public class If extends Stmt {

	Expr expr;
	Stmt stmt;

	public If(Expr x, Stmt s) {
		expr = x;
		stmt = s;
		if (expr.type != Type.Bool)
			expr.error("boolean required in if");
	}

	@Override
	public void gen(int a, int b) {
		// stmt的代码的标号
		int label = newlabel();
		// 为真时控制流穿越，为假时转向b
		expr.jumping(0, b);
		emitlabel(label);
		stmt.gen(label, b);
	}
}
