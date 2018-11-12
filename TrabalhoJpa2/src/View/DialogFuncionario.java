/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.DaoCidade;
import Controller.DaoFuncionario;
import Model.Cidade;
import Model.Funcionario;
import Tipos.TipoSexo;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Ezequias e Karol
 */
public class DialogFuncionario extends javax.swing.JDialog {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    DaoFuncionario dao = new DaoFuncionario();
    DaoCidade cidade = new DaoCidade();
    
    public void validaFormulario(){
        if (textNome.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Nome obrigátorio");
            textNome.requestFocus();
            return;
        }
        if (textCpf.getText().equals("   .   .  -  ")){
            JOptionPane.showMessageDialog(null, "Cpf obrigátorio");
            textCpf.requestFocus();
            return;
        }
        if (textDataNascimento.getText().equals("  /  /    ")){
            JOptionPane.showMessageDialog(null, "A data de nascimento ´é obrigátorio");
            textDataNascimento.requestFocus();
            return;
        }
        if (textEndereco.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Endereço obrigátorio");
            textEndereco.requestFocus();
            return;
        }
        if (textBairro.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Bairro obrigátorio");
            textBairro.requestFocus();
            return;
        }
        if (textIdCidade.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "selecione uma cidade");
            buttonPesquisarCidade.requestFocus();
            return;
        }
    }
    
    private void carregaSexo(){
       DefaultComboBoxModel cbm = new DefaultComboBoxModel(TipoSexo.values());
        comboSexo.setModel(cbm);
    }
    private void carregaCidade() throws SQLException{
        List<Cidade> lista = cidade.getCidadeList();
        tableCidade.setModel(
            new MyTableModel(Cidade.class, lista, tableCidade)
        );  
    }
    private void carregaCidade(String filtro) throws SQLException{        
        List<Cidade> lista = cidade.getCidadeList(filtro);
        tableCidade.setModel(
            new MyTableModel(Cidade.class, lista, tableCidade)
        ); 
    }
    
    private void carregaFuncionarios() throws SQLException{
        List<Funcionario> lista = dao.getFuncionarioList();
        tableFuncionario.setModel(
            new MyTableModel(Funcionario.class, lista, tableFuncionario)
        );
    }
    private void carregaFuncionarios(String filtro) throws SQLException{
        List<Funcionario> lista = dao.getFuncionarioList(filtro);
        tableFuncionario.setModel(
            new MyTableModel(Funcionario.class, lista, tableFuncionario)
        );
    }
    private void iniciaComponentes(){
        textId.setText("");
        textNome.setText("");
        textCpf.setText("");
        textBairro.setText("");
        textEndereco.setText("");
        textDataNascimento.setText("");
        comboSexo.setSelectedIndex(0);
        textIdCidade.setText("");
        textCidade.setText("");
        textFiltro.setText("");
        radioAtivo.setSelected(true);
        textNome.requestFocus();
    }
    //cria um objeto a partir da tela
    private Funcionario populateObject() throws ParseException, SQLException{
        Date data = sdf.parse(textDataNascimento.getText());
        int idCidade = Integer.parseInt(textIdCidade.getText());
        return (new Funcionario(
                    textId.getText().isEmpty()?0:Integer.parseInt(textId.getText()),
                    textNome.getText().toUpperCase(),
                    textCpf.getText(),
                    textBairro.getText().toUpperCase(),
                    textEndereco.getText().toUpperCase(),
                    data,
                    (TipoSexo)comboSexo.getSelectedItem(),
                    cidade.getCidade(idCidade),
                    (radioAtivo.isSelected()?0:1)
        ));
    }
    //preencher os componentes com o objeto
    private void populateComponente(Funcionario funcionario){
        String dataFormatada = sdf.format(funcionario.getDataNascimento());
        textId.setText(funcionario.getId()+"");
        textNome.setText(funcionario.getNome());
        textCpf.setText(funcionario.getCpf());
        textBairro.setText(funcionario.getBairro());
        textEndereco.setText(funcionario.getEndereco());
        textDataNascimento.setText(dataFormatada);
        textIdCidade.setText(funcionario.getCidade().getId()+"");
        textCidade.setText(funcionario.getCidade().getNome());
        comboSexo.setSelectedItem(funcionario.getSexo());
        if(0 == (funcionario.getStatus())){
            radioAtivo.setSelected(true);
        }else{
            jRadioButton1.setSelected(true);
        }
    }   
    //preencher os componentes com o objeto
    private void populateComponenteCidade(Cidade cidade){
        textIdCidade.setText(cidade.getId()+"");
        textCidade.setText(cidade.getNome());
    }   

    /**
     * Creates new form DialogFuncionario
     */
    public DialogFuncionario(java.awt.Frame parent, boolean modal) {
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
        comboSexo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        textEndereco = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        textBairro = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        radioAtivo = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        textDataNascimento = new javax.swing.JFormattedTextField();
        textCpf = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableFuncionario = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCidade = new javax.swing.JTable();
        buttonNovo = new javax.swing.JButton();
        buttonSalvar = new javax.swing.JButton();
        buttonRemover = new javax.swing.JButton();
        textFiltro = new javax.swing.JTextField();
        buttonFiltrar = new javax.swing.JButton();
        textCidade = new javax.swing.JTextField();
        buttonPesquisarCidade = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        textIdCidade = new javax.swing.JTextField();

        jRadioButton2.setText("jRadioButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Funcionarios");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));

        jLabel1.setText("Código");

        jLabel2.setText("Nome");

        jLabel3.setText("Sexo");

        textId.setEditable(false);
        textId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textIdActionPerformed(evt);
            }
        });

        jLabel4.setText("Cpf");

        jLabel5.setText("Data de nascimento");

        jLabel6.setText("Endereço");

        jLabel7.setText("Bairro");

        jLabel9.setText("Satus");

        buttonGroup1.add(radioAtivo);
        radioAtivo.setSelected(true);
        radioAtivo.setText("Ativo");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Inativo");

        try {
            textDataNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            textCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        textCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textCpfActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(comboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textEndereco))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(textId, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(textDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(textBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(radioAtivo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButton1)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(textId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(textDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(textEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(textBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(radioAtivo)
                    .addComponent(jRadioButton1))
                .addGap(86, 86, 86))
        );

        tableFuncionario.setModel(new javax.swing.table.DefaultTableModel(
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
        tableFuncionario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableFuncionarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableFuncionario);

        tableCidade.setModel(new javax.swing.table.DefaultTableModel(
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
        tableCidade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCidadeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableCidade);

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

        buttonPesquisarCidade.setText("...");
        buttonPesquisarCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPesquisarCidadeActionPerformed(evt);
            }
        });

        jLabel8.setText("Cidade");

        textIdCidade.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonNovo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buttonRemover)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textFiltro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buttonFiltrar))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textIdCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonPesquisarCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonNovo)
                    .addComponent(buttonSalvar)
                    .addComponent(buttonRemover)
                    .addComponent(textFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonFiltrar)
                    .addComponent(textCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonPesquisarCidade)
                    .addComponent(jLabel8)
                    .addComponent(textIdCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        
        try {
            this.carregaSexo();
            this.carregaCidade();
            this.carregaFuncionarios();
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }

    }//GEN-LAST:event_formWindowOpened

    private void tableFuncionarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableFuncionarioMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount()==2) {
            String valor = tableFuncionario.getValueAt(tableFuncionario.getSelectedRow(), 0)+"";
            int codigo = Integer.parseInt(valor);
            populateComponente(dao.getFuncionario(codigo));
        }
    }//GEN-LAST:event_tableFuncionarioMouseClicked

    private void buttonFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFiltrarActionPerformed
        try {
            // TODO add your handling code here:
            this.carregaFuncionarios(textFiltro.getText());
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
            dao.deleteFuncionario(populateObject());
            this.iniciaComponentes();
            this.carregaFuncionarios();
        }catch(SQLException | ParseException ex){
            System.out.println("Erro: " + ex.getMessage());
        }
    }//GEN-LAST:event_buttonRemoverActionPerformed

    private void buttonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSalvarActionPerformed
        // TODO add your handling code here:
        try{
            validaFormulario();
            if(textIdCidade.getText().isEmpty()){
                return;
            }
            if(textId.getText().isEmpty()){
                dao.addFuncionario(populateObject());
            }else{//atualização
                dao.updateFuncionario(populateObject());
            }
            this.iniciaComponentes();
            this.carregaFuncionarios();
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

    private void buttonPesquisarCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPesquisarCidadeActionPerformed
        //pesquisa de cidade
        try {
            this.carregaCidade(textCidade.getText());
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        
    }//GEN-LAST:event_buttonPesquisarCidadeActionPerformed

    private void tableCidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCidadeMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount()==2) {
            String strCidade = tableCidade.getValueAt(tableCidade.getSelectedRow(), 0)+"";
            int idCidade = Integer.parseInt(strCidade);
            populateComponenteCidade(cidade.getCidade(idCidade));
        }
    }//GEN-LAST:event_tableCidadeMouseClicked

    private void textCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textCpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textCpfActionPerformed

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
            java.util.logging.Logger.getLogger(DialogFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                DialogFuncionario dialog = new DialogFuncionario(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton buttonPesquisarCidade;
    private javax.swing.JButton buttonRemover;
    private javax.swing.JButton buttonSalvar;
    private javax.swing.JComboBox<String> comboSexo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton radioAtivo;
    private javax.swing.JTable tableCidade;
    private javax.swing.JTable tableFuncionario;
    private javax.swing.JTextField textBairro;
    private javax.swing.JTextField textCidade;
    private javax.swing.JFormattedTextField textCpf;
    private javax.swing.JFormattedTextField textDataNascimento;
    private javax.swing.JTextField textEndereco;
    private javax.swing.JTextField textFiltro;
    private javax.swing.JTextField textId;
    private javax.swing.JTextField textIdCidade;
    private javax.swing.JTextField textNome;
    // End of variables declaration//GEN-END:variables
}
