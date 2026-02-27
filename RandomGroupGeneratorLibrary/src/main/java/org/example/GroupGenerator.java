package org.example;

import java.util.Scanner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GroupGenerator {

    static ArrayList<String> estudantes = new ArrayList<>();
    static HashSet<Set<String>> paresFormados = new HashSet<>();

    public static void main(String[] args) {
        char opcao;
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Para inserir novos estudantes digite '1' ");
            System.out.println("Para gerar grupos de 2 estudantes de forma aleatoria digite '2' ");
            System.out.println("Para carregar o historico de grupos digite '3' ");
            System.out.println("Para inserir manualmente os grupos digite '4'. ");

            opcao = input.nextLine().charAt(0);

            switch (opcao) {
                case '1':
                    inserirEstudantes();
                    break;
                case '2':
                    gerarGrupos(estudantes, paresFormados);
                    break;
                case '3':
                
                    break;
                case '4':

                    break;
                default:
                    System.out.println("Caracter invalido");
            }
        }
    }

    // Método para inserir novos estudantes ao ArrayList estudantes.
    public static void inserirEstudantes() {
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Digite o nome do estudante: ");
            String nome = input.nextLine();

            if (nome.isEmpty()) {
                System.out.println("Nome invalido.");
                return;
            }

            if (estudantes.contains(nome)) {
                System.out.println("Estudante existente.");
            } else {
                estudantes.add(nome);
                System.out.println("Estudante adicionado com sucesso.");
            }
        }
    }
    // Método para criar pares aleatoriamente sem repetição.
    public static void gerarGrupos(ArrayList<String> estudantes, HashSet<Set<String>> paresFormados) {

        if (estudantes.size() < 2) {
            System.out.println("Não há estudantes suficientes para formar pares.");
            return;
        }

        Random random = new Random();

        while (true) {
            int indice1 = random.nextInt(estudantes.size());
            int indice2 = random.nextInt(estudantes.size());

            if (indice1 == indice2) {
                continue;
            }

            String aluno1 = estudantes.get(indice1);
            String aluno2 = estudantes.get(indice2);


            Set<String> par = new HashSet<>();
            par.add(aluno1);
            par.add(aluno2);

            if (paresFormados.contains(par)) {
                System.out.println("Par existente. ");
                continue;
            }
            paresFormados.add(par);
            break;
        }
    }
}
