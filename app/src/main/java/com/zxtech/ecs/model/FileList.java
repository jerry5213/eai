package com.zxtech.ecs.model;

public class FileList {

    private int FileId;
    private String FileUrl;
    private String FileName;
    private String FileContent;
    private String base64Bitmap;

    public FileList() {
    }

    public FileList(String FileUrl, String fileContent) {
        FileUrl = FileUrl;
        FileContent = fileContent;
    }

    public int getFileId() {
        return FileId;
    }

    public void setFileId(int fileId) {
        FileId = fileId;
    }

    public String getBase64Bitmap() {
        return base64Bitmap;
    }

    public void setBase64Bitmap(String base64Bitmap) {
        this.base64Bitmap = base64Bitmap;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String fileName) {
        FileUrl = fileName;
    }

    public String getFileContent() {
        return FileContent;
    }

    public void setFileContent(String fileContent) {
        FileContent = fileContent;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }
}