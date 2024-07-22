package com.example.demo.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {

    private int linha;
    private int colunas;
    private int minas;

    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linha, int colunas, int minas) {
        this.linha = linha;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarOsVizinhos();
        sortearMinas();
    }

    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = Campo::isMinado;

        do {
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        } while (minasArmadas < minas);
    }

    private void associarOsVizinhos() {
        for (Campo c1 : campos) {
            for (Campo c2 : campos) {
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void gerarCampos() {
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < colunas; j++) {
                campos.add(new Campo(i, j));
            }
        }
    }

    public boolean objetivoAlcancado() {
        return campos.stream().allMatch(Campo::objetivoAlcancado);
    }

    public void reiniciar() {
        campos.forEach(Campo::reiniciar);
        sortearMinas();
    }

    public void abrir(int linha, int coluna) {
        campos.stream()
              .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
              .findFirst()
              .ifPresent(Campo::abrir);
    }

    public void marcar(int linha, int coluna) {
        campos.stream()
              .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
              .findFirst()
              .ifPresent(Campo::alternarMarcacao);
    }

    public void revelarMinas() {
        campos.stream()
              .filter(Campo::isMinado)
              .forEach(c -> c.setAberto(true));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        // Adiciona os índices das colunas
        sb.append("   ");
        for (int c = 0; c < colunas; c++) {
            sb.append(" ");
            sb.append(c);
            sb.append(" ");
        }
        sb.append("\n");

        int i = 0;
        for (int l = 0; l < linha; l++) {
            // Adiciona o índice da linha
            sb.append(l);
            sb.append(" ");
            for (int c = 0; c < colunas; c++) {
                sb.append(" ");
                sb.append(campos.get(i).toString());
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
