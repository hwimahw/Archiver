import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Archiver {
    public static void main(String[] args) throws Exception{
       // toZip(args);
        unzip();

    }

    public static void toZip(String[] nameOfFiles) throws Exception{
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("Archive.zip"));
        for(int i = 0; i < nameOfFiles.length; i++){
            File file = new File(nameOfFiles[i]);
            toZipRecursive(file, zipOutputStream);
        }
        zipOutputStream.close();

    }
    private static void toZipRecursive(File file,  ZipOutputStream zipOutputStream) throws Exception{
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(int i = 0; i < files.length; i++) {
                toZipRecursive(files[i], zipOutputStream);
            }
        }else {
            String name = file.getPath();
            System.out.println(name);
            ZipEntry zipEntry = new ZipEntry(file.getPath());
            FileInputStream fileInputStream = new FileInputStream(file);
            zipOutputStream.putNextEntry(zipEntry);
            write(zipOutputStream, fileInputStream);
            zipOutputStream.closeEntry();
        }
    }

    public static void unzip() throws Exception{
        String zipFileName = "./src/main/resources/Archive.zip";
        String destDir = "./src/main/resources/Archiver";
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFileName));
        ZipFile zipFile = new ZipFile(zipFileName);
        ZipEntry zipEntry;
        while((zipEntry = zipInputStream.getNextEntry()) != null){
            String fileNameInArchive = zipEntry.getName();
            File file = new File(destDir + File.separator + fileNameInArchive);
            System.out.println("Unzipping to "+file.getAbsolutePath());
            //create directories for sub directories in zip
            new File(file.getParent()).mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            write(fileOutputStream, zipFile.getInputStream(zipEntry));
        }
    }

    public static void write(OutputStream outputStream , InputStream inputStream) throws Exception{
        byte[] bytes = new byte[1024];
        int length;
        while((length = inputStream.read(bytes)) >= 0) {
            outputStream.write(bytes, 0, length);
        }
      //  inputStream.close();
    }

}
