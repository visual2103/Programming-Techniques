package presentation;

import bll.Bill;
import bll.ClientBll;
import bll.OrderBll;
import bll.ProductBll;
import com.itextpdf.text.DocumentException;
import model.Client;
import model.Comanda;
import model.Produs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class View extends JPanel implements ActionListener{
    private JFrame frame;
    private JPanel currentPanel;
    private JTable clientTable;
    private JTable productTable;
    private JTable orderTable;
    private ClientBll clientBll ;
    private OrderBll orderBll ;
    private ProductBll productBll ;
    DefaultTableModel model;

    /***
     * Această metodă este concepută pentru a prelua dinamic proprietățile (câmpurile) unui obiect dat și pentru a adăuga aceste proprietăți ca și coloane în DefaultTableModel folosit de JTable.
     * Implementare: Folosește Reflectia în Java pentru a obține câmpurile clasei obiectului și apoi iterează peste ele pentru a adăuga numele fiecărui câmp ca și coloană în modelul tabelului.
     * @param object
     */

    public void retrieveProperties(Object object) {
        System.out.println(object.getClass().getName());
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
                System.out.println(value);
//                table.addColumn(new TableColumn(0,field.getName()));
                model.addColumn(field.getName());

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    /***
     *  Această metodă configurează și afișează fereastra principală a aplicației (JFrame) și componentele sale, inclusiv eticheta de bun venit și butoanele pentru navigarea între diferitele panouri (Clienți, Produse, Comenzi).
     * Implementare: Inițializează JFrame, îi setează dimensiunea și operația de închidere implicită, apoi adaugă o etichetă de bun venit și un panou cu butoane de navigare. Fiecare buton este asociat cu un ascultător de acțiuni pentru a afișa panoul corespunzător.
     */
    public void createAndShowGUI() {
        frame = new JFrame("Order Management Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);


        JLabel label = new JLabel("Welcome to your Shop!", SwingConstants.CENTER);
        Font font = new Font("Arial", Font.BOLD, 30);
        label.setFont(font);
        frame.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton button1 = new JButton("Clients");
        JButton button2 = new JButton("Products");
        JButton button3 = new JButton("Orders");

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        frame.add(buttonPanel, BorderLayout.NORTH);

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setVisible(false);
                showClientPanel();
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setVisible(false);
                showProductPanel();
            }
        });

        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                label.setVisible(false);
                showOrderPanel();
            }
        });

        frame.setVisible(true);
    }

    /***
     * Această metodă configurează și afișează panoul pentru gestionarea clienților, inclusiv câmpurile de formular pentru datele clienților și butoanele pentru adăugarea, ștergerea și editarea clienților.
     * Implementare: Inițializează diverse componente, cum ar fi etichete, câmpuri text și butoane, și le setează pozițiile. Apoi populează tabelul clienților prin preluarea tuturor clienților din ClientBll și adăugarea datelor lor în modelul tabelului.
     */
    JButton addButton,delButton ,editButton ,viewButton;
    JTextField idTextField , nameTextField ,emailTextField ,ageTextField  ,newNameTextField,newEmailTextField;

    public void showClientPanel() {
        JPanel clientPanel = new JPanel(null);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(50, 50, 100, 30);
        clientPanel.add(idLabel);

        idTextField = new JTextField();
        idTextField.setBounds(200, 50, 200, 30);
        clientPanel.add(idTextField);

        JLabel nameLabel = new JLabel("Nume:");
        nameLabel.setBounds(50, 100, 100, 30);
        clientPanel.add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(200, 100, 200, 30);
        clientPanel.add(nameTextField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 150, 100, 30);
        clientPanel.add(emailLabel);

        emailTextField = new JTextField();
        emailTextField.setBounds(200, 150, 200, 30);
        clientPanel.add(emailTextField);

        JLabel ageLabel = new JLabel("Vârstă:");
        ageLabel.setBounds(50, 200, 100, 30);
        clientPanel.add(ageLabel);

        ageTextField = new JTextField();
        ageTextField.setBounds(200, 200, 200, 30);
        clientPanel.add(ageTextField);

        JLabel updateLabel = new JLabel("Date noi :");
        updateLabel.setBounds(750, 70, 100, 30);
        clientPanel.add(updateLabel);

        newNameTextField = new JTextField();
        newNameTextField.setBounds(700, 100, 200, 30);
        clientPanel.add(newNameTextField);

        newEmailTextField = new JTextField();
        newEmailTextField.setBounds(700, 150, 200, 30);
        clientPanel.add(newEmailTextField);

        addButton = new JButton("Adauga client");
        addButton.setBounds(500, 50, 200, 30);
        addButton.addActionListener(this);
        clientPanel.add(addButton);

        delButton = new JButton("Sterge client");
        delButton.setBounds(500, 100, 200, 30);
        delButton.addActionListener(this);
        clientPanel.add(delButton);

        editButton = new JButton("Modifica client");
        editButton.setBounds(500, 150, 200, 30);
        editButton.addActionListener(this);
        clientPanel.add(editButton);

//        viewButton = new JButton("Lista de clienti");
//        viewButton.setBounds(500, 200, 200, 30);
//        viewButton.addActionListener(this);
//        clientPanel.add(viewButton);

        model = new DefaultTableModel() ;
        retrieveProperties(new Client());
        clientBll = new ClientBll();
        List<Client> clientList = clientBll.returnAll();
        for(Client c: clientList){
            String[] s = (String[]) c.row();
            model.insertRow(model.getRowCount(), c.row());
        }

        String[] columnNames ={} ;
        clientTable = new JTable(model);
        clientTable.setBounds(50,300 ,800,200);
        JScrollPane sp = new JScrollPane(clientTable);
        sp.setBounds(50,300 ,800,200);
        clientPanel.add(sp);

        clientPanel.setSize(500, 200);

        clientPanel.setVisible(true);


        showPanel(clientPanel);
    }

    /***
     *  Această metodă configurează și afișează panoul pentru gestionarea produselor, inclusiv câmpurile de formular pentru datele produselor și butoanele pentru adăugarea, ștergerea și editarea produselor.
     * Implementare: Similar cu showClientPanel(), inițializează componentele, setează pozițiile și populează tabelul produselor prin preluarea tuturor produselor din ProductBll și adăugarea datelor lor în modelul tabelului.
     */
    JTextField idTextField1 ,nameTextField1 ,quantityTextField1 ,priceTextField1 ,newNameTextField1 ,newEmailTextField1 ;
    JButton addButton1 , delButton1 , editButton1 ;
    public void showProductPanel() {
        model = new DefaultTableModel();
        JPanel productPanel = new JPanel(null);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(50, 50, 100, 30);
        productPanel.add(idLabel);

        JLabel updateLabel1 = new JLabel("Date noi :");
        updateLabel1.setBounds(750, 70, 100, 30);
        productPanel.add(updateLabel1);

        idTextField1 = new JTextField();
        idTextField1.setBounds(200, 50, 200, 30);
        productPanel.add(idTextField1);

        JLabel nameLabel = new JLabel("Nume:");
        nameLabel.setBounds(50, 100, 100, 30);
        productPanel.add(nameLabel);

        nameTextField1 = new JTextField();
        nameTextField1.setBounds(200, 100, 200, 30);
        productPanel.add(nameTextField1);

        JLabel quantityLabel = new JLabel("Cantitate:");
        quantityLabel.setBounds(50, 150, 100, 30);
        productPanel.add(quantityLabel);

        quantityTextField1 = new JTextField();
        quantityTextField1.setBounds(200, 150, 200, 30);
        productPanel.add(quantityTextField1);

        JLabel priceLabel = new JLabel("Preț:");
        priceLabel.setBounds(50, 200, 100, 30);
        productPanel.add(priceLabel);

        priceTextField1 = new JTextField();
        priceTextField1.setBounds(200, 200, 200, 30);
        productPanel.add(priceTextField1);

        newNameTextField1 = new JTextField();
        newNameTextField1.setBounds(700, 100, 200, 30);
        productPanel.add(newNameTextField1);

        newEmailTextField1 = new JTextField();
        newEmailTextField1.setBounds(700, 150, 200, 30);
        productPanel.add(newEmailTextField1);

        addButton1 = new JButton("Adauga produs");
        addButton1.setBounds(500, 50, 200, 30);
        addButton1.addActionListener(this);
        productPanel.add(addButton1);

        delButton1= new JButton("Sterge produs");
        delButton1.setBounds(500, 100, 200, 30);
        delButton1.addActionListener(this);

        productPanel.add(delButton1);

        editButton1 = new JButton("Modifica produs");
        editButton1.setBounds(500, 150, 200, 30);
        editButton1.addActionListener(this);

        productPanel.add(editButton1);

//        JButton viewButton1 = new JButton("Lista de produse");
//        viewButton1.setBounds(500, 200, 200, 30);
//        productPanel.add(viewButton1);

        model = new DefaultTableModel() ;
        retrieveProperties(new Produs());
        productBll = new ProductBll();


        List<Produs> produsList = productBll.returnAll();
        for(Produs c1: produsList){
            String[] s = (String[]) c1.row();
            model.insertRow(model.getRowCount(), c1.row());
        }

        String[] columnNames = {} ;
        productTable = new JTable(model);
        productTable.setBounds(50,300 ,800,200);
        JScrollPane sp = new JScrollPane(productTable);
        sp.setBounds(50,300 ,800,200);
        productPanel.add(sp);

        productPanel.setSize(500, 200);

        productPanel.setVisible(true);
        showPanel(productPanel);
    }

    /***
     * Această metodă configurează și afișează panoul pentru gestionarea comenzilor, inclusiv câmpurile de formular pentru datele comenzilor și butoanele pentru adăugarea și ștergerea comenzilor.
     * Implementare: Inițializează componente cum ar fi etichete, combo box-uri pentru selectarea clienților și produselor și un combo box pentru cantitate. De asemenea, populează tabelul comenzilor prin preluarea tuturor comenzilor din OrderBll și adăugarea datelor lor în modelul tabelului.
     */
    JButton addButton2 , delButton2 , editButton2 , viewButton2 ;
    JComboBox<String> idComboBox , nameComboBox ;
    JComboBox<Integer> totalQuantityComboBox ;
     public void showOrderPanel() {
        List<Client> clientList = new ArrayList<>() ;
        List<Produs> produsList = new ArrayList<>();
        clientBll = new ClientBll() ;
        productBll = new ProductBll() ;
        clientList = clientBll.returnAll() ;
        produsList = productBll.returnAll() ;

        JPanel orderPanel = new JPanel(null);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(50, 50, 100, 30);
        orderPanel.add(idLabel);

        JLabel nameLabel = new JLabel("Nume:");
        nameLabel.setBounds(50, 100, 100, 30);
        orderPanel.add(nameLabel);

        JLabel totalQuantityLabel = new JLabel("Cantitate totală:");
        totalQuantityLabel.setBounds(50, 150, 100, 30);
        orderPanel.add(totalQuantityLabel);

        totalQuantityComboBox = new JComboBox<>();
        totalQuantityComboBox.setBounds(200, 150, 200, 30);

        for (int i = 1; i <= 10; i++) {
            totalQuantityComboBox.addItem(i);
        }
        orderPanel.add(totalQuantityComboBox);

        String[] clientName =new String[clientList.size()];
        String[] productName =new String[produsList.size()];
        int i = 0 ;
        for(Client c : clientList){
            clientName[i] = c.getNume() ;
            i++;
        }
        i = 0 ;
        for(Produs c : produsList){
            productName[i] = c.getNume() ;
            i++;
        }
        idComboBox = new JComboBox<>(clientName);
        idComboBox.setBounds(200, 50, 200, 30);
        orderPanel.add(idComboBox);

        nameComboBox = new JComboBox<>(productName);
        nameComboBox.setBounds(200, 100, 200, 30);
        orderPanel.add(nameComboBox);

        addButton2 = new JButton("Adauga comanda");
        addButton2.setBounds(500, 50, 200, 30);
        addButton2.addActionListener(this);
        orderPanel.add(addButton2);

//        delButton2 = new JButton("Sterge comanda");
//        delButton2.setBounds(500, 100, 200, 30);
//        delButton2.addActionListener(this);
//        orderPanel.add(delButton2);
//
//        editButton2 = new JButton("Modifica comanda");
//        editButton2.setBounds(500, 150, 200, 30);
//        editButton2.addActionListener(this);
//
//         orderPanel.add(editButton2);

//        viewButton2 = new JButton("Plaseaza comanda");
//        viewButton2.setBounds(500, 200, 200, 30);
//        orderPanel.add(viewButton2);
        //+ mesaj de eroare daca s mai putine


        model = new DefaultTableModel() ;
        retrieveProperties(new Comanda());
        orderBll = new OrderBll();
        List<Comanda> orderList = orderBll.returnAll();
        for(Comanda c2: orderList){
            String[] s = (String[]) c2.row();
            model.insertRow(model.getRowCount(), c2.row());
        }



        String[] columnNames ={} ;
        orderTable = new JTable(model);
        orderTable.setBounds(50,300 ,800,200);
        JScrollPane sp = new JScrollPane(orderTable);
        sp.setBounds(50,300 ,800,200);
        orderPanel.add(sp);

        orderPanel.setSize(500, 200);

        orderPanel.setVisible(true);

        showPanel(orderPanel);
    }

    /***
     *  Această metodă este utilizată pentru a schimba panoul afișat în prezent în JFrame principal.
     * Implementare: Elimină panoul afișat în prezent (dacă există) și adaugă noul panou, apoi revalidează și repictează JFrame pentru a actualiza afișarea.
     * @param panelToShow
     */

    private void showPanel(JComponent panelToShow) {
        if (currentPanel != null) {
            frame.remove(currentPanel);
        }
        currentPanel = new JPanel(new BorderLayout());
        currentPanel.add(panelToShow, BorderLayout.CENTER);
        frame.add(currentPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    /***
     * Această metodă gestionează acțiunile efectuate când se apasă oricare dintre butoane (de exemplu, adăugarea, ștergerea sau editarea clienților, produselor sau comenzilor).
     * Implementare: Verifică sursa evenimentului (care buton a fost apăsat) și efectuează operațiunea corespunzătoare:
     * Operațiuni pentru Clienți:
     * Adăugare: Creează un nou obiect Client cu datele introduse și îl adaugă folosind ClientBll, apoi actualizează tabelul clienților.
     * Ștergere: Șterge un client pe baza ID-ului introdus și actualizează tabelul clienților.
     * Editare: Actualizează datele unui client pe baza ID-ului introdus și noilor valori, apoi actualizează tabelul clienților.
     * Operațiuni pentru Produse:
     * Adăugare: Creează un nou obiect Produs cu datele introduse și îl adaugă folosind ProductBll, apoi actualizează tabelul produselor.
     * Ștergere: Șterge un produs pe baza ID-ului introdus și actualizează tabelul produselor.
     * Editare: Actualizează datele unui produs pe baza ID-ului introdus și noilor valori, apoi actualizează tabelul produselor.
     * Operațiuni pentru Comenzi:
     * Adăugare: Creează un nou obiect Comanda pe baza clientului, produsului și cantității selectate, apoi îl adaugă folosind OrderBll. De asemenea, generează o factură pentru comandă.
     *
     * @param e the event to be processed
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String nume = nameTextField.getText();
            String email = emailTextField.getText();
            int varsta = Integer.parseInt(ageTextField.getText());
            Client client = new Client(nume, email, varsta);
            clientBll = new ClientBll();
            clientBll.addClient(client);

            model = new DefaultTableModel();
            clientTable.setModel(model);
            retrieveProperties(new Client());
            clientBll = new ClientBll();
            List<Client> clientList = clientBll.returnAll();
            for (Client c2 : clientList) {
                model.insertRow(model.getRowCount(), c2.row());
            }
        }
        if (e.getSource() == delButton) {
            int id = Integer.parseInt(idTextField.getText());
            clientBll.deleteClient(id);
            model = new DefaultTableModel();
            clientTable.setModel(model);
            retrieveProperties(new Client());
            clientBll = new ClientBll();
            List<Client> clientList = clientBll.returnAll();
            for (Client c2 : clientList) {
                model.insertRow(model.getRowCount(), c2.row());
            }
        }
        if (e.getSource() == editButton) {
            int id = Integer.parseInt(idTextField.getText());
            String field1 = newNameTextField.getText();
            String field2 = newEmailTextField.getText();
            clientBll.updateClient(id, field1, field2);
            clientTable.setModel(model);
            model = new DefaultTableModel();
            retrieveProperties(new Client());
            clientBll = new ClientBll();
            List<Client> clientList = clientBll.returnAll();
            for (Client c2 : clientList) {
                model.insertRow(model.getRowCount(), c2.row());
            }
        }

        // product
        if (e.getSource() == addButton1) {
            String nume = nameTextField1.getText();
            int cantitate = Integer.parseInt(quantityTextField1.getText());
            int pret = Integer.parseInt(priceTextField1.getText());
            Produs produs = new Produs(nume, cantitate, pret);
            productBll = new ProductBll();
            productBll.addProdus(produs);

            model = new DefaultTableModel();
            productTable.setModel(model);
            retrieveProperties(new Produs());
            productBll = new ProductBll();
            List<Produs> clientList = productBll.returnAll();
            for (Produs c2 : clientList) {
                model.insertRow(model.getRowCount(), c2.row());
            }
        }
        if (e.getSource() == delButton1) {
            int id = Integer.parseInt(idTextField1.getText());
            productBll.deleteProdus(id);
            model = new DefaultTableModel();
            productTable.setModel(model);
            retrieveProperties(new Produs());
            productBll = new ProductBll();
            List<Produs> clientList = productBll.returnAll();
            for (Produs c2 : clientList) {
                model.insertRow(model.getRowCount(), c2.row());
            }
        }
        if (e.getSource() == editButton1) {
            int id = Integer.parseInt(idTextField1.getText());
            String field1 = newNameTextField1.getText();
            String field2 = newEmailTextField1.getText();
            productBll.update(id, field1, field2);
            productTable.setModel(model);

            model = new DefaultTableModel();
            productTable.setModel(model);
            retrieveProperties(new Produs());
            productBll = new ProductBll();
            List<Produs> clientList = productBll.returnAll();
            for (Produs c2 : clientList) {
                model.insertRow(model.getRowCount(), c2.row());
            }
        }

        if (e.getSource() == addButton2) {
            productBll = new ProductBll();
            int clientId = idComboBox.getSelectedIndex() + 1;
            int productId = nameComboBox.getSelectedIndex() + 1;
            int quantityRequested = totalQuantityComboBox.getSelectedIndex() + 1;
            Produs product = productBll.findProdusById(productId);

            if (product.getCantitate() < quantityRequested) {
                JOptionPane.showMessageDialog(frame, "Stoc epuizat", "Eroare ", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Comanda order = new Comanda(clientId, product.getNume(), quantityRequested);
            OrderBll orderBll1 = new OrderBll();
            orderBll1.addOrder(order);

            model = new DefaultTableModel();
            orderTable.setModel(model);
            retrieveProperties(new Comanda());
            orderBll1 = new OrderBll();
            List<Comanda> orderList = orderBll1.returnAll();
            for (Comanda c2 : orderList) {
                model.insertRow(model.getRowCount(), c2.row());
            }

            int newQuantity = product.getCantitate() - quantityRequested;
            productBll.update(productId, "cantitate", String.valueOf(newQuantity));
            Client client = clientBll.findClientById(clientId);
            try {
                Bill bill = new Bill(client, product, quantityRequested);
            } catch (FileNotFoundException | DocumentException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == delButton2) {
            int id = idComboBox.getSelectedIndex() + 1;
            OrderBll orderBll1 = new OrderBll();
            orderBll1.deleteOrder(id);
            model = new DefaultTableModel();
            orderTable.setModel(model);
            retrieveProperties(new Comanda());
            orderBll1 = new OrderBll();
            List<Comanda> orderList = orderBll1.returnAll();
            for (Comanda c2 : orderList) {
                model.insertRow(model.getRowCount(), c2.row());
            }
        }
    }

}
