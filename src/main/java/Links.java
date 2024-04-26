import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.RecursiveTask;

public class Links extends RecursiveTask<String> {
    private String url;//адрес
    private String level;
    private CopyOnWriteArraySet<String> allLinks;//список всех ссылок

    public Links(String url, CopyOnWriteArraySet<String> allLinks) {//передаем url
        this.url = url;
        this.allLinks = allLinks;
    }

    public Links(String url) {//инициализируем новый список
        this.url = url;
        this.allLinks = new CopyOnWriteArraySet<>();
    }

    @Override
    protected String compute() {
        //получаем уровень отступа
        String tabulate = StringUtils.repeat("\t",
                url.lastIndexOf("/") != url.length() - 1 ? StringUtils.countMatches(url, "/") - 2
                        : StringUtils.countMatches(url, "/") - 3);

        StringBuilder stringBuilder = new StringBuilder(tabulate + url + "\n");

        Set<Links> allTask = new TreeSet<>(Comparator.comparing(o -> o.url));

        try {
            Thread.sleep(150);//чтобы не заблокировали
            Document document = Jsoup.connect(url).ignoreContentType(true).maxBodySize(0).get();
            Elements elements = document.select("a[href]");

            for (Element element : elements) {
                String attributeUrl = element.absUrl("href");//все ссылки сайта

                if (attributeUrl.startsWith(url)
                        && !attributeUrl.contains("#")
                        && !allLinks.contains(attributeUrl)) {

                    Links links = new Links(attributeUrl, allLinks);
                    links.fork();
                    allTask.add(links);
                    allLinks.add(attributeUrl);
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        for (Links link : allTask) {
            stringBuilder.append(link.join());
        }
        return stringBuilder.toString();
    }
}
