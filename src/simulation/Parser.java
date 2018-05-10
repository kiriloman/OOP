package simulation;

import grid.Point;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a Parser.
 */
public class Parser {
    /**
     * Document to parse from.
     */
    private Document document;

    /**
     * Parser constructor.
     * @param filePath Path to file to parse
     * @throws IOException IO exception
     * @throws SAXException Parser exception
     * @throws ParserConfigurationException Parser exception
     */
    Parser(String filePath) throws IOException, SAXException, ParserConfigurationException {
        File xmlFile = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(xmlFile);
    }

    /**
     * Reads number of rows of a grid from the file.
     * @return Number of rows
     */
    int readNumberOfRows() {
        return Integer.valueOf(document.getElementsByTagName("grid").item(0).getAttributes().getNamedItem("rowsnb").getTextContent());
    }

    /**
     * Reads number of columns of a grid from the file.
     * @return Number of columns
     */
    int readNumberOfColumns() {
        return Integer.valueOf(document.getElementsByTagName("grid").item(0).getAttributes().getNamedItem("colsnb").getTextContent());
    }

    /**
     * Reads final instant from the file.
     * @return Final instant
     */
    int readFinalInstant() {
        return Integer.valueOf(document.getElementsByTagName("simulation").item(0).getAttributes().getNamedItem("finalinst").getTextContent());
    }

    /**
     * Reads size of initial population.
     * @return Size of initial population
     */
    int readInitialPopulation() {
        return Integer.valueOf(document.getElementsByTagName("simulation").item(0).getAttributes().getNamedItem("initpop").getTextContent());
    }

    /**
     * Reads maximum size of a population.
     * @return Maximum size of a population
     */
    int readMaxPopulation() {
        return Integer.valueOf(document.getElementsByTagName("simulation").item(0).getAttributes().getNamedItem("maxpop").getTextContent());
    }

    /**
     * Reads comfort sensitivity.
     * @return Comfort sensitivity
     */
    int readComfortSens() {
        return Integer.valueOf(document.getElementsByTagName("simulation").item(0).getAttributes().getNamedItem("comfortsens").getTextContent());
    }

    /**
     * Reads mu value.
     * @return Mu
     */
    int readMu() {
        return Integer.valueOf(document.getElementsByTagName("death").item(0).getAttributes().getNamedItem("param").getTextContent());
    }

    /**
     * Reads rho value.
     * @return Rho
     */
    int readRho() {
        return Integer.valueOf(document.getElementsByTagName("reproduction").item(0).getAttributes().getNamedItem("param").getTextContent());
    }

    /**
     * Reads delta value.
     * @return Delta
     */
    int readDelta() {
        return Integer.valueOf(document.getElementsByTagName("move").item(0).getAttributes().getNamedItem("param").getTextContent());
    }

    /**
     * Reads initial point.
     * @return Initial point
     */
    Point readInitialPoint() {
        int coordX = Integer.valueOf(document.getElementsByTagName("initialpoint").item(0).getAttributes().getNamedItem("xinitial").getTextContent());
        int coordY = Integer.valueOf(document.getElementsByTagName("initialpoint").item(0).getAttributes().getNamedItem("yinitial").getTextContent());
        return new Point(coordX, coordY);
    }

    /**
     * Reads final point.
     * @return Final point
     */
    Point readFinalPoint() {
        int coordX = Integer.valueOf(document.getElementsByTagName("finalpoint").item(0).getAttributes().getNamedItem("xfinal").getTextContent());
        int coordY = Integer.valueOf(document.getElementsByTagName("finalpoint").item(0).getAttributes().getNamedItem("yfinal").getTextContent());
        return new Point(coordX, coordY);
    }

