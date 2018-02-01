package tw.howie.lab.ffmpeg.domain;

/**
 * @author howie
 * @since 2018/2/1
 */
public enum VideoAspect {

    aspect_169("16:9"),
    aspect_54("5:4"),
    aspect_43("4:3"),
    aspect_11("1:1");

    private final String aspect;

    VideoAspect(String aspect) {
        this.aspect = aspect;
    }

    public String getAspect() {
        return aspect;
    }
}
