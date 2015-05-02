package tm.kod.widgets.numberfield.client;

import com.vaadin.shared.ui.textfield.AbstractTextFieldState;

/**
 * Number field state class with shared strings
 * 
 * @author Kerim O.D.
 * 
 */
public class NumberFieldState extends AbstractTextFieldState {
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -5803268179789404932L;
	/**
	 * Is number has negative sign
	 */
	public boolean isSigned = true;
	/**
	 * Is number has grouping
	 */
	public boolean isUseGrouping = false;
	/**
	 * Grouping separator character
	 */
	public char groupingSeparator = ' ';
	/**
	 * Decimal separator character
	 */
	public char decimalSeparator = '.';
	/**
	 * Length of decimal part
	 */
	public int decimalLength = 0;
}