
package cn.shuyouliu.liusy.action;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.shuyouliu.liusy.LiusySocketServer;

/**
 * 
 * @author zywx
 */

@Component
@Path("/index")
public class IndexController {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
	@Path("start")
	public List<String> getStart() {
		String status = LiusySocketServer.start();
		List<String> list = new ArrayList<String>();
		list.add("status:" + status);
		return list;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
	@Path("status")
	public List<String> getStatus() {
		String status = LiusySocketServer.getLss().getStatus();
		List<String> list = new ArrayList<String>();
		list.add("status:" + status);
		return list;
	}
}
