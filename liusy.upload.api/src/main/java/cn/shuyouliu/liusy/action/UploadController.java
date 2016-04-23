
package cn.shuyouliu.liusy.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author zywx
 */

@Component
@Path("/upload")
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
	@Path("status")
	public List<String> getStart() {
		List<String> list = new ArrayList<String>();
		list.add("status:" + "running");
		return list;
	}

	/**
	 * Constants operating with images
	 */
	private static final String ARTICLE_IMAGES_PATH = "C:/tmp/";
	// private static final String JPG_CONTENT_TYPE = "image/jpeg";
	// private static final String PNG_CONTENT_TYPE = "image/png";

	/**
	 * 第一种方式上传
	 *
	 * @param fileInputStream
	 * @param disposition
	 * @return
	 */
	@POST
	@Path("uploadStream")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
	public Response uploadStream(@FormDataParam("file") InputStream fis,
			@FormDataParam("file") FormDataContentDisposition fdc) {

		// 获取文件流
		// FormDataBodyPart filePart = form.getField("file");
		// 获取表单的其他数据
		// FormDataBodyPart filenamePart = form.getField("otherparam");

		// ContentDisposition headerOfFilePart = filePart.getContentDisposition();
		// 把表单内容转换成流
		// InputStream fis = filePart.getValueAs(InputStream.class);

		// FormDataContentDisposition formDataContentDisposition = filePart.getFormDataContentDisposition();
		//
		String source = fdc.getFileName();
		try {
			source = new String(source.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//
		// @FormDataParam("file") InputStream fis,
		// @FormDataParam("file") FormDataContentDisposition disposition
		String imageName = Calendar.getInstance().getTimeInMillis() + "/" + source;
		File file = new File(ARTICLE_IMAGES_PATH + imageName);
		try {
			// 使用common io的文件写入操作
			FileUtils.copyInputStreamToFile(fis, file);
			// 原来自己的文件写入操作
			// saveFile(fileInputStream, file);
		} catch (IOException ex) {
			logger.error(UploadController.class.getName() + ex.getMessage() + ex.hashCode());
			return Response.status(200).entity("fail:" + ex.getMessage()).build();
		}
		logger.info("result:" + imageName);
		return Response.status(200).entity("success:" + imageName).build();
	}

	/**
	 * * 第二种方式上传 使用FormDataMultiPart 获取表单数据
	 *
	 * @param form
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	// @POST
	// @Path("uploadimage2")
	// @Consumes(MediaType.MULTIPART_FORM_DATA)
	// // @Produces(MediaType.APPLICATION_JSON)
	// public Response uploadimage2(FormDataMultiPart form, @Context HttpServletResponse response)
	// throws UnsupportedEncodingException {
	// // 获取文件流
	// FormDataBodyPart filePart = form.getField("file");
	// // 获取表单的其他数据
	// FormDataBodyPart usernamePart = form.getField("username");
	//
	// // ContentDisposition headerOfFilePart = filePart.getContentDisposition();
	// // 把表单内容转换成流
	// InputStream fileInputStream = filePart.getValueAs(InputStream.class);
	//
	// FormDataContentDisposition formDataContentDisposition = filePart.getFormDataContentDisposition();
	//
	// String source = formDataContentDisposition.getFileName();
	// String result = new String(source.getBytes("ISO8859-1"), "UTF-8");
	//
	// System.out.println("formDataContentDisposition.getFileName()result " + result);
	//
	// String filePath = ARTICLE_IMAGES_PATH + result;
	// File file = new File(filePath);
	// System.out.println("file " + file.getAbsolutePath());
	// try {
	// // 保存文件
	// FileUtils.copyInputStreamToFile(fileInputStream, file);
	// // saveFile(fileInputStream, file);
	// } catch (IOException ex) {
	// Logger.getLogger(UploadImageResource.class.getName()).log(Level.SEVERE, null, ex);
	// }
	// System.out.println("" + "images/" + result);
	//
	// response.setCharacterEncoding("UTF-8");
	//
	// Map map = new HashMap();
	//
	// map.put("src", result);
	//
	// return new Viewable("/showImg", map);
	// }

	/**
	 *
	 * 不从web服务器去读图片,在磁盘某个目录的文件可以通过流的方式去获取 ,通过 response.getOutputStream()放回数据
	 *
	 * @param imageName
	 *            image-name
	 * @param type
	 *            extension of image
	 * @param response
	 *            {@link HttpServletResponse}
	 * @throws IOException
	 */
	// @GET
	// @Path("/images/{name}.{type}")
	// public void showImg(@PathParam("name") String imageName, @PathParam("type") String type,
	// @Context HttpServletResponse response) throws IOException {
	// // System.out.println("showImg");
	// // try (InputStream in = new FileInputStream(ARTICLE_IMAGES_PATH + imageName + "." + type)) {
	// // FileUtils.copyFile(new File(ARTICLE_IMAGES_PATH + imageName + "." + type), response.getOutputStream());
	// // // FileCopyUtils.copy(in, response.getOutputStream());
	// // }
	// }

	// 保存文件信息到磁盘
	// private void saveFile(InputStream uploadedInputStream, File file) {
	// System.out.println("------saveFile-----");
	// try {
	// OutputStream outpuStream = new FileOutputStream(file);
	// int read = 0;
	// byte[] bytes = new byte[1024];
	// // outpuStream = new FileOutputStream(new File(serverLocation));
	// while ((read = uploadedInputStream.read(bytes)) != -1) {
	// outpuStream.write(bytes, 0, read);
	// }
	// outpuStream.flush();
	// outpuStream.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
}
