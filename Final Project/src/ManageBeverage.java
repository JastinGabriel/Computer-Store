import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ManageBeverage extends JFrame implements ActionListener, MouseListener{
	
	private String User;
	
	public JInternalFrame internalFrame = new JInternalFrame ("", true, true, true);
		
	JPanel panel, footerPanel, headerPanel, centerPanel, UDPanel, stockPanel, InsertPanel;
	JPanel tablePanel;
	JTable table;
	JLabel newBeverageId, newBeverageName, newBeverageType, newBeveragePrice, newBeverageStock;
	JLabel beverageId, beverageName, beverageType, beveragePrice, beverageStock, addStock;
	JTextField textFieldNBI, textFieldNBN, textFieldNBP, textFieldNBS;
	JTextField textFieldBI, textFieldBN, textFieldBP, textFieldBS, textFieldAS;
	JComboBox<String> comboBoxNBT, comboBoxBT;
	JSpinner spinnerBevStock, spinnerStock;
	DefaultTableModel dtm;
	JScrollPane scrollPane;
	
	JButton insertButton, updateButton, deleteButton, resetButton, addStockButton;
	
	String generateID;
	DatabaseConnector con = DatabaseConnector.getConnector();
	
	public void initComp() {
		
		headerPanel = new JPanel();
		centerPanel = new JPanel(new GridLayout(2,0,8,8));
		InsertPanel = new JPanel(new GridLayout(5, 4,8, 15));
		footerPanel = new JPanel(new GridLayout(2, 2, 8, 8));
		
		// table
		tablePanel = new JPanel(new BorderLayout());
		String columns[] = new String[] {
				"Beverage Id", "Beverage Name", "Beverage Type", "Beverage Price", "Beverage Stock"
				};
		
			dtm = new DefaultTableModel(columns,0);
			table = new JTable(dtm);
			table.setFillsViewportHeight(true);
			scrollPane = new JScrollPane(table);
			table.addMouseListener(this);
			
			
			headerPanel.add(new JLabel("Manage Beverage"));
			centerPanel.add(scrollPane);
			
		//bottom 
			newBeverageId = new JLabel("New Beverage ID");
			InsertPanel.add(newBeverageId);
			textFieldNBI = new JTextField();
			InsertPanel.add(textFieldNBI);
			textFieldNBI.setEditable(false);
			
			beverageId = new JLabel("Beverage ID");
			InsertPanel.add(beverageId);
			textFieldBI = new JTextField();
			InsertPanel.add(textFieldBI);
			textFieldBI.setEditable(false);
			
			newBeverageName = new JLabel("New Beverage Name");
			InsertPanel.add(newBeverageName);
			textFieldNBN = new JTextField();
			InsertPanel.add(textFieldNBN);
			
			beverageName = new JLabel("Beverage Name");
			InsertPanel.add(beverageName);
			textFieldBN = new JTextField();
			InsertPanel.add(textFieldBN);

			newBeverageType = new JLabel("New Beverage Type");
			InsertPanel.add(newBeverageType);
			
			String[] categoriesNBT = new String[] {
				"Boba", "Coffee", "Tea", "Smoothies"
			};
			comboBoxNBT = new JComboBox<String>(categoriesNBT);
			InsertPanel.add(comboBoxNBT);
				

			beverageType = new JLabel("Beverage Type");
			InsertPanel.add(beverageType);
			String[] categoriesBT = new String[] {
				"Boba", "Coffee", "Tea", "Smoothies"
			};
			comboBoxBT = new JComboBox<String>(categoriesBT);
			InsertPanel.add(comboBoxBT);
				
			newBeveragePrice = new JLabel("New Beverage Price");
			InsertPanel.add(newBeveragePrice);
			textFieldNBP = new JTextField();
			InsertPanel.add(textFieldNBP);
			
			beveragePrice = new JLabel("Beverage Price");
			InsertPanel.add(beveragePrice);
			textFieldBP = new JTextField();
			InsertPanel.add(textFieldBP);
				
			newBeverageStock = new JLabel("New Beverage Stock");
			InsertPanel.add(newBeverageStock);
			SpinnerNumberModel stockBevModel = new SpinnerNumberModel();
			stockBevModel.setMinimum(0);
			stockBevModel.setValue(0);
			spinnerBevStock = new JSpinner(stockBevModel);
			InsertPanel.add(spinnerBevStock);	
	
			beverageStock = new JLabel("Beverage Stock");
			InsertPanel.add(beverageStock);
			textFieldBS = new JTextField();
			InsertPanel.add(textFieldBS);
			textFieldBS.setEditable(false);
			
			centerPanel.add(InsertPanel);
			
		//buttonPanel
			
			stockPanel = new JPanel(new GridLayout(0,3,8,8));
			UDPanel = new JPanel(new GridLayout(0, 2, 8, 8));
			
			insertButton = new JButton ("Insert Beverage");
			footerPanel.add(insertButton);
			insertButton.addActionListener(this);
			
			updateButton = new JButton("Update Beverage");
			UDPanel.add(updateButton);
			updateButton.addActionListener(this);
			
			deleteButton = new JButton("Delete Beverage");
			UDPanel.add(deleteButton);
			deleteButton.addActionListener(this);
			
			footerPanel.add(UDPanel);
			
			resetButton = new JButton("Reset");
			footerPanel.add(resetButton);
			resetButton.addActionListener(this);
			
			addStock = new JLabel("Add Stock");
			stockPanel.add(addStock);
			
			SpinnerNumberModel stockModel = new SpinnerNumberModel();
			stockModel.setMinimum(0);
			stockModel.setValue(0);
			spinnerStock = new JSpinner(stockModel);
			stockPanel.add(spinnerStock);
			
			addStockButton = new JButton("Add Stock");
			stockPanel.add(addStockButton);
			addStockButton.addActionListener(this);
			
			footerPanel.add(stockPanel);
			
			headerPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
			InsertPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
			centerPanel.setBorder(new EmptyBorder(0, 20, 50, 20));
			footerPanel.setBorder(new EmptyBorder(8, 20, 30, 20));
			
			
			internalFrame.add(headerPanel, BorderLayout.NORTH);
			internalFrame.add(centerPanel, BorderLayout.CENTER);
			internalFrame.add(footerPanel, BorderLayout.SOUTH);
		
		}
		
	private void refreshTable() {
		dtm.setRowCount(0);
		String query = "Select * From beverages";
		ResultSet result = con.executeQuery(query);
		try {
			while (result.next()) {
				
					String id = result.getString("BeverageID");
					String name = result.getString("BeverageName");
					String type = result.getString("BeverageType");
					int price = result.getInt("BeveragePrice");
					int stock = result.getInt("BeverageStock");
				dtm.addRow(new Object[] {
						id,
						name,
						type,
						price,
						stock
						
				
				});
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	
	private void setField() {
		
		 String query = "SELECT RIGHT(BeverageID,3) AS generateID FROM beverages";
			ResultSet result = con.executeQuery(query);
			String BeverageID = null;
			// cusor -> 0
			try {
				while(result.next()) {
					
					BeverageID = result.getString("generateID");		
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			int xxx= Integer.parseInt(BeverageID) + 1;
			generateID = String.format("BE%03d", xxx);
			textFieldNBI.setText(generateID);
			

	}
	
	
	public void setInternalFrame() {
		internalFrame.setSize(1600, 900);
		internalFrame.setVisible(true);
		internalFrame.setLocation(40,20);
		internalFrame.setBackground(new java.awt.Color(0,191,255));
		add(internalFrame);
		
	}
	
	public ManageBeverage(String User) {
		this.User = User;
		initComp();
		refreshTable();
		setField();
		setInternalFrame();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 boolean isNumeric = false;
		 try {
			Long.parseLong(textFieldBP.getText());
			isNumeric = true;
		} catch (Exception e2) {
			isNumeric = false;
		}
		 
		 boolean isNewPriceNumbeic = false;
		 try {
				Long.parseLong(textFieldNBP.getText());
				isNewPriceNumbeic = true;
			} catch (Exception e2) {
				isNewPriceNumbeic = false;
			}
		 boolean isNewStockNumeric = false;
		 try {
				Long.parseLong(textFieldNBP.getText());
				isNewStockNumeric = true;
			} catch (Exception e2) {
				isNewStockNumeric = false;
			}
		 
		if (e.getSource() == insertButton) {
			if (textFieldNBI.getText().length() <5 || textFieldNBI.getText().length() > 30) {
				JOptionPane.showMessageDialog(ManageBeverage.this, "New Beverage Name must consist of 5 - 30 characters","Message", JOptionPane.WARNING_MESSAGE);
			}else if (!isNewPriceNumbeic || Long.parseLong(textFieldNBP.getText()) <= 0 ) {
				JOptionPane.showMessageDialog(ManageBeverage.this, "New Beverage Price must be more than 0 and numeric","Message", JOptionPane.WARNING_MESSAGE);
			}else if (!isNewStockNumeric || Long.parseLong(textFieldNBP.getText()) <= 0) {
				JOptionPane.showMessageDialog(ManageBeverage.this, "New Beverage Stock must be more than 0 and numeric","Message", JOptionPane.WARNING_MESSAGE);
			}else {
				int dialogButton = JOptionPane.showConfirmDialog(ManageBeverage.this, "Are you sure want to update beverage","Confirmation Message", JOptionPane.YES_NO_OPTION);
				if (dialogButton == JOptionPane.YES_OPTION) {
					String query = "INSERT INTO beverages (BeverageID, BeverageName, BeverageType, BeveragePrice, BeverageStock) Values (?,?,?,?,?)  ";
					PreparedStatement ps = con.prepare(query);
					try {
						ps.setString(1, textFieldNBI.getText());
						ps.setString(2, textFieldNBN.getText());
						ps.setString(3, (String)comboBoxNBT.getSelectedItem());
						ps.setInt(4, Integer.parseInt(textFieldNBP.getText()));
						ps.setInt(5, (Integer)spinnerBevStock.getValue());
						ps.execute();
						refreshTable();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}else if (dialogButton == JOptionPane.NO_OPTION) {
					
				}}
			
			
			
			
		}else if (e.getSource() == updateButton) {
			if (textFieldBI.getText().isEmpty()) {
				JOptionPane.showMessageDialog(ManageBeverage.this, "Please fill the beverage id! (click a row in Table)","Message", JOptionPane.WARNING_MESSAGE);
			}else if (textFieldBN.getText().length() < 5 || textFieldBN.getText().length() > 30) {
				JOptionPane.showMessageDialog(ManageBeverage.this, "Beverage Name must consist of 5 - 30 characters","Message", JOptionPane.WARNING_MESSAGE);
			}else if (Long.parseLong(textFieldBP.getText()) < 0 || !isNumeric ) {
				JOptionPane.showMessageDialog(ManageBeverage.this, "Please enter a valid Price","Message", JOptionPane.WARNING_MESSAGE);
			}else {
				int dialogButton = JOptionPane.showConfirmDialog(ManageBeverage.this, "Are you sure want to update beverage","Confirmation Message", JOptionPane.YES_NO_OPTION);
				if (dialogButton == JOptionPane.YES_OPTION) {
					String query = "UPDATE beverages set BeverageName=?, BeverageType=?, BeveragePrice=? WHERE BeverageID =? ";
					PreparedStatement ps = con.prepare(query);
					try {
						ps.setString(1, textFieldBN.getText());
						ps.setString(2, (String)comboBoxBT.getSelectedItem());
						ps.setInt(3, Integer.parseInt(textFieldBP.getText()));
						ps.setString(4, textFieldBI.getText());
						ps.execute();
						refreshTable();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}else if (dialogButton == JOptionPane.NO_OPTION) {
					
				}}
		}else if (e.getSource() == deleteButton) {
			if (textFieldBI.getText().isEmpty()) {
				JOptionPane.showMessageDialog(ManageBeverage.this, "Please fill the beverage id! (click a row in Table)","Message", JOptionPane.WARNING_MESSAGE);
			}else {
				int dialogButton = JOptionPane.showConfirmDialog(ManageBeverage.this, "Are you sure want to update beverage","Confirmation Message", JOptionPane.YES_NO_OPTION);
				if (dialogButton == JOptionPane.YES_OPTION) {
					String query = "DELETE FROM beverages WHERE BeverageID =? ";
					PreparedStatement ps = con.prepare(query);
					try {
						ps.setString(1, textFieldBI.getText());
						ps.execute();
						refreshTable();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}else if (dialogButton == JOptionPane.NO_OPTION) {
					
				}}
		}else if (e.getSource() == addStockButton) {
			if (textFieldBI.getText().isEmpty()) {
				JOptionPane.showMessageDialog(ManageBeverage.this, "Please fill the beverage id! (click a row in Table)","Message", JOptionPane.WARNING_MESSAGE);
			}else if ((Integer)spinnerStock.getValue() <= 0) {
				JOptionPane.showMessageDialog(ManageBeverage.this, "Added Stock must be more than 0","Message", JOptionPane.WARNING_MESSAGE);
			}else {
				int dialogButton = JOptionPane.showConfirmDialog(ManageBeverage.this, "Are you sure want to add beverage stock?","Confirmation Message", JOptionPane.YES_NO_OPTION);
				if (dialogButton == JOptionPane.YES_OPTION) {
					String query = "UPDATE beverages set BeverageStock=? WHERE BeverageID =? ";
					PreparedStatement ps = con.prepare(query);
					try {
						ps.setInt(1, (Integer)spinnerStock.getValue());
						ps.setString(2, textFieldBI.getText());
						ps.execute();
						refreshTable();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}else if (dialogButton == JOptionPane.NO_OPTION) {
					
				}}
		}else if (e.getSource() == resetButton) {
			textFieldNBN.setText("");
			comboBoxNBT.setSelectedItem("Boba");
			textFieldNBP.setText("");
			spinnerBevStock.setValue(0);
			
			textFieldBI.setText("");
			textFieldBN.setText("");
			comboBoxBT.setSelectedItem("Boba");
			textFieldBP.setText("");
			textFieldBS.setText("");
			spinnerStock.setValue(0);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == table) {
			Integer rowIndex = table.getSelectedRow();
			String BeverageID = (String)dtm.getValueAt(rowIndex, 0);
			String BeverageName = (String)dtm.getValueAt(rowIndex, 1);
			String BeverageType = (String)dtm.getValueAt(rowIndex, 2);
			int BeveragePrice = (int)dtm.getValueAt(rowIndex, 3);
			int BeverageStock = (int)dtm.getValueAt(rowIndex, 4);
			textFieldBI.setText(BeverageID);
			textFieldBN.setText(BeverageName);
			comboBoxBT.setSelectedItem(BeverageType);
			textFieldBP.setText(String.valueOf(BeveragePrice));
			textFieldBS.setText(String.valueOf(BeverageStock));
			
		}
		
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
