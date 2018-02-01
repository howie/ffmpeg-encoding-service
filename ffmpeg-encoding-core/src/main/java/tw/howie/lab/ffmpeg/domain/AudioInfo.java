package tw.howie.lab.ffmpeg.domain;

import java.util.Objects;

/**
 * @author howie
 * @since 2018/2/1
 */
public class AudioInfo {

    private int channels;

    private String codecName;

    private int sampleRate;

    AudioInfo(int channels,
              String codecName,
              int sampleRate) {
        this.channels = channels;
        this.codecName = codecName;
        this.sampleRate = sampleRate;
    }

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public String getCodecName() {
        return codecName;
    }

    public void setCodecName(String codecName) {
        this.codecName = codecName;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AudioInfo))
            return false;
        AudioInfo audioInfo = (AudioInfo) o;
        return channels == audioInfo.channels && sampleRate == audioInfo.sampleRate && Objects.equals(codecName,
                                                                                                      audioInfo.codecName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(channels, codecName, sampleRate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AudioInfo{");
        sb.append("channels=")
          .append(channels);
        sb.append(", codecName='")
          .append(codecName)
          .append('\'');
        sb.append(", sampleRate=")
          .append(sampleRate);
        sb.append('}');
        return sb.toString();
    }

}
