package tm.kod.widgets.numberfield.client;

import tm.kod.widgets.numberfield.NumberField;

import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.textfield.TextFieldConnector;
import com.vaadin.shared.ui.Connect;

/**
 * Client side number field connector class
 *
 * @author Kerim O.D.
 *
 */
@Connect(value = NumberField.class)
public class NumberFieldConnector extends TextFieldConnector {

    /**
     * Generated serial version UID
     */
    private static final long serialVersionUID = -5531145613926511796L;

    public NumberFieldConnector() {
    }

    @Override
    public NumberFieldState getState() {
        return (NumberFieldState) super.getState();
    }

    @Override
    public NumberFieldWidget getWidget() {
        return (NumberFieldWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent event) {
        NumberFieldWidget widget = getWidget();
        NumberFieldState state = getState();
        widget.setDecimalLength(state.decimalLength);
        widget.setDecimalSeparator(state.decimalSeparator);
        widget.setGroupingSeparator(state.groupingSeparator);
        widget.setSigned(state.isSigned);
        widget.setUseGrouping(state.isUseGrouping);
        super.onStateChanged(event);
    }

}
