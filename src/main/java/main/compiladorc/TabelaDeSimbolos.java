/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.compiladorc;

import java.util.HashMap;
import java.util.Map;
import main.compiladorc.AnalisadorSemantico.SemanticoException;

public class TabelaDeSimbolos {

    private final Map<String, Simbolo> simbolos = new HashMap<>();

    public void adicionarSimbolo(Simbolo simbolo) throws SemanticoException {
        if (simbolos.containsKey(simbolo.getNome())) {
            throw new SemanticoException("Variável '" + simbolo.getNome() + "' já declarada no mesmo escopo.");
        }
        simbolos.put(simbolo.getNome(), simbolo);
    }

    public Simbolo buscarSimbolo(String nome) throws SemanticoException {
        Simbolo simbolo = simbolos.get(nome);
        if (simbolo == null) {
            throw new SemanticoException("Variável '" + nome + "' não declarada.");
        }
        return simbolo;
    }
        public class SemanticoException extends Exception {

        public SemanticoException(String mensagem) {
            super(mensagem);
        }
    }
}

class Simbolo {
    private final String nome;
    private final String tipo;

    public Simbolo(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }
}
