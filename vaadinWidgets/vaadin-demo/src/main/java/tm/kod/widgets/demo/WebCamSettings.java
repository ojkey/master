package tm.kod.widgets.demo;

/**
 * @author Kerim Orazmuhammedov
 */
class WebCamSettings {
    private static final int DEFATUL_WIDTH = 320;
    private static final int DEFATUL_HEIGHT = 240;
    private static final double DEFATUL_QUALITY = 90;

    private Integer width;
    private Integer height;
    private Double quality;

    Integer getWidth() {
        if(width == null) {
            return DEFATUL_WIDTH;
        }
        return width;
    }

    void setWidth(Integer width) {
        this.width = width;
    }

    Integer getHeight() {
        if(height == null) {
            return DEFATUL_HEIGHT;
        }
        return height;
    }

    void setHeight(Integer height) {
        this.height = height;
    }

    Double getQuality() {
        if(quality == null) {
            return DEFATUL_QUALITY;
        }
        return quality;
    }

    void setQuality(Double quality) {
        this.quality = quality;
    }
}
