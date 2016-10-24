package com.company;

import java.io.IOException;

/**
 * Created by demon on 20.10.2016.
 */
public interface Archiver {
    public void createZipArchiveWithFile(String aName, String fName)
        throws IOException;
}
