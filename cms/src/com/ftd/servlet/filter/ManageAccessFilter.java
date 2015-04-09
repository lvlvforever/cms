package com.ftd.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ftd.i18n.I18nMgr;
import com.ftd.manage.ManagerSession;
import com.ftd.servlet.Context;

public class ManageAccessFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		ManagerSession managerSession = (ManagerSession) req.getSession()
				.getAttribute(ManagerSession.KEY);
		if (managerSession != null) {
			chain.doFilter(request, response);
		} else {
			String lang = (String) req.getAttribute("lang");

			String errStr = I18nMgr.getInstance().getMsg(lang,
					"msg.error.session.time.out");

			JSONObject result = new JSONObject();

			result.element(Context.RET_CODE, 0x101);
			result.element(Context.RET_MSG, errStr);

			result.write(resp.getWriter());
		}

	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}