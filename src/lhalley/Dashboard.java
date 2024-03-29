// Luke Halley - St. Num: 2007180 - 09/10/2018
// Dist. Systems Assignment 1 JDBC / MySQL File

// Features: 
// All CRUD Functions - Create, Remove, Update, Delete.
// Both navigation features - Next, Previous.
// Clear button.
// Usable GUI.
// Set window size.
// Current employee number indicator.
// Pop up message when Employee is added
// Pop up message when Employee is failed to be added including the error.
// Pop up option when Employee is prompted to be deleted asking the user if they want to go ahead with the remove.
// Pop up message when Employee is deleted sucessfully
// Verification of correct format when Adding Employee using regex.
// Verification of correct format when Updating Employee using regex.
// User is taken to the Employee they just added for convinience
// User is taken to previous Employee when they delete an Employee.
// Fault tolerence in all code.

package lhalley;

// Importing Database Tools
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// Importing GUI Tools
import javax.swing.*;
import java.awt.event.*;

public class Dashboard extends JFrame {

	// Stop serial warning
	private static final long serialVersionUID = 1L;

	// DB configurations
	private final String userName = "root";
	private final String password = "";
	private final String serverName = "localhost";
	private final int portNumber = 3306;
	private final String dbName = "test";
	private final String empTbl = "employee";

	// GUI configs
	private JTextField ssnField;
	private JTextField nameField;
	private JTextField dobField;
	private JTextField addressField;
	private JTextField salaryField;
	private JTextField genderField;

	// Global variables
	int offset = 0;
	int currentEmp = 1;
	String empView = "Viewing Employee Number: ";
	String blank = "";

