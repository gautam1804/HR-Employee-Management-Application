import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
import javax.swing.table.*;

class DesignationTModel extends AbstractTableModel
{
private String title[];
private DesignationManagerInterface designationManager;
private Set<DesignationInterface> designations;
private Object data[][];
private String sortArr[];

DesignationTModel()
{
populateDataStructure();
}

public int getRowCount()
{
return designations.size();
}
public int getColumnCount()
{
return title.length;
}
public String getColumnName(int columnIndex)
{
return title[columnIndex];
}

public Object getValueAt(int rowIndex,int columnIndex)
{
return data[rowIndex][columnIndex];
}

public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
public Class getColumnClass(int columnIndex)
{
Class c=null;
try{
if(columnIndex==0)
{
return c=Class.forName("java.lang.Integer");
}
if(columnIndex==1)
{
return c=Class.forName("java.lang.String");
}
}catch(Exception e)
{
System.out.println(e);
}
return c;
}
private void populateDataStructure()
{
title=new String[2];
title[0]="S.No.";
title[1]="Designation";

try
{
designationManager=DesignationManager.getDesignationManager();
designations=designationManager.getDesignations();
data=new Object[5][designations.size()];
sortArr=new String[designations.size()];

int i=0;
for(DesignationInterface designation : designations)
{
sortArr[i]=designation.getTitle();
i++;
}
for(int u = 0; u<sortArr.length; u++)   
{  
for (int j = u+1; j<sortArr.length; j++)  
{  
if(sortArr[u].compareTo(sortArr[j])>0)   
{  
String temp = sortArr[u];  
sortArr[u] = sortArr[j];  
sortArr[j] = temp;  
}  
}  
}

for(int t=0;t<sortArr.length;t++)
{
data[t][0]=1+t;
data[t][1]=sortArr[t];
}
}catch(BLException blException)
{
 java.util.List<String> properties=blException.getProperties();
properties.forEach((property)->{
System.out.println(blException.getException(property));
});
}
}
}

class presentation extends JFrame
{
private JButton cross,add,edit,delete,cancel,pdf;
private JTextField t1,textBox;;
JPanel p1,p2,p3,p4,p5,p6; 
private JLabel j,l1;
private Container container;
private ImageIcon add1,save;
private JTable table;
private JTableHeader header;
private JScrollPane jsp;
private DesignationTModel designationTableModel;
private String selectedCellValue;

presentation()
{
container=getContentPane();
BoxLayout boxLayout=new BoxLayout(container,BoxLayout.Y_AXIS);
container.setLayout(boxLayout);
designationTableModel=new DesignationTModel();
table=new JTable(designationTableModel);
Font font=new Font("Times New Roman",Font.PLAIN,24);
table.setFont(font);
table.setRowHeight(30);
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
table.getTableHeader().setReorderingAllowed(false);
header = table.getTableHeader();
header.setPreferredSize(new Dimension(30,30));

jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
jsp.setMaximumSize(new Dimension(500,300));
jsp.setPreferredSize(new Dimension(0,220));
p2 = new JPanel(new BorderLayout(10,10));
p2.add(new JLabel("Search"),BorderLayout.WEST);
t1=new JTextField(25);
//t1.setPreferredSize(new Dimension(100,100));
p2.add(t1,BorderLayout.CENTER); 
j=new JLabel("",JLabel.CENTER);
p2.add(j,BorderLayout.NORTH);
cross=new JButton(new ImageIcon("cross.png"));
//cross.setBounds(100,100,120,50);
p2.add(cross,BorderLayout.EAST);

p1 = new JPanel(new GridLayout(2,1,0,0));
p1.add(new JLabel("Designations"));
p1.add(p2);

p4=new JPanel(new GridLayout(0,5,10,0));
add1=new ImageIcon("add.png");
save=new ImageIcon("save.png");
add=new JButton(add1);
edit=new JButton(new ImageIcon("edit.png"));
delete=new JButton(new ImageIcon("delete.png"));
cancel=new JButton(new ImageIcon("cancel.png"));
pdf=new JButton(new ImageIcon("pdf.png"));

p4.add(add);
p4.add(edit);
p4.add(delete);
p4.add(cancel);
p4.add(pdf);

p5=new JPanel(new GridLayout(2,2,10,15));
p5.setBorder(BorderFactory.createEmptyBorder(200,300,300,300));
p5.setBorder(BorderFactory.createLineBorder(Color.black));
p5.setPreferredSize(new Dimension(900,300));
p5.add(new JLabel("Designation"),BorderLayout.WEST);
l1=new JLabel("_______________________");
textBox=new JTextField(25);
p5.add(l1,BorderLayout.CENTER);
p5.add(p4,BorderLayout.SOUTH);

Image icon=Toolkit.getDefaultToolkit().getImage("logo.png");
p6=new JPanel(new FlowLayout());
p6.add(p5);
container.add(p1);
container.add(jsp);
container.add(p6);

if(table.getModel().getRowCount()==0)
{
t1.setEnabled(false);
cross.setEnabled(false);
edit.setEnabled(false);
delete.setEnabled(false);
cancel.setEnabled(false);
pdf.setEnabled(false);
}

if(table.getModel().getRowCount()>0)
{
cancel.setEnabled(false);
}

table.addMouseListener(new MouseListener() {
public void mouseReleased(MouseEvent e) {
}
public void mousePressed(MouseEvent e) {
selectedCellValue = (String) table.getValueAt(table.getSelectedRow() , table.getSelectedColumn());
l1.setText(selectedCellValue);
System.out.println(selectedCellValue);
}
public void mouseExited(MouseEvent e) {
}
public void mouseEntered(MouseEvent e) {
}
public void mouseClicked(MouseEvent e) {
}
});

setIconImage(icon);
setTitle("HR Management");
setDefaultCloseOperation(EXIT_ON_CLOSE);
addListeners();
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int width=600;
int height=600;

setSize(width,height);
int x=(d.width/2)-(width/2);
int y=(d.height/2)-(height/2);
setLocation(x,y);
setVisible(true);
}
private void addListeners(){

cross.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ev)
{
t1.setText("");
}
});

add.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ev)
{

p5.remove(l1);
p5.removeAll();
p5.setBorder(BorderFactory.createEmptyBorder(200,300,300,300));
p5.setBorder(BorderFactory.createLineBorder(Color.black));
p5.setPreferredSize(new Dimension(900,300));
p5.add(new JLabel("Designation"),BorderLayout.WEST);
p5.add(textBox,BorderLayout.CENTER);
p5.add(p4,BorderLayout.SOUTH);


add.setIcon(save);

edit.setEnabled(false);
delete.setEnabled(false);
pdf.setEnabled(false);
cancel.setEnabled(true);
}
});

edit.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ev)
{

}
});

delete.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ev)
{

}
});

cancel.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ev)
{
p5.removeAll();
p5.setBorder(BorderFactory.createEmptyBorder(200,300,300,300));
p5.setBorder(BorderFactory.createLineBorder(Color.black));
p5.setPreferredSize(new Dimension(900,300));
p5.add(new JLabel("Designation"),BorderLayout.WEST);
p5.add(l1,BorderLayout.CENTER);
p5.add(p4,BorderLayout.SOUTH);


add.setIcon(add1);


add.setIcon(add1);
cancel.setEnabled(false);
edit.setEnabled(true);
delete.setEnabled(true);
pdf.setEnabled(true);
}
});

pdf.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ev)
{


}
});
}
}
class presentationpsp
{
public static void main(String gg[])
{
presentation s=new presentation();
}
}