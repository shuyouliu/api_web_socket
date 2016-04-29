package cn.shuyouliu.liusy.oper;

public class MathUtil {

	public static String bytesToHexString(byte[] bytes) {
		String result = "";
		for (int i = 0; i < bytes.length; i++) {
			result += " "+Integer.toHexString(bytes[i] & 0x000000FF);
		}
		return result;
	}

}
