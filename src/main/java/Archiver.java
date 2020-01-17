import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Archiver {
    public static void main(String[] args) throws Exception{
      //  zip(args);
        // unzip();
        //    ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("./src/main/resources/Archive.zip"));
     //   zip(args, zipOutputStream);
        addNewFilesToArchive(args);
    }

    public static void zip(String[] nameOfFiles, ZipOutputStream zipOutputStream) throws Exception{
        System.out.println(nameOfFiles.length);
        for(int i = 0; i < nameOfFiles.length; i++){
            File file = new File("./src/main/resources/" + nameOfFiles[i]);
            StringBuilder pathForZipEntry = new StringBuilder("");
            zipRecursive(pathForZipEntry,file, zipOutputStream);
        }
        zipOutputStream.close();

    }
    private static void zipRecursive(StringBuilder pathForZipEntry, File file,  ZipOutputStream zipOutputStream) throws Exception{
        pathForZipEntry.append(file.getName());
        if(file.isDirectory()){
            pathForZipEntry.append(File.separator);
            System.out.println(file.getPath() + "!");
            ZipEntry zipEntry = new ZipEntry(file.getPath() + "/"); // с помощью file.getPath в архиве будут создаваться папки
            System.out.println(pathForZipEntry.toString()+"@@@@@");
            System.out.println(file.getPath());
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.closeEntry();
            File[] files = file.listFiles();
            System.out.println(22222222);
            for(int i = 0; i < files.length; i++) {
                zipRecursive(pathForZipEntry,files[i], zipOutputStream);
            }
        }else {
            //  System.out.println(pathForZipEntry.toString()+"@@@@@");
            ZipEntry zipEntry = new ZipEntry(pathForZipEntry.toString()); // с помощью file.getPath в архиве будут создаваться папки
            zipOutputStream.putNextEntry(zipEntry);
            FileInputStream fileInputStream = new FileInputStream(file);
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
          //  System.out.println("Unzipping to "+file.getAbsolutePath());
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
        inputStream.close();
    }

    public static void addNewFilesToArchive(String[] newFileNames) throws Exception{
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("./src/main/resources/Archive2.zip"));
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream("./src/main/resources/Archive.zip"));
        ZipEntry zipEntry;
        while((zipEntry = zipInputStream.getNextEntry()) != null){
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.closeEntry();
        }
        System.out.println(newFileNames[0]);
        zip(newFileNames, zipOutputStream);


        File file = new File("./src/main/resources/Archive.zip");
        file.delete();
        File newArchive = new File("./src/main/resources/Archive2.zip");
        newArchive.renameTo(file);
        zipOutputStream.close();
    }

}
