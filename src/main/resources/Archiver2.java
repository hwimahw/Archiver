import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Archiver2{

    public static void unzip(File file, String str) throws FileNotFoundException, IOException{
        ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
        ZipFile zf = new ZipFile(file.getName());
        ZipEntry ze;

        while((ze = zis.getNextEntry()) != null){
            //  System.out.println(ze.getName());
            if(!ze.isDirectory()){

                File f = new File(str + ze.getName());
                f.createNewFile();
                write(zf.getInputStream(ze), new FileOutputStream(f));
            }
            else{
                new File(str + ze.getName()).mkdir();
            }

        }
        zf.close();
    }

    public static void zip (File file, ZipOutputStream zos) throws IOException{
        if(file.isDirectory()){
            zos.putNextEntry(new ZipEntry(file.getPath() + "/"));
            zos.closeEntry();
            File[] arrOfFiles = file.listFiles();
            for(int i = 0; i < arrOfFiles.length; i++){
                file = arrOfFiles[i];
                zip(file, zos);
            }

        }
        else{
            zos.putNextEntry(new ZipEntry(file.getPath()));
            write(new FileInputStream(file), zos);
            zos.closeEntry();
        }


    }

    public static void write(InputStream is, OutputStream os) throws IOException{
        byte[] array = new byte[1024];
        int len;
        while((len = is.read(array)) >= 0){
            os.write(array,0, len);
        }
        is.close();
    }

    public static void addFileToArchive(int flag, File file, ZipInputStream zis, ZipOutputStream zos) throws FileNotFoundException, IOException{

        ZipEntry ze;
        if(flag == 0) {
            while ((ze = zis.getNextEntry()) != null) {
                zos.putNextEntry(ze);
                zos.closeEntry();
            }
        }
        zip(file, zos);
    }

    public static void main(String[] args) throws Exception {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("AAR.zip"));

        for (int i = 0; i < args.length; i++) {
            File file = new File(args[i]);
            zip(file, zos);


        }
        zos.close();

//
//        if(args.length == 0){
//            return;
//        }
//        switch(args[0]) {
//            case "unzip": {
//                String str = "";
//                if(args.length == 3){
//                    Path path = Paths.get(args[2]);
//                    if(Files.exists(path)) {
//                        str = args[2];
//                    }
//                    else{
//                        File file = new File(args[2]);
//                        file.mkdirs();
//                        str = args[2];
//                    }
//                }
//                else{
//                    str = "";
//                }
//                unzip(new File(args[1]), str);
//                break;
//            }
//            case "zip": {
//                int flag = 0;
//                if(args[2].compareTo("withComment") == 0){
//                    flag = 1;
//                }
//                ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(args[1]));
//                int k;
//                if(flag == 1) {
//                    zos.setComment(args[3]);
//                    k = 4;
//                }
//                else {
//                    k = 2;
//                }
//                for (int i = k; i < args.length; i++) {
//                    File file = new File(args[i]);
//                    zip(file, zos);
//                }
//                zos.close();
//                break;
//            }
//            case "add": {
//                ZipInputStream zis = new ZipInputStream(new FileInputStream(args[1]));
//                ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("NewArchive"));
//                int flag = 0;
//                for (int i = 2; i < args.length; i++) {
//                    File file = new File(args[i]);
//                    addFileToArchive(flag, file, zis, zos);
//                    flag = 1;
//                }
//                File f1 = new File(args[1]);
//                File f2 = new File("NewArchive");
//                f2.renameTo(f1);
//                //f1.delete();
//                zis.close();
//                zos.close();
//                break;
//            }
//            case "getComment":{
//                ZipFile zf = new ZipFile(args[1]);
//                String str = zf.getComment();
//                System.out.println(str);
//                break;
//            }
//            case "setComment":{
//                ZipInputStream zis = new ZipInputStream(new FileInputStream(args[1]));
//                ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("NewArchive"));
//                zos.setComment(args[2]);
//                ZipEntry ze;
//                while ((ze = zis.getNextEntry()) != null) {
//                    zos.putNextEntry(ze);
//                    zos.closeEntry();
//                }
//                File f1 = new File(args[1]);
//                File f2 = new File("NewArchive");
//                f2.renameTo(f1);
//                //f1.delete();
//                zis.close();
//                zos.close();
//                break;
//
//            }
//            default:{
//                return;
//            }
//        }
    }
}