package tm.kod.numberfield.demo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.io.InputStream;
import tm.kod.webcam.WebCam;

@Theme("demo")
@Title("WebCam Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "tm.kod.numberfield.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        Component webCamContent = buildWebCamTabContent();
        content.addComponent(webCamContent);
        content.setComponentAlignment(webCamContent, Alignment.MIDDLE_CENTER);
        setContent(content);
    }

    private Component buildWebCamTabContent() {
        return new VerticalLayout() {
            WebCam webCam = new WebCam("Webcam");
            Image image = new Image("Image");
            HorizontalLayout buttons = new HorizontalLayout();
            {
                setSpacing(true);
                setMargin(true);
                setSizeUndefined();
                Label label = new Label("WebCamJS on Vaadin Demo");
                label.addStyleName("h1");
                addComponent(label);
                HorizontalLayout layout = new HorizontalLayout();
                layout.setSpacing(true);
                addComponent(layout);
                image.setHeight(240, Unit.PIXELS);
                image.setWidth(320, Unit.PIXELS);
                webCam.addReadyListener(new WebCam.ReadyListener() {

                    @Override
                    public void ready(WebCam.ReadyEvent e) {
                        buttons.setVisible(true);
                    }
                });
                webCam.addUploadCompleteListener(new WebCam.UploadCompleteListener() {

                    @Override
                    public void complete(final WebCam.CompleteEvent e) {
                        StreamResource sr = new StreamResource(new StreamResource.StreamSource() {

                            @Override
                            public InputStream getStream() {
                                return e.getStream();
                            }
                        }, System.currentTimeMillis() + ".jpeg");
                        image.setSource(sr);
                    }
                });
                layout.addComponent(webCam);
                layout.addComponent(image);
                // initing buttons
                buttons.setVisible(false); // visible when webcam is ready
                buttons.setSpacing(true);
                addComponent(buttons);
                // init freeze button
                Button button = new Button("Freeze", new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        webCam.freeze();
                    }
                });
                buttons.addComponent(button);
                // initing unfreeze button
                button = new Button("Unfreeze", new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        webCam.unfreeze();
                    }
                });
                buttons.addComponent(button);
                // initing snapshoot button
                button = new Button("Upload (Snap)", new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        webCam.snap();
                    }
                });
                button.addStyleName(ValoTheme.BUTTON_PRIMARY);
                buttons.addComponent(button);
            }
        };
    }
}
