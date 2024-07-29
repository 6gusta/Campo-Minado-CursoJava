package com.example.demo.visao;

import com.example.demo.modelo.Campo;
import com.example.demo.modelo.Tabuleiro;
import com.example.demo.modelo.Campoeveneto;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class Paineltabuleiro extends JPanel {

    public Paineltabuleiro(Tabuleiro tabuleiro) {
        setLayout(new GridLayout(tabuleiro.getLinha(), tabuleiro.getColunas()));

        // Para cada campo, adiciona um botão correspondente
        tabuleiro.paracdada(c -> add(new BotaoCampo(c, tabuleiro)));

        // Corrige a assinatura da lambda expression
        tabuleiro.registrarObservador((Campo campo, Campoeveneto evento) -> {
            // Verifica o tipo do evento e notifica o resultado
            if (evento == Campoeveneto.EXPLODIR) {
                JOptionPane.showMessageDialog(this, "Você perdeu!", "Resultado do Jogo", JOptionPane.INFORMATION_MESSAGE);
            } else if (tabuleiro.objetivoAlcancado()) {
                JOptionPane.showMessageDialog(this, "Você venceu!", "Resultado do Jogo", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
