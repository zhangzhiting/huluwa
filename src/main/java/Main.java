import javax.swing.*;

public class Main{
    public Ground ground;
    public static void main(String[] args){
        Ground ground=new Ground();
        Field myfield=new Field(ground.positions,ground);
        ground.add(myfield);
        ground.setTitle("Huluwa");
        ground.setSize(1050,700);
        ground.setVisible(true);

        ground.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
