import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailScraper {
    /* This class has one public method "scrapeEmailFromPage" which accepts a url string, scrapes the emails in it
     * according to a pattern and adds them to an array list if they are not already added.
     *
     */
    /* It is implemented in a way it will be given an arrayList (reference) from the above logic who calls it when it is
     * instantiated, and adds the harvested emails to the list - and the list will be used by the caller logic for other purposes (Eg. display
     * in GUI)
     */

    private Pattern emailPattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,10}\\b"); //we can add more domain filters
    private ArrayList<String> emailsScraped = new ArrayList<>();  // this contains the scraped emails

    public EmailScraper(ArrayList<String> emailsScraped) {  //accepts the array list in its constructor
        this.emailsScraped = emailsScraped;
    }

    private String retrievePage(String urlString) {
        // just a private method for the class.
        // opens a page, reads it and returns it as a single string to the public method.

        StringBuilder page = new StringBuilder();
        String line;
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader urlInput = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = urlInput.readLine()) != null) {
                page.append(line);
            }
            urlInput.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return page.toString();
    }

    public void scrapeEmailFromPage(String url) {
        String wholePage = retrievePage(url);
        Matcher emailMatcher = emailPattern.matcher(wholePage); // create a matcher object here using the whole page
        String emailFound;

        while (emailMatcher.find()) { //for each occurrence of email found, check if it exists in the harvested list and add it if not
            emailFound = emailMatcher.group();
            if (!emailsScraped.contains(emailFound)) {
                emailsScraped.add(emailFound);
            }
        }
    }
}
