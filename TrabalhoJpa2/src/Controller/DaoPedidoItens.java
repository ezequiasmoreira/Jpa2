package Controller;

import Conexao.Dados;
import Model.PedidoItens;
import java.util.List;
import javax.persistence.Query;

public class DaoPedidoItens {
    public void addPedidoItens(PedidoItens pedidoItens){
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().persist(pedidoItens);
        Dados.getEntity().getTransaction().commit();
    }
    public int getUltimoPedido() {
        int id = 0;
        for (PedidoItens pedidoItens : getPedidoItensList()) {
            id = Math.max(id, pedidoItens.getId());
        }
        return id;
    }
    public void updatePedidoItens(PedidoItens pedidoItens){
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().merge(pedidoItens);
        Dados.getEntity().getTransaction().commit();
    }    
    public void deletePedidoItens(PedidoItens pedidoItens){
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().remove(getPedidoItens(pedidoItens.getId()));
        Dados.getEntity().getTransaction().commit();
    }
    public void deleteColecaoPedidoItens(int fk){
        String HQL="delete from PedidoItens p where p.id_venda = ?1";
        Query query = Dados.getEntity().createQuery(HQL);
        query.setParameter(1, fk);
    }
    
    public PedidoItens getPedidoItens(int pk){
        return Dados.getEntity().find(PedidoItens.class, pk);
    }
    public List<PedidoItens> getPedidoItensList(){
        String HQL="select p from PedidoItens p order by p.id";
        Query query = Dados.getEntity().createQuery(HQL);
        return query.getResultList();
    }
    public List<PedidoItens> getPedidoVendaList(){
        String HQL="select p from PedidoItens p group by p.id_venda";
        Query query = Dados.getEntity().createQuery(HQL);
        return query.getResultList(); 
    }
    public List<PedidoItens> getPedidoItensList(int filtro){
        String HQL="select p from PedidoItens p where p.venda.id = ?1 ";
        Query query = Dados.getEntity().createQuery(HQL);
        query.setParameter(1,filtro);
        return query.getResultList();        
    }
    public List<PedidoItens> getPedidoList(int pk){
        String HQL="select p from PedidoItens p where p.id = ?1 ";
        Query query = Dados.getEntity().createQuery(HQL);
        query.setParameter(1, pk);
        return query.getResultList();   
    }
}
