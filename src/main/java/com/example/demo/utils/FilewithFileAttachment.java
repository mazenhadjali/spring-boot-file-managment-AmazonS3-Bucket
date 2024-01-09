package com.example.demo.utils;

import com.example.demo.entity.FileAttachment;

public class FilewithFileAttachment {

    private byte[] file;
    private FileAttachment fileAttachment;

    public FilewithFileAttachment(byte[] file, FileAttachment fileAttachment) {
        this.file = file;
        this.fileAttachment = fileAttachment;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public FileAttachment getFileAttachment() {
        return fileAttachment;
    }

    public void setFileAttachment(FileAttachment fileAttachment) {
        this.fileAttachment = fileAttachment;
    }
}
