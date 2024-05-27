package isp.RaceTrack.racedemo;
import java.awt.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class CarRace {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Car Race");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CarPanel carPanel = new CarPanel();

        frame.getContentPane().add(carPanel);
        frame.pack();
        frame.setSize(500,300);
        frame.setVisible(true);

        JFrame frame2 = new JFrame("Semaphore");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SemaphorePanel semaphorePanel = new SemaphorePanel();

        frame.getContentPane().add(semaphorePanel);
        frame.pack();
        frame.setVisible(true);

        SemaphoreThread semaphoreThread = new SemaphoreThread(semaphorePanel);

        Car car1 = new Car("Red car", carPanel,semaphoreThread);
        Car car2 = new Car("Blue car", carPanel,semaphoreThread);
        Car car3 = new Car("Green car", carPanel,semaphoreThread);
        Car car4 = new Car("Yellow car", carPanel,semaphoreThread);

        semaphoreThread.start();
        try {
            semaphoreThread.join();
        }
        catch(InterruptedException e)
        {
            e.getMessage();
        }
        car1.start();
        car2.start();
        car3.start();
        car4.start();
    }
    
}

class Car extends Thread {
    private String name;
    private int distance = 0;
    private CarPanel carPanel;

    private SemaphoreThread semaphoreThread;

    public Car(String name, CarPanel carPanel,SemaphoreThread semaphoreThread) {
        //set thread name;
        setName(name);
        this.name = name;
        this.carPanel = carPanel;
        this.semaphoreThread = semaphoreThread;
    }

    public void run() {
        while (distance < 400) {
            // simulate the car moving at a random speed
            int speed = (int) (Math.random() * 10) + 1;
            distance += speed;

            carPanel.updateCarPosition(name, distance);

            try {
                // pause for a moment to simulate the passage of time
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        carPanel.carFinished(name);
    }
}

class CarPanel extends JPanel {
    private int[] carPositions;
    private String[] carNames;
    private Color[] carColors;

    public CarPanel() {
        carPositions = new int[4];
        carNames = new String[]{"Carlitos", "Karitas", "Masinitili", "Volkswagen"};
        carColors = new Color[]{Color.BLUE, Color.CYAN,Color.PINK, Color.MAGENTA};
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < 4; i++) {
            int yPos = 50 + i * 50; // Vertical position of the car
            int xPos = carPositions[i]; // Horizontal position of the car
            int carSize = 30; // Size of the car

            g.setColor(carColors[i]);
            g.fillOval(xPos, yPos, carSize, carSize);
            g.setColor(Color.BLACK);
            g.drawString(carNames[i], xPos, yPos - 5);
        }
    }

    public void updateCarPosition(String carName, int distance) {
        int carIndex = getCarIndex(carName);
        if (carIndex != -1) {
            carPositions[carIndex] = distance;
            repaint();
        }
    }

    public void carFinished(String carName) {
        System.out.println("Car finished race.");
    }

    private int getCarIndex(String carName) {
        for (int i = 0; i < 4; i++) {
            if (carNames[i].equals(carName)) {
                return i;
            }
        }
        return -1;
    }
}
