/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.DaoFuncionario;
import Controller.DaoPedidoItens;
import Controller.DaoProduto;
import Controller.DaoVenda;
import Model.Funcionario;
import Model.PedidoItens;
import Model.Produto;
import Model.Venda;
import TDO.PesquisaPedido;
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

/**
 *
 * @author Ezequias e Karol
 */
public class DialogConsultaPedido extends javax.swing.JDialog {
    private DaoVenda venda = new DaoVenda();
    private DaoPedidoItens dao = new DaoPedidoItens();
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    
    private void carregaFuncionario(){       
        DefaultComboBoxModel cbm = new DefaultComboBoxModel(
        new Vector(new DaoFuncionario().getFuncionarioList()));
        comboFuncionario.setModel(cbm); 
    }
    private void carregarTabelaConsultaVenda(LinkedList<PedidoItens> listfiltos){
        //converte lista de pedidos em um TDO
        LinkedList<PesquisaPedido> listPesquisaPedidos = new LinkedList();
        for(PedidoItens pedidoItens: listfiltos ){
            PesquisaPedido pesquisaPedido = new PesquisaPedido(pedidoItens);
            listPesquisaPedidos.add(pesquisaPedido);
        }
        tableConsultaVenda.setModel(
            new MyTableModel(PesquisaPedido.class, listPesquisaPedidos, tableConsultaVenda)
        );
    }
    private List<PedidoItens> filtraPorStatus(List<PedidoItens> pedidos){
        int status = radioAtivo.isSelected()?0:1;
        LinkedList<String> lista = new LinkedList<>();
        LinkedList<PedidoItens> listStatus =  new LinkedList<>();        
        for (PedidoItens pedido : pedidos) {
            if (radioTodos.isSelected()){
                listStatus.add(pedido); 
            }else{
                if (pedido.getProduto().getStatus() == 0){
                    listStatus.add(pedido);
                }              
            }
        }
       return listStatus; 
    }
    private List<PedidoItens> filtraPorCliente(List<PedidoItens> listStatus){
        LinkedList<PedidoItens> listClientes =  new LinkedList<>();
        for (PedidoItens listStatu : listStatus) {
            if (textCliente.getText().isEmpty()){
                listClientes.add(listStatu); 
            }else{
                if (listStatu.getVenda().getCliente().getNome().contains(textCliente.getText().toUpperCase())){
                    listClientes.add(listStatu);
                }              
            }
        }
        return listClientes;
    }
    private List<PedidoItens> filtraPorFuncionarios(List<PedidoItens> listClientes){
        LinkedList<PedidoItens> listFuncionarios =  new LinkedList<>();
         for (PedidoItens listCliente : listClientes) {
            if (comboFuncionario.isEnabled()){
                if (listCliente.getVenda().getFuncionario().getNome().contains(((Funcionario)comboFuncionario.getSelectedItem()).getNome())){
                    listFuncionarios.add(listCliente);
                }                 
            }else{                
                listFuncionarios.add(listCliente);
            }
        }
        return listFuncionarios;
    }
    private List<PedidoItens> filtraPorPeriodo(List<PedidoItens> listFuncionarios){
        LinkedList<PedidoItens> listPeriodos =  new LinkedList<>();
        try {            
            Date dataInicial    = formato.parse(textDataInicial.getText());
            Date dataFinal      = formato.parse(textDataFinal.getText());
        
            for (PedidoItens listFuncionario : listFuncionarios) {
                Date dataVenda = listFuncionario.getVenda().getDataEfetuada();
                if ((dataVenda.after(dataInicial))&&(dataVenda.before(dataFinal))
                        ||(dataVenda.after(dataInicial)&&(dataVenda.equals(dataFinal))
                        ||(dataVenda.equals(dataInicial)&&(dataVenda.before(dataFinal))))
                         ||(dataVenda.equals(dataFinal)&&(dataVenda.equals(dataInicial)))){
                   listPeriodos.add(listFuncionario);
                }
            }
        } catch (ParseException ex) {
            System.out.println("erro " + ex.getMessage());
            
        }
        return listPeriodos;
    }
    private List<PedidoItens> filtraPorVenda(List<PedidoItens> listPeriodos){
        LinkedList<PedidoItens> listVendas =  new LinkedList<>();
        try{
            for (PedidoItens listPeriodo : listPeriodos) {
                if (textVenda.getText().isEmpty()){
                    listVendas.add(listPeriodo);
                }else{
                    if(listPeriodo.getVenda().getId() == Integer.parseInt(textVenda.getText())){
                        listVendas.add(listPeriodo);
                    }
                }
            }
        }catch(Exception ex){
            System.out.println("erro " + ex.getMessage());
        }
        
        return listVendas;
    }
    private void fimDosFiltrosdePedido(List<PedidoItens> ultimaListas){
        LinkedList<PedidoItens> listfiltos =  new LinkedList<>();
        for (PedidoItens ultimaLista : ultimaListas) {
            listfiltos.add(ultimaLista);
        }  
        this.carregarTabelaConsultaVenda(listfiltos);
    }
    private void filtraPedido(){
        
        //busca todos os pedidos 
        List<PedidoItens> pedidos           =  dao.getPedidoItensList();
        //filtra pedidos por status
        List<PedidoItens> listStatus        =  this.filtraPorStatus(pedidos);
        //filtra pedido por cliente
        List<PedidoItens> listClientes      =  this.filtraPorCliente(listStatus);
        //filtra pedido por funcionários
        List<PedidoItens> listFuncionarios  =  this.filtraPorFuncionarios(listClientes);
        //filtra pedido por periodo
        List<PedidoItens> listPeriodos      =  this.filtraPorPeriodo(listFuncionarios);
        //filtra pedido por venda
        List<PedidoItens> listVendas        =  this.filtraPorVenda(listPeriodos);
                
        //fim dos filtros de pedido
        this.fimDosFiltrosdePedido(listVendas);
    }
    public DialogConsultaPedido(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableConsultaVenda = new javax.swing.JTable();
        radioAtivo = new javax.swing.JRadioButton();
        radioInativo = new javax.swing.JRadioButton();
        textCliente = new javax.swing.JTextField();
        radioTodos = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textDataInicial = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        textDataFinal = new javax.swing.JFormattedTextField();
        comboFuncionario = new javax.swing.JComboBox<>();
        buttonPesquisar = new javax.swing.JButton();
        checkBoxFuncionario = new javax.swing.JCheckBox();
        textVenda = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tableConsultaVenda.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableConsultaVenda);

