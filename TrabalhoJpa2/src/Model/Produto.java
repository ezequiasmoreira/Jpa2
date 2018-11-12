
package Model;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "PRODUTO")
public class Produto  implements Serializable{
    @Id
    @SwingColumn(description = "Código",colorOfBackgound = "")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    
    @SwingColumn(description = "Nome",colorOfBackgound = "")
    @Column(name = "NOME",length = 100, nullable = false)
    private String nome;   
    
    @SwingColumn(description = "Validade",colorOfBackgound = "")
    @Temporal(TemporalType.DATE)
    @Column(name = "VALIDADE", nullable = false)
    private Date dataValidade;
    
    @Column(name = "PRECO_CUSTO", nullable = false)
    private double precoCusto;
    
    @Column(name = "PRECO_VENDA", nullable = false)
    private double precoVenda;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FORNECEDOR", nullable = false, referencedColumnName = "ID")
    @SwingColumn(description = "Fornecedor",colorOfBackgound = "")
    private Fornecedor fornecedor;
    
    @Column(name = "STATUS", nullable = false)
    private int status;
    
    @Column(name = "SALDO", nullable = false)
    private Double saldo;
    
    public Produto(){
    }
    
    public Produto(int id, String nome,Date dataValidade,double precoCusto,double precoVenda,Fornecedor fornecedor,int status,double saldo){
        this.setId(id);
        this.setNome(nome);
        this.setDataValidade(dataValidade);
        this.setPrecoCusto(precoCusto);
        this.setPrecoVenda(precoVenda);
        this.setFornecedor(fornecedor);
        this.setStatus(status);
        this.setSaldo(saldo);
        
    }
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome.trim().isEmpty()?"DEFAULT":nome.toUpperCase();
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }
    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto < 0 ? 0:precoCusto;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda < 0 ? 0:precoVenda;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status < 0 ? 0 : status;
    } 

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.id;
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
        final Produto other = (Produto) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
