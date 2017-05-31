/*
 * Copyright 2015 Kerim O.D.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tm.kod.widgets.demo;

import com.google.gwt.thirdparty.guava.common.io.BaseEncoding;
import com.vaadin.data.Binder;
import com.vaadin.data.Converter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import tm.kod.widgets.numberfield.NumberField;
import tm.kod.widgets.webcamjs.WebCamJS;

import java.io.ByteArrayInputStream;

/**
 *
 * @author Kerim O.D.
 */
class WebCamJSView extends VerticalLayout implements View {

    private final WebCamSettings settings = new WebCamSettings();
    private final Binder<WebCamSettings> settingsBinder = new Binder<>();
    private final WebCamJS webCam = new WebCamJS("Webcam");
    private final Image image = new Image("Image");
    private final HorizontalLayout buttons = new HorizontalLayout();

    WebCamJSView() {
        settingsBinder.readBean(settings);
        setSizeFull();
        VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.setMargin(true);
        content.setSizeUndefined();
        addComponent(content);
        setComponentAlignment(content, Alignment.MIDDLE_CENTER);
        Label label = new Label("WebCamJSJS on Vaadin Demo");
        label.addStyleName("h1");
        content.addComponent(label);
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        content.addComponent(layout);
        image.setHeight(240, Sizeable.Unit.PIXELS);
        image.setWidth(320, Sizeable.Unit.PIXELS);
        webCam.addReadyListener(e -> buttons.setVisible(true));
        webCam.addSnapReceivedListener(this::onReceived);
        layout.addComponent(webCam);
        layout.addComponent(image);
        layout.addComponent(createConfiguration());
        // initing buttons
        buttons.setVisible(false); // visible when webcam is ready
        buttons.setSpacing(true);
        content.addComponent(buttons);
        // init freeze button
        buttons.addComponent(new Button("Freeze", e -> webCam.freeze()));
        // initing unfreeze button
        buttons.addComponent(new Button("Unfreeze", e -> webCam.unfreeze()));
        // initing snapshoot button
        Button button = new Button("Upload (Snap)", e -> webCam.snap());
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        buttons.addComponent(button);
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Page.getCurrent().setTitle("WebCamJS Demo View");
    }

    private VerticalLayout createConfiguration() {
        VerticalLayout layout = new VerticalLayout();
        Label label = new Label("Configuration");
        label.addStyleName("h2 colored");
        layout.addComponent(label);
        FormLayout form = new FormLayout(); 
        form.setWidth(350, Unit.PIXELS);
        layout.addComponent(form);
        // cam width
        final NumberField camWidth = new NumberField("Cam width(pixels)");
        settingsBinder.forField(camWidth).withConverter(createNumberConverter())
                .bind(
                        WebCamSettings::getWidth,
                        WebCamSettings::setWidth);
        camWidth.setWidth(100, Unit.PERCENTAGE);
        form.addComponent(camWidth);
        // cam height
        final NumberField camHeight = new NumberField("Cam height(pixels)");
        settingsBinder.forField(camHeight).withConverter(createNumberConverter())
                .bind(
                        WebCamSettings::getHeight,
                        WebCamSettings::setHeight);

        camHeight.setWidth(100, Unit.PERCENTAGE);
        form.addComponent(camHeight);
        // image quality
        Slider quality = new Slider("Image Quality(0-100)", 0, 100);
        settingsBinder.forField(quality)
                .bind(
                        WebCamSettings::getQuality,
                        WebCamSettings::setQuality);
        quality.setWidth(100, Unit.PERCENTAGE);
        quality.setValue((double) webCam.getQuality());
        form.addComponent(quality);
        Button reset = new Button("Reset", this::onReset);
        form.addComponent(reset);
        return layout;
    }

    private Converter<String, Integer> createNumberConverter() {
        return new StringToIntegerConverter("Invalid int value");
    }

    void onReceived(final WebCamJS.ReceivedEvent e) {
        StreamResource sr = new StreamResource(() -> new ByteArrayInputStream(BaseEncoding.base64().decode(e.getBase64String())),
                System.currentTimeMillis() + ".jpeg");
        image.setSource(sr);
    }

    private void onReset(Button.ClickEvent event) {
        boolean isValid = settingsBinder.writeBeanIfValid(settings);
        if (isValid) {
            int intValue = nvl(settings.getWidth(), 320);
            webCam.setCamWidth(intValue);
            webCam.setWidth(intValue, Unit.PIXELS);
            intValue = nvl(settings.getHeight(), 240);
            webCam.setCamHeight(intValue);
            webCam.setHeight(intValue, Unit.PIXELS);
            webCam.setQuality(settings.getQuality().intValue());
        }
    }

    private int nvl(Object value, int defValue) {
        if(value == null) {
            return defValue;
        }
        return (Integer)value;
    }
}
