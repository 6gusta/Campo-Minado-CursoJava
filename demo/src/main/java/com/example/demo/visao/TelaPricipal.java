package com.example.demo.visao;

import com.example.demo.modelo.Tabuleiro;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TelaPricipal extends JFrame {

    public TelaPricipal() {
        Tabuleiro tabuleiro = new Tabuleiro(15, 15, 7);
        add(new Paineltabuleiro(tabuleiro));

        setTitle("Campo Minado");
        setSize(690, 438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaPricipal();
    }
}
