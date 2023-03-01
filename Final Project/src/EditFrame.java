import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
//import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Objek.User;

public class EditFrame extends JFrame implements ActionListener{
	
	private String UserID;

	public JInternalFrame internalFrame = new JInternalFrame ("", true, true, true);
//	JInternalFrame frame;
	JPanel panel, header, center, footer, radioPanel;
	JLabel updateProfile, changePassword;
	JLabel username, userEmail, userPhone, userAddress, userGender;
	JLabel oldPassword, newPassword, confirmPassword;
	JTextField usernameField, userEmailField, userPhoneField;
	JTextField oldPasswordField, newPasswordField, confirmPasswordField;
	JTextArea userAddressArea;
	JRadioButton male, female;
	ButtonGroup bg;
	JButton updateProfileButton, changePasswordButton;
	
	DatabaseConnector con = DatabaseConnector.getConnector();

	public void setFrame() {
		internalFrame = new JInternalFrame();	 // frame utama
		internalFrame.setSize(1200 ,600);
		internalFrame.setVisible(true);
		internalFrame.setLocation(40,20);
		add(internalFrame);
//		
//		frame = new JInternalFrame(); // set internal frame
//	    frame.setTitle("Edit Profile");
//	    frame.setResizable(true);
//	    frame.setClosable(true);
//	    frame.setMaximizable(true);
//	    frame.setIconifiable(true);
//	    frame.setSize(600, 500);
//	    frame.pack();
//	    
//	    mainframe.add(frame); // gabungin internal frame ke frame utama
	}
	
	public void initComp() {
		header = new JPanel(new GridLayout(0, 2));
		center = new JPanel(new GridLayout(0, 4, 1, 20));
		footer = new JPanel(new GridLayout(1, 4));
		radioPanel = new JPanel(new GridLayout(1, 4));
		panel = new JPanel(new BorderLayout(8, 8));
		
		updateProfile = new JLabel("Update Profile");
		updateProfile.setHorizontalAlignment(SwingConstants.CENTER);
		changePassword = new JLabel("Change Password");
		changePassword.setHorizontalAlignment(SwingConstants.CENTER);
			header.add(updateProfile);
			header.add(changePassword);
		
		username = new JLabel("Username"); 
			center.add(username);
		usernameField = new JTextField();
			center.add(usernameField);
		oldPassword = new JLabel("Old Password");
			center.add(oldPassword);
		oldPasswordField = new JTextField();
			center.add(oldPasswordField);
			
		userEmail = new JLabel("User Email");
			center.add(userEmail);
		userEmailField = new JTextField();
			center.add(userEmailField);
		newPassword = new JLabel("New Password");
			center.add(newPassword);
		newPasswordField = new JTextField();
			center.add(newPasswordField);
			
		userPhone = new JLabel("User Phone");
			center.add(userPhone);
		userPhoneField = new JTextField();
			center.add(userPhoneField);
		
		confirmPassword = new JLabel("Confirm Password");
			center.add(confirmPassword);
		confirmPasswordField = new JTextField();
			center.add(confirmPasswordField);
		
		//Text Area
		userAddress = new JLabel("User Address");
			center.add(userAddress);
		userAddressArea = new JTextArea(5,0);
			center.add(userAddressArea);
			
		JLabel InvisibleText = new JLabel("");
			center.add(InvisibleText);
		JLabel InvisibleText2 = new JLabel("");
			center.add(InvisibleText2);
		
		//Radio button
		center.add(radioPanel);
		userGender = new JLabel("User Gender");
		radioPanel.add(userGender);
		male = new JRadioButton("Male");
		female = new JRadioButton("Female");
			
		bg = new ButtonGroup();
		bg.add(male);
		bg.add(female);
		JPanel genderPanel = new JPanel(new GridLayout(0,2));
		genderPanel.add(male);
		genderPanel.add(female);
		center.add(genderPanel);
		
		//button
		updateProfileButton = new JButton("Update Profile");
			footer.add(updateProfileButton);
			updateProfileButton.addActionListener(this);
		changePasswordButton = new JButton("Change Password");
			footer.add(changePasswordButton);
			changePasswordButton.addActionListener(this);
	}
	
	public void setComp() {
		center.setBorder(new EmptyBorder(8, 8, 20, 8));
		footer.setBorder(new EmptyBorder(8, 10, 30, 8));
		
		panel.add(header, BorderLayout.NORTH);
		panel.add(center, BorderLayout.CENTER);
		panel.add(footer, BorderLayout.SOUTH);
		
		internalFrame.add(panel);
	}
	
