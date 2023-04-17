package com.busanit.petgathering.util;


public class CommonUtil {

	public static String getId(String email) {
		int index = email.indexOf("@");
		String id = "";
		if(email != null && index > 0 ) {
			id = email.substring(0, index);
		}else {
			id = email;
		}
			return id;
	}
}
