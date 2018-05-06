package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.*;

import grid.Point;
import org.w3c.dom.*;
import org.xml.sax.SAXException;


// Classe Parser. Vai ler ficheiro XML e printar sempre que necessario cenas pedidas no projeto.
// Em principio ta acabado
public class Parser {
    private Document document;

    public Parser(String filePath) throws IOException, SAXException, ParserConfigurationException {
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

    public HashMap<List<Point>, Integer> readSpecialCosts() {
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
            //adiciona a "linha" de baixo do rectangulo
            for (int j = fromPoint.getX(); j < toPoint.getX(); j++) {
                edge = new ArrayList<>();
                edge.add(new Point(j, fromPoint.getY()));
                edge.add(new Point(j + 1, fromPoint.getY()));
                //se a aresta ja ta com extra custo, verifica-se se o novo custo e maior.
                //se for alteramos o custo
                if (specialZones.containsKey(edge)) {
                    if (specialZones.get(edge) < cost)
                        specialZones.put(edge, cost);
                } else {
                    specialZones.put(edge, cost);
                }
            }
            //adiciona a "linha" de cima do rectangulo
            for (int j = fromPoint.getX(); j < toPoint.getX(); j++) {
                edge = new ArrayList<>();
                edge.add(new Point(j, toPoint.getY()));
                edge.add(new Point(j + 1, toPoint.getY()));
                //se a aresta ja ta com extra custo, verifica-se se o novo custo e maior.
                //se for alteramos o custo
                if (specialZones.containsKey(edge)) {
                    if (specialZones.get(edge) < cost)
                        specialZones.put(edge, cost);
                } else {
                    specialZones.put(edge, cost);
                }
            }
            //adiciona a "linha" vertical esquerda
            for (int j = fromPoint.getY(); j < toPoint.getY(); j++) {
                edge = new ArrayList<>();
                edge.add(new Point(fromPoint.getX(), j));
                edge.add(new Point(fromPoint.getX(), j + 1));
                //se a aresta ja ta com extra custo, verifica-se se o novo custo e maior.
                //se for alteramos o custo
                if (specialZones.containsKey(edge)) {
                    if (specialZones.get(edge) < cost)
                        specialZones.put(edge, cost);
                } else {
                    specialZones.put(edge, cost);
                }
            }
            //adiciona a "linha" vertical direita
            for (int j = fromPoint.getY(); j < toPoint.getY(); j++) {
                edge = new ArrayList<>();
                edge.add(new Point(toPoint.getX(), j));
                edge.add(new Point(toPoint.getX(), j + 1));
                //se a aresta ja ta com extra custo, verifica-se se o novo custo e maior.
                //se for alteramos o custo
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
