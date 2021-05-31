import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ManageUserForm extends JInternalFrame implements ActionListener {

	JPanel jpKiri, jpKanan, jpKananAtas, jpKananBawah, jpDOB, jpGender;
	JTable usersTable;
	JScrollPane usersPane;
	JLabel titleLabel, idLabel, nameLabel, emailLabel, passwordLabel, roleLabel, addressLabel, dobLabel, genderLabel;
	JTextField inputIdTF, inputNameTF, inputEmailTF;
	JPasswordField inputPasswordPF;
	JComboBox inputRoleCB, inputDateCB, inputMonthCB, inputYearCB;
	JTextArea inputAddressTA;
	JRadioButton inputMaleButton, inputFemaleButton;
	JButton insertButton, updateButton, deleteButton;

	UsersTableModel usersModel;
	ArrayList<Users> usersList;

	ConnectDB con;

	Calendar now = Calendar.getInstance();
	int curdate = now.get(Calendar.DATE);
	Date date = new Date();
	LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	int curmonth = localDate.getMonthValue();
	int curyear = now.get(Calendar.YEAR);
	String curdatestring = String.valueOf(curdate);

	public ManageUserForm() {
		super("ManageUserForm", false, true, false);

		InitializeComponents();

		setLayoutAndConfigureComponents();

		configureAndShowFrame();
	}

	private void InitializeComponents() {
		con = new ConnectDB();

		initializeandSeedTableModel();

		initializeAndConfigureTable();

		usersPane = new JScrollPane(usersTable);

		jpKiri = new JPanel(new GridLayout(2, 1));

		jpDOB = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jpGender = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		jpKananAtas = new JPanel(new GridLayout(8, 2));
		jpKananBawah = new JPanel(new GridLayout(3, 1));
		jpKanan = new JPanel(new GridLayout(2, 1));

		initializedLabelFieldAndSpinner();

		initializeButtonsAndListeners();
	}

	private void initializeandSeedTableModel() {
		usersList = con.getUsers();
		usersModel = new UsersTableModel(usersList);
	}

	private void initializeAndConfigureTable() {
		usersTable = new JTable(usersModel);
		usersTable.setRowSelectionAllowed(true);
		usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		usersTable.setAutoCreateRowSorter(true);

		usersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = usersTable.getSelectedRow();

				if (usersTable.getSelectedRow() == -1) {
					return;
				}

				String userid = usersTable.getValueAt(selectedRow, 0).toString();
				String username = usersTable.getValueAt(selectedRow, 1).toString();
				String userpassword = usersTable.getValueAt(selectedRow, 2).toString();
				String userdob = usersTable.getValueAt(selectedRow, 3).toString();
				String useremail = usersTable.getValueAt(selectedRow, 4).toString();
				String useraddress = usersTable.getValueAt(selectedRow, 5).toString();
				String userrole = usersTable.getValueAt(selectedRow, 6).toString();
				String usergender = usersTable.getValueAt(selectedRow, 7).toString();
				
				String[] arrOfStr = userdob.split("-", 3);
				String date = "";
				String month = "";
				String year = "";
				for (String a : arrOfStr) {
					date = arrOfStr[2];
					month = arrOfStr[1];
					year = arrOfStr[0];
				}
				char resultdate = date.charAt(0);
				char resultmonth = month.charAt(0);
				if(resultdate == '0') {
					resultdate = date.charAt(1);
					date = String.valueOf(resultdate);
					inputDateCB.setSelectedItem(date);
				}else {
					inputDateCB.setSelectedItem(date);
				}
				if(resultmonth == '0') {
					resultmonth = month.charAt(1);
					month = String.valueOf(resultmonth);
					inputMonthCB.setSelectedItem(month);
				}else{
					inputMonthCB.setSelectedItem(month);
				}
				inputIdTF.setText(userid);
				inputNameTF.setText(username);
				inputPasswordPF.setText(userpassword);
				inputYearCB.setSelectedItem(year);
				inputEmailTF.setText(useremail);
				inputAddressTA.setText(useraddress);
				inputRoleCB.setSelectedItem(userrole);
				if (usergender.equals("Male")) {
					inputMaleButton.setSelected(true);
					inputFemaleButton.setSelected(false);
				} else {
					inputFemaleButton.setSelected(true);
					inputMaleButton.setSelected(false);
				}
			}
		});
	}

	private void initializedLabelFieldAndSpinner() {
		titleLabel = new JLabel("User");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);

		idLabel = new JLabel("Id");
		nameLabel = new JLabel("Name");
		emailLabel = new JLabel("Email");
		passwordLabel = new JLabel("Password");
		roleLabel = new JLabel("Role");
		addressLabel = new JLabel("Address");
		dobLabel = new JLabel("Date of Birth");
		genderLabel = new JLabel("Gender");

		inputIdTF = new JTextField();
		inputIdTF.setEditable(false);
		inputNameTF = new JTextField();
		inputEmailTF = new JTextField();
		inputPasswordPF = new JPasswordField();
		
		String role[] = {"-", "User", "Admin"};
		inputRoleCB = new JComboBox(role);
		inputAddressTA = new JTextArea();
		
		String[] listdatea = new String[31];
		int listdate = 1;
		for (int i = 0; i <= 30; i++) {
			listdatea[i] = String.valueOf(listdate);
			listdate += 1;
		}
		inputDateCB = new JComboBox(listdatea);
		
		String[] listmontha = new String[12];
		int listmonth = 1;
		for (int i = 0; i <= 11; i++) {
			listmontha[i] = String.valueOf(listmonth);
			listmonth += 1;
		}
		inputMonthCB = new JComboBox(listmontha);
		
		int listyear = 1975;
		int yearnow = curyear - listyear;
		String[] listyeara = new String[yearnow+1];
		for (int i = 0; i <= yearnow; i++) {
			listyeara[i] = String.valueOf(listyear);
			listyear += 1;
		}
		inputYearCB = new JComboBox(listyeara);
	}

	private void initializeButtonsAndListeners() {
		inputMaleButton = new JRadioButton("Male");
		inputFemaleButton = new JRadioButton("Female");

		insertButton = new JButton("Insert");
		insertButton.addActionListener(this);
		updateButton = new JButton("Update");
		updateButton.addActionListener(this);
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
	}

	private void setLayoutAndConfigureComponents() {
		jpKiri.add(titleLabel);
		jpKiri.add(usersPane);
		
		jpKananAtas.add(idLabel);
		jpKananAtas.add(inputIdTF);
		jpKananAtas.add(nameLabel);
		jpKananAtas.add(inputNameTF);
		jpKananAtas.add(emailLabel);
		jpKananAtas.add(inputEmailTF);
		jpKananAtas.add(passwordLabel);
		jpKananAtas.add(inputPasswordPF);
		jpKananAtas.add(roleLabel);
		jpKananAtas.add(inputRoleCB);
		jpKananAtas.add(addressLabel);
		jpKananAtas.add(inputAddressTA);
		jpKananAtas.add(dobLabel);
		jpDOB.add(inputDateCB);
		jpDOB.add(inputMonthCB);
		jpDOB.add(inputYearCB);
		jpKananAtas.add(jpDOB);
		jpGender.add(inputMaleButton);
		jpGender.add(inputFemaleButton);
		jpKananAtas.add(genderLabel);
		jpKananAtas.add(jpGender);
		
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
		setTitle("Manage User");
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
		String name = inputNameTF.getText().toString();
		String email = inputEmailTF.getText().toString();
		String password = String.valueOf(inputPasswordPF.getPassword());
		String role = inputRoleCB.getSelectedItem().toString();
		String address = inputAddressTA.getText();
		String dates = inputDateCB.getSelectedItem().toString();
		String months = inputMonthCB.getSelectedItem().toString();
		String years = inputYearCB.getSelectedItem().toString();
		String gender = "";
		int roleid = 0;
		if(role.equals("User")) {
			roleid = 1;
		}else if (role.equals("Admin")){
			roleid = 2;
		}
		int date = Integer.parseInt(dates);
		int month = Integer.parseInt(months);
		int year = Integer.parseInt(years);
		int userid = 0;
		try {
			userid = Integer.parseInt(inputIdTF.getText());
		} catch (Exception e2) {
		}
		int insertuserid = 0;
		boolean cek = true;
		email = email.trim();
		
		String query = "SELECT MAX(UserId) as UserIdMax FROM User";
		
		con.rs = con.executeQuery(query);
		
		try {
			if(con.rs.next()) {
				insertuserid = con.rs.getInt("UserIdMax");
				insertuserid ++;
			}
		} catch (SQLException e2) {
			
		}
		
		if(!email.contains("@")) {
			cek = false;
		}else {
			String[] arrOfStr = email.split("@", 2);
			for (String a : arrOfStr) {
				if (a.contains("@")) {
					cek = false;
				} else if (arrOfStr[1].equals(".com")){
					cek = false;
				}
			}				
		}
		
		if (inputMaleButton.isSelected()){
			gender = inputMaleButton.getText();
		} else if (inputFemaleButton.isSelected()){
			gender = inputFemaleButton.getText();
		} else {
			gender = "";
		}
		if(e.getSource() == insertButton) {
			if(String.valueOf(userid).isEmpty() || userid == 0) {
				if (name.length() < 5 || name.length() > 30) {
					JOptionPane.showMessageDialog(this, "wrong format name!");
				} else if (cek == false || email.startsWith("@") || email.startsWith(".") || !email.endsWith(".com")) {
					JOptionPane.showMessageDialog(this, "error email!");
				} else if ((date >= curdate && month >= curmonth && year >= curyear) || (year > curyear)) {
					JOptionPane.showMessageDialog(this, "i'm sorry, u've not born yet!");
				} else if (!address.endsWith(" Street")) {
					JOptionPane.showMessageDialog(this, "Address must ends with Street!");
				} else if (name.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() 
						|| (inputMaleButton.isSelected() == false && inputFemaleButton.isSelected() == false)) {
					JOptionPane.showMessageDialog(this, "semua harus terisi!");
				} else if (role.equals("-")){
					JOptionPane.showMessageDialog(this, "Role must be between 'Admin' or 'User'");
				} else {
					query = "INSERT INTO user VALUES("+insertuserid+", "+roleid+", '"+name+"', '"+gender+"', '"+year+"-"+month+"-"+date+"', "
							+ "'"+address+"', '"+email+"', '"+password+"')";
					con.rs = con.executeUpdate(query);
					JOptionPane.showMessageDialog(this, "Insert Success");
					refreshUserDataFromDB();
				}
			}else {
				JOptionPane.showMessageDialog(this, "Id must be blank!");
			}
		} else if(e.getSource() == updateButton) {
			int selectedRow = usersTable.getSelectedRow();
			if(String.valueOf(userid).isEmpty() || userid == 0 || selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Error");
			}else {
				if (name.length() < 5 || name.length() > 30) {
					JOptionPane.showMessageDialog(this, "wrong format name!");
				} else if (cek == false || email.startsWith("@") || email.startsWith(".") || !email.endsWith(".com")) {
					JOptionPane.showMessageDialog(this, "error email!");
				} else if ((date >= curdate && month >= curmonth && year >= curyear) || (year > curyear)) {
					JOptionPane.showMessageDialog(this, "i'm sorry, u've not born yet!");
				} else if (!address.endsWith(" Street")) {
					JOptionPane.showMessageDialog(this, "Address must ends with Street!");
				} else if (name.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() 
						|| (inputMaleButton.isSelected() == false && inputFemaleButton.isSelected() == false)) {
					JOptionPane.showMessageDialog(this, "semua harus terisi!");
				} else if (role.equals("-")){
					JOptionPane.showMessageDialog(this, "Role must be between 'Admin' or 'User'");
				} else {
					query = "UPDATE user SET RoleId = "+roleid+", UserName = '"+name+"', UserGender = '"+gender+"', UserDOB = '"+year+"-"+month+"-"+date+"', "
							+ " UserAddress = '"+address+"', UserEmail = '"+email+"', UserPassword = '"+password+"' WHERE UserId = "+userid+"";
					con.rs = con.executeUpdate(query);
					JOptionPane.showMessageDialog(this, "Update Success");
					refreshUserDataFromDB();
				}
			}
		} else if (e.getSource() == deleteButton) {
			int selectedRow = usersTable.getSelectedRow();
			if(selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "User must be choosen");
			}else {
				query = "DELETE FROM user WHERE UserId = "+userid+"";
				con.rs = con.executeUpdate(query);
				JOptionPane.showMessageDialog(this, "Delete Success");
				refreshUserDataFromDB();
			}
		}
		inputIdTF.setText(null);
		inputNameTF.setText(null);
		inputEmailTF.setText(null);
		inputPasswordPF.setText(null);
		inputRoleCB.setSelectedIndex(0);
		inputAddressTA.setText(null);
		inputMaleButton.setSelected(false);
		inputFemaleButton.setSelected(false);
	}
	
	private void refreshUserDataFromDB() {
		usersModel.usersList = con.getUsers();
		usersModel.fireTableDataChanged();
	}
}
