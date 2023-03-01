package Objek;

public class Cart {

	public String BeverageID, BeverageName, BeverageType, SubTotal;
	public int BeveragePrice, BeverageStock, BeverageQuantity;
	
	public Cart(String beverageID, String beverageName, String beverageType, String subTotal, int beveragePrice,
			int beverageStock, int beverageQuantity) {
		this.BeverageID = beverageID;
		this.BeverageName = beverageName;
		this.BeverageType = beverageType;
		this.SubTotal = subTotal;
		this.BeveragePrice = beveragePrice;
		this.BeverageStock = beverageStock;
		this.BeverageQuantity = beverageQuantity;
	}
	
	public Cart() {
		// TODO Auto-generated constructor stub
	}

}
