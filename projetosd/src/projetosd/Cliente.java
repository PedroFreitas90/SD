package projetosd;

import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;

/**
 *
 * @author Grupo 45
 */
public class Cliente{

	public static void main(String[] args){
            String input = null;
            Utilizador utilizador = null;
            Socket socket = null;
            ReentrantLock lock = new ReentrantLock();
            Condition cond = lock.newCondition();

            try{
                socket = new Socket("localhost",8080);

                BufferedReader ler_socket = new BufferedReader(new InputStreamReader(socket.getInputStream()));	

                Menu menu = new Menu();
                ThreadClienteInput tci = new ThreadClienteInput(socket,menu,lock, cond);
                ThreadClienteOutput tco = new ThreadClienteOutput(ler_socket,menu,lock, cond);

                tci.start();	
                tco.start();

                tci.join();
                tco.join();

                ler_socket.close();

                System.out.println("Até uma próxima!\n");
                socket.close();
            }
            catch(IOException e){
                System.out.println(e.getMessage());
            }   
            catch(InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
}
