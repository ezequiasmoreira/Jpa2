package Controller;

import Conexao.Dados;
import Model.Fornecedor;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.Query;

public class DaoFornecedor{
    public void addFornecedor(Fornecedor fornecedor){
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().persist(fornecedor);
        Dados.getEntity().getTransaction().commit();
    }
    public void updateFornecedor(Fornecedor fornecedor) {
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().merge(fornecedor);
        Dados.getEntity().getTransaction().commit();
    }    
    public void deleteFornecedor(Fornecedor fornecedor) {
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().remove(getFornecedor(fornecedor.getId()));
        Dados.getEntity().getTransaction().commit();
    }
    public Fornecedor getFornecedor(int pk){
        return Dados.getEntity().find(Fornecedor.class, pk);
    }
    public List<Fornecedor> getFornecedorList() {
        String HQL="select f from Fornecedor f order by f.nome";
        Query query = Dados.getEntity().createQuery(HQL);
        return query.getResultList();
    }
    public List<Fornecedor> getFornecedorList(String filtro) {
        String HQL="select f from Fornecedor f where f.nome like ?1 order by f.nome";
        Query query = Dados.getEntity().createQuery(HQL);
        query.setParameter(1, "%"+filtro.toUpperCase()+"%");
        return query.getResultList();   
    } 
}
