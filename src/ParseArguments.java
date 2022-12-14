import org.apache.commons.cli.*;

public class ParseArguments {
    private CommandLine cmd;
    private Options options;

    public void displayHelp(){
        String helpStr = "media-len <options> <source_option> <file/folder>\n" +
                "media-len -f file1, file2...fileN\n" +
                "media-len -f file1, file2...fileN\n" +
                "media-len -v -d directory_path\n" +
                "media-len -a -d directory_path\n" +
                "media-len --all -d directory_path\n";
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(helpStr, this.options);
    }

    public ParseArguments(String[] args) throws Exception{
        this.options = new Options();
        this.options.addOption("h", false, "displays this help menu");
        this.options.addOption("a", false, "only includes audio files in the  directory");
        this.options.addOption("v", false, "only includes video files in the directory");
        this.options.addOption("l", false, "list individual file with its length");
        this.options.addOption("all", false, "includes both audio and video files in the directory");
        this.options.addOption("d",true, "directory path");
        this.options.addOption(Option.builder("f").desc("single filename or multiple filenames with absolute/relative path")
                .hasArgs().build());

        CommandLineParser parser = new DefaultParser();
        this.cmd = parser.parse(options, args);

    }

    public boolean hasOption(String str){
        return this.cmd.hasOption(str);
    }

    public String[] getFiles(){
        return this.cmd.getOptionValues("f");
    }

    public String getDirectory(){
        return this.cmd.getOptionValue("d");
    }
}
