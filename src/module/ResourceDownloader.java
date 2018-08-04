import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ResourceDownloader {
    public String workdDir;

    public ResourceDownloader(String wordDir) {
        this.workdDir = wordDir;    //contruct our class with given working dir - where to save the resources
    }

    public void parseUrlandCall(URL url) {
        String resourceFullPath = url.getPath();
        String dirPath = resourceFullPath.substring(0, resourceFullPath.lastIndexOf('/'));
        String resourceName = resourceFullPath.substring(resourceFullPath.lastIndexOf('/') + 1);
        String resourcetype = resourceFullPath.substring(resourceFullPath.lastIndexOf('/') + 1).split("\\.")[1];

        if (resourcetype.equals("html") || resourcetype.equals("htm") || resourcetype.equals("css") || resourcetype.equals("js")) {
            downloadTextFile(url, dirPath, resourceName);
        }
        else {
            downloadBinaryFile(url, dirPath, resourceName);
        }

    }


    public void downloadTextFile(URL urlString, String dirPath, String resourceName) {
        StringBuilder page = new StringBuilder();
        String line;
        try {
            URLConnection conn = urlString.openConnection();
            BufferedReader urlInput = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = urlInput.readLine()) != null) {
                page.append(line);
            }
            urlInput.close();
            dirPath = this.workdDir + dirPath;
            File writePath = new File(dirPath);

            if (!writePath.exists()) {
                writePath.mkdirs();
            }

            String resourcePath = dirPath + "/" + resourceName;
            FileOutputStream fileHandlle= new FileOutputStream(resourcePath);
            fileHandlle.write(page.toString().getBytes());
            fileHandlle.close();

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void downloadBinaryFile(URL urlString, String dirPath, String resourceName) {
        try {
            URLConnection conn = urlString.openConnection();
            DataInputStream urlInput = new DataInputStream(conn.getInputStream());
            ByteArrayOutputStream finalBuf = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];

            dirPath = this.workdDir + dirPath;
            File writePath = new File(dirPath);

            if (!writePath.exists()) {
                writePath.mkdirs();
            }

            String resourcePath = dirPath + "/" + resourceName;
            FileOutputStream fileHandlle = new FileOutputStream(resourcePath);
            int readBytes = 0;

            while ( (readBytes =  urlInput.read(buf)) != -1 ) {
                finalBuf.write(buf, 0, readBytes);
            }

            fileHandlle.write(finalBuf.toByteArray());
            fileHandlle.close();
            urlInput.close();
            finalBuf.close();

        }
        catch (Exception e)  {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<String> emailList = new ArrayList<>();
    public static void main(String[] args) {
        EmailScraper em = new EmailScraper(emailList);
        String url = "http://www.insa.gov.et";
        em.scrapeEmailFromPage(url);
        for (String email : emailList) {
            System.out.println(email);
        }
    }
}