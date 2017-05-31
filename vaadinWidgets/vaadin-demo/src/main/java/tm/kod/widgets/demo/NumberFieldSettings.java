package tm.kod.widgets.demo;

/**
 * @author Kerim Orazmuhammedov
 */
class NumberFieldSettings {
    private String number;
    private String value;
    private Boolean isSigned;
    private Boolean isUseGrouping;
    private String groupingSeparator = " ";
    private Integer decimalLength = 0;
    private String decimalSeparator = ".";
    private String decimalSimilarSeparators = ".,";

    String getNumber() {
        return number;
    }

    void setNumber(String number) {
        this.number = number;
    }

    String getValue() {
        return value;
    }

    void setValue(String value) {
        this.value = value;
    }

    Boolean getSigned() {
        return isSigned;
    }

    void setSigned(Boolean signed) {
        isSigned = signed;
    }

    Boolean getUseGrouping() {
        return isUseGrouping;
    }

    void setUseGrouping(Boolean useGrouping) {
        isUseGrouping = useGrouping;
    }

    String getGroupingSeparator() {
        return groupingSeparator;
    }

    void setGroupingSeparator(String groupingSeparator) {
        this.groupingSeparator = groupingSeparator;
    }

    Integer getDecimalLength() {
        return decimalLength;
    }

    void setDecimalLength(Integer decimalLength) {
        if (decimalLength == null) {
            this.decimalLength = 0;
        } else {
            this.decimalLength = decimalLength;
        }
    }

    String getDecimalSeparator() {
        return decimalSeparator;
    }

    void setDecimalSeparator(String decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    String getDecimalSimilarSeparators() {
        return decimalSimilarSeparators;
    }

    void setDecimalSimilarSeparators(String decimalSimilarSeparators) {
        this.decimalSimilarSeparators = decimalSimilarSeparators;
    }
}
