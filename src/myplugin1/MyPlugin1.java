package myplugin1;

import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;
import com.nomagic.magicdraw.license.utils.LicenseUtils;
import com.nomagic.magicdraw.plugins.Plugin;
import action.*;

public class MyPlugin1 extends Plugin {

	/*
    The init() method is called first when the MagicDraw application is started. It does not
    require a model to be loaded. This example calls the three custom functions if the init method.
     */
	@Override
	public void init() {
		//String property = System.getProperty("java.class.path");
		//OutMsg.disp(property);
		OutMsg.disp("Cameo Product is " + LicenseUtils.getEdition());

		createMainMenuAction();
		createBrowserAction();
		createDiagramAction();
	}


	private void createMainMenuAction() {
		var action = new MainMenuAction("CsepMainMenuAction", "Connect to CSEP Parts Database");
		var configurator = new MainMenuConfiguration(action);
		ActionsConfiguratorsManager.getInstance().addMainMenuConfigurator(configurator);
	}

	private void createBrowserAction() {
		var action = new BrowserAction("CsepBrowserAction", "Create CSEP Model Elements");
		var browserConfiguration = new BrowserConfiguration(action);
		ActionsConfiguratorsManager.getInstance().addContainmentBrowserContextConfigurator(browserConfiguration);

		var browserToolConfiguration = new BrowserRelatedElementsSubmenuConfiguration(action);
		ActionsConfiguratorsManager.getInstance().addContainmentBrowserContextConfigurator(browserToolConfiguration);
	}

	private void createDiagramAction() {
		var action = new DiagramAction("CsepDiagramAction", "CSEP Diagram action");
		var configurator = new DiagramConfiguration(action);
		ActionsConfiguratorsManager.getInstance().addAnyDiagramCommandBarConfigurator(configurator);
	}

	@Override
	public boolean close() {
		return true;
	}

	@Override
	public boolean isSupported() {
		return true;
	}
}
