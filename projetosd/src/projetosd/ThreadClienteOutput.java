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
public class ThreadClienteOutput extends Thread{
	private BufferedReader ler_socket;
	private Menu menu;
	private ReentrantLock lock; 
	private Condition cond;

	public ThreadClienteOutput(BufferedReader ler_socket, Menu menu, ReentrantLock l, Condition c){
		this.ler_socket = ler_socket;
		this.menu = menu;
		this.lock=l;
		this.cond=c;
	}
	
	public void run(){
		try{
			String linha;	
                        
			while((linha = ler_socket.readLine())!=null){
				if(linha.equals("Sessão iniciada!")){
					menu.setOpcao(1);
                                        System.out.println("Sessão iniciada!");
					this.lock.lock();
					cond.signal();
					this.lock.unlock();
				}
                                
                                else if(linha.equals("Catalogo Servidores")){
					menu.setOpcao(2);
					this.lock.lock();
					cond.signal();
					this.lock.unlock();
				}
                                
                                else if(linha.equals("Catalogo Leilao")){
					menu.setOpcao(3);
					this.lock.lock();
					cond.signal();
					this.lock.unlock();
				}
                                
                                else if(linha.equals("servidores em uso")){
                                    linha = ler_socket.readLine();
                                    if (linha.equals("no servers")){
                                        System.out.println("Não está a usufruir de nenhum servidor neste momento");
                                    }
                                    else{
                                        int n =Integer.parseInt(linha);
                                        int i = 1;
                                        System.out.println("Servidores em uso:");
                                        while (i<=n){
                                            linha = ler_socket.readLine();
                                            System.out.println(i+" - "+linha);
                                            ++i;
                                        }
                                        
                                    }
                                    menu.setOpcao(1);
                                    this.lock.lock();
                                    cond.signal();
                                    this.lock.unlock();
				}
                                
                                else if(linha.equals("libertar")){
                                    linha = ler_socket.readLine();
                                    System.out.println(linha);
                                    menu.setOpcao(1);
                                    this.lock.lock();
                                    cond.signal();
                                    this.lock.unlock();
				}
                                
                                else if(linha.equals("minha divida")){
                                    linha = ler_socket.readLine();
                                    System.out.println(linha);
                                    menu.setOpcao(1);
                                    this.lock.lock();
                                    cond.signal();
                                    this.lock.unlock();
				}
                                
                                else if(linha.equals("Não existem servidores disponíveis. Foi colocado em espera!")){
                                    menu.setOpcao(1);
                                    System.out.println("\n"+linha+"\n");
                                    this.lock.lock();
                                    cond.signal();
                                    this.lock.unlock();
				}
                                
                                else if(linha.equals("Pedido de servidor efetuado: t3.micro")|| linha.equals("Pedido de servidor efetuado: m5.large")){
                                        menu.setOpcao(1);
                                        System.out.println("\n"+linha+"\n");
                                        this.lock.lock();
					cond.signal();
					this.lock.unlock();
                                }
                                
                                else if(linha.equals("Licitação efetuada para o servidor t3.micro")|| linha.equals("Licitação efetuada para o servidor m5.large")){
                                        menu.setOpcao(1);
                                        System.out.println("\n"+linha+"\n");
                                        this.lock.lock();
					cond.signal();
					this.lock.unlock();
                                }
                                
                                else if(linha.equals("0")){
                                        menu.setOpcao(1);
                                        this.lock.lock();
					cond.signal();
					this.lock.unlock();
                                }
                                
				else if(linha.equals("Registado") || linha.equals("Terminou sessão") || linha.equals("Username não existe!!!")
                                        || linha.equals("A password está incorreta!") || linha.equals("Username já se encontra em uso!")){
					menu.setOpcao(0);
                                        System.out.println("\n"+linha+"\n");
					this.lock.lock();
					cond.signal();
					this.lock.unlock();
				}
                                else if(linha.equals("O seu pedido de servidor por licitação foi cancelado para dar prioridade a uma reserva")){
                                        System.out.println("\n"+linha+"\n");
                                }
                                else if(linha.equals("Não existem servidores disponíveis. A sua licitação foi posta em leilão!")){
                                        menu.setOpcao(1);
                                        System.out.println("\n"+linha+"\n");
                                        this.lock.lock();
					cond.signal();
					this.lock.unlock();
                                }
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}