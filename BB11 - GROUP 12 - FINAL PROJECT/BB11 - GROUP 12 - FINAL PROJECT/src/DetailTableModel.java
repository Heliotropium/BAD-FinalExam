import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class DetailTableModel extends AbstractTableModel{
	private String[] columnNames = { "Snack Name", "Snack Price", "Quantity" };
	public ArrayList<Detail> detailList;

	public DetailTableModel(ArrayList<Detail> detailList) {
		this.detailList = detailList;
	}

	public DetailTableModel() {
		detailList = new ArrayList<Detail>();
	}

	@Override
	public int getRowCount() {
		return detailList.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Detail detail = detailList.get(rowIndex);

		if (columnIndex == 0) {
			return detail.getSnackName();
		} else if (columnIndex == 1) {
			return detail.getSnackPrice();
		} else if (columnIndex == 2) {
			return detail.getQty();
		}
		return null;
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
}