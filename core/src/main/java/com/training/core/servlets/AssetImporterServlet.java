package com.training.core.servlets;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import com.day.cq.dam.api.AssetManager;

@Component(service = Servlet.class, property = {
		   ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
		   ServletResolverConstants.SLING_SERVLET_PATHS + "=" + "/bin/asset-importer"})
public class AssetImporterServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 5950151258849804483L;

	@Override
    protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
		ResourceResolver resourceResolver = request.getResourceResolver();
		final AssetManager assetManager = resourceResolver.adaptTo(AssetManager.class);
		int responseStatusCode = 500;
		String responseMessage = "Impossible to upload asset";
		
		if (assetManager != null) {
			long startTime = System.currentTimeMillis();
			
			FileInputStream fileInputStream = new FileInputStream("C:\\Users\\g.buono\\Desktop\\debian-10.9.0-amd64-netinst.zip");
			assetManager.createAsset("/content/dam/asset.zip", fileInputStream, "application/zip", true);
			
			long stopTime = System.currentTimeMillis();
			
			responseStatusCode = 200;
			responseMessage = "Asset uploaded in " + (stopTime - startTime) + " ms";
		}
		
		response.setStatus(responseStatusCode);
		response.getWriter().write(responseMessage);
	}
}
