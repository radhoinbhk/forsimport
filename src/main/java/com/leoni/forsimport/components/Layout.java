package com.leoni.forsimport.components;

import org.apache.log4j.Logger;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import com.leoni.forsimport.dao.TableDAO;
import com.leoni.forsimport.model.User;
import com.leoni.forsimport.pages.BasePage;
import com.leoni.forsimport.pages.Index;

/**
 * Layout component for pages of application test-project.
 */
// @Import(library={"context:js/jquery.js",
// "context:js/myeffects.js"})
//@Import(module = "bootstrap/collapse", library = { "js/jquery-1.12.3.min.js", "js/tether.min.js",
//		"js/bootstrap.min.js", "js/detectmobilebrowser.js", "js/jquery.mousewheel.js", "js/mwheelIntent.js",
//		"js/jquery.jscrollpane.min.js", "js/waves.min.js", "js/chartist.min.js",
//		"js/switchery.min.js", "js/jquery.flot.min.js", "js/jquery.flot.resize.min.js", "js/jquery.flot.tooltip.min.js",
//		"js/curvedLines.js", "js/tinycolor.js", "js/jquery.sparkline.min.js", "js/raphael.min.js",
//		"js/morris.min.js", "js/jquery-jvectormap-2.0.3.min.js", "js/jquery-jvectormap-world-mill.js", "js/dropify.min.js", 
//		"js/app.js", "js/demo.js", "js/forms-upload.js"})
public class Layout {
	@Inject
	private ComponentResources resources;
	private static final Logger LOG = Logger.getLogger(Import.class);
	/**
	 * The page title, for the <title> element and the
	 * <h1>element.
	 */
	@Property
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Property
	private String pageName;

	@Property
	@Inject
	@Symbol(SymbolConstants.APPLICATION_VERSION)
	private String appVersion;

	public String getClassForPageName() {
		return resources.getPageName().equalsIgnoreCase(pageName) ? "active" : null;
	}
	public String[] getPageNames() {
		return new String[] { "Index", "Import", "Export", "Administration" };
	}

	public boolean isExportPage() {
		return "Export".equals(pageName);
	}

	public boolean isImportPage() {
		return "Import".equals(pageName);
	}
	
	public boolean isAdministrationPage(){
		return "Administration".equals(pageName);
	}
//	public String getImgName(){
//		TableDAO dao = new TableDAO();
//		BasePage base = new BasePage();
//		LOG.info("***********"+base.getEmail());
//		if("ppe".equals(dao.getProfilUser(base.getEmail()))){
//			return "${context:images/ppe.png}";
//		}else if ("client dachat".equals(dao.getProfilUser(base.getEmail()))){
//			return "${context:images/SA.png}";
//		}else{
//			return "${context:images/dev.png}";
//		}
//		
//	}
}
