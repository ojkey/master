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
package tm.kod.widgets.numberfield;

import tm.kod.widgets.numberfield.client.NumberFieldState;

import com.vaadin.ui.TextField;
import tm.kod.widgets.numberfield.client.Util;

/**
 * Number field class, which accepts only number input and has configuration:
 * <br/>
 * 1. Character of decimal separator. Default = '.';<br/>
 * 2. Decimal length. Default = 0; <br/>
 * 3. Character of grouping separator. Default = ' ';<br/>
 * 4. Is signed. Default = true; <br/>
 * 5. Is use grouping. Default = false; <br/>
 *
 * @author Kerim O.D.
 *
 */
public class NumberField extends TextField {

    /**
     * Generated serial version UID
     */
    private static final long serialVersionUID = 7663236836018122696L;
    private static final String NEGATIVE_VALUE = "Negative value";
    // message strings
    private String messageInvalidNumberValue = "Invalid number value";

    public NumberField() {
    }

    public NumberField(String caption) {
        super(caption);
    }

    @Override
    protected NumberFieldState getState() {
        return (NumberFieldState) super.getState();
    }

    @Override
    protected NumberFieldState getState(boolean markAsDirty) {
        return (NumberFieldState) super.getState(markAsDirty);
    }

    @Override
    public void setValue(String newValue) throws ReadOnlyException {
        if (newValue != null) {
            newValue = formatValue(newValue);
        }
        super.setValue(newValue);
    }

    public void setDecimalSeparator(char sep) {
        getState().decimalSeparator = sep;
    }

    public char getDecimalSeparator() {
        return getState(false).decimalSeparator;
    }

    public void setDecimalLength(int length) {
        if (length < 0) {
            throw new IllegalArgumentException(NEGATIVE_VALUE);
        }
        getState().decimalLength = length;
    }

    public boolean isUseGrouping() {
        return getState(false).isUseGrouping;
    }

    public void setUseGrouping(boolean use) {
        getState().isUseGrouping = use;
    }

    public boolean isSigned() {
        return getState(false).isSigned;
    }

    public void setSigned(boolean signed) {
        getState().isSigned = signed;
    }

    public int getDecimalLength() {
        return getState(false).decimalLength;
    }

    public void setGroupingSeparator(char sep) {
        getState().groupingSeparator = sep;
    }

    public char getGroupingSeparator() {
        return getState(false).groupingSeparator;
    }

    public String getMessageInvalidNumberValue() {
        return messageInvalidNumberValue;
    }

    public void setMessageInvalidNumberValue(String message) {
        this.messageInvalidNumberValue = message;
    }

    protected String formatValue(String str) {
        str = str.trim();
        String groupsep = Util.changeIfMetaChar(getGroupingSeparator());
        str = str.replaceAll(groupsep, "");
        if (str.isEmpty() || str.equals("-")) {
            return str;
        }
        int p = str.indexOf(getDecimalSeparator());
        String pre, suf;
        if (p != -1) {
            pre = str.substring(0, p);
            suf = str.substring(p);
            int len = getDecimalLength() + 1;
            if (suf.length() > len) {
                suf = suf.substring(0, len);
            }
        } else {
            pre = str;
            suf = "";
        }
        if (isUseGrouping()) {
            // group numbers
            pre = useGrouping(pre);
            pre = pre.replaceAll(" ", groupsep);
        }
        // adding decimal part
        return pre + suf;
    }

    private String useGrouping(String s) {
        return s.replaceAll("(\\d{1,3})(?=(?:\\d{3})+$)", "$1 ");
    }
}
