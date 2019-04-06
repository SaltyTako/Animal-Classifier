/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalclassifier;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.apache.poi.*;//Aqui se importa POI, la libreria para excel, HU HA!
import static org.apache.poi.hssf.record.cf.BorderFormatting.BORDER_THIN;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderExtent;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.util.PropertyTemplate;
/**
 *
 * @author RAFAEL RAMIREZ
 */
public class Ventana {
    //Empezamos con el layout
    //TODO List:
    /*
    1)Dejar listo el layout
    2)leer los datos
    3)guardarlos
    4)ordenarlos
    5)imprimirlos
    6)encontrar una forma de añadir nuevas razas
    7)profit $$$
    */
    JFrame ventana = new JFrame("Clasificador de cabezas de ganado");
    JTextField Tatuaje, Nombre, Edad, Expositor, municipio_y_estado,nueva_raza,title;
    JLabel Tattoo, Name, Age, Expositioner, mun_y_estado,info,titulo;
    JButton Ingresar, Imprimir, Borrar, Ordenar, Agregar, Eliminar, Agregar_Titulo;
    JComboBox razas;
    String nombrerazas[]={
        /*Aqui iran los nombres de las razas*/
        "Doble Proposito Hembras","Machos Limonero Mestizos",
        "Hembras Limonero Mestizas","Machos Simbra Mestizos",
        "Hembras Simbra Mestizos","Hembras Ayshire Mestizos",
        "Machos Ayshire Mestizos","Machos Guzera Mestizos",
        "Hembras Guzera Mestizos","Machos Simental Mestizos",
        "Hembras Simental Mestizos","Machos Gyr Mestizos",
        "Hembras Gyr Mestizos","Machos Girholando Mestizos",
        "Hembras Girholando Mestizos","Machos Jersey Mestizos",
        "Hembras Jersey Mestizos","Machos Pardo Suizo Mestizos",
        "Hembras Pardo Suizo Mestizos","Machos Holstein Mestizos",
        "Hembras Holstein Mestizos","Machos Carora Mestizos",
        "Hembras Carora Mestizos","Machos F1 Holstein x Brahman",
        "Hembras F1 Holstein x Brahman","Machos F1 Holstein x Gyr",
        "Hembras F1 Holstein x Gyr","Machos Simbra Puros",
        "Hembras Simbra Puros","Machos Gyr Puros sin registro",
        "Hembras Gyr Puras sin registro","Machos Limonero Puro",
        "Hembras Limonero Puro","Machos Jersey Puro",
        "Hembras Jersey Puro","Machos Pardo Suizo Puro",
        "Hembras Pardo Suizo Puro","Macho Holstein Puro",
        "Hembras Holstein Puro","Machos Carora Puro",
        "Hembras Carora Puro", "Prole de Gyr",
        "Prole de Simbra"
    };
    int po;
    ArrayList<String> Razas = new ArrayList<>();
    
    int edades[] = {
        6,9,12,15,18,21,24,27,31,36,42,48,54,60,72 //Meses
    };
    Ganado aux[] = new Ganado[2000];
    Ganado[] principalganado = new Ganado[2000];//En este punto, le estoy dando un maximo de memoria
    //Probablemente cree 2, uno para los no ordenados, y otro para los ordenados
    //.t aber
    int contador;
    
    //Aqui añadire los escritores y lectores
    String nomarchivo = "Ganado.txt";
    String linea;
    //Lectores
    
    //Escritores
    FileReader Fr;
    BufferedReader entrada_archivo;
    
