/**
 * 
 */
package cn.shuyouliu.liusy.oper;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zywx
 *
 */
public class PackUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String content = "b1030000000040000f01030211ba040000000040";
		byte[] bytes = pack(content);
		System.out.println(unpack(bytes));
		System.out.println(new String(bytes));
		String s = MathUtil.bytesToHexString(bytes);
		System.out.println(s);
	}

	/**
	 * 打包字符串 类似php中pack在java中的实现
	 *
	 * @param str
	 * @return
	 */
	public static byte[] pack(String str) {
		int nibbleshift = 4;
		int position = 0;
		int len = str.length() / 2 + str.length() % 2;
		byte[] output = new byte[len];
		for (char v : str.toCharArray()) {
			byte n = (byte) v;
			if (n >= '0' && n <= '9') {
				n -= '0';
			} else if (n >= 'A' && n <= 'F') {
				n -= ('A' - 10);
			} else if (n >= 'a' && n <= 'f') {
				n -= ('a' - 10);
			} else {
				continue;
			}
			output[position] |= (n << nibbleshift);
			if (nibbleshift == 0) {
				position++;
			}
			nibbleshift = (nibbleshift + 4) & 7;
		}
		return output;
	}

	/**
	 * 16进制的字符解压 类php中unpack
	 *
	 * @param is
	 * @param len
	 * @return
	 * @throws IOException
	 */
	public static String unpack(InputStream is, int len) throws IOException {
		byte[] bytes = new byte[len];
		is.read(bytes);
		return unpack(bytes);
	}

	/***
	 * 16进制的字符解压 类php中unpack
	 * 
	 * @param bytes
	 * @return
	 */
	public static String unpack(byte[] bytes) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (bytes == null || bytes.length <= 0) {
			return null;
		}
		for (int i = 0; i < bytes.length; i++) {
			int v = bytes[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
	public static int readInt(byte[] buffer) {

		int position = 0;

		return ((buffer[position] & 0xFF)
		                | (buffer[position + 1] & 0xFF) << 8
		                | (buffer[position + 2] & 0xFF) << 16
		                | (buffer[position + 3] & 0xFF) << 24);

		}
	
	public static byte[] GetBytesFromInt32(int value) {

	byte[] buffer = new byte[4];
	for (int i = 0; i < 4; i++) {
	  buffer[i] = (byte) (value >> (8 * i));
	}

	return buffer;

	}
	/**  
	    * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和bytesToInt（）配套使用 
	    * @param value  
	    *            要转换的int值 
	    * @return byte数组 
	    */    
	public static byte[] intToBytes( int value )   
	{   
	    byte[] src = new byte[4];  
	    src[3] =  (byte) ((value>>24) & 0xFF);  
	    src[2] =  (byte) ((value>>16) & 0xFF);  
	    src[1] =  (byte) ((value>>8) & 0xFF);    
	    src[0] =  (byte) (value & 0xFF);                  
	    return src;   
	}  
	 /**  
	    * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。  和bytesToInt2（）配套使用 
	    */    
	public static byte[] intToBytes2(int value)   
	{   
	    byte[] src = new byte[4];  
	    src[0] = (byte) ((value>>24) & 0xFF);  
	    src[1] = (byte) ((value>>16)& 0xFF);  
	    src[2] = (byte) ((value>>8)&0xFF);    
	    src[3] = (byte) (value & 0xFF);       
	    return src;  
	} 
	/**  
	    * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序。 
	    *   
	    * @param ary  
	    *            byte数组  
	    * @param offset  
	    *            从数组的第offset位开始  
	    * @return int数值  
	    */    
	public static int bytesToInt_(byte[] ary, int offset) {  
	    int value;    
	    value = (int) ((ary[offset]&0xFF)   
	            | ((ary[offset+1]<<8) & 0xFF00)  
	            | ((ary[offset+2]<<16)& 0xFF0000)   
	            | ((ary[offset+3]<<24) & 0xFF000000));  
	    return value;  
	} 
    /**  
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用 
     *   
     * @param src  
     *            byte数组  
     * @param offset  
     *            从数组的第offset位开始  
     * @return int数值  
     */    
 public static int bytesToInt(byte[] src, int offset) {  
     int value;    
     value = (int) ((src[offset] & 0xFF)   
             | ((src[offset+1] & 0xFF)<<8)   
             | ((src[offset+2] & 0xFF)<<16)   
             | ((src[offset+3] & 0xFF)<<24));  
     return value;  
 }  
   
  /**  
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用 
     */  
 public static int bytesToInt2(byte[] src, int offset) {  
     int value;    
     value = (int) ( ((src[offset] & 0xFF)<<24)  
             |((src[offset+1] & 0xFF)<<16)  
             |((src[offset+2] & 0xFF)<<8)  
             |(src[offset+3] & 0xFF));  
     return value;  
 }  
	
}
