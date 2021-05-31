
public class Cart {
	int snackId, qty;
	String snackName;

	public Cart(int snackId, String snackName, int qty) {
		this.snackId = snackId;
		this.qty = qty;
		this.snackName = snackName;
	}

	public String getSnackName() {
		return snackName;
	}

	public void setSnackName(String snackName) {
		this.snackName = snackName;
	}

	public int getSnackId() {
		return snackId;
	}

	public void setSnackId(int snackId) {
		this.snackId = snackId;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
	
}
