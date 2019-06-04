/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.DomainCombiner;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Steven
 */
public class PositionXML {
     //variables
    private Document document;
    private Element root;
    private String path;

    public PositionXML(String path) throws IOException, JDOMException {
        //ruta en la que se encuentra el archivo XML
        this.path = path;

        File fileCharacter = new File(path);//esto es solo para hacer validacion
        if (fileCharacter.exists()) {
            //1. EL ARCHIVO YA EXISTE, ENTONCES LO CARGO EN MEMORIA

            //toma la estructura de datos y las carga en memoria
            SAXBuilder saxBuilder = new SAXBuilder();
            saxBuilder.setIgnoringElementContentWhitespace(true);

            //carga la memoria
            this.document = saxBuilder.build(path);
            this.root = this.document.getRootElement();
        } else {
            //2. EL ARCHIVO NO EXISTE, ENTONCES LO CREO Y LUEGO LO CARGO EN MEMORIA

            //CREAMOS EL ELEMENTO RAIZ
            this.root = new Element("Position");

            //CREAMOS EL DOCUMENTO
            this.document = new Document(this.root);

            //GUARDAMOS EN DISCO DURO
            storeXML();
        }
    }//end constructor

    //almacena en disco duro nuestro documento xml en la ruta especificada
    private void storeXML() throws IOException {
        XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.output(document, new PrintWriter(path));
    }

    //metodo para insertar un nuevo estudiante en el documento xml
    public void insertCharacter(Domain.Position Position) throws IOException {
        //INSERTAMOS EN EL DOCUMENTO EN MEMORIA
        //para insertar en xml, primero se crean los elementos

        //crear el estudiante
        Element ePosition = new Element("position");

        Element eX = new Element("x");
        eX.addContent(String.valueOf(Position.getX()));

                Element eY = new Element("y");
        eY.addContent(String.valueOf(Position.getY()));

        //agregar al elemento student el contenido de nombre y nota
        ePosition.addContent(eX);
        ePosition.addContent(eY);

        //AGREGAMOS AL ROOT
        this.root.addContent(ePosition);

        //FINALMENTE: GUARDAR EN DD
        storeXML();
    }//end insert

    //delete
    public void deleteStudent() throws IOException {
        List elementList = this.root.getChildren();
        elementList.remove(1);

        //FINALMENTE: GUARDAR EN DD
        storeXML();
    }

    //metodo para obtener todos los estudiantes en un arreglo
    public ArrayList<Domain.Position> getAllPosition() {
        //obtenemos la cantidad de estudiantes

        //obtenemos una lista con todos los elementos de root
        List elementList = this.root.getChildren();

        //definimos el tamanno del arreglo
        ArrayList<Domain.Position> charactersArray = new ArrayList<>();

        //recorremos la lista para ir creando los objetos de tipo estudiante
        for (Object currentObject : elementList) {
            //transformo el object
            Element currentElement = (Element) currentObject;

            Domain.Position currentCharacter = new Domain.Position();

          
            currentCharacter.setX(Integer.parseInt(currentElement.getChild("x").getValue()));

            currentCharacter.setY(Integer.parseInt(currentElement.getChild("y").getValue()));


            //guardar en el arreglo
            charactersArray.add(currentCharacter);
        }//end for
        return charactersArray;
    }
}
