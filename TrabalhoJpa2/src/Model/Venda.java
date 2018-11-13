
package Model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "VENDA")
public class Venda implements Serializable{
    @Id
    @SwingColumn(description = "Código",colorOfBackgound = "")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;  
    
    @SwingColumn(description = "Data",colorOfBackgound = "")
    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_EFETUADA", nullable = false)
    private Date dataEfetuada;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FUNCIONARIO", nullable = false, referencedColumnName = "ID")
    @SwingColumn(description = "Funcionário",colorOfBackgound = "")
    private Funcionario funcionario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENTE", nullable = false, referencedColumnName = "ID")
    @SwingColumn(description = "Cliente",colorOfBackgound = "")
    private Cliente cliente;
    
    
    public Venda(){
    }
    
    public Venda(int id,Date dataEfetuada, Funcionario funcionario, Cliente cliente){
        this.setId(id);
        this.setDataEfetuada(dataEfetuada);
        this.setFuncionario(funcionario);
        this.setCliente(cliente);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getDataEfetuada() {
        return dataEfetuada;
    }

    public void setDataEfetuada(Date dataEfetuada) {
        this.dataEfetuada = dataEfetuada;
    }
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Venda other = (Venda) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
