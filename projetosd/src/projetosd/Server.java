package projetosd;

import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;

/**
 *
 * @author Grupo 45
 */
public class Server{

	static public void main (String[] args){
            ServerSocket s;
            Socket c = null;
            int i=0;
            Gestao g = new Gestao();
            ReentrantLock lock = new ReentrantLock();

            try{
                s = new ServerSocket(8080);

                while((c=s.accept()) != null){ 
                    BufferedReader read_socket = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    PrintWriter write_socket = new PrintWriter(c.getOutputStream(),true);

                    Condition cond = lock.newCondition();
                    BufferServidor ms = new BufferServidor(cond,lock);

                    ThreadServidorRead tsr = new ThreadServidorRead(read_socket,g,ms);
                    ThreadServidorWrite tsw = new ThreadServidorWrite(write_socket,ms);
                    tsr.start();
                    tsw.start();
                }
                s.close();
            }
            catch(IOException e){
                    System.out.println(e.getMessage()); 
            }
	}
}