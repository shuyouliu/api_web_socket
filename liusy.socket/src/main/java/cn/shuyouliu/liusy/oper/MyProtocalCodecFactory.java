package cn.shuyouliu.liusy.oper;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * @see mina 编码、解码工厂
 * @author Herman.Xiong
 * @date 2014年6月19日 15:36:16
 *
 */
public class MyProtocalCodecFactory implements ProtocolCodecFactory {
	private final MyProtocalEncoder encoder; // 编码
	private final MyProtocalDecoder decoder; // 解码

	public MyProtocalCodecFactory(String charset) {
		encoder = new MyProtocalEncoder(charset);
		decoder = new MyProtocalDecoder(charset);
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) {
		return encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) {
		return decoder;
	}

}