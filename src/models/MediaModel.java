package models;

public class MediaModel {
    private String fileName;
    private double duration;
    private String format;

    public MediaModel(String fileName, double duration, String format) {
        this.fileName = fileName;
        this.duration = duration;
        this.format = format;
    }

    public String getFileName() {
        return fileName;
    }

    public double getDuration() {
        return duration;
    }

    public String getFormat() {
        return format;
    }
}
