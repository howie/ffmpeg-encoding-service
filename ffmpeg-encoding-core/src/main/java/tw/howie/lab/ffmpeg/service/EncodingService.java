package tw.howie.lab.ffmpeg.service;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tw.howie.lab.ffmpeg.domain.Video;
import tw.howie.lab.ffmpeg.domain.VideoFormat;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author howie
 * @since 2018/2/1
 */
public class EncodingService {

    private final Logger log = LoggerFactory.getLogger(EncodingService.class);

    private final FFprobe ffprobe;

    private final FFmpeg ffmpeg;

    private final FFmpegExecutor executor;

    @Inject
    public EncodingService(FFprobe ffprobe,
                           FFmpeg ffmpeg) {
        this.ffprobe = ffprobe;
        this.ffmpeg = ffmpeg;
        executor = new FFmpegExecutor(ffmpeg, ffprobe);
    }

    public FFmpegJob thumbnail(Video sourceVideo,
                               String outputPath,
                               int framePosition) {

        FFmpegBuilder builder = new FFmpegBuilder().setInput(sourceVideo.getSourcePath())
                                                   .addOutput(outputPath)
                                                   .setFrames(1)
                                                   .addExtraArgs("-ss", "" + framePosition)
                                                   //.setVideoFrameRate(FFmpeg.FPS_29_97)
                                                   //.setVideoFilter("select='gte(n\\,10)',scale=200:-1")
                                                   .done();

        FFmpegJob job = executor.createJob(builder);
        job.run();
        return job;
    }

    public FFmpegJob encoding(Video sourceVideo,
                              String outputPath) {

        FFmpegBuilder builder = new FFmpegBuilder().setInput(sourceVideo.getSourcePath())     // Filename, or a FFmpegProbeResult
                                                   .overrideOutputFiles(true) // Override the output if it exists

                                                   .addOutput(outputPath)                       // Filename for the destination
                                                   .setFormat(VideoFormat.MP4.getFormat()) // Format is inferred from filename, or can be set
                                                   //.setTargetSize(250_000)  // Aim for a 250KB file
                                                   //.disableSubtitle()       // No subtiles
                                                   .setAudioChannels(sourceVideo.getAudioInfo()
                                                                                .getChannels())         // Mono audio
                                                   .setAudioCodec("aac")        // using the aac codec
                                                   .setAudioSampleRate(48_000)  // at 48KHz
                                                   .setAudioBitRate(32768)      // at 32 kbit/s

                                                   .setVideoCodec("libx264")     // Video using x264
                                                   .setVideoFrameRate(sourceVideo.getVideoInfo()
                                                                                 .getFrameRate())
                                                   .setVideoFrameRate(24, 1)     // at 24 frames per second
                                                   .setVideoResolution(640, 480) // at 640x480 resolution
                                                   .setPreset("fast")
                                                   .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
                                                   .done();

        ///videos/encoded/funp-admin.tenmax.io/b09a4b3389f09dc238fdbc8d69c46779_1280x720_3038k_44100.mp4
        // ffmpeg -v warning -progress progress.log -y -i
        // /videos/source/funp-admin.tenmax.io/b09a4b3389f09dc238fdbc8d69c46779.mp4
        // -c:a aac -ac 2 -c:v libx264 -preset:v fast -threads 0 -r 29.97002997002997 -g 59.94005994005994
        // -sc_threshold 0 -b:v 3038k -s:v 1280x720
        // /videos/encoded/funp-admin.tenmax.io/b09a4b3389f09dc238fdbc8d69c46779_1280x720_3038k_44100.mp4'

        // Run a one-pass encode
        FFmpegJob job = executor.createJob(builder, new ProgressListener() {

            // Using the FFmpegProbeResult determine the duration of the input
            final double duration_ns = sourceVideo.getDuration() * TimeUnit.SECONDS.toNanos(1);

            @Override
            public void progress(Progress progress) {
                double percentage = progress.out_time_ns / duration_ns;

                // Print out interesting information about the progress
                System.out.println(String.format("[%.0f%%] status:%s frame:%d time:%s ms fps:%.0f speed:%.2fx",
                                                 percentage * 100,
                                                 progress.status,
                                                 progress.frame,
                                                 FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS),
                                                 progress.fps.doubleValue(),
                                                 progress.speed));
            }
        });

        job.run();

        return job;
    }

    public Video videoInfoExtract(String inputPath) throws IOException {

        FFmpegProbeResult probeResult = ffprobe.probe(inputPath);

        return Video.build(inputPath, probeResult.getFormat(), probeResult.getStreams());

    }
}
