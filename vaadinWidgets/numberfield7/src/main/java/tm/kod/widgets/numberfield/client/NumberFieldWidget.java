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
package tm.kod.widgets.numberfield.client;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.vaadin.client.ui.VTextField;

/**
 * NuberField widget class
 *
 * @author Kerim O.D.
 */
public class NumberFieldWidget extends VTextField {

    /**
     * Negative zero
     */
    public static final String NEGATIVE_ZERO = "-0";
    /**
     * Negative zero
     */
    public static final String ZERO = "0";
    /**
     * Negative string
     */
    public static final String NEGATIVE_STRING = "-";
    // sign that special key down
    private boolean specialKeyDown;
    // sign that input has negative sign
    private boolean hasNegativeSign;
    // sign that input has decimal separator
    private boolean hasSeparator;
    /**
     * Is number has negative sign
     */
    private boolean signed = true;
    /**
     * Is number has grouping
     */
    private boolean useGrouping;
    /**
     * Grouping separator character
     */
    private char groupingSeparator = ' ';
    /**
     * If number is decimal
     */
    private boolean decimal = false;
    /**
     * Decimal separator character
     */
    private char decimalSeparator = '.';
    /**
     * temp string
     */
    private String oldValue;
    /**
     * previous cursor position
     */
    private int prevCursor;
    /**
     * selection length
     */
    private int selectionLength;
    // key down handler
    private final KeyDownHandler keyDownHandler = new KeyDownHandler() {
        @Override
        public void onKeyDown(KeyDownEvent event) {
            int nativeKeyCode = event.getNativeKeyCode();
            // is special key down
            specialKeyDown = event.isAnyModifierKeyDown()
                    || event.isAltKeyDown() || event.isControlKeyDown()
                    || event.isMetaKeyDown() || event.isShiftKeyDown()
                    || event.isDownArrow() || event.isLeftArrow()
                    || event.isRightArrow() || event.isUpArrow()
                    || (nativeKeyCode == KeyCodes.KEY_END)
                    || (nativeKeyCode == KeyCodes.KEY_HOME)
                    || (nativeKeyCode == KeyCodes.KEY_DOWN)
                    || (nativeKeyCode == KeyCodes.KEY_UP)
                    || (nativeKeyCode == KeyCodes.KEY_LEFT)
                    || (nativeKeyCode == KeyCodes.KEY_RIGHT)
                    || (nativeKeyCode == KeyCodes.KEY_BACKSPACE)
                    || (nativeKeyCode == KeyCodes.KEY_DELETE)
                    || (nativeKeyCode == KeyCodes.KEY_PAGEDOWN)
                    || (nativeKeyCode == KeyCodes.KEY_PAGEUP)
                    || (nativeKeyCode == KeyCodes.KEY_ENTER)
                    || (nativeKeyCode == KeyCodes.KEY_ESCAPE)
                    || (nativeKeyCode == KeyCodes.KEY_ALT)
                    || (nativeKeyCode == KeyCodes.KEY_CTRL)
                    || (nativeKeyCode == KeyCodes.KEY_SHIFT)
                    || (nativeKeyCode == KeyCodes.KEY_TAB);
            oldValue = NumberFieldWidget.super.getValue();
            if (oldValue == null) {
                oldValue = "";
            }
            prevCursor = getCursorPos();
            selectionLength = getSelectionLength();
        }
    };
    // key press handler
    private final KeyPressHandler keyPressHandler = new KeyPressHandler() {
        @Override
        public void onKeyPress(KeyPressEvent event) {
            char charCode = event.getCharCode();
            int curPos = getCursorPos();
            // if pressed number key
            if (('0' <= charCode) && (charCode <= '9')) {
                // Number cannot start with zero
                if (charCode == '0' && curPos == 0 && !decimal) {
                    event.preventDefault();
                }
                // if entered negative char
            } else if (charCode == '-') {
                // if not signed
                // or cursor is not in first position
                // or all ready has negative sign
                if (!signed || (curPos != 0) || hasNegativeSign) {
                    // canceling
                    event.preventDefault();
                }
                // if entered decimal separator char
            } else if (charCode == decimalSeparator) {
                // if is integer
                if (!decimal || hasSeparator) {
                    // canceling
                    event.preventDefault();
                    return;
                }
                int pos = 0;
                if (hasNegativeSign) {
                    pos = 1;
                }
                // if cursor on the first position or after '-'
                if (curPos == pos) {
                    // canceling
                    event.preventDefault();
                }
                // if another key was down
            } else if (!specialKeyDown) {
                // canceling
                event.preventDefault();
            }
        }
    };

