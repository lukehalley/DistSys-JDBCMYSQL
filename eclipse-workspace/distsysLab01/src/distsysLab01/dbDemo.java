package distsysLab01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

// GUI
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings({
    "serial",
    "unused"
})
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

            String selectTableSQL = "SELECT * FROM " + empTbl + " ORDER BY id LIMIT 1 OFFSET " + 0;
            System.out.println("SENDING THIS: " + selectTableSQL);

            dbConnection = getConnection();
            statement = dbConnection.createStatement();

            ResultSet rs = statement.executeQuery(selectTableSQL);



            while (rs.next()) {

                String customerID = rs.getString("id");
                String customerSSN = rs.getString("ssn");
                String customerBDATE = rs.getString("bdate");
                String customerName = rs.getString("name");
                String customerAddress = rs.getString("address");
                String customerSex = rs.getString("sex");
                String customerWorks = rs.getString("worksfor");
                String customerSalary = rs.getString("salary");

                System.out.println("id : " + customerID);
                System.out.println("ssn : " + customerSSN);
                System.out.println("bdate : " + customerBDATE);
                System.out.println("name : " + customerName);
                System.out.println("address : " + customerAddress);
                System.out.println("sex : " + customerSex);
                System.out.println("works : " + customerWorks);
                System.out.println("salary : " + customerSalary);

                ssnField = new JTextField();
                ssnField.setBounds(87, 41, 215, 20);
                getContentPane().add(ssnField);
                ssnField.setColumns(10);
                ssnField.setText(customerSSN);
                ssnField.repaint();

                dobField = new JTextField();
                dobField.setColumns(10);
                dobField.setBounds(87, 72, 215, 20);
                getContentPane().add(dobField);
                dobField.setText(customerBDATE);
                ssnField.repaint();

                nameField = new JTextField();
                nameField.setColumns(10);
                nameField.setBounds(87, 103, 215, 20);
                getContentPane().add(nameField);
                nameField.setText(customerName);
                ssnField.repaint();

                addressField = new JTextField();
                addressField.setColumns(10);
                addressField.setBounds(87, 134, 215, 20);
                getContentPane().add(addressField);
                addressField.setText(customerAddress);
                ssnField.repaint();

                salaryField = new JTextField();
                salaryField.setColumns(10);
                salaryField.setBounds(87, 165, 215, 20);
                getContentPane().add(salaryField);
                salaryField.setText(customerSalary);
                ssnField.repaint();

                genderField = new JTextField();
                genderField.setColumns(10);
                genderField.setBounds(87, 196, 215, 20);
                getContentPane().add(genderField);
                genderField.setText(customerSex);
                ssnField.repaint();

            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(312, 40, 112, 23);
        getContentPane().add(btnAdd);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(312, 71, 112, 23);
        getContentPane().add(btnDelete);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(312, 102, 112, 23);
        getContentPane().add(btnUpdate);

        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(312, 133, 112, 23);
        getContentPane().add(btnClear);

        JButton btnNext = new JButton("Next");
        btnNext.setBounds(213, 227, 89, 23);
        btnNext.addActionListener(new ActionListener() {
            int offset = 1;
            public void actionPerformed(ActionEvent arg0) {
                
                try {
                    Connection dbConnection = null;
                    Statement statement = null;

                    String selectTableSQL = "SELECT * FROM " + empTbl + " ORDER BY id LIMIT 1 OFFSET " + offset;
                    System.out.println("SENDING THIS: " + selectTableSQL);

                    dbConnection = getConnection();
                    
                    Statement s = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                        ResultSet r = s
                            .executeQuery("SELECT * FROM " + empTbl);
                        r.last();
                        int count = r.getRow();
                        r.beforeFirst();
                    
                    statement = dbConnection.createStatement();

                    if (offset < count && offset >= -1){
                    	
                    	offset++;
                    
                    	ResultSet rs = statement.executeQuery(selectTableSQL);

                    	while (rs.next()) {

                            String customerID = rs.getString("id");
                            String customerSSN = rs.getString("ssn");
                            String customerBDATE = rs.getString("bdate");
                            String customerName = rs.getString("name");
                            String customerAddress = rs.getString("address");
                            String customerSex = rs.getString("sex");
                            String customerWorks = rs.getString("worksfor");
                            String customerSalary = rs.getString("salary");

                            System.out.println("id : " + customerID);
                            System.out.println("ssn : " + customerSSN);
                            System.out.println("bdate : " + customerBDATE);
                            System.out.println("name : " + customerName);
                            System.out.println("address : " + customerAddress);
                            System.out.println("sex : " + customerSex);
                            System.out.println("works : " + customerWorks);
                            System.out.println("salary : " + customerSalary);

                            ssnField = new JTextField();
                            ssnField.setBounds(87, 41, 215, 20);
                            getContentPane().add(ssnField);
                            ssnField.setColumns(10);
                            ssnField.setText(customerSSN);
                            ssnField.repaint();

                            dobField = new JTextField();
                            dobField.setColumns(10);
                            dobField.setBounds(87, 72, 215, 20);
                            getContentPane().add(dobField);
                            dobField.setText(customerBDATE);
                            ssnField.repaint();

                            nameField = new JTextField();
                            nameField.setColumns(10);
                            nameField.setBounds(87, 103, 215, 20);
                            getContentPane().add(nameField);
                            nameField.setText(customerName);
                            ssnField.repaint();

                            addressField = new JTextField();
                            addressField.setColumns(10);
                            addressField.setBounds(87, 134, 215, 20);
                            getContentPane().add(addressField);
                            addressField.setText(customerAddress);
                            ssnField.repaint();

                            salaryField = new JTextField();
                            salaryField.setColumns(10);
                            salaryField.setBounds(87, 165, 215, 20);
                            getContentPane().add(salaryField);
                            salaryField.setText(customerSalary);
                            ssnField.repaint();

                            genderField = new JTextField();
                            genderField.setColumns(10);
                            genderField.setBounds(87, 196, 215, 20);
                            getContentPane().add(genderField);
                            genderField.setText(customerSex);
                            ssnField.repaint();

                        }
                    }

                } catch (SQLException e) {

                    System.out.println(e.getMessage());

                }

                System.out.println(offset);

                JButton btnPrevious = new JButton("Previous");
                btnPrevious.setBounds(87, 227, 89, 23);
                getContentPane().add(btnPrevious);
                btnPrevious.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent arg0) {
                        
                        System.out.println(offset);

                        try {
                            Connection dbConnection = null;
                            Statement statement = null;

                            String selectTableSQL = "SELECT * FROM " + empTbl + " ORDER BY id LIMIT 1 OFFSET " + offset;
                            System.out.println("SENDING THIS: " + selectTableSQL);

                            dbConnection = getConnection();
                            
                            Statement s = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                                ResultSet r = s
                                    .executeQuery("SELECT * FROM " + empTbl);
                                r.last();
                                int count = r.getRow();
                                r.beforeFirst();
                            
                            if (offset <= count && offset >= 0){
                            	
                            	offset--;
                            	
	                            statement = dbConnection.createStatement();
	
	                            ResultSet rs = statement.executeQuery(selectTableSQL);
	
	                            while (rs.next()) {
	
	                                String customerID = rs.getString("id");
	                                String customerSSN = rs.getString("ssn");
	                                String customerBDATE = rs.getString("bdate");
	                                String customerName = rs.getString("name");
	                                String customerAddress = rs.getString("address");
	                                String customerSex = rs.getString("sex");
	                                String customerWorks = rs.getString("worksfor");
	                                String customerSalary = rs.getString("salary");
	
	                                System.out.println("id : " + customerID);
	                                System.out.println("ssn : " + customerSSN);
	                                System.out.println("bdate : " + customerBDATE);
	                                System.out.println("name : " + customerName);
	                                System.out.println("address : " + customerAddress);
	                                System.out.println("sex : " + customerSex);
	                                System.out.println("works : " + customerWorks);
	                                System.out.println("salary : " + customerSalary);
	
	                                ssnField = new JTextField();
	                                ssnField.setBounds(87, 41, 215, 20);
	                                getContentPane().add(ssnField);
	                                ssnField.setColumns(10);
	                                ssnField.setText(customerSSN);
	                                ssnField.repaint();
	
	                                dobField = new JTextField();
	                                dobField.setColumns(10);
	                                dobField.setBounds(87, 72, 215, 20);
	                                getContentPane().add(dobField);
	                                dobField.setText(customerBDATE);
	                                ssnField.repaint();
	
	                                nameField = new JTextField();
	                                nameField.setColumns(10);
	                                nameField.setBounds(87, 103, 215, 20);
	                                getContentPane().add(nameField);
	                                nameField.setText(customerName);
	                                ssnField.repaint();
	
	                                addressField = new JTextField();
	                                addressField.setColumns(10);
	                                addressField.setBounds(87, 134, 215, 20);
	                                getContentPane().add(addressField);
	                                addressField.setText(customerAddress);
	                                ssnField.repaint();
	
	                                salaryField = new JTextField();
	                                salaryField.setColumns(10);
	                                salaryField.setBounds(87, 165, 215, 20);
	                                getContentPane().add(salaryField);
	                                salaryField.setText(customerSalary);
	                                ssnField.repaint();
	
	                                genderField = new JTextField();
	                                genderField.setColumns(10);
	                                genderField.setBounds(87, 196, 215, 20);
	                                getContentPane().add(genderField);
	                                genderField.setText(customerSex);
	                                ssnField.repaint();
	
	                            }
	                            
                            }

                        } catch (SQLException e) {

                            System.out.println(e.getMessage());

                        }

                    }

                });
            }
        });
        getContentPane().add(btnNext);

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