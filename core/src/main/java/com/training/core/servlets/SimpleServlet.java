package com.training.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.training.core.config.OsgiConfig;

@Component(service = Servlet.class, property = {
		   ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
		   ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=sling/servlet/default", 
		   ServletResolverConstants.SLING_SERVLET_SELECTORS + "=simpleservlet",
		   ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=json"})
public class SimpleServlet extends SlingAllMethodsServlet {
	
	private static final long serialVersionUID = 4658459046306824894L;
	
	@Reference
	transient OsgiConfig osgiConfig;

	@Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
		response.setStatus(200);
		response.getWriter().write("Hello World!!!");
	}
}
