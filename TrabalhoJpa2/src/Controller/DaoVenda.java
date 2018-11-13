package Controller;

import Conexao.Dados;
import Model.Venda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Query;

public class DaoVenda {
    public void addVenda(Venda venda){
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().persist(venda);
        Dados.getEntity().getTransaction().commit();
    }
    public int getUltimaVenda() throws SQLException{
        int id = 0;
        for (Venda venda : getVendaList()) {
            id = Math.max(id, venda.getId());
        }
        return id;
    }
    public void updateVenda(Venda venda){
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().merge(venda);
        Dados.getEntity().getTransaction().commit();
    }    
    public void deleteVenda(Venda venda){
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().remove(getVenda(venda.getId()));
        Dados.getEntity().getTransaction().commit();
    }
    public Venda getVenda(int pk){
        return Dados.getEntity().find(Venda.class, pk);
    }
    public List<Venda> getVendaList(){
        String HQL="select v from Venda v order by v.id";
        Query query = Dados.getEntity().createQuery(HQL);
        return query.getResultList();
    }    
    public List<Venda> getVendaList(int pk){
        String HQL="select v from Venda v where v.id = ?1";
        Query query = Dados.getEntity().createQuery(HQL);
        query.setParameter(1,pk);
        return query.getResultList();    
    }
}
