import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LoginForm extends JInternalFrame implements ActionListener {
	MainForm mainform;
	JPanel pnlheader, pnlisi, pnlfooter;
	JLabel lbltitle, lblemail, lblpassword;
	JTextField txtemail;
	JPasswordField txtpassword;
	JButton loginButton;
	
	
	ConnectDB condb = new ConnectDB();

	public LoginForm(MainForm mainform) {
		super("Login", false, true, false);
		
		this.mainform = mainform;
		
		pnlheader = new JPanel();
		pnlisi = new JPanel(new GridLayout(2, 2));
		pnlfooter = new JPanel();

		lbltitle = new JLabel("Login");
		lblemail = new JLabel("Email");
		lblpassword = new JLabel("Password");

		txtemail = new JTextField();
		txtpassword = new JPasswordField();
		loginButton = new JButton("Login");

		pnlheader.add(lbltitle);
		pnlisi.add(lblemail);
		pnlisi.add(txtemail);

		pnlisi.add(lblpassword);
		pnlisi.add(txtpassword);

		pnlfooter.add(loginButton);

		add(pnlheader, BorderLayout.NORTH);
		add(pnlisi, BorderLayout.CENTER);
		add(pnlfooter, BorderLayout.SOUTH);

		loginButton.addActionListener(this);

		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 200);
		setResizable(false);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == loginButton) {
			String email = txtemail.getText();
			String password = new String(txtpassword.getPassword());
			int roleid = 0, iduser = 0;
			String username = "";
			String query = "SELECT * FROM user WHERE UserEmail = '"+email+"' AND UserPassword = '"+password+"'";
			boolean check = true;
			
			String error = "";
			
			if(email.isEmpty() || password.isEmpty()) {
				error = "Harus diisi semua boss!";
				check = false;
			}else {
				condb.rs = condb.executeQuery(query);
				try {
					if(condb.rs.next() == false) {
						JOptionPane.showMessageDialog(this, "Kurang tepat!");
						check = false;
					}else {
						try {
							roleid = condb.rs.getInt("RoleId");
						} catch (Exception e2) {
						}
						
						try {
							username = condb.rs.getString("UserName").toString();
						} catch (SQLException e2) {
						}
						try {
							iduser = condb.rs.getInt("UserId");
						} catch (SQLException e2) {
						}
					}
					
				} catch (SQLException e2) {
				}
			}
			
			if(!error.isEmpty()) {
				JOptionPane.showMessageDialog(this, error);
			}else if(check == true){
				User.setUser(iduser, email, password, roleid, username);
				JOptionPane.showMessageDialog(this, "Welcome, " + username);
				mainform.initialized();
				this.dispose();
			}
		}
		
		
	}

}