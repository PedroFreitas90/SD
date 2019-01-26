package projetosd;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Grupo 45
 */
public class Leilao {
    
    private List<Licitacao> leilao;
    
    public Leilao(){
        this.leilao = new ArrayList<>();
    }
    
    public synchronized List<Licitacao> getLeilao(){
        List<Licitacao> res = new ArrayList<>();
        for(Licitacao l: this.leilao)
            res.add(l.clone());
        return res;
    }
    
    public synchronized Licitacao getToplicitacao(String tipo){
        List<Licitacao> res = new ArrayList<>(this.leilao);
        Licitacao best = null;
        for(Licitacao l : res){
            if(l.getServidor().equals(tipo)){
                if(best == null)
                    best = l;
                else if(best.getValorLicitado()<l.getValorLicitado())
                    best=l;
            }
        }
        this.leilao.remove(best);
        return best;
    }
    
    public synchronized void setLeilao(List<Licitacao> leilao){
        this.leilao = new ArrayList<>();
        for(Licitacao l: leilao)
            this.leilao.add(l);
    }
    
    public synchronized void addLicitacao(Licitacao c){
        this.leilao.add(c);
    }
    
    public synchronized int size(){
        return this.leilao.size();
    }
    
}
