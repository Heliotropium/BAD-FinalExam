public class User {

	public static String emailUser, password, fullNameUser;
	public static int roleid, userid;
	
	public static void setUser(int iduser, String email, String pass, int role, String fullName) {
		userid = iduser;
		emailUser = email;
		password = pass;
		roleid = role;
		fullNameUser = fullName;
		
	}
	
	public static void clearUser() {
		
		emailUser = null;
		password = null;
		roleid = 0;
		fullNameUser = null;
		
	}

}
