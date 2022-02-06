package com.demo.ioc.models;

import com.demo.ioc.enums.DirectoryType;

public class DirectoryInf {
    private final String directoryPath;
    private final DirectoryType directoryType;

    public DirectoryInf(String directoryPath, DirectoryType directoryType) {
        this.directoryPath = directoryPath;
        this.directoryType = directoryType;
    }

    public DirectoryType getDirectoryType() {
        return directoryType;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }
}
