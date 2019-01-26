package projetosd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Grupo 45
 */
public class Gestao {
    
    private Map<Integer,Servidor> servidor;
    private Map<String,Utilizador> utilizadores;
    private Map<String,BufferServidor> mensagens;
    private Leilao leilao;
    private ReentrantLock lockutilizadores;
    private ReentrantLock lockservidores = new ReentrantLock();
    private ReentrantLock mensagensLock = new ReentrantLock();
    private Condition c = lockservidores.newCondition();
    private ArrayBlockingQueue queue;

    public Gestao(){
        this.servidor = new HashMap<>();
        this.utilizadores = new HashMap<>();
        this.mensagens = new HashMap<>();
        this.queue = new ArrayBlockingQueue(10);
        this.leilao = new Leilao();
        int i=0;
        for(i=0;i<3;i++){
            this.servidor.put(i+1, new Servidor(i+1,"t3.micro",0.99,true));
        }
        for(;i<6;i++){
        this.servidor.put(i+1, new Servidor(i+1,"m5.large",0.99,true));
        }
    }
    
    
    public Servidor getServidor(int id){
        return this.servidor.get(id);
    }
    
    /**
       * Método que valida as credenciais no início de sessão e retorna o respetivo Utilizador.
       * @param username Nome do utilizador
       * @param password  Password do utilizador
       * 
       * @return Utilizador
    */
    public Utilizador iniciarSessao(String username, String password, BufferServidor ms) throws LoginInvalidoException{
        Utilizador u;
        
        synchronized(this.utilizadores){
            if(!this.utilizadores.containsKey(username)){
                throw new LoginInvalidoException("Username não existe!!!");
            }
            else if(!this.utilizadores.get(username).getPassword().equals(password)){
                    throw new LoginInvalidoException("A password está incorreta!!!");
            }
            u = this.utilizadores.get(username);
        }
        
        synchronized(this.mensagens){
            if(this.mensagens.containsKey(username)){
                BufferServidor m = this.mensagens.get(username);
                String linha;
                while((linha = m.getMensagens())!=null){
                    ms.setMensagens(linha,null);
                }
                this.mensagens.put(username,ms);
            }
        }
        return u;
    }
    
    public void registarUtilizador(String user, String pass, BufferServidor ms) throws RegistoInvalidoException{
        synchronized(this.utilizadores){
            if(this.utilizadores.containsKey(user)){
                throw new RegistoInvalidoException("Username já se encontra em uso!");
            }
            else {
                Utilizador u = new Utilizador(user,pass);
                this.utilizadores.put(user,u);
                synchronized(this.mensagens){
                    this.mensagens.put(user,ms);
                }
            }
        }
    }
    
    public boolean isThereServidores(String tipo){
        try{
            this.lockservidores.lock();
            return servidor.values().stream().anyMatch(a -> a.getTipo().equals(tipo) && a.getDisponibilidade());
        } finally {
            this.lockservidores.unlock();
        }
    }
    
    public synchronized boolean isThereleiloados(String tipo){
        return servidor.values().stream().anyMatch(a -> a.getTipo().equals(tipo) && a.getleiloado()!=0);
    }
    
