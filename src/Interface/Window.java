package Interface;

import Data.PositionXML;
import Domain.FifthLane;
import Domain.FirstLane;
import Domain.FourthLane;
import Domain.Music;
import Domain.Position;
import Domain.SecondLane;
import Domain.ThirdLane;
import Utility.Variables;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import org.jdom.JDOMException;

public class Window extends Application implements Runnable, EventHandler<ActionEvent> {
//RYU es el más veloz, luego bill, megaman es el más lento y kirby es random
private static Window instance = new Window();
    private Thread thread;
    private Scene scene;
    private Pane pane;
    private Canvas canvas;
    private Image image;
    private Image barrierImage;

    private ThirdLane third;
    private FirstLane first;
    private SecondLane second;
    private FourthLane fourth;
    private FifthLane fifth;
    private PositionXML posXML;

    private Button buttonCreate;
    private Button buttonBarrier;
    private Button buttonRevert;
    private Button buttonSimulation;
    private Button buttonInterrupt;
    private Button buttonRestart;
    private Button buttonSoundOnOff;

    private TextField testValue;
    private TextField textCarries;

    private Label labelValue;
    private Label labelCarriles;

    private boolean soundOnOff;
    Image imageSound;
    ImageView imageView;

    private ComboBox SpeedComboBox;

    boolean stopThreads = false;
    private ArrayList<Image> imagesSprite;
    int randomLane;

    private ArrayList<FirstLane> lanesFirst;
    private ArrayList<SecondLane> lanesSecond;
    private ArrayList<ThirdLane> lanesThird;
    private ArrayList<FourthLane> lanesFourth;
    private ArrayList<FifthLane> lanesFifth;

