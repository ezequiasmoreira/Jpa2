package Tipos;

public enum TipoPessoa {
    F("Física"),
    J("Jurídica");
    
    private String descricao;

    private TipoPessoa(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
