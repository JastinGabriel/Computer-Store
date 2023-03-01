import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFormCustomer extends JFrame {
	
		String UserID;
		String UserName;
		JPanel header, center;
		public JDesktopPane pane = new JDesktopPane();
		
		DatabaseConnector con = DatabaseConnector.getConnector();
		
		public void initComp() {
			header = new JPanel();
			center = new JPanel();
			 
			JMenuBar mb = new JMenuBar();
			this.setJMenuBar(mb);
			JMenu menu1 = new JMenu("Profile");
			mb.add(menu1);
			JMenuItem menuItemEditProfile = new JMenuItem(new AbstractAction("Edit Profile") {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					EditFrame editForm = new EditFrame(UserID);
					pane.add(editForm.internalFrame);
					remove(center);
					add(pane, BorderLayout.CENTER);
				}
			});
			menu1.add(menuItemEditProfile);
			
			
			JMenuItem menuItemLogOff = new JMenuItem(new AbstractAction("Log off") {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					new LoginForm();
				}
			});
			menu1.add(menuItemLogOff);
			
			
			JMenuItem menuItemExit = new JMenuItem(new AbstractAction("Exit") {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					
				}
			});
			menu1.add(menuItemExit);
					
			JMenu menu2 = new JMenu("Transaction");
			mb.add(menu2);
			JMenuItem menuItemBuyBeverage = new JMenuItem(new AbstractAction("Buy Beverage") {
				
				@Override
				public void actionPerformed(ActionEvent e) {
//					setVisible(false);
					BuyBeverageForm newForm1 = new BuyBeverageForm(UserID);
					pane.add(newForm1.internalFrame);
					remove(center);
					add(pane, BorderLayout.CENTER);
					

				}
			});
			menu2.add(menuItemBuyBeverage);
			
			
			JMenuItem menuItemViewTransactionHistory = new JMenuItem(new AbstractAction("View Transaction History") {
				
				@Override
				public void actionPerformed(ActionEvent e) {
//					setVisible(false);
					TransactionForm newForm2 = new TransactionForm(UserID);
					pane.add(newForm2.internalFrame);
					remove(center);
					add(pane, BorderLayout.CENTER);

					
				}
			});
			menu2.add(menuItemViewTransactionHistory);
			
		}
		
		public void setComp() {
			header.add(new JLabel("Main Form"));
			header.setBackground(new java.awt.Color(0,191,255));
			center.add(new JLabel("Welcome to Janji Jywa, " + UserName));
			center.setBorder(new EmptyBorder(400, 500, 500, 500));
			center.setBackground(new java.awt.Color(0,191,255));
			
			pane.setBackground(new java.awt.Color(0,191,255));
			
			this.add(center, BorderLayout.CENTER);

		}
		
		public void setFrame() {
			setSize(1800, 1000);
			setBackground(new java.awt.Color(0,191,255));
			setTitle("Janji Jywa");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
		}
		
		private void getData() {
			String query = "SELECT * FROM users" + " WHERE UserID = " + "\"" +  UserID + "\"";
			ResultSet result = con.executeQuery(query);
			try {
				while(result.next()) {
					
					UserName = result.getString("UserName");
					
				}
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
		
	public MainFormCustomer(String UserID) {
		this.UserID = UserID;
		setFrame();
		initComp();
		getData();
		setComp();
		setVisible(true);
	}

}
