package com.example.demo.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {

    private final int linha;
    private final int coluna;
    private boolean minado;
    private boolean aberto;
    private boolean marcado;

    private List<Campo> vizinhos = new ArrayList<>();
    private List<Campoobs> observadores = new ArrayList<>();

    Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public void registrarObservador(Campoobs observador) {
        observadores.add(observador);
    }

    private void notificarObservadores(Campoeveneto evento) {
        observadores.forEach(o -> o.eventoOcorreu(this, evento));
    }

    boolean adicionarVizinho(Campo vizinho) {
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaLinha + deltaColuna;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }

    public void alternarMarcacao() {
        if (!aberto) {
            marcado = !marcado;
            if (marcado) {
                notificarObservadores(Campoeveneto.MARCAR);
            } else {
                notificarObservadores(Campoeveneto.DESMARCAR);
            }
        }
    }

    public boolean abrir() {
        if (!aberto && !marcado) {
            if (minado) {
                notificarObservadores(Campoeveneto.EXPLODIR);
                return true;
            }

            setAberto(true);

            if (vizinhancaSegura()) {
                vizinhos.forEach(Campo::abrir);
            }

            return true;
        } else {
            return false;
        }
    }

    boolean vizinhancaSegura() {
        return vizinhos.stream().noneMatch(Campo::isMinado);
    }

    void minar() {
        minado = true;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public boolean isMinado() {
        return minado;
    }

    public boolean isAberto() {
        return aberto;
    }

    void setAberto(boolean aberto) {
        this.aberto = aberto;
        if (aberto) {
            notificarObservadores(Campoeveneto.ABRIR);
        }
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public long minasNaVizinhanca() {
        return vizinhos.stream().filter(Campo::isMinado).count();
    }

    public void resetar() {
        aberto = false;
        minado = false;
        marcado = false;
        notificarObservadores(Campoeveneto.DESMARCAR);
    }

    public void reiniciar() {
    }


}
