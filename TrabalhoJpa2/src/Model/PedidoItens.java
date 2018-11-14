
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

@Entity
@Table(name = "PEDIDO_ITENS")
public class PedidoItens implements Serializable{
    @Id
    @SwingColumn(description = "Código",colorOfBackgound = "")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;    
    
    @SwingColumn(description = "Quantidade",colorOfBackgound = "")
    @Column(name = "QUANTIDADE", nullable = false)
    private double quantidade;
    
    @SwingColumn(description = "Desconto",colorOfBackgound = "")
    @Column(name = "DESCONTO", nullable = false)
    private double desconto;    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VENDA", nullable = false, referencedColumnName = "ID")
    private Venda venda;
    
    @SwingColumn(description = "Produto",colorOfBackgound = "")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUTO", nullable = false, referencedColumnName = "ID")
    private Produto produto;
    
    public PedidoItens(){
    }

    public PedidoItens(int id, double quantidade, double desconto, Venda venda, Produto produto) {
        this.id = id;
        this.quantidade = quantidade;
        this.desconto = desconto;
        this.venda = venda;
        this.produto = produto;
    }
    
    public int getId() {
        return id;
    }
    public int getIdVenda() {
        return venda.getId();
    }
    public Date getData() {
        return venda.getDataEfetuada();
    }
    public String getCliente() {
        return venda.getCliente().getNome();
    }
    public String getFuncionario() {
        return venda.getFuncionario().getNome();
    }
    public double getPrecoProduto() {
        return produto.getPrecoVenda();
    }
    public String getProdutoDescricao() {
        return produto.getNome();
    }

    public void setId(int id) {
        this.id = id;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this.id;
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
        final PedidoItens other = (PedidoItens) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
   
    
}
