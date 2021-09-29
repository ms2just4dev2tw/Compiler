package org.self;

/**
 * 表达式求值 ExpressionEvaluation
 * <p>
 * 将中缀表达式转换成后缀表达式
 * <p>
 * 再对后缀表达式求值
 * 
 * @author TungWang
 */
public class SuffixExprEval {

	// 运算符的优先级
	private enum PriorityOp {
		// 左小括号，右小括号
		LeftParenthesis('(', 1, 6), RightParenthesis(')', 6, 1),
		// 加减乘除
		Add('+', 3, 2), Subtract('-', 3, 2), Multiply('*', 5, 4), Divide('/', 5, 4),
		// 赋值运算符
		Assign('=', 0, 0);

		private char symbol;
		// 运算符的左优先级和右优先级
		private int leftPriority, rightPriority;

		private PriorityOp(char symbol, int leftPriority, int rightPriority) {
			this.symbol = symbol;
			this.leftPriority = leftPriority;
			this.rightPriority = rightPriority;
		}

		/**
		 * 比较运算符的优先级
		 * 
		 * @param leftOp 左边的运算符，位于栈顶
		 * @param rightOp 右边的运算符，还未加入op栈
		 * @return 左运算符的左优先级 - 右运算符的右优先级
		 */
		public static int compare(char leftOp, char rightOp) {
			return opType(leftOp).leftPriority - opType(rightOp).rightPriority;
		}

		private static PriorityOp opType(char symbol) {
			for (PriorityOp op : values())
				if (op.symbol == symbol)
					return op;
			throw new UnsupportedOperationException("UnsupportedOperation " + symbol);
		}
	}

	// 存储运算符的栈结构
	private class Stack<E> {
		private int top;
		private int capacity;
		private Object array[];

		private Stack(int initCapacity) {
			this.capacity = initCapacity;
			this.array = new Object[initCapacity];
		}

		private E pop() {
			if (top == 0)
				throw new IndexOutOfBoundsException((top - 1) + " is less than 0" + capacity);
			return (E) array[--top];
		}

		private E peek() {
			if (top == 0)
				throw new IndexOutOfBoundsException((top - 1) + " is less than 0" + capacity);
			return (E) array[top - 1];
		}

		private void push(Object e) {
			if (top == capacity)
				throw new IndexOutOfBoundsException(top + " is greater than " + (capacity - 1));
			array[top++] = e;
		}
	}

	private Stack stack;

	public SuffixExprEval(int initCapacity) {
		stack = new Stack(initCapacity);
	}

	/**
	 * @param infixExpr 不含有变量的简单中缀表达式
	 */
	public void work(String infixExpr) {
		char[] infix = infixExpr.toCharArray();
		String suffix = infix2suffix(infix);
		System.out.println(suffix);

		int value = compute(suffix.toCharArray());
		System.out.println(value);
	}

	/**
	 * 将中缀表达式转换成后缀表达式
	 * 
	 * @param infix 中缀表达式
	 * @return 解析后的后缀表达式
	 */
	private String infix2suffix(char[] infix) {
		Stack<Character> opStack = stack;
		// 压入赋值运算符
		opStack.push('=');
		// 存储后缀表达式的缓冲区
		StringBuilder suffixExpr = new StringBuilder();
		for (char ch : infix) {
			// 如果是数字直接放入缓冲区
			if (Character.isDigit(ch))
				suffixExpr.append(ch);
			else {
				int left2right = PriorityOp.compare(opStack.peek(), ch);
				for (;; left2right = PriorityOp.compare(opStack.peek(), ch)) {
					if (left2right < 0) {
						// 进栈
						opStack.push(ch);
						break;
					} else if (left2right == 0) {
						// 优先级相同，出栈
						opStack.pop();
						break;
					} else
						// 出栈
						suffixExpr.append(opStack.pop());
				}
			}
		}
		// 将op栈的其他运算符除了赋值运算符都加入缓冲区
		for (char ch = opStack.pop(); ch != '='; ch = opStack.pop())
			suffixExpr.append(ch);
		return suffixExpr.toString();
	}

	/**
	 * 计算后缀表达式
	 * 
	 * @param suffix 后缀表达式
	 * @return 后缀表达式的计算结果
	 */
	private int compute(char[] suffix) {
		// 运算数栈
		Stack<Integer> numStack = stack;

		for (char ch : suffix) {
			if (Character.isDigit(ch))
				numStack.push(ch - 48);
			else
				switch (ch) {
				case '+':
					// 弹出两个数，计算后压入栈
					numStack.push(numStack.pop() + numStack.pop());
					break;
				case '-':
					// 弹出两个数，计算后压入栈
					numStack.push(numStack.pop() - numStack.pop());
					break;
				case '*':
					// 弹出两个数，计算后压入栈
					numStack.push(numStack.pop() * numStack.pop());
					break;
				case '/':
					// 弹出两个数，计算后压入栈
					int left = numStack.pop();
					int right = numStack.pop();
					numStack.push(right / left);
					break;
				default:
					throw new UnsupportedOperationException("Unsupported Symbol: " + ch);
				}
		}
		return numStack.pop();
	}

}
