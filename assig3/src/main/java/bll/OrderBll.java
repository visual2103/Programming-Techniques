package bll;

import dao.OrderDAO;
import model.Comanda;
import model.Produs;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/***
 *Aceste metode sunt parte a logicii de afaceri (bll - business logic layer) și interacționează cu obiectele de acces la date (dao - data access objects) pentru a gestiona operațiile CRUD (create, read, update, delete) pe obiecte de comandă (Comanda).
 */
public class OrderBll {
    private OrderDAO orderDAO ;
    private static final Logger LOGGER = Logger.getLogger(OrderBll.class.getName());


    public OrderBll() {
        this.orderDAO = new OrderDAO() ;
    }

    /***
     * Această metodă caută și returnează o comandă bazată pe id-ul dat. Dacă comanda nu este găsită, se aruncă o excepție NoSuchElementException. Orice altă excepție survenită în timpul căutării este logată și se aruncă o excepție RuntimeException.
     * @param id
     * @return obiect de tip Comanda (comanda cu id ul id)
     */
    public Comanda findOrderById(int id) {
        try {
            Comanda comanda = orderDAO.findById(id) ;
            if (comanda == null) {
                throw new NoSuchElementException("This order with id " + id + " wasn't found!");
            }
            return comanda;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "An error occurred while finding order by id: " + e.getMessage());
            throw new RuntimeException("Failed to find order by id due to database error.");
        }
    }

    /***
     *  Această metodă returnează o listă cu toate comenzile din sistem.
     * @return lista cu elementele
     */
    public List<Comanda> returnAll (){
        return orderDAO.findAll() ;
    }

    /***
     * Această metodă adaugă o comandă nouă în sistem folosind obiectul de comandă dat.
     * @param comanda
     */
    public void addOrder(Comanda comanda) {
        orderDAO.insert(comanda);
    }
    /***
     * Această metodă șterge o comandă din sistem după ID-ul său.
     *
     * @param id ID-ul comenzii care trebuie ștearsă.
     */
    public void deleteOrder(int id) {
        //de pus cv exceptii
        orderDAO.delete(id);
    }
    /***
     * Această metodă actualizează o comandă în sistem pe baza ID-ului său și a perechii cheie-valoare dată.
     *
     * @param id ID-ul comenzii care trebuie actualizată.
     * @param s1 Numele câmpului care trebuie actualizat.
     * @param s2 Noua valoare a câmpului.
     */
    public void updateOrder(int id,String s1, String s2) {
        StringBuilder x = new StringBuilder();
        x.append(" " + s1 + "=\"" + s2 + "\"");
        orderDAO.updateById(id, x.toString());
    }
}
