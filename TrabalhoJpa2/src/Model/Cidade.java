
package Model;

import Tipos.TipoSigla;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CIDADE")

public class Cidade implements Serializable{
    @Id
    @SwingColumn(description = "Código",colorOfBackgound = "")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    
    @SwingColumn(description = "Nome",colorOfBackgound = "")
    @Column(name = "NOME",length = 100, nullable = false)
    private String nome;
    
    @SwingColumn(description = "Estado",colorOfBackgound = "")
    @Enumerated(EnumType.STRING)
    @Column(name = "SIGLA",length = 2, nullable = false)
    private TipoSigla sigla;
    
    public Cidade(){        
    }

    public Cidade(int id, String nome, TipoSigla sigla) {
        this.setId(id);
        this.setNome(nome);
        this.setSigla(sigla);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome.trim().isEmpty()?"PADRÃO" : nome.toUpperCase();
    }

    public void setNome(String nome) {
        this.nome = nome.trim().isEmpty()?"PADRÃO" : nome.toUpperCase();
    }

    public TipoSigla getSigla() {
        return sigla;
    }

    public void setSigla(TipoSigla sigla) {
        this.sigla = sigla;
    }

    @Override
    public String toString() {
        return  nome;
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
        final Cidade other = (Cidade) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    

}
