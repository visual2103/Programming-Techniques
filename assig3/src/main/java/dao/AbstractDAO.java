package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

import javax.swing.*;


public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;


	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/***
	 *Această metodă creează și returnează o interogare SQL pentru a selecta toate câmpurile din tabela asociată clasei generice bazate pe valoarea dată a câmpului specificat.
	 * @param *primeste un camp din tabela SQL
	 * @return String
	 */

	private String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}

	/***
	 * Această metodă returnează o listă de toate entitățile din tabela asociată clasei generice.
	 * @return o lista cu toate entitatile tabelei SQL
	 */
	public List<T> findAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM " + type.getSimpleName();
		List<T> entities = new ArrayList<>();
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);

			resultSet = statement.executeQuery();
			entities = createObjects(resultSet);

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return entities;
	}

	/***
	 *  Această metodă găsește și returnează o entitate din tabela asociată clasei generice bazată pe id-ul dat.
	 * @param id
	 * @return entitate din tabela SQL
	 */
	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			List<T> results = createObjects(resultSet);
			if (!results.isEmpty()) {
				return results.get(0);
			} else {

				return null;
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	/***
	 * Această metodă primește un obiect ResultSet și creează și returnează o listă de obiecte de tipul clasei generice, folosind datele din ResultSet.
	 * @param resultSet
	 * @return lista de obiecte generice
	 */
	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		Constructor[] ctors = type.getDeclaredConstructors();
		Constructor ctor = null;
		for (int i = 0; i < ctors.length; i++) {
			ctor = ctors[i];
			if (ctor.getGenericParameterTypes().length == 0)
				break;
		}
		try {
			while (resultSet.next()) {
				ctor.setAccessible(true);
				T instance = (T)ctor.newInstance();
				for (Field field : type.getDeclaredFields()) {
					String fieldName = field.getName();
					Object value = resultSet.getObject(fieldName);
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				// Verificați dacă obiectul este deja prezent în listă înainte de adăugare
				if (!list.contains(instance)) {
					list.add(instance);
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}

	/***
	 *  Această metodă inserează o nouă entitate în tabela asociată clasei generice folosind obiectul dat și returnează același obiect.
	 * @param t
	 * @return obiect de tip generic
	 */
	public T insert(T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "INSERT INTO " + type.getSimpleName() + " (";

		// Append the column names except for the ID column (if it's autoincrement)
		// Adjust this part based on your table schema
		StringBuilder columnsBuilder = new StringBuilder();
		StringBuilder valuesBuilder = new StringBuilder();

		Field[] fields = type.getDeclaredFields();
		for (Field field : fields) {
			// Exclude the field if it's an auto-incremented ID or if it's static
			if (field.getName().equals("id") || Modifier.isStatic(field.getModifiers())) {
				continue;
			}

			columnsBuilder.append(field.getName()).append(", ");
			valuesBuilder.append("?, ");
		}

		// Remove the trailing comma and space
		columnsBuilder.setLength(columnsBuilder.length() - 2);
		valuesBuilder.setLength(valuesBuilder.length() - 2);

		query += columnsBuilder.toString() + ") VALUES (" + valuesBuilder.toString() + ")";

		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			int parameterIndex = 1;
			for (Field field : fields) {
				if (field.getName().equals("id") || Modifier.isStatic(field.getModifiers())) {
					continue;
				}

				field.setAccessible(true);
				Object value = field.get(t);
				statement.setObject(parameterIndex++, value);
			}

			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Insertion failed, no rows affected.");
			}

			// Retrieve the generated keys if needed
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				// You might need to set the generated key to your entity if necessary
				// For example:
				// t.setId(resultSet.getInt(1));
			} else {
				throw new SQLException("Insertion failed, no ID obtained.");
			}
		} catch (SQLException | IllegalAccessException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return t;
	}

	/***
	 * Această metodă construiește un șir de caractere care reprezintă o actualizare specifică bazată pe valorile specificate și poate fi utilizată pentru a actualiza un câmp specific al unei entități.
	 * @param id
	 * @param s1
	 * @param s2
	 */
	   public void update(int id,String s1, String s2) {
		StringBuilder x = new StringBuilder();
		x.append(" " + s1 + "=\"" + s2 + "\"");
		//clientDAO.updateById(id,x.toString());
	   }

	/***
	 * Această metodă creează și returnează o interogare SQL pentru a actualiza un câmp specific al unei entități bazată pe numele câmpului și valoarea specificată.
	 * @param field
	 * @param value
	 * @return
	 */
	/***
	 *  Această metodă actualizează o entitate din tabela asociată clasei generice bazată pe id-ul dat și valoarea specificată.
	 * @param field
	 * @param value
	 * @return
	 */
	private String createUpdateQuery(String field, String value) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(type.getSimpleName());
		sb.append(" SET " + value + " ");
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}
	public void updateById(int id,String value) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createUpdateQuery("id", value);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			int y = statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
	}

	/***
	 * Această metodă șterge o entitate din tabela asociată clasei generice bazată pe id-ul dat și aruncă o excepție NoSuchElementException dacă entitatea nu este găsită.
	 * @param id
	 * @throws NoSuchElementException
	 */
		public void delete ( int id) throws NoSuchElementException {
			Connection connection = null;
			PreparedStatement statement = null;
			String query = "DELETE FROM " + type.getSimpleName() + " WHERE id = ?";
			try {
				connection = ConnectionFactory.getConnection();
				statement = connection.prepareStatement(query);
				statement.setInt(1, id);

				int affectedRows = statement.executeUpdate();
				if (affectedRows == 0) {
					throw new NoSuchElementException("Clientul cu id-ul " + id + " nu există în tabel.");
				}
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteById " + e.getMessage());
			} finally {
				ConnectionFactory.close(statement);
				ConnectionFactory.close(connection);
			}
		}

	}
