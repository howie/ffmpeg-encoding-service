package tw.howie.lab.ffmpeg.domain;

import java.util.Objects;

/**
 * @author howie
 * @since 2018/2/1
 */
public class VideoInfo {

    private final int height;

    private final int width;

    private final float frameRate;

    private final String displayAspectRatio;

    VideoInfo(int height,
              int width,
              float frameRate,
              String displayAspectRatio) {
        this.height = height;
        this.width = width;
        this.frameRate = frameRate;
        this.displayAspectRatio = displayAspectRatio;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public float getFrameRate() {
        return frameRate;
    }

    public String getDisplayAspectRatio() {
        return displayAspectRatio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof VideoInfo))
            return false;
        VideoInfo videoInfo = (VideoInfo) o;
        return height == videoInfo.height && width == videoInfo.width && Float.compare(videoInfo.frameRate, frameRate)
                == 0 && Objects.equals(displayAspectRatio, videoInfo.displayAspectRatio);
    }

    @Override
    public int hashCode() {

        return Objects.hash(height, width, frameRate, displayAspectRatio);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VideoInfo{");
        sb.append("height=")
          .append(height);
        sb.append(", width=")
          .append(width);
        sb.append(", frameRate=")
          .append(frameRate);
        sb.append(", displayAspectRatio='")
          .append(displayAspectRatio)
          .append('\'');
        sb.append('}');
        return sb.toString();
    }

}
