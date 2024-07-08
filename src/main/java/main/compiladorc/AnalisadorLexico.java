/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.compiladorc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnalisadorLexico {

    private BufferedReader reader;
    private char currentChar;
    private int currentLine;

    public AnalisadorLexico(String filePath) throws IOException {
        this.reader = new BufferedReader(new FileReader(filePath));
        this.currentChar = (char) reader.read();
        this.currentLine = 1;
    }

    public List<Token> tokenize() throws IOException, LexicoException {
        List<Token> tokens = new ArrayList<>();
        while (currentChar != 0xFFFF) { // 0xFFFF indica o final do arquivo
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace();
            } else if (Character.isLetter(currentChar) || currentChar == '_') {
                tokens.add(parseIdentifierOrKeyword());
            } else if (Character.isDigit(currentChar)) {
                tokens.add(parseNumber());
            } else {
                tokens.add(parseSymbol());
            }
        }
        tokens.add(new Token(TokenType.FINAL_ARQUIVO, "")); // Adiciona token de final de arquivo
        return tokens;
    }

    private void skipWhitespace() throws IOException {
        while (Character.isWhitespace(currentChar)) {
            if (currentChar == '\n') {
                currentLine++;
            }
            currentChar = (char) reader.read();
        }
    }

    private Token parseIdentifierOrKeyword() throws IOException {
        StringBuilder sb = new StringBuilder();
        int line = currentLine;
        while (Character.isLetterOrDigit(currentChar) || currentChar == '_') {
            sb.append(currentChar);
            currentChar = (char) reader.read();
        }
        String lexeme = sb.toString();
        TokenType type = TokenType.getKeywordType(lexeme);
        if (type == null) {
            type = TokenType.IDENTIFICADOR;
        }
        return new Token(type, lexeme);
    }

    private Token parseNumber() throws IOException, LexicoException {
        StringBuilder sb = new StringBuilder();
        int line = currentLine;
        while (Character.isDigit(currentChar)) {
            sb.append(currentChar);
            currentChar = (char) reader.read();
        }
        return new Token(TokenType.NUMERO, sb.toString());
    }

    private Token parseSymbol() throws IOException, LexicoException {
        switch (currentChar) {
            case '+':
                currentChar = (char) reader.read();
                if (currentChar == '=') {
                    currentChar = (char) reader.read();
                    return new Token(TokenType.MAIS_IGUAL, "+=");
                }
                return new Token(TokenType.ADICAO, "+");
            case '-':
                currentChar = (char) reader.read();
                if (currentChar == '=') {
                    currentChar = (char) reader.read();
                    return new Token(TokenType.MENOS_IGUAL, "-=");
                }
                return new Token(TokenType.SUBTRACAO, "-");
            case '*':
                currentChar = (char) reader.read();
                if (currentChar == '=') {
                    currentChar = (char) reader.read();
                    return new Token(TokenType.MULTIPLICACAO_IGUAL, "*=");
                }
                return new Token(TokenType.MULTIPLICACAO, "*");
            case '/':
                currentChar = (char) reader.read();
                if (currentChar == '=') {
                    currentChar = (char) reader.read();
                    return new Token(TokenType.DIVISAO_IGUAL, "/=");
                } else if (currentChar == '/') {
                    skipCommentLine();
                    return null; // Comentário ignorado, retorna null
                } else if (currentChar == '*') {
                    skipCommentBlock();
                    return null; // Comentário ignorado, retorna null
                }
                return new Token(TokenType.DIVISAO, "/");
            case '=':
                currentChar = (char) reader.read();
                if (currentChar == '=') {
                    currentChar = (char) reader.read();
                    return new Token(TokenType.IGUAL_IGUAL, "==");
                }
                return new Token(TokenType.IGUAL, "=");
            case '>':
                currentChar = (char) reader.read();
                if (currentChar == '=') {
                    currentChar = (char) reader.read();
                    return new Token(TokenType.MAIOR_IGUAL, ">=");
                } else if (currentChar == '>') {
                    currentChar = (char) reader.read();
                    if (currentChar == '=') {
                        currentChar = (char) reader.read();
                        return new Token(TokenType.MAIOR_MAIOR_IGUAL, ">>=");
                    }
                    return new Token(TokenType.MAIOR_MAIOR, ">>");
                }
                return new Token(TokenType.MAIOR, ">");
            case '<':
                currentChar = (char) reader.read();
                if (currentChar == '=') {
                    currentChar = (char) reader.read();
                    return new Token(TokenType.MENOR_IGUAL, "<=");
                } else if (currentChar == '<') {
                    currentChar = (char) reader.read();
                    if (currentChar == '=') {
                        currentChar = (char) reader.read();
                        return new Token(TokenType.MENOR_MENOR_IGUAL, "<<=");
                    }
                    return new Token(TokenType.MENOR_MENOR, "<<");
                }
                return new Token(TokenType.MENOR, "<");
            case '!':
                currentChar = (char) reader.read();
                if (currentChar == '=') {
                    currentChar = (char) reader.read();
                    return new Token(TokenType.NAO_IGUAL, "!=");
                }
                return new Token(TokenType.PONTO_DE_EXCLAMACAO, "!");
            case '&':
                currentChar = (char) reader.read();
                if (currentChar == '&') {
                    currentChar = (char) reader.read();
                    return new Token(TokenType.E_E, "&&");
                }
                return new Token(TokenType.E_COMERCIAL, "&");
            case '|':
                currentChar = (char) reader.read();
                if (currentChar == '|') {
                    currentChar = (char) reader.read();
                    return new Token(TokenType.OU, "||");
                }
                return new Token(TokenType.BARRA_VERTICAL, "|");
            case '^':
                currentChar = (char) reader.read();
                if (currentChar == '=') {
                    currentChar = (char) reader.read();
                    return new Token(TokenType.POTENCIA_IGUAL, "^=");
                }
                return new Token(TokenType.POTENCIA, "^");
            case '~':
                currentChar = (char) reader.read();
                return new Token(TokenType.TIL, "~");
            case '#':
                currentChar = (char) reader.read();
                return new Token(TokenType.CARDINAL, "#");
            case ':':
                currentChar = (char) reader.read();
                if (currentChar == '>') {
                    currentChar = (char) reader.read();
                    return new Token(TokenType.DOIS_PONTOS, ":>");
                }
                return new Token(TokenType.DOIS_PONTOS, ":");
            case '.':
                currentChar = (char) reader.read();
                if (currentChar == '.') {
                    currentChar = (char) reader.read();
                    if (currentChar == '.') {
                        currentChar = (char) reader.read();
                        return new Token(TokenType.DUPLA_PERCENTAGEM_DOIS_PONTOS, "...:");
                    }
                    return new Token(TokenType.PERCENTAGEM_DOIS_PONTOS, "..");
                }
                return new Token(TokenType.PONTO, ".");
            default:
                // Caractere desconhecido, trata como erro ou ignora
                currentChar = (char) reader.read();
                break;
        }
        return null;
    }

    private void skipCommentLine() throws IOException {
        while (currentChar != '\n' && currentChar != 0xFFFF) {
            currentChar = (char) reader.read();
        }
    }

    private void skipCommentBlock() throws IOException, LexicoException {
        currentChar = (char) reader.read();
        char prevChar = 0;
        while (!(prevChar == '*' && currentChar == '/') && currentChar != 0xFFFF) {
            if (currentChar == '\n') {
                currentLine++;
            }
            prevChar = currentChar;
            currentChar = (char) reader.read();
        }
        if (currentChar == 0xFFFF) {
            throw new LexicoException("Comentário de bloco não fechado.");
        }
        currentChar = (char) reader.read(); // Avança após o fechamento do comentário
    }

    public void close() throws IOException {
        reader.close();
    }

    public class LexicoException extends Exception {

        public LexicoException(String message) {
            super(message);
        }
    }
}
