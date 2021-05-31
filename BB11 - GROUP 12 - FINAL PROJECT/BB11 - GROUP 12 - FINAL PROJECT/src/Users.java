public class Users {
	int userId;
	String userName, userPassword, userDOB, userEmail, userAddress, userRole, userGender;
	
	public Users(int userId, String userName, String userPassword, String userDOB, String userEmail, String userAddress,
			String userRole, String userGender) {
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userDOB = userDOB;
		this.userEmail = userEmail;
		this.userAddress = userAddress;
		this.userRole = userRole;
		this.userGender = userGender;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserDOB() {
		return userDOB;
	}
	public void setUserDOB(String userDOB) {
		this.userDOB = userDOB;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	
	
}