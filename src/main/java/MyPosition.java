import java.util.Random;
import javax.swing.*;
import java.awt.*;

class Location{
    private int x;
    private int y;
    public Location(int x,int y)
    {
        this.x=x;
        this.y=y;
    }

    public Location()
    {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}


class Creature implements Runnable{
    public MyPosition[][] positions;
    public Ground ground;
    protected Image image;
    Location loc=new Location();
    boolean is_alive;
    boolean is_evil;
    public boolean has_moved;
    private COLOR color;
    public COLOR getColor()
    {
        return color;
    }
    public void setColor(COLOR co)
    {
        color=co;
    }

    public Location getLoc(){
        return loc;
    }

    public void setLoc(Location nloc){
        loc=nloc;
    }
    public Image getImage() {
        return this.image;
    }

    public void search_for_enemy(){
        System.out.println("fight");
           Random rand=new Random();
           boolean win=rand.nextBoolean();
           Location my=getLoc();
           int qx=my.getX()-2;
           if(qx<0)
               qx=0;
           int qy= my.getY()-2;
           if(qy<0)
               qy=0;
           for(int i=qx;i<my.getX()+2&&i<20;i++)
               for(int j=qy;j<my.getY()+2&&j<20;j++){
                    if(positions[i][j].isHolderNull())
                        continue;
                    else if(positions[i][j].ReturnHolder().is_alive!=true)
                        continue;
                    else if(positions[i][j].ReturnHolder().is_evil!=this.is_evil){
                        System.out.println("打起来了");
                        if(win){
                            positions[i][j].ReturnHolder().is_alive=false;
                            return;
                        }
                        else{
                            positions[my.getX()][my.getY()].ReturnHolder().is_alive=false;
                            return;
                        }
                    }

               }
    }


    public boolean is_there_empty(int x,int y){
        if(positions[x][y].isHolderNull()){
            return true;
        }
        else{
            return false;
        }
    }

    Creature(MyPosition[][] positions,Ground ground){
        this.positions=positions;
        this.ground=ground;
        this.is_alive=true;
    }