    // key up handler
    private final KeyUpHandler keyUpHandler = new KeyUpHandler() {
        @Override
        public void onKeyUp(KeyUpEvent event) {
            String value = NumberFieldWidget.super.getValue();
            if (value == null || value.isEmpty()) {
                hasSeparator = false;
                hasNegativeSign = false;
            } else {
                String formated = formatString(value);
                if (formated.isEmpty()) {
                    setValue("");
                } else if (!oldValue.equals(formated)) {
                    int pos = getNewCursorPosition(formated);
                    setValue(value);
                    setCursorPos(pos);
                }
                hasNegativeSign = value.startsWith(NEGATIVE_STRING);
                hasSeparator = value.contains(Character.toString(decimalSeparator));
            }
        }

        private int getNewCursorPosition(String newValue) {
            int nlen = newValue.length();
            int curPos = prevCursor + selectionLength + nlen - oldValue.length();
            if (curPos < 0 || curPos > nlen) {
                curPos = nlen;
            }
            return curPos;
        }
    };

    public NumberFieldWidget() {
        addKeyDownHandler(keyDownHandler);
        addKeyPressHandler(keyPressHandler);
        addKeyUpHandler(keyUpHandler);
    }

    @Override
    public void setValue(String value) {
        if (value == null) {
            hasNegativeSign = false;
            hasSeparator = false;
        } else {
            hasNegativeSign = value.startsWith(NEGATIVE_STRING);
            hasSeparator = value.contains(String.valueOf(decimalSeparator));
            value = formatString(value);
        }
        super.setValue(value);
    }

    @Override
    public String getValue() {
        String value = super.getValue();
        if (value != null) {
            return formatString(value);
        }
        return null;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signet) {
        this.signed = signet;
    }

    public boolean isUseGrouping() {
        return useGrouping;
    }

    public void setUseGrouping(boolean useGrouping) {
        this.useGrouping = useGrouping;
    }

    public char getGroupingSeparator() {
        return groupingSeparator;
    }

    public void setGroupingSeparator(char groupingSeparator) {
        this.groupingSeparator = groupingSeparator;
    }

    public boolean isDecimal() {
        return decimal;
    }

    public void setDecimal(boolean decimal) {
        this.decimal = decimal;
    }

    public char getDecimalSeparator() {
        return decimalSeparator;
    }

    public void setDecimalSeparator(char decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    /**
     * Formatting entered string
     *
     * @param str string to format
     * @return formatted string
     */
    private String formatString(String str) {
        str = str.trim();
        String groupsep = Util.changeIfMetaChar(groupingSeparator);
        str = str.replaceAll(groupsep, "");
        if (str.isEmpty()
                || str.equals(NEGATIVE_STRING)) {
            return str;
        }
        if (str.equals(ZERO) || str.equals(NEGATIVE_ZERO)) {
            return str;
        }
        str = removeZero(str);
        String decSep = String.valueOf(decimalSeparator);
        int p = str.indexOf(decSep);
        String pre, suf;
        if (p != -1) {
            pre = str.substring(0, p);
            suf = str.substring(p);
        } else {
            pre = str;
            suf = "";
        }
        if (useGrouping) {
            // group numbers
            pre = useGrouping(pre);
            pre = pre.replaceAll(" ", groupsep);
        }
        str = pre + suf;
        if (str.startsWith(decSep)) {
            str = ZERO + str;
        } else if (str.startsWith(NEGATIVE_STRING + decSep)) {
            str = str.replaceFirst("-" + decSep, NEGATIVE_ZERO + decSep);
        }
        return str;
    }

    private String removeZero(String value) {
        if (!value.isEmpty()) {
            if (value.startsWith(ZERO)) {
                return removeZero(value.substring(1));
            }
            if (value.startsWith(NEGATIVE_ZERO)) {
                return NEGATIVE_STRING + removeZero(value.substring(2));
            }
        }
        return value;
    }

    /**
     * Grouping digit values
     *
     * @param s number string to modify
     * @return modified string with number grouping
     */
    private native String useGrouping(String s)/*-{
     return s.replace(/(\s)+/g, '').replace(/(\d{1,3})(?=(?:\d{3})+$)/g, '$1 ');
     }-*/;
}
