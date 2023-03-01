import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.dsig.SignedInfo;


public class RegisterForm extends JFrame implements ActionListener, MouseListener {

	   JPanel mainPanel = new JPanel(new GridLayout(8,2,10,20));
	   JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	   
	    
	  //  Title
	    JLabel loginLabel = new JLabel("Register Form");
	    JLabel IDLabel = new JLabel("ID");
	    JTextField IDField = new JTextField();
	    JLabel usernameLabel = new JLabel("User Name");
	    JTextField usernameField = new JTextField();
	    JLabel emailLabel = new JLabel("Email");
	    JTextField emailField = new JTextField();
	    JLabel phoneLabel = new JLabel("Phone");
	    JTextField phoneField = new JTextField();
	    JLabel addressLabel = new JLabel("Address");
	    JTextArea addressField = new JTextArea(5,0);
	    JLabel passwordLabel = new JLabel("Password");
	    JTextField passwordField = new JTextField();
	    JLabel genderLabel = new JLabel("Gender");
	    JPanel genderPanel = new JPanel(new GridLayout());
	    JRadioButton maleRadio = new JRadioButton("Male");
	    JRadioButton femaleRadio = new JRadioButton("Female");
	    JLabel roleLabel = new JLabel("Role");
	    JComboBox roleComboBox = new JComboBox();

	    
	    JPanel panelButton = new JPanel();
	    JButton registerButton = new JButton("Register");
	    JLabel SignIn = new JLabel ("Sign In");
	    JPanel footerPanel = new JPanel(new GridLayout(2,0));
	    
	    String generateID;
	    DatabaseConnector con = DatabaseConnector.getConnector();
	 
 private void setComponent() {
	 mainPanel.setBackground(new java.awt.Color(0,191,255));
	 mainPanel.setBorder(new EmptyBorder(40, 80, 100, 80));
	 registerPanel.setBackground(new java.awt.Color(0,191,255));
	 
	 loginLabel.setFont(new Font("Serif", Font.PLAIN, 24));
	 registerPanel.add(loginLabel);
	 
	 mainPanel.add(IDLabel);
	 
	 IDField.setEditable(false);
	 mainPanel.add(IDField);
	 
	 mainPanel.add(usernameLabel);
	 mainPanel.add(usernameField);
	 mainPanel.add(emailLabel); 
	 mainPanel.add(emailField);
	 mainPanel.add(phoneLabel);
	 mainPanel.add(phoneField);
	 mainPanel.add(addressLabel);
	 mainPanel.add(addressField);
	 mainPanel.add(passwordLabel);
	 mainPanel.add(passwordField);
	 mainPanel.add(genderLabel);
	 
	 maleRadio.setBackground(new java.awt.Color(0,191,255));
	 femaleRadio.setBackground(new java.awt.Color(0,191,255));
	    ButtonGroup gender = new ButtonGroup();
	    gender.add(maleRadio);
	    gender.add(femaleRadio);
	    genderPanel.add(maleRadio);
	    genderPanel.add(femaleRadio); 
	 mainPanel.add(genderPanel);   
	 mainPanel.add(roleLabel);
	    
	 roleComboBox.addItem("Admin");
	 roleComboBox.addItem("Customer");
	 mainPanel.add(roleComboBox);
	 
	    panelButton.add(registerButton);
	    panelButton.setBackground(new java.awt.Color(0,191,255));
	    registerButton.addActionListener(this);
	    
	    SignIn.setHorizontalAlignment(SwingConstants.CENTER);
	    
	    footerPanel.add(panelButton);
	    
	    SignIn.addMouseListener(this);
	    footerPanel.add(SignIn);
	    footerPanel.setBackground(new java.awt.Color(0,191,255));
	    footerPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
	    
	    add(registerPanel, BorderLayout.NORTH);
	    add(mainPanel, BorderLayout.CENTER);
	    add(footerPanel, BorderLayout.SOUTH);
	    
 }
 
 public RegisterForm() {
	 initializeFrame();
	 setComponent();
	 setField();
   
 }
 


 public void initializeFrame() {
  setTitle("Register Form");
  setSize(600, 650);
  setLocationRelativeTo(null);
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setVisible(true);
  
 }

