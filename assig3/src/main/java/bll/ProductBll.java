package bll;

import bll.validator.NameValidator;
import dao.AbstractDAO;
import dao.ClientDAO;
import dao.ProductDAO;
import model.Client;
import model.Produs;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa ProductBll reprezintă logica de afaceri pentru entitatea Produs.
 * Aceasta se ocupă cu gestionarea operațiunilor legate de produse,
 * cum ar fi găsirea, adăugarea, ștergerea și actualizarea produselor.
 */

public class ProductBll  {
    private ProductDAO productDAO ;
    private static final Logger LOGGER = Logger.getLogger(ClientBll.class.getName());

    public ProductBll(){
        this.productDAO = new ProductDAO() ;
    }

    /**
     * Găsește un produs după ID-ul său.
     *
     * @param id ID-ul produsului care trebuie găsit.
     * @return Obiectul Produs găsit.
     * @throws NoSuchElementException dacă produsul nu este găsit.
     */
    public Produs findProdusById(int id) {
        try {
            Produs produs = productDAO.findById(id);
            if (produs == null) {
                throw new NoSuchElementException("This client with id " + id + " wasn't found!");
            }
            return produs;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "An error occurred while finding client by id: " + e.getMessage());
            throw new RuntimeException("Failed to find client by id due to database error.");
        }
    }

    /**
     * Returnează o listă cu toate produsele.
     *
     * @return Listă cu toate produsele.
     */
    public List<Produs> returnAll (){
        return productDAO.findAll() ;
    }

    /**
     * Adaugă un produs nou în baza de date.
     *
     * @param produs Obiectul Produs care trebuie adăugat.
     */
    public void addProdus(Produs produs){
        NameValidator validator = new NameValidator() ;
        productDAO.insert(produs);
    }

    /**
     * Șterge un produs din baza de date după ID-ul său.
     *
     * @param id ID-ul produsului care trebuie șters.
     */
    public void deleteProdus(int id) {
        //de pus cv exceptii
        productDAO.delete(id);
    }

    /**
     * Actualizează un produs în baza de date pe baza ID-ului său.
     *
     * @param id ID-ul produsului care trebuie actualizat.
     * @param s1 Numele câmpului care trebuie actualizat.
     * @param s2 Noua valoare a câmpului.
     */
    public void update(int id,String s1, String s2) {
        StringBuilder x = new StringBuilder();
        x.append(" " + s1 + "=\"" + s2 + "\"");
        productDAO.updateById(id,x.toString());
    }

    /**
     * Actualizează un produs în baza de date pe baza ID-ului său.
     *
     * @param s1 Numele câmpului care trebuie actualizat.
     * @param s2 Noua valoare a câmpului.
     * @param xx ID-ul produsului care trebuie actualizat.
     */
    public void update(String s1, String s2, int xx) {
        StringBuilder x = new StringBuilder();
        x.append(" " + s1 + "=\"" + s2 + "\"");
        productDAO.updateById(xx,x.toString());
    }

}
