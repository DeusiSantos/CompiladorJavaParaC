/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.compiladorc;

import java.util.List;

public class AnalisadorSemantico {

    private final List<Token> tokens;
    private int indiceAtual;
    private final TabelaDeSimbolos tabelaDeSimbolos;

    public AnalisadorSemantico(List<Token> tokens) {
        this.tokens = tokens;
        this.indiceAtual = 0;
        this.tabelaDeSimbolos = new TabelaDeSimbolos();
    }

    public void analyze() throws SemanticoException, TabelaDeSimbolos.SemanticoException {
        while (indiceAtual < tokens.size()) {
            Token tokenAtual = getCurrentToken();
            switch (tokenAtual.getType()) {
                case INT:
                case FLOAT:
                case CHAR:
                case DOUBLE:
                    declararVariavel(tokenAtual.getLexeme());
                    break;
                case IDENTIFICADOR:
                    usarVariavel(tokenAtual);
                    break;
                case IF:
                case WHILE:
                case FOR:
                case DO:
                    validarExpressaoBooleana(tokenAtual);
                    break;
                case FUNCTION:
                    verificarDeclaracaoFuncao();
                    break;
                case PRINTF:
                    verificarPrintf();
                    break;
                case SCANF:
                    verificarScanf();
                    break;
                case MAIN:
                    verificarMain();
                    break;
                default:
                    consumirToken();
                    break;
            }
        }
    }

    private Token getCurrentToken() {
        if (indiceAtual < tokens.size()) {
            return tokens.get(indiceAtual);
        } else {
            return new Token(TokenType.FINAL_ARQUIVO, ""); // Retorna um token de final de arquivo se estiver fora do alcance
        }
    }

    private void consumirToken() {
        if (indiceAtual < tokens.size()) {
            indiceAtual++;
        }
    }

    private void declararVariavel(String tipo) throws SemanticoException, TabelaDeSimbolos.SemanticoException {
        consumirToken(); // Consome o tipo da variável
        String nome = getCurrentToken().getLexeme();
        consumirToken(); // Consome o identificador da variável

        Simbolo simbolo = new Simbolo(nome, tipo);
        tabelaDeSimbolos.adicionarSimbolo(simbolo);

        // Consome até o ponto e vírgula
        while (getCurrentToken().getType() != TokenType.FINAL_DE_INSTRUCAO) {
            consumirToken();
        }
        consumirToken(); // Consome o ponto e vírgula
    }

    private void usarVariavel(Token token) throws SemanticoException, TabelaDeSimbolos.SemanticoException {
        String nome = token.getLexeme();
        consumirToken(); // Consome o identificador

        Simbolo simbolo = tabelaDeSimbolos.buscarSimbolo(nome);
        // Verificar compatibilidade de tipos aqui, se necessário
    }

    private void validarExpressaoBooleana(Token token) throws SemanticoException {
        // Considere aqui a implementação detalhada para validar expressões booleanas
        // Exemplo simplificado: verificar se o token atual é um operador relacional válido
        if (!isOperadorRelacionalValido(token)) {
            throw new SemanticoException("Erro: operador relacional inválido: " + token.getLexeme());
        }
        // Adicione mais lógica conforme necessário para validar expressões booleanas completas
    }

    private boolean isOperadorRelacionalValido(Token token) {
        // Exemplo simples: verificar se o token é um operador relacional válido (==, !=, <, >, <=, >=)
        TokenType type = token.getType();
        return type == TokenType.IGUAL || type == TokenType.DIFERENTE || type == TokenType.MENOR
                || type == TokenType.MAIOR || type == TokenType.MENOR_IGUAL || type == TokenType.MAIOR_IGUAL;
    }

    private void verificarDeclaracaoFuncao() throws SemanticoException {
        Token tokenAtual = getCurrentToken();
        // Exemplo simples: verificar se o token atual é um tipo de dado válido (int, float, etc.)
        if (!isTipoDado(tokenAtual)) {
            throw new SemanticoException("Erro: tipo de dado inválido na declaração de função: " + tokenAtual.getLexeme());
        }

        consumirToken(); // Consome o tipo de dado da função
        tokenAtual = getCurrentToken();

        // Verifica se o próximo token é um identificador (nome da função)
        if (tokenAtual.getType() != TokenType.IDENTIFICADOR) {
            throw new SemanticoException("Erro: esperado identificador na declaração de função.");
        }

        // Implemente mais lógica conforme necessário para verificar parâmetros e corpo da função
    }

    private boolean isTipoDado(Token token) {
        // Exemplo simples: verificar se o token é um tipo de dado válido (int, float, etc.)
        TokenType type = token.getType();
        return type == TokenType.INT || type == TokenType.FLOAT || type == TokenType.CHAR
                || type == TokenType.DOUBLE || type == TokenType.VOID; // Adicione mais tipos conforme necessário
    }

