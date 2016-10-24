package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by demon on 20.10.2016.
 */
public class ZipArchiver implements Archiver {
    @Override
    public void createZipArchiveWithFile(String aName, String fName) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(aName));
        File file = new File(fName);
        FileInputStream fis = new FileInputStream(file);
        ZipEntry ze = new ZipEntry(file.getName());
        zos.putNextEntry(ze);

        wfftz(fis, zos);

        fis.close();
        zos.closeEntry();
        zos.close();
    }

    protected  void wfftz(FileInputStream is, ZipOutputStream zos) throws IOException{
        byte[] buf = new byte[8000];
        int length;
        while(true){
            length = is.read(buf);
            if (length < 0) break;
            zos.write(buf, 0, length);

        }
    }
}
