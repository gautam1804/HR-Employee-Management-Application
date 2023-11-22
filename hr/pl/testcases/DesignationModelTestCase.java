import java.awt.*;
import javax.swing.*;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.exceptions.*;

class DesignationModelTestCase extends JFrame
{
private JTable tb;
private DesignationModel designationModel;
private Container container;
private JScrollPane jsp;

DesignationModelTestCase()
{
designationModel=new DesignationModel();
tb=new JTable(designationModel);
jsp=new JScrollPane(tb,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

container=getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);   
setLocation(100,200);
setSize(600,600);
setVisible(true);
}


public static void main(String[] gg)
{
DesignationModelTestCase dmtc=new DesignationModelTestCase();
}}