    StringTokenizer tokenboy;
    //Asumo que tambien deberia hacer dos array de strings
    /*
    O mejor, deberia hacer un array de strings con los datos, y despues un archivo con los datos ya ordenados
    */
    //Strings ayudantes
    String Tat;
    String nom;
    String ed;
    String exp;
    String est;
    String razindex;
    String raz;
    int ayudaindex;
    int ayudaedad;
    String Titulo_doc;
    
    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> animalist2 = new JList<>(model);
    /*
    Esto funciona de la siguiente manera
    si añado algo a model, se añade al JList
    */
    public Ventana() throws IOException
    {   
        /*
        Obtener los datos de un string al presionar enter O un boton
        guardarlos en un array
        pasarlo a un metodo de una clase, llamada "Orden" que los clasifique
        Por ultimo, imprimir con el tutorial
        ¿Profit? $$$
        0)Incluir un ComboBox con todas las razas
        1)Hacer el jtextfield que solo reaccione con el botón
        2)Hacer una lista con todas las razas
        */
        //Aqui van los JLabel que identificaran cada espacio
        
        File savedAnimals = new File("Ganado.txt");
        boolean exists = savedAnimals.exists();
        //De momento, esto demuestra que el metodo funciona
        /*
        Ahora solo falta añadir la lógica detras de ello
        */
        contador = 0;//el contador pasará a estar aqui, en caso de que se deban añadir mas, no exista algún conflicto
        if(exists == true)
        {   
            Fr = new FileReader(nomarchivo);
            entrada_archivo = new BufferedReader(Fr);
            
            linea = entrada_archivo.readLine();
            while(linea!=null)
            {   
                model.addElement(linea);
                tokenboy = new StringTokenizer(linea);
                Tat = tokenboy.nextToken("-");
                nom = tokenboy.nextToken("-");
                ed = tokenboy.nextToken("-");
                ayudaedad= Integer.parseInt(ed);
                exp = tokenboy.nextToken("-");
                est = tokenboy.nextToken("-");
                razindex = tokenboy.nextToken("-");
                ayudaindex= Integer.parseInt(razindex);
                raz = tokenboy.nextToken("-");
                //Aqui empezamos a guardar los datos con el tokenizer
                principalganado[contador] = new Ganado(Tat,nom,ayudaedad,exp,est,ayudaindex,raz);
                contador++;
                linea = entrada_archivo.readLine();
            }
            //En este punto, se debe verificar que el archivo contenga algo dentro de el
            //Ademas, seria bueno imprimir la cantidad de animales que hay
            System.out.println("Existe");
        }
            //¿y que pasa si el archivo existe? entonces esto jamás se inicializará? ojito
            
            //En este punto, como el archivo no existe, se debe crear uno prro
        
        
        for(po=0;po<nombrerazas.length;po++)
        {
            Razas.add(nombrerazas[po]);
        }
        titulo = new JLabel("Titulo: "); 
        titulo.setBounds(222, 20, 100, 20);
        Tattoo = new JLabel("Tatuaje: ");
        Tattoo.setBounds(222, 50, 100, 20);
        Name = new JLabel("Nombre: ");
        Name.setBounds(222, 70, 100, 20);
        Age = new JLabel("Edad: ");
        Age.setBounds(222, 90, 100, 20);
        Expositioner = new JLabel("Expositor/Agrop.: ");
        Expositioner.setBounds(222, 110, 100, 20);
        mun_y_estado = new JLabel("Municipio/Estado: ");
        mun_y_estado.setBounds(222, 130, 120, 20);
        info = new JLabel("(Expresar en meses)");
        info.setBounds(482,90,120,20);
        //Arriba estan los JLabel que identifican cada espacio
        Ingresar = new JButton("Guardar datos");
        Ingresar.setBounds(222, 170, 150, 30);
        
        
        
        Borrar = new JButton("Borrar");
        Borrar.setBounds(222, 200 ,150, 30);
        
        Ordenar = new JButton("Generar");
        Ordenar.setBounds(373, 200, 150, 30);
        
        Agregar = new JButton("Agregar");
        Agregar.setBounds(20, 200, 150, 30);
        
        Eliminar = new JButton("Eliminar");
        Eliminar.setBounds(480, 250, 150, 30);
        
        Agregar_Titulo = new JButton("Agregar Titulo");
        Agregar_Titulo.setBounds(490, 15, 180, 30);
        //Aqui van los JTextField
        title = new JTextField();
        title.setBounds(332, 20, 150, 20);
        
        Tatuaje = new JTextField();
        Tatuaje.setBounds(332, 50, 150, 20);
        Nombre = new JTextField();
        Nombre.setBounds(332, 70, 150, 20);
        Edad = new JTextField();
        Edad.setBounds(332, 90, 150, 20);
        Expositor = new JTextField();
        Expositor.setBounds(332, 110, 150, 20);
        municipio_y_estado = new JTextField();
        municipio_y_estado.setBounds(332, 130, 150, 20);
        nueva_raza = new JTextField();
        nueva_raza.setBounds(20, 170, 150, 20);
        //Arriba estan los JTextField
        ButtonHndlr x = new ButtonHndlr();
        //Aqui van los botones
        
        Ingresar.addMouseListener(x);
        Borrar.addMouseListener(x);
        Ordenar.addMouseListener(x);
        Agregar.addMouseListener(x);
        Eliminar.addMouseListener(x);
        Agregar_Titulo.addMouseListener(x);
        
        ventana.add(Ingresar);
        ventana.add(Borrar);
        ventana.add(Ordenar);
        ventana.add(Agregar);
        ventana.add(Eliminar);
        ventana.add(Agregar_Titulo);
        //Arriba estan los botones
        razas = new JComboBox(Razas.toArray());
        razas.setMaximumRowCount(10);
        razas.setBounds(20, 20, 200, 20);
        ventana.add(razas);
        //JLabels
        ventana.add(titulo);
        ventana.add(Tattoo);
        ventana.add(Name);
        ventana.add(Age);
        ventana.add(Expositioner);
        ventana.add(mun_y_estado);
        ventana.add(info);
        //JTextFields
        ventana.add(title);
        ventana.add(Tatuaje);
        ventana.add(Nombre);
        ventana.add(Edad);
        ventana.add(Expositor);
        ventana.add(municipio_y_estado);
        ventana.add(nueva_raza);
        //Botones
        
        //JList
        animalist2.setVisibleRowCount(15);
        animalist2.setBounds(20, 250, 450, 200);
        ventana.add(animalist2);
        //
        ventana.setLayout(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(700,500);
        ventana.setVisible(true);
        
    }
    //Aqui afuera creamos una clase para las acciones de los botones xd
    class ButtonHndlr implements MouseListener
    {
        String doble_e = "";
        int helper = 0;
        @Override
        public void mouseClicked(MouseEvent e) {
            
            try {
        //do something with 'source'
        if(e.getSource()==Ingresar)
            {
                FileWriter fw = new FileWriter(nomarchivo,true);//Basicamente, lo que le digo es que lo abra sobreescribiendo
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter salida_archivo = new PrintWriter(bw,true);
                //Aqui el codigo para Ingresar
                int prueba = 0;
                if("".equals(Tatuaje.getText()) || "".equals(Nombre.getText()) || "".equals(Edad.getText()) || "".equals(Expositor.getText()) || "".equals(municipio_y_estado.getText()))
                {
                    JOptionPane.showMessageDialog(null, "Se deben llenar todos los campos para ingresar los datos al sistema");
                    Tatuaje.setText("");
                    Nombre.setText("");
                    Edad.setText("");
                    Expositor.setText("");
                    municipio_y_estado.setText("");
                    razas.setSelectedIndex(0);
                }else if(Integer.parseInt(Edad.getText())<6 || Integer.parseInt(Edad.getText())>72)
                {
                    JOptionPane.showMessageDialog(null, "Los datos de la edad han sido ingresados de forma incorrecta, por favor ingrese un valor entre 6 y 72");
                }else
                {
                    helper = Integer.parseInt(Edad.getText());
                    principalganado[contador] = new Ganado(Tatuaje.getText(),Nombre.getText(),helper,Expositor.getText(),municipio_y_estado.getText(),razas.getSelectedIndex(),Razas.get(razas.getSelectedIndex()));
                    JOptionPane.showMessageDialog(null, "Los datos han sido ingresados con exito");
                    Tatuaje.setText("");
                    Nombre.setText("");
                    Edad.setText("");
                    Expositor.setText("");
                    municipio_y_estado.setText("");
                    prueba = razas.getSelectedIndex();
                    doble_e = razas.getSelectedItem().toString();
                    System.out.println(doble_e);
                    razas.setSelectedIndex(0);
                    principalganado[contador].mostrar();
                    model.addElement(principalganado[contador].salida_a_archivo());
                    salida_archivo.print(principalganado[contador].salida_a_archivo());
                    salida_archivo.println();
                    salida_archivo.close();
                    contador++;
                }
                System.out.println(contador);
                //En ingresar, hay que introducir el codigo de agregar a una ventana visible
                /*
                Esto se podria hacer con un JTextfield, que lea el lugar donde esta guardado e imprima, pero hay que hacerlo
                seleccionable...
                Tendré que buscar una ventana que logre hacer eso
                Se puede usar con un JList, ademas de que me dice la seleccion, puedo eliminar el elemento
                Es estatico, yo mismo establezco el tamaño, asi que bueno, habrá que intentarlo
                */
                
                /*
                Ahora, el siguiente problema será guardar todos los datos de forma estática
                Primero, hay que hacer una modificacion en el constructor
                hacer un if que busque el archivo, de mostrarlo, que cargue un label indicado que se ha cargado
                si no lo encuentra, que diga que no hay dicho archivo
                
                luego, modificar este boton, diciendole que cree un archivo si no existe, y si existe, que le agregue datos
                lo siguiente, será que el automaticamente introduzca los datos cuando inicie
                */
            }else if(e.getSource()==Borrar)
            {
                Tatuaje.setText("");
                Nombre.setText("");
                Edad.setText("");
                Expositor.setText("");
                municipio_y_estado.setText("");
                razas.setSelectedIndex(0);
            }
            else if(e.getSource()==Ordenar)
            {
                /*
                En este momento, tengo un indice guardado dentro del programa
                Ademas de eso, tengo una forma de tomar el nombre de la raza para la impresion
                No se si hacer aqui directamente la impresion, o guardar todo en el array
                secundario y luego hacerlo
                Prefiero hacer la impresion aqui
                
                -----Estructura del programa-----
                un for, sus medidas seran ()
                despues de ese for, se imprime la raza, posiblemente cree una hoja para cada raza
                habra que checkear, preferiblemente antes, si dicha raza tiene alguna entrada, para omitir la impresion
                de material innecesario
                despues, revisar en que categoría mensual cae cada animal, y hacer un separador para cada categoría
                luego, imprimir, ahi termina el ciclo
                despues se crea el documento, y gg ez pz
                */
                Workbook finalbook = new HSSFWorkbook();//Por alguna razón, el otro no funcionó xd
                CellStyle style = finalbook.createCellStyle();
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                style.setBorderTop(BorderStyle.THIN);
                
                HSSFCell cell = null;
                int ayudante;
                int tester, cont2;
                int hlpr;
                boolean hay = false;
                String prrongo="";
                boolean firstentry = false;
                String stringedad = "";
                int cantedades = 0; //Este servidor contará la cantidad de animales que hay de cierta especie
                //Para luego clasificarlos por edad
                
                int ayudarows = 0;
                int dirtydan = 1;
                boolean menor = false, joven = false, mayor=false, El_despaco=false;
                for(tester=0;tester<razas.getItemCount();tester++)//Este for se recorre en base a la cantidad de razas
                {
                    //Este for se recorre en base a la cantidad de items en la lista
                    hay = false;
                    cantedades = 0;
                    for(hlpr=0;hlpr<contador;hlpr++)//Este recorre la cantidad de animales que se han ingresado
                    {
                        if(Razas.get(tester).equals(principalganado[hlpr].raza))
                        {
                            hay = true;
                            prrongo = Razas.get(tester);
                            aux[cantedades] = new Ganado(principalganado[hlpr].getTatto(),principalganado[hlpr].getnombre(),principalganado[hlpr].getedad(),principalganado[hlpr].getexp(),principalganado[hlpr].getestado(),principalganado[hlpr].getindexraza(),principalganado[hlpr].getRaza());
                            cantedades++;
                            
                        }
                    }
                    //Hay que imprimir el numero de cada animal
                    /*
                    Por ende, hay que utilizar la variable contador
                    entonces, puedo añadir la impresion dentro de la primera celda generada
                    luego de establecerle el formato como a todas las demas, hay que cambiar el numero
                    desde luego, no se puede tocar a contador directamente
                    asi que, hay que restarle un numero, que ira disminuyendo, hasta llegar a 0, y así, imprimira todos los
                    animales existentes
                    */
                    if(hay==true)
                    {
                        ayudarows=5;
                        menor = false;
                        joven = false;
                        mayor = false;
                        System.out.println("Hay "+cantedades +" elementos de " + prrongo);
                        //Aquí irá el codigo
                        //Aqui se deberia crear la hoja de excel, con el nombre de la raza
                        Sheet hoja = finalbook.createSheet(prrongo);//Se crea la hoja con el nombre de la raza
                        //Ya entendí como funcionan los row y cell
                        //Aqui se imprime la Raza en el documento
                        Row rowtitledoc = hoja.createRow(1);
                        HSSFCell cell3 = (HSSFCell) rowtitledoc.createCell(1);
                        cell3.setCellValue(Titulo_doc);
                        
                        Row rowrace = hoja.createRow(3);
                        HSSFCell cell2 = (HSSFCell) rowrace.createCell(1);
                        cell2.setCellValue(prrongo);
                        //ahora, a aplicar
                        for(hlpr=0;hlpr<edades.length-1;hlpr++)//Un for que es recorrido por cada edad
                        {
                            firstentry=false;
                            for(cont2=0;cont2<cantedades;cont2++)//Se recorre por la cantidad de elementos que haya de dicha raza
                            {
                                if(aux[cont2].edad>=edades[hlpr] && aux[cont2].edad<=edades[hlpr+1] && aux[cont2].checked==false)
                                {   
                                    if(firstentry==false)
                                    {
                                        //Aqui habra cabida de 3 ifs, porque a la primera vez que entre en un grupo de cierta edad, tambien hay que verificar que sea campeon joven, menor o mayor
                                        
                                        System.out.println("Entró por primera vez");
                                        firstentry=true;
                                        Row row = hoja.createRow(ayudarows);//Debo crear un auxiliar para esto
                                        ayudarows+=6;
                                        stringedad = "Agrupacion de " + edades[hlpr]+ " a " + edades[hlpr+1];
                                        row.createCell(1).setCellValue(stringedad);
                                        //Aqui se imprime el nombre del grupo de la edad
                                    }else if(firstentry==true)
                                    {
                                        ayudarows+=5;
                                    }
                                    //Aqui se imprimen los datos
                                    //Vamos a ver si esto funciona
                                    if(aux[cont2].edad<=12)
                                    {
                                        menor = true;
                                    }else if(aux[cont2].edad>12 && aux[cont2].edad<=24)
                                    {
                                        joven = true;
                                    }else if(aux[cont2].edad>24)
                                    {
                                        mayor = true;
                                    }
                                    //tatuaje y expositor
                                    
                                    //Este es el espacio inicial
                                    hoja.addMergedRegion(new CellRangeAddress(ayudarows-4,ayudarows-2,8,8));
                                    
                                    Row rowtatto = hoja.createRow(ayudarows-4);
                                    rowtatto.createCell(8).setCellStyle(style);
                                    
                                    //rowtatto.createCell(1).setCellValue("Tatuaje:");
                                    HSSFCell cell1 = (HSSFCell) rowtatto.createCell(1);
                                    cell1.setCellValue(dirtydan);
                                    cell1.setCellStyle(style);
                                    CellUtil.setAlignment(cell1, HorizontalAlignment.CENTER_SELECTION);
                                    CellUtil.setVerticalAlignment(cell1, VerticalAlignment.CENTER);
                                    hoja.addMergedRegion(new CellRangeAddress(ayudarows-4,ayudarows-2,1,1));
                                    dirtydan++;
                                    cell = (HSSFCell) rowtatto.createCell(2);
                                    cell.setCellValue("Tatuaje");
                                    cell.setCellStyle(style);
                                    cell = (HSSFCell) rowtatto.createCell(3);
                                    cell.setCellValue(aux[cont2].tatuaje);
                                    cell.setCellStyle(style);
                                    //rowtatto.createCell(2).setCellValue(aux[cont2].tatuaje);
                                    //rowtatto.createCell(4).setCellValue("Expositor: " + aux[cont2].expositor);
                                    cell = (HSSFCell) rowtatto.createCell(4);
                                    cell.setCellValue("Expositor: " + aux[cont2].expositor);
                                    cell.setCellStyle(style);
                                    CellUtil.setAlignment(cell, HorizontalAlignment.CENTER_SELECTION);
                                    CellUtil.setVerticalAlignment(cell, VerticalAlignment.CENTER);
                                    hoja.addMergedRegion(new CellRangeAddress(ayudarows-4,ayudarows-3,4,7));
                                    //rowtatto.createCell(5).setCellValue("Prueba prrongo");//Ahora hay que implementar esto, GG ez
                                    //hoja.addMergedRegion(new CellRangeAddress(ayudarows-5,ayudarows-4,5,6));
                                    //nombre y estado
                                    Row rowname = hoja.createRow(ayudarows-3);
                                    //rowname.createCell(1).setCellValue("Nombre:");
                                    cell = (HSSFCell) rowname.createCell(2);
                                    cell.setCellValue("Nombre");
                                    cell.setCellStyle(style);
                                    //rowname.createCell(2).setCellValue(aux[cont2].nombre);
                                    cell = (HSSFCell) rowname.createCell(3);
                                    cell.setCellValue(aux[cont2].nombre);
                                    cell.setCellStyle(style);
                                    cell = (HSSFCell) rowname.createCell(1);
                                    cell.setCellStyle(style);
                                    cell = (HSSFCell) rowname.createCell(8);
                                    cell.setCellStyle(style);
                                    //rowname.createCell(2).setCellValue(aux[cont2].nombre);
                                    //edad
                                    Row rowage = hoja.createRow(ayudarows-2);
                                    //rowage.createCell(1).setCellValue("Edad:");
                                    cell = (HSSFCell) rowage.createCell(2);
                                    cell.setCellValue("Edad:");
                                    cell.setCellStyle(style);
                                    
                                    cell = (HSSFCell) rowage.createCell(1);
                                    cell.setCellStyle(style);
                                    cell = (HSSFCell) rowage.createCell(8);
                                    cell.setCellStyle(style);
                                    //rowage.createCell(2).setCellValue(aux[cont2].edad+ " meses");
                                    cell = (HSSFCell) rowage.createCell(3);
                                    cell.setCellValue(aux[cont2].edad+ " meses");
                                    cell.setCellStyle(style);
                                    
                                    //
                                    //rowage.createCell(4).setCellValue("Municipio/Estado: " + aux[cont2].estado);
                                    hoja.addMergedRegion(new CellRangeAddress(ayudarows-2,ayudarows-2,4,7));
                                    cell = (HSSFCell) rowage.createCell(4);
                                    cell.setCellValue("Municipio/Estado: " + aux[cont2].estado);
                                    cell.setCellStyle(style);
                                    CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
                                    for(ayudante=5;ayudante<8;ayudante++)
                                    {
                                        cell = (HSSFCell) rowname.createCell(ayudante);
                                        cell.setCellStyle(style);
                                        cell = (HSSFCell) rowtatto.createCell(ayudante);
                                        cell.setCellStyle(style);
                                        cell = (HSSFCell) rowage.createCell(ayudante);
                                        cell.setCellStyle(style);
                                    }
                                    
                                    //
                                    System.out.println("Este elemento de "+ Razas.get(tester) +" pertenece a la categoria de edades entre "+edades[hlpr]+" y "+edades[hlpr+1]+" meses");
                                    aux[cont2].checked=true;
                                    //En este punto, se imprimen todos los datos
                                    //hoja final == Fin del juego, viejo
                                    
                                    
                                    
                                }
                                
                                    
                                //Para imprimir el campeon de cada categoria se puede hacer lo siguiente:
                                /*
                                Se crea un bool que verifique que entró a dicha categoría
                                Se crea otro bool que verifique que ya salió de la misma
                                si ambos booleanos se encuentran verificados, se debe imprimir la barra de dicho campeonato
                                dicho if solo podra ir antes del if principal y, por supuesto, no puede ir cocatenado
                                
                                */
                            }
                                //Me provoca crear esto dentro de cada if
                                if(menor==true && edades[hlpr]==9 && cont2==cantedades)
                                {
                                    System.out.println("Hay campeon menor de " + Razas.get(tester));
                                    ayudarows+=2;
                                    Row rowChamp = hoja.createRow(ayudarows-2);
                                    cell = (HSSFCell) rowChamp.createCell(1);
                                    cell.setCellValue("Campeon Menor: _______________________________");
                                    ayudarows++;
                                    Row rowReserve = hoja.createRow(ayudarows-1);
                                    cell = (HSSFCell) rowReserve.createCell(1);
                                    cell.setCellValue("Reserva Campeon Menor: _______________________");
                                    ayudarows++;
                                    
                                }
                                if(joven==true && edades[hlpr]==21 && cont2==cantedades)
                                {
                                    System.out.println("Hay campeon joven de " + Razas.get(tester));
                                    ayudarows+=2;
                                    Row rowChamp = hoja.createRow(ayudarows-2);
                                    cell = (HSSFCell) rowChamp.createCell(1);
                                    cell.setCellValue("Campeon Joven: _______________________________");
                                    ayudarows++;
                                    Row rowReserve = hoja.createRow(ayudarows-1);
                                    cell = (HSSFCell) rowReserve.createCell(1);
                                    cell.setCellValue("Reserva Campeon Joven: _______________________");
                                    ayudarows++;
                                    
                                }
                                if(mayor==true && edades[hlpr]==60 && cont2==cantedades)
                                {
                                    System.out.println("Hay campeon mayor de " + Razas.get(tester));
                                    ayudarows+=2;
                                    Row rowChamp = hoja.createRow(ayudarows-2);
                                    cell = (HSSFCell) rowChamp.createCell(1);
                                    cell.setCellValue("Campeon Mayor: _______________________________");
                                    ayudarows++;
                                    Row rowReserve = hoja.createRow(ayudarows-1);
                                    cell = (HSSFCell) rowReserve.createCell(1);
                                    cell.setCellValue("Reserva Campeon Mayor: _______________________");
                                }
                                
                                if(mayor == true && joven == true  && edades[hlpr]==60 && cont2==cantedades || mayor ==true && menor == true  && edades[hlpr]==60 && cont2==cantedades)
                                {
                                    System.out.println("Hay gran campeonato de " + Razas.get(tester));
                                    ayudarows++;
                                    Row rowChamp = hoja.createRow(ayudarows);
                                    cell = (HSSFCell) rowChamp.createCell(1);
                                    cell.setCellValue("Gran Campeonato: _____________________________");
                                    ayudarows++;
                                    Row rowReserve = hoja.createRow(ayudarows+1);
                                    cell = (HSSFCell) rowReserve.createCell(1);
                                    cell.setCellValue("Reserva Gran Campeonato: _____________________");
                                }
                        }
                        //Sheet hoja = finalbook.createSheet(prrongo);
                    }
                    
                }
                FileOutputStream fileOut = null;
                try {
                    fileOut = new FileOutputStream("CATALOGO-2018.xls");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    finalbook.write(fileOut);
                } catch (IOException ex) {
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    fileOut.close();
                } catch (IOException ex) {
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    // Closing the workbook
                    finalbook.close();
                    /*
                    Nueva estructura:
                    Se crea un for que recorra las razas
                    se verifica que existan 1 o mas objetos de esa raza
                    luego, se buscan esos objetos, y se imprimen dentro de la hoja
                    luego, se sale al for principal de la raza y se sigue el procedimiento
                    luego, se procede a entregar el documento
                    gg ez mid
                    */
                } catch (IOException ex) {
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(null, "Se ha generado el documento excel de forma exitosa");
                
            }else if(e.getSource()==Agregar)
            {
                //Codigo para agregar
                String helpmale = "";
                String helpfemale = "";
                int help2;
                helpmale = "Machos " + nueva_raza.getText() ;
                helpfemale ="Hembras " + nueva_raza.getText();
                Razas.add(helpmale);
                Razas.add(helpfemale);
                nueva_raza.setText("");
                razas.addItem(helpmale);
                razas.addItem(helpfemale);
                JOptionPane.showMessageDialog(null, "Se ha agregado una nueva raza");
            }else if(e.getSource()==Eliminar)
            {   //Hay que volver a llenar el archivo de texto con los datos de aquí
                int seleccionado = 0;
                int er;
                seleccionado = animalist2.getSelectedIndex();
                model.remove(seleccionado);
                if(seleccionado<contador)
                {
                    for(er=seleccionado;er<contador-1;er++)
                    {
                        principalganado[er].copiar(principalganado[er+1]);
                    }
                    contador--;
                }else if(seleccionado == contador)
                {
                    principalganado[seleccionado] = null;
                    contador--;
                }
                //ahora, a hacer un for que reescriba el archivo de texto
                System.out.println(seleccionado);
                PrintWriter pw;
                try {
                pw = new PrintWriter("Ganado.txt");
                
                for(er=0;er<contador;er++)
                {
                    pw.println(principalganado[er].salida_a_archivo());
                }
                pw.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }else if(e.getSource()==Agregar_Titulo)
            {
                //Aqui va el codigo del titulo
                String tester = "";
                tester = title.getText();
                if("".equals(tester))
                {
                    JOptionPane.showMessageDialog(null, "Ingrese un titulo valido");
                }else
                {
                    //Debe guardarse en una variable
                    Titulo_doc = tester;
                    JOptionPane.showMessageDialog(null, "Se ha establecido el titulo del documento");
                    title.setText("");
                }
            }
                } catch (NumberFormatException exep) 
                { // catch any exception
                     JOptionPane.showMessageDialog(null, "Ingrese la edad de forma correcta");
                     Tatuaje.setText("");
                     Nombre.setText("");
                     Edad.setText("");
                     Expositor.setText("");
                     municipio_y_estado.setText("");
                     razas.setSelectedIndex(0);
                } catch (IOException excepto)
                {
                    System.out.println("F");
                }
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }
        
    }
}
