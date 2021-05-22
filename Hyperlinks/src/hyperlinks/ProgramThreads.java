/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperlinks;

import static hyperlinks.Menu.isValid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author owner
 */
public class ProgramThreads implements Runnable {

    private ArrayList<String> links;
    private int depth;
    private long elapsedTime;
    int linksSum;

    public ProgramThreads(ArrayList<String> links, int depth) {
        this.links = links;
        this.depth = depth;
    }

    @Override
    public void run() {

        getLinksWithDepth(links, depth);
        System.out.println("Successed links = " + getLinksSum() + " links");

        System.out.println("THREAD time = " + getElapsedTime() + " milli-seconds");
    }

    public void getLinksWithDepth(ArrayList<String> valideLinks, int depth) {
        long start = System.currentTimeMillis();

        Document doc2 = null;
        Elements linksInDepth = null;
        ArrayList<String> valideLinksInDepth = new ArrayList<String>();

        for (int i = 0; i < valideLinks.size(); i++) {
            try {
                String link = valideLinks.get(i);
                doc2 = (Document) Jsoup.connect(link).get();
                linksInDepth = doc2.select("a[href]");
                for (Iterator<Element> it = linksInDepth.iterator(); it.hasNext();) {
                    Element linkInDepth = it.next();
                    if (isValid(linkInDepth.attr("abs:href"))) {
                        System.out.println(linkInDepth.attr("abs:href"));
                        valideLinksInDepth.add(linkInDepth.attr("abs:href"));
                    }

                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        int n = depth;
        n -= 1;

        if (n >= 1) {
            getLinksWithDepth(valideLinksInDepth, n);
        }

        long end = System.currentTimeMillis();
        elapsedTime = end - start;
        linksSum += valideLinksInDepth.size();
        System.out.println("Thread is FINISHED");

    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public int getLinksSum() {
        return linksSum;
    }

}
