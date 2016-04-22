package cn.shuyouliu.liusy.oper;

import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * @see 编码类，对要发送的数据进行编码，封装成byte数组发送到服务端
 * @author Herman.Xiong
 * @date 2014年6月19日 15:37:33
 */
public class MyProtocalEncoder extends ProtocolEncoderAdapter {
	private final String charset;

	public MyProtocalEncoder(String charset) {
		this.charset = charset;
	}

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {

		ByteBuffer b = null;

		if (b != null) {

			b.flip();
			IoBuffer ioBuf = IoBuffer.wrap(b);
			out.write(ioBuf);
		}

	}

	public void dispose() throws Exception {
	}
}
