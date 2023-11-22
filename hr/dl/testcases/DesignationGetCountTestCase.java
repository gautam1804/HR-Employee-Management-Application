import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;

public class DesignationGetCountTestCase
{
public static void main(String gg[])
{
try
{
DesignationDAOInterface designationDAO;
designationDAO = new DesignationDAO();
System.out.println("Designation Count : "+designationDAO.getCount());
}catch(DAOException daoE)
{
System.out.println(daoE.getMessage());
}
}
}