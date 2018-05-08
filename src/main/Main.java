package main;

import org.xml.sax.SAXException;
import simulation.Simulation;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        if (args.length != 1) {
            System.out.println("Usage: Main <file.xml>");
            System.exit(1);
        }
        new Simulation(args[0]).simulate();
        //new Simulation("src/data1.xml").simulate();
    }
}
