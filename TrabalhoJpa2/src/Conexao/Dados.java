package Conexao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Dados {
    private static EntityManager em = null;
    
    public static EntityManager getEntity(){
        if (em == null){
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("TrabalhoJpa2PU");
            em = factory.createEntityManager();
        }
        return em;
    }
}