	public EditFrame(String UserID) {
		this.UserID = UserID;
		setFrame();
		initComp();
		setComp();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		 boolean isNumeric = false;
		 try {
			Long.parseLong(userPhoneField.getText());
			isNumeric = true;
		} catch (Exception e2) {
			isNumeric = false;
		}
		
		int totalAd= 0;
		int totaldot= 0;
		
		for (int i = 0; i < userEmailField.getText().length(); i++) {
			char x = userEmailField.getText().charAt(i);
			if (x == '.') {
				break;
			}else if (x == '@') {
				totalAd++;
				for (int j = 0; j < userEmailField.getText().length(); j++) {
					char y = userEmailField.getText().charAt(j);
					if (y == '.') {
						totaldot++;
					}}}
		}
		
		if (e.getSource() == updateProfileButton) {
			if (usernameField.getText().length() < 5 || usernameField.getText().length() > 30) {
				JOptionPane.showMessageDialog(EditFrame.this, "User Name must be between 5 - 30 characters","Message", JOptionPane.WARNING_MESSAGE);
			}else if (userEmailField.getText().startsWith("@") || userEmailField.getText().startsWith(".")) {
				JOptionPane.showMessageDialog(EditFrame.this, "Please fill with valid email!","Message", JOptionPane.WARNING_MESSAGE);
			}else if (userEmailField.getText().contains("@.") || userEmailField.getText().contains(".@")) {
				JOptionPane.showMessageDialog(EditFrame.this, "Please fill with valid email!","Message", JOptionPane.WARNING_MESSAGE);
			}else if (totalAd != 1 || totaldot != 1) {
				JOptionPane.showMessageDialog(EditFrame.this, "Please fill with valid email!","Message", JOptionPane.WARNING_MESSAGE);
			}else if (!isNumeric || userPhoneField.getText().length() < 12) {
				JOptionPane.showMessageDialog(EditFrame.this, "Phone number must be numeric and more than equals 12 digits","Message", JOptionPane.WARNING_MESSAGE);
			}else if (!(userAddressArea.getText().length()>10) || !(userAddressArea.getText().endsWith(" Street"))) {
				JOptionPane.showMessageDialog(EditFrame.this, "must consist of 10 or more characters and ends with ‘ Street’","Message", JOptionPane.WARNING_MESSAGE);
			}else if (!male.isSelected() && !female.isSelected()) {
				JOptionPane.showMessageDialog(EditFrame.this, "Gender must be selected either “Male” or “Female","Message", JOptionPane.WARNING_MESSAGE);
			}else {
				int dialogButton = JOptionPane.showConfirmDialog(EditFrame.this, "Are you sure want to Update Profile?","Confirmation Message", JOptionPane.YES_NO_OPTION);
				if (dialogButton == JOptionPane.YES_OPTION) {
					String gender = null;
					 if (male.isSelected()) {
						gender = "Male";
					}else if (female.isSelected()) {
						gender = "Female";
					}
					String query = "UPDATE users set UserName=?, UserEmail=?, UserPhone=?, UserAddress=?, UserGender=? WHERE UserID=? ";
					PreparedStatement ps = con.prepare(query);
					try {
						ps.setString(1, usernameField.getText());
						ps.setString(2, userEmailField.getText());
						ps.setString(3, userPhoneField.getText());
						ps.setString(4, userAddressArea.getText());
						ps.setString(5, gender);
						ps.setString(6, UserID);						
						ps.execute();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}else if (dialogButton == JOptionPane.NO_OPTION) {
					
				}}
			
		}else if (e.getSource() == changePasswordButton) {
			String UserPassword = null;
			String query = "SELECT * FROM users WHERE UserID = " + "\"" + UserID + "\"";
			ResultSet result = con.executeQuery(query);
			// cusor -> 0
			try {
				while(result.next()) {
					

					UserPassword = result.getString("UserPassword");
					
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
			if (!(oldPasswordField.getText().contentEquals(UserPassword))) {
				JOptionPane.showMessageDialog(EditFrame.this, "Wrong Old Password” or “Female","Message", JOptionPane.WARNING_MESSAGE);
			}else if (newPasswordField.getText().length()<5 || newPasswordField.getText().length()>30) {
				JOptionPane.showMessageDialog(EditFrame.this, "Password must 5 - 30 length of character","Message", JOptionPane.WARNING_MESSAGE);
			}else if (newPasswordField.getText().matches("[a-zA-z]+")) {
				JOptionPane.showMessageDialog(EditFrame.this, "must at least contain 1 character and 1 digit","Message", JOptionPane.WARNING_MESSAGE);
			}else if (!(confirmPasswordField.getText().contentEquals(newPasswordField.getText()))) {
				JOptionPane.showMessageDialog(EditFrame.this, "Confirmation Password must match with New Password","Message", JOptionPane.WARNING_MESSAGE);
			}else {
				int dialogButton = JOptionPane.showConfirmDialog(EditFrame.this, "Are you sure want to change password?","Confirmation Message", JOptionPane.YES_NO_OPTION);
				if (dialogButton == JOptionPane.YES_OPTION) {
					String query2 = "UPDATE users set UserPassword=? WHERE UserID=? ";
					PreparedStatement ps = con.prepare(query2);
					try {
						ps.setString(1, newPasswordField.getText());
						ps.setString(2, UserID);
						ps.execute();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}else if (dialogButton == JOptionPane.NO_OPTION) {
					
				}}
		}
		
	}

}
