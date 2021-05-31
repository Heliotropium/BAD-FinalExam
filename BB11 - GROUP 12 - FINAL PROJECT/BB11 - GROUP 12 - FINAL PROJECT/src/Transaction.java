public class Transaction {
	int transId, totalPrice;
	String transDate;
	
	public Transaction(int transId, int totalPrice, String transDate) {
		this.transId = transId;
		this.totalPrice = totalPrice;
		this.transDate = transDate;
	}
	
	public int getTransId() {
		return transId;
	}
	public void setTransId(int transId) {
		this.transId = transId;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
}