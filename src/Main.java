import calculators.MediaLenCalc;

public class Main {
    public static void main(String[] args){
        try {
            MediaLenCalc mediaLenCalc = new MediaLenCalc();
            ParseArguments parser = new ParseArguments(args);

            //finally initialize MediaCalc that performs all the tasks
            MediaCalc mediaCalc = new MediaCalc(mediaLenCalc, parser);
            //start processing the commands and display results
            mediaCalc.process();
        }catch (Exception e){
            if(e.getMessage().equals("NoMediaException")){
                System.out.println("No media files found in the given directory");
                return;
            }
            e.printStackTrace();
        }
    }
}
