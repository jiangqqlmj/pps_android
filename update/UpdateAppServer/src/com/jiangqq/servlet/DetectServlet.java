package com.jiangqq.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jiangqq.bean.UpdateInformation;

/**
 * 进行检测应用程序的更新信息
 * @author jiangqingqing
 * 
 */
public class DetectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 UpdateInformation information=new UpdateInformation();
		 information.setLastForce(1);
		 information.setServerFlag(1);
		 //....
		 Gson gson=new Gson();
		 String json= gson.toJson(information);
		 PrintWriter pw=response.getWriter();
		 pw.write(json);
		 pw.flush();
		 pw.close();
	}

}
