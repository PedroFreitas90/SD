package projetosd;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Grupo 45
 */
public class Servidor {
    private int id;
    private String tipo;
    private double preco;
    private boolean disponivel;
    private boolean reservado;
    private double leiloado;
    private String utilizador;
    private ReentrantLock lockservidor;
    private Condition c;
    private LocalDateTime datainicio;
    
    public Servidor(int id,String n, double p, boolean d){
        this.id=id;
        this.tipo = n;
        this.preco = p;
        this.disponivel = d;
        this.reservado = false;
        this.leiloado = 0;
        this.utilizador = null;
        this.lockservidor = new ReentrantLock();
        this.c = lockservidor.newCondition();
        this.datainicio = null;
    }
    
    
    public synchronized void serverawait(){
        try {
            c.await();
        } catch (InterruptedException ex) {
        }
    }
    
    
    public synchronized void serversignal(){
            c.signal();
    }
    
    public synchronized void reserva(String utilizador){
        this.disponivel=false;
        this.leiloado = 0;
        this.reservado=true;
        this.utilizador=utilizador;
        this.datainicio = LocalDateTime.now();
    }
    
    
    public synchronized void leiloa(String utilizador,Double valor){
        this.disponivel=false;
        this.leiloado = valor;
        this.reservado=false; 
        this.utilizador=utilizador;
        this.datainicio = LocalDateTime.now();
    }
    
    
    public synchronized double liberta(){
        long tempo = this.datainicio.until(LocalDateTime.now(), ChronoUnit.SECONDS);
        double preco = 0;
        this.disponivel=true;
        if(this.leiloado != 0){
            preco = (this.leiloado * tempo)/3600;
        }
        else if(this.reservado){
            preco = (this.preco * tempo)/3600;
        }
        this.leiloado = 0;
        this.reservado=false;
        this.utilizador=null;
        return preco;
    }
    
    public synchronized void setUtilizador(String u){
        this.utilizador = u;
    }
    
    public synchronized String getUtilizador(){
        return this.utilizador;
    }
    
    public synchronized void setDisponibilidade(boolean b){
        this.disponivel = b;
    }
    
    public synchronized boolean getDisponibilidade(){
        return disponivel;
    }
    
    public synchronized double getleiloado(){
        return this.leiloado;
    }
    
    public synchronized LocalDateTime getDataInicio(){
        return this.datainicio;
    }
    
    public synchronized Integer getID(){
        return this.id;
    }
    
    public synchronized String getTipo(){
        return this.tipo;
    }
    
    public synchronized double getPreco(){
        return this.preco;
    }
    
    public synchronized void lockServidor(){
        this.lockservidor.lock();
    }
    
    public synchronized void unlockServidor(){
        this.lockservidor.unlock();
    }
}
