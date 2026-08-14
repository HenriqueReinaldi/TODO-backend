package org.henrique.permanencia;

import org.henrique.classes.Quadro_de_tarefas;
import org.henrique.classes.Tarefa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Permanencia {
    private static final String path = "/home/carlos/Documents/projetos/projetosJAVA/permanencia";
    public ArrayList<Quadro_de_tarefas> quadros_carregados;


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
                quadros_carregados.add(quadro);
            }
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("vbai se foder (erro abrindo arquivo)");
            System.exit(-1);
        }
    }


    private void delete_tarefa(Path path){
        try {
            Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(java.io.File::delete);
        }
        catch (IOException e){
            System.out.println("deu merda limpando tarefa");
            System.exit(-1);
        }
    }

    private void create_tarefa(Tarefa t, String path){
        File tp = new File(path);

        System.out.println(tp);
        if (!tp.mkdir()){
            return;
        }

        List<String> info = List.of("categoria", "data_termino", "desc", "prioridade", "status");
        for (String i : info) {
            try {
                Path pt = Path.of(path, i);
                Files.createFile(pt);

                Files.writeString(pt, t.get_attr(i) );
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("deu merda criando tarefa");
                System.exit(-1);
            }
        }
    }

    private void save_quadro(Quadro_de_tarefas q){
        Path root = Paths.get(path, q.nome);

        List<Path> tarefas_paths = List.of();
        try{
            Stream<Path> stream = Files.list(root);
            tarefas_paths = stream.filter(Files::isDirectory).toList();
            stream.close();
        }
        catch (IOException e){
            System.out.println("deu merda salvando tarefas");
            System.exit(-1);
        }


        for (Path tp : tarefas_paths){
            delete_tarefa(tp);
        }

        for (Tarefa t : q.lista_de_tarefas){
            create_tarefa(t, Path.of(String.valueOf(root), t.nome).toString());
        }

    }


    public Permanencia() {
        quadros_carregados = new ArrayList<>();
        load_quadros();
        save_quadro(quadros_carregados.getFirst());
    }
}
