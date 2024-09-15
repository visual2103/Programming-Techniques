package start;

import bll.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import model.Client;
import model.Comanda;
import model.Produs;
import presentation.View ;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	public static void main(String[] args) throws SQLException, FileNotFoundException, DocumentException {

		ClientBll clientBll = new ClientBll();
		ProductBll productBll = new ProductBll();
		OrderBll orderBll = new OrderBll();

		Client client = null;
//		Client newClient = new Client("Dari" , "dari@yahoo.com ", 21);
//		Comanda comanda1 = null;
//		Comanda newOrder = new Comanda("visine" , 10) ;
//
//		List<Client> clientList = null;
//		List<Comanda> comandaList = null;
//
//		Produs produs1 = null;
//		Produs produs2 = null ;
//		Produs newProduct = new Produs("capsuni" ,10,12);
//
//		Bill bill = new Bill(newClient , newProduct , 10);

		try {
//			List<Produs> produsList = productBll.returnAll();
//			for(Produs i : produsList){
//				System.out.println(i);
//			}
//			Produs produs = productBll.findProdusById(1);
//			System.out.println(produs);

//
			//clientBll.deleteClient(16);
//			System.out.println(client);
//
//			comanda1 = orderBll.findOrderById(2);
//			System.out.println(comanda1);
//
//			clientList = clientBll.returnAll();
//			comandaList = orderBll.returnAll();
//
//			productBll.addProduct(newProduct);
//			productBll.deleteProduct(5);
//
//			orderBll.addOrder(newOrder);

			//clientBll.updateClient(newClient);
//			clientBll.update(4,"nume" , "francesco");
//			productBll.updateProduct(8,"cantitate" ,"34");
//			orderBll.updateOrder(3,"cantitate_totala" ,"888");


		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "An error occurred: " + ex.getMessage());
		}

//		if (comandaList != null) {
//			ReflectionExample.retrievePropertiesFromSQL(Comanda.class);
//		} else {
//			LOGGER.log(Level.INFO, "Lista de comenzi este goală");
//		}

//		if (client != null) {
//			ReflectionExample.retrievePropertiesFromSQL(Client.class);
//		} else {
//			LOGGER.log(Level.INFO, "Lista de clienți este goală");
//		}



		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				View view = new View();
				view.createAndShowGUI();
			}
		});


	}
}
