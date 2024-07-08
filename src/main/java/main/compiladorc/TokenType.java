/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.compiladorc;

public enum TokenType {
    // Tipos de Dados
    CHAR,
    STRING,
    DOUBLE,
    FLOAT,
    INT,
    LONG,
    SHORT,
    SIGNED,
    UNSIGNED,
    VOID,

    // Identificadores e Comentários
    IDENTIFICADOR,
    COMENTARIO,

    // Delimitadores e Finais
    ABRE_PARENTES,
    FECHA_PARENTES,
    ABRE_CHAVETA,
    FECHA_CHAVETA,
    ABRE_COLCHETE,
    FECHA_COLCHETE,
    FINAL_DE_INSTRUCAO,
    FINAL_ARQUIVO,

    // Operadores Aritméticos e de Atribuição
    INCREMENTO,
    DECREMENTO,
    MAIS_IGUAL,
    MENOS_IGUAL,
    MULTIPLICACAO_IGUAL,
    DIVISAO_IGUAL,
    IGUAL,
    ADICAO,
    SUBTRACAO,
    MULTIPLICACAO,
    DIVISAO,
    POTENCIA,
    UMA_BARRA_DIREITA,

    // Operadores Relacionais e Lógicos
    MAIOR,
    MENOR,
    MAIOR_IGUAL,
    MENOR_IGUAL,
    MAIOR_MAIOR,
    MENOR_MENOR_IGUAL,
    NAO_IGUAL,
    IGUAL_IGUAL,
    OU,
    E_E,

    // Operadores Especiais
    VIRGULA,
    DOIS_PONTOS,
    PONTO,
    PONTO_DE_EXCLAMACAO,
    E,
    TIL,
    PORCENTAGEM,
    CARDINAL,
    MAIOR_MAIOR_IGUAL,
    PORCENTAGEM_IGUAL,
    E_IGUAL,
    POTENCIA_IGUAL,
    BARRA_IGUAL,
    MENOR_DOIS_PONTOS,
    MENOR_PORCENTAGEM,
    PORCENTAGEM_MAIOR,
    PORCENTAGEM_DOIS_PONTOS,
    DUPLA_PORCENTAGEM_DOIS_PONTOS,

    // Palavras Reservadas
    AUTO,
    MAIN,
    PRINTF,
    FUNCTION,
    SCANF,
    BREAK,
    CASE,
    CONST,
    CONTINUE,
    DEFAULT,
    DO,
    ELSE,
    ENUM,
    EXTERN,
    FOR,
    GOTO,
    IF,
    INLINE,
    REGISTER,
    RESTRICT,
    RETURN,
    SIZEOF,
    STATIC,
    STRUCT,
    SWITCH,
    TYPEDEF,
    UNION,
    VOLATILE,
    WHILE,

    // Especiais Adicionais
    E_COMERCIAL, NUMERO, MENOR_MENOR, BARRA_VERTICAL,
    DUPLA_PERCENTAGEM_DOIS_PONTOS,
    PERCENTAGEM_DOIS_PONTOS, PARENTESES_FECHA,
    BIBLIOTECA,
    PARENTESES_ABRE, FORMAT_SPECIFIER, DIFERENTE, STRING_FORMATO;

    private String lexeme;

    TokenType() {
        this.lexeme = null;
    }

    TokenType(String lexeme) {
        this.lexeme = lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }

    // Método para obter o tipo de token a partir do lexeme
    public static TokenType getKeywordType(String lexeme) {
        switch (lexeme) {
            case "auto": return AUTO;
            case "main": return MAIN;
            case "printf": return PRINTF;
            case "scanf": return SCANF;
            case "break": return BREAK;
            case "case": return CASE;
            case "const": return CONST;
            case "continue": return CONTINUE;
            case "default": return DEFAULT;
            case "do": return DO;
            case "else": return ELSE;
            case "enum": return ENUM;
            case "extern": return EXTERN;
            case "for": return FOR;
            case "goto": return GOTO;
            case "if": return IF;
            case "inline": return INLINE;
            case "register": return REGISTER;
            case "restrict": return RESTRICT;
            case "return": return RETURN;
            case "sizeof": return SIZEOF;
            case "static": return STATIC;
            case "struct": return STRUCT;
            case "switch": return SWITCH;
            case "typedef": return TYPEDEF;
            case "union": return UNION;
            case "volatile": return VOLATILE;
            case "while": return WHILE;
            default: return null;
        }
    }
}
