/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.DaoFornecedor;
import Controller.DaoProduto;
import Model.Fornecedor;
import Model.Produto;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Ezequias e Karol
 */
public class DialogProduto extends javax.swing.JDialog {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    DaoProduto dao = new DaoProduto();
    
    public void validaFormulario(){
        if (textNome.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Nome obrigátorio");
            textNome.requestFocus();
            return;
        }
        if (textDataValidade.getText().equals("  /  /    ")){
            JOptionPane.showMessageDialog(null, "A data de nascimento ´é obrigátorio");
            textDataValidade.requestFocus();
            return;
        }
        if (textPrecoCusto.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Preço de custo é obrigátorio");
            textPrecoCusto.requestFocus();
            return;
        }
        if (textPrecoVenda.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Preço de venda é obrigátorio");
            textPrecoVenda.requestFocus();
            return;
        }
        if (textSaldo.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Saldo obrigátorio");
            textSaldo.requestFocus();
            return;
        }
    }
    
    private void carregaFornecedor() {
       
       DefaultComboBoxModel cbm = new DefaultComboBoxModel(
        new Vector(new DaoFornecedor().getFornecedorList()));
        comboFornecedor.setModel(cbm);       
    }
    
    private void carregaProdutos(){
        List<Produto> lista = dao.getProdutoList();
        tableProduto.setModel(
            new MyTableModel(Produto.class, lista, tableProduto)
        );
    }
    private void carregaProdutos(String filtro) throws SQLException{
        List<Produto> lista = dao.getProdutoList(filtro);
        tableProduto.setModel(
            new MyTableModel(Produto.class, lista, tableProduto)
        );
    }
    private void iniciaComponentes(){
        textId.setText("");
        textNome.setText("");
        textDataValidade.setText("");
        textPrecoCusto.setText("");
        textPrecoVenda.setText("");
        comboFornecedor.setSelectedIndex(0);
        textFiltro.setText("");
        textSaldo.setText("");
        radioAtivo.setSelected(true);
        textNome.requestFocus();
    }
    //cria um objeto a partir da tela
    private Produto populateObject() throws ParseException, SQLException{
        Date data = sdf.parse(textDataValidade.getText());
        return (new Produto(
                    textId.getText().isEmpty()?0:Integer.parseInt(textId.getText()),
                    textNome.getText(),
                    new java.sql.Date(data.getTime()),
                    Double.parseDouble(textPrecoCusto.getText()),
                    Double.parseDouble(textPrecoVenda.getText()),
                    (Fornecedor)comboFornecedor.getSelectedItem(),
                    (radioAtivo.isSelected()?0:1),
                    Double.parseDouble(textSaldo.getText())
        ));
    }
    //preencher os componentes com o objeto
    private void populateComponente(Produto produto){
        String dataFormatada = sdf.format(produto.getDataValidade());
        textId.setText(produto.getId()+"");
        textNome.setText(produto.getNome());
        textDataValidade.setText(dataFormatada);
        textPrecoCusto.setText(produto.getPrecoCusto()+"");
        textPrecoVenda.setText(produto.getPrecoVenda()+"");
        comboFornecedor.setSelectedItem(produto.getFornecedor());
        if(0 == (produto.getStatus())){
            radioAtivo.setSelected(true);
        }else{
            radioInativo.setSelected(true);
        }
        textSaldo.setText(produto.getSaldo()+"");
    }   
    

