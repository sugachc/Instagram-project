package com.cos.photosns.util;

public class Script {
	
	
	public static String back(String msg) {
		StringBuffer sb=new StringBuffer();
		sb.append("<script>");
		sb.append("alert('"+msg+"');");
		sb.append("history.back();");
		sb.append("</script>");
		return sb.toString(); //경고창 하나 띄우고 뒤로 돌아감
	}

}
