package main;

import grid.Map;
import grid.Point;
import main.Parser;
import org.xml.sax.SAXException;
import pec.*;
import population.Individual;
import population.Population;
import simulation.Simulation;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

// CLASSE ALEATORIA DE TESTES, pode-se ignorar
public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        new Simulation().simulate();
    }
}