	// Main class -  Everything is run here
	public Dashboard() throws SQLException {

		// Create labels for all fields
		JLabel lblSsn = new JLabel("SSn (1234)");
		lblSsn.setBounds(10, 44, 156, 14);
		getContentPane().add(lblSsn);

		JLabel lblDob = new JLabel("DOB (yyyy-mm-dd)");
		lblDob.setBounds(10, 75, 156, 14);
		getContentPane().add(lblDob);

		JLabel lblName = new JLabel("Name (John Appleseed)");
		lblName.setBounds(10, 106, 156, 14);
		getContentPane().add(lblName);

		JLabel lblAddress = new JLabel("Address (Cork Rd, Waterford)");
		lblAddress.setBounds(10, 137, 156, 14);
		getContentPane().add(lblAddress);

		JLabel lblSalary = new JLabel("Salary (1234)");
		lblSalary.setBounds(10, 168, 156, 14);
		getContentPane().add(lblSalary);

		JLabel lblGender = new JLabel("Gender (M/F)");
		lblGender.setBounds(10, 199, 156, 14);
		getContentPane().add(lblGender);

		JLabel lblEmployeeDetails = new JLabel("Employee Details");
		lblEmployeeDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmployeeDetails.setBounds(0, 0, 582, 37);
		getContentPane().add(lblEmployeeDetails);

		JTextPane txtpnEmp = new JTextPane();
		txtpnEmp.setBackground(UIManager.getColor("Button.background"));
		txtpnEmp.setText(empView + currentEmp);
		txtpnEmp.setBounds(461, 168, 112, 82);
		getContentPane().add(txtpnEmp);

		// Create text fields for each peice of information about the Employee
		ssnField = new JTextField();
		ssnField.setBounds(176, 41, 275, 20);
		getContentPane().add(ssnField);
		ssnField.setColumns(10);

		dobField = new JTextField();
		dobField.setBounds(176, 72, 275, 20);
		getContentPane().add(dobField);
		dobField.setColumns(10);
		getContentPane().add(dobField);

		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(176, 103, 275, 20);
		getContentPane().add(nameField);

		addressField = new JTextField();
		addressField.setColumns(10);
		addressField.setBounds(176, 134, 275, 20);
		getContentPane().add(addressField);

		salaryField = new JTextField();
		salaryField.setColumns(10);
		salaryField.setBounds(176, 165, 275, 20);
		getContentPane().add(salaryField);

		genderField = new JTextField();
		genderField.setColumns(10);
		genderField.setBounds(176, 196, 275, 20);
		getContentPane().add(genderField);

		// Creating buttons - Next, Previous, Add, Delete, Update & Clear
		JButton btnNext = new JButton("Next");
		btnNext.setBounds(362, 227, 89, 23);
		getContentPane().add(btnNext);

		JButton btnTemp = new JButton("Previous");
		btnTemp.setBounds(263, 227, 89, 23);
		getContentPane().add(btnTemp);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(461, 40, 112, 23);
		getContentPane().add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(461, 71, 112, 23);
		getContentPane().add(btnDelete);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(461, 102, 112, 23);
		getContentPane().add(btnUpdate);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(461, 133, 112, 23);
		getContentPane().add(btnClear);

		// Loading first row of database in, if there is a row available it will fill the fields with the data
		// if there are no rows in the database it will set the fields to be empty allowin the user to then add
		// Employees
		try {
			// Init of connection object
			Connection dbConnection = null;
			// Init of statement object
			Statement statement = null;
			
			// Creating select statement to get the first Employee from the database
			String selectTableSQL = "SELECT * FROM " + empTbl + " ORDER BY id LIMIT 1 OFFSET " + offset;
			
			// Getting the connection
			dbConnection = getConnection();
			// Begin creation of the db statement before executing command
			statement = dbConnection.createStatement();
			
			// Getting the number of rows in the database to be used by the logic below
			Statement s = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet r = s.executeQuery("SELECT * FROM " + empTbl);
			// Goes to the last row
			r.last();
			// Get its row number therefore getting the row count
			int count = r.getRow();
			r.beforeFirst();
			
			// If we have data to place into the fields
			if (count > 0) {
				// Select out first row and put it in a result set
				ResultSet rs = statement.executeQuery(selectTableSQL);
				// While the result set has elements in it
				while (rs.next()) {
					// Appending the data from the result set to individual strings
					String customerSSN = rs.getString("ssn");
					String customerBDATE = rs.getString("bdate");
					String customerName = rs.getString("name");
					String customerAddress = rs.getString("address");
					String customerSex = rs.getString("sex");
					String customerSalary = rs.getString("salary");
					
					// Setting the strings as the text to all our text fields
					ssnField.setText(customerSSN);
					// repaint - repaints the element to ensure out field is updated
					ssnField.repaint();

					dobField.setText(customerBDATE);
					dobField.repaint();

					nameField.setText(customerName);
					nameField.repaint();

					addressField.setText(customerAddress);
					addressField.repaint();

					salaryField.setText(customerSalary);
					salaryField.repaint();

					genderField.setText(customerSex);
					genderField.repaint();

				}

			} else {
				// There is no data to fill so set our fields to blank
				ssnField.setText(blank);

				dobField.setText(blank);

				nameField.setText(blank);

				addressField.setText(blank);

				salaryField.setText(blank);

				genderField.setText(blank);

			}

		} catch (SQLException e) {
			// Catch any errors and display them in a pop on the users screen aswell as in the console
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "Data could not be initialized! Error: " + e.getMessage(), "Initialization Error!",JOptionPane.ERROR_MESSAGE);
			System.err.println("Data could not be initialized! Error: " + e.getMessage());

		}

		// Next and Previous buttons that allow the user to navigate back and fourth between all available Employees including
		// Employees added by the user.
		
