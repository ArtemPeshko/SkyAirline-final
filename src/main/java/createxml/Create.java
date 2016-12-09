package createxml;

import airpark.AviaPort;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static controllers.Controller.bortIndex;

public class Create {

    public static void JDOMtest(List<AviaPort> airr, String fileName) throws IOException {
        Document doc = new Document();
        doc.setRootElement(new Element("AirPlaner"));
        for (AviaPort airpla : airr) {

            Element airplnElement = (new Element(airpla.getName() + "_" + airpla.getNumber()));
            airplnElement.addContent(new Element("speed").setText("" + airpla.getSpeed()));
            airplnElement.addContent(new Element("weight").setText(" Weight =" + airpla.getWeight() + " Range =" + airpla.getRange()));
            airplnElement.addContent(new Element("fuel").setText("" + airpla.getFuel()));
            airplnElement.addContent(new Element("tank").setText("" + airpla.getTank()));
            airplnElement.addContent(new Element("passenger").setText("" + airpla.getPassenger()));
            airplnElement.addContent(new Element("passenger_seat").setText("" + airpla.getPassengerSeat()));
            airplnElement.addContent(new Element("range").setText("" + airpla.getRange()));
            doc.getRootElement().addContent(airplnElement);
        }
        XMLOutputter xmlWriter = new XMLOutputter(Format.getPrettyFormat());
        xmlWriter.output(doc, new FileOutputStream(fileName));

    }

    public static void JDOMtest2(HashMap<Integer, OutXML> currentjet, String fileName) throws IOException {
        Document doc = new Document();
        doc.setRootElement(new Element("AirPlaner"));
        Element airplnElement = new Element(currentjet.get(bortIndex).name);

        airplnElement.addContent(new Element("number").setText("" + currentjet.get(bortIndex).number));
        airplnElement.addContent(new Element("speed").setText("" + currentjet.get(bortIndex).speed));
        airplnElement.addContent(new Element("range").setText("" + currentjet.get(bortIndex).range));
        airplnElement.addContent(new Element("weight").setText("" + currentjet.get(bortIndex).weight));
        airplnElement.addContent(new Element("fuel").setText("" + currentjet.get(bortIndex).fuel));
        airplnElement.addContent(new Element("tank").setText("" + currentjet.get(bortIndex).tank));
        airplnElement.addContent(new Element("passenger").setText("" + currentjet.get(bortIndex).passenger));
        airplnElement.addContent(new Element("passenger_seat").setText("" + currentjet.get(bortIndex).passenger_seat));
        airplnElement.addContent(new Element("FlyCost").setText("" + currentjet.get(bortIndex).flyCost));

        doc.getRootElement().addContent(airplnElement);

        XMLOutputter xmlWriter = new XMLOutputter(Format.getPrettyFormat());
        xmlWriter.output(doc, new FileOutputStream(fileName));
    }

}