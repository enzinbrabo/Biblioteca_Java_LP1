package pt.estga.randomlb;

import lp.trabalho1.IODataClass;
import lp.trabalho1.StudentInfo;
import org.example.GroupGenerator;
import java.util.Scanner;


public class Main {
    static void main(String[] args) {

        String ficheiroEstudantes = "students.txt";
        String ficheiroGrupos = "groups.txt";
        IODataClass io = new IODataClass();

        StudentInfo[] listaEstudantes = io.loadStudentUC(ficheiroEstudantes);

        if (listaEstudantes == null) {
            System.out.println("Erro ao carregar estudantes.");
            return;
        }
        char opcao;
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Para inserir novos estudantes digite '1' ");
            System.out.println("Para gerar grupos de 2 estudantes de forma aleatoria digite '2' ");
            System.out.println("Para carregar o historico de grupos digite '3' ");
            System.out.println("Para inserir manualmente os grupos digite '4'. ");

            opcao = input.nextLine().charAt(0);

            switch (opcao) {
                case '1':
                    for  (int i = 0; i < listaEstudantes.length; i++) {
                        String nome = listaEstudantes[i].getStudentName();
                        GroupGenerator.inserirEstudante(nome);
                    }
                    break;
                case '2':
                    String[] gruposGerados = new String[listaEstudantes.length / 2];
                    int contador = 0;
                    while (true) {
                        String grupo = GroupGenerator.gerarGrupoAleatorio();

                        if (grupo == null) {
                            break;
                        }
                        gruposGerados[contador] = grupo;
                        contador++;
                    }
                    System.out.println("Grupos gerados com sucesso!");
                    io.outputGroups(ficheiroGrupos, gruposGerados);
                    break;

                case '3':
                    GroupGenerator.getHistorico();
                    break;
                case '4':
                    GroupGenerator.gerarGrupoManual(0, 0);
                    break;
                default:
                    System.out.println("Caracter invalido");
            }
        }



    }
}
