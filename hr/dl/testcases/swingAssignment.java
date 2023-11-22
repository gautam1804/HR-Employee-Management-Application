import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;


class DesignationTableModel extends AbstractTableModel
{
private String title[];
private DesignationDAOInterface designationDAO;
private Set<DesignationDTOInterface> designations;
private Object data[][];

DesignationTableModel()
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
title[0]="Code";
title[1]="Title";
try
{
designationDAO = new DesignationDAO();
designations=designationDAO.getAll();
data=new Object[5][designations.size()];
int i=0;
for(DesignationDTOInterface designationDTO : designations)
{
data[i][0]=designationDTO.getCode();
data[i][1]=designationDTO.getTitle();
i++;
}
}catch(DAOException daoE)
{
System.out.println(daoE.getMessage());
}
}
}
class swingA extends JFrame
{
private JTable table;
private JScrollPane jsp;
private DesignationTableModel designationTableModel;
private Container container;

swingA()
{
designationTableModel=new DesignationTableModel();
table=new JTable(designationTableModel);
Font font=new Font("Times New Roman",Font.PLAIN,24);
table.setFont(font);
table.setRowHeight(30);

//Assignment:
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
table.getTableHeader().setReorderingAllowed(false);
JTableHeader header = table.getTableHeader();
header.setPreferredSize(new Dimension(50,90));

jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);

Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int width=600;
int height=600;

setSize(width,height);
int x=(d.width/2)-(width/2);
int y=(d.height/2)-(height/2);
setLocation(x,y);
setVisible(true);
}

}
class swingpsp
{
public static void main(String gg[])
{
swingA s=new swingA();
}
}