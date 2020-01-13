import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Archiver {
    public static void main(String[] args) throws Exception{
        toZip(args);
    }

    public static void toZip(String[] nameOfFiles) throws Exception{
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("Archive.zip"));
        for(int i = 0; i < nameOfFiles.length; i++){
            File file = new File(nameOfFiles[i]);
            toZipRecursive(file, zipOutputStream);
        }
        zipOutputStream.close();

    }
    public static void toZipRecursive(File file,  ZipOutputStream zipOutputStream) throws Exception{
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(int i = 0; i < files.length; i++) {
                toZipRecursive(files[i], zipOutputStream);
            }
        }else {
            ZipEntry zipEntry = new ZipEntry(file.getPath());
            FileInputStream fileInputStream = new FileInputStream(file);
            zipOutputStream.putNextEntry(zipEntry);
            write(zipOutputStream, fileInputStream);
            zipOutputStream.closeEntry();
        }
    }

    public static void write(ZipOutputStream zipOutputStream , FileInputStream fileInputStream) throws Exception{
        byte[] bytes = new byte[1024];
        int length;
        while((length = fileInputStream.read(bytes)) >= 0) {
            zipOutputStream.write(bytes, 0, length);
        }
        fileInputStream.close();
    }

}
