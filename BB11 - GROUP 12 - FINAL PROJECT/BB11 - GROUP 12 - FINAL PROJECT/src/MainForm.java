import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MainForm extends JFrame implements ActionListener, MenuListener {

	JDesktopPane pane = new JDesktopPane();

	JMenuBar barUser, barAdmin, barMain;
	JMenu userMenu, buyMenu, transMenu, manageMenu;
	JMenuItem loginItem, registerItem, logoutItem, exitItem, manageSnackItem, manageUserItem;

	User user;

	public MainForm() {

		initialized();

	}

	public void initialized() {
		userMenu = new JMenu("User");

		loginItem = new JMenuItem("Login");
		registerItem = new JMenuItem("Register");
		logoutItem = new JMenuItem("Logout");
		exitItem = new JMenuItem("Exit");

		userMenu.add(loginItem);
		userMenu.add(registerItem);
		userMenu.add(logoutItem);
		userMenu.add(new JSeparator());
		userMenu.add(exitItem);

		buyMenu = new JMenu("Buy");

		transMenu = new JMenu("Transaction");

		manageMenu = new JMenu("Manage");
		manageSnackItem = new JMenuItem("Manage Snack");
		manageUserItem = new JMenuItem("Manage User");
		manageMenu.add(manageSnackItem);
		manageMenu.add(manageUserItem);

		if (user.roleid == 1) {
			barUser = new JMenuBar();
			barUser.add(userMenu);
			barUser.add(buyMenu);
			barUser.add(transMenu);
			barUser.setVisible(true);
			loginItem.setVisible(false);
			registerItem.setVisible(false);
			logoutItem.setVisible(true);
			setJMenuBar(barUser);
		} else if (user.roleid == 2) {
			barAdmin = new JMenuBar();
			barAdmin.add(userMenu);
			barAdmin.add(manageMenu);
			loginItem.setVisible(false);
			registerItem.setVisible(false);
			logoutItem.setVisible(true);
			setJMenuBar(barAdmin);
		} else if (user.roleid == 0) {
			logoutItem.setVisible(false);
			barMain = new JMenuBar();
			barMain.add(userMenu);
			setJMenuBar(barMain);
		}

		loginItem.addActionListener(this);
		registerItem.addActionListener(this);
		logoutItem.addActionListener(this);
		exitItem.addActionListener(this);
		buyMenu.addMenuListener(this);
		transMenu.addMenuListener(this);
		manageSnackItem.addActionListener(this);
		manageUserItem.addActionListener(this);

		setTitle("Main Form");
		setVisible(true);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(pane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == loginItem) {
			LoginForm login = new LoginForm(this);
			login.setVisible(true);
			pane.add(login);
		} else if (e.getSource() == registerItem) {
			RegisterForm regis = new RegisterForm();
			regis.setVisible(true);
			pane.add(regis);
		} else if (e.getSource() == logoutItem) {
			user.clearUser();
			JOptionPane.showMessageDialog(this, "Account has been logout!");
			initialized();
		} else if (e.getSource() == manageSnackItem) {
			ManageSnackForm managesnack = new ManageSnackForm();
			managesnack.setVisible(true);
			pane.add(managesnack);
		} else if (e.getSource() == manageUserItem) {
			ManageUserForm manageuser = new ManageUserForm();
			manageuser.setVisible(true);
			pane.add(manageuser);
		} else {
			dispose();
		}

	}

	@Override
	public void menuSelected(MenuEvent e) {
		if (e.getSource() == buyMenu) {
			BuySnackForm buysnack = new BuySnackForm();
			buysnack.setVisible(true);
			pane.add(buysnack);
		} else if (e.getSource() == transMenu) {
			TransactionForm trans = new TransactionForm();
			trans.setVisible(true);
			pane.add(trans);
		}

	}

	@Override
	public void menuDeselected(MenuEvent e) {

	}

	@Override
	public void menuCanceled(MenuEvent e) {

	}

}