    /**
     * Creates new form DialogProduto
     */
    public DialogProduto(java.awt.Frame parent, boolean modal) {
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textId = new javax.swing.JTextField();
        textNome = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        radioAtivo = new javax.swing.JRadioButton();
        radioInativo = new javax.swing.JRadioButton();
        textDataValidade = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        textPrecoCusto = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        textPrecoVenda = new javax.swing.JTextField();
        comboFornecedor = new javax.swing.JComboBox<>();
        textSaldo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProduto = new javax.swing.JTable();
        buttonNovo = new javax.swing.JButton();
        buttonSalvar = new javax.swing.JButton();
        buttonRemover = new javax.swing.JButton();
        textFiltro = new javax.swing.JTextField();
        buttonFiltrar = new javax.swing.JButton();

        jRadioButton2.setText("jRadioButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de produtos");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));

        jLabel1.setText("Código");

        jLabel2.setText("Nome");

        jLabel3.setText("Fornecedor");

        textId.setEditable(false);
        textId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textIdActionPerformed(evt);
            }
        });

        jLabel5.setText("Data de validade");

        jLabel9.setText("Satus");

        buttonGroup1.add(radioAtivo);
        radioAtivo.setSelected(true);
        radioAtivo.setText("Ativo");

        buttonGroup1.add(radioInativo);
        radioInativo.setText("Inativo");

        try {
            textDataValidade.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel10.setText("Preço Custo");

        jLabel11.setText("Preço venda");

        textPrecoVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textPrecoVendaActionPerformed(evt);
            }
        });

        jLabel4.setText("Saldo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(textId, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2))
                            .addComponent(textPrecoCusto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textPrecoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9)
                                    .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(radioAtivo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(radioInativo))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(textSaldo)
                                            .addComponent(textDataValidade, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)))))))
                    .addComponent(comboFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(textDataValidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(textPrecoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(129, 129, 129))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textSaldo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(textPrecoCusto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11)
                                .addComponent(jLabel4)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(comboFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(radioAtivo)
                                    .addComponent(radioInativo))))
                        .addGap(97, 97, 97))))
        );

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
        jScrollPane1.setViewportView(tableProduto);

        buttonNovo.setText("Novo");
        buttonNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNovoActionPerformed(evt);
            }
        });

        buttonSalvar.setText("Salvar");
        buttonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSalvarActionPerformed(evt);
            }
        });

        buttonRemover.setText("Remover");
        buttonRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRemoverActionPerformed(evt);
            }
        });

        textFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFiltroActionPerformed(evt);
            }
        });

        buttonFiltrar.setText("Filtrar");
        buttonFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFiltrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonNovo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buttonRemover)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buttonFiltrar))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonNovo)
                    .addComponent(buttonSalvar)
                    .addComponent(buttonRemover)
                    .addComponent(textFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonFiltrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.carregaFornecedor();
        this.carregaProdutos();

    }//GEN-LAST:event_formWindowOpened

    private void tableProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProdutoMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount()==2) {
            String valor = tableProduto.getValueAt(tableProduto.getSelectedRow(), 0)+"";
            int codigo = Integer.parseInt(valor);
            populateComponente(dao.getProduto(codigo));
        }
    }//GEN-LAST:event_tableProdutoMouseClicked

    private void buttonFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFiltrarActionPerformed
        try {
            // TODO add your handling code here:
            this.carregaProdutos(textFiltro.getText());
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }//GEN-LAST:event_buttonFiltrarActionPerformed

    private void buttonRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRemoverActionPerformed
        // TODO add your handling code here:
        if(textId.getText().isEmpty()){
            return;
        }
        if(JOptionPane.showConfirmDialog(null, "Confirma?" )!= 0){
            return;
        }
        try{
            dao.deleteProduto(populateObject());
            this.iniciaComponentes();
            this.carregaProdutos();
        }catch(SQLException | ParseException ex){
            System.out.println("Erro: " + ex.getMessage());
        }
    }//GEN-LAST:event_buttonRemoverActionPerformed

    private void buttonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSalvarActionPerformed
        // TODO add your handling code here:
        try{
            validaFormulario();
            
            if(textId.getText().isEmpty()){
                dao.addProduto(populateObject());
            }else{//atualização
                dao.updateProduto(populateObject());
            }
            this.iniciaComponentes();
            this.carregaProdutos();
        }catch(SQLException | ParseException ex){
            System.out.println("Erro: " + ex.getMessage());
        }
    }//GEN-LAST:event_buttonSalvarActionPerformed

    private void buttonNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNovoActionPerformed
        // TODO add your handling code here:
        this.iniciaComponentes();
    }//GEN-LAST:event_buttonNovoActionPerformed

    private void textIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textIdActionPerformed

    private void textFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFiltroActionPerformed

    private void textPrecoVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textPrecoVendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textPrecoVendaActionPerformed

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
            java.util.logging.Logger.getLogger(DialogProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                DialogProduto dialog = new DialogProduto(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton buttonFiltrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton buttonNovo;
    private javax.swing.JButton buttonRemover;
    private javax.swing.JButton buttonSalvar;
    private javax.swing.JComboBox<String> comboFornecedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton radioAtivo;
    private javax.swing.JRadioButton radioInativo;
    private javax.swing.JTable tableProduto;
    private javax.swing.JFormattedTextField textDataValidade;
    private javax.swing.JTextField textFiltro;
    private javax.swing.JTextField textId;
    private javax.swing.JTextField textNome;
    private javax.swing.JTextField textPrecoCusto;
    private javax.swing.JTextField textPrecoVenda;
    private javax.swing.JTextField textSaldo;
    // End of variables declaration//GEN-END:variables
}