    public void move(){
        if(is_alive==false)
            return;
        else if(is_evil){
            System.out.println("坏人准备移动");
            Location rn=getLoc();
            int x=rn.getX();
            int rnx=x;
            int y=rn.getY();
            int rny=y;
            if(x>8)
            {
                if(is_there_empty(x-1,y))
                {
                    x=x-1;
                    positions[rnx][rny].ClearHolder();
                    rn.setX(x);
                    setLoc(rn);
                    positions[x][y].setHolder(this,x,y);
                    System.out.println("坏人移动了");
                    return;
                }
                else if(is_there_empty(x-2,y))
                {
                    x=x-2;
                    positions[rnx][rny].ClearHolder();
                    rn.setX(x);
                    setLoc(rn);
                    positions[x][y].setHolder(this,x,y);
                    System.out.println("坏人移动了");
                    return;
                }


            }
            else if(y>10){
                if(is_there_empty(x,y-1))
                {
                    y=y-1;
                    rn.setX(x);
                    setLoc(rn);
                    positions[rnx][rny].ClearHolder();
                    positions[x][y].setHolder(this,x,y);
                    return;
                }
                if(is_there_empty(x,y-2))
                {
                    y=y-2;
                    rn.setX(x);
                    setLoc(rn);
                    positions[rnx][rny].ClearHolder();
                    positions[x][y].setHolder(this,x,y);
                    return;
                }
            }
            else if(y<10){
                if(is_there_empty(x,y+1))
                {
                    y=y+1;
                    rn.setX(x);
                    setLoc(rn);
                    positions[rnx][rny].ClearHolder();
                    positions[x][y].setHolder(this,x,y);
                    return;
                }
            }
            else if(x<8){
                if(is_there_empty(x+1,y))
                {
                    x=x+1;
                    rn.setX(x);
                    setLoc(rn);
                    positions[rnx][rny].ClearHolder();
                    positions[x][y].setHolder(this,x,y);
                    return;
                }
            }
            else{
                if(positions[x+1][y].ReturnHolder().is_alive==false&&positions[x][y+1].ReturnHolder().is_alive==false) {
                    Random fly = new Random();
                    int flying = fly.nextInt(10);
                    if (flying % 2 == 0 && is_there_empty(2, 6)) {
                        x = 2;
                        y = 6;
                        positions[rnx][rny].ClearHolder();
                        positions[x][y].setHolder(this, x, y);
                        return;
                    }
                }
                if(positions[x+1][y].ReturnHolder().is_alive==false&&positions[x][y-1].ReturnHolder().is_alive==false){
                    Random fly=new Random();
                    boolean flying=fly.nextBoolean();
                    if(flying&&is_there_empty(2,6)){
                        x=2;
                        y=6;
                        positions[rnx][rny].ClearHolder();
                        positions[x][y].setHolder(this,x,y);
                        return;
                    }

                }
                }
            }
        else{
            Location rn=getLoc();
            int x=rn.getX();
            int y=rn.getY();
            int rnx=x;
            int rny=y;
            if(x<10)
            {
                if(is_there_empty(x+1,y))
                {
                    x=x+1;
                    rn.setX(x);
                    setLoc(rn);
                    positions[rnx][rny].ClearHolder();
                    positions[x][y].setHolder(this,x,y);
                    return;
                }
                if(is_there_empty(x+1,y))
                {
                    x=x+1;
                    rn.setX(x);
                    setLoc(rn);
                    positions[rnx][rny].ClearHolder();
                    positions[x][y].setHolder(this,x,y);
                    System.out.println("坏人移动了");
                    return;
                }
            }
            else if(x>10){
                if(is_there_empty(x-1,y))
                {
                    x=x-1;
                    rn.setX(x);
                    setLoc(rn);
                    positions[rnx][rny].ClearHolder();
                    positions[x][y].setHolder(this,x,y);
                    return;
                }
            }
            if(y>10){
                if(is_there_empty(x,y-1))
                {
                    y=y-1;
                    rn.setX(x);
                    setLoc(rn);
                    positions[rnx][rny].ClearHolder();
                    positions[x][y].setHolder(this,x,y);
                    return;
                }
            }
            else if(y<10){
                if(is_there_empty(x,y+1))
                {
                    y=y+1;
                    rn.setX(x);
                    setLoc(rn);
                    positions[rnx][rny].ClearHolder();
                    positions[x][y].setHolder(this,x,y);
                    return;
                }
            }
            else{
                if(positions[x+1][y].ReturnHolder().is_alive==false&&positions[x][y+1].ReturnHolder().is_alive==false) {
                    Random fly = new Random();
                    int flying = fly.nextInt(10);
                    if (flying % 2 == 0 && is_there_empty(12, 6)) {
                        x = 12;
                        y = 6;
                        positions[rnx][rny].ClearHolder();
                        positions[x][y].setHolder(this, x, y);
                        return;
                    }
                }
                if(positions[x+1][y].ReturnHolder().is_alive==false&&positions[x][y-1].ReturnHolder().is_alive==false){
                        Random fly=new Random();
                        boolean flying=fly.nextBoolean();
                        if(flying&&is_there_empty(12,6)){
                            x=12;
                            y=6;
                            positions[rnx][rny].ClearHolder();
                            positions[x][y].setHolder(this,x,y);
                            return;
                        }

                }
            }
        }
    }

    public boolean define_evilwin(){
        boolean a=true;
        for(int i=0;i<20;i++)
            for(int j=0;j<20;j++)
            {
                if(positions[i][j].isHolderNull())
                    continue;
                else{
                    if(positions[i][j].ReturnHolder().is_evil==false){
                        a=a&(!positions[i][j].ReturnHolder().is_alive);
                    }
                }
            }
        return a;
    }

    public boolean define_goodwin(){
        boolean a=true;
        for(int i=0;i<20;i++)
            for(int j=0;j<20;j++)
            {
                if(positions[i][j].isHolderNull())
                    continue;
                else{
                    if(positions[i][j].ReturnHolder().is_evil==true){
                        a=a&(!positions[i][j].ReturnHolder().is_alive);
                    }
                }
            }
        return a;
    }

