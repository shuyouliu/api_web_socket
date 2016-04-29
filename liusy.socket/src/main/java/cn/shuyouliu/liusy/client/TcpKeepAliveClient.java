package cn.shuyouliu.liusy.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

import cn.shuyouliu.liusy.oper.PackUtils;

public class TcpKeepAliveClient {

	private String ip;

	private int port;

	private static Socket socket = null;

	private static int timeout = 50 * 1000;

	public TcpKeepAliveClient(String ip, int port) {

		this.ip = ip;

		this.port = port;

	}

	public void receiveAndSend() throws IOException {

		InputStream input = null;

		OutputStream output = null;

		try {

			//if (socket == null || socket.isClosed() || !socket.isConnected()) {

				socket = new Socket();

				InetSocketAddress addr = new InetSocketAddress(ip, port);

				socket.connect(addr, timeout);

				socket.setSoTimeout(timeout);

				System.out.println("TcpKeepAliveClientnew ");
				//socket.close();
			//}

			input = socket.getInputStream();

			String str = "{type:ping}";
			int len = 4;
			output = socket.getOutputStream();
			
			byte[] receiveBytes = new byte[1024];// 收到的包字节数组
			
			byte[] x = PackUtils.intToBytes2(str.length());
			for (int i = 0; i < x.length; i++) {
				receiveBytes[i] = x[i];
			}
			byte[] y = str.getBytes("utf-8");
			for (int i = 0; i < y.length; i++) {
				receiveBytes[4+i] = y[i];
			}
			len += y.length;
			
			output.write(receiveBytes, 0, len);

			output.flush();
			
			
			
			// read body

			//byte[] receiveBytes = {};// 收到的包字节数组

			while (true) {

				if (input.available() > 0) {

					receiveBytes = new byte[input.available()];

					input.read(receiveBytes);

					// send

					System.out.println( Arrays.toString( receiveBytes));
					System.out.println("TcpKeepAliveClientsend date :" + new String(receiveBytes));

					//output.write(receiveBytes, 0, receiveBytes.length);

					//output.flush();

				}

			}

		} catch (Exception e) {

			e.printStackTrace();

			System.out.println("TcpClientnew socket error");

		}

	}

	public static void main(String[] args) throws Exception {
		//http://115.28.52.132/liusy/index/start
		//for (int i = 0 ; i < 10000 ; i ++){
			TcpKeepAliveClient client = new TcpKeepAliveClient("qinxin.chinacloudapp.cn", 3016);//115.28.52.132
			client.receiveAndSend();
			Thread.sleep(1000);
		//}
		
	}

}