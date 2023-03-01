package Objek;

public class DetailTransaction {

	public String TransactionID, BeverageID, BeverageName,BeverageType;
	public int BeveragePrice,Quantity, SubTotal;
	
	public DetailTransaction(String transactionID, String beverageID, String beverageName, String beverageType,
			int beveragePrice, int quantity, int subTotal) {
		TransactionID = transactionID;
		BeverageID = beverageID;
		BeverageName = beverageName;
		BeverageType = beverageType;
		BeveragePrice = beveragePrice;
		Quantity = quantity;
		SubTotal = subTotal;
	}

	public DetailTransaction() {
		// TODO Auto-generated constructor stub
	}

}
