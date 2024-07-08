/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package main.compiladorc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import main.compiladorc.AnalisadorLexico.LexicoException;
import main.compiladorc.AnalisadorSemantico.SemanticoException;
import main.compiladorc.AnalisadorSintatico.SintaticoException;
import static main.compiladorc.Lexico.analyze;
import static main.compiladorc.Lexico.getListTokenType;

public class CompiladorC {

    public static void main(String[] args) throws SemanticoException, TabelaDeSimbolos.SemanticoException, SintaticoException {
        String filePath = "../INPUT.txt";
        try {
            // Inicializa os analisadores
//            AnalisadorLexico lexico = new AnalisadorLexico(filePath);

//            Lexico l = new Lexico();
//            Lexico.analyze(filePath)
            analyze(filePath);
            List<Token> tokens = getListTokenType();
             System.out.println("\nTokens reconhecidos:\n");
            for (Token token : tokens) {
                System.out.println(token);
            }
            // Realiza a análise léxica
//            List<Token> tokens = lexico.tokenize();

            // Realiza a análise sintática
//            AnalisadorSintatico sintatico = new AnalisadorSintatico(tokens);
//            sintatico.analyze();

            AnalisadorSemantico semantico = new AnalisadorSemantico(tokens);

            // Realiza a análise sintática
            semantico.analyze();

            // Exibe os tokens reconhecidos (opcional)
//            System.out.println("\nTokens reconhecidos:\n");
//            for (Token token : tokens) {
//                if (token != null) {
//                    System.out.println(token);
//                }
//            }

            // Se chegou até aqui, o código foi compilado com sucesso
            System.out.println("Compilação concluída sem erros.");

            // Fecha o leitor após o uso
//            lexico.close();

        } catch (SemanticoException e) {
            System.err.println("Erro de compilação: " + e.getMessage());
        }
    }
}
