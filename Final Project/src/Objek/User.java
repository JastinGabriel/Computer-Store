package Objek;

public class User {

	public String UserID,UserName, UserEmail, UserPassword, UserDOB, UserGender, UserAddress, UserPhone, UserRole;
	
	public User(String userID, String userName, String userEmail, String userPassword, String userDOB,
			String userGender, String userAddress, String userPhone, String userRole) {

		this.UserID = userID;
		this.UserName = userName;
		this.UserEmail = userEmail;
		this.UserPassword = userPassword;
		this.UserDOB = userDOB;
		this.UserGender = userGender;
		this.UserAddress = userAddress;
		this.UserPhone = userPhone;
		this.UserRole = userRole;
	}

	public User() {
		
	}

}
