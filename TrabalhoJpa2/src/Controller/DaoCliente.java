package Controller;

import Conexao.Dados;
import Model.Cliente;
import Model.Fornecedor;
import Tipos.TipoSexo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Query;

public class DaoCliente {
    public void addCliente(Cliente cliente) {
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().persist(cliente);
        Dados.getEntity().getTransaction().commit();
    }
    public void updateCliente(Cliente cliente){
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().merge(cliente);
        Dados.getEntity().getTransaction().commit();
    }    
    public void deleteCliente(Cliente cliente){
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().remove(getCliente(cliente.getId()));
        Dados.getEntity().getTransaction().commit();
    }
    public Cliente getCliente(int pk){
         return Dados.getEntity().find(Cliente.class, pk);
    }
    public List<Cliente> getClienteList(){
         String HQL="select c from Cliente c order by c.nome";
        Query query = Dados.getEntity().createQuery(HQL);
        return query.getResultList();
    }
    public List<Cliente> getClienteList(String filtro){
        String HQL="select c from Cliente c where c.nome like ?1 order by c.nome";
        Query query = Dados.getEntity().createQuery(HQL);
        query.setParameter(1, "%"+filtro.toUpperCase()+"%");
        return query.getResultList();         
    }
    
    
    
    
}
