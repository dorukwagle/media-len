import contracts.MediaLength;
import models.MediaModel;

import java.io.File;
import java.util.ArrayList;

public class MediaCalc {
    private boolean isFolderLen = false;
    private boolean isAudio = false;
    private boolean isVideo = false;
    private boolean isDirectory = false;
    private boolean isFile = false;
    private boolean isListAll = false;
    private boolean isAudioVideo = false;
    private MediaLength mediaLength;

    private ParseArguments parser;
    public MediaCalc(MediaLength mediaLength, ParseArguments parser){
//        "a", false, "only includes audio files in the  directory");
//        "v", false, "only includes video files in the directory");
//        "l", false, "list individual file with its length");
//        "all", false, "includes both audio and video files in the directory");
//        "f", true, "single filename or multiple filenames with absolute/relative path");
//        "d",true, "directory path");

        this.mediaLength = mediaLength;
        this.parser = parser;

        //check for all the supplied cli arguments and initialize rest of values;
        if(this.parser.hasOption("h")){
            parser.displayHelp();
            return;
        }
        if(this.parser.hasOption("a"))
            this.isAudio = true;
        if(this.parser.hasOption("v"))
            this.isVideo = true;
        if(this.parser.hasOption("l"))
            this.isListAll = true;
        if(this.parser.hasOption("all"))
            this.isAudioVideo = true;
        if(this.parser.hasOption("f"))
            this.isFile = true;
        if(this.parser.hasOption("d"))
            this.isDirectory = true;
    }

    //validate all the options as well as values of the options
    private boolean validateOptions(){
        //option 'a', 'v' and 'all' requires -d option
        if((this.isAudio || this.isVideo || isAudioVideo) && !this.isDirectory){
            System.out.println("Expected -d flag");
            System.out.println("Type: media-len -h for help");
            return false;
        }
        //there can't be -f and -d options at the same time
        if(this.isDirectory && this.isFile){
            System.out.println("Invalid Syntax");
            System.out.println("Type: media-len -h for help");
            return false;
        }

        //now check if the supplied arguments has valid value
        if(this.isDirectory){
            try {
                File file = new File(this.parser.getDirectory());
                if(file.isFile()){
                    System.out.println("Expected a directory");
                    System.out.println("Type: media-len -h for help");
                    return false;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(this.isFile){
            try{
                File file;
                String[] str = this.parser.getFiles();
                for(String st: str){
                    file = new File(st);
                    if(!file.isFile()){
                        System.out.printf("not a file: %s\n", file.getAbsolutePath());
                        return false;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }

    //process the commands and display the answer
    public void process(){
        //check if the options are valid
        if(!this.validateOptions())
            return;
        //check if it is directory and audio video is selected
        if(this.isDirectory){
            if((this.isVideo && this.isAudio) || this.isAudioVideo)
                this.displayAudioVideoLen();
            else if(this.isAudio)
                this.displayAudiosLen();
            else if(this.isVideo)
                this.displayVideosLen();
        }
        else if(this.isFile)
            this.displayMediasLen();
    }

    //convert seconds to human-readable duration
    private String toHumanReadable(double seconds){
        double hours;
        double minutes;
        double sec;

        //first convert total seconds to minutes
        minutes = (int) (seconds / 60);
        sec = seconds % 60;
        //convert minutes to hours
        hours = (int) (minutes / 60);
        minutes = minutes % 60;

        return String.format("%f:%f:%f", hours, minutes, sec);
    }

    //convert ArrayList of models into displayable data
    private String createDisplayable(ArrayList<MediaModel> mediaInfos){
        try {
            StringBuilder displayable = new StringBuilder((this.isListAll ? "\nDuration \t Format \t File" : ""));
            int len = mediaInfos.size();
            //store total time if more than one file
            double total = 0;
            //now send the files to the MediaLength to calculate

            for( MediaModel model : mediaInfos){
                displayable.append(this.isListAll ?
                        (String.format("\n%s \t %s \t %s", this.toHumanReadable(model.getDuration()), model.getFormat(), model.getFileName())) :
                        ("\n" + model.getDuration()));
                if(len > 1)
                    total += model.getDuration();
            }
            if(len > 1){
                displayable.append(String.format("\nTotal : %s", this.toHumanReadable(total)));
            }
            return displayable.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //calculate and display media length
    private void displayMediasLen(){
        try {
            String[] files = this.parser.getFiles();
            ArrayList<MediaModel> mediaInfos = this.mediaLength.getMediasLen(files);
            System.out.println(this.createDisplayable(mediaInfos));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //calculate and display video length :directory
    private void displayVideosLen(){
        try {
            String directory = this.parser.getDirectory();
            ArrayList<MediaModel> mediaInfos = this.mediaLength.getDirVideosLen(directory);
            System.out.println(this.createDisplayable(mediaInfos));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //calculate and display audio length :directory
    private void displayAudiosLen(){
        try {
            String directory = this.parser.getDirectory();
            ArrayList<MediaModel> mediaInfos = this.mediaLength.getDirAudiosLen(directory);
            System.out.println(this.createDisplayable(mediaInfos));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //calculate and display audio and video length :directory
    private void displayAudioVideoLen(){
        this.displayVideosLen();
        this.displayAudiosLen();
    }

}
