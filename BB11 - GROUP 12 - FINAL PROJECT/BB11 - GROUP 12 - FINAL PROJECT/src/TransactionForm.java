import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class TransactionForm extends JInternalFrame implements MouseListener{

	JPanel jpKiri, jpKanan;
	JLabel titleTransLabel, titleDetailLabel;
	JTable headerTransTable, detailTransTable;
	JScrollPane headerTransPane, detailTransPane;

	TransactionTableModel transacitonModel;
	DetailTableModel detailModel;
	DefaultTableModel detailModelDefault = new DefaultTableModel();
	ArrayList<Transaction> transactionList;
	Vector<Detail> detailList;
	
	String header[] = {"SnackName", "SnackPrice", "Quantity"};
	
	ConnectDB con;
	
	int transid = 0;
	
	int selectedIndex = 0;

	public TransactionForm() {
		super("TransactionForm", false, true, false);
		InitializeComponents();

		setLayoutAndConfigureComponents();

		configureAndShowFrame();
	}

	private void setLayoutAndConfigureComponents() {
		jpKiri.add(titleTransLabel);
		jpKiri.add(headerTransPane);

		jpKanan.add(titleDetailLabel);
		jpKanan.add(detailTransPane);
	}

	private void InitializeComponents() {
		con = new ConnectDB();

		initializeandSeedTableModel();

		initializeAndConfigureTable();

		headerTransPane = new JScrollPane(headerTransTable);
		detailTransPane = new JScrollPane(detailTransTable);

		jpKiri = new JPanel(new GridLayout(2, 1));

		jpKanan = new JPanel(new GridLayout(2, 1));

		initializedLabelFieldAndSpinner();

	}

	private void initializedLabelFieldAndSpinner() {
		titleTransLabel = new JLabel("Transaction");
		titleTransLabel.setHorizontalAlignment(JLabel.CENTER);

		titleDetailLabel = new JLabel("Detail");
		titleDetailLabel.setHorizontalAlignment(JLabel.CENTER);
	}

	private void initializeAndConfigureTable() {
		headerTransTable = new JTable(transacitonModel);
		headerTransTable.setRowSelectionAllowed(true);
		headerTransTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		headerTransTable.setAutoCreateRowSorter(true);
		headerTransTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = headerTransTable.getSelectedRow();
				
				if(selectedRow == -1) {
					return;
				}
				
				transid = (int) headerTransTable.getValueAt(selectedRow, 0);
			}
		});
		headerTransTable.addMouseListener(this);

		detailTransTable = new JTable();
		detailTransTable.setModel(detailModelDefault);
		detailTransTable.setRowSelectionAllowed(false);
	}

	private void initializeandSeedTableModel() {
		transactionList = con.getTrans();
		transacitonModel = new TransactionTableModel(transactionList);
		
		for (int i = 0; i < header.length; i++) {
			detailModelDefault.addColumn(header[i]);
		}
	}

	private void configureAndShowFrame() {
		setLayout(new GridLayout(1, 2));
		add(jpKiri);
		add(jpKanan);
		setTitle("History Transaction");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setSize(new Dimension(1520, 750));
		setVisible(true);
		setMaximizable(true);
		setDefaultLocale(null);
		setClosable(true);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		detailModelDefault.setRowCount(0);
		if(transid > 0) {
			String query = "SELECT SnackName, SnackPrice, Quantity FROM snack INNER JOIN detailtransaction ON snack.SnackId = detailtransaction.SnackId WHERE TransactionId = "+transid+"";
			con.rs = con.executeQuery(query);
			String snackname = "";
			int snackprice = 0, qty = 0;
			try {
				while(con.rs.next()) {
					snackname = con.rs.getString("SnackName").toString();
					snackprice = (int) con.rs.getInt("SnackPrice");
					qty = (int) con.rs.getInt("Quantity");
					Object[] data = {snackname, snackprice, qty};
					detailModelDefault.addRow(data);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	
}