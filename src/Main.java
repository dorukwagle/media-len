import calculators.MediaLenCalc;

public class Main {
    public static void main(String[] args){
        try {
            MediaLenCalc mediaLenCalc = new MediaLenCalc();
            //finally initialize MediaCalc that performs all the tasks
            MediaCalc mediaCalc = new MediaCalc(mediaLenCalc, args);
            //start processing the commands and display results
            mediaCalc.process();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
