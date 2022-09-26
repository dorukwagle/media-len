package contracts;

import models.MediaModel;

import java.util.ArrayList;

public interface MediaLength {
    //length of single audio/video file
    MediaModel getMediaLen(String filename) throws Exception;

    //length of all audios inside a directory
    ArrayList<MediaModel> getDirAudioLen(String directory) throws Exception;

    //length of all videos inside a directory
    ArrayList<MediaModel> getDirVidoeLen(String directory) throws Exception;

    //length of multiple media files
    ArrayList<MediaModel> getMediasLen(String[] filenames) throws Exception;
}
