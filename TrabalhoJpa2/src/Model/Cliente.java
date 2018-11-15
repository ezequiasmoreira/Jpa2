
package Model;

import Tipos.TipoSexo;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "CLIENTE")
public class Cliente implements Serializable{
    
    @Id
    @SwingColumn(description = "Código",colorOfBackgound = "")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    
    @SwingColumn(description = "Nome",colorOfBackgound = "")
    @Column(name = "NOME",length = 100, nullable = false)
    private String nome;  
    
    @SwingColumn(description = "Cpf",colorOfBackgound = "")
    @Column(name = "CPF",length = 14, nullable = false)
    private String cpf;
    
    @SwingColumn(description = "Bairro",colorOfBackgound = "")
    @Column(name = "BAIRRO",length = 100, nullable = false)
    private String bairro;
    
    @Column(name = "ENDERECO",length = 250, nullable = false)
    @SwingColumn(description = "Endereço",colorOfBackgound = "")
    private String endereco;    
    
    @Temporal(TemporalType.DATE)
    @Column(name = "nascimento", nullable = false)
    private Date dataNascimento;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "SEXO",length = 1, nullable = false)
    private TipoSexo sexo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CIDADE", nullable = false, referencedColumnName = "ID")
    private Cidade cidade;
    
    @Column(name = "STATUS", nullable = false)
    private int status;
    
    public Cliente(){
    }
    public Cliente(int id, String nome,String cpf,String bairro,String endereco,  Date dataNascimento,TipoSexo sexo,Cidade cidade,int status){
        
        this.setId(id);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setBairro(bairro);
        this.setEndereco(endereco);
        this.setDataNascimento(dataNascimento);
        this.setSexo(sexo);
        this.setCidade(cidade);
        this.setStatus(status);
        
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
        this.nome = nome.trim().isEmpty()?"DEFAULT":nome.toUpperCase();;
    }
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf.trim().isEmpty()?"00000000000":cpf.toUpperCase();
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro.trim().isEmpty()?"DEFAULT":bairro.toUpperCase();
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco.trim().isEmpty()?"DEFAULT":endereco.toUpperCase();
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status<0 ? 0:status;
    }   

    public TipoSexo getSexo() {
        return sexo;
    }

    public void setSexo(TipoSexo sexo) {
        this.sexo = sexo;
    }
    
}
