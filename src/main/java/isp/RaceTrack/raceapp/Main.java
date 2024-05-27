package isp.RaceTrack.raceapp;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Race!");
        JFrame frame = new JFrame("Car Race");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,300);
        frame.setVisible(true);
        JFrame frame2=new JFrame("Ranking");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(600,600);
        frame2.setVisible(false);


        CarPanel carPanel = new CarPanel();
        SemaphorePanel semaphorePanel = new SemaphorePanel();
        RankingPanel rankingPanel=new RankingPanel();

        semaphorePanel.setBounds(0,0,100,300);
        carPanel.setBounds(150,0,500,300);
        rankingPanel.setBounds(0,0,500,500);
        frame.add(carPanel);
        frame.add(semaphorePanel);
        frame2.add(rankingPanel);


        SemaphoreThread semaphoreThread = new SemaphoreThread(semaphorePanel);
        Car car1 = new Car("Car", carPanel, semaphoreThread,rankingPanel,frame2);
        Car car2 = new Car("Car2", carPanel, semaphoreThread,rankingPanel,frame2);
        Car car3 = new Car("Car3", carPanel, semaphoreThread,rankingPanel,frame2);
        Car car4 = new Car("Car4", carPanel, semaphoreThread,rankingPanel,frame2);
        Ranking rank=new Ranking(rankingPanel,car1,car2,car3,car4,carPanel,semaphoreThread);

        semaphoreThread.start();
        car1.start();
        car2.start();
        car3.start();
        car4.start();
        rank.start();
    }
}
