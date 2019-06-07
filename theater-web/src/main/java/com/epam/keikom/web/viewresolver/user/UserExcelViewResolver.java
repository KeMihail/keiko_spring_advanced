package com.epam.keikom.web.viewresolver.user;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

public class UserExcelViewResolver implements ViewResolver {

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {

		UserExcelView view = new UserExcelView();
		return view;
	}
}
