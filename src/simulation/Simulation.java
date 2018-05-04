package simulation;

import grid.Point;
import population.Population;
import main.Parser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Classe Simulation. Pensei em por o main aqui. mas pode-se mudar.
//Vai simular obviamente.
public class Simulation {
    private static int mu, delta, rho, cols, rows, initialPop, maxPop, finalInst, comfortSens;
    private static Point initialPoint, finalPoint;
    private static List<List<Object>> specialCostZones;
    private static List<Point> obstacles;
    private static Parser parser;

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        parseFile("input");
        simulate();
    }

    // retira toda a informaçao necessario do ficheiro XML
    private static void parseFile(String filePath) throws ParserConfigurationException, SAXException, IOException {
        parser = new Parser(filePath);
        mu = parser.readMu();
        delta = parser.readDelta();
        rho = parser.readRho();
        cols = parser.readNumberOfColumns();
        rows = parser.readNumberOfRows();
        initialPop = parser.readInitialPopulation();
        maxPop = parser.readMaxPopulation();
        finalInst = parser.readFinalInstant();
        comfortSens = parser.readComfortSens();
        initialPoint = parser.readInitialPoint();
        finalPoint = parser.readFinalPoint();
        specialCostZones = parser.readSpecialCosts();
        obstacles = parser.readObstacles();
    }

    // simula
    private static void simulate() {
        Population population = new Population(initialPop);
        int observationNum = 1, numberOfEvents = 0, bestCost = 0;
        boolean finalPointHit = false;
        List<Point> bestPath = new ArrayList<>();
        double bestComfort = 0;

        for (int i = 1; i <= finalInst; i++) {
            // de finalInst/20 em finalInst/20 imprimir o estado atual
            if (i % (finalInst / 20) == 0) {
                parser.printObservation(observationNum, i, numberOfEvents, population.size, finalPointHit, bestPath, bestCost, bestComfort);
                observationNum++;
            }
            // Simular

        }
    }
}
