import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ManageSnackForm extends JInternalFrame implements ActionListener {

	JPanel jpKiri, jpKanan, jpKananAtas, jpKananBawah;
	JTable snackTable;
	JScrollPane snackPane;
	JLabel snackTitleLabel, nameLabel, idLabel, priceLabel;
	JTextField inputIdTF, inputNameTF, inputPriceTF;
	JButton insertButton, updateButton, deleteButton;

	SnackTableModel snackModel;
	ArrayList<Snack> snackList;

	ConnectDB con;

	public ManageSnackForm() {
		super("TransactionForm", false, true, false);
		InitializeComponents();

		setLayoutAndConfigureComponents();

		configureAndShowFrame();
	}

	private void InitializeComponents() {
		con = new ConnectDB();

		initializeandSeedTableModel();

		initializeAndConfigureTable();

		snackPane = new JScrollPane(snackTable);

		jpKiri = new JPanel(new GridLayout(2, 1));

		jpKananAtas = new JPanel(new GridLayout(3, 2));
		jpKananBawah = new JPanel(new GridLayout(3, 1));
		jpKanan = new JPanel(new GridLayout(2, 1));

		initializedLabelFieldAndSpinner();
		
		initializeButtonsAndListeners();
	}

	private void initializeandSeedTableModel() {
		snackList = con.getSnacks();
		snackModel = new SnackTableModel(snackList);
	}

	private void initializeAndConfigureTable() {
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
				String snackprice = snackTable.getValueAt(selectedRow, 2).toString();

				inputIdTF.setText(snackid);
				inputNameTF.setText(snackname);
				inputPriceTF.setText(snackprice);
			}
		});
	}

	private void initializedLabelFieldAndSpinner() {
		snackTitleLabel = new JLabel("Snack");
		snackTitleLabel.setHorizontalAlignment(JLabel.CENTER);

		idLabel = new JLabel("Id");
		nameLabel = new JLabel("Name");
		priceLabel = new JLabel("Price");

		inputIdTF = new JTextField();
		inputIdTF.setEditable(false);
		inputNameTF = new JTextField();
		inputPriceTF = new JTextField();
	}
	
	private void initializeButtonsAndListeners() {
		insertButton = new JButton("Insert");
		insertButton.addActionListener(this);
		updateButton = new JButton("Update");
		updateButton.addActionListener(this);
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
	}

	private void setLayoutAndConfigureComponents() {
		jpKiri.add(snackTitleLabel);
		jpKiri.add(snackPane);

		jpKananAtas.add(idLabel);
		jpKananAtas.add(inputIdTF);
		jpKananAtas.add(nameLabel);
		jpKananAtas.add(inputNameTF);
		jpKananAtas.add(priceLabel);
		jpKananAtas.add(inputPriceTF);

		jpKananBawah.add(insertButton);
		jpKananBawah.add(updateButton);
		jpKananBawah.add(deleteButton);

		jpKanan.add(jpKananAtas);
		jpKanan.add(jpKananBawah);
	}

	private void configureAndShowFrame() {
		setLayout(new GridLayout(1, 2));
		add(jpKiri);
		add(jpKanan);
		setTitle("Manage Snack");
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
		if (!(inputNameTF.getText().isEmpty() || inputPriceTF.getText().isEmpty())) {
			int id = 0, price = 0;
			String name = inputNameTF.getText();
			try {
				price = Integer.parseInt(inputPriceTF.getText());
				
			} catch (Exception e2) {
				System.out.println("Price must be an integer!");
			}
			String query = "";
			if (e.getSource() == insertButton) {
				query = "SELECT MAX(SnackId) as MaxSnackId FROM snack";
				con.rs = con.executeQuery(query);
				try {
					if (con.rs.next()) {
						id = con.rs.getInt("MaxSnackId");
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				id++;
				query = "SELECT * FROM snack WHERE SnackName = '" + name + "'";
				con.rs = con.executeQuery(query);
				try {
					if (con.rs.next() == true || name.length() < 5 || name.length() > 30 || price <= 0) {
						JOptionPane.showMessageDialog(this, "Insert Error");
					} else if(price != (int) price)	{
						JOptionPane.showMessageDialog(this, "Price must be and Integer!");
					} else {
						JOptionPane.showMessageDialog(this, "Insert Success");
						query = "INSERT INTO snack VALUES (" + id + ", '" + name + "', " + price + ")";
						con.rs = con.executeUpdate(query);
						refreshSnackDataFromDB();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else if (e.getSource() == updateButton) {
				int selectedRow = snackTable.getSelectedRow();
				if (name.length() < 5 || name.length() > 30 || price <= 0 || selectedRow == -1) {
					JOptionPane.showMessageDialog(this, "Update Error");
				} else {
					id = Integer.parseInt(inputIdTF.getText());
					JOptionPane.showMessageDialog(this, "Update Success");
					query = "UPDATE snack SET SnackName = '" + name + "', SnackPrice = " + price + " WHERE SnackId = "
							+ id + "";
					con.rs = con.executeUpdate(query);
					refreshSnackDataFromDB();
				}
			} else if (e.getSource() == deleteButton) {
				int selectedRow = snackTable.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(this, "Delete Error");
				} else {
					id = Integer.parseInt(inputIdTF.getText());
					JOptionPane.showMessageDialog(this, "Delete Success");
					query = "DELETE FROM snack WHERE SnackId = " + id + "";
					con.rs = con.executeUpdate(query);
					refreshSnackDataFromDB();
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "Something is error!");
		}
		inputIdTF.setText(null);
		inputNameTF.setText(null);
		inputPriceTF.setText(null);
	}

	private void refreshSnackDataFromDB() {
		snackModel.snackList = con.getSnacks();
		snackModel.fireTableDataChanged();
	}
}