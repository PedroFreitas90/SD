package projetosd;

import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;

/**
 *
 * @author Grupo 45
 */
public class ThreadClienteInput extends Thread{
	
    private BufferedReader ler_teclado;
    private PrintWriter escrever_socket;
    private Socket socket;
    private Menu menu;
    private ReentrantLock lock; 
    private Condition c;

    public ThreadClienteInput(Socket socket, Menu menu, ReentrantLock l, Condition c){
        try{
            this.ler_teclado = new BufferedReader(new InputStreamReader(System.in)); 
            this.escrever_socket = new PrintWriter(socket.getOutputStream(),true);
            this.socket = socket; 
            this.menu = menu;
            this.lock = l;
            this.c = c;
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void run(){
        String input = null;

        try{
            menu.showMenu();
            while((input = ler_teclado.readLine())!= null){
                if(menu.getOpcao() == 0){				
                    if(input.equals("1")){						
                        escrever_socket.println("1");
                        System.out.print("Username: ");
                        input = ler_teclado.readLine();
                        escrever_socket.println(input);

                        System.out.print("Password: ");
                        input = ler_teclado.readLine();
                        escrever_socket.println(input);

                        this.lock.lock();
                        c.await();
                        this.lock.unlock();
                        input="1";
                    }
                    else if(input.equals("2")){			// Efetuar registo		
                        escrever_socket.println("2");
                        System.out.print("Username: ");
                        input = ler_teclado.readLine();
                        escrever_socket.println(input);

                        System.out.print("Password: ");
                        input = ler_teclado.readLine();
                        escrever_socket.println(input);
                        input="2";
                    }
                    else if(input.equals("0")){					// Sair
                        break;
                    }
                    if(input.equals("1") || input.equals("2") || input.equals("0")){ 
                        menu.showMenu();
                    }
                    else System.out.println("Opção inválida.");
                }
                else if(menu.getOpcao() == 1){
                    if(input.equals("0")){						
                            break;
                    }
                    else if(input.equals("1")){						
                        escrever_socket.println("1.1");  //MOstra catalogo de servidores
                        this.lock.lock();
                        c.await();
                        this.lock.unlock();
                        input="1";
                    }
                    else if(input.equals("2")){						
                        escrever_socket.println("1.2");  //MOstra catalogo de servidores
                        this.lock.lock();
                        c.await();
                        this.lock.unlock();
                        input="2";
                    }
                    else if(input.equals("3")){
                        escrever_socket.println("1.3"); //MOstra servidores em uso
                        this.lock.lock();
                        c.await();
                        this.lock.unlock();
                        input="3";
                    }
                    else if(input.equals("4")){ 
                        escrever_socket.println("1.4");
                        System.out.print("Insira o ID do Servidor: ");
                        input = ler_teclado.readLine();
                        escrever_socket.println(input);
                        this.lock.lock();
                        c.await();
                        this.lock.unlock();
                        input="4";
                    }
                    else if(input.equals("5")){
                        escrever_socket.println("1.5");
                        this.lock.lock();
                        c.await();
                        this.lock.unlock();
                        input="5";
                    }
                    if(input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4") || input.equals("5") || input.equals("0")){ 
                        menu.showMenu();
                    }
                    else System.out.println("Opção inválida.");
                }
                else if(menu.getOpcao() == 2){						
                    if(input.equals("1")){						
                            escrever_socket.println("1.2.1");
                            this.lock.lock();
                            c.await();
                            this.lock.unlock();
                            input="1";
                    }
                    else if(input.equals("2")){						
                            escrever_socket.println("1.2.2");
                            this.lock.lock();
                            c.await();
                            this.lock.unlock();
                            input="2";
                    }
                    else if(input.equals("0")){						
                            escrever_socket.println("0");
                            this.lock.lock();
                            c.await();
                            this.lock.unlock();
                            input="2";
                    }
                    if(input.equals("1") || input.equals("2") || input.equals("0")){ 
                            menu.showMenu();
                    }
                    else System.out.println("Opção inválida.");
                }
                else if(menu.getOpcao() == 3){						
                    if(input.equals("1")){						
                        escrever_socket.println("1.1.1");
                        System.out.print("insira a licitação: ");
                        input = ler_teclado.readLine();
                        boolean done = false;
                        while(!done){
                        try{
                        double valor = Double.parseDouble(input);
                        done = true;
                        }catch(NumberFormatException e){
                            System.out.println("Valor tem que ser numérico!");
                            System.out.print("insira a licitação: ");
                            input = ler_teclado.readLine();
                        }
                        }
                        escrever_socket.println(input);
                        this.lock.lock();
                        c.await();
                        this.lock.unlock();
                        input="1";
                    }
                    else if(input.equals("2")){						
                        escrever_socket.println("1.1.2");
                        this.lock.lock();
                        c.await();
                        this.lock.unlock();
                        input="2";
                    }
                    else if(input.equals("0")){						
                        escrever_socket.println("0");
                        this.lock.lock();
                        c.await();
                        this.lock.unlock();
                        input="2";
                    }
                    if(input.equals("1") || input.equals("2") || input.equals("0")){ 
                        menu.showMenu();
                    }
                    else System.out.println("Opção inválida.");
                }
            }
            socket.shutdownOutput();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
