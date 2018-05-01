package ist.oop.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class Parser {
    private Document document;

    Parser(String filePath) throws IOException, SAXException, ParserConfigurationException {
        File xmlFile = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(xmlFile);
    }

    public int readNumberOfRows() {
        return Integer.valueOf(document.getElementsByTagName("grid").item(0).getAttributes().getNamedItem("rowsnb").getTextContent());
    }

    public int readNumberOfColumns() {
        return Integer.valueOf(document.getElementsByTagName("grid").item(0).getAttributes().getNamedItem("colsnb").getTextContent());
    }

    public int readFinalInstant() {
        return Integer.valueOf(document.getElementsByTagName("simulation").item(0).getAttributes().getNamedItem("finalinst").getTextContent());
    }

    public int readInitialPopulation() {
        return Integer.valueOf(document.getElementsByTagName("simulation").item(0).getAttributes().getNamedItem("initpop").getTextContent());
    }

    public int readMaxPopulation() {
        return Integer.valueOf(document.getElementsByTagName("simulation").item(0).getAttributes().getNamedItem("maxpop").getTextContent());
    }

    public int readComfortSens() {
        return Integer.valueOf(document.getElementsByTagName("simulation").item(0).getAttributes().getNamedItem("comfortsens").getTextContent());
    }

    public int readMu() {
        return Integer.valueOf(document.getElementsByTagName("death").item(0).getAttributes().getNamedItem("param").getTextContent());
    }

    public int readRho() {
        return Integer.valueOf(document.getElementsByTagName("reproduction").item(0).getAttributes().getNamedItem("param").getTextContent());
    }

    public int readDelta() {
        return Integer.valueOf(document.getElementsByTagName("move").item(0).getAttributes().getNamedItem("param").getTextContent());
    }

    public Point readInitialPoint() {
        int coordX = Integer.valueOf(document.getElementsByTagName("initialpoint").item(0).getAttributes().getNamedItem("xinitial").getTextContent());
        int coordY = Integer.valueOf(document.getElementsByTagName("initialpoint").item(0).getAttributes().getNamedItem("yinitial").getTextContent());
        return new Point(coordX, coordY);
    }

    public Point readFinalPoint() {
        int coordX = Integer.valueOf(document.getElementsByTagName("finalpoint").item(0).getAttributes().getNamedItem("xfinal").getTextContent());
        int coordY = Integer.valueOf(document.getElementsByTagName("finalpoint").item(0).getAttributes().getNamedItem("yfinal").getTextContent());
        return new Point(coordX, coordY);
    }

    public List<List<Object>> readSpecialCosts() {
        List<List<Object>> specialZones = new ArrayList<>();
        List<Object> list;
        int coordX, coordY;
        NodeList nodeList = document.getElementsByTagName("zone");
        for (int i = 0; i < nodeList.getLength(); i++) {
            list = new ArrayList<>();
            coordX = Integer.valueOf(nodeList.item(i).getAttributes().getNamedItem("xinitial").getTextContent());
            coordY = Integer.valueOf(nodeList.item(i).getAttributes().getNamedItem("yinitial").getTextContent());
            list.add(new Point(coordX, coordY));
            coordX = Integer.valueOf(nodeList.item(i).getAttributes().getNamedItem("xfinal").getTextContent());
            coordY = Integer.valueOf(nodeList.item(i).getAttributes().getNamedItem("yfinal").getTextContent());
            list.add(new Point(coordX, coordY));
            list.add(Integer.valueOf(nodeList.item(i).getTextContent()));
            specialZones.add(list);
        }
        return specialZones;
    }

    public List<Point> readObstacles() {
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

    public void printObservation(int obsN, int instant, int events, int size, boolean hitFinalP, List<Point> path, int cost, double comfort) {
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "Observation " + obsN + ":", "", ""));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Present instant:", instant));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Number of realized events:", events));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Population size:", size));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Final point has been hit:", hitFinalP));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Path of the best fit individual:", path.toString().replace("[", "{").replace("]", "}")));
        System.out.print(String.format("%1$-15s%2$-35s%3$s\n", "", "Cost/Comfort:", (double) cost/comfort));
    }

    public void printResult(List<Point> path) {
        System.out.println("Path of the best fit individual = " + path.toString().replace("[", "{").replace("]", "}"));
    }
}
