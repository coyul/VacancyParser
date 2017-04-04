package com.javarush.test.level28.lesson15.big01;


import com.javarush.test.level28.lesson15.big01.model.HHStrategy;
import com.javarush.test.level28.lesson15.big01.model.Model;
import com.javarush.test.level28.lesson15.big01.model.MoikrugStrategy;
import com.javarush.test.level28.lesson15.big01.model.Provider;
import com.javarush.test.level28.lesson15.big01.view.HtmlView;



public class Aggregator
{
    public static void main(String[] args)
    {

        HtmlView view = new HtmlView();
        Provider providersHH = new Provider(new HHStrategy());
        Provider providersMK = new Provider(new MoikrugStrategy());
        Model model = new Model(view, providersHH, providersMK);
        Controller controller = new Controller(model);
        view.setController(controller);
        view.userCitySelectEmulationMethod();

    }
}
