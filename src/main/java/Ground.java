import javax.swing.*;

public class Ground extends JFrame {
    public MyPosition[][] positions=new MyPosition[20][20];
    Ground(){
        for(int i=0;i<20;i++)
            for(int j=0;j<20;j++)
            {
                positions[i][j]=new MyPosition();
            }

    }
}