    /**
     * Reads zones with special costs.
     * @return Zones with special costs and its costs
     */
    HashMap<List<Point>, Integer> readSpecialCosts() {
        HashMap<List<Point>, Integer> specialZones = new HashMap<>();
        List<Point> edge;
        int coordX, coordY, cost;
        Point fromPoint, toPoint;
        NodeList nodeList = document.getElementsByTagName("zone");
        for (int i = 0; i < nodeList.getLength(); i++) {
            coordX = Integer.valueOf(nodeList.item(i).getAttributes().getNamedItem("xinitial").getTextContent());
            coordY = Integer.valueOf(nodeList.item(i).getAttributes().getNamedItem("yinitial").getTextContent());
            fromPoint = new Point(coordX, coordY);
            coordX = Integer.valueOf(nodeList.item(i).getAttributes().getNamedItem("xfinal").getTextContent());
            coordY = Integer.valueOf(nodeList.item(i).getAttributes().getNamedItem("yfinal").getTextContent());
            toPoint = new Point(coordX, coordY);
            cost = Integer.valueOf(nodeList.item(i).getTextContent());
            // Adds bottom line of rectangle
            for (int j = fromPoint.getX(); j < toPoint.getX(); j++) {
                edge = new ArrayList<>();
                edge.add(new Point(j, fromPoint.getY()));
                edge.add(new Point(j + 1, fromPoint.getY()));
                // If an edge already has extra cost, checks if new cost is higher
                // If so, set new cost as edge's cost
                if (specialZones.containsKey(edge)) {
                    if (specialZones.get(edge) < cost)
                        specialZones.put(edge, cost);
                } else {
                    specialZones.put(edge, cost);
                }
            }
            // Adds top line of rectangle
            for (int j = fromPoint.getX(); j < toPoint.getX(); j++) {
                edge = new ArrayList<>();
                edge.add(new Point(j, toPoint.getY()));
                edge.add(new Point(j + 1, toPoint.getY()));
                // If an edge already has extra cost, checks if new cost is higher
                // If so, set new cost as edge's cost
                if (specialZones.containsKey(edge)) {
                    if (specialZones.get(edge) < cost)
                        specialZones.put(edge, cost);
                } else {
                    specialZones.put(edge, cost);
                }
            }
            // Adds left line of rectangle
            for (int j = fromPoint.getY(); j < toPoint.getY(); j++) {
                edge = new ArrayList<>();
                edge.add(new Point(fromPoint.getX(), j));
                edge.add(new Point(fromPoint.getX(), j + 1));
                // If an edge already has extra cost, checks if new cost is higher
                // If so, set new cost as edge's cost
                if (specialZones.containsKey(edge)) {
                    if (specialZones.get(edge) < cost)
                        specialZones.put(edge, cost);
                } else {
                    specialZones.put(edge, cost);
                }
            }
            // Adds right line of rectangle
            for (int j = fromPoint.getY(); j < toPoint.getY(); j++) {
                edge = new ArrayList<>();
                edge.add(new Point(toPoint.getX(), j));
                edge.add(new Point(toPoint.getX(), j + 1));
                // If an edge already has extra cost, checks if new cost is higher
                // If so, set new cost as edge's cost
                if (specialZones.containsKey(edge)) {
                    if (specialZones.get(edge) < cost)
                        specialZones.put(edge, cost);
                } else {
                    specialZones.put(edge, cost);
                }
            }
        }
        return specialZones;
    }

    /**
     * Reads points that are obstacles.
     * @return List of obstacles
     */
    List<Point> readObstacles() {
        List<Point> obstacles = new ArrayList<>();
        NodeList nodeList = document.getElementsByTagName("obstacle");
        int coordX, coordY;
        for (int i = 0; i < nodeList.getLength(); i++) {
            coordX = Integer.valueOf(nodeList.item(i).getAttributes().getNamedItem("xpos").getTextContent());
            coordY = Integer.valueOf(nodeList.item(i).getAttributes().getNamedItem("ypos").getTextContent());
            obstacles.add(new Point(coordX, coordY));
        }
        return obstacles;
    }

    /**
     * Prints best fit individual's path.
     * @param path Path to print
     */
    void printResult(List<Point> path) {
        System.out.println("Path of the best fit individual = " + path.toString().replace("[", "{").replace("]", "}"));
    }
}
