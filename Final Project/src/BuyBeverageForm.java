import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Objek.Cart;

public class BuyBeverageForm extends JFrame implements ActionListener, MouseListener{

		private String UserID;
		private JPanel headerPanel, centerPanel, footerPanel;
		private JPanel tablePanel = new JPanel();
		
		private JPanel insertPanel = new JPanel(new GridLayout(1, 2));
		private JPanel insertLeft = new JPanel (new GridLayout(3, 2));
		private JPanel insertRight = new JPanel (new GridLayout(3, 2));
	
		
		public JInternalFrame internalFrame = new JInternalFrame ("Beverage", true, true, true, true);
		
		private JLabel title, beverageID, beverageName, beverageType, beveragePrice, beverageStock, beverageQuantity;
		private JPanel beverageIDFieldPanel, beverageNameFieldPanel, beverageTypeFieldPanel, beveragePriceFieldPanel, beverageStockFieldPanel, beverageQuantitySpinnerPanel;
		
		private JTextField beverageIDField, beverageNameField, beverageTypeField, beveragePriceField, beverageStockField;
		
		private JSpinner beverageQuantitySpinner;
		private JButton addCart, remove, clear, checkout;
		private JPanel addPanel;
	
		private DefaultTableModel beverageModel, cartModel;
		private JTable beverageTable, cartTable;
		private JScrollPane beverageScrollpane, cartScrollpane;
		
		private SpinnerNumberModel SpinnerQuantity = new SpinnerNumberModel();
		
		ArrayList cartList = new ArrayList();
		
		DatabaseConnector con = DatabaseConnector.getConnector();
		
	public void setInternalFrame() {
		
		
//		pane.add(new JLabel("Welcome to Janji Jywa, daniel fujiyono"));
//		this.add(pane);
		
//		internalFrame.setSize(internalFrame.getMaximumSize());
//		internalFrame.setLocation(30,30);
//		try {
//			internalFrame.setMaximum(true);
//		} catch (Exception e) {
//
//		}
		internalFrame.setSize(1680,900);
		internalFrame.setVisible(true);
		internalFrame.setLocation(40,20);
		internalFrame.setBackground(new java.awt.Color(0,191,255));
		add(internalFrame);
		
	}
	
	public void initComponent() {
		String beverageColumn[] = {"Beverage ID", "Beverage Name", "Beverage Type", "Beverage Price", "Beverage Stock"};
		beverageModel = new DefaultTableModel(beverageColumn,0);
		beverageTable = new JTable(beverageModel);
		beverageTable.setFillsViewportHeight(true);
		beverageScrollpane = new JScrollPane(beverageTable);
		beverageTable.addMouseListener(this);

		
		String cartColumn[] = {"Beverage ID", "Beverage Name", "Beverage Type", "Beverage Price", "Beverage Stock","Beverage Quantity", "Sub Total"};
		cartModel = new DefaultTableModel(cartColumn,0);
		cartTable = new JTable(cartModel);
		cartTable.setFillsViewportHeight(true);
		cartScrollpane = new JScrollPane(cartTable);
		
		
		headerPanel = new JPanel();
		centerPanel = new JPanel(new GridLayout(4, 1));
		footerPanel = new JPanel(new GridLayout(0,3,10,15));
		
		title = new JLabel("Buy Beverage");
		title.setHorizontalAlignment(SwingConstants.CENTER);

		beverageID = new JLabel ("Beverage ID");
		beverageName = new JLabel ("Beverage Name");
		beverageType = new JLabel ("Beverage Type");
		beveragePrice = new JLabel ("Beverage Price");
		beverageStock = new JLabel ("Beverage Stock");
		beverageQuantity = new JLabel ("Beverage Quantity");
		
		beverageIDField = new JTextField(); 
		beverageNameField = new JTextField(); 
		beverageTypeField = new JTextField(); 
		beveragePriceField = new JTextField(); 
		beverageStockField = new JTextField(); 
		
		beverageIDFieldPanel = new JPanel(new FlowLayout (FlowLayout.RIGHT));
		beverageNameFieldPanel = new JPanel(new FlowLayout (FlowLayout.RIGHT));
		beverageTypeFieldPanel = new JPanel(new FlowLayout (FlowLayout.RIGHT));
		beveragePriceFieldPanel = new JPanel(new FlowLayout (FlowLayout.RIGHT));
		beverageStockFieldPanel = new JPanel(new FlowLayout (FlowLayout.RIGHT));
		beverageQuantitySpinnerPanel = new JPanel(new FlowLayout (FlowLayout.RIGHT));
		
		beverageQuantitySpinner = new JSpinner(SpinnerQuantity);
		SpinnerQuantity.setMinimum(1);
		SpinnerQuantity.setValue(1);;

		addCart = new JButton("Add to Cart");
		remove = new JButton("Remove Selected Cart");
		clear = new JButton("Clear Cart");
		checkout = new JButton("Checkout");
		
		addPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
//		DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//		LocalDateTime now = LocalDateTime.now();
//		String dateNow = date.format(now);
//		System.out.println(dateNow);
	}
	
