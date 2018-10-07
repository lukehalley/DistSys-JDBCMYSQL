package distsysLab01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// GUI
import javax.swing.*;

import java.awt.event.*;

@SuppressWarnings("serial")
public class dbDemo extends JFrame {

    // DB
    private final String userName = "root";
    private final String password = "";
    private final String serverName = "localhost";
    private final int portNumber = 3306;
    private final String dbName = "assignment1";
    private final String empTbl = "employee";
    private JTextField ssnField;
    private JTextField nameField;
    private JTextField dobField;
    private JTextField addressField;
    private JTextField salaryField;
    private JTextField genderField;
	int offset = 0;
	int currentEmp = 1;
	String empView = "Viewing Employee Number: ";
    String blank = "";

    // GUI

    public dbDemo() throws SQLException {

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
        
        JButton btnNext = new JButton("Next");
        btnNext.setBounds(362, 227, 89, 23);
        getContentPane().add(btnNext);
        
        JButton btnPrevious = new JButton("Previous");
        btnPrevious.setBounds(263, 227, 89, 23);
        getContentPane().add(btnPrevious);
        
        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(461, 40, 112, 23);
        getContentPane().add(btnAdd);

        // Loading first row
        try {
            Connection dbConnection = null;
            Statement statement = null;

            String selectTableSQL = "SELECT * FROM " + empTbl + " ORDER BY id LIMIT 1 OFFSET " + offset;
            System.out.println("SENDING THIS: " + selectTableSQL);

            dbConnection = getConnection();
            statement = dbConnection.createStatement();
            
            Statement s = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet r = s.executeQuery("SELECT * FROM " + empTbl);
            r.last();
            int count = r.getRow();
            r.beforeFirst();
            
            if (count > 0) {
            	
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

                }
            	
            } else {

                ssnField.setText(blank);
                
                dobField.setText(blank);
                
                nameField.setText(blank);
                
                addressField.setText(blank);

                salaryField.setText(blank);

                genderField.setText(blank);
                
            	
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }
        
        // Next and Previous buttons

        btnNext.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {

                try {
                    Connection dbConnection = null;
                    Statement statement = null;

                    dbConnection = getConnection();
                    
                    Statement s = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                        ResultSet r = s
                            .executeQuery("SELECT * FROM " + empTbl);
                        r.last();
                        int count = r.getRow();
                        r.beforeFirst();
                        
                        System.out.println("COUNT: " + count);
                    
                    statement = dbConnection.createStatement();

                    offset++;

                    if (offset < count && offset >= 0){
                    	
                        String selectTableSQL = "SELECT * FROM " + empTbl + " ORDER BY id LIMIT 1 OFFSET " + offset;
                        System.out.println("SENDING THIS: " + selectTableSQL);
                    
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
                    	offset--;
                    }

                } catch (SQLException e) {

                    System.out.println(e.getMessage());

                }

                System.out.println("OFFSET: " + offset);


                btnPrevious.addActionListener(new ActionListener() {

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
                                System.out.println("COUNT: " + count);

                            	offset--;
                                
                                if (offset < count && offset >= 0){
                    	
	                                String selectTableSQL = "SELECT * FROM " + empTbl + " ORDER BY id LIMIT 1 OFFSET " + offset;
	                                System.out.println("SENDING THIS: " + selectTableSQL);
	                            	
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
                            	offset++;
                            }

                        } catch (SQLException e) {

                            System.out.println(e.getMessage());

                        }
                        
                        System.out.println("OFFSET: " + offset);

                    }

                });
            }
        });

        // Add button
        btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String currentSSN = ssnField.getText();
				String currentDOB = dobField.getText();
				String currentName = nameField.getText();
				String currentAdd = addressField.getText();
				String currentSalary = salaryField.getText();
				String currentGender = genderField.getText();

				// Check Date format is valid.
				if (currentSSN.matches(".*\\d+.*") && !currentAdd.matches(".*\\d+.*") && !currentName.matches(".*\\d+.*") && currentDOB.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})") && currentSalary.matches(".*\\d+.*") && currentGender.matches("[a-zA-z]{1}")) {
					try {
	                    Connection dbConnection = null;
	                    Statement statement = null;

	                    dbConnection = getConnection();
	                    
	                    Statement s = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	                        ResultSet r = s.executeQuery("SELECT * FROM " + empTbl);
	                        r.last();
	                        int count = r.getRow();
	                        r.beforeFirst();
	                        System.out.println(count);
	                        int id = count + 1;
	                        System.out.println("ID: " + id + " Count: " + count);

			            String selectTableSQL = 
			            		"INSERT INTO " + empTbl + " VALUES (" 
			            				+ id + ", " + currentSSN + ", " + "'" + currentDOB + "'" + ", " 
			            				+ "'" + currentName + "'" + ", " + "'" + currentAdd + "'" + ", " 
			            				+ "'" + currentGender + "'" + ", " + currentSalary + ")";

			            dbConnection = getConnection();
			            statement = dbConnection.createStatement();

			            statement.executeUpdate(selectTableSQL);
			            
			            String getLast = "SELECT * FROM " + empTbl + " ORDER BY ID DESC LIMIT 1";
			            System.out.println("SENDING THIS: " + getLast);
			            
						offset = count;
						int current = count + 1;
						System.out.println("CURRENT: " + current);
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
	                        
	                        System.out.println("CURRENT EMP: " + currentEmp);
	                        
							final JPanel panel = new JPanel();
							JOptionPane.showMessageDialog(panel, "Employee: " + customerName + " has been added sucessfully!", "Employee Add Sucessful!", JOptionPane.INFORMATION_MESSAGE);
	                        
	                    }

			        } catch (SQLException error) {

			            System.out.println(error.getMessage());

			        }
				}
				
				else {
					final JPanel panel = new JPanel();
					JOptionPane.showMessageDialog(panel, "Please enter all fields in the correct!", "Error", JOptionPane.ERROR_MESSAGE);
				}


			}
        });

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(461, 71, 112, 23);
        getContentPane().add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			    int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to delete this employee?","Warning", dialogButton);
				if(dialogResult == JOptionPane.YES_OPTION){
					String currentSSN = ssnField.getText();
					try {
	                    Connection dbConnection = null;
	                    Statement statement = null;

	                    dbConnection = getConnection();
	                    
			            String deleteBySSN = "DELETE FROM " + empTbl + " WHERE ssn=" + "'" + currentSSN  + "'";

			            dbConnection = getConnection();
			            statement = dbConnection.createStatement();
			            statement.executeUpdate(deleteBySSN);
			            
	                    Statement s = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	                    ResultSet r = s.executeQuery("SELECT * FROM " + empTbl);
	                    r.last();
	                    int count = r.getRow();
	                    r.beforeFirst();
	                    System.out.println(count);
	                    int id = count + 1;
	                    System.out.println("ID: " + id + " Count: " + count);
			            
			            String getLast = "SELECT * FROM " + empTbl + " ORDER BY ID DESC LIMIT 1";
			            System.out.println("SENDING THIS: " + getLast);
			            
						offset = count;
						int current = count + 1;
						System.out.println("CURRENT: " + current);
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
	                        
	                        System.out.println("CURRENT EMP: " + currentEmp);
	                        
							final JPanel panel = new JPanel();
							JOptionPane.showMessageDialog(panel, "Employee: has been deleted sucessfully!", "Employee Delete Sucessful!", JOptionPane.INFORMATION_MESSAGE);
	                        
	                    }

			        } catch (SQLException error) {

			            System.out.println(error.getMessage());

			        }
				}
			}
        });

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(461, 102, 112, 23);
        getContentPane().add(btnUpdate);
        btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String currentSSN = ssnField.getText();
				String currentDOB = dobField.getText();
				String currentName = nameField.getText();
				String currentAdd = addressField.getText();
				String currentSalary = salaryField.getText();
				String currentGender = genderField.getText();
				
				if (currentSSN.matches(".*\\d+.*") && !currentAdd.matches(".*\\d+.*") && !currentName.matches(".*\\d+.*") && currentDOB.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})") && currentSalary.matches(".*\\d+.*") && currentGender.matches("[a-zA-z]{1}")) {
	
					try {
		                Connection dbConnection = null;
		                Statement statement = null;
		
		                dbConnection = getConnection();
			            statement = dbConnection.createStatement();
			            
	                    String selectTableSQL = "SELECT * FROM " + empTbl + " ORDER BY id LIMIT 1 OFFSET " + offset;
	                    System.out.println("SENDING THIS: " + selectTableSQL);
	                    
	                    System.out.println("Offset: " + offset);
	
	                    ResultSet rs = statement.executeQuery(selectTableSQL);
	                    
	                    rs.next();
	                    
	                    System.out.println("BEFORE");
			            String updateEmp = "UPDATE " + empTbl 
			            		+ " SET ssn = " + "'" + currentSSN + "'"
			            		+ ", bdate = " + "'" + currentDOB + "'"
			            		+ ", name = " + "'" + currentName + "'"
			            		+ ", address = " + "'" + currentAdd + "'"
			            		+ ", sex = " + "'" + currentGender + "'"
			            		+ ", salary = " + "'" + currentSalary + "'"
			            		+ " WHERE ssn = " + "'" + rs.getString("ssn") + "'";
			            System.out.println("SENDING THIS: " + updateEmp);
			            System.out.println("AFTER");
						 
						statement.executeUpdate(updateEmp);
						
						final JPanel panel = new JPanel();
						JOptionPane.showMessageDialog(panel, "Employee: " + currentName + " has been updated sucessfully!", "Employee Update Sucessful!", JOptionPane.INFORMATION_MESSAGE);
			        } catch (SQLException error) {
		
			            System.out.println(error.getMessage());
		
			        }
				} else {
					final JPanel panel = new JPanel();
					JOptionPane.showMessageDialog(panel, "Please enter all fields in the correct!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		
        });

        // Clear Button
        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(461, 133, 112, 23);
        getContentPane().add(btnClear);
        btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                ssnField.setText(blank);
                dobField.setText(blank);
                nameField.setText(blank);
                addressField.setText(blank);
                salaryField.setText(blank);
                genderField.setText(blank);
			}
        	
        });
        
        
        getContentPane().setLayout(null);
    }

    public Connection getConnection() throws SQLException {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        conn = DriverManager.getConnection("jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/" + this.dbName + "?verifyServerCertificate=false&useSSL=true", connectionProps);
        return conn;
    }

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

    public void createInitTables() {

        // Connect to MySQL
        Connection conn = null;
        try {
            conn = this.getConnection();
            System.out.println("Connected to the" + dbName + " database");
        } catch (SQLException e) {
            System.out.println("ERROR: Could not connect to the" + dbName + " database");
            e.printStackTrace();
            return;
        }

        String employeeString =
            "CREATE TABLE IF NOT EXISTS " + this.empTbl + " ( " +
            "id INTEGER NOT NULL, " +
            "ssn int(9) NOT NULL, " +
            "bdate date NOT NULL, " +
            "name varchar(80) NOT NULL, " +
            "address varchar(160) NOT NULL, " +
            "sex char(1) NOT NULL, " +
            "worksfor int(3) NOT NULL, " +
            "supervises int(3) NOT NULL, " +
            "PRIMARY KEY (id))";

        

        String[] tables = {
            employeeString
        };

        for (String s: tables) {
            // Create the project table
            try {
                this.executeUpdate(conn, s);
                System.out.println("Created a table");
            } catch (SQLException e) {
                System.out.println("ERROR: Could not create table");
                e.printStackTrace();
                return;
            }
        }
        
        String ssnUnique = "ALTER TABLE " + this.empTbl + " ADD UNIQUE(`ssn`)";
        Statement statement = null;
        try {
			statement = conn.createStatement();
			statement.executeUpdate(ssnUnique);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        

    }

    public void dropTable(String table) {

        // Connect to MySQL
        Connection conn = null;
        try {
            conn = this.getConnection();
            System.out.println("Connected to the" + dbName + " database");
        } catch (SQLException e) {
            System.out.println("ERROR: Could not connect to the" + dbName + " database");
            e.printStackTrace();
            return;
        }

        try {
            String dropString = "DROP TABLE " + table;
            this.executeUpdate(conn, dropString);
            System.out.println("Dropped " + table);
        } catch (SQLException e) {
            System.out.println("ERROR: Could not drop " + table);
            e.printStackTrace();
            return;
        }

    }

    public static void main(String[] args) throws SQLException {
        dbDemo app = new dbDemo();
        app.createInitTables();
        dbDemo f = new dbDemo();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setSize(598, 303);
        f.setResizable(false);
        f.setVisible(true);
    }

}