package projetosd;

import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.SECONDS;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Grupo 45
 */
public class ThreadServidorRead extends Thread{
    private BufferedReader read_socket;
    private Gestao g;
    private Utilizador u;
    private BufferServidor ms;

    public ThreadServidorRead(BufferedReader read_socket, Gestao g, BufferServidor ms){
        this.read_socket = read_socket;
        this.g = g;
        this.u = null;
        this.ms = ms;
    }
            
    public void run(){
        try{
            String input;
            while((input = read_socket.readLine()) != null){ 
                if(input.equals("1")){						
                    String user,pass;
                    user = read_socket.readLine();
                    pass = read_socket.readLine();

                    try{
                        this.u = g.iniciarSessao(user,pass,ms);
                        ms.setMensagens("Sessão iniciada!",null);
                    }
                    catch(Exception e){
                        ms.setMensagens(e.getMessage(),null);
                    }
                }
                else if(input.equals("2")){					
                    String user,pass;
                    user = read_socket.readLine();
                    pass = read_socket.readLine();

                    try{
                        g.registarUtilizador(user,pass,ms);
                        ms.setMensagens("Registado",null);
                    }
                    catch(Exception e){
                        ms.setMensagens(e.getMessage(),null);
                    }
                }
                else if(input.equals("1.1")){
                    try{
                            ms.setMensagens("Catalogo Leilao",null);
                        }
                        catch(Exception e){
                            ms.setMensagens(e.getMessage(),null);
                        }
                }
                else if(input.equals("1.2")){
                    try{
                            ms.setMensagens("Catalogo Servidores",null);
                        }
                        catch(Exception e){
                            ms.setMensagens(e.getMessage(),null);
                        }
                }
                else if(input.equals("1.3")){
                    try{
                        List<Integer> l = this.u.getServidores();
                        ArrayList<String> str = new ArrayList<String>();
                        str.add("servidores em uso");
                        if(l.size()!=0){
                        str.add(Integer.toString(l.size()));
                        Servidor s;
                        for(Integer i : l){
                            s = this.g.getserver(i);
                            if(s.getleiloado()!=0)
                                str.add(s.getTipo()+" ID: "+i + " (Leiloado a "+s.getleiloado()+"€)");
                            else 
                                str.add(s.getTipo()+" ID: "+i + " (Reservado a "+s.getPreco()+"€)");
                        }
                        ms.setMensagens("Servidores",str);
                        }
                        else{
                            str.add("no servers");
                            ms.setMensagens("",str);
                        }
                    }
                    catch(Exception e){
                            ms.setMensagens(e.getMessage(),null);
                    }
                }
                else if(input.equals("1.4")){
                    try{ 
                        String s = read_socket.readLine();
                        int opcao = Integer.parseInt(s);
                        g.largarServidor(opcao,this.u.getUsername(), ms);
                        ms.setMensagens("libertar",null);
                        ms.setMensagens("Servidor libertado: "+this.g.getServidor(opcao).getTipo(),null);
                    }
                    catch(Exception e){
                        ms.setMensagens(e.getMessage(),null);
                    }
                }
                else if(input.equals("1.5")){
                    try{ 
                        List<Integer> l = this.u.getServidores();
                        double divida = this.u.getDivida();
                        for(int id: l){
                            Servidor s = g.getServidor(id);
                            divida += ((s.getDataInicio().until(LocalDateTime.now(), SECONDS))*s.getPreco())/3600;
                        }
                        this.u.setDivida(divida);
                        ms.setMensagens("minha divida",null);
                        ms.setMensagens("Divida: "+Double.toString(divida)+"€",null);
                    }
                    catch(Exception e){
                        ms.setMensagens(e.getMessage(),null);
                    }
                }
                else if(input.equals("1.2.1")){
                    try{
                        g.pedirServidor("t3.micro", u.getUsername(), ms);
                        ms.setMensagens("Pedido de servidor efetuado: t3.micro",null);
                        }
                    catch(Exception e){
                    ms.setMensagens(e.getMessage(),null);
                    }
                }
                else if(input.equals("1.2.2")){
                    try{        
                        g.pedirServidor("m5.large", u.getUsername(), ms);
                        ms.setMensagens("Pedido de servidor efetuado: m5.large",null);
                        }
                    catch(Exception e){
                            ms.setMensagens(e.getMessage(),null);
                    }
                }
                else if(input.equals("1.1.1")){
                    try{
                        double licitacao = Double.parseDouble(read_socket.readLine());
                        g.leiloarServidor("t3.micro", u.getUsername(), licitacao, ms);
                        ms.setMensagens("Licitação efetuada para o servidor t3.micro",null);
                    }
                    catch(Exception e){
                        ms.setMensagens(e.getMessage(),null);
                    }
                }
                else if(input.equals("1.1.2")){
                    try{        
                        g.pedirServidor("m5.large", u.getUsername(), ms);
                        ms.setMensagens("Licitação efetuada para o servidor m5.large",null);
                    }
                    catch(Exception e){
                        ms.setMensagens(e.getMessage(),null);
                    }
                }
                else if(input.equals("0")){					
                    try{
                        ms.setMensagens("0",null);
                    }
                    catch(Exception e){
                        ms.setMensagens(e.getMessage(),null);
                    }
                }
            }
            read_socket.close();
            ms.setMensagens("Sair",null);
        }
        catch(Exception e){
                System.out.println(e.getMessage());
        }
    }
}