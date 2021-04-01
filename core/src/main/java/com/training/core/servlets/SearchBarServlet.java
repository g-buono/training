package com.training.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.search.PredicateConverter;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

@Component(service = Servlet.class, property = {
		   ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
		   ServletResolverConstants.SLING_SERVLET_PATHS+"=/bin/search"})
public class SearchBarServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 8180797134416347169L;
	
	@Reference
    private transient QueryBuilder queryBuilder;
	
	@Override
    protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
		JSONArray searchResults = new JSONArray();
		
		try {
			String searchQuery = request.getParameter("searchQuery");
			String pagesLocation = request.getParameter("pagesLocation");
			String assetsLocation = request.getParameter("assetsLocation");
			
			if (searchQuery != null && pagesLocation != null && assetsLocation != null) {
				ResourceResolver resourceResolver = request.getResourceResolver();
				Map<String, Object> pagePredicatesMap = createPageSearchPredicateMap(searchQuery, pagesLocation);
				
				PredicateGroup predicates = PredicateConverter.createPredicates(pagePredicatesMap);
		        Query query = queryBuilder.createQuery(predicates, resourceResolver.adaptTo(Session.class));
	
		        SearchResult searchResult = query.getResult();
		        List<Hit> hits = searchResult.getHits();
		        
		        for (Hit hit : hits) {
		        	JSONObject page = new JSONObject();
	        	
					page.put("title", hit.getTitle());
					page.put("url", hit.getPath());
					
					searchResults.put(page);
		        }
			}
		} catch (JSONException | RepositoryException e) {
			e.printStackTrace();
		} finally {
			response.setStatus(200); 
			response.setContentType("application/json");
			response.getWriter().write(searchResults.toString());
		}
	}
	
	private Map<String, Object> createPageSearchPredicateMap(String queryString, String queryLocation) {
		Map<String, Object> predicateMap = new HashMap<>();
		
		String[] fullText = {queryString};
		String[] type = {"cq:Page"};
		String[] contentPath = {queryLocation};
		
		predicateMap.put("fulltext", fullText);
		predicateMap.put("type", type);
		predicateMap.put("path", contentPath);
		
		return predicateMap;
	}
	
}
