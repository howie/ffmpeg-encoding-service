package tw.howie.lab.ffmpeg.domain;

/**
 * @author howie
 * @since 2018/2/1
 */
public enum VideoFormat {

    MP4("mp4");

    private final String format;

    VideoFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
