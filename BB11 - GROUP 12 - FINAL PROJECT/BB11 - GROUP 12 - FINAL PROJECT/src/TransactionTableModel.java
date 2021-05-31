import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TransactionTableModel extends AbstractTableModel {
	private String[] columnNames = { "Transaction Id", "Total Price", "Transaction Date" };
	public ArrayList<Transaction> transactionList;

	public TransactionTableModel(ArrayList<Transaction> transactionList) {
		this.transactionList = transactionList;
	}

	public TransactionTableModel() {
		transactionList = new ArrayList<Transaction>();
	}

	@Override
	public int getRowCount() {
		return transactionList.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Transaction trans = transactionList.get(rowIndex);

		if (columnIndex == 0) {
			return trans.getTransId();
		} else if (columnIndex == 1) {
			return trans.getTotalPrice();
		} else if (columnIndex == 2) {
			return trans.getTransDate();
		}
		return null;
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
}