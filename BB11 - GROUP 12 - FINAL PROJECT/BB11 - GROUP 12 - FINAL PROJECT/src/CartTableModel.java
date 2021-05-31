import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class CartTableModel extends AbstractTableModel{
	
	private String[] columnNames = {"ID", "Name", "Quantity"};
	public ArrayList<Cart> cartList;
	
	public CartTableModel(ArrayList <Cart> cartList) {
		this.cartList = cartList;
	}
	
	public CartTableModel() {
		cartList = new ArrayList<Cart>();
	}

	@Override
	public int getRowCount() {
		return cartList.size();
	}
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Cart cart = cartList.get(rowIndex);
		
		if (columnIndex == 0) {
			return cart.getSnackId();
		}else if (columnIndex == 1) {
			return cart.getSnackName();
		}else if (columnIndex == 2) {
			return cart.getQty();
		}
		return null;
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
}