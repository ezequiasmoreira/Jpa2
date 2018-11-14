
package staticas;

import Model.Venda;
import java.util.LinkedList;

public class Staticas {
    
    private static LinkedList<Venda> listaVendaStatica = new LinkedList<>();
    public Staticas(){
        
    }
    
    public static LinkedList<Venda> getListaStaticaVenda(){
        return listaVendaStatica;
    }
    
}
