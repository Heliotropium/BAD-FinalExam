import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class SnackTableModel extends AbstractTableModel{
	private String[] columnNames = {"Id", "Name"};
	public ArrayList<Snack> snackList;
	private String[] columnNamesAdmin = {"Id", "Name", "Price"};
	
	User user;
	
	public SnackTableModel(ArrayList <Snack> snackList) {
		this.snackList = snackList;
	}
	
	public SnackTableModel() {
		snackList = new ArrayList<Snack>();
	}

	@Override
	public int getRowCount() {
		return snackList.size();
	}
	@Override
	public int getColumnCount() {
		if(user.roleid == 1) {
			return columnNames.length;			
		}else if(user.roleid == 2) {
			return columnNamesAdmin.length;
		}
		return 0;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Snack cart = snackList.get(rowIndex);
		if(user.roleid == 1) {
			if (columnIndex == 0) {
				return cart.getSnackId();
			}else if (columnIndex == 1) {
				return cart.getSnackName();
			}			
		}else if(user.roleid == 2) {
			if (columnIndex == 0) {
				return cart.getSnackId();
			}else if (columnIndex == 1) {
				return cart.getSnackName();
			}else if(columnIndex == 2) {
				return cart.getSnackPrice();
			}
		}
		return null;
	}
	
	@Override
	public String getColumnName(int column) {
		if(user.roleid == 1) {
			return columnNames[column];			
		}else if(user.roleid == 2) {
			return columnNamesAdmin[column];
		}
		return null;
	}
}