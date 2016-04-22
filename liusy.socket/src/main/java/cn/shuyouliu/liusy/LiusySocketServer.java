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

import cn.shuyouliu.liusy.oper.MinaImageHander;
import cn.shuyouliu.liusy.oper.MyProtocalCodecFactory;

/**
 * @author zywx
 *
 */
public class LiusySocketServer {
	private static final Logger logger = LoggerFactory.getLogger(LiusySocketServer.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
			return "ok";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "fail";
	}

	private static final int PORT = 8002;

	public void _start() throws IOException {
		// LoggingFilter记录所有事件和请求
		// ProtocolCodecFilter将到来的ByteBuffer转换成消息对象（POJO）
		// CompressionFilter压缩数据
		// SSLFilter增加SSL – TLS – StartTLS支持

		acceptor = new NioSocketAcceptor();

		acceptor.getFilterChain().addLast("code", new ProtocolCodecFilter(new MyProtocalCodecFactory("utf-8")));
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());

		acceptor.setHandler(new MinaImageHander());

		acceptor.getSessionConfig().setReadBufferSize(2048);

		acceptor.bind(new InetSocketAddress(PORT));

		logger.info("Listeningon port " + PORT);

	}

	private IoAcceptor acceptor = null;

	public String getStatus() {
		if (acceptor != null) {
			return "running:" + acceptor.getActivationTime();
		}
		return "stop";
	}

}
