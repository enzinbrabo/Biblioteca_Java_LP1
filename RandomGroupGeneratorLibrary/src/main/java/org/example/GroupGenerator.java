package org.example;

import java.util.ArrayList;
import java.util.Random;

public class GroupGenerator {

    static ArrayList<String> estudantes = new ArrayList<>();
    static ArrayList<String> paresFormados = new ArrayList<>();
    static int contador = 0;

    // Adicionar estudante
    public static boolean inserirEstudante(String nome) {

        if (nome.isEmpty()) {
            return false;
        }

        if (estudantes.contains(nome)) {
            return false;
        }

        estudantes.add(nome);
        return true;
    }

    // Gerar grupo aleatório
    public static String gerarGrupoAleatorio() {

        if (estudantes.size() < 2) {
            return null;
        }

        Random random = new Random();
        int limiteDeTentativas = 0;

        while (limiteDeTentativas < 1000) {
            limiteDeTentativas++;
            int indice1 = random.nextInt(estudantes.size());
            int indice2 = random.nextInt(estudantes.size());

            if (indice1 == indice2) {
                continue;
            }

            String aluno1 = estudantes.get(indice1);
            String aluno2 = estudantes.get(indice2);

            String par = aluno1 + "-" + aluno2;
            String parAoContrario = aluno2 + "-" + aluno1;


            if (paresFormados.contains(par) || paresFormados.contains(parAoContrario)) {
                continue;
            }

            paresFormados.add(par);
            estudantes.remove(aluno1);
            estudantes.remove(aluno2);
            contador++;


            return par;
        }

        return null;
    }

    // Histórico de pares
    public static ArrayList<String> getHistorico() {
        return paresFormados;
    }

    // Gerar grupo manualmente usando posições
    public static String gerarGrupoManual(int posicaoAluno1, int posicaoAluno2) {

        if (estudantes.size() < 2) {
            return null;
        }

        if (posicaoAluno1 == posicaoAluno2) {
            return null;
        }

        if (posicaoAluno1 >= estudantes.size() || posicaoAluno2 >= estudantes.size()) {
            return null;
        }

        String aluno1 = estudantes.get(posicaoAluno1);
        String aluno2 = estudantes.get(posicaoAluno2);
        String parManual = aluno1 + "-" + aluno2;
        String parManual2 = aluno2 + "-" + aluno1;

        if (paresFormados.contains(parManual) || paresFormados.contains(parManual2)) {
            return null;
        }

        paresFormados.add(parManual);
        estudantes.remove(aluno1);
        estudantes.remove(aluno2);
        contador++;
        return parManual;
    }
    //Grupos que ja foram feitos
    public static void inserirNoHistorico(String par) {
        if (!paresFormados.contains(par)) {
            paresFormados.add(par);
        }
    }
}