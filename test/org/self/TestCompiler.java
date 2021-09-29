package org.self;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.self.lexer.Lexer;
import org.self.parser.Parser;

class TestCompiler {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		try {
			// 初始化词法分析器
			Lexer lexer = new Lexer();
			// 初始化语法分析器
			Parser parser = new Parser(lexer);
			// 开始解析
			parser.program();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
