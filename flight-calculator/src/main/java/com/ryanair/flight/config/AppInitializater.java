package com.ryanair.flight.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
/**
 * <p>Replacing of {@code web.xml}</p>
 * <p>Annotated configuration is faster in developing than xml based configuration</p>
 * @author angel santos
 *
 */
public class AppInitializater extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getRootConfigClasses()
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{SpringMVCConfig.class, SpringConfig.class};
	}
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getServletConfigClasses()
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.support.AbstractDispatcherServletInitializer#getServletMappings()
	 */
	@Override
	protected String[] getServletMappings() {
		//this configuration is for all petition in our context
		return new String[]{"/"};
	}

}
