package com.revature.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


import com.revature.service.WebAppService;


@SuppressWarnings("serial")
public class WebAppController extends HttpServlet{
	
	WebAppService serv;
	
	public WebAppController() {
		serv = new WebAppService();
	}
	
	public WebAppController(WebAppService newServ) {
		serv = newServ;
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		if(serv.writeTableJson(out)) {
			resp.setStatus(200);
		}else {
			resp.setStatus(500);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader read = req.getReader();
		serv.addRowFromJson(read);
		resp.setStatus(200);

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader read = req.getReader();
		serv.updateRowFromJson(read);
		resp.setStatus(200);

	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader read = req.getReader();
		serv.deleteRowFromJson(read);
		resp.setStatus(200);

	}
	
	
}
