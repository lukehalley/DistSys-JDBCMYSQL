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
    private JTextField dobField;
    private JTextField nameField;
    private JTextField addressField;
    private JTextField salaryField;
    private JTextField genderField;
	int offset = 0;

    // GUI

    public dbDemo() throws SQLException {

        JLabel lblSsn = new JLabel("SSn");
        lblSsn.setBounds(10, 44, 72, 14);
        getContentPane().add(lblSsn);

        JLabel lblDob = new JLabel("DOB");
        lblDob.setBounds(10, 75, 72, 14);
        getContentPane().add(lblDob);

        JLabel lblName = new JLabel("Name");
        lblName.setBounds(10, 106, 72, 14);
        getContentPane().add(lblName);

        JLabel lblAddress = new JLabel("Address");
        lblAddress.setBounds(10, 137, 72, 14);
        getContentPane().add(lblAddress);

        JLabel lblSalary = new JLabel("Salary");
        lblSalary.setBounds(10, 168, 72, 14);
        getContentPane().add(lblSalary);

        JLabel lblGender = new JLabel("Gender");
        lblGender.setBounds(10, 199, 72, 14);
        getContentPane().add(lblGender);

        JLabel lblEmployeeDetails = new JLabel("Employee Details");
        lblEmployeeDetails.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmployeeDetails.setBounds(0, 0, 434, 37);
        getContentPane().add(lblEmployeeDetails);

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

                    ssnField = new JTextField();
                    ssnField.setBounds(87, 41, 215, 20);
                    getContentPane().add(ssnField);
                    ssnField.setColumns(10);
                    ssnField.setText(customerSSN);
                    
                    dobField = new JTextField();
                    dobField.setColumns(10);
                    dobField.setBounds(87, 72, 215, 20);
                    getContentPane().add(dobField);
                    dobField.setText(customerBDATE);
                    
                    nameField = new JTextField();
                    nameField.setColumns(10);
                    nameField.setBounds(87, 103, 215, 20);
                    getContentPane().add(nameField);
                    nameField.setText(customerName);
                    
                    addressField = new JTextField();
                    addressField.setColumns(10);
                    addressField.setBounds(87, 134, 215, 20);
                    getContentPane().add(addressField);
                    addressField.setText(customerAddress);
                    
                    salaryField = new JTextField();
                    salaryField.setColumns(10);
                    salaryField.setBounds(87, 165, 215, 20);
                    getContentPane().add(salaryField);
                    salaryField.setText(customerSalary);
                    
                    genderField = new JTextField();
                    genderField.setColumns(10);
                    genderField.setBounds(87, 196, 215, 20);
                    getContentPane().add(genderField);
                    genderField.setText(customerSex);
                    

                }
            	
            } else {
            	
                String customerSSN = "";
                String customerBDATE = "";
                String customerName = "";
                String customerAddress = "";
                String customerSex = "";
                String customerSalary = "";

                ssnField = new JTextField();
                ssnField.setBounds(87, 41, 215, 20);
                getContentPane().add(ssnField);
                ssnField.setColumns(10);
                ssnField.setText(customerSSN);
                

                dobField = new JTextField();
                dobField.setColumns(10);
                dobField.setBounds(87, 72, 215, 20);
                getContentPane().add(dobField);
                dobField.setText(customerBDATE);
                

                nameField = new JTextField();
                nameField.setColumns(10);
                nameField.setBounds(87, 103, 215, 20);
                getContentPane().add(nameField);
                nameField.setText(customerName);
                

                addressField = new JTextField();
                addressField.setColumns(10);
                addressField.setBounds(87, 134, 215, 20);
                getContentPane().add(addressField);
                addressField.setText(customerAddress);
                

                salaryField = new JTextField();
                salaryField.setColumns(10);
                salaryField.setBounds(87, 165, 215, 20);
                getContentPane().add(salaryField);
                salaryField.setText(customerSalary);
                

                genderField = new JTextField();
                genderField.setColumns(10);
                genderField.setBounds(87, 196, 215, 20);
                getContentPane().add(genderField);
                genderField.setText(customerSex);
                
            	
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }
        
        // Next and Previous buttons
        JButton btnNext = new JButton("Next");
        btnNext.setBounds(213, 227, 89, 23);
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

                            ssnField = new JTextField();
                            ssnField.setBounds(87, 41, 215, 20);
                            getContentPane().add(ssnField);
                            ssnField.setColumns(10);
                            ssnField.setText("");
                            ssnField.setText(customerSSN);
                            
                            dobField = new JTextField();
                            dobField.setColumns(10);
                            dobField.setBounds(87, 72, 215, 20);
                            getContentPane().add(dobField);
                            dobField.setText("");
                            dobField.setText(customerBDATE);
                            
                            nameField = new JTextField();
                            nameField.setColumns(10);
                            nameField.setBounds(87, 103, 215, 20);
                            getContentPane().add(nameField);
                            nameField.setText("");
                            nameField.setText(customerName);
                            
                            addressField = new JTextField();
                            addressField.setColumns(10);
                            addressField.setBounds(87, 134, 215, 20);
                            getContentPane().add(addressField);
                            addressField.setText("");
                            addressField.setText(customerAddress);
                            
                            salaryField = new JTextField();
                            salaryField.setColumns(10);
                            salaryField.setBounds(87, 165, 215, 20);
                            getContentPane().add(salaryField);
                            salaryField.setText("");
                            salaryField.setText(customerSalary);
                            
                            genderField = new JTextField();
                            genderField.setColumns(10);
                            genderField.setBounds(87, 196, 215, 20);
                            getContentPane().add(genderField);
                            genderField.setText("");
                            genderField.setText(customerSex);
                            
                        } 
                    } else {
                    	offset--;
                    }

                } catch (SQLException e) {

                    System.out.println(e.getMessage());

                }

                System.out.println("OFFSET: " + offset);

                JButton btnPrevious = new JButton("Previous");
                btnPrevious.setBounds(87, 227, 89, 23);
                getContentPane().add(btnPrevious);
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
		
		                                ssnField = new JTextField();
		                                ssnField.setBounds(87, 41, 215, 20);
		                                getContentPane().add(ssnField);
		                                ssnField.setColumns(10);
		                                ssnField.setText("");
		                                ssnField.setText(customerSSN);
		                                
		                                dobField = new JTextField();
		                                dobField.setColumns(10);
		                                dobField.setBounds(87, 72, 215, 20);
		                                getContentPane().add(dobField);
		                                dobField.setText("");
		                                dobField.setText(customerBDATE);
		                                
		                                nameField = new JTextField();
		                                nameField.setColumns(10);
		                                nameField.setBounds(87, 103, 215, 20);
		                                getContentPane().add(nameField);
		                                nameField.setText("");
		                                nameField.setText(customerName);
		                                
		                                addressField = new JTextField();
		                                addressField.setColumns(10);
		                                addressField.setBounds(87, 134, 215, 20);
		                                getContentPane().add(addressField);
		                                addressField.setText("");
		                                addressField.setText(customerAddress);
		                                
		                                salaryField = new JTextField();
		                                salaryField.setColumns(10);
		                                salaryField.setBounds(87, 165, 215, 20);
		                                getContentPane().add(salaryField);
		                                salaryField.setText("");
		                                salaryField.setText(customerSalary);
		                                
		                                genderField = new JTextField();
		                                genderField.setColumns(10);
		                                genderField.setBounds(87, 196, 215, 20);
		                                getContentPane().add(genderField);
		                                genderField.setText("");
		                                genderField.setText(customerSex);
		
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
        getContentPane().add(btnNext);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(312, 40, 112, 23);
        getContentPane().add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String currentSSN = ssnField.getText();
				String currentDOB = dobField.getText();
				String currentName = nameField.getText();
				String currentAdd = addressField.getText();
				String currentSalary = salaryField.getText();
				String currentGender = genderField.getText();

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
		            
		            offset = count - 1;
		            
		            ResultSet x = statement.executeQuery(getLast);
	            
                	while (x.next()) {
                		
                        String customerSSN = x.getString("ssn");
                        String customerBDATE = x.getString("bdate");
                        String customerName = x.getString("name");
                        String customerAddress = x.getString("address");
                        String customerSex = x.getString("sex");
                        String customerSalary = x.getString("salary");

                        ssnField = new JTextField();
                        ssnField.setBounds(87, 41, 215, 20);
                        getContentPane().add(ssnField);
                        ssnField.setColumns(10);
                        ssnField.setText("");
                        ssnField.setText(customerSSN);
                        
                        dobField = new JTextField();
                        dobField.setColumns(10);
                        dobField.setBounds(87, 72, 215, 20);
                        getContentPane().add(dobField);
                        dobField.setText("");
                        dobField.setText(customerBDATE);
                        
                        nameField = new JTextField();
                        nameField.setColumns(10);
                        nameField.setBounds(87, 103, 215, 20);
                        getContentPane().add(nameField);
                        nameField.setText("");
                        nameField.setText(customerName);
                        
                        addressField = new JTextField();
                        addressField.setColumns(10);
                        addressField.setBounds(87, 134, 215, 20);
                        getContentPane().add(addressField);
                        addressField.setText("");
                        addressField.setText(customerAddress);
                        
                        salaryField = new JTextField();
                        salaryField.setColumns(10);
                        salaryField.setBounds(87, 165, 215, 20);
                        getContentPane().add(salaryField);
                        salaryField.setText("");
                        salaryField.setText(customerSalary);
                        
                        genderField = new JTextField();
                        genderField.setColumns(10);
                        genderField.setBounds(87, 196, 215, 20);
                        getContentPane().add(genderField);
                        genderField.setText("");
                        genderField.setText(customerSex);
                        
                    }

		        } catch (SQLException error) {

		            System.out.println(error.getMessage());

		        }
			}
        });

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(312, 71, 112, 23);
        getContentPane().add(btnDelete);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(312, 102, 112, 23);
        getContentPane().add(btnUpdate);

        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(312, 133, 112, 23);
        getContentPane().add(btnClear);
        
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
        f.setSize(450, 300);
        f.setResizable(false);
        f.setVisible(true);
    }
}