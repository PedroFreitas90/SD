package projetosd;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 *
 * @author Grupo 45
 */
public class Menu {
    private String menu;
    private int opcao;

    public void showMenu() {
        switch(opcao){
            case 0: System.out.println("************* MENU ****************\n"+
                                       "* 1 - Iniciar Sessao              *\n"+
                                       "* 2 - Registar                    *\n"+
                                       "* 0 - Sair                        *\n"+
                                       "***********************************\n");
                    break;
            case 1: System.out.println("*********** ÁREA CLIENTE **********\n"+
                                       "* 1 - Licitar um Servidor         *\n"+
                                       "* 2 - Efetuar pedido de Servidor  *\n"+
                                       "* 3 - Servidores em uso           *\n"+
                                       "* 4 - Libertar Servidores         *\n"+
                                       "* 5 - Consultar valor em dívida   *\n"+
                                       "* 0 - Terminar Sessao             *\n"+
                                       "***********************************\n");
                    break;
            case 2: System.out.println("******** Catálogo Servidores ******\n"+
                                       "* 1 - t3.micro                    *\n"+
                                       "* 2 - m5.large .                  *\n"+
                                       "* 0 - voltar                      *\n"+
                                       "***********************************\n");
                    break;     
            case 3: System.out.println("******** Catálogo Servidores ******\n"+
                                       "* 1 - t3.micro                    *\n"+
                                       "* 2 - m5.large .                  *\n"+
                                       "* 0 - voltar                      *\n"+
                                       "***********************************\n");
                    break;
                    
        }
    }

    public int getOpcao(){
        return this.opcao;
    }

    public void setOpcao(int n){
        this.opcao=n;
    }

}