	public void setComponent() {
		
		//Label Text
		JPanel ID = new JPanel (new FlowLayout(FlowLayout.LEFT,10, 10));
		JPanel Name = new JPanel (new FlowLayout(FlowLayout.LEFT,10, 10));
		JPanel Type = new JPanel (new FlowLayout(FlowLayout.LEFT,10, 10));
		JPanel Price = new JPanel (new FlowLayout(FlowLayout.LEFT,10, 10));
		JPanel Stock = new JPanel (new FlowLayout(FlowLayout.LEFT,10, 10));
		JPanel Quantity = new JPanel (new FlowLayout(FlowLayout.LEFT,10, 10));
//		ID.setBackground(Color.WHITE);
		
		ID.add(beverageID);
		Name.add(beverageName);
		Type.add(beverageType);
		Price.add(beveragePrice);
		Stock.add(beverageStock);
		Quantity.add(beverageQuantity);
		
		//Panel Field
		beverageIDField.setPreferredSize(new Dimension(350, 25));
		beverageNameField.setPreferredSize(new Dimension(350, 25));
		beverageTypeField.setPreferredSize(new Dimension(350, 25));
		beveragePriceField.setPreferredSize(new Dimension(350, 25));
		beverageStockField.setPreferredSize(new Dimension(350, 25));
		beverageQuantitySpinner.setPreferredSize(new Dimension(350, 25));
		
		beverageIDField.setEditable(false);
		beverageNameField.setEditable(false);
		beverageTypeField.setEditable(false);
		beveragePriceField.setEditable(false);
		beverageStockField.setEditable(false);
				
		beverageIDFieldPanel.add(beverageIDField);
		beverageNameFieldPanel.add(beverageNameField);
		beverageTypeFieldPanel.add(beverageTypeField);
		beveragePriceFieldPanel.add(beveragePriceField);
		beverageStockFieldPanel.add(beverageStockField);
		beverageQuantitySpinnerPanel.add(beverageQuantitySpinner);
		
		
		headerPanel.add(title);
		centerPanel.add(beverageScrollpane, BorderLayout.NORTH);
		
		
		insertLeft.add(ID);
		insertLeft.add(beverageIDFieldPanel);
		insertLeft.add(Name);
		insertLeft.add(beverageNameFieldPanel);
		insertLeft.add(Type);
		insertLeft.add(beverageTypeFieldPanel);
		
		insertRight.add(Price);
		insertRight.add(beveragePriceFieldPanel);
		insertRight.add(Stock);
		insertRight.add(beverageStockFieldPanel);
		insertRight.add(Quantity);
		insertRight.add(beverageQuantitySpinnerPanel);
		
		insertPanel.add(insertLeft);
		insertPanel.add(insertRight);

		centerPanel.add(insertPanel, BorderLayout.CENTER);
		
		addPanel.add(addCart);
		addCart.addActionListener(this);
		
		centerPanel.add(addPanel);
		centerPanel.add(cartScrollpane, BorderLayout.SOUTH);
		
		remove.addActionListener(this);
		footerPanel.add(remove);
		clear.addActionListener(this);
		footerPanel.add(clear);
		checkout.addActionListener(this);
		footerPanel.add(checkout);
		
		internalFrame.add(headerPanel,BorderLayout.NORTH);
		internalFrame.add(centerPanel,BorderLayout.CENTER);
		internalFrame.add(footerPanel,BorderLayout.SOUTH);

	}

	
	private void refreshTableBeverage() {
		beverageModel.setRowCount(0);
		String query = "Select * From beverages";
		ResultSet result = con.executeQuery(query);
		try {
			while (result.next()) {
				Object[] row = new Object[] {
					result.getString("BeverageID"),
					result.getString("BeverageName"),
					result.getString("BeverageType"),
					result.getInt("BeveragePrice"),
					result.getInt("BeverageStock")
				};
				beverageModel.addRow(row);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	private void refreshTableCart() {
		cartModel.setRowCount(0);
		String query = "Select b.BeverageID, b.BeverageName, b.BeverageType, b.BeveragePrice, b.BeverageStock, c.Quantity From beverages b"
				+ " join carts c on c.BeverageID = b.BeverageID"
				+ " where UserID = \"" + UserID + "\""
				+ " group by b.BeverageID";
		ResultSet result = con.executeQuery(query);
		try {
			while (result.next()) {
				int SubTotal = result.getInt("Quantity")*result.getInt("BeveragePrice");
				
				
					String id = result.getString("BeverageID");
					String name = result.getString("BeverageName");
					String type = result.getString("BeverageType");
					int price = result.getInt("BeveragePrice");
					int stock = result.getInt("BeverageStock");
					int quantity = result.getInt("Quantity");
					String Total = "Rp. " + String.valueOf(SubTotal) + ",-";
					
					cartList.add(new Cart(id,name,type,Total,price,stock,quantity));
				cartModel.addRow(new Object[] {
						id,
						name,
						type,
						price,
						stock,
						quantity,
						Total
				});
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	public BuyBeverageForm(String UserID) {
		this.UserID = UserID;
		initComponent();
		setComponent();
		refreshTableBeverage();
		refreshTableCart();
		setInternalFrame();
//		setVisible(true
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean cekCart = true;// false = sudah ada | true = belum ada
		
		if (e.getSource() == addCart) {
			
			if (beverageTable.getSelectionModel().isSelectionEmpty()) {
				JOptionPane.showMessageDialog(BuyBeverageForm.this, "Please select the beverage!","Message", JOptionPane.WARNING_MESSAGE);
			}
			else {
			
			Integer rowIndex = beverageTable.getSelectedRow();
			int cekStock = (int)beverageModel.getValueAt(rowIndex, 4);
			Integer rowCart = -1;
			
			if (cekStock == 0) {
				JOptionPane.showMessageDialog(BuyBeverageForm.this, "There is no more stock for this beverage!","Message", JOptionPane.WARNING_MESSAGE);
			}
				
			
			for (int i = 0; i < cartModel.getRowCount(); i++) {
				String cekID = (String) cartModel.getValueAt(i, 0);
				rowCart++;
				if (beverageIDField.getText().contentEquals(cekID)) {
					cekCart = false;
					break;
				}
			}
			if (cekCart) {
				JOptionPane.showMessageDialog(BuyBeverageForm.this, "Successfully Insert Cart!","Message", JOptionPane.WARNING_MESSAGE);
				String query = "INSERT INTO carts (UserID, BeverageID, Quantity) VALUES (?,?,?)";
				PreparedStatement ps = con.prepare(query);
				try {
					ps.setString(1, UserID);
					ps.setString(2, beverageIDField.getText());
					ps.setInt(3, (Integer)beverageQuantitySpinner.getValue());
					ps.execute();
					refreshTableCart();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}else if (!cekCart) {
				int cartStock = (int)cartModel.getValueAt(rowCart, 5);
				int newStock =  cartStock + (int)SpinnerQuantity.getValue();
				String query = "UPDATE carts set Quantity=? WHERE UserID=? AND BeverageID=? ";
				PreparedStatement ps = con.prepare(query);
				try {
					ps.setInt(1, newStock);
					ps.setString(2, UserID);
					ps.setString(3, beverageIDField.getText());
					ps.execute();
					refreshTableCart();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}}}
		else if (e.getSource() == remove) {
			Integer rowIndexCart = cartTable.getSelectedRow();
			String ID = (String)cartModel.getValueAt(rowIndexCart, 0);
			String query = "Delete from carts where BeverageID=?";
			PreparedStatement ps = con.prepare(query);
			try {
				ps.setString(1, ID);
				ps.execute();
				refreshTableCart();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}else if (e.getSource() == clear) {
			int dialogButton = JOptionPane.showConfirmDialog(BuyBeverageForm.this, "Are you sure want to clear cart","Confirmation Message", JOptionPane.YES_NO_OPTION);
			if (dialogButton == JOptionPane.YES_OPTION) {
				String query = "Delete from carts where UserID=?";
				PreparedStatement ps = con.prepare(query);
				try {
					ps.setString(1, UserID);
					ps.execute();
					refreshTableCart();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}else if (dialogButton == JOptionPane.NO_OPTION) {

			}
		}else if (e.getSource() == checkout) {

			int dialogButton = JOptionPane.showConfirmDialog(BuyBeverageForm.this, "Are you sure want to clear cart","Confirmation Message", JOptionPane.YES_NO_OPTION);
			if (dialogButton == JOptionPane.YES_OPTION) {
				String query = "Delete from carts where UserID=?";
				PreparedStatement ps = con.prepare(query);
				try {
					ps.setString(1, UserID);
					ps.execute();
					refreshTableCart();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			for (int i = 0; i < cartList.size(); i++) {
				
				Cart CartList = (Cart) cartList.get(i);
				int newStock = CartList.BeverageStock - CartList.BeverageQuantity;
				String query2 = "UPDATE beverages set BeverageStock=? WHERE BeverageID=?";
				PreparedStatement ps2 = con.prepare(query2);
				try {
					ps2.setInt(1, newStock);
					ps2.setString(2, CartList.BeverageID);
					ps2.execute();
					refreshTableBeverage();
				} catch (Exception e2) {
					e2.printStackTrace();
				}}

			JOptionPane.showMessageDialog(BuyBeverageForm.this, "Successfully Checkout Cart!","Message", JOptionPane.WARNING_MESSAGE);
			
			String query4 = "SELECT RIGHT(TransactionID,3) AS generateID FROM headertransactions";
			ResultSet result = con.executeQuery(query4);
			String TransactionID = null;	
				try {
					while(result.next()) {
						
						TransactionID = result.getString("generateID");		
						
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
			int xxx= Integer.parseInt(TransactionID) + 1;
			String generateID = String.format("TR%03d", xxx);
		
			String query3 = "INSERT INTO headertransactions (TransactionID, UserID, TransactionDate) VALUES (?,?,?)";
			PreparedStatement ps3 = con.prepare(query3);
			try {
				ps3.setString(1, generateID);
				ps3.setString(2, UserID);
				ps3.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));
				ps3.execute();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			for (int i = 0; i < cartList.size(); i++) {
				Cart CartList = (Cart) cartList.get(i);
				String query5 = "INSERT INTO detailtransactions (TransactionID, BeverageID, Quantity) VALUES (?,?,?)";
				PreparedStatement ps4 = con.prepare(query5);
				try {
					ps4.setString(1, generateID);
					ps4.setString(2, CartList.BeverageID);
					ps4.setInt(3, CartList.BeverageQuantity);
					ps4.execute();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
	
			}else if (dialogButton == JOptionPane.NO_OPTION) {
				System.out.println("no");
			}
		} 
	}	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == beverageTable) {
			Integer rowIndex = beverageTable.getSelectedRow();
			String BeverageID = (String)beverageModel.getValueAt(rowIndex, 0);
			String BeverageName = (String)beverageModel.getValueAt(rowIndex, 1);
			String BeverageType = (String)beverageModel.getValueAt(rowIndex, 2);
			int Price = (int)beverageModel.getValueAt(rowIndex, 3);
			int Stock = (int)beverageModel.getValueAt(rowIndex, 4);
			beverageIDField.setText(BeverageID);
			beverageNameField.setText(BeverageName);
			beverageTypeField.setText(BeverageType);
			beveragePriceField.setText(String.valueOf(Price));
			beverageStockField.setText(String.valueOf(Stock));
			SpinnerQuantity.setMaximum(Stock);
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
