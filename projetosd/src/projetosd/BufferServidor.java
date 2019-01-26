package projetosd;

import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Grupo 45
 */
public class BufferServidor {
	private Condition c;
	private ReentrantLock lock;
        private List<String> mensagens;
	private int i;

	public BufferServidor(Condition c, ReentrantLock lock){
            this.mensagens = new ArrayList<>();
            this.c = c;
            this.lock = lock;
            this.i = 0;
	}

	public void setMensagens(String msg, ArrayList<String> lista){
            this.lock.lock();
            try{
                if(lista == null)
                    this.mensagens.add(msg);
                else {
                    for(String m : lista)
                        this.mensagens.add(m);
                }
                c.signal();
            }
            finally{
                this.lock.unlock();
            }
	}

	public String getMensagens(){
            this.lock.lock();
            try{
                if(i!=mensagens.size()){
                    return this.mensagens.get((i++));
                }
                else return null;
            }
            finally{
                this.lock.unlock();
            }
	}

	public Condition getCondition(){
            return this.c;
	}

	public ReentrantLock getLock(){
            return this.lock;
	}
}