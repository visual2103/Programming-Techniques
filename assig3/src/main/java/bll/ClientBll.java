package bll;

import bll.validator.NameValidator;
import dao.ClientDAO;
import model.Client;
import java.util.logging.*;
import java.util.* ;

// operatiile pe client

/***
 * Clasa ClientBll conține operațiile pe care le putem efectua asupra obiectelor de tip Client.
 * Aceasta face parte din stratul de logică a afacerii (Business Logic Layer - BLL) și utilizează DAO-uri (Data Access Objects) pentru a interacționa cu baza de date.
 */
public class ClientBll {
    private ClientDAO clientDAO ;
    private static final Logger LOGGER = Logger.getLogger(ClientBll.class.getName());

    public ClientBll(){
        this.clientDAO = new ClientDAO() ;
    }

    /***
     * Această metodă caută și returnează un client bazat pe id-ul dat.
     * Dacă clientul nu este găsit, se aruncă o excepție NoSuchElementException.
     * Orice altă excepție survenită în timpul căutării este logată și se aruncă o excepție RuntimeException.
     *
     * @param id ID-ul clientului care trebuie găsit.
     * @return Obiectul de tip Client găsit.
     * @throws NoSuchElementException dacă clientul cu id-ul dat nu este găsit.
     */

    public Client findClientById(int id) {
        try {
            Client client = clientDAO.findById(id);
            if (client == null) {
                throw new NoSuchElementException("This client with id " + id + " wasn't found!");
            }
            return client;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "An error occurred while finding client by id: " + e.getMessage());
            throw new RuntimeException("Failed to find client by id due to database error.");
        }
    }

    /***
     * Această metodă returnează o listă cu toți clienții din sistem.
     *
     * @return Lista cu toți clienții.
     */

    public List<Client> returnAll (){
        return clientDAO.findAll() ;
    }

    /***
     * Această metodă adaugă un client nou în sistem folosind obiectul de client dat.
     * Se validează numele clientului înainte de a fi inserat.
     *
     * @param client Obiectul Client care trebuie adăugat.
     */
    public void addClient(Client client){
        NameValidator validator = new NameValidator() ;
        validator.validate(client);
        clientDAO.insert(client);
    }
    /***
     * Această metodă șterge un client din sistem după ID-ul său.
     *
     * @param id ID-ul clientului care trebuie șters.
     */

    public void deleteClient(int id) {
        //de pus cv exceptii
        clientDAO.delete(id);
    }

    /***
     * Această metodă actualizează un client în sistem pe baza ID-ului său și a perechii cheie-valoare dată.
     *
     * @param id ID-ul clientului care trebuie actualizat.
     * @param s1 Numele câmpului care trebuie actualizat.
     * @param s2 Noua valoare a câmpului.
     */

    public void update(int id,String s1, String s2) {
        StringBuilder x = new StringBuilder();
        x.append(" " + s1 + "=\"" + s2 + "\"");
        clientDAO.updateById(id,x.toString());
    }
    /***
     * Această metodă actualizează un client în sistem pe baza ID-ului său și a perechii cheie-valoare dată.
     * Este similară cu metoda update(int id, String s1, String s2).
     *
     * @param id ID-ul clientului care trebuie actualizat.
     * @param s1 Numele câmpului care trebuie actualizat.
     * @param s2 Noua valoare a câmpului.
     */

    public void updateClient(int id,String s1, String s2) {
        StringBuilder x = new StringBuilder();
        x.append(" " + s1 + "=\"" + s2 + "\"");
        clientDAO.updateById(id, x.toString());
    }

}

