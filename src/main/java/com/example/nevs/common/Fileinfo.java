package com.example.nevs.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Fileinfo {
    private String filename;
    private String bucket;
    private boolean directory;
}
