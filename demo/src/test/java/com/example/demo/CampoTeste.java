package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.execao.ExplosaoException;
import com.example.demo.modelo.Campo;

public class CampoTeste {

    private Campo campo;

    @BeforeEach
    void iniciarCampo() {
        campo = new Campo(3, 3);
    }

    @Test
    void testevizinhoEsquerda() {
        Campo vizinhoEsquerda = new Campo(3, 2);
        boolean resultadoEsquerda = campo.adicionarVizinho(vizinhoEsquerda);
        assertTrue(resultadoEsquerda, "Vizinho à esquerda não adicionado corretamente.");
    }

    @Test
    void testevizinhoEmCima() {
        Campo vizinho = new Campo(2, 3);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado, "Vizinho em cima não adicionado corretamente.");
    }

    @Test
    void testevizinhoEmBaixo() {
        Campo vizinho = new Campo(4, 3);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado, "Vizinho embaixo não adicionado corretamente.");
    }

    @Test
    void testevizinhoDiagonal() {
        Campo vizinho = new Campo(2, 2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado, "Vizinho diagonal não adicionado corretamente.");
    }

    @Test
    void testeNaoVizinho() {
        Campo vizinho = new Campo(1, 1);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertFalse(resultado, "Campo que não é vizinho foi adicionado como vizinho.");
    }

    @Test
    void testeValorPadrao() {
        assertFalse(campo.isMarcado(), "Campo não deveria estar marcado por padrão.");
    }

    @Test
    void testeAlternarMarcacao() {
        campo.alternarMarcacao();
        assertTrue(campo.isMarcado(), "Campo deveria estar marcado após alternar marcação.");
    }

    @Test
    void testeAbrirNaoMinadoMarcado() {
        // Marca o campo e depois desmarca
        campo.alternarMarcacao();
        campo.alternarMarcacao();
        // Campo não está minado e não está marcado, deve ser aberto
        assertTrue(campo.abrir(), "Campo deveria ser aberto se não está minado e não está marcado.");
    }

    @Test
    void testeAbrirMinadoMarcado() {
        // Marca o campo e mina o campo
        campo.alternarMarcacao();
        campo.minar();
        // Campo está minado e marcado, não deve ser aberto
        assertFalse(campo.abrir(), "Campo minado e marcado não deveria ser aberto.");
    }

    @Test
    void testeAbrirMinadoNaoMarcado() {
        // Mina o campo
        campo.minar();
        // Campo está minado e não marcado, deve lançar uma exceção
        assertThrows(ExplosaoException.class, () -> campo.abrir(), "Deveria lançar ExplosaoException ao abrir um campo minado.");
    }

    @Test
    void testeAbrirComVizinho() {
        Campo campo11 = new Campo(2, 2);
        Campo campo22 = new Campo(2, 3);
        campo22.adicionarVizinho(campo11);
        campo.adicionarVizinho(campo22);

        // Abre o campo
        campo.abrir();

        // Verifica se o campo vizinho direto foi aberto
        assertTrue(campo22.isAberto(), "Campo vizinho deve estar aberto.");

        // Verifica se o campo diagonal permanece fechado
        assertTrue(campo11.isFechado(), "Campo diagonal deve estar fechado.");
    }

    
}
