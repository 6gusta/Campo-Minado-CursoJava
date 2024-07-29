package com.example.demo.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private int minas;

    private final List<Campo> campos = new ArrayList<>();
    private final List<Campoobs> observadores = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    // Método para registrar observadores corretamente
    public void registrarObservador(Campoobs observador) {
        observadores.add(observador);
    }

    // Notifica todos os observadores com o evento
    public void notificarObservadores(boolean resultado) {
        for (Campoobs observador : observadores) {
            if (resultado) {
                observador.eventoOcorreu(null, Campoeveneto.ABRIR); // Indicativo de vitória
            } else {
                observador.eventoOcorreu(null, Campoeveneto.EXPLODIR); // Indicativo de derrota
            }
        }
    }

    public void abrir(int linha, int coluna) {
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(Campo::abrir);
    }

    public void alternarMarcacao(int linha, int coluna) {
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(Campo::alternarMarcacao);
    }

    private void gerarCampos() {
        for (int linha = 0; linha < linhas; linha++) {
            for (int coluna = 0; coluna < colunas; coluna++) {
                var campo = new Campo(linha, coluna);
                campo.registrarObservador(this::eventoOcorreu);
                campos.add(campo);
            }
        }
    }

    private void associarVizinhos() {
        for (Campo c1 : campos) {
            for (Campo c2 : campos) {
                c1.adicionarVizinho(c2);
            }
        }
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

    public boolean objetivoAlcancado() {
        return campos.stream().allMatch(c -> c.isAberto() || c.isMarcado());
    }

    public void reiniciar() {
        campos.forEach(Campo::resetar);
        sortearMinas();
    }

    public void paracdada(java.util.function.Consumer<Campo> funcao) {
        campos.forEach(funcao);
    }

    public int getLinha() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    private void eventoOcorreu(Campo campo, Campoeveneto evento) {
        if (evento == Campoeveneto.EXPLODIR) {
            mostrarBombas();
            notificarObservadores(false); // Notifica derrota
        } else if (objetivoAlcancado()) {
            notificarObservadores(true); // Notifica vitória
        }
    }

    private void mostrarBombas() {
        campos.stream()
            .filter(Campo::isMinado)
            .filter(c -> !c.isMarcado())
            .forEach(c -> c.setAberto(true));
    }

    public void revelarMinas() {
        campos.stream()
            .filter(Campo::isMinado)
            .filter(c -> !c.isMarcado())
            .forEach(c -> c.setAberto(true));
    }

    public void marcar(int i, int j) {
    }
}
