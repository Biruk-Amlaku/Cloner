package ui;

import javafx.scene.control.TreeItem;

public class Clone extends TreeItem<String> {
    public String url;
    public String cloneDate;
    public String cloneDepth;
    public String numberOfClonedHTML;
    public String numberOfClonedCSS;
    public String numberOfClonedJS;
    public String numberOfClonedImages;

    public Clone(String directory) {
        super(directory);
        this.url = directory;
        cloneDate = "0";
        cloneDepth = "0";
        numberOfClonedHTML = "0";
        numberOfClonedCSS = "0";
        numberOfClonedJS = "0";
        numberOfClonedImages = "0";
    }
}
