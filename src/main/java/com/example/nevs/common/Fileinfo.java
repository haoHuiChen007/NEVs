package com.example.nevs.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Fileinfo {
    private String filename;
    private String bucket;
    private boolean directory;
    public String getUrl(){
        return "http://192.168.78.128:9002/"+ bucket+"/"+directory+"/" + filename;

    }
}
