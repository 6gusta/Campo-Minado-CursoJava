package com.example.demo;

import com.example.demo.modelo.Tabuleiro;

public class testedotesmian {

    public int somar(int a, int b){
        return a + b;
    }

    public static void main(String[] args) {


        Tabuleiro tabuleiro = new Tabuleiro(3,3,9);
        tabuleiro.abrir(2, 2);
        
    }
    
}