    public void run() {
        boolean is_end=false;
        while (!is_end&&is_alive&&!Thread.interrupted()) {
            if(define_evilwin())
                is_end=true;
            else if(define_goodwin())
                is_end=true;
            move();
            ground.repaint();
            try{
                Thread.sleep(3000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            has_moved = true;
            try {
                search_for_enemy();
                ground.repaint();
                Thread.sleep(2000);
                has_moved=false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    void report_status()
    {

    }

}

class QueenSnake extends Creature
{
    public QueenSnake(MyPosition[][] positions,Ground ground)
    {
        super(positions,ground);
        this.is_evil=true;
        this.setColor(COLOR.GREY);
        ImageIcon iia=new ImageIcon("./src/main/resources/snack.png");
        image=iia.getImage();
    }
    public void report_status()
    {

        System.out.print("蛇 精   ");
    }
}

class Grandpa extends Creature
{
    public Grandpa(MyPosition[][] positions,Ground ground)
    {
        super(positions,ground);
        setColor(COLOR.WHITE);
        ImageIcon iia=new ImageIcon("./src/main/resources/grandpa.png");
        image=iia.getImage();
        is_evil=false;
    }
    public void report_status()
    {
        System.out.print("爷 爷   ");

    }
}

class Huluwa extends Creature{
    public String name;
    public int seniority;
    Huluwa(COLOR color,MyPosition[][] positions,Ground ground)
    {
        super(positions,ground);
        is_evil=false;
        this.setColor(color);
        switch(color)
        {
            case CHI:
            {
                name="老大";
                seniority=1;
                ImageIcon iia=new ImageIcon("./src/main/resources/CHI.png");
                image=iia.getImage();
            }
            break;
            case CHENG:
            {
                name="老二";
                seniority=2;
                ImageIcon iia=new ImageIcon("./src/main/resources/CHENG.png");
                image=iia.getImage();
            }
            break;
            case HUANG:
            {
                name="老三";
                seniority=3;
                ImageIcon iia=new ImageIcon("./src/main/resources/HUANG.png");
                image=iia.getImage();
            }
            break;
            case LV:
            {
                name="老四";
                seniority=4;
                ImageIcon iia=new ImageIcon("./src/main/resources/LV.png");
                image=iia.getImage();
            }
            break;
            case QING:
            {
                name="老五";
                seniority=5;
                ImageIcon iia=new ImageIcon("./src/main/resources/QING.png");
                image=iia.getImage();
            }
            break;
            case LAN:
            {
                name="老六";
                seniority=6;
                ImageIcon iia=new ImageIcon("./src/main/resources/LAN.png");
                image=iia.getImage();
            }
            break;
            case ZI:
            {
                name="老七";
                seniority=7;
                ImageIcon iia=new ImageIcon("./src/main/resources/zi.png");
                image=iia.getImage();
            }

        }
    }
    public void report_status()
    {
        if(seniority==1)
        {
            System.out.print("老 大   ");

        }
        else if(seniority==2)
        {
            System.out.print("老 二   ");

        }
        else if(seniority==3)
        {
            System.out.print("老 三   ");

        }
        else if(seniority==4)
        {
            System.out.print("老 四   ");

        }
        else if(seniority==5)
        {
            System.out.print("老 五   ");

        }
        else if(seniority==6)
        {
            System.out.print("老 六   ");

        }
        else if(seniority==7)
        {
            System.out.print("老 七   ");

        }
    }
}

class Minoin extends Creature{
    public String name;
    public void report_status()
    {
        System.out.print("小喽啰  ");

    }
    Minoin(MyPosition[][] positions,Ground ground)
    {
        super(positions,ground);
        is_evil=true;
        this.setColor(COLOR.BLACK);
        ImageIcon iia=new ImageIcon("./src/main/resources/MINION.png");
        image=iia.getImage();
    }
}

enum COLOR{
    CHI,CHENG,HUANG,LV,QING,LAN,ZI,BLACK,WHITE,GREY;
}

public class MyPosition<T extends Creature>{
    private int index_x;
    private int index_y;
    private T holder;

    public void setHolder(T t,int x,int y)
    {
        index_x=x;
        index_y=y;
        holder=t;
        holder.loc.setX(index_x);
        holder.loc.setY(index_y);
    }
    public boolean isHolderNull()
    {
        if(holder==null)
            return true;
        else
            return false;
    }
    public T ReturnHolder()
    {
        return holder;
    }
    public void ClearHolder()
    {
        holder=null;
    }
}