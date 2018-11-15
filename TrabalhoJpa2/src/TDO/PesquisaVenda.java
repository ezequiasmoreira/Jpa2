
package TDO;

import Model.PedidoItens;
import Model.SwingColumn;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PesquisaVenda {
    @SwingColumn(description = "Cód",colorOfBackgound = "")
    private int codigoDoPedido;
    @SwingColumn(description = "Cód.Venda",colorOfBackgound = "")
    private int codigoDaVenda;
    @SwingColumn(description = "Cód.Prod.",colorOfBackgound = "")
    private int codigoDoProduto;
    @SwingColumn(description = "Produto",colorOfBackgound = "")
    private String decricaoDoProduto;
    @SwingColumn(description = "Saldo",colorOfBackgound = "")
    private Double saldoDoProduto;
   
    @SwingColumn(description = "Prod.Status",colorOfBackgound = "")
    private String statusDoProduto;
    @SwingColumn(description = "Cliente",colorOfBackgound = "")
    private String nomeDoCliente;
    @SwingColumn(description = "Funcionário",colorOfBackgound = "")
    private String nomeDoFuncionario;
    @SwingColumn(description = "Data",colorOfBackgound = "")
    private Date dataDaVenda;
    private final SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    
    public PesquisaVenda(PedidoItens pedidoItens) {
        this.codigoDoPedido = pedidoItens.getId();
        this.codigoDaVenda = pedidoItens.getVenda().getId();
        this.codigoDoProduto = pedidoItens.getProduto().getId();
        this.decricaoDoProduto = pedidoItens.getProduto().getNome();
        this.saldoDoProduto =   pedidoItens.getProduto().getSaldo();
        this.statusDoProduto = pedidoItens.getProduto().getStatus() == 0 ?"Ativo":"Inativo";
        this.nomeDoCliente = pedidoItens.getVenda().getCliente().getNome();
        this.nomeDoFuncionario = pedidoItens.getVenda().getFuncionario().getNome();
        this.dataDaVenda = pedidoItens.getVenda().getDataEfetuada();
    }
    
    public int getCodigoDoPedido() {
        return codigoDoPedido;
    }

    public Double getSaldoDoProduto() {
        return saldoDoProduto;
    }
    public int getCodigoDaVenda() {
        return codigoDaVenda;
    }

    public int getCodigoDoProduto() {
        return codigoDoProduto;
    }

    public String getDecricaoDoProduto() {
        return decricaoDoProduto;
    }

    public String getStatusDoProduto() {
        return statusDoProduto;
    }

    public String getNomeDoCliente() {
        return nomeDoCliente;
    }

    public String getNomeDoFuncionario() {
        return nomeDoFuncionario;
    }

    public String getDataDaVenda() {        
        return formato.format(dataDaVenda);
    }
    
    
    
}
