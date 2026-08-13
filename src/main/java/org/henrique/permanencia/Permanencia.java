package org.henrique.permanencia;

import org.henrique.classes.Quadro_de_tarefas;
import org.henrique.classes.Tarefa;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

// QUANDO O SISTEMA FOR INVOCADO, DEVE CARREGAR TODAS AS DIFERENTES LISTAS
//


public class Permanencia {
    private static final String path = "/home/carlos/Documents/projetos/projetosJAVA/permanencia";
    private static final String perm_path = "/home/carlos/Documents/projetos/projetosJAVA/permanencia/perm";

    private static ArrayList<Quadro_de_tarefas> quadros;


    private String get_file_content(String path) throws FileNotFoundException{
        File f = new File(path);
        Scanner f_read = new Scanner(f);
        StringBuilder sb = new StringBuilder();
        while (f_read.hasNextLine()){
            sb.append(f_read.nextLine());
        }
        return sb.toString();
    }


    private Tarefa fetch_tarefa(String path, String nome) throws FileNotFoundException{
        String desc = get_file_content(Path.of(path, "desc").toString());
        String data_termino = get_file_content(Path.of(path, "data_termino").toString());
        String categoria = get_file_content(Path.of(path, "categoria").toString());
        String status = get_file_content(Path.of(path, "status").toString());
        String prioridade_string = get_file_content(Path.of(path, "prioridade").toString());
        int prioridade = 0;

        try{
            prioridade = Integer.parseInt(prioridade_string);
        } catch (NumberFormatException ex){
            //fds
        }


        return new Tarefa(nome, desc, data_termino, prioridade, categoria, status);
    }


    private Quadro_de_tarefas fetch_quadro(String path, String nome) throws FileNotFoundException{
        File f = new File(Path.of(path, "_").toString());
        Quadro_de_tarefas qt = new Quadro_de_tarefas(nome);

        Scanner f_read = new Scanner(f);
        while(f_read.hasNextLine()){
            String tarefa = f_read.nextLine();
            Tarefa pega = fetch_tarefa(Path.of(path, tarefa).toString(), tarefa);
            qt.append_tarefa(pega);
        }

        return qt;
    }

    public Permanencia() {
        quadros = new ArrayList<>();
        File f = new File(perm_path);

        try{
            Scanner f_read = new Scanner(f);
            while (f_read.hasNextLine()){
                String linha = f_read.nextLine();
                String quadro_path = Path.of(path, linha).toString();
                Quadro_de_tarefas quadro = fetch_quadro(quadro_path, linha);

                quadros.add(quadro);
            }
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
            System.out.println("vbai se foder (erro abrindo arquivo)");
            System.exit(-1);
        }


        for(Quadro_de_tarefas q : quadros){
            IO.println(q.nome);
            q.listar_tarefas();
        }
    }
}
