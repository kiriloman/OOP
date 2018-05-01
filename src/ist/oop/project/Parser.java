package ist.oop.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class Parser {
    public int colms, rows, finalInst, initPop, maxPop, comfortSens, mu, delta, rho;
    public Point initialPoint, finalPoint;
    public List<List<Object>> specialCosts;
    public List<Point> obstacles;

    public void read(String filePath) throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        NodeList nodeList;
        int coordX, coordY;

        nodeList = document.getElementsByTagName("simulation");
        finalInst = Integer.valueOf(nodeList.item(0).getAttributes().getNamedItem("finalinst").getTextContent());
        initPop = Integer.valueOf(nodeList.item(0).getAttributes().getNamedItem("initpop").getTextContent());
        maxPop = Integer.valueOf(nodeList.item(0).getAttributes().getNamedItem("maxpop").getTextContent());
        comfortSens = Integer.valueOf(nodeList.item(0).getAttributes().getNamedItem("comfortsens").getTextContent());

        nodeList = document.getElementsByTagName("grid");
        colms = Integer.valueOf(nodeList.item(0).getAttributes().getNamedItem("colsnb").getTextContent());
        rows = Integer.valueOf(nodeList.item(0).getAttributes().getNamedItem("rowsnb").getTextContent());

        nodeList = document.getElementsByTagName("initialpoint");
        coordX = Integer.valueOf(nodeList.item(0).getAttributes().getNamedItem("xinitial").getTextContent());
        coordY = Integer.valueOf(nodeList.item(0).getAttributes().getNamedItem("yinitial").getTextContent());
        initialPoint = new Point(coordX, coordY);

        nodeList = document.getElementsByTagName("finalpoint");
        coordX = Integer.valueOf(nodeList.item(0).getAttributes().getNamedItem("xfinal").getTextContent());
        coordY = Integer.valueOf(nodeList.item(0).getAttributes().getNamedItem("yfinal").getTextContent());
        finalPoint = new Point(coordX, coordY);

        nodeList = document.getElementsByTagName("zone");
        specialCosts = new ArrayList<>();
        List<Object> list;
        for (int i = 0; i < nodeList.getLength(); i++) {
            list = new ArrayList<>();
            coordX = Integer.valueOf(nodeList.item(i).getAttributes().getNamedItem("xinitial").getTextContent());
            coordY = Integer.valueOf(nodeList.item(i).getAttributes().getNamedItem("yinitial").getTextContent());
            list.add(new Point(coordX, coordY));
            coordX = Integer.valueOf(nodeList.item(i).getAttributes().getNamedItem("xfinal").getTextContent());
            coordY = Integer.valueOf(nodeList.item(i).getAttributes().getNamedItem("yfinal").getTextContent());
            list.add(new Point(coordX, coordY));
            list.add(Integer.valueOf(nodeList.item(i).getTextContent()));
            specialCosts.add(list);
        }

        nodeList = document.getElementsByTagName("obstacle");
        obstacles = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            coordX = Integer.valueOf(nodeList.item(i).getAttributes().getNamedItem("xpos").getTextContent());
            coordY = Integer.valueOf(nodeList.item(i).getAttributes().getNamedItem("ypos").getTextContent());
            obstacles.add(new Point(coordX, coordY));
        }

        mu = Integer.valueOf(document.getElementsByTagName("death").item(0).getAttributes().getNamedItem("param").getTextContent());
        rho = Integer.valueOf(document.getElementsByTagName("reproduction").item(0).getAttributes().getNamedItem("param").getTextContent());
        delta = Integer.valueOf(document.getElementsByTagName("move").item(0).getAttributes().getNamedItem("param").getTextContent());
    }

    public void printObservation(int obsN, int instant, int events, int size, boolean hitFinalP, List<Point> path, int cost, int comfort) {
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
