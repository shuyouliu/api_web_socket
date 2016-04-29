package cn.shuyouliu.liusy.oper;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.shuyouliu.liusy.entity.Message;

/**
 * @see 解码类
 * @author Herman.Xiong
 * @date 2014年6月19日 15:36:59
 */

public class MyProtocalDecoder extends CumulativeProtocolDecoder {
	private static final Logger logger = LoggerFactory.getLogger(MyProtocalDecoder.class);
	private final String charset;

	public MyProtocalDecoder(String charset) {
		this.charset = charset;
	}

	@Override
	/**
	 * 下面这个方法注意返回值，返回true表示已经够一个对象了，转交给hander处理 返回false表示数据不够，等待下一个数据包到达后一起处理。
	 */
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		logger.debug("run in ... decode");
		CharsetDecoder decoder = Charset.forName(charset).newDecoder();
		int smsLength = 0;
		int pos = in.position();
		logger.debug("limit:" + in.limit());
		int remaining = in.remaining();
		logger.debug("remaining:" + remaining);
		try {
			// 判断长度4位 可配置
			if (remaining < 4) {
				in.position(pos);
				// in.limit(limit);
				return false;
			}
			
			byte [] b4 = new byte[4];
			in.get(b4);
			logger.debug(" 4 start : "+Arrays.toString( b4));
			smsLength = PackUtils.bytesToInt2(b4, 0);
			// 判断是否够解析出的长度
			logger.debug("smsLength "+ smsLength);
			if (remaining < smsLength || smsLength < 0) {
				in.position(pos);
				return false;
			}
			byte [] dst = new byte[remaining];
			//in.position(4);
			in.get(dst,0,smsLength);
			logger.debug(" sms data : "+Arrays.toString( dst));
			Message mes = new Message();
			mes.setMsg(new String(dst));
			logger.debug(mes.getMsg());
			out.write(mes);
		} catch (Exception e) {
			in.position(pos);
			// in.limit(limit);
			e.printStackTrace();
			logger.debug("error:"+e.getMessage());
			return false;
		}
		return true;
	}

}
