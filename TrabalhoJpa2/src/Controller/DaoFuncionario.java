package Controller;

import Conexao.Dados;
import Model.Funcionario;
import Tipos.TipoSexo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Query;

public class DaoFuncionario {
    public void addFuncionario(Funcionario funcionario){
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().persist(funcionario);
        Dados.getEntity().getTransaction().commit();
    }
    public void updateFuncionario(Funcionario funcionario){
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().merge(funcionario);
        Dados.getEntity().getTransaction().commit();
    }    
    public void deleteFuncionario(Funcionario funcionario){
        Dados.getEntity().getTransaction().begin();
        Dados.getEntity().remove(getFuncionario(funcionario.getId()));
        Dados.getEntity().getTransaction().commit();
    }
    public Funcionario getFuncionario(int pk) {
        return Dados.getEntity().find(Funcionario.class, pk);
    }
    public List<Funcionario> getFuncionarioList(){
        String HQL="select f from Funcionario f order by f.nome";
        Query query = Dados.getEntity().createQuery(HQL);
        return query.getResultList();
    }
    public List<Funcionario> getFuncionarioList(String filtro){
        String HQL="select f from Fornecedor f where f.nome like ?1 order by f.nome";
        Query query = Dados.getEntity().createQuery(HQL);
        query.setParameter(1, "%"+filtro.toUpperCase()+"%");
        return query.getResultList();      
    }
}
