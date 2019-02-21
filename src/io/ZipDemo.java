package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipDemo {

    public static void main(String[] args) throws Exception {
        // createZip();
//		unzip();
        list2();
    }

    private static void createZip() throws Exception {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
                "test.zip"))) {
            zos.setLevel(Deflater.BEST_COMPRESSION);
            ZipEntry ze1 = new ZipEntry("f2.txt");
            zos.putNextEntry(ze1);
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream("f2.txt"));
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
                zos.write(buffer, 0, len);
            }
            bis.close();
            ZipEntry ze2 = new ZipEntry("f4.txt");
            zos.putNextEntry(ze2);
            BufferedInputStream bis2 = new BufferedInputStream(
                    new FileInputStream("f3.txt"));
            byte[] buffer2 = new byte[1024];
            int len2 = -1;
            while ((len2 = bis2.read(buffer2, 0, buffer2.length)) != -1) {
                zos.write(buffer2, 0, len2);
            }
            bis2.close();
            zos.closeEntry();
        }
    }

    private static void unzip() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(
                "test.zip"))) {
            ZipEntry entry = null;
            while ((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();
                String path = "test" + File.separator + name;
                File file = new File(path);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(file));
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = zis.read(buffer, 0, buffer.length)) != -1) {
                    bos.write(buffer, 0, len);
                }
                bos.close();
            }
        }
    }

    public static void list() throws Exception {
        ZipFile f = new ZipFile("test.zip");
        Enumeration<? extends ZipEntry> e = f.entries();
        while(e.hasMoreElements()){
            ZipEntry ze=e.nextElement();
            System.out.println(ze.getName());
        }
    }

    private static void list2() throws Exception{
        ZipFile f = new ZipFile("test.zip");
        f.stream().forEach(entry->System.out.println(entry.getName()));
    }
}

