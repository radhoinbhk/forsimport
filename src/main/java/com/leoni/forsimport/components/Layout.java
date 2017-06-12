package com.leoni.forsimport.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import com.leoni.forsimport.pages.Index;
import com.leoni.forsimport.services.Authenticator;

/**
 * Layout component for pages of application test-project.
 */
@Import(library = {"vendor/jquery/jquery-1.12.3.min.js", "vendor/tether/js/tether.min.js",
		"vendor/bootstrap/js/bootstrap.min.js", "vendor/detectmobilebrowser/detectmobilebrowser.js",
		"vendor/jscrollpane/jquery.mousewheel.js", "vendor/jscrollpane/mwheelIntent.js",
		"vendor/jscrollpane/jquery.jscrollpane.min.js", "vendor/waves/waves.min.js", "vendor/chartist/chartist.min.js",
		"vendor/switchery/dist/switchery.min.js", "vendor/flot/jquery.flot.min.js",
		"vendor/flot/jquery.flot.resize.min.js", "vendor/flot.tooltip/js/jquery.flot.tooltip.min.js",
		"vendor/CurvedLines/curvedLines.js", "vendor/TinyColor/tinycolor.js",
		"vendor/sparkline/jquery.sparkline.min.js", "vendor/raphael/raphael.min.js", "vendor/morris/morris.min.js",
		"vendor/jvectormap/jquery-jvectormap-2.0.3.min.js", "vendor/jvectormap/jquery-jvectormap-world-mill.js",
		"vendor/dropify/dist/js/dropify.min.js", "js/app.js", "js/demo.js", "js/forms-upload.js" })
public class Layout {
	@Inject
	private ComponentResources resources;
	// private static final Logger LOG = Logger.getLogger(Import.class);
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

	Authenticator authenticator;
	@InjectPage
	private Index index;
	
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

	public boolean isAdministrationPage() {
		return "Administration".equals(pageName);
	}
	
	public Object onLogout() {
		authenticator.logout();
		if (!authenticator.isLoggedIn()) {
			return index;
		}
		return null;
	}
	
	// public String getImgName(){
	// TableDAO dao = new TableDAO();
	// BasePage base = new BasePage();
	// LOG.info("***********"+base.getEmail());
	// if("ppe".equals(dao.getProfilUser(base.getEmail()))){
	// return "${context:images/ppe.png}";
	// }else if ("client dachat".equals(dao.getProfilUser(base.getEmail()))){
	// return "${context:images/SA.png}";
	// }else{
	// return "${context:images/dev.png}";
	// }
	//
	// }
}
