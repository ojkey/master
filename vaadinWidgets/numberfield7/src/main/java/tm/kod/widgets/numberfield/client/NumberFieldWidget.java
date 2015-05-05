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
    // current length of decimal part
    private int currentDecimalLen;
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
     * Decimal separator character
     */
    private char decimalSeparator = '.';
    /**
     * Length of decimal part
     */
    private int decimalLength = 0;
    /**
     * temp string
     */
    private String oldValue;
    /**
     * previous cursor position
     */
    private int prevCursor;
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
            oldValue = getValue();
            if (oldValue == null) {
                oldValue = "";
            }
            prevCursor = getCursorPos();
        }
    };
    // key press handler
    private final KeyPressHandler keyPressHandler = new KeyPressHandler() {
        @Override
        public void onKeyPress(KeyPressEvent event) {
            char charCode = event.getCharCode();
            String value = getValue();
            int curPos = getCursorPos();
            // if pressed number key
            if (('0' <= charCode) && (charCode <= '9')) {
                // if is integer field
                if (decimalLength == 0) {
                    // if first number is '0'
                    if (NEGATIVE_ZERO.equals(value)) {
                        // setting "-"
                        setValue(NEGATIVE_STRING);
                    } else if (ZERO.equals(value)) {
                        // setting empty string
                        setValue("");
                    }
                    // else if is decimal and entered "-0" or "0"
                } else if (NEGATIVE_ZERO.equals(value) || ZERO.equals(value)) {
                    // appending separator char
                    value += decimalSeparator;
                    setValue(value);
                }
                // entered number of decimal part
                if (hasSeparator && !specialKeyDown) {
                    int sepIndex = value.indexOf(String.valueOf(decimalSeparator));
                    if (curPos > sepIndex) {
                        if (currentDecimalLen == decimalLength) {
                            // canceling
                            event.preventDefault();
                        } else {
                            // increase decimal length
                            currentDecimalLen++;
                        }
                    }
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
                // if is integer or
                if (decimalLength == 0 || hasSeparator) {
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
                } else {
                    currentDecimalLen = 0;
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
            String value = getValue();
            int keyCode = event.getNativeKeyCode();
            if (specialKeyDown) {
                if (value != null) {
                    if (keyCode == KeyCodes.KEY_BACKSPACE) {
                        hasSeparator = decimalLength > 0
                                && prevCursor > value.indexOf(Character
                                        .toString(decimalSeparator));
                        if (hasSeparator && currentDecimalLen > 0) {
                            currentDecimalLen--;
                            if (currentDecimalLen == 0) {
                                value = value.substring(0, value.indexOf(Character
                                        .toString(decimalSeparator)));
                            }
                        }
                    }
                }
            }
            if (value == null || value.isEmpty()) {
                hasSeparator = false;
                hasNegativeSign = false;
            } else {
                value = formatString(value);
                if (value.isEmpty()) {
                    event.preventDefault();
                } else {
                    int nlen = value.length();
                    int olen;
                    if (oldValue == null) {
                        olen = nlen;
                    } else {
                        olen = oldValue.length();
                    }
                    if (olen != nlen) {
                        setValue(value);
                        resetCursorPosition(olen, nlen);
                    } else if (keyCode == KeyCodes.KEY_BACKSPACE) {
                        setValue(value);
                    }
                }
                String sign = value.substring(0, 1);
                hasNegativeSign = NEGATIVE_STRING.equals(sign);
                hasSeparator = decimalLength > 0
                        && value.contains(Character
                                .toString(decimalSeparator));

            }
            if (!hasSeparator) {
                currentDecimalLen = 0;
            }
            oldValue = null;
        }

        private void resetCursorPosition(int olen, int nlen) {
            int curPos = prevCursor;
            int diff = nlen - olen;
            curPos += diff;
            if (curPos > nlen) {
                curPos = nlen;
            }
            if (curPos < 0) {
                curPos = 0;
            }
            setCursorPos(curPos);
        }
    };

    public NumberFieldWidget() {
        addKeyDownHandler(keyDownHandler);
        addKeyPressHandler(keyPressHandler);
        addKeyUpHandler(keyUpHandler);
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        if (value != null) {
            String sign = value.substring(0, 1);
            hasNegativeSign = NEGATIVE_STRING.equals(sign);
            int decSepIndex = value.indexOf(String.valueOf(decimalSeparator));
            hasSeparator = decimalLength > 0 && decSepIndex != -1;
            if (hasSeparator) {
                String dec = value.substring(decSepIndex + 1);
                currentDecimalLen = dec.length();
            } else {
                currentDecimalLen = 0;
            }
        } else {
            hasNegativeSign = false;
            hasSeparator = false;
            currentDecimalLen = 0;
        }
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

    public char getDecimalSeparator() {
        return decimalSeparator;
    }

    public void setDecimalSeparator(char decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    public int getDecimalLength() {
        return decimalLength;
    }

    public void setDecimalLength(int decimalLength) {
        this.decimalLength = decimalLength;
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
        if (str.isEmpty() || str.equals(NEGATIVE_STRING)) {
            return str;
        }
        int p = str.indexOf(decimalSeparator);
        String pre, suf;
        if (p != -1) {
            pre = str.substring(0, p);
            suf = str.substring(p);
            int len = decimalLength + 1;
            if (suf.length() > len) {
                suf = suf.substring(0, len);
            }
        } else {
            pre = str;
            suf = "";
        }
        if (useGrouping) {
            // group numbers
            pre = useGrouping(pre);
            pre = pre.replaceAll(" ", groupsep);
        }
        // adding decimal part
        return pre + suf;
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
