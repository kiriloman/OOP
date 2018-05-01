package ist.oop.project;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Reader reader = new Reader();
            reader.read("input");
            List<Point> points = new ArrayList<>();
            points.add(new Point(2, 3));
            points.add(new Point(6, 3));
            points.add(new Point(1, 3));
            points.add(new Point(8, 3));
            reader.printObservation(1, 2, 3, 4, true, points, 1, 2);
            reader.printResult(points);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }
}
