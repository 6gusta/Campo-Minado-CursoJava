package com.example.demo.modelo;

@FunctionalInterface
public interface Campoobs {

    // Método original que é utilizado na classe Campo
    void eventoOcorreu(Campo campo, Campoeveneto evento);

    // Remover ou ajustar o segundo método, se necessário
    // void eventoOcorreu(boolean resultado);
}
