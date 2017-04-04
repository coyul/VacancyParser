package com.javarush.test.level28.lesson15.big01.view;

import com.javarush.test.level28.lesson15.big01.Controller;
import com.javarush.test.level28.lesson15.big01.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View
{
    private Controller controller;
    private final String filePath = "./src/" + this.getClass().getPackage().getName().replace(".","/") + "/vacancies.html";
    @Override
    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    @Override
    public void update(List<Vacancy> vacancies)
    {
        try {
            String fileBody = getUpdatedFileContent(vacancies);
            updateFile(fileBody);
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Some exception occurred");
        }

    }

    public void userCitySelectEmulationMethod(){
        controller.onCitySelect("Moscow");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancyList) throws IOException{
        Document doc = getDocument();
        Element element = doc.getElementsByClass("template").get(0);
        Element cloneElement = element.clone();
        cloneElement.removeClass("template");
        cloneElement.removeAttr("style");

        Elements elements = doc.getElementsByTag("tr");
        for (Element e1 : elements)
        {
            if (e1.hasClass("vacancy")&&!e1.hasClass("template")) e1.remove();
        }

        for (Vacancy vacancy : vacancyList){
            Element e = cloneElement.clone();
            e.getElementsByClass("city").get(0).text(vacancy.getCity());
            e.getElementsByClass("companyName").get(0).text(vacancy.getCompanyName());
            e.getElementsByClass("salary").get(0).text(vacancy.getSalary());

            Element title = e.getElementsByTag("a").get(0);
            title.text(vacancy.getTitle());
            title.attr("href", vacancy.getUrl());

            element.before(e.outerHtml());
        }

        return doc.html();

    }

    private void updateFile(String string) throws IOException{
        FileWriter writer = new FileWriter(filePath);
        writer.write(string);
        writer.flush();
        writer.close();
    }

    protected Document getDocument() throws IOException{
        return Jsoup.parse(new File(filePath), "UTF-8");
    }


}
