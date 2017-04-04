package com.javarush.test.level28.lesson15.big01.model;

import com.javarush.test.level28.lesson15.big01.vo.Vacancy;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoikrugStrategy implements Strategy
{
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?page=%d&q=java+%s";

    @Override
    public List<Vacancy> getVacancies(String searchString)
    {
        Document doc;
        List<Vacancy> vacancyList = new ArrayList<>();
        Elements elements;
        int i = 0;
        try
        {
            while (true)
            {
                doc = getDocument(searchString, i++);
                if (doc == null) break;
                elements = doc.getElementsByClass("job");
                if (elements.size() ==0 || elements.isEmpty()) break;

                for (Element element : elements)
                {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(element.getElementsByClass("title").get(0).text());
                    if (element.html().contains("location"))
                        vacancy.setCity(element.getElementsByClass("location").get(0).text());
                    else vacancy.setCity("");
                    vacancy.setCompanyName(element.getElementsByClass("company_name").get(0).getElementsByTag("a").get(0).text());
                    vacancy.setSiteName("moikrug.ru");
                    if (element.html().contains("salary"))
                        vacancy.setSalary(element.getElementsByClass("salary").get(0).text());
                    else vacancy.setSalary("");
                    vacancy.setUrl("https://moikrug.ru" + element.getElementsByClass("title").get(0).getElementsByTag("a").attr("href"));

                    vacancyList.add(vacancy);

                }
            }

        }
        catch (IOException e)
        {
        }
        return vacancyList;
    }

    protected Document getDocument(String searchString, int page) throws IOException
    {
        Document doc;
        Connection connection = Jsoup.connect(String.format(URL_FORMAT, page, searchString))
                .userAgent("Mozilla/5.0")
                .referrer("http://moikrug.ru");
        doc = connection.get();

        return doc;
    }
}