        buttonGroup1.add(radioAtivo);
        radioAtivo.setText("Ativo");

        buttonGroup1.add(radioInativo);
        radioInativo.setText("Inativo");

        buttonGroup1.add(radioTodos);
        radioTodos.setSelected(true);
        radioTodos.setText("Todos");

        jLabel1.setText("Produto");

        jLabel3.setText("Cliente");

        jLabel4.setText("Periodo");

        try {
            textDataInicial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel5.setText("até");

        try {
            textDataFinal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        comboFuncionario.setEnabled(false);

        buttonPesquisar.setText("Pesquisar");
        buttonPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPesquisarActionPerformed(evt);
            }
        });

        checkBoxFuncionario.setText("Pesquisar por funcionário");
        checkBoxFuncionario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkBoxFuncionarioMouseClicked(evt);
            }
        });

        jLabel2.setText("Venda");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radioInativo)
                            .addComponent(radioAtivo)
                            .addComponent(jLabel1)
                            .addComponent(radioTodos))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(comboFuncionario, 0, 209, Short.MAX_VALUE)
                                .addComponent(checkBoxFuncionario)
                                .addComponent(textCliente, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(textVenda)
                                    .addComponent(textDataInicial, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textDataFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonPesquisar)
                        .addGap(19, 19, 19))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioAtivo)
                    .addComponent(textCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioInativo)
                    .addComponent(jLabel4)
                    .addComponent(checkBoxFuncionario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(radioTodos)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(textDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonPesquisar)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPesquisarActionPerformed
        this.filtraPedido();
    }//GEN-LAST:event_buttonPesquisarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Date data = new Date(System.currentTimeMillis());
        textDataInicial.setText(formato.format(data));
        textDataFinal.setText(formato.format(data));
        this.carregaFuncionario();
    }//GEN-LAST:event_formWindowOpened

    private void checkBoxFuncionarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkBoxFuncionarioMouseClicked
        if (checkBoxFuncionario.isSelected()){
            comboFuncionario.setEnabled(true);
        }else{
            comboFuncionario.setEnabled(false);
        }    
    }//GEN-LAST:event_checkBoxFuncionarioMouseClicked

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
            java.util.logging.Logger.getLogger(DialogConsultaPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogConsultaPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogConsultaPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogConsultaPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogConsultaPedido dialog = new DialogConsultaPedido(new javax.swing.JFrame(), true);
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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton buttonPesquisar;
    private javax.swing.JCheckBox checkBoxFuncionario;
    private javax.swing.JComboBox<String> comboFuncionario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton radioAtivo;
    private javax.swing.JRadioButton radioInativo;
    private javax.swing.JRadioButton radioTodos;
    private javax.swing.JTable tableConsultaVenda;
    private javax.swing.JTextField textCliente;
    private javax.swing.JFormattedTextField textDataFinal;
    private javax.swing.JFormattedTextField textDataInicial;
    private javax.swing.JTextField textVenda;
    // End of variables declaration//GEN-END:variables
}
