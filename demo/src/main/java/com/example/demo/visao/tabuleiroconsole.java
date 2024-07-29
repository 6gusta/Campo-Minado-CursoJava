package com.example.demo.visao;

import java.util.Scanner;
import com.example.demo.execao.ExplosaoException;
import com.example.demo.execao.SairException;
import com.example.demo.modelo.Tabuleiro;

public class tabuleiroconsole {

    private Tabuleiro tabuleiro;
    private Scanner entrada = new Scanner(System.in);

    public tabuleiroconsole(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        executarJogo();
    }

    private void executarJogo() {
        try {
            boolean continuar = true;
            while (continuar) {
                cicloDoJogo();
                System.out.println("Outra partida? (S/n) ");
                String resposta = entrada.nextLine();
                if ("n".equalsIgnoreCase(resposta)) {
                    continuar = false;
                } else {
                    tabuleiro.reiniciar();
                }
            }
        } catch (SairException e) {
            System.out.println("Tchau !!!");
        } finally {
            entrada.close();
        }
    }

    private void cicloDoJogo() {
        try {
            while (!tabuleiro.objetivoAlcancado()) {
                System.out.println(tabuleiro);
                String digitado = capturarValorDigitado("Digite (x, y) para abrir ou (x, y, m) para marcar: ");

                String[] valores = digitado.split(",");
                if (valores.length < 2 || valores.length > 3) {
                    System.out.println("Entrada inválida! Use o formato (x, y) para abrir ou (x, y, m) para marcar.");
                    continue;
                }

                try {
                    int x = Integer.parseInt(valores[0].trim());
                    int y = Integer.parseInt(valores[1].trim());

                    if (valores.length == 3 && "m".equalsIgnoreCase(valores[2].trim())) {
                        tabuleiro.marcar(x, y);
                    } else {
                        tabuleiro.abrir(x, y);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida! As coordenadas devem ser números inteiros.");
                }
            }
            System.out.println(tabuleiro);
            System.out.println("Você ganhou!!!");
        } catch (ExplosaoException e) {
            System.out.println(tabuleiro);
            System.out.println("Você perdeu!");
            tabuleiro.revelarMinas();
        }
    }

    private String capturarValorDigitado(String texto) {
        System.out.print(texto);
        String digitado = entrada.nextLine();
        if ("sair".equalsIgnoreCase(digitado)) {
            throw new SairException();
        }
        return digitado;
    }
}
