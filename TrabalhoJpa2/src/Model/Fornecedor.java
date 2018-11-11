
package Model;

import Tipos.TipoPessoa;
import java.io.Serializable;
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

@Entity
@Table(name = "FORNECEDOR")
public class Fornecedor implements Serializable{
    @Id
    @SwingColumn(description = "Código",colorOfBackgound = "")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    
    @SwingColumn(description = "Nome",colorOfBackgound = "")
    @Column(name = "NOME",length = 100, nullable = false)
    private String nome;
    
    @Column(name = "CNPJ",length = 20, nullable = false)
    private String cnpj;  
    
    @Column(name = "BAIRRO",length = 100, nullable = false)
    private String bairro; 
    
    @Column(name = "ENDERECO",length = 250, nullable = false)
    private String endereco; 
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CIDADE", nullable = false, referencedColumnName = "id")
    private Cidade cidade;
    
    @Column(name = "STATUS", nullable = false)
    private int status;
    
    @SwingColumn(description = "Tipo",colorOfBackgound = "")
    @Enumerated(EnumType.STRING)
    @Column(name = "PESSOA",length = 2, nullable = false)
    private TipoPessoa pessoa;
    
    public Fornecedor(){
    }
    public Fornecedor(int id, String nome,String cnpj,String bairro,String endereco,Cidade cidade,int status,TipoPessoa pessoa){
        this.setId(id);
        this.setNome(nome);
        this.setCnpj(cnpj);        
        this.setBairro(bairro);        
        this.setEndereco(endereco);        
        this.setCidade(cidade);
        this.setStatus(status);        
        this.setPessoa(pessoa);        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome.toUpperCase();
    }

    public void setNome(String nome) {
        this.nome = nome.trim().isEmpty()?"DEFAULT":nome.toUpperCase();
    }
    
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getBairro() {
        return bairro.toUpperCase();
    }

    public void setBairro(String bairro) {
        this.bairro = bairro.trim().isEmpty()?"DEFAULT":bairro.toUpperCase();
    }

    public String getEndereco() {
        return endereco.toUpperCase();
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco.trim().isEmpty()?"DEFAULT":endereco.toUpperCase();
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
        this.status = status < 0 ? 0 : status;
    } 
    public TipoPessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(TipoPessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public String toString() {
        return nome;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
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
        final Fornecedor other = (Fornecedor) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
