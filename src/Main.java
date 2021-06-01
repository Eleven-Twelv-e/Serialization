import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main  {
    static LocalDateTime time = LocalDateTime.now();
    static StringBuilder sb = new StringBuilder("");
    static String ps = File.separator;
    static String path = "D:"+ps+"Java Web Projects"+ps+"Directories"+ps+"Games";



    public static void main(String[] args) throws IOException {

        GameProgress gmOne = new GameProgress(2,3,5,3.4);
        GameProgress gmTwo = new GameProgress(2,7,9,5.4);
        GameProgress gmTree = new GameProgress(7,1,5,8.2);

        List<String> gameProgressPath = new ArrayList<>();
        gameProgressPath.add(path+"\\savegames\\");
        gameProgressPath.add(path+"\\savegames\\");
        gameProgressPath.add(path+"\\savegames\\");

        createFolders(path+ps+"src"+ps+"main");
        createFiles(path+ps+"src"+ps+"test");
        createFolders(path+ps+"res"+ps+"drawables");
        createFolders(path+ps+"res"+ps+"vectors");
        createFolders(path+ps+"res"+ps+"icons");
        createFolders(path+ps+"savegames");
        createFolders(path+ps+"temp");

        createFiles(path+ps+"src"+ps+"main"+ps+"Main.java");
        createFiles(path+ps+"src"+ps+"main"+ps+"Utils.java");
        createFiles(path+ps+"temp"+ps+"temp.txt");

        saveGame(path+ps+"savegames"+ps+"one.dat",gmOne);
        saveGame(path+ps+"savegames"+ps+"two.dat",gmTwo);
        saveGame(path+ps+"savegames"+ps+"three.dat",gmTree);

        zipFiles(path+ps+"savegames"+ps,gameProgressPath);
        deleteFiles(path+"");


    }

    private static File createFiles(String path) throws IOException{
        File file = new File(path);
        if (file.createNewFile()){
            writeLog(sb.append(time + " Files are created successfully! \n"));
        }else if (file.exists()){
            writeLog(sb.append(time + " Files are already exist!  \n"));
        }else {
            writeLog(sb.append(time + " Error when try to create files!\n " ));
        }
        return file;
    }


    private static File createFolders(String path) throws IOException {
        File file = new File(path);
        if(file.mkdirs()){
            writeLog(sb.append(time + " Directories are create successfully! \n"));
        }else if (file.exists()){
            writeLog(sb.append(time + " Directories are already exist! \n"));
        }else {
            writeLog(sb.append(time + " Error when try to create directories! \n"));
        }
        return file;
    }

    public static void writeLog(StringBuilder sb) throws IOException{
        try (FileWriter write = new FileWriter(path + ps+"temp"+ps+"temp.txt")) {
            write.write("\n" + sb.append("\n"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveGame(String pth, GameProgress gm) throws IOException{

        try(FileOutputStream fos = new FileOutputStream(pth)  ) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
            objectOutputStream.writeObject(gm);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static  void zipFiles(String pth, List<String> list) throws IOException{
        ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pth + "\\output.zip"));

        for (int i = 0; i < list.size(); i++) {
            String filename = list.get(i);
            FileInputStream fis = new FileInputStream(filename);
            ZipEntry entry = new ZipEntry("ex" + i + ".dat");
            zout.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
            fis.close();
        }
        zout.close();
    }

    public static void deleteFiles(String path) {

        String toDel = ".dat";
        final FilenameFilter filter = new ExtensionFilter(toDel);
        final File dir = new File(path);
        String[] filenames = dir.list(filter);
        if (filenames != null) {
            for (String filename : filenames) {
                String fullFilename = path + File.separator + filename;
                File file = new File(fullFilename);
                file.delete();
            }
        } else {
            try {
                writeLog(sb.append(time + " Nothing to delete \n"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ExtensionFilter implements FilenameFilter {
        private final String toDel;

        public ExtensionFilter(String ext) {

            toDel = ext;
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(toDel);
        }
    }

}
