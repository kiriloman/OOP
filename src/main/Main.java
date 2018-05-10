package main;

import org.xml.sax.SAXException;
import simulation.Simulation;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Main class to run the simulation.
 */

public class Main {
    /**
     * Receives an xml.file as input, creates a new Simulation
     * over that file and simulates.
     * @param args Input arguments
     * @throws ParserConfigurationException Parser exception
     * @throws SAXException Parser exception
     * @throws IOException IO exception
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        if (args.length != 1) {
            System.out.println("Usage: Main <file.xml>");
            System.exit(1);
        }
        new Simulation(args[0]).simulate();
    }
}