package Controller;

import Conexao.Dados;
import Model.Cidade;
import java.util.List;
import javax.persistence.Query;

public class DaoCidade {
    public void addCidade(Cidade cidade){
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().persist(cidade);
        Dados.getEntity().getTransaction().commit();
    }
    
    public void updateCidade(Cidade cidade){
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().merge(cidade);
        Dados.getEntity().getTransaction().commit();
    }
    
    public void deleteCidade(Cidade cidade){        
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().remove(getCidade(cidade.getId()));
        Dados.getEntity().getTransaction().commit();
    }
    
    public Cidade getCidade (int pk){
        return Dados.getEntity().find(Cidade.class, pk);
    }
    
    public List<Cidade> getCidadeList(){
        String HQL="select c from Cidade c order by c.nome";
        Query query = Dados.getEntity().createQuery(HQL);
        return query.getResultList();
    }
    
    public List<Cidade> getCidadeList(String filtro){
        String HQL="select c from Cidade c where c.nome like ?1 order by c.nome";
        Query query = Dados.getEntity().createQuery(HQL);
        query.setParameter(1, "%"+filtro.toUpperCase()+"%");
        return query.getResultList();
    }
}
