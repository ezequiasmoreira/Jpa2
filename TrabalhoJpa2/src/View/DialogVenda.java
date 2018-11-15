/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.DaoCliente;
import Controller.DaoProduto;
import Controller.DaoFuncionario;
import Controller.DaoPedidoItens;
import Controller.DaoVenda;
import Model.Cliente;
import Model.Produto;
import Model.Funcionario;
import Model.PedidoItens;
import Model.Venda;
import TDO.PesquisaVenda;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import staticas.Staticas;

/**
 *
 * @author Ezequias e Karol
 */
public class DialogVenda extends javax.swing.JDialog {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    DaoVenda    dao = new DaoVenda();
    DaoPedidoItens pedidoItens = new DaoPedidoItens();
    DaoFuncionario funcionario = new DaoFuncionario();
    DaoProduto produto = new DaoProduto();
    DaoCliente cliente = new DaoCliente();
    
    public void validaFormulario(){
        if (textDataEfetuada.getText().equals("  /  /    ")){
            JOptionPane.showMessageDialog(null, "A data de nascimento ´é obrigátorio");
            textDataEfetuada.requestFocus();
            return;
        }
        if (textIdCliente.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "selecione uma um produto");
            buttonPesquisarCliente.requestFocus();
            return;
        }
    }
    
    private void carregaFuncionario(){       
        DefaultComboBoxModel cbm = new DefaultComboBoxModel(
        new Vector(new DaoFuncionario().getFuncionarioList()));
        comboFuncionario.setModel(cbm); 
    }
    private void carregaProduto(){
        List<Produto> lista = produto.getProdutoList();
        tableProduto.setModel(
            new MyTableModel(Produto.class, lista, tableProduto)
        );  
    }
    private void carregaCliente(){
        List<Cliente> lista = cliente.getClienteList();
        tableCliente.setModel(
            new MyTableModel(Cliente.class, lista, tableCliente)
        );  
    }
    private void carregaCliente(String filtro) throws SQLException{
        List<Cliente> lista = cliente.getClienteList(filtro);
        tableCliente.setModel(
            new MyTableModel(Cliente.class, lista, tableCliente)
        );  
    }
    private void carregaProduto(String filtro){        
        List<Produto> lista = produto.getProdutoList(filtro);
        tableProduto.setModel(
            new MyTableModel(Produto.class, lista, tableProduto)
        ); 
    }
    
    private void carregaPedidoItens(int IdVenda){
        List<PedidoItens> lista = pedidoItens.getPedidoItensList(IdVenda);
        //converte lista de pedidos em um TDO
        LinkedList<PesquisaVenda> listPesquisaVendas = new LinkedList();
        for(PedidoItens pedidoItens: lista ){
            PesquisaVenda pesquisaVenda = new PesquisaVenda(pedidoItens);
            listPesquisaVendas.add(pesquisaVenda);
        }
        tablePedidoItens.setModel(
            new MyTableModel(PesquisaVenda.class, listPesquisaVendas, tablePedidoItens)
        );        
    }
    
    private void iniciaComponentes(){
        textTotal.setText("");
        textIdVenda.setText("");
        comboFuncionario.setSelectedIndex(0);
        textIdCliente.setText("");
        textCliente.setText("");
        textIdProduto.setText("");
        textProduto.setText("");
        textQuantidade.setText("0");
        textDesconto.setText("0");
        comboFuncionario.setEnabled(true);
        textCliente.setEditable(true);
        tableCliente.setEnabled(true);
    }
    //cria um objeto a partir da tela
    private Venda populateObject() throws ParseException{
        int idCliente = Integer.parseInt(textIdCliente.getText());
        return (new Venda(
                    textIdVenda.getText().isEmpty()?0:Integer.parseInt(textIdVenda.getText()),
                    sdf.parse(textDataEfetuada.getText()),
                    ((Funcionario)comboFuncionario.getSelectedItem()),
                    cliente.getCliente(idCliente)
                ));
    }
    private PedidoItens populateObjectPedido(int idVenda) throws ParseException, SQLException{
        return (new PedidoItens(
                    0,
                    textQuantidade.getText().isEmpty() ? 1 : Double.parseDouble(textQuantidade.getText()),
                    textDesconto.getText().isEmpty() ? 0 : Double.parseDouble(textDesconto.getText()),
                    dao.getVenda(idVenda),
                    produto.getProduto(Integer.parseInt(textIdProduto.getText())) 
                ));
    }
    //preencher os componentes com o objeto
    private void populateComponente(Funcionario funcionario){
        String dataFormatada = sdf.format(funcionario.getDataNascimento());
        textIdVenda.setText(funcionario.getId()+"");
        textDataEfetuada.setText(dataFormatada);
        comboFuncionario.setSelectedItem(funcionario.getSexo());
        
    }   
    //preencher os componentes com o objeto
    private void populateComponenteProduto(Produto produto){
        textIdProduto.setText(produto.getId()+"");
        textProduto.setText(produto.getNome());
    }   
    private void populateComponenteCliente(Cliente cliente){
        textIdCliente.setText(cliente.getId()+"");
        textCliente.setText(cliente.getNome());
    }   
    private void populateComponentePedidoItens(PedidoItens pedido){
        textIdpediddoItens.setText(pedido.getId()+"");
        textPedidoRemover.setText(pedido.getProdutoDescricao());
    } 
    private void atualizaTotal(int idVenda) throws SQLException{
        List<PedidoItens> pedidos = pedidoItens.getPedidoItensList(idVenda);
        double totalizador = 0;
        for (PedidoItens pedido : pedidos) {
            double preco    = pedido.getProduto().getPrecoVenda();
            double qtd      = pedido.getQuantidade() == 0 ? 1 : pedido.getQuantidade();
            double desconto = pedido.getDesconto();
            totalizador = totalizador +(preco*qtd)-desconto;  
        }
        textTotal.setText(totalizador+"");
    } 
    private void atualizaSaldo(int idPedido,String sinal) throws SQLException{
        List<PedidoItens> pedidos   = pedidoItens.getPedidoList(idPedido);
        
        for (PedidoItens pedido : pedidos) {
            List<Produto> produtos      = produto.getProdutoUpdateList(pedido.getProduto().getId());
            double novoSaldo = 0;
            double saldoAtual    = pedido.getProduto().getSaldo();
            
            if (sinal.equals("+")){
                novoSaldo     = saldoAtual -  ((pedido.getQuantidade() == 0)? 1 :pedido.getQuantidade()) ; 
            }else{
                
                 novoSaldo     = saldoAtual + ((pedido.getQuantidade() == 0)? 1 :pedido.getQuantidade()) ;
            }
            
            for (Produto prod : produtos) {
                produto.updateProduto(new Produto(
                                        prod.getId(),
                                        prod.getNome(),
                                        prod.getDataValidade(),
                                        prod.getPrecoCusto(), 
                                        prod.getPrecoVenda(), 
                                        prod.getFornecedor(),
                                        prod.getStatus(),
                                        novoSaldo
                                    ));
            }
        }
       
        
    } 
    private void atualizaVendaDaPesquisa(){
        for(Venda venda : Staticas.getListaStaticaVenda()){
            textIdVenda.setText(venda.getId()+"");
            textDataEfetuada.setText(sdf.format(venda.getDataEfetuada()));
            textIdCliente.setText(venda.getCliente().getId()+"");
            textCliente.setText(venda.getCliente().getNome());
            comboFuncionario.setSelectedItem(venda.getFuncionario()); 
            comboFuncionario.setEnabled(false);
            textCliente.setEditable(false);
            tableCliente.setEnabled(false);
        } 
    }
    private void atualizaPedidoDaPesquisa() throws SQLException{       
        int idVenda = 0;
        for(Venda venda : Staticas.getListaStaticaVenda()){
            idVenda = venda.getId();
        }
        this.carregaPedidoItens(idVenda);
        this.atualizaTotal(idVenda);        
    }
    

    /**
     * Creates new form DialogFuncionario
     */
    public DialogVenda(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jRadioButton2 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textIdVenda = new javax.swing.JTextField();
        comboFuncionario = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        textDataEfetuada = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        textIdCliente = new javax.swing.JTextField();
        textCliente = new javax.swing.JTextField();
        buttonPesquisarCidade = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableProduto = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableCliente = new javax.swing.JTable();
        textIdProduto = new javax.swing.JTextField();
        textProduto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        buttonPesquisarCliente = new javax.swing.JButton();
        buttonSalvar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        textQuantidade = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        textDesconto = new javax.swing.JTextField();
        buttonNovo = new javax.swing.JButton();
        buttonCancelar = new javax.swing.JButton();
        buttonPesquisarVenda = new javax.swing.JButton();
        buttonOk = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePedidoItens = new javax.swing.JTable();
        buttonRemover = new javax.swing.JButton();
        textIdpediddoItens = new javax.swing.JTextField();
        textPedidoRemover = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        textTotal = new javax.swing.JTextField();

        jRadioButton2.setText("jRadioButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerar venda");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));

        jLabel1.setText("Código");

        jLabel3.setText("Funcionario");

        textIdVenda.setEditable(false);
        textIdVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textIdVendaActionPerformed(evt);
            }
        });

        jLabel5.setText("Data");

        textDataEfetuada.setEditable(false);
        try {
            textDataEfetuada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel8.setText("Cliente");

        textIdCliente.setEditable(false);

        buttonPesquisarCidade.setText("...");
        buttonPesquisarCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPesquisarCidadeActionPerformed(evt);
            }
        });

        tableProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProdutoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableProduto);

        tableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableClienteMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableCliente);

        textIdProduto.setEditable(false);

        jLabel2.setText("Produto");

        buttonPesquisarCliente.setText("...");
        buttonPesquisarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonPesquisarClienteMouseClicked(evt);
            }
        });

        buttonSalvar.setText("Salvar");
        buttonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSalvarActionPerformed(evt);
            }
        });

        jLabel4.setText("Quantidade");

        textQuantidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textQuantidadeActionPerformed(evt);
            }
        });

        jLabel6.setText("Desconto");

        buttonNovo.setText("Novo");
        buttonNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNovoActionPerformed(evt);
            }
        });

        buttonCancelar.setText("Cancelar");
        buttonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelarActionPerformed(evt);
            }
        });

        buttonPesquisarVenda.setText("jButton1");
        buttonPesquisarVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonPesquisarVendaMouseClicked(evt);
            }
        });
        buttonPesquisarVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPesquisarVendaActionPerformed(evt);
            }
        });

        buttonOk.setText("OK");
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textIdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel8)
                        .addGap(5, 5, 5)
                        .addComponent(textIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonPesquisarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(textIdVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buttonPesquisarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(textDataEfetuada))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(buttonOk)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(buttonNovo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(buttonSalvar)
                                        .addGap(14, 14, 14)
                                        .addComponent(buttonCancelar)))))
                        .addGap(0, 4, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(buttonPesquisarCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textIdVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(comboFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonPesquisarVenda)
                    .addComponent(buttonOk)
                    .addComponent(buttonNovo)
                    .addComponent(buttonCancelar)
                    .addComponent(buttonSalvar))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textDataEfetuada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(textQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(textDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textIdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(buttonPesquisarCidade)
                    .addComponent(textIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(textCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonPesquisarCliente))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        tablePedidoItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablePedidoItens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePedidoItensMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablePedidoItens);

        buttonRemover.setText("Remover");
        buttonRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRemoverActionPerformed(evt);
            }
        });

        textIdpediddoItens.setEditable(false);

        jLabel7.setText("Total");

        textTotal.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textIdpediddoItens, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textPedidoRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonRemover)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textIdpediddoItens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textPedidoRemover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonRemover))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(textTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.carregaFuncionario();
        this.carregaProduto();
        this.carregaCliente();
        Date data = new Date(System.currentTimeMillis());
        textDataEfetuada.setText(sdf.format(data));

    }//GEN-LAST:event_formWindowOpened
    
    private void tablePedidoItensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePedidoItensMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount()==2) {
            String valor = tablePedidoItens.getValueAt(tablePedidoItens.getSelectedRow(), 0)+"";
            int codigo = Integer.parseInt(valor);
            populateComponentePedidoItens(pedidoItens.getPedidoItens(codigo));
        }
    }//GEN-LAST:event_tablePedidoItensMouseClicked

    private void buttonRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRemoverActionPerformed
        
        if(textIdpediddoItens.getText().isEmpty()){
            return;
        }
        if(textIdVenda.getText().isEmpty()){
            return;
        }
        if(JOptionPane.showConfirmDialog(null, "Confirma a exclusão do item?" )!= 0){
            return;
        }
        try{
            int pk = Integer.parseInt(textIdpediddoItens.getText());
            this.atualizaSaldo(Integer.parseInt(textIdpediddoItens.getText()),"-");
            pedidoItens.deletePedidoItens(pedidoItens.getPedidoItens(pk));
            int idVenda = Integer.parseInt(textIdVenda.getText());             
            this.carregaPedidoItens(idVenda);
            this.atualizaTotal(idVenda);
            textIdpediddoItens.setText("");
            textPedidoRemover.setText("");
        }catch(SQLException ex){
            System.out.println("Erro: " + ex.getMessage());
        }
    }//GEN-LAST:event_buttonRemoverActionPerformed

    private void buttonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelarActionPerformed
        int idVenda = (textIdVenda.getText().isEmpty())? 0 : Integer.parseInt(textIdVenda.getText());
        if (idVenda != 0){
            dao.deleteVenda(dao.getVenda(idVenda));
            pedidoItens.deleteColecaoPedidoItens(idVenda);
            this.iniciaComponentes();
            this.carregaPedidoItens(0);
        }
        
    }//GEN-LAST:event_buttonCancelarActionPerformed

    private void buttonNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNovoActionPerformed
        this.iniciaComponentes();
        this.carregaPedidoItens(0);
    }//GEN-LAST:event_buttonNovoActionPerformed

    private void textIdVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textIdVendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textIdVendaActionPerformed

    private void buttonPesquisarCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPesquisarCidadeActionPerformed
        //pesquisa de cidade
        this.carregaProduto(textProduto.getText());
        
    }//GEN-LAST:event_buttonPesquisarCidadeActionPerformed

    private void tableProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProdutoMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount()==2) {
            String strProduto = tableProduto.getValueAt(tableProduto.getSelectedRow(), 0)+"";
            int idProduto = Integer.parseInt(strProduto);
            populateComponenteProduto(produto.getProduto(idProduto));
        }
    }//GEN-LAST:event_tableProdutoMouseClicked

    private void tableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableClienteMouseClicked
        
        if(tableCliente.isEnabled() == false){
            return;
        }
        if(evt.getClickCount()==2){
            String strCliente = tableCliente.getValueAt(tableCliente.getSelectedRow(), 0)+"";
            int idCliente = Integer.parseInt(strCliente);
            populateComponenteCliente(cliente.getCliente(idCliente));
        }
    }//GEN-LAST:event_tableClienteMouseClicked

    private void textQuantidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textQuantidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textQuantidadeActionPerformed

    private void buttonPesquisarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonPesquisarClienteMouseClicked
        try {
            this.carregaCliente(textCliente.getText());
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }//GEN-LAST:event_buttonPesquisarClienteMouseClicked

    private void buttonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSalvarActionPerformed
        try{
            validaFormulario();
            if(textIdCliente.getText().isEmpty()){
                return;
            }
            if(textIdProduto.getText().isEmpty()){
                return;
            }
            if(textIdVenda.getText().isEmpty()){
                dao.addVenda(populateObject()); 
                int idVenda     = dao.getUltimaVenda();
                pedidoItens.addPedidoItens(populateObjectPedido(idVenda));
                int idPedido    = pedidoItens.getUltimoPedido();
                if(idPedido > 0){
                    System.out.println(idPedido + "");
                    this.atualizaSaldo(idPedido,"+");
                    textIdVenda.setText(idVenda+"");
                    this.carregaPedidoItens(idVenda);
                    this.atualizaTotal(idVenda);
                    comboFuncionario.setEnabled(false);
                    textCliente.setEditable(false);
                    tableCliente.setEnabled(false);
                }
            }else{
                int idVenda = Integer.parseInt(textIdVenda.getText());
                pedidoItens.addPedidoItens(populateObjectPedido(idVenda));
                int idPedido    = pedidoItens.getUltimoPedido();
                this.atualizaSaldo(idPedido,"+");
                this.atualizaTotal(idVenda);
                this.carregaPedidoItens(idVenda);
            }
        }catch(SQLException | ParseException ex){
            System.out.println("Erro: " + ex.getMessage());
        }
    }//GEN-LAST:event_buttonSalvarActionPerformed

    private void buttonPesquisarVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonPesquisarVendaMouseClicked
        new DialogPesquisaVenda(null, true).setVisible(true);
    }//GEN-LAST:event_buttonPesquisarVendaMouseClicked

    private void buttonPesquisarVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPesquisarVendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonPesquisarVendaActionPerformed

    private void buttonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOkActionPerformed
         
        try {
            this.atualizaPedidoDaPesquisa();
            this.atualizaVendaDaPesquisa();
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }//GEN-LAST:event_buttonOkActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogVenda dialog = new DialogVenda(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancelar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton buttonNovo;
    private javax.swing.JButton buttonOk;
    private javax.swing.JButton buttonPesquisarCidade;
    private javax.swing.JButton buttonPesquisarCliente;
    private javax.swing.JButton buttonPesquisarVenda;
    private javax.swing.JButton buttonRemover;
    private javax.swing.JButton buttonSalvar;
    private javax.swing.JComboBox<String> comboFuncionario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tableCliente;
    private javax.swing.JTable tablePedidoItens;
    private javax.swing.JTable tableProduto;
    private javax.swing.JTextField textCliente;
    private javax.swing.JFormattedTextField textDataEfetuada;
    private javax.swing.JTextField textDesconto;
    private javax.swing.JTextField textIdCliente;
    private javax.swing.JTextField textIdProduto;
    private javax.swing.JTextField textIdVenda;
    private javax.swing.JTextField textIdpediddoItens;
    private javax.swing.JTextField textPedidoRemover;
    private javax.swing.JTextField textProduto;
    private javax.swing.JTextField textQuantidade;
    private javax.swing.JTextField textTotal;
    // End of variables declaration//GEN-END:variables
}
