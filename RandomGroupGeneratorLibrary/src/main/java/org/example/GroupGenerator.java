package org.example;

import java.util.Scanner;

import java.util.ArrayList;

import java.util.Random;

public class GroupGenerator {

    static ArrayList<String> estudantes = new ArrayList<>();
    static ArrayList<String> paresFormados = new ArrayList<>();
    static int contador = 0;

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
                    gerarGruposAleatorios(estudantes, paresFormados);
                    break;
                case '3':
                    historico(paresFormados);
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
    public static void gerarGruposAleatorios(ArrayList<String> estudantes, ArrayList<String> paresFormados) {

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
            String par = (aluno1 + "-" + aluno2);
            String parAoContrario = (aluno2 + "-" + aluno1);
            if (paresFormados.contains(par) || paresFormados.contains(parAoContrario)) {
                System.out.println("Esse par ja existe");
                break;
            }


            paresFormados.add(contador, par);
            contador++;
        }

    }

    //Metodo para printar historico de pares formados
    public static void historico(ArrayList<String> paresFormados) {
        for (int i = 0; i < paresFormados.size(); i++) {
            System.out.println(paresFormados.get(contador));
        }
    }

    //Metodo para inserir pares manualmente
    public static void gerarGruposManualmente(ArrayList<String> estudantes, ArrayList<String> paresFormados) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Lista de Estudantes disponiveis: ");
            for (int i = 0; i < estudantes.size(); i++) {
                System.out.println(estudantes.get(i) + "Posiçao: " + i);
            }
            System.out.println("Lista de grupos ja formados: ");
            for (int i = 0; i < paresFormados.size(); i++) {
                System.out.println(paresFormados.get(i));
            }
            System.out.println("Em que posiçao esta o primeiro elemento do grupo: ");
            int posicaoAluno1 = sc.nextInt();
            System.out.println("Em que posiçao esta o segundo elemento do grupo: ");
            int posicaoAluno2 = sc.nextInt();
            if (posicaoAluno2 == posicaoAluno1) {
                System.out.println("Nao pode existir um grupo com dois elementos iguais");
            }
            if (posicaoAluno1 > estudantes.size() || posicaoAluno2 > estudantes.size()) {
                System.out.println("Nao existe aluno nessa posiçao");
            } else {
                String parManual = (estudantes.get(posicaoAluno1) + "-" + estudantes.get(posicaoAluno2));
                String parManual2 = (estudantes.get(posicaoAluno2) + "-" + estudantes.get(posicaoAluno1));
                if (paresFormados.contains(parManual) || paresFormados.contains(parManual2)) {
                    System.out.println("Esse par ja existe");
                }
                paresFormados.add(contador, parManual);
                contador++;

            }
        }

    }
}
