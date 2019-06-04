package Domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.image.Image;
import org.jdom.JDOMException;

public class SecondLane extends Character {

    public SecondLane(String identification, int x, int y, int imgNum, int speed) throws FileNotFoundException {
        super(identification, x, y, imgNum, speed);
        setSprite();
    }

    public void setSprite() throws FileNotFoundException {
        ArrayList<Image> sprite = super.getSprite();
        for (int i = 0; i < 6; i++) {
            if (super.getSpeed() == Utility.Variables.SlOW) {
                sprite.add(new Image(new FileInputStream("src/Assets/bill" + i + ".gif")));
            } else if (super.getSpeed() == Utility.Variables.QUICK) {
                sprite.add(new Image(new FileInputStream("src/Assets/Ryu" + i + ".gif")));
            } else if (super.getSpeed() == Utility.Variables.MEDIUM) {
                sprite.add(new Image(new FileInputStream("src/Assets/mega" + i + ".gif")));
            } else if (super.getSpeed() == Utility.Variables.RANDOM) {
                sprite.add(new Image(new FileInputStream("src/Assets/Running" + i + ".png")));
            }
        }
        super.setSprite(sprite);
    }

    @Override
    public void run() {
        ArrayList<Image> sprite = super.getSprite();
        int x = 0;
        int change = 1;
        while (true) {
            try {
                switch (change) {

                    case 1:
                        if (!super.isReverse()) {
                            for (int i = super.getY(); i < 440; i = i + 15) {//´+40
                                for (int j = 3; j < 6; j++) {
                                    super.setImage(sprite.get(j));
                                    Thread.sleep(this.getSpeed());
                                }
                                super.setY(i);
                                if (super.getY() > 255 && super.getY() < 450) {
                                    super.setX(super.getX() - 7);
                                }
                                change = 2;
                                if (super.isReverse()) {
                                    i = 440;
                                    change = 1;
                                }
                            }
                        } else {
                            for (int r = super.getY(); r > 190; r = r - 15) {
                                for (int t = 0; t < 3; t++) {
                                    super.setImage(sprite.get(t));
                                    Thread.sleep(this.getSpeed());
                                }
                                super.setY(r);
                                if (super.getY() > 255 && super.getY() < 420) {
                                    super.setX(super.getX() + 7);
                                }
                                change = -1;
                                if (!super.isReverse()) {
                                    r = 190;
                                    change = 1;
                                }
                            }
                        }

                        break;
                    case 2:
                        if (!super.isReverse()) {
                            for (int i = super.getX(); i > 215; i = i - 15) {//-15
                                for (int j = 3; j < 6; j++) {
                                    super.setImage(sprite.get(j));
                                    Thread.sleep(100);
                                }
                                super.setX(i);
                                change = 3;
                                if (super.isReverse()) {
                                    i = 215;
                                    change = 2;
                                }
                            }
                        } else {
                            for (int i = super.getX(); i < 750; i = i + 15) {
                                for (int j = 0; j < 3; j++) {
                                    super.setImage(sprite.get(j));
                                    Thread.sleep(100);
                                }
                                super.setX(i);
                                change = 1;
                                if (!super.isReverse()) {
                                    i = 750;
                                    change = 2;
                                }
                            }
                        }

                        break;
                    case 3:
                        if (super.isWall()) {
                            suspend();
                        }
                        change = 4;

                        break;

                    case 4:
                        System.out.println("4");
                        if (!super.isReverse()) {
                            for (int i = super.getY(); i > 40; i = i - 15) {//-20
                                for (int j = 0; j < 3; j++) {
                                    super.setImage(sprite.get(j));
                                    Thread.sleep(100);
                                }
                                super.setY(i);
                                change = 5;
                                if (super.getY() > 30) {
                                    if (super.getY() > 230) {
                                        super.setX(super.getX() - 10);
                                    } else {
                                        super.setX(super.getX() + 6);
                                    }
                                }
                                if (super.isReverse()) {
                                    i = 40;
                                    change = 4;
                                }
                            }
                        } else {
                            for (int i = super.getY(); i < 430; i = i + 15) {//+45
                                for (int j = 0; j < 3; j++) {
                                    super.setImage(sprite.get(j));
                                    Thread.sleep(100);
                                }
                                super.setY(i);
                                change = 3;
                                if (super.getY() > 30) {
                                    if (super.getY() > 230) {
                                        super.setX(super.getX() + 10);
                                    } else {
                                        super.setX(super.getX() - 6);
                                    }
                                }
                                if (!super.isReverse()) {
                                    i = 440;
                                    change = 4;
                                }
                            }
                        }
                        break;

                    case 5:
                        if (!super.isReverse()) {
                            for (int i = super.getX(); i < 700; i = i + 15) {//+10
                                for (int j = 0; j < 3; j++) {
                                    super.setImage(sprite.get(j));
                                    Thread.sleep(100);
                                }
                                super.setX(i);
                                change = 6;
                                if (super.isReverse()) {
                                    i = 690;
                                    change = 5;
                                }
                            }
                        } else {
                            for (int i = super.getX(); i > 140; i = i - 15) {
                                for (int j = 3; j < 6; j++) {
                                    super.setImage(sprite.get(j));
                                    Thread.sleep(100);
                                }
                                super.setX(i);
                                change = 4;
                                if (!super.isReverse()) {
                                    i = 140;
                                    change = 5;
                                }
                            }
                        }
                        break;

                    case 6:
                        if (!super.isReverse()) {
                            for (int i = super.getY(); i < 230; i = i + 15) {//+10
                                for (int j = 3; j < 6; j++) {
                                    super.setImage(sprite.get(j));
                                    Thread.sleep(100);
                                }
                                super.setY(i);
                                change = -1;
                                if (super.getY() < 220) {
                                    super.setX(super.getX() + 9);
                                }
                                if (super.isReverse()) {
                                    i = 230;
                                    change = 6;
                                }
                            }
                        } else {
                            for (int i = super.getY(); i > 40; i = i - 15) {//-20
                                for (int j = 3; j < 6; j++) {
                                    super.setImage(sprite.get(j));
                                    Thread.sleep(100);
                                }
                                super.setY(i);
                                change = 5;
                                if (super.getY() < 220) {
                                    super.setX(super.getX() - 9);
                                }
                                if (!super.isReverse()) {
                                    i = 90;
                                    change = 6;
                                }
                            }
                        }
                        break;

                    default:
                        break;
                }

            } catch (InterruptedException ex) {
            }
        }
    }//run
}//fin de clase
