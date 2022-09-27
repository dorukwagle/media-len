package calculators;
import contracts.MediaLength;
import models.MediaModel;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;


public class MediaLenCalc implements MediaLength {
    private FFprobe fprobe;

    public MediaLenCalc() throws Exception{
        this.fprobe = new FFprobe("/usr/bin/ffprobe");;
    }

    private MediaModel getMediaLen(String filename) throws Exception{
        FFmpegProbeResult result = fprobe.probe(filename);
        FFmpegFormat format = result.getFormat();
        return new MediaModel(
                format.filename,
                format.duration,
                format.format_long_name
                );
    }

    @Override
    public ArrayList<MediaModel> getDirAudiosLen(String directory) throws Exception {
        String[] filenames = this.getMediaFiles(directory, "audio");
        return this.getMediasLen(filenames);
    }

    @Override
    public ArrayList<MediaModel> getDirVideosLen(String directory) throws Exception{
        String[] filenames = this.getMediaFiles(directory, "video");
        return this.getMediasLen(filenames);
    }

    @Override
    public ArrayList<MediaModel> getMediasLen(String[] filenames) throws Exception{
        ArrayList<MediaModel> medias = new ArrayList<>();
        for(String filename : filenames){
            medias.add(this.getMediaLen(filename));
        }
        return medias;
    }

    private String[] getMediaFiles(String directory, String mediaType) throws Exception{
        //video format lists
        String videoFormats = "mp4, mov, wmv, avi, avchd, flv, f4v, swf, mkv, webm, mpeg-2";
        //audio format lists
        String audioFormats = "pcm, wav, aiff, mp3, aac, ogg, wma, flac, alac, m4a";
        //list all the files in the given directory
        File file = new File(directory);
        //apply filter and only list files, exclude folders and non video files
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if(!file.isFile())
                    return false;
                String name = file.getName();
                name = name.toLowerCase();
                String[] part = name.split(".");
                String format = part[part.length - 1];
                String formatList = (mediaType.equals("video")? videoFormats : audioFormats);
                if(formatList.contains(format))
                    return true;
                else
                    return false;
            }
        });

        //now convert file array to String array
        if(files == null)
            throw new Exception("NoMediaException");

        String[] filenames = new String[files.length];
        for (int i = 0; i<files.length; ++i){
            filenames[i] = files[i].getAbsolutePath();
        }
        return filenames;
    }
}
