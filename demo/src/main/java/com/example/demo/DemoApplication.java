package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.modelo.Tabuleiro;
import com.example.demo.visao.tabuleiroconsole;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		Tabuleiro tabuleiro = new Tabuleiro(6 ,6,6);



		new tabuleiroconsole(tabuleiro);





		

		tabuleiro.abrir(3, 3);
		tabuleiro.marcar(4, 4);
		tabuleiro.marcar(4, 5);


		System.out.println(tabuleiro);


	}

}
