package com.billjc.framework;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.billjc.framework.util.Constant;
import com.billjc.framework.util.SessionUtil;
import com.billjc.speak.users.entity.User;

public class SessionFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");

		try {
			User user = SessionUtil.getLoginUser(req);
			String req_uri = req.getRequestURI().toUpperCase();
			if (req_uri.indexOf("/PASSPORT/CHECKLOGIN.DO") > -1
					|| req_uri.indexOf("/PASSPORT/LOGOUT.DO") > -1
					|| req_uri.indexOf("LOGOUT.JSP") > -1
					|| req_uri.indexOf("/STUDENTS/CHECKWW_NUM.DO") > -1
					|| req_uri.indexOf("/STUDENTS/CHECKQQ_NUM.DO") > -1
					|| req_uri.indexOf("/STUDENTS/CHECKPHONE.DO") > -1
					|| req_uri.indexOf("/STUDENTS/CHECKSKYPE_NUM.DO") > -1
					|| req_uri.indexOf("/STUDENTS/CHECKMAIL.DO") > -1
					|| req_uri.indexOf("/STUDENTS/ADDSTUDENT.DO") > -1
					|| req_uri.indexOf("/TAOBAO/REFRESH.DO") > -1
					|| null != user) {
				chain.doFilter(request, response);
			} else {
				PrintWriter out = response.getWriter();
				out.println("<script language=\"JavaScript\" type=\"text/javascript\">alert('\u8bf7\u767b\u5f55\u540e\u518d\u8fdb\u884c\u64cd\u4f5c!');window.top.location.href = \""
						+ req.getContextPath() + "/login.jsp\"</script>");
				out.close();
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
	}

	public void init(FilterConfig config) throws ServletException {

	}
}
