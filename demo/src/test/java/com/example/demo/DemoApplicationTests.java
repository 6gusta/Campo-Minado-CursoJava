package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void testar() {
        int a = 1 + 1;
        assertEquals(2, a);
    }

@Test
	void testa2(){
		int x = 2 + 10 - 9;

		assertEquals(3, x);
	}

    @Test

    void testsomar(){
        testedotesmian  r = new testedotesmian();

        int resultado = r.somar(2, 3);

        assertEquals(5 ,resultado , " A  soma de 2 e 3 deve ser 5  ");
    }
}
