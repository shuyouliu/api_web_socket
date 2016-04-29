/**
 * Date:2016年4月21日
 * Author:zywx
 * Email:shuyouliu@126.com
 */
package cn.shuyouliu.liusy;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.shuyouliu.liusy.oper.MinaWeixinHander;
import cn.shuyouliu.liusy.oper.MyProtocalCodecFactory;
import cn.shuyouliu.liusy.oper.PackUtils;

/**
 * @author zywx
 *
 */
public class LiusySocketServer {
	private static final Logger logger = LoggerFactory.getLogger(LiusySocketServer.class);

	private static String version = "0.3";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LiusySocketServer.getLss().start();
		
		int x = 4;
		
		System.out.println(Integer.toHexString(x));
		byte xe[] = PackUtils.intToBytes2(x);
		for (int i = 0; i < xe.length; i++) {
			System.out.println(xe[i]);
		}
		
		int y = PackUtils.bytesToInt2(xe, 0);
		System.out.println(y);
	}

	private static LiusySocketServer lss = null;

	private LiusySocketServer() {
		logger.info("start socket server ****************");
	}

	public static LiusySocketServer getLss() {
		if (lss == null) {
			lss = new LiusySocketServer();
		}
		return lss;
	}

	/**
	 * socket Server 启动
	 */
	public static String start() {
		try {
			getLss()._start();
			logger.info("-------*********************----------:socket start");
			return "ok v"+version;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "fail";
	}
 
	private static final int PORT = 3016;

	
	public void _start() throws IOException {
		// LoggingFilter记录所有事件和请求
		// ProtocolCodecFilter将到来的ByteBuffer转换成消息对象（POJO）
		// CompressionFilter压缩数据
		// SSLFilter增加SSL – TLS – StartTLS支持

		acceptor = new NioSocketAcceptor();

		acceptor.getFilterChain().addLast("code", new ProtocolCodecFilter(new MyProtocalCodecFactory("utf-8")));
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());

		acceptor.setHandler(new MinaWeixinHander());

		acceptor.getSessionConfig().setReadBufferSize(2048);

		acceptor.bind(new InetSocketAddress(PORT));

		logger.info("Listeningon port " + PORT);

	}

	private IoAcceptor acceptor = null;

	public String getStatus() {
		if (acceptor != null) {
			return "v1.3 running:" + acceptor.getActivationTime();
		}
		return "stop v"+version;
	}

	public void _stop() {
		//acceptor.unbind();
		acceptor.dispose(true);
		
	}

}
