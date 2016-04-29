package cn.shuyouliu.liusy.oper;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @see 编码类，对要发送的数据进行编码，封装成byte数组发送到服务端
 * @author Herman.Xiong
 * @date 2014年6月19日 15:37:33
 */
public class MyProtocalEncoder extends ProtocolEncoderAdapter {
	private static final Logger logger = LoggerFactory.getLogger(MyProtocalEncoder.class);
	private final String charset;

	public MyProtocalEncoder(String charset) {
		this.charset = charset;
	}

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		logger.debug(message.toString()+"   encodeing . message ");
		try {
			IoBuffer ioBuf = IoBuffer.allocate(message.toString().length()+4);
			CharsetEncoder encoder = Charset.forName(charset).newEncoder();
			ioBuf.setAutoExpand(true);
			String str = message.toString();
			byte[] receiveBytes = new byte[str.length() + 4];// 收到的包字节数组
			byte[] x = PackUtils.intToBytes2(str.length());
			ioBuf.put(x,0,4);
			
			for (int i = 0; i < x.length; i++) {
				receiveBytes[i] = x[i];
			}
			byte[] y = str.getBytes("utf-8");
			ioBuf.put(y, 0, str.length());
			
			for (int i = 0; i < y.length; i++) {
				receiveBytes[4+i] = y[i];
			}
			ioBuf.flip();
			out.write(ioBuf);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

	public void dispose() throws Exception {
	}
}
