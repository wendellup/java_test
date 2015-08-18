package com.test.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

//import org.apache.tomcat.util.http.fileupload.FileItem;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

public class FileUploadHandler extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5100278616539041215L;
	
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long beginMillis = System.currentTimeMillis();
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("content/text;charset=utf-8");
		String uri = request.getRequestURI();
		String action = uri.substring(uri.lastIndexOf("/"), uri
				.lastIndexOf("."));
		System.out.println(action);
		
		String fileName = null;
		double fileSize = 0;
		int cnt = 0;
		
		if ("/upload".equals(action)) {
			try {
				if (request.getContentType() != null) {
					// 请求头不为空
					DiskFileItemFactory fif = new DiskFileItemFactory();
					ServletFileUpload sfu = new ServletFileUpload(fif);

					// 解析完以后, 每个表单域中的数据会封装到
					// 一个对应的 FileItem 对象里面
					List<FileItem> items = sfu.parseRequest(request);
					for (int i = 0; i < items.size(); i++) {
						FileItem curr = items.get(i);
						if (curr.isFormField()) {
							// 普通的表单域
						} else {
							cnt++;
							// 上传文件
							// 获得实际部署时的物理路径
							String path = getServletContext().getRealPath(
									"upload");
							File pathFile = new File(path);
							if (!pathFile.exists()) {
								pathFile.mkdirs();
							}
							// 获得原来的文件的名称
							fileName = curr.getName();
							String path2 = path + File.separator + System.currentTimeMillis()+"_"+fileName;
							System.out.println(path2);
							//持久化上传的图片的路径
							
							File file = new File(path2);
							curr.write(file);
							if (file.exists() && file.isFile()){ 
								fileSize = file.length();
							}
						}
					}
				}

				// 转发到 userDetail.jsp 界面
//				request.setAttribute("pictures", pictures);
//				request.setAttribute("user", findUser);
//				out.print("上传成功");
//				out
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			}

		}
		
		if(cnt==1){
			long endMillis = System.currentTimeMillis();
			long costMillis = endMillis - beginMillis;
			System.out.println("文件名:"+fileName+",文件大小:"+fileSize/1024/1024+"MB,上传花费时间:"+costMillis);
//			request.setAttribute("fileName", fileName);
//			request.setAttribute("fileSize", fileSize/1024/1024);
//			request.setAttribute("cost", costMillis);
			String outStr = "文件名:"+fileName+",文件大小:"+fileSize/1024/1024+"MB,上传花费时间:"+costMillis;
			request.getSession().setAttribute("outputStr", outStr);
		}
		try {
			response.sendRedirect("testFileUpload.jsp");
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
