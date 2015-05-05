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

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Kerim O.D.
 */
public class MainView extends VerticalLayout implements View {

    public MainView() {
        setSizeFull();
        VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.setMargin(true);
        content.setSizeUndefined();
        addComponent(content);
        setComponentAlignment(content, Alignment.TOP_CENTER);
        Label label = new Label("Welcome to (tm.kod.widgets) Main View!");
        label.addStyleName("h2");
        content.addComponent(label);
        Link link = new Link("NumberField7 Demo", new ExternalResource("#!numberfield7"));
        link.setIcon(FontAwesome.PENCIL);
        link.addStyleName(ValoTheme.LINK_LARGE);
        content.addComponent(link);
        link = new Link("WebCamJS Demo", new ExternalResource("#!webcamjs"));
        link.setIcon(FontAwesome.CAMERA);
        link.addStyleName(ValoTheme.LINK_LARGE);
        content.addComponent(link);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Page.getCurrent().setTitle("Demo (tm.kod.widgets) Main View");
    }

}
