/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.compiladorc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexico {

    private static final Map<String, TokenType> reservedWords = new HashMap<>();
    private static final List<Token> ListTokenType = new ArrayList<>();

    static {
        for (TokenType type : TokenType.values()) {
            if (type.getLexeme() != null) {
                reservedWords.put(type.getLexeme(), type);
            }
        }
    }

    public static void analyze(String filename) {
        ListTokenType.clear(); // Limpa a lista de TokenType antes de iniciar uma nova análise
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            int lineNumber = 1; // Iniciar a contagem das linhas em 1
            while ((line = reader.readLine()) != null) {
                analyzeLine(line, lineNumber);
                lineNumber++;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo");
            System.out.println(e.getMessage());
        }
    }

    private static void analyzeLine(String line, int lineNumber) {
        if (line.trim().startsWith("#")) {
            ListTokenType.add(new Token(TokenType.BIBLIOTECA, "#", lineNumber));
        }

        String[] tokens = line.split("\\s+|(?=[{}(),;\\[\\]])|(?<=[{}(),;\\[\\]])|(?<=[^\\w\\s])[=+-]");
        for (String token : tokens) {
            // Tipo de Dados
            switch (token) {
                case "char":
                    ListTokenType.add(new Token(TokenType.CHAR, token, lineNumber));
                    break;
                case "printf":
                    ListTokenType.add(new Token(TokenType.PRINTF, token, lineNumber));
                    break;
                case "scanf":
                    ListTokenType.add(new Token(TokenType.SCANF, token, lineNumber));
//                case "string":
//                    ListTokenType.add(new Token(TokenType.STRING, token, lineNumber));
                    break;
                case "double":
                    ListTokenType.add(new Token(TokenType.DOUBLE, token, lineNumber));
                    break;
                case "float":
                    ListTokenType.add(new Token(TokenType.FLOAT, token, lineNumber));
                    break;
                case "int":
                    ListTokenType.add(new Token(TokenType.INT, token, lineNumber));
                    break;
                case "long":
                    ListTokenType.add(new Token(TokenType.LONG, token, lineNumber));
                    break;
                case "short":
                    ListTokenType.add(new Token(TokenType.SHORT, token, lineNumber));
                    break;
                case "signed":
                    ListTokenType.add(new Token(TokenType.SIGNED, token, lineNumber));
                    break;
                case "unsigned":
                    ListTokenType.add(new Token(TokenType.UNSIGNED, token, lineNumber));
                    break;
                case "void":
                    ListTokenType.add(new Token(TokenType.VOID, token, lineNumber));
                    break;
                case "+":
                    ListTokenType.add(new Token(TokenType.ADICAO, token, lineNumber));
                    break;
                case "-":
                    ListTokenType.add(new Token(TokenType.SUBTRACAO, token, lineNumber));
                    break;
                case "*":
                    ListTokenType.add(new Token(TokenType.MULTIPLICACAO, token, lineNumber));
                    break;
                case "/":
                    ListTokenType.add(new Token(TokenType.DIVISAO, token, lineNumber));
                    break;
                case "==":
                    ListTokenType.add(new Token(TokenType.IGUAL_IGUAL, token, lineNumber));
                    break;
                case "!=":
                    ListTokenType.add(new Token(TokenType.NAO_IGUAL, token, lineNumber));
                    break;
                case "<":
                    ListTokenType.add(new Token(TokenType.MENOR, token, lineNumber));
                    break;
                case ">":
                    ListTokenType.add(new Token(TokenType.MAIOR, token, lineNumber));
                    break;
                case "<=":
                    ListTokenType.add(new Token(TokenType.MENOR_IGUAL, token, lineNumber));
                    break;
                case ">=":
                    ListTokenType.add(new Token(TokenType.MAIOR_IGUAL, token, lineNumber));
                    break;
                case "++":
                    ListTokenType.add(new Token(TokenType.INCREMENTO, token, lineNumber));
                    break;
                case "--":
                    ListTokenType.add(new Token(TokenType.DECREMENTO, token, lineNumber));
                    break;
                case "*=":
                    ListTokenType.add(new Token(TokenType.MULTIPLICACAO_IGUAL, token, lineNumber));
                    break;
                case "+=":
                    ListTokenType.add(new Token(TokenType.MAIS_IGUAL, token, lineNumber));
                    break;
                case "-=":
                    ListTokenType.add(new Token(TokenType.MENOS_IGUAL, token, lineNumber));
                    break;
                case "/=":
                    ListTokenType.add(new Token(TokenType.DIVISAO_IGUAL, token, lineNumber));
                    break;
                case "//":
                    ListTokenType.add(new Token(TokenType.COMENTARIO, token, lineNumber));
                    break;
                case "/*":
                case "*/":
                    ListTokenType.add(new Token(TokenType.COMENTARIO, token, lineNumber));
                    break;
                case "(":
                    ListTokenType.add(new Token(TokenType.ABRE_PARENTES, token, lineNumber));
                    break;
                case ")":
                    ListTokenType.add(new Token(TokenType.FECHA_PARENTES, token, lineNumber));
                    break;
                case "{":
                    ListTokenType.add(new Token(TokenType.ABRE_CHAVETA, token, lineNumber));
                    break;
                case "}":
                    ListTokenType.add(new Token(TokenType.FECHA_CHAVETA, token, lineNumber));
                    break;
                case ";":
                    ListTokenType.add(new Token(TokenType.FINAL_DE_INSTRUCAO, token, lineNumber));
                    break;
                default:
                    if (token.matches("\\d+")) {
                        ListTokenType.add(new Token(TokenType.NUMERO, token, lineNumber));
                    } else if (token.matches("\\d+\\.\\d+")) {
                        ListTokenType.add(new Token(TokenType.NUMERO, token, lineNumber));
                    } else if (reservedWords.containsKey(token)) {
                        ListTokenType.add(new Token(reservedWords.get(token), token, lineNumber));
                    } else if (token.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
                        if (isFuncao(line, token)) {
                            ListTokenType.add(new Token(TokenType.FUNCTION, token, lineNumber));
                        } else if (isArray(line, token)) {
                            ListTokenType.add(new Token(TokenType.IDENTIFICADOR, token, lineNumber));
                        } else {
                            ListTokenType.add(new Token(TokenType.IDENTIFICADOR, token, lineNumber));
                        }
                    } else if (token.startsWith("\"") && token.endsWith("\"")) {
                        ListTokenType.add(new Token(TokenType.STRING, token, lineNumber));
                    }
                    break;
            }
        }
    }

    private static boolean isArray(String line, String token) {
        int index = line.indexOf(token);
        return index != -1 && index + token.length() < line.length() && line.charAt(index + token.length()) == '['
                && (index == 0 || !Character.isLetterOrDigit(line.charAt(index - 1)));
    }

    
    
    private static boolean isFuncao(String line, String token) {
        int index = line.indexOf(token);
        return index != -1 && index + token.length() < line.length() && line.charAt(index + token.length()) == '('
                && (index == 0 || !Character.isLetterOrDigit(line.charAt(index - 1)));
    }

    public static List<Token> getListTokenType() {
        return ListTokenType;
    }

    public static void main(String[] args) {
        // Teste básico de análise léxica
        analyze("INPUT.txt");
        for (Token token : ListTokenType) {
            System.out.println(token);
        }
    }
}

//class Token {
//    private TokenType type;
//    private String lexeme;
//
//    public Token(TokenType type, String lexeme) {
//        this.type = type;
//        this.lexeme = lexeme;
//    }
//
//    public TokenType getType() {
//        return type;
//    }
//
//    public String getLexeme() {
//        return lexeme;
//    }
//
//    @Override
//    public String toString() {
//        return "Token{" +
//                "type=" + type +
//                ", lexeme='" + lexeme + '\'' +
//                '}';
//    }
//}
