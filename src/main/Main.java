package main;

import grid.Map;
import grid.Point;
import main.Parser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// CLASSE ALEATORIA DE TESTES, pode-se ignorar
public class Main {
    public static void main(String[] args) {
        Map M = new Map(5,4);
        Point p1= new Point(1,1);
        Point p2= new Point(1,1);
        System.out.println(p1.equals(p2));


        M.createGrid();

        System.out.println(M.map.containsKey(p1));
        System.out.println(Arrays.toString(M.map.keySet().toArray()));
    }
}
