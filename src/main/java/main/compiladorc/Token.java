/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.compiladorc;

public class Token {

    private TokenType type; // Tipo do token
    private String lexeme; // Lexema associado ao token
    private int line; // Linha onde o token foi encontrado

    public Token(TokenType type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
    }

    Token(TokenType type, String lexeme, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
    }

    public TokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String toString() {
        return String.format("    ==> %-20s => '%-30s' => %d", type.name(), lexeme, line);
    }

}
