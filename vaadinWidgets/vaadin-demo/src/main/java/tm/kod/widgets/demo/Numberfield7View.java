/*
 * Copyright 2015 okd.
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
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import tm.kod.widgets.numberfield.NumberField;

/**
 *
 * @author Kerim O.D.
 */
public class Numberfield7View extends VerticalLayout implements View {

    NumberField numberField = new NumberField("<span style=\"font-size: 2.4em;\">Demo NumberField</span>");
    TextField value = new TextField("Value");
    Label currentValue = new Label();
    NumberField minValue = new NumberField("Min Value");
    NumberField maxValue = new NumberField("Max Value");
    CheckBox isSigned = new CheckBox("Is signed");
    CheckBox isUseGrouping = new CheckBox("Is Use Grouping");
    TextField groupingSeparator = new TextField("Grouping Separator");
    NumberField decimalLength = new NumberField("Decimalc Length");
    TextField decimalSeparator = new TextField("Decimal Separator");

    public Numberfield7View() {
        setSizeFull();
        HorizontalLayout content = new HorizontalLayout();
        content.setWidth(700, Sizeable.Unit.PIXELS);
        content.setMargin(true);
        content.setSpacing(true);
        addComponent(content);
        setComponentAlignment(content, Alignment.MIDDLE_CENTER);
        VerticalLayout form = new VerticalLayout();
        form.setSpacing(true);
        content.addComponent(form);
        numberField.setWidth(100, Sizeable.Unit.PERCENTAGE);
        numberField.setCaptionAsHtml(true);
        numberField.setNullRepresentation("");
        numberField.setImmediate(true);
        form.addComponent(numberField);
        form.addComponent(new Button("Get current Value:", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                currentValue.setValue(numberField.getValue());
            }
        }));
        form.addComponent(currentValue);
        form = new VerticalLayout();
        form.setCaption("<h3>Field Settings</h3>");
        form.setCaptionAsHtml(true);
        form.setSpacing(true);
        content.addComponent(form);
        value.setWidth(100, Sizeable.Unit.PERCENTAGE);
        minValue.setNullRepresentation("");
        minValue.setWidth(100, Sizeable.Unit.PERCENTAGE);
        maxValue.setNullRepresentation("");
        maxValue.setWidth(100, Sizeable.Unit.PERCENTAGE);
        isSigned.setValue(Boolean.TRUE);
        isUseGrouping.setValue(Boolean.TRUE);
        groupingSeparator.setMaxLength(1);
        groupingSeparator.setValue(" ");
        groupingSeparator.setWidth(100, Sizeable.Unit.PERCENTAGE);
        decimalLength.setDoubleValue(0d);
        decimalLength.setWidth(50, Sizeable.Unit.PERCENTAGE);
        decimalSeparator.setMaxLength(1);
        decimalSeparator.setValue(".");
        decimalSeparator.setWidth(100, Sizeable.Unit.PERCENTAGE);
        form.addComponent(value);
        form.addComponent(minValue);
        form.addComponent(maxValue);
        form.addComponent(isSigned);
        form.addComponent(isUseGrouping);
        form.addComponent(groupingSeparator);
        form.addComponent(decimalLength);
        form.addComponent(decimalSeparator);
        Button submitButton = new Button("Reset settings", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                numberField.setMinValue(minValue.getDoubleValue());
                numberField.setMaxValue(maxValue.getDoubleValue());
                numberField.setSigned(
                        Boolean.TRUE.equals(isSigned.getValue()));
                numberField.setUseGrouping(
                        Boolean.TRUE.equals(isUseGrouping.getValue()));
                numberField.setGroupingSeparator(
                        getChar(groupingSeparator, ' '));
                numberField.setDecimalLength(getInt(decimalLength));
                numberField.setDecimalSeparator(
                        getChar(decimalSeparator, '.'));
                numberField.setValue(value.getValue());
            }

            char getChar(TextField field, char defaultChar) {
                String val = field.getValue();
                if (val == null || val.isEmpty()) {
                    return defaultChar;
                }
                return val.charAt(0);
            }

            int getInt(NumberField field) {
                Double val = field.getDoubleValue();
                if (val == null) {
                    return 0;
                }
                return val.intValue();
            }
        });
        submitButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        form.addComponent(submitButton);
        form.setComponentAlignment(submitButton, Alignment.BOTTOM_RIGHT);
        submitButton.click();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Page.getCurrent().setTitle("NumberField7 Demo View");
    }

}
