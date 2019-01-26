package projetosd;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Grupo 45
 */
public class Utilizador {
    
    private String username;
    private String password;
    private double divida;
    private List<Integer> servidores;

    public Utilizador(){
            this.username = "n/a";
            this.password = "n/a";
            this.servidores= new ArrayList<Integer>();
    }

    public Utilizador(Utilizador c) {
        this.username = c.getUsername();
        this.password = c.getPassword();
        this.servidores= c .getServidores();
    }

    public Utilizador(String username, String password){
    	this.username = username;
    	this.password = password;
        this.servidores= new ArrayList<Integer>();
    }
    
    public synchronized void updateDivida(double p){
        this.divida += p;
    }
    
    public synchronized List<Integer> getServidores(){
        ArrayList<Integer> ar = new ArrayList<Integer>();
        for(Integer i :this.servidores){
            ar.add(i);
        }
        return ar;
        
    }
    public synchronized void addserver(Integer i){
        this.servidores.add(i);
    }
    
    public synchronized void removeserver(Integer i){
        this.servidores.remove(i);
    }

    public synchronized String getUsername(){
    	return this.username;
    }

    public synchronized String getPassword(){
    	return this.password;
    }
    
    public synchronized double getDivida(){
    	return this.divida;
    }
    
    public synchronized void setDivida(double d){
        this.divida = d;
    }

    public synchronized void setUsername(String username){
    	this.username = username;
    }

    public synchronized void setPassword(String password){
    	this.password = password;
    }

    public String toString(){
    	StringBuilder string;
    	string = new StringBuilder();
    	string.append("Nome: ");
    	string.append(this.password+'\n');
    	string.append("Username: ");
    	string.append(this.username+'\n');
        return string.toString();
    }

    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        Utilizador u = (Utilizador) obj;
        return u.getUsername().equals(this.username) && u.getPassword().equals(this.password);

    }

    public Utilizador clone(){
        return new Utilizador(this);
    }


}