package com.training.core.htl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;

public class HeaderPojo extends WCMUsePojo {
	
	private LinkedList<Page> menuItems;

	@Override
	public void activate() throws Exception {
		menuItems = new LinkedList<>();
		Page currentPage = getCurrentPage();
		
		if (currentPage != null) {
			Page languagePage = currentPage.getAbsoluteParent(2);
			
			if (languagePage != null) {
				menuItems.add(languagePage);
				
				Iterator<Page> languagePageChildren = languagePage.listChildren();
				
				while (languagePageChildren.hasNext()) {
					menuItems.add(languagePageChildren.next());
				}
			}
		}
	}
	
	public List<Page> getMenuItems() {
		return menuItems;
	}
	
}
