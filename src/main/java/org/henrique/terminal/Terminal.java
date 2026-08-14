package org.henrique.terminal;

import org.henrique.classes.Quadro_de_tarefas;
import org.henrique.classes.Tarefa;
import org.henrique.permanencia.Permanencia;


import java.lang.reflect.Array;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Terminal {
    private final Permanencia perm;
    private Quadro_de_tarefas quadro_carregado;

    private modo_term mode = modo_term.SEM_QUADRO;
    private modo_listagem mode_list = modo_listagem.L_STATUS;
    private int mostar_prox = 1;

    private void interpretar_modoz(String in){
        if (in.equals("sair")){
            mode = modo_term.SAIR;
            return;
        }

        if (in.startsWith("novo ") && in.length() > 5){
            String nome = in.substring(5);

            for (Quadro_de_tarefas q : perm.quadros_carregados){
                if (q.nome.equals(nome)){
                    System.out.println("Esse quadro já existe!");
                    return;
                }
            }

            Quadro_de_tarefas novo = new Quadro_de_tarefas(nome);

            perm.quadros_carregados.add(novo);
            quadro_carregado = novo;
            mode = modo_term.COM_QUADRO;
        }

        for (Quadro_de_tarefas q : perm.quadros_carregados){
            if (q.nome.equals(in)){
                quadro_carregado = q;
                mode = modo_term.COM_QUADRO;
                return;
            }
        }

    }

    private void interpretar_modoo(String in){
        if (in.equals("sair")) {
            mode = modo_term.SAIR;
            return;
        }

        if (in.startsWith("modo ") && in.length() > 5){
            String ver = in.substring(5);
            switch (ver){
                case "status" -> mode_list = modo_listagem.L_STATUS;
                case "prioridade" -> mode_list = modo_listagem.L_PRIORIDADE;
                case "categoria" -> mode_list = modo_listagem.L_CATEGORIA;
                default -> mode_list = modo_listagem.L_FDS;
            }
            return;
        }

        if (in.startsWith("nova ") && in.length() > 5){
            String nome = in.substring(5);
            if (quadro_contem(nome)) return;

            Tarefa nova = new Tarefa(in.substring(5), "", "", 0, "", "TODO");
            quadro_carregado.append_tarefa(nova);
            return;
        }

        if (in.startsWith("ver ") && in.length() > 4){
            ver_tarefa(in.substring(4));
            mostar_prox = 0;
            return;
        }

        if (in.startsWith("todo ") && in.length() > 5){
            String nome = in.substring(5);
            Tarefa t = get_ref_quadro(nome);
            if (t == null) return;

            t.status = "TODO";
            return;
        }
        if (in.startsWith("doing ") && in.length() > 6){
            String nome = in.substring(6);
            Tarefa t = get_ref_quadro(nome);
            if (t == null) return;

            t.status = "DOING";
            return;
        }
        if (in.startsWith("done ") && in.length() > 5){
            String nome = in.substring(5);
            Tarefa t = get_ref_quadro(nome);
            if (t == null) return;

            t.status = "DONE";
            t.data_termino = LocalDateTime.now().toString();
            return;
        }


        if (in.startsWith("prioridade ") && in.length() > 11){
            String[] args = in.substring(11).split(" ", 2);
            if (args.length != 2) return;

            Tarefa t = get_ref_quadro(args[0]);
            if (t == null) return;

            t.prioridade = Integer.parseInt(args[1]);

        }
        if (in.startsWith("descricao ") && in.length() > 10){
            String[] args = in.substring(10).split(" ", 2);
            if (args.length != 2) return;

            Tarefa t = get_ref_quadro(args[0]);
            if (t == null) return;

            t.desc = args[1];
        }
        if (in.startsWith("categoria ") && in.length() > 10){
            String[] args = in.substring(10).split(" ");
            if (args.length < 2) return;

            Tarefa t = get_ref_quadro(args[0]);
            if (t == null) return;

            t.categoria = args[1];
        }

        if (in.startsWith("del ") && in.length() > 4) {
            String nome = in.substring(4);

            Tarefa ref = get_ref_quadro(nome);
            if (ref == null) return;

            quadro_carregado.lista_de_tarefas.remove(ref);
            return;
        }

        if (in.equals("menu")){
            mode = modo_term.SEM_QUADRO;
            return;
        }
    }


    private boolean quadro_contem(String nome){
        for (Tarefa t : quadro_carregado.lista_de_tarefas){
            if (t.nome.equals(nome)){
                return true;
            }
        }
        return false;
    }

    private Tarefa get_ref_quadro(String nome){
        for (Tarefa t : quadro_carregado.lista_de_tarefas){
            if (t.nome.equals(nome)){
                return t;
            }
        }
        return null;
    }

    private void ver_tarefa(String nome){
        Tarefa ref = get_ref_quadro(nome);
        if (ref == null) return;

        System.out.printf("%s:==================\n\n", ref.nome);

        System.out.println("    descrição:" + ref.desc);
        System.out.println("    termino  :" + ref.data_termino);

        System.out.println("\n==========================\n");
    }
    private void listar_por_categoria(){
        ArrayList<String> prioridades = new ArrayList<>();

        for (Tarefa t : quadro_carregado.lista_de_tarefas){
            if (prioridades.contains(t.categoria)) continue;
            prioridades.add(t.categoria);
        }

        for (String cat : prioridades){
            System.out.printf("  %s:=====================\n\n", cat);

            for (Tarefa t : quadro_carregado.lista_de_tarefas) {
                if (t.categoria.equals(cat)) System.out.println("    " + t.nome);
            }
            System.out.println();
        }
    }
    private void listar_por_status(){
        System.out.println("  todo:===================\n");

        for (Tarefa t : quadro_carregado.lista_de_tarefas){
            if (t.status.equals("TODO")){
                System.out.println("    " + t.nome);
            }
        }
        System.out.println("\n  doing:==================\n");

        for (Tarefa t : quadro_carregado.lista_de_tarefas){
            if (t.status.equals("DOING")){
                System.out.println("    " + t.nome);
            }
        }

        System.out.println("\n  done:===================\n");

        for (Tarefa t : quadro_carregado.lista_de_tarefas){
            if (t.status.equals("DONE")){
                System.out.println("    " + t.nome);
            }
        }

    }
    private void listar_por_prioridade(){
        ArrayList<Integer> prioridades = new ArrayList<>();

        for (Tarefa t : quadro_carregado.lista_de_tarefas){
            if (prioridades.contains(t.prioridade)) continue;
            prioridades.add(t.prioridade);
        }

        Collections.sort(prioridades);

        for (Integer pri : prioridades){
            System.out.printf("  %-4d:===================\n\n", pri);

            for (Tarefa t : quadro_carregado.lista_de_tarefas) {
                if (t.prioridade == pri) System.out.println("    " + t.nome);
            }
            System.out.println();
        }
    }
    private void listar_tarefas(){
        if (mostar_prox == 0){
            mostar_prox = 1;
            return;
        }

        System.out.println("Tarefas:==================\n");

        switch (mode_list){
            case L_STATUS:
                listar_por_status();
                break;
            case L_CATEGORIA:
                listar_por_categoria();
                break;
            case L_PRIORIDADE:
                listar_por_prioridade();
                break;
            default:
                for (Tarefa t : quadro_carregado.lista_de_tarefas){
                    System.out.println("    " + t.nome);
                }
                System.out.println();
                break;
        }

        System.out.println("==========================\n");
    }

    public void iniciar(){
        Scanner scan = new Scanner(System.in);

        while (true){
            if (mode == modo_term.SEM_QUADRO){
                System.out.println("Selecione um quadro de tarefas:");
                System.out.println("    (para criar um novo, digite 'novo nome')");

                for (Quadro_de_tarefas q : perm.quadros_carregados){
                    System.out.println("    " + q.nome);
                }

                System.out.print("@>");
                interpretar_modoz(scan.nextLine());
            }
            else if (mode == modo_term.COM_QUADRO){
                listar_tarefas();

                System.out.print("@"+ quadro_carregado.nome +">");
                String comando = scan.nextLine();
                interpretar_modoo(comando);
            }
            else {
                break;
            }
        }

        for (Quadro_de_tarefas q : perm.quadros_carregados){
            perm.save_quadro(q);
        }
        System.out.println("até!");
    }

    public Terminal(Permanencia perm){
        this.perm = perm;
    }
}