    private void verificarPrintf() throws SemanticoException {
        consumirToken(); // Consome o token printf

        // Verifica se o próximo token é um parêntese de abertura '('
        if (getCurrentToken().getType() != TokenType.ABRE_PARENTES) {
            throw new SemanticoException("Erro: esperado '(' após printf.");
        }
        consumirToken(); // Consome o '('

        // Verifica os argumentos dentro do printf
        verificarArgumentosPrintf();

        // Verifica se o próximo token é um parêntese de fechamento ')'
        if (getCurrentToken().getType() != TokenType.FECHA_PARENTES) {
            throw new SemanticoException("Erro: esperado ')' após os argumentos do printf.");
        }
        consumirToken(); // Consome o ')'

        // Consome o ponto e vírgula ou o fechamento do bloco
        if (getCurrentToken().getType() != TokenType.FINAL_DE_INSTRUCAO) {
            throw new SemanticoException("Erro: esperado ponto e vírgula após printf.");
        }
        consumirToken(); // Consome o ponto e vírgula
    }

    private void verificarArgumentosPrintf() throws SemanticoException {
        while (getCurrentToken().getType() != TokenType.FECHA_PARENTES) {
            if (getCurrentToken().getType() == TokenType.FORMAT_SPECIFIER) {
                validarFormatoPrintf(getCurrentToken());
            }
            consumirToken();
        }
    }

    private void validarFormatoPrintf(Token tokenFormato) throws SemanticoException {
        String lexeme = tokenFormato.getLexeme();

        // Exemplo simples: verificar se o formato corresponde a %d, %f, %s, etc.
        if (!lexeme.matches("^%[dfs]$")) {
            throw new SemanticoException("Erro: formato inválido no printf: " + lexeme);
        }
    }

    private void verificarScanf() throws SemanticoException {
        Token tokenAtual = getCurrentToken();

        // Verifica se o token atual é uma chamada scanf
        if (tokenAtual.getType() != TokenType.SCANF) {
            throw new SemanticoException("Erro: esperado scanf.");
        }

        // Consome o token scanf
        consumirToken();

        // Verifica se o próximo token é um parêntese de abertura
        if (getCurrentToken().getType() != TokenType.ABRE_PARENTES) {
            throw new SemanticoException("Erro: esperado '(' após scanf.");
        }

        // Consome o parêntese de abertura
        consumirToken();

        // Verifica os argumentos dentro do scanf
        verificarArgumentosScanf();

        // Verifica se o próximo token é um parêntese de fechamento
        if (getCurrentToken().getType() != TokenType.FECHA_PARENTES) {
            throw new SemanticoException("Erro: esperado ')' após argumentos de scanf.");
        }

        // Consome o parêntese de fechamento
        consumirToken();

        // Verifica se há um ponto e vírgula no final da linha scanf
        if (getCurrentToken().getType() != TokenType.FINAL_DE_INSTRUCAO) {
            throw new SemanticoException("Erro: esperado ';' no final da linha de scanf.");
        }

        // Consome o ponto e vírgula
        consumirToken();
    }

    private void verificarArgumentosScanf() throws SemanticoException {
        // Implementa a lógica para verificar os argumentos de scanf
        while (true) {
            Token tokenAtual = getCurrentToken();

            // Verifica se o argumento é uma string de formato (%d, %f, etc.)
            if (tokenAtual.getType() == TokenType.STRING_FORMATO) {
                // Consome o token de string de formato
                consumirToken();

                // Verifica se o próximo token é uma variável onde o valor será armazenado
                if (getCurrentToken().getType() != TokenType.IDENTIFICADOR) {
                    throw new SemanticoException("Erro: esperado identificador após string de formato.");
                }

                // Consome o identificador da variável
                consumirToken();
            } else {
                // Se não for uma string de formato, saímos do loop
                break;
            }

            // Verifica se há uma vírgula separando os argumentos
            if (getCurrentToken().getType() == TokenType.VIRGULA) {
                // Consome a vírgula e continua para o próximo argumento
                consumirToken();
            } else {
                // Se não houver vírgula, saímos do loop
                break;
            }
        }
    }

    private void verificarMain() throws SemanticoException {
        // Verifica se a função main foi declarada corretamente
        boolean mainEncontrada = false;
        for (Token token : tokens) {
            if (token.getType() == TokenType.MAIN) {
                mainEncontrada = true;
                break;
            }
        }
        if (!mainEncontrada) {
            throw new SemanticoException("Função 'main' não declarada.");
        }
    }

    public class SemanticoException extends Exception {

        public SemanticoException(String mensagem) {
            super(mensagem);
        }
    }
}
