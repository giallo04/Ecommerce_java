package Database;

import Entity.Merce.Prodotto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Map;

//Facade per la persistenza dei dati nel database.
public class GestorePersistenza {


    public void salva(Object oggetto) {

        EntityManager em = JpaUtil.getInstance().getEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(oggetto);

            em.getTransaction().commit();

        } catch (RuntimeException e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {

            em.close();
        }
    }
    public void modifica(Object oggetto) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(oggetto);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
public void rimuovi(long id) {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(trovaPerId(Prodotto.class, id));
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {}
}

    public void salvaTutti(Object... oggetti) {

        EntityManager em = JpaUtil.getInstance().getEntityManager();

        try {
            em.getTransaction().begin();

            for (Object oggetto : oggetti) {
                em.persist(oggetto);
            }

            em.getTransaction().commit();

        } catch (RuntimeException e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }


    public <T> T trovaPerId(Class<T> classe, Long id) {

        EntityManager em = JpaUtil.getInstance().getEntityManager();

        try {

            return em.find(classe, id);

        } finally {
            em.close();
        }
    }


    public <T> List<T> cercaPerCampo(Class<T> classe,
                                     String nomeCampo,
                                     Object valore) {

        return cercaPerCampi(
                classe,
                Map.of(nomeCampo, valore)
        );
    }

    /*
     * Cerca tutti gli oggetti persistenti che soddisfano un insieme di condizioni.
     *
     * La query JPQL viene costruita nel livello database.
     */
    public <T> List<T> cercaPerCampi(Class<T> classe,
                                     Map<String, Object> campi) {

        EntityManager em = JpaUtil.getInstance().getEntityManager();

        try {
            StringBuilder jpql = new StringBuilder();

            jpql.append("SELECT e FROM ")
                    .append(classe.getSimpleName())
                    .append(" e");

            if (!campi.isEmpty()) {
                jpql.append(" WHERE ");

                int contatore = 0;

                for (String nomeCampo : campi.keySet()) {
                    if (contatore > 0) {
                        jpql.append(" AND ");
                    }

                    String nomeParametro = nomeCampo.replace(".", "_");

                    jpql.append("e.")
                            .append(nomeCampo)
                            .append(" = :")
                            .append(nomeParametro);

                    contatore++;
                }
            }

            TypedQuery<T> query = em.createQuery(
                    jpql.toString(),
                    classe
            );

            for (String nomeCampo : campi.keySet()) {
                String nomeParametro = nomeCampo.replace(".", "_");
                query.setParameter(nomeParametro, campi.get(nomeCampo));
            }

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    /*
     * Cerca il primo oggetto persistente che soddisfa un insieme di condizioni.
     *
     * Se non trova nessun risultato, restituisce null.
     */
    public <T> T cercaPrimoPerCampi(Class<T> classe,
                                    Map<String, Object> campi) {

        List<T> risultati = cercaPerCampi(classe, campi);

        if (risultati.isEmpty()) {
            return null;
        }

        return risultati.get(0);
    }

}