import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class FileTest {
    public static Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName()
                            .toString());
                    System.out.println(path.getFileName().toString());
                }
            }
        }
        return fileList;
    }

    public boolean existsFile(String path){
        File tempFile = new File(path);
        boolean exists = tempFile.exists();
        return exists;
    }

    public boolean hasGoodExtension(String filename, String extension){
        Integer i = filename.lastIndexOf('.');
        String extensionFileName = filename.substring(i);
        if( extensionFileName.compareTo(extension) == 0){
            return true;
        }
        else return false;
    }

    public static void main(String[] args) throws IOException {
        Set<String> test = listFilesUsingDirectoryStream("C:\\Users\\sophi\\Downloads\\VLS_TBOX_(Sophie) (1)\\VLS_TBOX_(Sophie)\\TBox01");
        //String file1 = test.get(0);
    }

}
