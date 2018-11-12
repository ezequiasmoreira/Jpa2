package Controller;

import Conexao.Dados;
import Model.Fornecedor;
import Model.Produto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Query;

public class DaoProduto {
    public void addProduto(Produto produto) {
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().persist(produto);
        Dados.getEntity().getTransaction().commit();
    }
    public void updateProduto(Produto produto){
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().merge(produto);
        Dados.getEntity().getTransaction().commit();
    }    
    public void deleteProduto(Produto produto){
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().remove(getProduto(produto.getId()));
        Dados.getEntity().getTransaction().commit();
    }
    public Produto getProduto(int pk){
        return Dados.getEntity().find(Produto.class, pk);
    }
    public List<Produto> getProdutoList(){
       String HQL="select p from Produto p order by p.nome";
        Query query = Dados.getEntity().createQuery(HQL);
        return query.getResultList();
    }
    public List<Produto> getProdutoUpdateList(int pk){        
        String HQL="select p from Produto p where p.id like ?1 order by p.nome";
        Query query = Dados.getEntity().createQuery(HQL);
        query.setParameter(1,pk);
        return query.getResultList(); 
    }
    public List<Produto> getProdutoList(String filtro) throws SQLException{
        String HQL="select p from Produto p where p.nome like ?1 order by p.nome";
        Query query = Dados.getEntity().createQuery(HQL);
        query.setParameter(1, "%"+filtro.toUpperCase()+"%");
        return query.getResultList();     
    }
    
    
    
    
}
