import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Objek.User;



public class LoginForm extends JFrame implements ActionListener, MouseListener{

	JLabel Title = new JLabel("Login Form");
	JLabel Email = new JLabel("Email");
	JLabel Password = new JLabel("Password");
	JLabel SignUp = new JLabel("Sign Up Here");
	JTextField EmailField = new JTextField();
	JPasswordField PassField = new JPasswordField();
	JButton loginButton = new JButton("Login");
	
	ArrayList userList = new ArrayList();
	
	DatabaseConnector connector = DatabaseConnector.getConnector();
	
	private void setComponent() {
		JPanel headerPanel = new JPanel();
		JPanel centerPanel = new JPanel(new GridLayout(2, 2,0,8));
		JPanel footerPanel = new JPanel(new GridLayout(2, 0	,0,15));
		
		Title.setFont(new Font("Dialog", Font.PLAIN, 28));
		headerPanel.add(Title);
		
		centerPanel.add(Email);
		centerPanel.add(EmailField);
		
		centerPanel.add(Password);
		centerPanel.add(PassField);
		centerPanel.setBorder(new EmptyBorder(15, 25, 10, 25));
		
		loginButton.addActionListener(this);
		SignUp.addMouseListener(this);
		
		SignUp.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel panelButton = new JPanel();
		panelButton.add(loginButton);
		
		footerPanel.add(panelButton);
		footerPanel.add(SignUp);
		footerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
		
		add(headerPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(footerPanel, BorderLayout.SOUTH);
		
		
	}
	
	private void getData() {
		userList.clear();
		
		String query = "SELECT * FROM users";
		ResultSet result = connector.executeQuery(query);
		// cusor -> 0
		try {
			while(result.next()) {
				String UserID,UserName, UserEmail, UserPassword, UserDOB, UserGender, UserAddress, UserPhone, UserRole;
				
				UserID = result.getString("UserID");
				UserName = result.getString("UserName");
				UserEmail = result.getString("UserEmail");
				UserPassword = result.getString("UserPassword");
				UserDOB = result.getString("UserDOB");
				UserGender = result.getString("UserGender");
				UserAddress = result.getString("UserAddress");
				UserPhone = result.getString("UserPhone");
				UserRole = result.getString("UserRole");
				
				userList.add(new User(UserID,UserName, UserEmail, UserPassword, UserDOB, UserGender, UserAddress, UserPhone, UserRole));
				
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
	}
	
	public void setFrame() {
		setSize(600,300);
		setTitle("Login Frame");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public LoginForm() {
		setFrame();
		setComponent();
		getData();
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if (e.getSource() == loginButton) {
			User cekUser = null;
			if (EmailField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(LoginForm.this, "Please Enter Your Email","Message", JOptionPane.WARNING_MESSAGE);
			}
			else if (PassField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(LoginForm.this, "Please Enter Your Password","Message", JOptionPane.WARNING_MESSAGE);
			}
			else {
				for (int i = 0; i < userList.size(); i++) {
					User user = (User) userList.get(i);
					if (EmailField.getText().equals(user.UserEmail) && PassField.getText().equals(user.UserPassword)) {
						cekUser = user;
						break;
					}					
			}
				if (cekUser == null) {
					JOptionPane.showMessageDialog(LoginForm.this, "Wrong email / password","Message", JOptionPane.WARNING_MESSAGE);
					return;
				}
				else {
					if (cekUser.UserRole.equals("Admin")) {
						setVisible(false);
						new MainFormAdmin(cekUser.UserID);
					}
					else if (cekUser.UserRole.equals("Customer")) {
						setVisible(false);
						new MainFormCustomer(cekUser.UserID);
					}
				}
			
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		setVisible(false);
		new RegisterForm();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
