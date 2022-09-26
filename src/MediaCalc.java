import calculators.MediaLenCalc;
import contracts.MediaLength;

import java.io.File;

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

        ;
    }

}
