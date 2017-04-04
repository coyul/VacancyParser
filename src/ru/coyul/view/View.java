package com.javarush.test.level28.lesson15.big01.view;

import com.javarush.test.level28.lesson15.big01.Controller;
import com.javarush.test.level28.lesson15.big01.vo.Vacancy;

import java.util.List;

/**
 * Created by Юлия on 16.02.2017.
 */
public interface View
{
    void update(List<Vacancy> vacancies);
    void setController(Controller controller);
}