    public void leiloarServidor(String nome, String user, double licitacao, BufferServidor ms) throws LoginInvalidoException, ServidorIndisponivelException, InterruptedException{
        boolean encontrou = false;
        
        if(nome.equals("t3.micro")){
            if(!isThereServidores("t3.micro")){
                ms.setMensagens("Não existem servidores disponíveis. A sua licitação foi posta em leilão!", null);
                Licitacao l = new Licitacao(nome,user,licitacao);
                leilao.addLicitacao(l);
            }
            else if(isThereServidores("t3.micro")){
                for(Servidor s: servidor.values()){
                    if(s.getTipo().equals("t3.micro") && s.getDisponibilidade()){
                        s.leiloa(user, licitacao);
                        encontrou = true;
                        this.utilizadores.get(user).addserver(s.getID());
                        break;   
                    }
                }
            }
            if(!encontrou){
                throw new ServidorIndisponivelException();
            }
        }
        
        if(nome.equals("m5.large")){
            if(!isThereServidores("m5.large")){
                ms.setMensagens("Não existem servidores disponíveis. A sua licitação foi posta em leilão!", null);
                Licitacao l = new Licitacao(nome,user,licitacao);
                leilao.addLicitacao(l);
            }
            if(isThereServidores("m5.large")){
                for(Servidor s: servidor.values()){
                    if(s.getTipo().equals("m5.large") && s.getDisponibilidade()){
                        s.leiloa(user,licitacao);
                        encontrou = true;
                        this.utilizadores.get(user).addserver(s.getID());
                        break;   
                    }
                }
            }
            if(!encontrou){
                throw new ServidorIndisponivelException();
            }
        }
    }
    
    
    public Servidor getlicitacaomenor(){
        Servidor menor = null;
        for(Servidor s: servidor.values()){
            if(s.getleiloado()!=0){
                if(menor==null)
                    menor = s;
            else if(s.getleiloado()<menor.getPreco()){
                menor=s;
            }
            }
        
        }
        return menor;
    }
        
        
    public void pedirServidor(String nome, String user, BufferServidor ms) throws LoginInvalidoException, ServidorIndisponivelException, InterruptedException{
        boolean encontrou = false;
        if(nome.equals("t3.micro")){
            while(!isThereServidores("t3.micro")&&!isThereleiloados("t3.micro")){
                ms.setMensagens("Não existem servidores disponíveis. Foi colocado em espera!", null);
                lockservidores.lock();
                c = lockservidores.newCondition();
                queue.put(c);
                c.await();
                lockservidores.unlock();
            }
            if(isThereServidores("t3.micro")){
                for(Servidor s: servidor.values()){
                    System.out.println("A alocar servidor t3.micro...");
                    if(s.getTipo().equals("t3.micro") && s.getDisponibilidade()){
                        s.reserva(user);
                        encontrou = true;
                        this.utilizadores.get(user).addserver(s.getID());
                        break;   
                    }
                }
            }
            else if(isThereleiloados("t3.micro")){
                Servidor server = null;
                if((server = getlicitacaomenor())!=null){
                    String usr = server.getUtilizador();
                    BufferServidor mens = this.mensagens.get(usr);
                    mens.setMensagens("O seu pedido de servidor por licitação foi cancelado para dar prioridade a uma reserva", null);
                    double mon = server.liberta();
                    this.utilizadores.get(usr).updateDivida(mon);
                    this.utilizadores.get(usr).removeserver(server.getID());
                    server.reserva(user);
                    this.utilizadores.get(user).addserver(server.getID());
                    encontrou=true;
                }
            }
            if(!encontrou){
                throw new ServidorIndisponivelException();
            }
        }
        else if(nome.equals("m5.large")){
            while(!isThereServidores("m5.large")){
                ms.setMensagens("Não existem servidores disponíveis. Foi colocado em espera!", null);
                lockservidores.lock();
                c = lockservidores.newCondition();
                queue.put(c);
                c.await();
                lockservidores.unlock();
            }
            if(isThereServidores("m5.large")){
                for(Servidor s: servidor.values()){
                    System.out.println("A alocar servidor m5.large...");
                    if(s.getTipo().equals("m5.large") && s.getDisponibilidade()){
                        s.reserva(user);
                        encontrou = true;
                        this.utilizadores.get(user).addserver(s.getID());
                        break;   
                    }
                }
            }
            else if(isThereleiloados("m5.large")){
                Servidor server = null;
                if((server = getlicitacaomenor())!=null){
                    String usr = server.getUtilizador();
                    BufferServidor mens = this.mensagens.get(usr);
                    mens.setMensagens("O seu pedido de servidor por licitação foi cancelado para dar prioridade a uma reserva", null);
                    double mon = server.liberta();
                    this.utilizadores.get(usr).updateDivida(mon);
                    this.utilizadores.get(usr).removeserver(server.getID());
                    server.reserva(user);
                    this.utilizadores.get(user).addserver(server.getID());
                    encontrou=true;
                }
            }
            if(!encontrou){
                throw new ServidorIndisponivelException();
            }
        }
    }
    
    public Servidor getserver(Integer i){
       return this.servidor.get(i);
    }
    
    public void largarServidor(int id, String user, BufferServidor ms){
           
            Utilizador u = this.utilizadores.get(user);
            
            Servidor s = servidor.get(id);
            double preco = s.liberta();
            u.updateDivida(preco);
            u.removeserver(id);
            lockservidores.lock();
            try{
                if(queue.peek()!=null){
                    Condition c = (Condition) queue.take();
                    c.signal();
                }
                
                else if(leilao.size()!=0){
                  Licitacao best = leilao.getToplicitacao(s.getTipo());
                  String us = best.getUtilizador();
                  String serv = best.getServidor();
                  double precoli = best.getValorLicitado();
                  servidor.get(serv).leiloa(user, precoli);
                }
                else{
                System.out.println("Queue de clientes vazia!");
                }
                       
            } catch (InterruptedException ex) {
            } finally{
                    lockservidores.unlock();
                }
    }
}

