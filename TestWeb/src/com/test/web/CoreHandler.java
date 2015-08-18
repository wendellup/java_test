package com.test.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.egame.client.EGameClientV2;
import cn.egame.common.model.PageData;
import cn.egame.common.util.Utils;
import cn.egame.interfaces.EnumType.GameType;
import cn.egame.interfaces.EnumType.SearchSortType;
import cn.egame.interfaces.gc.ExtraInfo;

public class CoreHandler extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5100278616539041215L;
	
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String webRoot = this.getServletContext().getRealPath("/WEB-INF");
        Utils.setAppRoot(webRoot);
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
		
		if ("/testGet".equals(action)) {
			try {
				PageData pageData = EGameClientV2.getInstance().pageGameIdByChannelId(
						0, 0, GameType.mobile, 1, 701, 0, 20,
						SearchSortType.downCountWeek,new ExtraInfo());
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			}
		}
		
		try {
			response.sendRedirect("test.jsp");
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
