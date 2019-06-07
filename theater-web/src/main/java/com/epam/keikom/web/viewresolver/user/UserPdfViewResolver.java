package com.epam.keikom.web.viewresolver.user;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class UserPdfViewResolver implements ViewResolver {

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {

		UserPdfView view = new UserPdfView();
		return view;
	}
}
