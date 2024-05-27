package isp.RaceTrack.raceapp;

import javax.swing.*;
import java.awt.*;

public class Ranking extends Thread
{
    private RankingPanel panel;
    private CarPanel panel2;
    private SemaphoreThread semaphoreThread;
    private Car car1;
    private Car car2;
    private Car car3;
    private Car car4;
    Ranking(RankingPanel panel,Car car1,Car car2,Car car3,Car car4,CarPanel panelus,SemaphoreThread thread)
    {
        this.panel=panel;
        this.car1=car1;
        this.car2=car2;
        this.car3=car3;
        this.car4=car4;
        panel2=panelus;
        semaphoreThread=thread;
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
        int time=0;
        while(car1.isAlive() || car2.isAlive() || car3.isAlive() ||car4.isAlive())
        {
            try {
                Thread.sleep(10);
                time++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        panel.setFinaltime(time);
    }
}
class RankingPanel extends JPanel
{
    Color[] colors;
    String[] names;
    int[] rank;
    int[] time;
    int finaltime;
    RankingPanel()
    {
        names=new String[]{"Car1", "Car2", "Car3", "Car4"};
        colors = new Color[]{Color.RED, Color.BLUE,Color.GREEN, Color.YELLOW};
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int xwin=120;
        int ywin=250;
        int sz=30;
        for(int i=0;i<4;i++)
        {
            g.setColor(colors[i]);
            g.fillOval(xwin+i*100,ywin,sz,sz);
            try {
                String ranks = rankstring(rank[i]);
                String times= timestring(time[i]);
                g.drawString("Place:"+ranks,xwin+i*100-5,ywin+50);
                g.drawString("Time:"+times,xwin+i*100-5,ywin+100);
            }
            catch(NullPointerException e)
            {
                e.getMessage();
            }
        }
        g.setColor(Color.BLACK);
        g.drawString(this.getFinaltime(),250,400);
    }

    public void setRank(int[] Rank)
    {
        rank=Rank;
        repaint();
    }

    public void setTime(int[] Time)
    {
        time=Time;
        repaint();
    }

    public void setFinaltime(int finaltime) {
        this.finaltime = finaltime;
        repaint();
    }

    public String getFinaltime() {
        return "Total time:"+finaltime;
    }

    public String rankstring(int i)
    {
        if(i==0)
            return "1";
        else
            if(i==1)
                return "2";
            else
                if(i==2)
                    return "3";
                else
                    if(i==3)
                        return "4";
        return "0";
    }

    public String timestring(int i)
    {
        for(int j=0;j<=100;j++)
        {
            if(i==j)
                return i+"ms";
        }
        return "0";
    }

}
