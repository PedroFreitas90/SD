package projetosd;

/**
 *
 * @author Grupo 45
 */
public class Licitacao {
    private String utilizador;
    private String tiposervidor;
    private double valor;
    
    public Licitacao(String u, String s, double v){
        this.utilizador = u;
        this.tiposervidor = s;
        this.valor = v;
    }
    
    public Licitacao(Licitacao l){
        this.utilizador = l.getUtilizador();
        this.tiposervidor = l.getServidor();
        this.valor = l.getValorLicitado();
    }
    
    public String getUtilizador(){
        return this.utilizador;
    }
    
    public String getServidor(){
        return this.tiposervidor;
    }
    
    public double getValorLicitado(){
        return this.valor;
    }
    
    public void setValorLicitado(double l){
        this.valor = l;
    }
    
    public void setUtilizador(String u){
        this.utilizador = u;
    }
    
    public void setTipoServidor(String s){
        this.tiposervidor = s;
    }
    
    public Licitacao clone(){
        return new Licitacao(this);
    }
}
