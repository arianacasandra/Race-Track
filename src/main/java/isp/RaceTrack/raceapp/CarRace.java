package isp.RaceTrack.raceapp;
import java.awt.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class CarRace {
    public static void main(String[] args) {

    }

}

class Car extends Thread {
    private String name;
    private int distance = 0;
    private CarPanel carPanel;
    private SemaphoreThread semaphoreThread;
    private PlaySound sound=new PlaySound();
    private RankingPanel rankingPanel;
    private JFrame frame;

    public Car(String name, CarPanel carPanel,SemaphoreThread semaphoreThread,RankingPanel panel,JFrame frame) {
        //set thread name;
        setName(name);
        this.name = name;
        this.carPanel = carPanel;
        this.semaphoreThread = semaphoreThread;
        this.rankingPanel=panel;
        this.frame=frame;
    }

    public void run()
    {
        if(semaphoreThread!=null)
        {
            try {
                semaphoreThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        sound.playSound();
        int time=0;
        while (distance < 400) {
            // simulate the car moving at a random speed
            int speed = (int) (Math.random() * 10) + 1;
            distance += speed;

            carPanel.updateCarPosition(name, distance);

            try {
                // pause for a moment to simulate the passage of time
                Thread.sleep(100);
                time++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sound.stopSound();
        carPanel.saveRanking(name,time);
        carPanel.carFinished(name);
        rankingPanel.setRank(carPanel.getRanking());
        rankingPanel.setTime(carPanel.getTimeFinished());
        frame.setVisible(true);
    }
}

class CarPanel extends JPanel {
    private int[] carPositions;
    private String[] carNames;
    private Color[] carColors;
    private int[] carRanking;
    private int[] timeFinished;
    static int pos;

    public CarPanel() {
        carPositions = new int[4];
        carNames = new String[]{"Car", "Car2", "Car3", "Car4"};
        carColors = new Color[]{Color.RED, Color.BLUE,Color.GREEN, Color.YELLOW};
        carRanking=new int[4];
        timeFinished=new int[4];
        pos=0;
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

    public void saveRanking(String CarName,int time)
    {
        int carIndex=getCarIndex(CarName);
        if(carIndex!=-1)
        {
            carRanking[carIndex]=pos;
            timeFinished[carIndex]=time;
            pos++;
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

    public int[] getRanking()
    {
        return carRanking;
    }

    public int[] getTimeFinished()
    {
        return timeFinished;
    }
}
