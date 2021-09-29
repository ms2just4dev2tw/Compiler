/**
 * <h3>表达式的中间代码</h3>
 * {@link org.self.inter.Node 语法树的节点}
 * <ul>
 * 		<li>{@link org.self.inter.Expr 表达式}</li>
 * 		<ul>
 * 			<li>{@link org.self.inter.expr.Constant 常量}</li>
 * 			<li>{@link org.self.inter.expr.Id 标识符}</li>
 * 			<li>{@link org.self.inter.expr.Logical 逻辑运算符}</li>
 * 			<ul>
 * 				<li>{@link org.self.inter.expr.logical.And 与运算}</li>
 * 				<li>{@link org.self.inter.expr.logical.Not 非运算}</li>
 * 				<li>{@link org.self.inter.expr.logical.Or 或运算}</li>
 * 				<li>{@link org.self.inter.expr.logical.Rel }</li>
 * 			</ul>
 * 			<li>{@link org.self.inter.expr.Op 运算符}</li>
 * 			<ul>
 * 				<li>{@link org.self.inter.expr.op.Access 数组访问}</li>
 * 				<li>{@link org.self.inter.expr.op.Arith}</li>
 * 				<li>{@link org.self.inter.expr.op.Unary 单目运算符}</li>
 * 			</ul>
 * 			<li>{@link org.self.inter.expr.Temp }</li>
 * 		</ul>
 * 		<li> {@link org.self.inter.Stmt 语句 }</li>
 * 		<ul>
 * 			<li>{@link org.self.inter.stmt.Break break语句}</li>
 * 			<li>{@link org.self.inter.stmt.Do }</li>
 * 			<li>{@link org.self.inter.stmt.Else }</li>
 * 			<li>{@link org.self.inter.stmt.If }</li>
 * 			<li>{@link org.self.inter.stmt.Seq }</li>
 * 			<li>{@link org.self.inter.stmt.Set }</li>
 * 			<li>{@link org.self.inter.stmt.SetElem }</li>
 * 			<li>{@link org.self.inter.stmt.While }</li>
 * 		<ul>
 *	<ul>
 * 		
 * @author TungWang
 * 
 */
package org.self.inter;
