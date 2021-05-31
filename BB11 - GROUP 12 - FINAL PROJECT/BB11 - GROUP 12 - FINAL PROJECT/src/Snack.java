public class Snack {
	int snackId;
	String snackName;
	int snackPrice;
	
	public Snack(int snackId, String snackName, int snackPrice) {
		this.snackId = snackId;
		this.snackName = snackName;
		this.snackPrice = snackPrice;
	}
	
	public Snack(int snackId, String snackName) {
		this.snackId = snackId;
		this.snackName = snackName;
	}
	
	public int getSnackId() {
		return snackId;
	}
	public void setSnackId(int snackId) {
		this.snackId = snackId;
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
}
