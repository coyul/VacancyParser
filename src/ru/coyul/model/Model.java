package com.javarush.test.level28.lesson15.big01.model;

import com.javarush.test.level28.lesson15.big01.view.View;
import com.javarush.test.level28.lesson15.big01.vo.Vacancy;

import java.util.ArrayList;

public class Model
{
    private View view;
    private Provider [] provider;

    public Model(View view, Provider...provider)
    {
        if (view==null||provider==null||provider.length == 0) throw new IllegalArgumentException();
        this.view = view;
        this.provider = provider;
    }

    public void selectCity(String city){
        ArrayList <Vacancy> list = new ArrayList<>();
        for (Provider provider : this.provider){
            list.addAll(provider.getJavaVacancies(city));
        }
        view.update(list);
    }
}
