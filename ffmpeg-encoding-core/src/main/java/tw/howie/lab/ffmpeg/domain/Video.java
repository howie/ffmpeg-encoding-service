package tw.howie.lab.ffmpeg.domain;

import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegStream;

import java.util.List;

/**
 * @author howie
 * @since 2018/2/1
 */
public class Video {

    private final String sourcePath;

    private final String sourceFormat;

    private final double duration;

    private final long bitRate;

    private final VideoInfo videoInfo;

    private final AudioInfo audioInfo;

    private Video(String sourcePath,
                  String sourceFormat,
                  double duration,
                  long bitRate,
                  VideoInfo videoInfo,
                  AudioInfo audioInfo) {
        this.sourcePath = sourcePath;
        this.sourceFormat = sourceFormat;
        this.duration = duration;
        this.bitRate = bitRate;
        this.videoInfo = videoInfo;
        this.audioInfo = audioInfo;
    }

    public VideoInfo getVideoInfo() {
        return videoInfo;
    }

    public AudioInfo getAudioInfo() {
        return audioInfo;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public String getSourceFormat() {
        return sourceFormat;
    }

    public double getDuration() {
        return duration;
    }

    public long getBitRate() {
        return bitRate;
    }

    public static Video build(String sourcePath,
                              FFmpegFormat format,
                              List<FFmpegStream> streams) {

        return new Video(sourcePath,
                         format.format_name,
                         format.duration,
                         format.bit_rate,
                         new VideoInfo(streams.get(0).height,
                                       streams.get(0).width,
                                       streams.get(0).r_frame_rate.floatValue(),
                                       streams.get(0).display_aspect_ratio),
                         new AudioInfo(streams.get(1).channels, streams.get(1).codec_name, streams.get(1).sample_rate));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Video{");
        sb.append("sourcePath='")
          .append(sourcePath)
          .append('\'');
        sb.append(", sourceFormat='")
          .append(sourceFormat)
          .append('\'');
        sb.append(", duration=")
          .append(duration);
        sb.append(", bitRate=")
          .append(bitRate);
        sb.append(", videoInfo=")
          .append(videoInfo);
        sb.append(", audioInfo=")
          .append(audioInfo);
        sb.append('}');
        return sb.toString();
    }
}
