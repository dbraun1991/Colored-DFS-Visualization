import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MyCanvas
{
    JLabel myView;
    BufferedImage mySurface;
    Random random = new Random();

    public MyCanvas(int myWidth, int myHeight)
    {
        mySurface = new BufferedImage(myWidth,myHeight,BufferedImage.TYPE_INT_RGB);
        myView = new JLabel(new ImageIcon(mySurface));
        Graphics g = mySurface.getGraphics();
        // background
        g.setColor(Color.blue);
        g.fillRect(0,0,myWidth,myHeight);

        g.setColor(Color.BLACK);
        // Keep this until I figured out if it's painted on load or not.
        // g.drawLine(10, 20, 350, 380);
        g.dispose();

        // ActionListener listener = new ActionListener() {
        //     public void actionPerformed(ActionEvent ae) {
        //        addNewElement();
        //    }
        // };

        // Timer timer = new Timer(500, listener);
        // timer.start();
    }


    public void doDFS(int startX, int startY, int lastDirection, boolean [] [] wholeField, Color myColor, int myR, int myG, int myB) {
        try {
            Thread.sleep(20);
        } catch  (InterruptedException e) {
            System.out.println("DFS ... Thread.sleep ... FAIL");
        }
        // startX = width of start square
        // startY = height of start square
        // lastDirection = last move -> 0 = left, 1 = up, 2 = right, 3 = bottom
        int myRandomDirection;

        int directions [];
        if (lastDirection == 0) {
            directions = new int [] {1,2,3};
        } else if (lastDirection == 1) {
            directions = new int [] {0,2,3};
        } else if (lastDirection == 2) {
            directions = new int [] {0,1,3};
        } else if (lastDirection == 3) {
            directions =new int[]{0, 1, 2};
        } else {
            System.out.println("DFS ... Directions ... FAIL");
            directions = new int[]{0, 1, 2, 3};
        }

        shuffleArray(directions);

        // set as marked
        wholeField[startX][startY] = true;

        // draw
        Graphics g = mySurface.getGraphics();
        int x = startX*10;
        int y = startY*10;
        // color
        drawRect(x,y,g, myColor);
        g.dispose();
        myView.repaint();
        //

        for (int loop=0; loop<3; loop++) {
            // 3 loops
            myRandomDirection = (int)(Math.random()*100) % directions.length;
            if (directions[myRandomDirection] == 0) {   // left
                // check if direction is possible (in Boudries)
                if (startX - 1 > 0) {
                    if (!(wholeField[startX-1][startY])) {
                        if (myColor.getRed()-myR < 0 || myColor.getRed()-myR > 255) {
                            myR = myR*-1;
                        }
                        myColor = new Color (myColor.getRed()-myR, myColor.getGreen(), myColor.getBlue());
                        doDFS(startX-1,startY,myRandomDirection, wholeField, myColor, myR, myG, myB);
                    } else {
                        System.out.println("Field is Filled :  x = " + startX + "   y = " + startY);
                    }
                }
            } else if  (directions[myRandomDirection] == 1) {   // up
                // check if direction is possible (in Boudries)
                if (startY - 1 > 0) {
                    if (!(wholeField[startX][startY-1])) {
                        if (myColor.getGreen()-myG < 0 || myColor.getGreen()-myG > 255) {
                            myG = myG*-1;
                        }
                        myColor = new Color (myColor.getRed(), myColor.getGreen()-myG, myColor.getBlue());
                        doDFS(startX,startY-1,myRandomDirection, wholeField, myColor, myR, myG, myB);
                    } else {
                        System.out.println("Field is Filled :  x = " + startX + "   y = " + startY);
                    }
                }
            } else if  (directions[myRandomDirection] == 2) {   // right
                // check if direction is possible (in Boudries)
                if (startX + 1 < wholeField.length ) {
                    if (!(wholeField[startX+1][startY])) {
                        if (myColor.getBlue()-myB < 0 || myColor.getBlue()-myB > 255) {
                            myB = myB*-1;
                        }
                        myColor = new Color (myColor.getRed(), myColor.getGreen(), myColor.getBlue()-myB);
                        doDFS(startX+1,startY,myRandomDirection, wholeField, myColor, myR, myG, myB);
                    } else {
                        System.out.println("Field is Filled :  x = " + startX + "   y = " + startY);
                    }
                }
            } else if  (directions[myRandomDirection] == 3) {   // down
                // check if direction is possible (in Boudries)
                if (startY + 1 < wholeField.length) {
                    if (!(wholeField[startX][startY+1])) {
                        doDFS(startX,startY+1,myRandomDirection, wholeField, myColor, myR, myG, myB);
                    }
                } else {
                    System.out.println("Field is Filled :  x = " + startX + "   y = " + startY);
                }
            }

            // shift
            if (directions.length>1) {
                int directions_2 [] = new int [directions.length-1];
                int i2 = 0;
                for (int i = 0; i<directions_2.length; i++) {
                    if (i == myRandomDirection) {
                        i2++;
                    }
                    directions_2[i] = directions[i+i2];
                }
                directions = directions_2;
            }
        }
    }

    public int [] shuffleArray(int[] array) {
        ArrayList<Integer> solution = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            solution.add(array[i]);
        }
        Collections.shuffle(solution);
        int b = 0;
        for (int i:solution) {
            array[b] = i;
            b++;
        }
        return array;
    }


    public void addNewElement() {
        boolean drawArc = random.nextBoolean();
        int x = random.nextInt(400);
        int y = random.nextInt(400);
        Graphics g = mySurface.getGraphics();
        drawRect(x,y,g, new Color(255,255,255));

        g.dispose();
        myView.repaint();
    }

    public static void main(String[] args)
    {

        // Dimension of canvas
        int myWidth = 400;
        int myHeight = 400;

        MyCanvas canvas = new MyCanvas(myWidth,myHeight);
        JFrame frame = new JFrame();
        int vertexes = 0;
        // Change this next part later to be dynamic.
        vertexes = 10;
        int canvasSize = vertexes * vertexes;
        frame.setSize(canvasSize, canvasSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(canvas.myView);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);


        // boolean [] [] field  = new boolean [(int)(myWidth*0.1)] [(int)(myHeight*0.1)];
        boolean [] [] field  = new boolean [40] [40];
        for (boolean [] f : field) {
            for (boolean b: f) {
                b = false;
            }
        }
        int myStartX = (int)((myWidth*0.1)/2);
        int myStartY = (int)((myHeight*0.1)/2);
        int myRandomDirection = (int)(Math.random()*100) % 4;

        Color myColor = new Color(255, 255, 255); // Color white
        int myR = 15;
        int myG = 15;
        int myB = 15;

        // DFS Paint
        canvas.doDFS(myStartX,myStartY,myRandomDirection, field, myColor, myR, myG, myB);
    }

    public static void drawRect(int x, int y, Graphics g, Color myColor)
    {
        // (x,y) = upper left corner
        int xLoc = x;
        int yLoc = y;
        g.setColor(myColor);
        g.fillRect(xLoc, yLoc, 10, 10);
        g.drawRect(xLoc, yLoc, 10, 10);
    }

}