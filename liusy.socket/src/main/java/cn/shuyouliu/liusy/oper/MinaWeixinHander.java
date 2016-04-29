package cn.shuyouliu.liusy.oper;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.shuyouliu.liusy.LiusySocketServer;
import cn.shuyouliu.liusy.entity.Message;

public class MinaWeixinHander implements IoHandler {
	private static final Logger logger = LoggerFactory.getLogger(MinaWeixinHander.class);
	
	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1) throws Exception {
		arg1.printStackTrace();
		logger.debug(" exceptionCaught arg0"+arg0+"arg1"+arg1);
	}

	@Override
	public void messageReceived(IoSession session, Object arg1) throws Exception {
		// TODO Auto-generated method stub
		logger.debug(" messageReceived arg0"+session+"arg1"+arg1+arg1.toString());
		// 可以接受到信息。 
		if (arg1 != null && arg1 instanceof Message){
			Message msg = (Message)arg1;
			String str = msg.getMsg();
			if (str != null && str.indexOf("ping") != -1){
				session.write(msg);
			}
		}
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub
		logger.debug(" messageSent arg0"+arg0+"arg1"+arg1.toString());
	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		logger.debug(" sessionClosed arg0"+arg0);
		arg0.closeNow();
		
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
	public void inputClosed(IoSession arg0) throws Exception {
		logger.debug(" inputClosed arg0"+arg0);
		arg0.closeNow();//.close(true);
	}
	

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		CharsetEncoder encoder = Charset.forName("utf-8").newEncoder();
		logger.debug("one client connect:" + session);
		//

		String jsonWeixins ="{\"type\":\"wechatInfo\",\"AccountList\":[{\"aliasname\":\"登录ddddddddddddddddddddddddddddddddddd xxx\",\"passwd\":\"********\",\"myname\":\"wxid_vmc45zdh523y12\"},{\"aliasname\":\"登录222 xxx\",\"passwd\":\"**333******\",\"myname\":\"wxid_vm44444c45zdh523y12\"}]}";
		//String jsonWeixins ="{\"type\":\"handshake\",\"wechatInfo\":[{\"aliasname\":\"xxxx\",\"passwd\":\"fff\",\"myname\":\"nnnn\"},{\"aliasname\":\"xxsdfxx\",\"passwd\":\"ffsdf\",\"myname\":\"nndfnn\"}]}"; 
		/*OUTPUT(resetPinOutput)							
		字段名称		类型/结构元素	最大长度	默认值	必输	描述	说明（码表）
		type		string			Y	类型	
		wechatInfo		list			Y	微信信息	
									
		OUTPUT(wechatInfo信息数据结构)							
		字段名称		类型/结构元素	最大长度	默认值	必输	描述	说明（码表）
		aliasname		string			Y	登录名	
		passwd		string			Y	密码	
		myname		string			Y	微信号	
		*/
		
//		IoBuffer io = IoBuffer.allocate((int) (4 + jsonWeixins.length()));
//		io.putInt(jsonWeixins.length());
//		io.putString(jsonWeixins, encoder);
//		logger.debug("send remaining:" + io.limit());
//		io.flip();
		logger.debug("数据已经读取完毕，准备发送");
		//session.write(io);
		Message msg = new Message();
		msg.setAlonght(5);
		msg.setMsg(jsonWeixins);
		session.write(msg);
	}
	public static void main(String[] args) throws CharacterCodingException {
		CharsetEncoder encoder = Charset.forName("utf-8").newEncoder();
		String jsonWeixins ="{\"type\":\"wechatinfo\",\"AccountList\":[{\"aliasname\":\"x中文xxx\",\"passwd\":\"fff\",\"myname\":\"nnnn\"},{\"aliasname\":\"xxsdfxx\",\"passwd\":\"ffsdf\",\"myname\":\"nndfnn\"}]}";
		IoBuffer io = IoBuffer.allocate((int) (4 + jsonWeixins.length()));
		io.putInt(2);
		io.putString(jsonWeixins, encoder); 
		logger.debug("send remaining:" + io.limit());
		io.flip();
		logger.debug("数据已经读取完毕，准备发送");
		logger.debug(io.getHexDump()+io.toString());

	}
}