		// Next button
		btnNext.addActionListener(new ActionListener() {
			// When the next button is clicked the following will happen
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					Connection dbConnection = null;
					Statement statement = null;

					dbConnection = getConnection();

					Statement s = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ResultSet r = s.executeQuery("SELECT * FROM " + empTbl);
					r.last();
					int count = r.getRow();
					r.beforeFirst();

					statement = dbConnection.createStatement();
					// Offset is a varible I use when getting an specific Employee from the database,
					// below I am incrementing the offset by 1 everytime the next button is clicked therefore moving onto the
					// next employee.
					offset++;

					// Making sure the user cannot go out of bounds which would result in them trying to get an
					// Employee that does not exsist and make an invalid query
					if (offset < count && offset >= 0) {

						String selectTableSQL = "SELECT * FROM " + empTbl + " ORDER BY id LIMIT 1 OFFSET " + offset;

						ResultSet rs = statement.executeQuery(selectTableSQL);

						while (rs.next()) {

							String customerSSN = rs.getString("ssn");
							String customerBDATE = rs.getString("bdate");
							String customerName = rs.getString("name");
							String customerAddress = rs.getString("address");
							String customerSex = rs.getString("sex");
							String customerSalary = rs.getString("salary");

							ssnField.setText(customerSSN);
							ssnField.repaint();

							dobField.setText(customerBDATE);
							dobField.repaint();

							nameField.setText(customerName);
							nameField.repaint();

							addressField.setText(customerAddress);
							addressField.repaint();

							salaryField.setText(customerSalary);
							salaryField.repaint();

							genderField.setText(customerSex);
							genderField.repaint();
							
							// Getting the Employees ID so I can display it in the right hand side
							// allowing the user to see which Employee they are currently viewing
							int customerID = rs.getInt("id");
							txtpnEmp.setText(empView + customerID);
							getContentPane().add(txtpnEmp);

						}
					} else {
						// If the user manages to get out bounds bring them back
						offset--;
					}

				} catch (SQLException e) {
					// Catch any errors and display them in a pop on the users screen aswell as in the console
					final JPanel panel = new JPanel();
					JOptionPane.showMessageDialog(panel, "Error selecting the next Employee! Error: " + e.getMessage(), "Navigation Error!",JOptionPane.ERROR_MESSAGE);
					System.err.println("Error selecting the next Employee! Error: " + e.getMessage());
				}

				// Previous button - very simular to the next button except backwards
				JButton btnPrevious = new JButton("Previous");
				btnPrevious.setBounds(263, 227, 89, 23);
				// Removes temporary previous button I place in the GUI at the start of the program due to small bug
				// where the previous button does not appear unless the previous button is moused over.
				getContentPane().remove(btnTemp);
				getContentPane().add(btnPrevious);
				btnPrevious.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {

						try {
							Connection dbConnection = null;
							Statement statement = null;

							dbConnection = getConnection();

							Statement s = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
							ResultSet r = s.executeQuery("SELECT * FROM " + empTbl);
							r.last();
							int count = r.getRow();
							r.beforeFirst();
							
							// decrement the offset by 1 everytime the previous button is clicked therefore moving back to the
							// previous employee.
							offset--;

							if (offset < count && offset >= 0) {

								String selectTableSQL = "SELECT * FROM " + empTbl + " ORDER BY id LIMIT 1 OFFSET " + offset;

								statement = dbConnection.createStatement();

								ResultSet rs = statement.executeQuery(selectTableSQL);

								while (rs.next()) {

									String customerSSN = rs.getString("ssn");
									String customerBDATE = rs.getString("bdate");
									String customerName = rs.getString("name");
									String customerAddress = rs.getString("address");
									String customerSex = rs.getString("sex");
									String customerSalary = rs.getString("salary");

									ssnField.setText(customerSSN);
									ssnField.repaint();

									dobField.setText(customerBDATE);
									dobField.repaint();

									nameField.setText(customerName);
									nameField.repaint();

									addressField.setText(customerAddress);
									addressField.repaint();

									salaryField.setText(customerSalary);
									salaryField.repaint();

									genderField.setText(customerSex);
									genderField.repaint();

									int customerID = rs.getInt("id");
									txtpnEmp.setText(empView + customerID);
									getContentPane().add(txtpnEmp);

								}

							} else {
								// If the user manages to get out bounds bring them back
								offset++;
							}

						} catch (SQLException e) {
							// Catch any errors and display them in a pop on the users screen aswell as in the console
							final JPanel panel = new JPanel();
							JOptionPane.showMessageDialog(panel, "Error moving to the previous Employee! Error: " + e.getMessage(), "Navigation Error!",JOptionPane.ERROR_MESSAGE);
							System.err.println("Error moving to the previous Employee! Error: " + e.getMessage());

						}

					}

				});
			}
		});

		// Add button which allows a user to add an Employee to the database which can then be viewed
		// in the GUI
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String currentSSN = ssnField.getText();
				String currentDOB = dobField.getText();
				String currentName = nameField.getText();
				String currentAdd = addressField.getText();
				String currentSalary = salaryField.getText();
				String currentGender = genderField.getText();

				// Check all dates are valid and in the format they should be using regex expressions with boolean logic.
				// ".*\\d+.*" = check its a number - 1234
				// "([0-9]{4})-([0-9]{2})-([0-9]{2})" checks the date is in the correct format - 2018-01-01
				// "[a-zA-z]{1}" checks if its a character - M or F
				if (currentSSN.matches(".*\\d+.*")
						&& !currentName.matches(".*\\d+.*") 
						&& currentDOB.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")
						&& currentSalary.matches(".*\\d+.*") 
						&& currentGender.matches("[a-zA-z]{1}")) {
					try {
						Connection dbConnection = null;
						Statement statement = null;

						dbConnection = getConnection();

						Statement s = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						ResultSet r = s.executeQuery("SELECT * FROM " + empTbl);
						r.last();
						int count = r.getRow();
						r.beforeFirst();
						// ID is created by adding 1 to the count
						int id = count + 1;

						String selectTableSQL = "INSERT INTO " + empTbl + " VALUES (" + id + ", " + currentSSN + ", "
								+ "'" + currentDOB + "'" + ", " + "'" + currentName + "'" + ", " + "'" + currentAdd
								+ "'" + ", " + "'" + currentGender + "'" + ", " + currentSalary + ")";

						dbConnection = getConnection();
						statement = dbConnection.createStatement();

						// Adds the Employee to the DB
						statement.executeUpdate(selectTableSQL);

						// Gets the last row - ie the row the user just added
						String getLast = "SELECT * FROM " + empTbl + " ORDER BY ID DESC LIMIT 1";
						
						// Setting the current employee to the newely created one.
						offset = count;
						int current = count + 1;
						txtpnEmp.setText(empView + current);
						txtpnEmp.repaint();

						ResultSet x = statement.executeQuery(getLast);

						while (x.next()) {

							String customerSSN = x.getString("ssn");
							String customerBDATE = x.getString("bdate");
							String customerName = x.getString("name");
							String customerAddress = x.getString("address");
							String customerSex = x.getString("sex");
							String customerSalary = x.getString("salary");

							ssnField.setText(customerSSN);
							ssnField.repaint();

							dobField.setText(customerBDATE);
							dobField.repaint();

							nameField.setText(customerName);
							nameField.repaint();

							addressField.setText(customerAddress);
							addressField.repaint();

							salaryField.setText(customerSalary);
							salaryField.repaint();

							genderField.setText(customerSex);
							genderField.repaint();

							// Pop up to let the user know that the new Employee has been added
							final JPanel panel = new JPanel();
							JOptionPane.showMessageDialog(panel,
									"Employee: " + customerName + " has been added sucessfully!",
									"Employee Add Sucessful!", JOptionPane.INFORMATION_MESSAGE);

						}

					} catch (SQLException error) {
						final JPanel panel = new JPanel();
						JOptionPane.showMessageDialog(panel, "Error adding Employee! Error: " + error.getMessage(), "Navigation Error!",JOptionPane.ERROR_MESSAGE);
						System.err.println("Error adding Employee! Error: " + error.getMessage());

					}
				} else {
					// Pop up window if the user enters any field incorrectly
					final JPanel panel = new JPanel();
					JOptionPane.showMessageDialog(panel, "Please enter all fields in the correct!", "Error",JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		
		// Allows the user to remove an Employee
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// A confirm pop to make sure the user wants to remove the user. If yes is pressed the Employee is
				// removed from the database, if No is pressed nothing is done.
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this employee?",
						"Warning", dialogButton);
				if (dialogResult == JOptionPane.YES_OPTION) {
					// Gets the current SSN to be used with logic below.
					String currentSSN = ssnField.getText();
					try {
						Connection dbConnection = null;
						Statement statement = null;

						dbConnection = getConnection();

						// Find the Employee by its SSN as this is a unique field.
						String deleteBySSN = "DELETE FROM " + empTbl + " WHERE ssn=" + "'" + currentSSN + "'";

						dbConnection = getConnection();
						statement = dbConnection.createStatement();
						statement.executeUpdate(deleteBySSN);
						Statement s = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
						ResultSet r = s.executeQuery("SELECT * FROM " + empTbl);
						r.last();
						int count = r.getRow();
						r.beforeFirst();

						String getLast = "SELECT * FROM " + empTbl + " ORDER BY ID DESC LIMIT 1";

						// Update the current employee view.
						offset = count;
						int current = count + 1;
						txtpnEmp.setText(empView + current);
						txtpnEmp.repaint();

						ResultSet x = statement.executeQuery(getLast);

						while (x.next()) {

							String customerSSN = x.getString("ssn");
							String customerBDATE = x.getString("bdate");
							String customerName = x.getString("name");
							String customerAddress = x.getString("address");
							String customerSex = x.getString("sex");
							String customerSalary = x.getString("salary");

							ssnField.setText(customerSSN);
							ssnField.repaint();

							dobField.setText(customerBDATE);
							dobField.repaint();

							nameField.setText(customerName);
							nameField.repaint();

							addressField.setText(customerAddress);
							addressField.repaint();

							salaryField.setText(customerSalary);
							salaryField.repaint();

							genderField.setText(customerSex);
							genderField.repaint();

							// Pop up to notify the user that the Employee has been deleted
							final JPanel panel = new JPanel();
							JOptionPane.showMessageDialog(panel, "Employee: has been deleted sucessfully!","Employee Delete Sucessful!", JOptionPane.INFORMATION_MESSAGE);
						}

					} catch (SQLException error) {
						final JPanel panel = new JPanel();
						JOptionPane.showMessageDialog(panel, "Error deleting Employee! Error: " + error.getMessage(), "Navigation Error!",JOptionPane.ERROR_MESSAGE);
						System.err.println("Error deleting Employee! Error: " + error.getMessage());
					}
				}
			}
		});

		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Get all the values which are currently in the fields which will be used
				// for the update of the Employee.
				String currentSSN = ssnField.getText();
				String currentDOB = dobField.getText();
				String currentName = nameField.getText();
				String currentAdd = addressField.getText();
				String currentSalary = salaryField.getText();
				String currentGender = genderField.getText();
			
				// Check all dates are valid and in the format they should be using regex expressions with boolean logic.
				// ".*\\d+.*" = check its a number - 1234
				// "([0-9]{4})-([0-9]{2})-([0-9]{2})" checks the date is in the correct format - 2018-01-01
				// "[a-zA-z]{1}" checks if its a character - M or F
				if (currentSSN.matches(".*\\d+.*") 
						&& !currentName.matches(".*\\d+.*") 
						&& currentDOB.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")
						&& currentSalary.matches(".*\\d+.*") 
						&& currentGender.matches("[a-zA-z]{1}")) {

					try {
						Connection dbConnection = null;
						Statement statement = null;

						dbConnection = getConnection();
						statement = dbConnection.createStatement();

						String selectTableSQL = "SELECT * FROM " + empTbl + " ORDER BY id LIMIT 1 OFFSET " + offset;

						ResultSet rs = statement.executeQuery(selectTableSQL);

						rs.next();
						
						// Creating update query string
						String updateEmp = "UPDATE " + empTbl + " SET ssn = " + "'" + currentSSN + "'" + ", bdate = "
								+ "'" + currentDOB + "'" + ", name = " + "'" + currentName + "'" + ", address = " + "'"
								+ currentAdd + "'" + ", sex = " + "'" + currentGender + "'" + ", salary = " + "'"
								+ currentSalary + "'" + " WHERE ssn = " + "'" + rs.getString("ssn") + "'";

						// Updating Employee
						statement.executeUpdate(updateEmp);

						// Pop up to notify the user the Employee was updated.
						final JPanel panel = new JPanel();
						JOptionPane.showMessageDialog(panel,
								"Employee: " + currentName + " has been updated sucessfully!",
								"Employee Update Sucessful!", JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException error) {
						final JPanel panel = new JPanel();
						JOptionPane.showMessageDialog(panel, "Error updating Employee! Error: " + error.getMessage(), "Navigation Error!",JOptionPane.ERROR_MESSAGE);
						System.err.println("Error updating Employee! Error: " + error.getMessage());

					}
				} else {
					final JPanel panel = new JPanel();
					JOptionPane.showMessageDialog(panel, "Please enter all fields in the correct!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		
		// Clear button which simply sets all the text fields empty if the use presses the button
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ssnField.setText(blank);
				dobField.setText(blank);
				nameField.setText(blank);
				addressField.setText(blank);
				salaryField.setText(blank);
				genderField.setText(blank);
			}

		});

		// Sets the layout
		getContentPane().setLayout(null);
	}
	
	// Function which is used to get a connection to the database
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		// Setting credentials
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);
		// Getting the connection using jdbc
		conn = DriverManager.getConnection("jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/" + this.dbName
				+ "?verifyServerCertificate=false&useSSL=true", connectionProps);
		return conn;
	}
	
	// Function for executing a query or update to the database
	public boolean executeUpdate(Connection conn, String command) throws SQLException {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(command); // This will throw a SQLException if it fails
			return true;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
	// Function to initialize the database.
	public void createInitTables() {

		// Connect to MySQL
		Connection conn = null;
		try {
			conn = this.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		// Create the employee table if it doesnt already.
		String employeeString = "CREATE TABLE IF NOT EXISTS " + this.empTbl + " ( " + "id INTEGER NOT NULL, "
				+ "ssn int(9) NOT NULL, " + "bdate date NOT NULL, " + "name varchar(80) NOT NULL, "
				+ "address varchar(160) NOT NULL, " + "sex char(1) NOT NULL, " + "worksfor int(3) NOT NULL, "
				+ "supervises int(3) NOT NULL, " + "PRIMARY KEY (id))";
		String[] tables = { employeeString };

		for (String s : tables) {
			// Create the project table
			try {
				this.executeUpdate(conn, s);
			} catch (SQLException e) {
				System.err.println("ERROR: Could not create table");
				e.printStackTrace();
				return;
			}
		}
		// Make the SSN field unique as it is used in some logic like the delete function.
		String ssnUnique = "ALTER TABLE " + this.empTbl + " ADD UNIQUE(`ssn`)";
		Statement statement = null;
		try {
			statement = conn.createStatement();
			statement.executeUpdate(ssnUnique);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	// Main function which starts the app aswell as sets the size and make the window non-resizable.
	public static void main(String[] args) throws SQLException {
		Dashboard app = new Dashboard();
		app.createInitTables();
		Dashboard f = new Dashboard();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setSize(598, 303);
		f.setResizable(false);
		f.setVisible(true);
	}

}