import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Objek.DetailTransaction;

public class TransactionForm extends JFrame implements MouseListener {
	
	private String UserID;

	public JInternalFrame internalFrame = new JInternalFrame ("", true, true, true);
	
	private JLabel Title, SelectedID,GrandTotal;
	
	private JTable HeaderTransactionTable, 	DetailTransactionTable;
	private DefaultTableModel HTTModel, DTTModel;
	
	private JScrollPane HTTScrollpane, DTTScrollpane;
	
	private JTextField SelectedIDField, GrandTotalField;
	private JPanel SelectedIDPanel, GrandTotalPanel;
	
	private JPanel Frame, headerPanel, centerPanel;
	
	DatabaseConnector con = DatabaseConnector.getConnector();
	
	ArrayList DetailTransactionsList = new ArrayList();
	
	public void setInternalFrame() {
		
		internalFrame.setSize(1680,900);
		internalFrame.setVisible(true);
		internalFrame.setLocation(40,20);
		add(internalFrame);
		
	}
	
	private void initComponent() {
		Title = new JLabel ("Transaction History");
		SelectedID = new JLabel ("Selected ID");
		GrandTotal = new JLabel ("Grand Total");
		
		SelectedIDField = new JTextField();
		GrandTotalField = new JTextField();
		
		SelectedIDField.setEditable(false);
		GrandTotalField.setEditable(false);
		GrandTotalField.setText("Rp. 0,-");
		
		SelectedIDField.setPreferredSize(new Dimension(200, 25));
		GrandTotalField.setPreferredSize(new Dimension(200, 25));
		
		SelectedIDPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		SelectedIDPanel.add(SelectedID);
		SelectedIDPanel.add(SelectedIDField);
		
		GrandTotalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		GrandTotalPanel.add(GrandTotal);
		GrandTotalPanel.add(GrandTotalField);

		String HTTColumn[] = {"Transaction ID", "User ID", "Transaction Date"};
		HTTModel = new DefaultTableModel(HTTColumn,0);
		HeaderTransactionTable = new JTable(HTTModel);
		HeaderTransactionTable.setFillsViewportHeight(true);
		HTTScrollpane = new JScrollPane(HeaderTransactionTable);
		HeaderTransactionTable.addMouseListener(this);
		
		String DTTColumn[] = {"Transaction ID", "Beverage ID", "Beverage Name","Beverage Type","Beverage Price","Beverage Quantity","Sub Total"};
		DTTModel = new DefaultTableModel(DTTColumn,0);
		DetailTransactionTable = new JTable(DTTModel);
		DetailTransactionTable.setFillsViewportHeight(true);
		DTTScrollpane = new JScrollPane(DetailTransactionTable);
		
		Frame = new JPanel(new BorderLayout(8,8));
		internalFrame.add(Frame);
		
		headerPanel = new JPanel();
		headerPanel.add(Title);
		Frame.add(headerPanel);
		
		centerPanel = new JPanel(new GridLayout(4,0));
		centerPanel.add(HTTScrollpane, BorderLayout.NORTH);
		centerPanel.add(SelectedIDPanel);
		centerPanel.add(DTTScrollpane, BorderLayout.CENTER);
		centerPanel.add(GrandTotalPanel, BorderLayout.SOUTH);
		Frame.add(centerPanel);
	}
	
	private void refreshTransactionHeader() {
		
		HTTModel.setRowCount(0);
		String query = "Select * From headertransactions";
		ResultSet result = con.executeQuery(query);
		try {
			while (result.next()) {
				Object[] row = new Object[] {
					result.getString("TransactionID"),
					result.getString("UserID"),
					result.getDate("TransactionDate"),
				};
				HTTModel.addRow(row);		
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	private void refreshTransactionDetail() {
		int GrandTotal = 0;
		DTTModel.setRowCount(0);
		String query = String.format("Select dt.TransactionID, dt.BeverageID, b.BeverageName, b.BeverageType, b.BeveragePrice, dt.Quantity From detailtransactions dt "
				+ " join beverages b on b.BeverageID = dt.BeverageID"
				+ " join headertransactions ht on ht.TransactionID = dt.TransactionID"
				+ " where UserID = \"" + UserID + "\""
				+ " AND dt.TransactionID = '%s' "
				, SelectedIDField.getText());
		ResultSet result = con.executeQuery(query);
		try {
			while (result.next()) {
				int SubTotal = result.getInt("Quantity")*result.getInt("BeveragePrice");
				String TransactionID = result.getString("TransactionID");
				String BeverageID = result.getString("BeverageID");
				String BeverageName = result.getString("BeverageName");
				String BeverageType = result.getString("BeverageType");
				int BeveragePrice = result.getInt("BeveragePrice");
				int Quantity = result.getInt("Quantity");
				String Total = "Rp. " + String.valueOf(SubTotal) + ",-";
				GrandTotal += SubTotal;
				
				DetailTransactionsList.add(new DetailTransaction(TransactionID, BeverageID, BeverageName,BeverageType, BeveragePrice,Quantity, SubTotal));
				
				DTTModel.addRow(new Object[]{	
						TransactionID,
						BeverageID,
						BeverageName,
						BeverageType,
						BeveragePrice,
						Quantity,
						Total
				});
				String STotal = "Rp. " + String.valueOf(GrandTotal) + ",-";
				GrandTotalField.setText(String.valueOf(STotal));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	public TransactionForm(String UserID) {
		this.UserID = UserID;
		setInternalFrame();
		initComponent();
		refreshTransactionHeader();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	
		if (e.getSource() == HeaderTransactionTable) {
			Integer rowIndex = HeaderTransactionTable.getSelectedRow();
			System.out.println(rowIndex);
			String TransactionID = (String)HTTModel.getValueAt(rowIndex, 0);
			SelectedIDField.setText(TransactionID);
			refreshTransactionDetail();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
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
