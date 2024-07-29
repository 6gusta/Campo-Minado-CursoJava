package com.example.demo.visao;

import com.example.demo.modelo.Campo;
import com.example.demo.modelo.Campoeveneto;
import com.example.demo.modelo.Campoobs;
import com.example.demo.modelo.Tabuleiro; // Adiciona a importação para reiniciar o tabuleiro

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements Campoobs, MouseListener {

    private final Campo campo;
    private static final Color BG_PADRAO = new Color(184, 184, 184);
    private static final Color BG_EXPLODIR = new Color(189, 66, 68);
    private static final Color BG_ABERTO = new Color(213, 213, 213);
    private static final Color TEXTO_VERDE = new Color(0, 100, 0);

    // Adiciona uma referência ao Tabuleiro para reiniciá-lo
    private final Tabuleiro tabuleiro; 

    public BotaoCampo(Campo campo, Tabuleiro tabuleiro) {
        this.campo = campo;
        this.tabuleiro = tabuleiro; // Inicializa o tabuleiro

        setBackground(BG_PADRAO);
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setFont(new Font("Courier", Font.BOLD, 15));

        campo.registrarObservador(this);
        addMouseListener(this);
    }

    @Override
    public void eventoOcorreu(Campo campo, Campoeveneto evento) {
        switch (evento) {
            case ABRIR:
                aplicarEstiloAbrir();
                break;
            case MARCAR:
                aplicarEstiloMarcar();
                break;
            case DESMARCAR:
                aplicarEstiloDesmarcar();
                break;
            case EXPLODIR:
                aplicarEstiloExplodir();
                reiniciarJogo(); // Chama o método para reiniciar o jogo ao explodir
                break;
            case REINICIAR:
                aplicarEstiloReiniciar();
                break;
            default:
                break;
        }

        // Verifica se o objetivo foi alcançado para reiniciar o jogo em caso de vitória
        if (tabuleiro.objetivoAlcancado()) {
            JOptionPane.showMessageDialog(this, "Você venceu! O jogo será reiniciado.");
            reiniciarJogo();
        }
    }

    private void aplicarEstiloAbrir() {
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setBackground(BG_ABERTO);

        if (campo.isMinado()) {
            setBackground(BG_EXPLODIR);
            setText("X");
            return;
        }

        setForeground(definirCorTexto(campo.minasNaVizinhanca()));
        setText(campo.minasNaVizinhanca() > 0 ? String.valueOf(campo.minasNaVizinhanca()) : "");
    }

    private void aplicarEstiloExplodir() {
        setBackground(BG_EXPLODIR);
        setForeground(Color.WHITE);
        setText("X");
        setIcon(new ImageIcon("img/bomba.png"));
    }

    private void aplicarEstiloMarcar() {
        setBackground(BG_PADRAO);
        setForeground(Color.BLACK);
        setText("M");
        setIcon(new ImageIcon("img/bandeira.png"));
    }

    private void aplicarEstiloDesmarcar() {
        setBackground(BG_PADRAO);
        setForeground(Color.BLACK);
        setText("");
        setIcon(null);
    }

    private void aplicarEstiloReiniciar() {
        setBackground(BG_PADRAO);
        setForeground(Color.BLACK);
        setText("");
        setIcon(null);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    }

    private Color definirCorTexto(long minas) {
        switch ((int) minas) {
            case 1: return Color.BLUE;
            case 2: return TEXTO_VERDE;
            case 3: return Color.RED;
            default: return Color.BLACK;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) { // Botão esquerdo do mouse
            campo.abrir(); // Abre o campo
        } else if (e.getButton() == MouseEvent.BUTTON3) { // Botão direito do mouse
            campo.alternarMarcacao(); // Alterna a marcação
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    private void reiniciarJogo() {
        // Reinicia o tabuleiro
        tabuleiro.reiniciar();

        // Reinicia os estilos de todos os botões
        tabuleiro.paracdada(campo -> {
            campo.reiniciar();
            aplicarEstiloReiniciar();
        });
    }
}
