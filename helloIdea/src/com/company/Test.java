package com.company;

import java.io.IOException;

public class Test {

    public static void main(String[] args) {
        try {
            Archiver zipper = new ZipArchiver();
            zipper.createZipArchiveWithFile(args[0], args[1]);
        } catch (IOException e) {
            System.out.println("cannot read file" + e.toString());
        } catch (Exception e) {
            System.out.println("error" + e.toString());
        }
        System.out.println("success");
    }
}
