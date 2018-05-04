package main;

import grid.Map;
import grid.Point;
import main.Parser;
import org.xml.sax.SAXException;
import pec.*;
import population.Individual;
import population.Population;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// CLASSE ALEATORIA DE TESTES, pode-se ignorar
public class Main {
    public static void main(String[] args) {
        Population population = new Population(20);
        PEC pec = new PEC();
        Event event1 = new Death(10);
        Event event2 = new EventPrint(11);
        Event event3 = new Reproduction(7);
        Event event4 = new Move(10);
        Event event5 = new Move(5);
        Event event6 = new EventPrint(5);
        Event event7 = new Reproduction(2);
        Event event8 = new Move(13);
        ((EventInd) event1).setHost(new Individual(1));
        ((EventInd) event3).setHost(new Individual(2));
        ((EventInd) event4).setHost(new Individual(3));
        ((EventInd) event5).setHost(new Individual(1));
        ((EventInd) event7).setHost(new Individual(2));
        ((EventInd) event8).setHost(new Individual(1));
        pec.addEvent(event1);
        pec.addEvent(event2);
        pec.addEvent(event3);
        pec.addEvent(event4);
        pec.addEvent(event5);
        pec.addEvent(event6);
        pec.addEvent(event7);
        pec.addEvent(event8);
        System.out.println(pec.eventQueue.toString());
        pec.getEvent().execute();
        pec.getEvent().execute();
        pec.getEvent().execute();
    }
}
