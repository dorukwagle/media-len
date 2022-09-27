package calculators;
import contracts.MediaLength;
import models.MediaModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MediaLenCalc implements MediaLength {

    private MediaModel getMediaLen(String filename) throws Exception{
        BufferedReader bufferedReader;
        String error = "", e;
        MediaModel model = null;
        String[] cmd = {"ffprobe", "-v", "error",  "-show_entries", "format=duration", "-of", "default=noprint_wrappers=1:nokey=1", filename};
        Process process = Runtime.getRuntime().exec(cmd);

        //first, check for error
        bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        if((e = bufferedReader.readLine()) != null){
            error += e + "\n";
            while ((e = bufferedReader.readLine()) != null){
                error += e + "\n";
            }
            throw new Exception("CommandError:~ " + error);
        }

        //now read the output of the command
        bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String duration;
        if((duration = bufferedReader.readLine()) != null){
            model = new MediaModel(new File(filename).getName(), Double.parseDouble(duration));
        }
        return model;
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
