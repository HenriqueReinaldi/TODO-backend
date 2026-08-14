package org.henrique.permanencia;

import org.henrique.classes.Quadro_de_tarefas;
import org.henrique.classes.Tarefa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// QUANDO O SISTEMA FOR INVOCADO, DEVE CARREGAR TODAS AS DIFERENTES LISTAS
//


public class Permanencia {
    private static final String path = "/home/carlos/Documents/projetos/projetosJAVA/permanencia";

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

    private Quadro_de_tarefas fetch_quadro(Path path) throws IOException{
        Stream<Path> stream = Files.list(path);
        List<Path> tarefas_paths = stream.filter(Files::isDirectory).toList();
        stream.close();

        Quadro_de_tarefas qt = new Quadro_de_tarefas(path.getFileName().toString());

        for (Path tp : tarefas_paths){
            Tarefa pega = fetch_tarefa(tp.toString(), tp.getFileName().toString());
            qt.append_tarefa(pega);

        }

        return qt;
    }

    private void load_quadros(){
        Path root = Paths.get(path);

        try (Stream<Path> stream = Files.list(root)){

            List<Path> quadros_paths = stream.filter(Files::isDirectory).toList();
            for (Path qp : quadros_paths) {
                Quadro_de_tarefas quadro = fetch_quadro(qp);
                quadros.add(quadro);
            }

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("vbai se foder (erro abrindo arquivo)");
            System.exit(-1);
        }

        for(Quadro_de_tarefas q : quadros){
            System.out.println(q.nome);
            q.listar_tarefas();
        }
    }




    public Permanencia() {
        quadros = new ArrayList<>();
        load_quadros();
    }
}
