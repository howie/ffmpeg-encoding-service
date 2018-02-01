package tw.howie.lab.ffmpeg;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.job.FFmpegJob;
import org.junit.Before;
import org.junit.Test;
import tw.howie.lab.ffmpeg.domain.Video;
import tw.howie.lab.ffmpeg.service.EncodingService;

import java.io.IOException;

/**
 * @author howie
 * @since 2018/2/1
 */
public class EncodingServiceIntegrationTest {

    private EncodingService encodingService;

    @Before
    public void before() throws IOException {
        FFmpeg ffmpeg = new FFmpeg("/usr/local/bin/ffmpeg");
        FFprobe ffprobe = new FFprobe("/usr/local/bin/ffprobe");
        encodingService = new EncodingService(ffprobe, ffmpeg);

    }

    @Test
    public void encoding() throws IOException, InterruptedException {

        String input = "/tmp/SER_480x854.mp4";
        String output = "/tmp/encoding_640x480_337k_44100.mp4";
        Video video = encodingService.videoInfoExtract(input);
        System.out.println(video);
        FFmpegJob job = encodingService.encoding(video, output);

        System.out.println("State:" + job.getState());

    }

    @Test
    public void thumbnail() throws IOException {
        String input = "/tmp/SER_480x854.mp4";
        String output = "/tmp/thumbnail_%05d.png";
        Video video = encodingService.videoInfoExtract(input);
        System.out.println(video);
        FFmpegJob job = encodingService.thumbnail(video, output, 100);

        System.out.println("State:" + job.getState());
    }

}