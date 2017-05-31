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

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
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
class NumberField7View extends VerticalLayout implements View {

    private final NumberFieldSettings settings = new NumberFieldSettings();
    private final Binder<NumberFieldSettings> settingsBinder = new Binder<>();
    private final NumberField numberField = new NumberField("<span style=\"font-size: 2.4em;\">Demo NumberField</span>");
    private final TextField value = new TextField("Value");
    private final Label currentValue = new Label();
    private final CheckBox isSigned = new CheckBox("Is signed");
    private final CheckBox isUseGrouping = new CheckBox("Is Use Grouping");
    private final TextField groupingSeparator = new TextField("Grouping Separator");
    private final NumberField decimalLength = new NumberField("Decimal length");
    private final TextField decimalSeparator = new TextField("Decimal Separator");
    private final TextField decimalSimilarSeparators = new TextField("Decimal Similar Separators");

    NumberField7View() {
        bindBindersToFields();
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
        form.addComponent(numberField);
        form.addComponent(new Button("Get current Value:", event -> currentValue.setValue(numberField.getValue())));
        form.addComponent(currentValue);
        form = new VerticalLayout();
        form.setCaption("<h3>Field Settings</h3>");
        form.setCaptionAsHtml(true);
        form.setSpacing(true);
        content.addComponent(form);
        value.setWidth(100, Sizeable.Unit.PERCENTAGE);
        isSigned.setValue(Boolean.TRUE);
        isUseGrouping.setValue(Boolean.TRUE);
        groupingSeparator.setMaxLength(1);
        groupingSeparator.setValue(" ");
        groupingSeparator.setWidth(100, Sizeable.Unit.PERCENTAGE);
        decimalSeparator.setMaxLength(1);
        decimalSeparator.setValue(".");
        decimalSeparator.setWidth(100, Sizeable.Unit.PERCENTAGE);
        decimalSimilarSeparators.setValue(".,");
        decimalSimilarSeparators.setWidth(100, Sizeable.Unit.PERCENTAGE);
        form.addComponent(value);
        form.addComponent(isSigned);
        form.addComponent(isUseGrouping);
        form.addComponent(groupingSeparator);
        form.addComponent(decimalLength);
        form.addComponent(decimalSeparator);
        form.addComponent(decimalSimilarSeparators);
        Button submitButton = new Button("Reset settings", this::onSubmit);
        submitButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        form.addComponent(submitButton);
        form.setComponentAlignment(submitButton, Alignment.BOTTOM_RIGHT);
        submitButton.click();
    }

    private void bindBindersToFields() {
        settingsBinder.forField(numberField)
                .bind(
                        NumberFieldSettings::getNumber,
                        NumberFieldSettings::setNumber);
        settingsBinder.forField(value)
                .bind(
                        NumberFieldSettings::getValue,
                        NumberFieldSettings::setValue);
        settingsBinder.forField(isSigned)
                .bind(
                        NumberFieldSettings::getSigned,
                        NumberFieldSettings::setSigned);
        settingsBinder.forField(isUseGrouping)
                .bind(
                        NumberFieldSettings::getUseGrouping,
                        NumberFieldSettings::setUseGrouping);
        settingsBinder.forField(groupingSeparator)
                .bind(
                        NumberFieldSettings::getGroupingSeparator,
                        NumberFieldSettings::setGroupingSeparator);
        settingsBinder.forField(decimalLength).withConverter(new StringToIntegerConverter("Invalid number value"))
                .bind(
                        NumberFieldSettings::getDecimalLength,
                        NumberFieldSettings::setDecimalLength);
        settingsBinder.forField(decimalSeparator)
                .bind(
                        NumberFieldSettings::getDecimalSeparator,
                        NumberFieldSettings::setDecimalSeparator);
        settingsBinder.forField(decimalSimilarSeparators)
                .bind(
                        NumberFieldSettings::getDecimalSimilarSeparators,
                        NumberFieldSettings::setDecimalSimilarSeparators);
        settingsBinder.readBean(settings);
    }

    private void onSubmit(Button.ClickEvent event) {
        boolean isValid = settingsBinder.writeBeanIfValid(settings);
        if (isValid) {
            numberField.setSigned(
                    Boolean.TRUE.equals(isSigned.getValue()));
            numberField.setUseGrouping(
                    Boolean.TRUE.equals(isUseGrouping.getValue()));
            numberField.setGroupingSeparator(
                    getChar(settings.getGroupingSeparator(), ' '));
            numberField.setDecimalLength(settings.getDecimalLength());
            numberField.setDecimalSeparator(
                    getChar(settings.getDecimalSeparator(), '.'));
            numberField.setDecimalSimilarSeparators(decimalSimilarSeparators.getValue());
            numberField.setValue(settings.getValue());
        }
    }

    private char getChar(String val, char defaultChar) {
        if (val == null || val.isEmpty()) {
            return defaultChar;
        }
        return val.charAt(0);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Page.getCurrent().setTitle("NumberField7 Demo View");
    }

}
