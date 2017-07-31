package com.bkav.home.data;

public class ValidString {
	public ValidString(String string) {
		this.string = validate(string);
	}
	
	public String getString() {
		return this.string;
	}

	private String string;
	
	public static String validate(String input) {
		StringBuilder builder = new StringBuilder();
		char[] arr = input.toCharArray();
		for (int index = 0; index < arr.length; index++) {
			switch (arr[index]) {
			case '{': case '}': case '[': case ']': case ',': case ':': case '\\':
				builder.append('\\');
				builder.append(arr[index]);
				break;
			default:
				builder.append(arr[index]);
			}
		}
		
		return builder.toString();
	}
}
