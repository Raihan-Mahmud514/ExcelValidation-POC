package excelvalidation.util;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static excelvalidation.readexcel.dirPath;

public class amtUtilities {
    public static void sleep(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        }
        catch (Exception e) {
            /// throw new Exception("Pause between steps was interrupted", e);
            e.printStackTrace();
        }
    }
    public static void moveFiles(String path){
        File src = new File(path+"\\Data");
        createDir(dirPath,"DataVault");
        File dest = new File(path+"\\DataVault");

        try {
            FileUtils.copyDirectory(src, dest);
            System.out.println("Moving previous datafiles to vault");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtils.cleanDirectory(src);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void createDir(String path, String dir){
        String directoryName = dirPath+"\\"+dir;

        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdir();
            System.out.println("Created New Directory- "+dir);
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }
    }
}
