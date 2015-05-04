package tm.kod.widgets.demo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("demo")
@Title("Widget Add-ons Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI { 
    Navigator navigator;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "tm.kod.widgets.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        navigator = new Navigator(this, this);
        navigator.addView("", new MainView());
        navigator.addView("numberfield7", new Numberfield7View());
        navigator.addView("webcamjs", new WebCamJSView());
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        setContent(content);
    }

}
