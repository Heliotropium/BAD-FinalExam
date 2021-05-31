import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

public class ConnectDB {

	public Connection con;
	public Statement stmt;
	public ResultSet rs;
	public ResultSetMetaData rsm;

	public ConnectDB() {

		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/snack_shop", "root", "");

			stmt = con.createStatement();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public ResultSet executeQuery(String query) {

		try {
			rs = stmt.executeQuery(query);
			rsm = (ResultSetMetaData) rs.getMetaData();
		} catch (Exception e) {
			System.out.println(e);
		}

		return rs;

	}

	public ResultSet executeUpdate(String query) {

		try {
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e);
		}

		return rs;

	}

	public int executeUpdates(String query) {
		int result = -1;

		try {
			result = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int insertStudent(int transid, int snackid, int qty) {
		int result = -1;
		String query = "INSERT INTO Students VALUES (?, ?, ?)";
		try {
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, transid);
			preparedStatement.setInt(2, snackid);
			preparedStatement.setInt(3, qty);
			result = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<Cart> getCarts() {
		ArrayList<Cart> cartList = new ArrayList<>();
		String query = "SELECT cart.SnackId as SnackId, SnackName as SnackName, Quantity as Quantity\r\n"
				+ "FROM cart, snack\r\n" + "WHERE cart.SnackId = snack.SnackId";

		rs = executeQuery(query);

		try {
			while (rs.next()) {

				int snackid = Integer.parseInt(rs.getString("SnackId"));
				String snackname = rs.getString("SnackName");
				int qty = Integer.parseInt(rs.getString("Quantity"));

				cartList.add(new Cart(snackid, snackname, qty));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cartList;
	}

	public ArrayList<Snack> getSnacksBuy() {
		ArrayList<Snack> snackList = new ArrayList<>();
		String query = "SELECT SnackId, SnackName from snack";

		rs = executeQuery(query);

		try {
			while (rs.next()) {

				int snackid = Integer.parseInt(rs.getString("SnackId"));
				String snackname = rs.getString("SnackName").toString();

				snackList.add(new Snack(snackid, snackname));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return snackList;
	}

	public int deleteStudent(String snackid) {
		int result = -1;
		String query = "Delete From snack WHERE snackid = ?";

		try {
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, snackid);
			result = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList<Transaction> getTrans() {
		ArrayList<Transaction> transactionList = new ArrayList<>();

		String query = "SELECT headertransaction.TransactionId, SUM(SnackPrice*Quantity) as TotalPrice, TransactionDate FROM snack INNER JOIN detailtransaction ON snack.SnackId = detailtransaction.SnackId INNER JOIN headertransaction ON detailtransaction.TransactionId = headertransaction.TransactionId GROUP BY headertransaction.TransactionId";

		rs = executeQuery(query);

		try {
			while (rs.next()) {
				int transId = rs.getInt("TransactionId");
				int totalPrice = rs.getInt("TotalPrice");
				String transDate = rs.getString("TransactionDate");

				transactionList.add(new Transaction(transId, totalPrice, transDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return transactionList;
	}

	public Vector<Detail> getDetails(int transIds) {
		Vector<Detail> detailList = new Vector<>();

		String query = "SELECT SnackName, SnackPrice, Quantity FROM snack INNER JOIN detailtransaction ON snack.SnackId = detailtransaction.SnackId WHERE TransactionId = "
				+ transIds + "";

		rs = executeQuery(query);

		try {
			while (rs.next()) {
				String snackName = rs.getString("SnackName");
				int snackPrice = rs.getInt("SnackPRice");
				int qty = rs.getInt("Quantity");

				detailList.add(new Detail(snackName, snackPrice, qty));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return detailList;
	}

	public ArrayList<Snack> getSnacks() {
		ArrayList<Snack> snackList = new ArrayList<>();

		String query = "SELECT * FROM snack";

		rs = executeQuery(query);

		try {
			while (rs.next()) {
				int snackId = rs.getInt("SnackId");
				String snackName = rs.getString("SnackName");
				int snackPrice = rs.getInt("SnackPrice");

				snackList.add(new Snack(snackId, snackName, snackPrice));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return snackList;
	}

	public ArrayList<Users> getUsers() {
		ArrayList<Users> usersList = new ArrayList<>();

		String query = "SELECT * FROM user";

		rs = executeQuery(query);

		try {
			while (rs.next()) {
				int usersId = rs.getInt("UserId");
				String usersName = rs.getString("UserName");
				String usersPassowrd = rs.getString("UserPassword");
				String usersDOB = rs.getString("UserDOB");
				String usersEmail = rs.getString("UserEmail");
				String usersAddress = rs.getString("UserAddress");
				int userRole = rs.getInt("RoleId");
				String usersRole = "";
				if (userRole == 1) {
					usersRole = "User";
				} else if (userRole == 2) {
					usersRole = "Admin";
				}
				String usersGender = rs.getString("UserGender");
				usersList.add(new Users(usersId, usersName, usersPassowrd, usersDOB, usersEmail, usersAddress,
						usersRole, usersGender));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return usersList;
	}

}