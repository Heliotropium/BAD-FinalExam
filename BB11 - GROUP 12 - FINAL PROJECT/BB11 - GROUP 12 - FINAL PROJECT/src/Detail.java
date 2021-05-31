public class Detail {
	String snackName;
	int snackPrice, qty;
	
	public Detail(String snackName, int snackPrice, int qty) {
		this.snackName = snackName;
		this.snackPrice = snackPrice;
		this.qty = qty;
	}
	
	public String getSnackName() {
		return snackName;
	}
	public void setSnackName(String snackName) {
		this.snackName = snackName;
	}
	public int getSnackPrice() {
		return snackPrice;
	}
	public void setSnackPrice(int snackPrice) {
		this.snackPrice = snackPrice;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
}