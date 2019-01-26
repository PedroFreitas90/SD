package projetosd;

import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;

/**
 *
 * @author Grupo 45
 */
public class ThreadServidorWrite extends Thread{
	private PrintWriter write_socket;
	private Condition c;
	private BufferServidor ms;
	private ReentrantLock lock;

	public ThreadServidorWrite(PrintWriter write_socket, BufferServidor ms){
		this.write_socket = write_socket;
		this.c = ms.getCondition();
		this.ms = ms;
		this.lock = ms.getLock();
	}
	
	public void run(){
		this.lock.lock();
		try{
			String linha;
			while(true){
				while((linha = ms.getMensagens())==null)
					c.await();
				if(linha.equals("Sair"))
					break;
				this.write_socket.println(linha);
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			this.lock.unlock();
		}
	}
}