 private void setField() {
	
	 String query = "SELECT RIGHT(UserID,3) AS generateID FROM users";
		ResultSet result = con.executeQuery(query);
		String UserID = null;
		// cusor -> 0
		try {
			while(result.next()) {
				
				UserID = result.getString("generateID");		
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int xxx= Integer.parseInt(UserID) + 1;
		generateID = String.format("US%03d", xxx);
		IDField.setText(generateID);

}
 
 private void insertData() {
	 String name = usernameField.getText();
	 String email = emailField.getText();
	 String password = passwordField.getText();
	 String gender = null;
	 if (maleRadio.isSelected()) {
		gender = "Male";
	}else if (femaleRadio.isSelected()) {
		gender = "Female";
	}
	 String address = addressField.getText();
	 String phone = phoneField.getText();
	 String role = (String)roleComboBox.getSelectedItem();
	 
	 
	 String query = "INSERT INTO users (UserID, UserName, UserEmail, UserPassword, UserGender, UserAddress, UserPhone, UserRole) VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement ps = con.prepare(query);
		try {			
			ps.setString(1, generateID);
			ps.setString(2, name);
			ps.setString(3, email);
			ps.setString(4, password);
			ps.setString(5, gender);
			ps.setString(6, address);
			ps.setString(7, phone);
			ps.setString(8, role);
			ps.execute();
		} catch (Exception e2) {
			e2.printStackTrace();
		}

}

 @Override
 public void actionPerformed(ActionEvent e) {
	 boolean isNumeric = false;
	 try {
		Long.parseLong(phoneField.getText());
		isNumeric = true;
	} catch (Exception e2) {
		isNumeric = false;
	}
	 
	int totalAd= 0;
	int totaldot= 0;
	
	for (int i = 0; i < emailField.getText().length(); i++) {
		char x = emailField.getText().charAt(i);
		if (x == '.') {
			break;
		}else if (x == '@') {
			totalAd++;
			for (int j = 0; j < emailField.getText().length(); j++) {
				char y = emailField.getText().charAt(j);
				if (y == '.') {
					totaldot++;
				}}}
	}
	 
	 if (e.getSource() == registerButton) {
		 if (usernameField.getText().length()<5 || usernameField.getText().length()>30) {
			JOptionPane.showMessageDialog(RegisterForm.this, "User Name must be between 5 - 30 characters","Message", JOptionPane.WARNING_MESSAGE);
		}else if (emailField.getText().startsWith("@") || emailField.getText().startsWith(".")) {
			JOptionPane.showMessageDialog(RegisterForm.this, "Please fill with valid email!","Message", JOptionPane.WARNING_MESSAGE);
		}else if (emailField.getText().contains("@.") || emailField.getText().contains(".@")) {
			JOptionPane.showMessageDialog(RegisterForm.this, "Please fill with valid email!","Message", JOptionPane.WARNING_MESSAGE);
		}else if (totalAd != 1 || totaldot != 1) {
			JOptionPane.showMessageDialog(RegisterForm.this, "Please fill with valid email!","Message", JOptionPane.WARNING_MESSAGE);
		}else if (!isNumeric || phoneField.getText().length() < 12) {
			JOptionPane.showMessageDialog(RegisterForm.this, "Phone number must be numeric and more than equals 12 digits","Message", JOptionPane.WARNING_MESSAGE);
		}else if (!(addressField.getText().length()>10) || !(addressField.getText().endsWith(" Street"))) {
			JOptionPane.showMessageDialog(RegisterForm.this, "must consist of 10 or more characters and ends with � Street�","Message", JOptionPane.WARNING_MESSAGE);
		}else if (passwordField.getText().length()<5 || passwordField.getText().length()>30) {
			JOptionPane.showMessageDialog(RegisterForm.this, "Password must 5 - 30 length of character","Message", JOptionPane.WARNING_MESSAGE);
		}else if (passwordField.getText().matches("[a-zA-z]+")) {
			JOptionPane.showMessageDialog(RegisterForm.this, "must at least contain 1 character and 1 digit","Message", JOptionPane.WARNING_MESSAGE);
		}else if (!maleRadio.isSelected() && !femaleRadio.isSelected()) {
			JOptionPane.showMessageDialog(RegisterForm.this, "Gender must be selected either �Male� or �Female","Message", JOptionPane.WARNING_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(RegisterForm.this, "Sucessfully Register!","Message", JOptionPane.WARNING_MESSAGE);
		}
		 insertData();
	 }
  
 }

@Override
public void mouseClicked(MouseEvent e) {
	setVisible(false);
	new LoginForm();
}

@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}



}