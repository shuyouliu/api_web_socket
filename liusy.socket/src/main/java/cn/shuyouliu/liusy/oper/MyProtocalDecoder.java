package cn.shuyouliu.liusy.oper;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import cn.shuyouliu.liusy.entity.Message;

/**
 * @see 解码类
 * @author Herman.Xiong
 * @date 2014年6月19日 15:36:59
 */

public class MyProtocalDecoder extends CumulativeProtocolDecoder {
	private final String charset;

	public MyProtocalDecoder(String charset) {
		this.charset = charset;
	}

	@Override
	/**
	 * 下面这个方法注意返回值，返回true表示已经够一个对象了，转交给hander处理 返回false表示数据不够，等待下一个数据包到达后一起处理。
	 */
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		System.out.println("进入decode");
		CharsetDecoder decoder = Charset.forName(charset).newDecoder();
		int smsLength = 0;
		int pos = in.position();
		System.out.println("limit:" + in.limit());
		int remaining = in.remaining();
		System.out.println("remaining:" + remaining);
		try {

			// 判断长度4位 可配置
			if (remaining < 4) {
				in.position(pos);
				// in.limit(limit);
				return false;
			}

			// 判断是否够解析出的长度
			smsLength = in.getInt();
			System.out.println(smsLength);
			if (remaining < smsLength || smsLength < 0) {
				in.position(pos);
				return false;
			}
			Message mes = new Message();
			int alonght = in.getInt(0);
			System.out.println("alongth:" + alonght);
			mes.setAlonght(alonght);
			int namelongth = in.getInt();
			System.out.println("namelonth:" + namelongth);
			mes.setImagelongth(namelongth);
			String name = in.getString(namelongth, decoder);
			System.out.println("name:" + name);
			mes.setImagename(name);
			long imagelongth = in.getLong();
			System.out.println("imagelongth:" + imagelongth);
			mes.setImagelongth(imagelongth);
			byte[] image = new byte[(int) imagelongth];
			in.get(image);
			mes.setImage(image);
			out.write(mes);

		} catch (Exception e) {
			in.position(pos);
			// in.limit(limit);
			return false;
		}
		return true;
	}

}
