package org.henrique.alarme;

import org.henrique.classes.Tarefa;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class Alarme {
    private final DateTimeFormatter formato;

    public void criar_alarme_pergunta(Tarefa t){
        Scanner scan = new Scanner(System.in);
        System.out.print("Criar alarme? (sim/nao)> ");
        if (!scan.nextLine().equals("sim")) return;

        criar_alarme(t);
    }
    public void criar_alarme(Tarefa t){
        Scanner scan = new Scanner(System.in);
        LocalDateTime alarme = null;

        String resposta;
        while (true){
            try{
                System.out.print("Data do alarme (dd/mm/aaaa hh:mm)> ");
                resposta = scan.nextLine();
                alarme = LocalDateTime.parse(resposta, formato);
                break;
            } catch (Exception _) {}
        }


        t.alarmes.add(resposta);
    }

    public void rodar_alarmes(Tarefa t){
        List<String> novos_alarmes = new ArrayList<>();
        LocalDateTime agora = LocalDateTime.now();


        for (String a : t.alarmes){
            LocalDateTime alarme = LocalDateTime.parse(a, formato);
            LocalDateTime hora_alarme = alarme.minusHours(2);

            if (agora.isAfter(hora_alarme)){
                StringBuilder sb = new StringBuilder();
                sb.repeat("!", Math.max(0, t.prioridade));

                int diff = Math.toIntExact(ChronoUnit.MINUTES.between(agora, alarme));

                if (diff > 0) {
                    System.out.println("/" + sb.toString()  + "\\ " + t.nome + " -> Faltam " + diff + " mins para o alarme acabar! (" + a + ")");
                }
                else{
                    System.out.println("/" + sb.toString()  + "\\ " + t.nome + " -> passou do limite do alarme! (autoremovido) (" + a + ")");
                }

            }

            if (alarme.isAfter(agora)){
                novos_alarmes.add(a);
            }
        }

        t.alarmes = novos_alarmes;
    }

    public Alarme(){
        formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }
}
