package me.yeall.beetl.listener;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.web.SimpleCrossFilter;

public class BeetlFitler extends SimpleCrossFilter implements Filter {

	private static GroupTemplate groupTemplate = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String path = detectWebRootPath();
		String root = filterConfig.getInitParameter("root");
		Configuration cfg;
		try {
			cfg = Configuration.defaultConfiguration();
			WebAppResourceLoader resourceLoader = new WebAppResourceLoader(path+root);
			groupTemplate = new GroupTemplate(resourceLoader, cfg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected GroupTemplate getGroupTemplate() {
		return groupTemplate;
	}
	
	private static String detectWebRootPath() {
		try {
			String path = BeetlFitler.class.getResource("/").toURI().getPath();
			return new File(path).getParentFile().getParentFile().getCanonicalPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
