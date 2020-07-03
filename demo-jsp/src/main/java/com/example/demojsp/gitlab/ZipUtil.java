package com.example.demojsp.gitlab;


import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * zip解压示例
 *
 * @author : ydfind
 * @date : 2019-06-06
 */
public class ZipUtil {

    public static void main(String[] args) throws IOException {
        String filename = "D:\\a.zip";
        String path = "D:\\";
        ZipUtil.unZip(filename, path);
    }


    public static void unZip(String sourceFilename, String targetDir) throws IOException {
        unZip(new File(sourceFilename), targetDir);
    }

    /**
     * 将sourceFile解压到targetDir
     * @param sourceFile
     * @param targetDir
     * @throws RuntimeException
     */
    public static void unZip(File sourceFile, String targetDir) throws IOException {
        long start = System.currentTimeMillis();
        if (!sourceFile.exists()) {
            throw new FileNotFoundException("cannot find the file = " + sourceFile.getPath());
        }
        ZipFile zipFile = null;
        try{
            zipFile = new ZipFile(sourceFile);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (entry.isDirectory()) {
                    String dirPath = targetDir + "/" + entry.getName();
                    createDirIfNotExist(dirPath);
                } else {
                    File targetFile = new File(targetDir + "/" + entry.getName());
                    createFileIfNotExist(targetFile);
                    InputStream is = null;
                    FileOutputStream fos = null;
                    try {
                        is = zipFile.getInputStream(entry);
                        fos = new FileOutputStream(targetFile);
                        int len;
                        byte[] buf = new byte[1024];
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }
                    }finally {
                        try{
                            fos.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try{
                            is.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        } finally {
            if(zipFile != null){
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void createDirIfNotExist(String path){
        File file = new File(path);
        createDirIfNotExist(file);
    }

    public static void createDirIfNotExist(File file){
        if(!file.exists()){
            file.mkdirs();
        }
    }

    public static void createFileIfNotExist(File file) throws IOException {
        createParentDirIfNotExist(file);
        file.createNewFile();
    }

//    public static void createParentDirIfNotExist(String filename){
//        File file = new File(filename);
//        createParentDirIfNotExist(file);
//    }

    public static void createParentDirIfNotExist(File file){
        createDirIfNotExist(file.getParentFile());
    }
}
