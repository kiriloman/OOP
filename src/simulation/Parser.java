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

public class Parser {
    private Document document;

    Parser(String filePath) throws IOException, SAXException, ParserConfigurationException {
        File xmlFile = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(xmlFile);
    }

    protected int readNumberOfRows() {
        return Integer.valueOf(document.getElementsByTagName("grid").item(0).getAttributes().getNamedItem("rowsnb").getTextContent());
    }

    protected int readNumberOfColumns() {
        return Integer.valueOf(document.getElementsByTagName("grid").item(0).getAttributes().getNamedItem("colsnb").getTextContent());
    }

    protected int readFinalInstant() {
        return Integer.valueOf(document.getElementsByTagName("simulation").item(0).getAttributes().getNamedItem("finalinst").getTextContent());
    }

    protected int readInitialPopulation() {
        return Integer.valueOf(document.getElementsByTagName("simulation").item(0).getAttributes().getNamedItem("initpop").getTextContent());
    }

    protected int readMaxPopulation() {
        return Integer.valueOf(document.getElementsByTagName("simulation").item(0).getAttributes().getNamedItem("maxpop").getTextContent());
    }

    protected int readComfortSens() {
        return Integer.valueOf(document.getElementsByTagName("simulation").item(0).getAttributes().getNamedItem("comfortsens").getTextContent());
    }

    protected int readMu() {
        return Integer.valueOf(document.getElementsByTagName("death").item(0).getAttributes().getNamedItem("param").getTextContent());
    }

    protected int readRho() {
        return Integer.valueOf(document.getElementsByTagName("reproduction").item(0).getAttributes().getNamedItem("param").getTextContent());
    }

    protected int readDelta() {
        return Integer.valueOf(document.getElementsByTagName("move").item(0).getAttributes().getNamedItem("param").getTextContent());
    }

    protected Point readInitialPoint() {
        int coordX = Integer.valueOf(document.getElementsByTagName("initialpoint").item(0).getAttributes().getNamedItem("xinitial").getTextContent());
        int coordY = Integer.valueOf(document.getElementsByTagName("initialpoint").item(0).getAttributes().getNamedItem("yinitial").getTextContent());
        return new Point(coordX, coordY);
    }

    protected Point readFinalPoint() {
        int coordX = Integer.valueOf(document.getElementsByTagName("finalpoint").item(0).getAttributes().getNamedItem("xfinal").getTextContent());
        int coordY = Integer.valueOf(document.getElementsByTagName("finalpoint").item(0).getAttributes().getNamedItem("yfinal").getTextContent());
        return new Point(coordX, coordY);
    }

    protected HashMap<List<Point>, Integer> readSpecialCosts() {
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

    protected List<Point> readObstacles() {
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

    protected void printResult(List<Point> path) {
        System.out.println("Path of the best fit individual = " + path.toString().replace("[", "{").replace("]", "}"));
    }
}
