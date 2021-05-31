import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class BuySnackForm extends JInternalFrame implements ActionListener {

	JTable cartTable, snackTable;
	JLabel titleCartLabel, titleSnackLabel;
	JTextField inputIdTF, inputNameTF;
	JSpinner inputQtySpinner;
	JButton removeItemButton, checkOutButton, addtoCartButton;

	JPanel jpKiri, jpTitleKiri, jpIsiKiri, jpButtonKiri, jpKanan, jpTitleKanan, jpIsiKanan, jpButtonKanan;

	JScrollPane cartPane;
	JScrollPane snackPane;

	User user;

	CartTableModel cartModel;
	SnackTableModel snackModel;
	ArrayList<Cart> cartList;
	ArrayList<Snack> snackList;

	ConnectDB con;

	Calendar now = Calendar.getInstance();
	int curdate = now.get(Calendar.DATE);
	Date date = new Date();
	LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	int curmonth = localDate.getMonthValue();
	int curyear = now.get(Calendar.YEAR);

	public BuySnackForm() {
		super("BuySnackForm", false, true, false);

		InitializeComponents();

		setLayoutAndConfigureComponents();

		configureAndShowFrame();
	}

	private void setLayoutAndConfigureComponents() {
		jpTitleKiri.add(titleCartLabel);
		jpIsiKiri.add(cartPane);
		jpButtonKiri.add(removeItemButton);
		jpButtonKiri.add(checkOutButton);

		jpKiri.add(jpTitleKiri);
		jpKiri.add(jpIsiKiri);
		jpKiri.add(jpButtonKiri);

		jpTitleKanan.add(titleSnackLabel);
		jpIsiKanan.add(snackPane);
		jpButtonKanan.add(inputIdTF);
		jpButtonKanan.add(inputNameTF);
		jpButtonKanan.add(inputQtySpinner);
		jpButtonKanan.add(addtoCartButton);

		jpKanan.add(jpTitleKanan);
		jpKanan.add(jpIsiKanan);
		jpKanan.add(jpButtonKanan);

	}

	private void initializedLabelFieldAndSpinner() {
		titleCartLabel = new JLabel("Cart");
		titleCartLabel.setHorizontalAlignment(JLabel.CENTER);

		titleSnackLabel = new JLabel("Snack");
		titleSnackLabel.setHorizontalAlignment(JLabel.CENTER);
		inputIdTF = new JTextField();
		inputIdTF.setEditable(false);
		inputNameTF = new JTextField();
		inputNameTF.setEditable(false);
		inputQtySpinner = new JSpinner();
	}

	private void InitializeComponents() {
		con = new ConnectDB();

		initializeandSeedTableModel();

		initializeAndConfigureTable();

		cartPane = new JScrollPane(cartTable);
		snackPane = new JScrollPane(snackTable);

		jpTitleKiri = new JPanel();
		jpIsiKiri = new JPanel();
		jpButtonKiri = new JPanel(new GridLayout(2, 1));
		jpKiri = new JPanel(new GridLayout(3, 1));

		jpTitleKanan = new JPanel();
		jpIsiKanan = new JPanel();
		jpButtonKanan = new JPanel(new GridLayout(4, 1));
		jpKanan = new JPanel(new GridLayout(3, 1));

		initializedLabelFieldAndSpinner();

		initializeButtonsAndListeners();
	}

	private void initializeButtonsAndListeners() {
		removeItemButton = new JButton("Remove");
		checkOutButton = new JButton("Check Out");

		addtoCartButton = new JButton("Add to Cart");

		removeItemButton.addActionListener(this);
		checkOutButton.addActionListener(this);
		addtoCartButton.addActionListener(this);

	}

	private void initializeandSeedTableModel() {
		cartList = con.getCarts();
		cartModel = new CartTableModel(cartList);

		snackList = con.getSnacksBuy();
		snackModel = new SnackTableModel(snackList);
	}

	private void initializeAndConfigureTable() {
		cartTable = new JTable(cartModel);
		cartTable.setRowSelectionAllowed(true);
		cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		snackTable = new JTable(snackModel);
		snackTable.setRowSelectionAllowed(true);
		snackTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		snackTable.setAutoCreateRowSorter(true);

		snackTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = snackTable.getSelectedRow();

				if (snackTable.getSelectedRow() == -1) {
					return;
				}

				String snackid = snackTable.getValueAt(selectedRow, 0).toString();
				String snackname = snackTable.getValueAt(selectedRow, 1).toString();

				inputIdTF.setText(snackid);
				inputNameTF.setText(snackname);
			}
		});
	}

	private void configureAndShowFrame() {
		setLayout(new GridLayout(1, 2));
		add(jpKiri);
		add(jpKanan);
		setTitle("Buy Snack");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setSize(new Dimension(1520, 750));
		setVisible(true);
		setMaximizable(true);
		setDefaultLocale(null);
		setClosable(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addtoCartButton) {
			triggerInsert();
		} else if (e.getSource() == removeItemButton) {
			triggerRemove();
		} else if (e.getSource() == checkOutButton) {
			triggerCheckOut();
		}
	}

	private void triggerCheckOut() {
		String query = "";
		int userid = 0, snackid = 0, qty = 0, transid = 0;

		query = "SELECT MAX(TransactionId) as MaxTransId FROM headertransaction";
		con.rs = con.executeQuery(query);
		try {
			if (con.rs.next()) {
				transid = con.rs.getInt("MaxTransId");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		transid++;
		cartList = con.getCarts();
		userid = user.userid;
		query = "INSERT INTO headertransaction VALUES (" + transid + ", " + userid + ", '" + curyear + "-" + curmonth
				+ "-" + curdate + "')";
		con.rs = con.executeUpdate(query);
		for (int i = 0; i < cartList.size(); i++) {
			snackid = cartList.get(i).getSnackId();
			qty = cartList.get(i).getQty();
			query = "INSERT INTO detailtransaction VALUES ('" + transid + "', '" + cartList.get(i).getSnackId() + "', '" + cartList.get(i).getQty() + "')";
			con.rs = con.executeUpdate(query);
		}
		JOptionPane.showMessageDialog(this, "All item has bought");
		query = "DELETE FROM cart";
		con.rs = con.executeUpdate(query);
		refreshCartDataFromDB();
	}

	private void triggerRemove() {
		int selectedRow = cartTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select an item!");
		} else {
			selectedRow = cartTable.getSelectedRow();
			int snackid = (int) cartTable.getValueAt(selectedRow, 0);
			String snackname = cartTable.getValueAt(selectedRow, 1).toString();
			String query = "";

			query = "DELETE FROM cart WHERE SnackId = '" + snackid + "'";
			con.rs = con.executeUpdate(query);
			JOptionPane.showMessageDialog(this, snackname + " has been deleted!");
			refreshCartDataFromDB();
		}
	}

	private void triggerInsert() {
		int selectedIndex = snackTable.getSelectedRow();

		if (selectedIndex == -1) {
			JOptionPane.showMessageDialog(this, "Please select an item!");
		} else {
			int snackid = Integer.parseInt(inputIdTF.getText());
			int qty = (int) inputQtySpinner.getValue();
			int userid = user.userid;
			String query = "";

			query = "SELECT SnackId FROM cart WHERE SnackId = " + snackid + "";
			con.rs = con.executeQuery(query);

			if (qty > 0) {
				try {
					if (con.rs.next() == true) {
						query = "UPDATE cart SET Quantity = Quantity + '" + qty + "'" + " WHERE cart.SnackId = "
								+ snackid + "";
						con.rs = con.executeUpdate(query);
						JOptionPane.showMessageDialog(this, "Update Success");
					} else {
						query = "INSERT INTO cart VALUES('" + userid + "', '" + snackid + "'," + " '" + qty + "')";
						con.rs = con.executeUpdate(query);
						JOptionPane.showMessageDialog(this, "Insert Success");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				refreshCartDataFromDB();
			} else {
				JOptionPane.showMessageDialog(this, "Qty must be more than 0");
			}
			inputQtySpinner.setValue(0);
		}
	}

	private void refreshCartDataFromDB() {
		cartModel.cartList = con.getCarts();
		cartModel.fireTableDataChanged();
	}
}
