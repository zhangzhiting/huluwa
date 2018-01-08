import java.io.*;
import java.util.TimerTask;
import java.util.Timer;

public class Battle {
    public MyPosition[][] positions;
    public Ground ground;
    public static int battlecount=0;
    Battle(final MyPosition[][] positions, Ground ground) {
        battlecount++;
        this.positions = positions;
        this.ground = ground;
        Grandpa grandpa = new Grandpa(positions, ground);
        QueenSnake qs = new QueenSnake(positions, ground);
        Huluwa[] huluwas;
        huluwas = new Huluwa[7];
        huluwas[0] = new Huluwa(COLOR.CHI, positions, ground);
        huluwas[1] = new Huluwa(COLOR.CHENG, positions, ground);
        huluwas[2] = new Huluwa(COLOR.HUANG, positions, ground);
        huluwas[3] = new Huluwa(COLOR.LV, positions, ground);
        huluwas[4] = new Huluwa(COLOR.QING, positions, ground);
        huluwas[5] = new Huluwa(COLOR.LAN, positions, ground);
        huluwas[6] = new Huluwa(COLOR.ZI, positions, ground);
        positions[0][10].setHolder(grandpa, 0, 10);
        positions[19][10].setHolder(qs, 19, 10);
        positions[2][4].setHolder(huluwas[0], 2, 4);
        positions[2][6].setHolder(huluwas[1], 2, 6);
        positions[2][8].setHolder(huluwas[2], 2, 8);
        positions[2][10].setHolder(huluwas[3], 2, 10);
        positions[2][12].setHolder(huluwas[4], 2, 12);
        positions[2][14].setHolder(huluwas[5], 2, 14);
        positions[2][16].setHolder(huluwas[6], 2, 16);
        Minoin[] minions = new Minoin[7];
        minions[0] = new Minoin(positions, ground);
        minions[1] = new Minoin(positions, ground);
        minions[2] = new Minoin(positions, ground);
        minions[3] = new Minoin(positions, ground);
        minions[4] = new Minoin(positions, ground);
        minions[5] = new Minoin(positions, ground);
        minions[6] = new Minoin(positions, ground);
        positions[12][4].setHolder(minions[1], 12, 4);
        positions[12][16].setHolder(minions[2], 12, 16);
        positions[14][6].setHolder(minions[3], 14, 6);
        positions[14][14].setHolder(minions[4], 14, 14);
        positions[16][8].setHolder(minions[5], 16, 8);
        positions[16][12].setHolder(minions[6], 16, 12);
        positions[18][10].setHolder(minions[0], 18, 10);
        final Thread[] allthread = new Thread[16];
        for (int i = 0; i < 7; i++) {
            allthread[i] = new Thread(huluwas[i]);
            allthread[i + 7] = new Thread(minions[i]);

        }
        allthread[14] = new Thread(grandpa);
        allthread[15] = new Thread(qs);

        for (int i = 0; i < 16; i++) {
            allthread[i].start();
        }

        TimerTask judgewin = new TimerTask() {
            @Override
            public void run() {
              System.out.println("判断比赛胜负");
                if (define_evilwin()) {
                    for (int i = 0; i < 16; i++) {
                        allthread[i].interrupt();
                    }
                    System.out.println("over,yaoguai win");
                    for(int i=0;i<20;i++){
                        for(int j=0;j<20;j++){
                            positions[i][j].ClearHolder();
                        }
                    }
                } else if (define_goodwin()) {
                    for (int i = 0; i < 16; i++) {
                        allthread[i].interrupt();
                    }
                    System.out.println("over,huluwa win");
                    Thread.interrupted();
                    for(int i=0;i<20;i++){
                        for(int j=0;j<20;j++){
                            positions[i][j].ClearHolder();
                        }
                    }
                }
            }
        };
        TimerTask save_battle=new TimerTask() {
            @Override
            public void run() {
                String fn="battle"+battlecount+".txt";
                File file=new File(fn);
               try{
                   FileWriter fw=new FileWriter(file,true);
                   BufferedWriter bw=new BufferedWriter(fw);
                   for(int i=0;i<20;i++)
                   {
                       String savestr=new String();
                       for(int j=0;j<20;j++)
                       {
                           if(positions[i][j].isHolderNull())
                           {
                               savestr=savestr+"0";
                           }
                           else if(positions[i][j].ReturnHolder().is_alive==false)
                           {
                               savestr=savestr+"d";
                           }
                           else if(positions[i][j].ReturnHolder().getColor()==COLOR.CHI)
                           {
                               savestr=savestr+"1";
                           }
                           else if(positions[i][j].ReturnHolder().getColor()==COLOR.CHENG)
                           savestr=savestr+"2";
                           else if(positions[i][j].ReturnHolder().getColor()==COLOR.HUANG)
                           savestr=savestr+"3";
                           else if(positions[i][j].ReturnHolder().getColor()==COLOR.LV)
                           savestr=savestr+"4";
                           else if(positions[i][j].ReturnHolder().getColor()==COLOR.QING)
                           savestr=savestr+"5";
                           else if(positions[i][j].ReturnHolder().getColor()==COLOR.LAN)
                           savestr=savestr+"6";
                           else if(positions[i][j].ReturnHolder().getColor()==COLOR.ZI)
                           savestr=savestr+"7";
                           else if(positions[i][j].ReturnHolder().getColor()==COLOR.WHITE)
                           savestr=savestr+"g";
                           else if(positions[i][j].ReturnHolder().getColor()==COLOR.BLACK)
                           savestr=savestr+"m";
                           else if(positions[i][j].ReturnHolder().getColor()==COLOR.GREY)
                           savestr=savestr+"s";
                       }
                       savestr=savestr;
                       bw.write(savestr);
                       bw.newLine();
                       bw.flush();
                   }
               }catch(IOException e){
                   e.printStackTrace();
               }
            }
        };
        Timer timer=new Timer();
        timer.schedule(judgewin,1000,4000);
        Timer save_timer=new Timer();
        timer.schedule(save_battle,10,5000);
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

}
