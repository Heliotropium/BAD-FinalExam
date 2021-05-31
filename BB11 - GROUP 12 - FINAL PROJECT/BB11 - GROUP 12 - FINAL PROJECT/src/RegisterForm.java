import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RegisterForm extends JInternalFrame implements ActionListener {

	MainForm mainform;
	JPanel pnlheader, pnlisi, pnlfooter, pnldob, pnlgender;
	JLabel lblnama, lbltitle, lblemail, lblpassword, lblconfirmpass, lbladdress, lbldob, lblgender;
	JTextField txtnama, txtemail;
	JPasswordField txtpassword, txtconfirmpass;
	JTextArea txtaddress;
	JComboBox txtdate, txtmonth, txtyear;
	JRadioButton maleButton, femaleButton;
	JButton register;
	ButtonGroup groupgender;

	ConnectDB con = new ConnectDB();

	Calendar now = Calendar.getInstance();
	int curdate = now.get(Calendar.DATE);
	Date date = new Date();
	LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	int curmonth = localDate.getMonthValue();
	int curyear = now.get(Calendar.YEAR);
	String curdatestring = String.valueOf(curdate);

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public RegisterForm() {
		super("Register Form", false, true, false);

		pnlheader = new JPanel();
		pnlisi = new JPanel(new GridLayout(7, 2));
		pnlfooter = new JPanel();
		pnldob = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlgender = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		groupgender = new ButtonGroup();

		lbltitle = new JLabel("Register");
		lblnama = new JLabel("Name");
		lblemail = new JLabel("Email");
		lblpassword = new JLabel("Password");
		lblconfirmpass = new JLabel("Confirm Password");
		lbladdress = new JLabel("Address");
		lbldob = new JLabel("Date of Birth");
		lblgender = new JLabel("Gender");

		txtnama = new JTextField();
		txtemail = new JTextField();
		txtpassword = new JPasswordField();
		txtconfirmpass = new JPasswordField();
		txtaddress = new JTextArea();

		String[] listdatea = new String[31];
		int listdate = 1;
		for (int i = 0; i <= 30; i++) {
			listdatea[i] = String.valueOf(listdate);
			listdate += 1;
		}
		txtdate = new JComboBox(listdatea);

		String[] listmontha = new String[12];
		int listmonth = 1;
		for (int i = 0; i <= 11; i++) {
			listmontha[i] = String.valueOf(listmonth);
			listmonth += 1;
		}
		txtmonth = new JComboBox(listmontha);

		int listyear = 1975;
		int yearnow = curyear - listyear;
		String[] listyeara = new String[yearnow+1];
		for (int i = 0; i <= yearnow; i++) {
			listyeara[i] = String.valueOf(listyear);
			listyear += 1;
		}
		txtyear = new JComboBox(listyeara);

		pnldob.add(txtdate);
		pnldob.add(txtmonth);
		pnldob.add(txtyear);
		maleButton = new JRadioButton("Male");
		femaleButton = new JRadioButton("Female");
		pnlgender.add(maleButton);
		pnlgender.add(femaleButton);
		groupgender.add(maleButton);
		groupgender.add(femaleButton);
		register = new JButton("Register");
		register.addActionListener(this);

		pnlisi.add(lblnama);
		pnlisi.add(txtnama);
		pnlisi.add(lblemail);
		pnlisi.add(txtemail);
		pnlisi.add(lblpassword);
		pnlisi.add(txtpassword);
		pnlisi.add(lblconfirmpass);
		pnlisi.add(txtconfirmpass);
		pnlisi.add(lbladdress);
		pnlisi.add(txtaddress);
		pnlisi.add(lbldob);
		pnlisi.add(pnldob);
		pnlisi.add(lblgender);
		pnlisi.add(pnlgender);

		pnlfooter.add(register);

		pnlheader.add(lbltitle);
		add(pnlheader, BorderLayout.NORTH);
		add(pnlisi, BorderLayout.CENTER);
		add(pnlfooter, BorderLayout.SOUTH);

		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400, 500);
		setResizable(false);
	}

	boolean isValidDate(String input) {
		try {
			format.parse(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == register) {
			String name = txtnama.getText();
			String email = txtemail.getText();
			String password = String.valueOf(txtpassword.getPassword());
			String confirmpass = String.valueOf(txtconfirmpass.getPassword());
			String address = txtaddress.getText();
			String dates = txtdate.getSelectedItem().toString();
			String months = txtmonth.getSelectedItem().toString();
			String years = txtyear.getSelectedItem().toString();
			String gender = "";
			int date = Integer.parseInt(dates);
			int month = Integer.parseInt(months);
			int year = Integer.parseInt(years);
			int userid = 0;
			boolean cek = true;
			email = email.trim();
			
			String query = "SELECT MAX(UserId) as UserIdMax FROM User";
			
			con.rs = con.executeQuery(query);
			
			try {
				if(con.rs.next()) {
					userid = con.rs.getInt("UserIdMax");
					userid ++;
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
			
			if (maleButton.isSelected()){
				gender = maleButton.getText();
			} else if (femaleButton.isSelected()){
				gender = femaleButton.getText();
			} else {
				gender = "";
			}
			
			if (name.length() < 5 || name.length() > 30) {
				JOptionPane.showMessageDialog(this, "wrong format name!");
			} else if (cek == false || email.startsWith("@") || email.startsWith(".") || !email.endsWith(".com")) {
				JOptionPane.showMessageDialog(this, "error email!");
			} else if ((date >= curdate && month >= curmonth && year >= curyear) || (year > curyear)) {
				JOptionPane.showMessageDialog(this, "i'm sorry, u've not born yet!");
			} else if (!password.equals(confirmpass)) {
				JOptionPane.showMessageDialog(this, "Password and Confirm Password must same!");
			} else if (!address.endsWith(" Street")) {
				JOptionPane.showMessageDialog(this, "Address must ends with Street!");
			} else if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmpass.isEmpty()
					|| address.isEmpty() || (maleButton.isSelected() == false && femaleButton.isSelected() == false)) {
				JOptionPane.showMessageDialog(this, "semua harus terisi!");
			} else {
				query = "INSERT INTO user VALUES("+userid+", 1, '"+name+"', '"+gender+"', '"+year+"-"+month+"-"+date+"', "
						+ "'"+address+"', '"+email+"', '"+password+"')";
				con.rs = con.executeUpdate(query);
				
				JOptionPane.showMessageDialog(this, "Insert Success");
				this.dispose();
			}
		}
	}

}