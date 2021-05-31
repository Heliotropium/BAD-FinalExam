import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class UsersTableModel extends AbstractTableModel {
	private String[] columnNames = { "Id", "Name", "Password", "Date Of Birth", "Email", "Address", "Role", "Gender" };
	public ArrayList<Users> usersList;

	public UsersTableModel(ArrayList<Users> usersList) {
		this.usersList = usersList;
	}

	public UsersTableModel() {
		usersList = new ArrayList<Users>();
	}

	@Override
	public int getRowCount() {
		return usersList.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Users users = usersList.get(rowIndex);
		if (columnIndex == 0) {
			return users.getUserId();
		} else if (columnIndex == 1) {
			return users.getUserName();
		} else if (columnIndex == 2) {
			return users.getUserPassword();
		} else if (columnIndex == 3) {
			return users.getUserDOB();
		} else if (columnIndex == 4) {
			return users.getUserEmail();
		} else if (columnIndex == 5) {
			return users.getUserAddress();
		} else if (columnIndex == 6) {
			return users.getUserRole();
		} else if (columnIndex == 7) {
			return users.getUserGender();
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
}