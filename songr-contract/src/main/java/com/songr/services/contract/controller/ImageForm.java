package com.songr.services.contract.controller;


import com.mysql.cj.jdbc.Blob;

public class ImageForm {
    Blob thumbnail;

    public Blob getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Blob thumbnail) {
        this.thumbnail = thumbnail;
    }
}