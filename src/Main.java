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
    static String path = "D:\\Java Web Projects\\Directories\\Games";

   static File file = new File(path);
   static File file1 = new File(path + "\\src");
    static File file2 = new File(path + "\\res\\drawables");
    static File file3 = new File(path + "\\savegames");
    static File file4 = new File(path + "\\temp");
    static File file44 = new File(path + "\\temp\\temp.txt");
    static File file5 = new File(path + "\\src\\main");
    static File file51 = new File(path + "\\src\\main\\Main.java");
    static File file55 = new File(path + "\\src\\main\\Utils.java");
    static File file6 = new File(path + "\\src\\test");
    static File file7 = new File(path + "s\\res\\vectors");
    static File file8 = new File(path + "\\res\\icons");
    static Scanner sc;

    static {
        try {
            sc = new Scanner(file44);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        GameProgress gmOne = new GameProgress(2,3,5,3.4);
        GameProgress gmTwo = new GameProgress(2,7,9,5.4);
        GameProgress gmTree = new GameProgress(7,1,5,8.2);

        List<String> gameProgressPath = new ArrayList<>();
        gameProgressPath.add(path+"\\savegames\\");
        gameProgressPath.add(path+"\\savegames\\");
        gameProgressPath.add(path+"\\savegames\\");


        if (file.mkdir() &&
                file1.mkdirs() &&
                file2.mkdirs() &&
                file3.mkdir() &&
                file4.mkdirs() &&
                file5.mkdirs() &&
                file6.mkdir() &&
                file7.mkdir() &&
                file8.mkdir()) {

            writeLog(sb.append(time + " Directories are create successfully! \n"), sc);
        } else if (file.exists() &&
                file1.exists() &&
                file2.exists() &&
                file3.exists() &&
                file4.exists() &&
                file5.exists() &&
                file6.exists() &&
                file7.exists() &&
                file8.exists()) {

            writeLog(sb.append(time + " Directories are already exist! \n"), sc);
        } else {

            writeLog(sb.append(time + " Error when try to create directories! \n"), sc);
        }

        try {
            if (file4.createNewFile() &&
                    file44.createNewFile() &&
                    file5.createNewFile() &&
                    file55.createNewFile() &&
                    file51.createNewFile()) {
                writeLog(sb.append(time + " Files are created successfully! \n"), sc);
            } else if (file4.exists() &&
                    file44.exists() &&
                    file5.exists() &&
                    file55.exists() &&
                    file51.exists()) {
                writeLog(sb.append(time + " Files are already exist!  \n"), sc);
            }
        } catch (IOException e) {
            writeLog(sb.append(time + " Error when try to create files!\n " + e), sc);
            e.printStackTrace();
        }

        saveGame(path+"\\savegames\\",gmOne);
        saveGame(path+"\\savegames\\",gmTwo);
        saveGame(path+"\\savegames\\",gmTree);

        zipFiles(path+"\\savegames\\",gameProgressPath);


    }

    public static void writeLog(StringBuilder sb, Scanner sc) throws IOException{
        try (FileWriter write = new FileWriter(path + "\\temp\\temp.txt")) {
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
                writeLog(sb.append(time + " Nothing to delete \n"), sc);

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
