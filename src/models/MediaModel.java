package models;

public class MediaModel {
    private String fileName;
    private double duration;

    public MediaModel(String fileName, double duration) {
        this.fileName = fileName;
        this.duration = duration;
    }

    public String getFileName() {
        return fileName;
    }

    public double getDuration() {
        return duration;
    }

}
