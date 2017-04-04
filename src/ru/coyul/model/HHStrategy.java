package com.javarush.test.level28.lesson15.big01.model;

import com.javarush.test.level28.lesson15.big01.vo.Vacancy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HHStrategy implements Strategy
{
    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";

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
                elements = doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                if (elements.isEmpty()) break;

                for (Element element : elements)
                {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").get(0).text());
                    if (element.html().contains("vacancy-serp__vacancy-compensation"))
                    {
                        vacancy.setSalary(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").get(0).text());
                    } else vacancy.setSalary("");
                    vacancy.setCity(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").get(0).text());
                    vacancy.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").get(0).text());
                    vacancy.setSiteName("hh.ru");
                    vacancy.setUrl(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").attr("href"));

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
        Connection connection = Jsoup.connect(String.format(URL_FORMAT, searchString, page))
                .userAgent("Mozilla/5.0")
                .referrer("http://hh.ru");
        doc = connection.get();

        return doc;
    }
}
