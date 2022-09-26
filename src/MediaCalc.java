import calculators.MediaLenCalc;
import contracts.MediaLength;

public class MediaCalc {
    private boolean isFolderLen = false;
    private boolean isAudio = false;
    private boolean isVideo = false;
    private boolean isDirectory = false;
    private boolean isListAll = false;
    private MediaLength mediaLength;

    private ParseArguments parser;
    public MediaCalc(MediaLength mediaLength, ParseArguments parser){
        this.mediaLength = mediaLength;
        this.parser = parser;
    }

    //process the commands and display the answer
    public void process(){

    }

}
