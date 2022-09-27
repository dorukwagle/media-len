package contracts;

import models.MediaModel;

import java.util.ArrayList;

public interface MediaLength {
    //length of all audios inside a directory
    ArrayList<MediaModel> getDirAudiosLen(String directory) throws Exception;

    //length of all videos inside a directory
    ArrayList<MediaModel> getDirVideosLen(String directory) throws Exception;

    //length of multiple media files
    ArrayList<MediaModel> getMediasLen(String[] filenames) throws Exception;
}
