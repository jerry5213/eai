package com.zxtech.ecs.model;

import java.io.Serializable;

public class FileBean implements Serializable {

    private int id;
    private String fileName;
    private String url;
    private int length;
    private int finished;

    public FileBean() {
    }

    public FileBean(int id, String fileName, String url, int finished) {
        this.id = id;
        this.fileName = fileName;
        this.url = url;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }


    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        int progress = (int) (finished*1.0f/length *100);
        return "FileBean{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", length=" + length +
                ", finished=" + finished +
                "，progress="+progress+
                '}';
    }
}
