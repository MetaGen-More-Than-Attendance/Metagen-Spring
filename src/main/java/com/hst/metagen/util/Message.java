package com.hst.metagen.util;

public class Message {

	public static final String DISCONTINUOUS = "You are failed because of the discontinuous attendance!";

	public static String getLeftAttendanceMessage(int days) {
		return String.format("You have only %d days left for not failing", days);
	}
}
