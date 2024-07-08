/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.compiladorc;

import java.util.List;

public class AnalisadorSintatico {

    private final List<Token> tokens;
    private int indiceAtual;
    private Token tokenAtual;

    public AnalisadorSintatico(List<Token> tokens) {
        this.tokens = tokens;
        this.indiceAtual = 0;
        this.tokenAtual = tokens.get(indiceAtual);
    }

    public void analyze() throws SintaticoException {
        programa();
        if (indiceAtual < tokens.size()) {
            throw new SintaticoException("Erro sintático: tokens extras no final do arquivo.");
        }
    }

    private void consumirToken() {
        if (indiceAtual < tokens.size()) {
            indiceAtual++;
        }
    }

    private Token getCurrentToken() {
        if (indiceAtual < tokens.size()) {
            return tokens.get(indiceAtual);
        } else {
            return new Token(TokenType.FINAL_ARQUIVO, ""); // Retorna um token de final de arquivo se estiver fora do alcance
        }
    }

    private void match(TokenType tipoEsperado) throws SintaticoException {
        Token tokenAtual = getCurrentToken();
        if (tokenAtual.getType() == tipoEsperado) {
            consumirToken();
        } else {
            throw new SintaticoException("Erro sintático: token inesperado " + tokenAtual + " na linha " + tokenAtual.getLine());
        }
    }

    private void avancar() {
        indiceAtual++;
        if (indiceAtual < tokens.size()) {
            tokenAtual = tokens.get(indiceAtual);
        } else {
            tokenAtual = new Token(TokenType.FINAL_ARQUIVO, "", tokenAtual.getLine());
        }
    }

    private void programa() throws SintaticoException {
        // Pular tokens do tipo BIBLIOTECA antes de iniciar a análise principal
        while (tokenAtual.getType() == TokenType.BIBLIOTECA) {
            avancar();
        }

        // Verifica a estrutura de um programa simples em C
        if (getCurrentToken().getType() == TokenType.CARDINAL) {
            match(TokenType.CARDINAL);
            match(TokenType.IDENTIFICADOR); // include
            match(TokenType.MENOR);
            match(TokenType.IDENTIFICADOR); // stdio
            match(TokenType.PONTO);
            match(TokenType.IDENTIFICADOR); // h
            match(TokenType.MAIOR);
        }

//        // Pular tokens do tipo BIBLIOTECA antes de iniciar a análise principal
//        while (tokenAtual.getType() == TokenType.BIBLIOTECA) {
//            avancar();
//        }
        // Parte principal do programa
        match(TokenType.INT);

        match(TokenType.MAIN);

        match(TokenType.ABRE_PARENTES);

        match(TokenType.FECHA_PARENTES);

        match(TokenType.ABRE_CHAVETA);

        comandos();

        match(TokenType.FECHA_CHAVETA);
    }

    private void comandos() throws SintaticoException {
        while (getCurrentToken().getType() != TokenType.FECHA_CHAVETA) {
            comando();
        }
    }

    private void comando() throws SintaticoException {
        Token tokenAtual = getCurrentToken();
        if (tokenAtual.getType() == TokenType.PRINTF) {
            match(TokenType.PRINTF);
            match(TokenType.ABRE_PARENTES);
            match(TokenType.IDENTIFICADOR); // Supondo que seja uma string
            match(TokenType.FECHA_PARENTES);
            match(TokenType.FINAL_DE_INSTRUCAO);
        } else if (tokenAtual.getType() == TokenType.RETURN) {
            match(TokenType.RETURN);
            match(TokenType.NUMERO);
            match(TokenType.FINAL_DE_INSTRUCAO);
        } else {
            throw new SintaticoException("Erro sintático: comando inesperado na linha " + tokenAtual.getLine());

        }
    }

    private void chamadaDeFuncao() throws SintaticoException {
        // Verifica se é uma chamada para printf ou scanf
        if (tokenAtual.getType() == TokenType.FUNCTION) {
            String functionName = tokenAtual.getLexeme();
            if (!functionName.equals("printf") && !functionName.equals("scanf")) {
                throw new SintaticoException("Esperava-se uma chamada para printf ou scanf, mas encontrou: " + functionName);
            }
        } else {
            throw new SintaticoException("Esperava-se uma chamada de função, mas encontrou: " + tokenAtual);
        }

        match(TokenType.FUNCTION); // Match função (printf ou scanf)
        match(TokenType.ABRE_PARENTES);
        match(TokenType.STRING); // Verifica o formato da string de formato
        if (tokenAtual.getType() == TokenType.E_COMERCIAL) {
            match(TokenType.E_COMERCIAL);
            match(TokenType.IDENTIFICADOR);
        } else {
            match(TokenType.IDENTIFICADOR);
        }
        match(TokenType.FECHA_PARENTES);
        match(TokenType.FINAL_DE_INSTRUCAO);
    }

    public class SintaticoException extends Exception {

        public SintaticoException(String message) {
            super(message);
        }
    }

}
