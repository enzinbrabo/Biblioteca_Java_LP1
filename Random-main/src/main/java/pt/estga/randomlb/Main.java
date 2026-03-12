package pt.estga.randomlb;

import lp.trabalho1.IODataClass;
import lp.trabalho1.StudentInfo;
import org.example.GroupGenerator;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String ficheiroEstudantes = "C:\\Users\\nicol\\Semestre-2\\LP\\Biblioteca_Java_LP1\\Random-main\\students";
        String ficheiroGrupos = "C:\\Users\\nicol\\Semestre-2\\LP\\Biblioteca_Java_LP1\\Random-main\\groups";
        IODataClass io = new IODataClass();

        StudentInfo[] listaEstudantes = io.loadStudentUC(ficheiroEstudantes);

        if (listaEstudantes == null) {
            System.out.println("Erro ao carregar estudantes.");
            return;
        }

        // Preenchimento inicial
        for (StudentInfo aluno : listaEstudantes) {
            GroupGenerator.inserirEstudante(aluno.getStudentName());
        }

        try (Scanner input = new Scanner(System.in)) {
            while (true) {
                System.out.println("1 - Gerar grupos de 2 estudantes de forma aleatoria");
                System.out.println("2 - Carregar o historico de grupos para evitar repeticoes");
                System.out.println("3 - Inserir manualmente um grupo e gerar o resto");
                System.out.println("0 - Sair do programa");
                System.out.print("Escolha uma opcao: ");

                String linha = input.nextLine();
                if (linha.isEmpty()) continue;
                char opcao = linha.charAt(0);

                if (opcao == '0') break;

                switch (opcao) {
                    case '1':
                        if (GroupGenerator.estudantes.isEmpty()) {
                            for (StudentInfo aluno : listaEstudantes) {
                                GroupGenerator.inserirEstudante(aluno.getStudentName());
                            }
                        }
                        gerarRestanteESalvar(listaEstudantes, io, ficheiroGrupos);
                        break;

                    case '2':
                        String[] historico = io.loadGroupsasString(ficheiroGrupos);
                        if (historico != null) {
                            for (String s : historico) {
                                String[] partes = s.split(",");
                                if (partes.length >= 5) {
                                    String aluno1 = partes[2].trim();
                                    String aluno2 = partes[4].trim();
                                    GroupGenerator.inserirNoHistorico(aluno1 + "-" + aluno2);
                                }
                            }
                            System.out.println("Historico carregado com sucesso! Os proximos grupos serao ineditos.");
                        } else {
                            System.out.println("Nenhum historico anterior encontrado.");
                        }
                        break;

                    case '3':
                        if (GroupGenerator.estudantes.isEmpty()) {
                            for (StudentInfo aluno : listaEstudantes) {
                                GroupGenerator.inserirEstudante(aluno.getStudentName());
                            }
                        }

                        System.out.println("Digite a posicao do primeiro aluno na lista (Ex: 0): ");
                        int pos1 = Integer.parseInt(input.nextLine());
                        System.out.println("Digite a posicao do segundo aluno na lista (Ex: 1): ");
                        int pos2 = Integer.parseInt(input.nextLine());

                        String grupoManual = GroupGenerator.gerarGrupoManual(pos1, pos2);
                        if (grupoManual != null) {
                            System.out.println("Grupo manual inserido com sucesso: " + grupoManual);
                            System.out.println("Gerando os grupos restantes automaticamente...");
                            gerarRestanteESalvar(listaEstudantes, io, ficheiroGrupos);
                        } else {
                            System.out.println("Erro: Posições inválidas ou este par de alunos já fez grupo no passado!");
                        }
                        break;

                    default:
                        System.out.println("Caracter invalido!");
                }
            }
        }
    }

    private static void gerarRestanteESalvar(StudentInfo[] listaEstudantes, IODataClass io, String ficheiroGrupos) {
        ArrayList<String> novosGrupos = new ArrayList<>();
        while (true) {
            String grupo = GroupGenerator.gerarGrupoAleatorio();
            if (grupo == null) {
                break;
            }
            novosGrupos.add(grupo);
        }

        if (novosGrupos.isEmpty()) {
            System.out.println("Aviso: Nao foi possivel gerar novos grupos (ou não ha alunos suficientes).");
        } else {
            System.out.println("Grupos gerados com sucesso!");
            salvarGruposNoFormatoDoProfessor(novosGrupos, listaEstudantes, io, ficheiroGrupos);
        }
    }

    private static void salvarGruposNoFormatoDoProfessor(ArrayList<String> novosGrupos, StudentInfo[] listaEstudantes, IODataClass io, String ficheiroGrupos) {
        String[] formatoFinal = new String[novosGrupos.size()];

        for (int i = 0; i < novosGrupos.size(); i++) {
            String[] nomes = novosGrupos.get(i).split("-");
            int id1 = encontrarIdDoAluno(listaEstudantes, nomes[0]);
            int id2 = encontrarIdDoAluno(listaEstudantes, nomes[1]);

            formatoFinal[i] = (i + 1) + ", " + id1 + ", " + nomes[0] + ", " + id2 + ", " + nomes[1];
            System.out.println("Grupo " + (i + 1) + ": " + nomes[0] + " e " + nomes[1]);
        }

        try {
            io.outputGroups(ficheiroGrupos, formatoFinal);
            System.out.println("Grupos gravados no ficheiro com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao gravar no ficheiro.");
        }
    }

    private static int encontrarIdDoAluno(StudentInfo[] lista, String nome) {
        for (StudentInfo s : lista) {
            if (s.getStudentName().equals(nome)) {
                return s.getStudentID();
            }
        }
        return 0;
    }
}