    AudioClip clip;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Graphics and Threads");
        initComponents(primaryStage);
        primaryStage.setOnCloseRequest(exit);
        primaryStage.show();
        ArrayList<Image> imagesSprite = new ArrayList<>();
        playMusic();

    }

    @Override
    public void run() {

        long start;
        long elapsed;
        long wait;
        int fps = 30;
        long time = 1000 / fps;

        while (true) {
            try {
                start = System.nanoTime();
                elapsed = System.nanoTime() - start;
                wait = time - elapsed / 1000000;
                Thread.sleep(wait);
                GraphicsContext gc = this.canvas.getGraphicsContext2D();
                draw(gc);
            } catch (InterruptedException ex) {
            }
        }
    }

    private void initComponents(Stage primaryStage) throws IOException, JDOMException {
        this.posXML = new PositionXML(Utility.Variables.POSITIONPATH);
        try {
            this.pane = new Pane();
            this.scene = new Scene(this.pane, Variables.WIDTH, Variables.HEIGHT);
            this.canvas = new Canvas(Variables.WIDTH, Variables.HEIGHT);
            this.image = new Image(new FileInputStream("src/Assets/pistaa.jpg"));
            this.barrierImage = new Image(new FileInputStream("src/Assets/valla2.png"));

            this.pane.getChildren().add(this.canvas);

            this.testValue = new TextField();
            this.textCarries = new TextField();

            this.labelValue = new Label("Value");
            this.labelCarriles = new Label("Lanes");

            this.buttonCreate = new Button("Create");
            this.buttonBarrier = new Button("Barrier");
            this.buttonRevert = new Button("Revert");
            this.buttonSimulation = new Button("Simulation");
            this.buttonInterrupt = new Button("Interrupt");
            this.buttonRestart = new Button("Restart");
            this.buttonSoundOnOff = new Button();
            this.soundOnOff = true;
            this.imageSound = new Image(new FileInputStream("src/Assets/SoundOn.png"));
            imageView = new ImageView(imageSound);
            imageView.setFitWidth(40);
            imageView.setFitHeight(30);
            this.buttonSoundOnOff.setGraphic(imageView);
            this.clip = new AudioClip(this.getClass().getResource("batman.mp3").toString());

            this.SpeedComboBox = new ComboBox();
            this.SpeedComboBox.getItems().addAll("Medium", "Quick", "Slow", "Random");
            this.SpeedComboBox.relocate(1040, 150);
            this.SpeedComboBox.setValue("Speed");

            this.buttonCreate.relocate(1040, 75);
            this.buttonBarrier.relocate(1040, 325);
            this.buttonRevert.relocate(1040, 365);
            this.buttonInterrupt.relocate(1040, 405);
            this.buttonRestart.relocate(1040, 445);
            this.buttonSimulation.relocate(1040, 495);
            this.buttonSoundOnOff.relocate(1070, 555);

            this.testValue.relocate(1040, 40);
            this.textCarries.relocate(1040, 278);

            this.labelCarriles.relocate(1040, 258);
            this.labelValue.relocate(1040, 20);

            this.buttonCreate.setPrefSize(110, 30);
            this.buttonBarrier.setPrefSize(110, 30);
            this.buttonRevert.setPrefSize(110, 30);
            this.buttonInterrupt.setPrefSize(110, 30);
            this.buttonRestart.setPrefSize(110, 30);
            this.buttonSoundOnOff.setPrefSize(50, 30);
            this.buttonSimulation.setPrefSize(115, 50);

            this.buttonCreate.setOnAction(this);
            this.buttonBarrier.setOnAction(this);
            this.buttonInterrupt.setOnAction(this);
            this.buttonRevert.setOnAction(this);
            this.buttonSimulation.setOnAction(this);
            this.buttonSoundOnOff.setOnAction(this);
            this.buttonRestart.setOnAction(this);

            this.textCarries.setPrefSize(140, 30);
            this.testValue.setPrefSize(140, 30);

            this.pane.getChildren().add(buttonCreate);
            this.pane.getChildren().add(buttonBarrier);
            this.pane.getChildren().add(buttonRevert);
            this.pane.getChildren().add(buttonInterrupt);
            this.pane.getChildren().add(buttonSimulation);
            this.pane.getChildren().add(buttonRestart);
            this.pane.getChildren().add(buttonSoundOnOff);

            this.pane.getChildren().add(testValue);
            this.pane.getChildren().add(textCarries);
            this.pane.getChildren().add(labelCarriles);
            this.pane.getChildren().add(labelValue);

            this.pane.getChildren().add(SpeedComboBox);

            primaryStage.setScene(this.scene);
            lanesFirst = new ArrayList<>();
            lanesSecond = new ArrayList<>();
            lanesThird = new ArrayList<>();
            lanesFourth = new ArrayList<>();
            lanesFifth = new ArrayList<>();
//            Position p1 = new Position(785, 200);
//            posXML.insertCharacter(p1);
//            Position p2 = new Position(815, 200);
//            posXML.insertCharacter(p2);
//            Position p3 = new Position(845, 200);
//            posXML.insertCharacter(p3);
//            Position p4 = new Position(875, 200);
//            posXML.insertCharacter(p4);
//            Position p5 = new Position(905, 200);
//            posXML.insertCharacter(p5);

            this.thread = new Thread(this);
            this.thread.start();
        } catch (FileNotFoundException | BufferOverflowException ex) {
        }
    }

    public void playMusic() {
        Music myMusic = Music.getInstance();
        myMusic.setAudio(clip);
    }
    
    EventHandler<WindowEvent> exit = new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            System.exit(0);
        }
    };
    
    @Override
    public void handle(ActionEvent e) {
        if ((Button) e.getSource() == buttonCreate) {
            
            for (int i = 0; i < Integer.parseInt(testValue.getText()); i++) {
                randomLane = (int) (Math.random() * (5 - 1) + 1);
                System.out.println(randomLane);
                
                System.out.println(SpeedComboBox.getValue());
                switch (randomLane) {
                    case 1: {
                        
                        try {
                            
                            if (SpeedComboBox.getValue().equals("Medium")) {
                                FirstLane firstL = new FirstLane("thread 1", posXML.getAllPosition().get(0).getX(), posXML.getAllPosition().get(0).getY(), 0, Utility.Variables.MEDIUM);
                                lanesFirst.add(firstL);
                            } else if (SpeedComboBox.getValue().equals("Quick")) {
                                FirstLane firstL = new FirstLane("thread 1", posXML.getAllPosition().get(0).getX(), posXML.getAllPosition().get(0).getY(), 0, Utility.Variables.QUICK);
                                lanesFirst.add(firstL);
                            } else if (SpeedComboBox.getValue().equals("Slow")) {
                                FirstLane firstL = new FirstLane("thread 1", posXML.getAllPosition().get(0).getX(), posXML.getAllPosition().get(0).getY(), 0, Utility.Variables.SlOW);
                                lanesFirst.add(firstL);
                            } else if (SpeedComboBox.getValue().equals("Random")) {
                                FirstLane firstL = new FirstLane("thread 1", posXML.getAllPosition().get(0).getX(), posXML.getAllPosition().get(0).getY(), 0, Utility.Variables.RANDOM);
                                lanesFirst.add(firstL);
                            }
                            
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case 2:
                        
                        try {
                            
                            if (SpeedComboBox.getValue().equals("Medium")) {
                                SecondLane secondL = new SecondLane("thread 2", posXML.getAllPosition().get(1).getX(), posXML.getAllPosition().get(1).getY(), 0, Utility.Variables.MEDIUM);
                                lanesSecond.add(secondL);
                            } else if (SpeedComboBox.getValue().equals("Quick")) {
                                SecondLane secondL = new SecondLane("thread 2", posXML.getAllPosition().get(1).getX(), posXML.getAllPosition().get(1).getY(), 0, Utility.Variables.QUICK);
                                lanesSecond.add(secondL);
                            } else if (SpeedComboBox.getValue().equals("Slow")) {
                                SecondLane secondL = new SecondLane("thread 2", posXML.getAllPosition().get(1).getX(), posXML.getAllPosition().get(1).getY(), 0, Utility.Variables.SlOW);
                                lanesSecond.add(secondL);
                            } else if (SpeedComboBox.getValue().equals("Random")) {
                                SecondLane secondL = new SecondLane("thread 2", posXML.getAllPosition().get(1).getX(), posXML.getAllPosition().get(1).getY(), 0, Utility.Variables.RANDOM);
                                lanesSecond.add(secondL);
                            }
                            
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        break;
                    case 3:
                        try {
                            
                            if (SpeedComboBox.getValue().equals("Medium")) {
                                ThirdLane thirdL = new ThirdLane("thread 3", posXML.getAllPosition().get(2).getX(), posXML.getAllPosition().get(2).getY(), 0, Utility.Variables.MEDIUM);
                                lanesThird.add(thirdL);
                            } else if (SpeedComboBox.getValue().equals("Quick")) {
                                ThirdLane thirdL = new ThirdLane("thread 3", posXML.getAllPosition().get(2).getX(), posXML.getAllPosition().get(2).getY(), 0, Utility.Variables.QUICK);
                                lanesThird.add(thirdL);
                            } else if (SpeedComboBox.getValue().equals("Slow")) {
                                ThirdLane thirdL = new ThirdLane("thread 3", posXML.getAllPosition().get(2).getX(), posXML.getAllPosition().get(2).getY(), 0, Utility.Variables.SlOW);
                                lanesThird.add(thirdL);
                            } else if (SpeedComboBox.getValue().equals("Random")) {
                                ThirdLane thirdL = new ThirdLane("thread 3", posXML.getAllPosition().get(2).getX(), posXML.getAllPosition().get(2).getY(), 0, Utility.Variables.RANDOM);
                                lanesThird.add(thirdL);
                            }
                            
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        break;
                    
                    case 4:
                        try {
                            
                            if (SpeedComboBox.getValue().equals("Medium")) {
                                FourthLane fourthL = new FourthLane("thread 4", posXML.getAllPosition().get(3).getX(), posXML.getAllPosition().get(3).getY(), 0, Utility.Variables.MEDIUM);
                                lanesFourth.add(fourthL);
                            } else if (SpeedComboBox.getValue().equals("Quick")) {
                                FourthLane fourthL = new FourthLane("thread 4", posXML.getAllPosition().get(3).getX(), posXML.getAllPosition().get(3).getY(), 0, Utility.Variables.QUICK);
                                lanesFourth.add(fourthL);
                            } else if (SpeedComboBox.getValue().equals("Slow")) {
                                FourthLane fourthL = new FourthLane("thread 4", posXML.getAllPosition().get(3).getX(), posXML.getAllPosition().get(3).getY(), 0, Utility.Variables.SlOW);
                                lanesFourth.add(fourthL);
                            } else if (SpeedComboBox.getValue().equals("Random")) {
                                FourthLane fourthL = new FourthLane("thread 4", posXML.getAllPosition().get(3).getX(), posXML.getAllPosition().get(3).getY(), 0, Utility.Variables.RANDOM);
                                lanesFourth.add(fourthL);
                            }
                            
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        break;
                    
                    case 5:
                        try {
                            
                            if (SpeedComboBox.getValue().equals("Medium")) {
                                FifthLane fifthL = new FifthLane("thread 5", posXML.getAllPosition().get(4).getX(), posXML.getAllPosition().get(4).getY(), 0, Utility.Variables.MEDIUM);
                                lanesFifth.add(fifthL);
                            } else if (SpeedComboBox.getValue().equals("Quick")) {
                                FifthLane fifthL = new FifthLane("thread 5", posXML.getAllPosition().get(4).getX(), posXML.getAllPosition().get(4).getY(), 0, Utility.Variables.QUICK);
                                lanesFifth.add(fifthL);
                            } else if (SpeedComboBox.getValue().equals("Slow")) {
                                FifthLane fifthL = new FifthLane("thread 5", posXML.getAllPosition().get(4).getX(), posXML.getAllPosition().get(4).getY(), 0, Utility.Variables.SlOW);
                                lanesFifth.add(fifthL);
                            } else if (SpeedComboBox.getValue().equals("Random")) {
                                FifthLane fifthL = new FifthLane("thread 5", posXML.getAllPosition().get(4).getX(), posXML.getAllPosition().get(4).getY(), 0, Utility.Variables.QUICK);
                                lanesFifth.add(fifthL);
                            }
                            
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        break;
                    
                    default:
                        break;
                    
                }
            }
            
        }
        if ((Button) e.getSource() == buttonRestart) {
            stopThreads = true;
            System.out.println(stopThreads);
            if (stopThreads == true) {
                if (!lanesFirst.isEmpty() || !lanesSecond.isEmpty() || !lanesThird.isEmpty() || !lanesFourth.isEmpty() || !lanesFifth.isEmpty()) {
                    for (int i = 0; i < lanesFirst.size(); i++) {
                        lanesFirst.get(i).resume();
                    }
                    for (int i = 0; i < lanesSecond.size(); i++) {
                        lanesSecond.get(i).resume();
                    }
                    
                    for (int i = 0; i < lanesThird.size(); i++) {
                        lanesThird.get(i).resume();
                    }
                    
                    for (int i = 0; i < lanesFourth.size(); i++) {
                        lanesFourth.get(i).resume();
                    }
                    
                    for (int i = 0; i < lanesFifth.size(); i++) {
                        lanesFifth.get(i).resume();
                    }
                    
                }
                if (textCarries.getText().equals("1")) {
                    if (!lanesFirst.isEmpty()) {
                        for (int i = 0; i < lanesFirst.size(); i++) {
                            lanesFirst.get(i).setWall(false);
                            lanesFirst.get(i).setWall(false);
                        }
                    }
                    
                }
                
                if (textCarries.getText().equals("2")) {
                    if (!lanesSecond.isEmpty()) {
                        for (int i = 0; i < lanesSecond.size(); i++) {
                            lanesSecond.get(i).setWall(false);
                            lanesSecond.get(i).setReverse(false);
                        }
                    }
                }
                if (textCarries.getText().equals("3")) {
                    if (!lanesThird.isEmpty()) {
                        
                        for (int i = 0; i < lanesThird.size(); i++) {
                            lanesThird.get(i).setWall(false);
                            lanesThird.get(i).setReverse(false);
                        }
                    }
                }
                if (textCarries.getText().equals("4")) {
                    if (!lanesFourth.isEmpty()) {
                    }
                    for (int i = 0; i < lanesFourth.size(); i++) {
                        lanesFourth.get(i).setWall(false);
                        lanesFourth.get(i).setReverse(false);
                    }
                }
                if (textCarries.getText().equals("5")) {
                    if (!lanesFifth.isEmpty()) {
                        for (int i = 0; i < lanesFifth.size(); i++) {
                            lanesFifth.get(i).setWall(false);
                            lanesFifth.get(i).setReverse(false);
                        }
                    }
                }
                
            }
        }
        if ((Button) e.getSource() == buttonBarrier) {
            
            if (textCarries.getText().equals("1")) {
                if (!lanesFirst.isEmpty()) {
                    for (int i = 0; i < lanesFirst.size(); i++) {
                        lanesFirst.get(i).setWall(true);
                    }
                }
            }
            
            if (textCarries.getText().equals("2")) {
                if (!lanesSecond.isEmpty()) {
                    for (int i = 0; i < lanesSecond.size(); i++) {
                        lanesSecond.get(i).setWall(true);
                    }
                }
            }
            if (textCarries.getText().equals("3")) {
                if (!lanesThird.isEmpty()) {
                    
                    for (int i = 0; i < lanesThird.size(); i++) {
                        lanesThird.get(i).setWall(true);
                    }
                }
            }
            if (textCarries.getText().equals("4")) {
                if (!lanesFourth.isEmpty()) {
                }
                for (int i = 0; i < lanesFourth.size(); i++) {
                    lanesFourth.get(i).setWall(true);
                }
            }
            
            if (textCarries.getText().equals("5")) {
                
                if (!lanesFifth.isEmpty()) {
                    for (int i = 0; i < lanesFifth.size(); i++) {
                        lanesFifth.get(i).setWall(true);
                    }
                }
            }
        }
        
        if ((Button) e.getSource() == buttonInterrupt) {
            stopThreads = false;
            if (stopThreads != true) {
                if (!lanesFirst.isEmpty() || !lanesSecond.isEmpty() || !lanesThird.isEmpty() || !lanesFourth.isEmpty() || !lanesFifth.isEmpty()) {
                    for (int i = 0; i < lanesFirst.size(); i++) {
                        lanesFirst.get(i).suspend();
                    }
                    for (int i = 0; i < lanesSecond.size(); i++) {
                        lanesSecond.get(i).suspend();
                    }
                    
                    for (int i = 0; i < lanesThird.size(); i++) {
                        lanesThird.get(i).suspend();
                    }
                    
                    for (int i = 0; i < lanesFourth.size(); i++) {
                        lanesFourth.get(i).suspend();
                    }
                    
                    for (int i = 0; i < lanesFifth.size(); i++) {
                        lanesFifth.get(i).suspend();
                    }
                }
            }
            
        }
        if ((Button) e.getSource() == buttonRevert) {
            if (textCarries.getText().equals("1")) {
                if (!lanesFirst.isEmpty()) {
                    for (int i = 0; i < lanesFirst.size(); i++) {
                        lanesFirst.get(i).setReverse(true);
                    }
                }
            }
            
            if (textCarries.getText().equals("2")) {
                if (!lanesSecond.isEmpty()) {
                    for (int i = 0; i < lanesSecond.size(); i++) {
                        lanesSecond.get(i).setReverse(true);
                    }
                }
            }
            if (textCarries.getText().equals("3")) {
                if (!lanesThird.isEmpty()) {
                    
                    for (int i = 0; i < lanesThird.size(); i++) {
                        lanesThird.get(i).setReverse(true);
                    }
                }
            }
            if (textCarries.getText().equals("4")) {
                if (!lanesFourth.isEmpty()) {
                }
                for (int i = 0; i < lanesFourth.size(); i++) {
                    lanesFourth.get(i).setReverse(true);
                }
            }
            
            if (textCarries.getText().equals("5")) {
                
                if (!lanesFifth.isEmpty()) {
                    for (int i = 0; i < lanesFifth.size(); i++) {
                        lanesFifth.get(i).setReverse(true);
                    }
                }
            }
        }
        if ((Button) e.getSource() == buttonSoundOnOff) {
            if (soundOnOff == true) {
                clip.stop();
                soundOnOff = false;
                try {
                    this.imageSound = new Image(new FileInputStream(("src/Assets/SoundOff.png")));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }
                imageView = new ImageView(imageSound);
                imageView.setFitWidth(40);
                imageView.setFitHeight(30);
                this.buttonSoundOnOff.setGraphic(imageView);
            } else {
                clip.play();
                soundOnOff = true;
                try {
                    this.imageSound = new Image(new FileInputStream("src/Assets/SoundOn.png"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }
                imageView = new ImageView(imageSound);
                imageView.setFitWidth(40);
                imageView.setFitHeight(30);
                this.buttonSoundOnOff.setGraphic(imageView);
            }
        }
        if ((Button) e.getSource() == buttonSimulation) {
            stopThreads = true;
            System.out.println(stopThreads);
            if (stopThreads == true) {
                
                if (!lanesFirst.isEmpty() || !lanesSecond.isEmpty() || !lanesThird.isEmpty() || !lanesFourth.isEmpty() || !lanesFifth.isEmpty()) {
                    for (int i = 0; i < lanesFirst.size(); i++) {
                        
                        lanesFirst.get(i).start();
                        
                    }
                    for (int i = 0; i < lanesSecond.size(); i++) {
                        lanesSecond.get(i).start();
                    }
                    
                    for (int i = 0; i < lanesThird.size(); i++) {
                        lanesThird.get(i).start();
                    }
                    
                    for (int i = 0; i < lanesFourth.size(); i++) {
                        lanesFourth.get(i).start();
                    }
                    
                    for (int i = 0; i < lanesFifth.size(); i++) {
                        lanesFifth.get(i).start();
                    }
                }
                
                AudioClip clip2 = new AudioClip(this.getClass().getResource("vuvuzela.mp3").toString());
                clip2.play();
            }
            try {
                thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        }
    }
    
    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, Variables.WIDTH, Variables.HEIGHT);
        gc.drawImage(this.image, 0, 0);
        System.out.println(lanesFirst.size());
        
        if (!lanesFirst.isEmpty() || !lanesSecond.isEmpty() || !lanesThird.isEmpty() || !lanesFourth.isEmpty() || !lanesFifth.isEmpty()) {
            for (int i = 0; i < lanesFirst.size(); i++) {
                gc.drawImage(lanesFirst.get(i).getImage(), lanesFirst.get(i).getX(), lanesFirst.get(i).getY());
            }
            for (int i = 0; i < lanesSecond.size(); i++) {
                gc.drawImage(lanesSecond.get(i).getImage(), lanesSecond.get(i).getX(), lanesSecond.get(i).getY());
            }
            for (int i = 0; i < lanesThird.size(); i++) {
                gc.drawImage(lanesThird.get(i).getImage(), lanesThird.get(i).getX(), lanesThird.get(i).getY());
            }
            for (int i = 0; i < lanesFourth.size(); i++) {
                gc.drawImage(lanesFourth.get(i).getImage(), lanesFourth.get(i).getX(), lanesFourth.get(i).getY());
            }
            for (int i = 0; i < lanesFifth.size(); i++) {
                gc.drawImage(lanesFifth.get(i).getImage(), lanesFifth.get(i).getX(), lanesFifth.get(i).getY());
            }
        } else {
            System.out.println("Empty");
        }
        
        if (!lanesFirst.isEmpty() || !lanesSecond.isEmpty() || !lanesThird.isEmpty() || !lanesFourth.isEmpty() || !lanesFifth.isEmpty()) {
            for (int i = 0; i < lanesFirst.size(); i++) {
                if (lanesFirst.get(i).isWall()) {
                    gc.drawImage(barrierImage, 230, 400);
                }
            }
            
            for (int i = 0; i < lanesSecond.size(); i++) {
                if (lanesSecond.get(i).isWall()) {
                    gc.drawImage(barrierImage, 215, 440);
                }
            }
            
            for (int i = 0; i < lanesThird.size(); i++) {
                if (lanesThird.get(i).isWall()) {
                    gc.drawImage(barrierImage, 190, 470);
                }
                
            }
            
            for (int i = 0; i < lanesFourth.size(); i++) {
                if (lanesFourth.get(i).isWall()) {
                    gc.drawImage(barrierImage, 175, 500);
                }
            }
            
            for (int i = 0; i < lanesFifth.size(); i++) {
                if (lanesFifth.get(i).isWall()) {
                    gc.drawImage(barrierImage, 159, 510);
                    
                }
            }
        }
        
    }
}//End aclss
