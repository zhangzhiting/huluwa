import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.FileDialog;
import java.awt.event.KeyListener;
import java.io.*;
import javax.swing.Timer;

public class Field extends JPanel{
    public MyPosition[][] positions;
    public Ground ground;
    Field(MyPosition[][] positions,Ground ground){
        this.positions=positions;
        this.ground=ground;
        addKeyListener(new myAdapter());
        setFocusable(true);
    }
    public void InitUI(Graphics g){
        g.setColor(Color.green);
        g.fillRect(0,0,1050,700);

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                g.setColor(Color.PINK);
                g.fillRect(30*(4+i),30*j,25,25);
            }
        }
    }

    public void showmap(Graphics g)
    {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if(positions[i][j].isHolderNull()==false) {
                    if(positions[i][j].ReturnHolder().is_alive) {
                        Image image = positions[i][j].ReturnHolder().getImage();
                        g.drawImage(image, 30 * (i + 4), 30 * j, 25, 25, this);
                    }
                    else
                    {
                        ImageIcon aii=new ImageIcon("./src/main/resources/dead.png");
                        Image image=aii.getImage();
                        g.drawImage(image,30*(i+4),30*j,25,25,this);
                    }
                }
            }
        }
    }

    class PaintBack implements Runnable{
        String fn;

        public void run(){
            File file=new File(fn);
            BufferedReader br=null;

            try{
                try{
                    br=new BufferedReader(new FileReader(file));
                    String tempString=null;
                    int line=1;
                    while((tempString=br.readLine())!=null){
                        System.out.println("line"+line+":"+tempString);
                        for(int i=0;i<tempString.length();i++){
                            char item=tempString.charAt(i);
                            if(item=='0'){
                                positions[line-1][i].ClearHolder();
                            }
                            else if(item=='d'){
                                positions[line-1][i].setHolder(new Minoin(positions,ground),line-1,i);
                                positions[line-1][i].ReturnHolder().is_alive=false;
                            }
                            else if(item=='1'){
                                positions[line-1][i].setHolder(new Huluwa(COLOR.CHI,positions,ground),line-1,i);
                            }
                            else if(item=='2'){
                                positions[line-1][i].setHolder(new Huluwa(COLOR.CHENG,positions,ground),line-1,i);
                            }
                            else if(item=='3'){
                                positions[line-1][i].setHolder(new Huluwa(COLOR.HUANG,positions,ground),line-1,i);
                            }
                            else if(item=='4'){
                                positions[line-1][i].setHolder(new Huluwa(COLOR.LV,positions,ground),line-1,i);
                            }
                            else if(item=='5'){
                                positions[line-1][i].setHolder(new Huluwa(COLOR.QING,positions,ground),line-1,i);
                            }
                            else if(item=='6'){
                              positions[line-1][i].setHolder(new Huluwa(COLOR.LAN,positions,ground),line-1,i);
                            }
                            else if(item=='7'){
                                positions[line-1][i].setHolder(new Huluwa(COLOR.ZI,positions,ground),line-1,i);
                            }
                            else if(item=='g'){
                                positions[line-1][i].setHolder(new Grandpa(positions,ground),line-1,i);
                            }
                            else if(item=='s'){
                               positions[line-1][i].setHolder(new QueenSnake(positions,ground),line-1,i);
                            }
                            else if(item=='m'){
                                positions[line-1][i].setHolder(new Minoin(positions,ground),line-1,i);
                            }

                        }
                        line++;
                        if(line==21){
                            line=1;
                            repaint();
                            Thread.sleep(6000);
                            System.out.println("休息了十秒");
                            for(int i1=0;i1<20;i1++)
                                for(int i2=0;i2<20;i2++){
                                    positions[i1][i2].ClearHolder();
                                }
                        }
                    }

                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }


        PaintBack(String filename)
        {
            this.fn=filename;
        }
    }
    class myAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int key=e.getKeyCode();
            if(key==KeyEvent.VK_SPACE){
                Battle battle=new Battle(positions,ground);
                System.out.println("battle start");
            }
            else if(key==KeyEvent.VK_L){
                FileDialog mydialog=new FileDialog(ground,"open",FileDialog.LOAD);
                mydialog.setVisible(true);
                String filename=mydialog.getDirectory()+mydialog.getFile();
                PaintBack pg=new PaintBack(filename);
                new Thread(pg).start();
            }
        }
    }

    public void paint(Graphics g){
        InitUI(g);
        showmap(g);
    }
}
