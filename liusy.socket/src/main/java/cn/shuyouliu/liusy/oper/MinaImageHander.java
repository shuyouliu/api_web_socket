package cn.shuyouliu.liusy.oper;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.shuyouliu.liusy.LiusySocketServer;
import cn.shuyouliu.liusy.entity.Message;

public class MinaImageHander implements IoHandler {
	private static final Logger logger = LoggerFactory.getLogger(MinaImageHander.class);
	
	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1) throws Exception {
		arg1.printStackTrace();
		logger.debug(" exceptionCaught arg0"+arg0+"arg1"+arg1);
	}

	@Override
	public void messageReceived(IoSession arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub
		logger.debug(" messageReceived arg0"+arg0+"arg1"+arg1);
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub
		logger.debug(" messageSent arg0"+arg0+"arg1"+arg1);
	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		logger.debug(" sessionClosed arg0"+arg0);
	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		logger.debug(" sessionCreated arg0"+arg0);
	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		// TODO Auto-generated method stub
		logger.debug(" sessionIdle arg0"+arg0);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		CharsetEncoder encoder = Charset.forName("utf-8").newEncoder();
		System.out.println("one client connect:" + session);
		// 检测到有连接了，服务器就可以发生图片给客服端了，在什么地方发送根据自己情况而定
		Message mes = new Message();
		FileInputStream fileInputStream = new FileInputStream("C:\\Weixin\\minaImage\\minaImage\\Chrysanthemum.jpg");
		FileChannel channel = fileInputStream.getChannel();
		String jsonStr = "{\"ack\":\"true\", \"command\":\"getstandbydata\", \"dcsn\":\"sn-001\", \"id\":\"2001\"}";

		mes.setImagename(jsonStr);
		mes.setImagenamelongth(mes.getImagename().length());
		ByteBuffer bytebuffer = ByteBuffer.allocate((int) channel.size());
		bytebuffer.clear();
		channel.read(bytebuffer);
		mes.setImagelongth(channel.size());
		mes.setImage(bytebuffer.array());
		mes.setAlonght((int) (4 + 4 + 8 + mes.getImagelongth() + mes.getImagenamelongth()));
		System.out.println("limit:" + bytebuffer.limit());
		System.out.println("mes.getImagelongth():" + mes.getImagelongth());
		IoBuffer io = IoBuffer.allocate((int) (4 + 4 + 8 + mes.getImagelongth() + mes.getImagenamelongth()));
		io.putInt(mes.getAlonght());
		io.putInt(mes.getImagenamelongth());
		io.putString(mes.getImagename(), encoder);
		io.putLong(mes.getImagelongth());
		io.put(bytebuffer);
		io.put(mes.getImage());
		System.out.println("send remaining:" + io.limit());
		io.flip();
		System.out.println("数据已经读取完毕，准备发送");
		session.write(io);
	}

	@Override
	public void inputClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
