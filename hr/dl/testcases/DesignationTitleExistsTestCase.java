import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;

public class DesignationTitleExistsTestCase
{
public static void main(String gg[])
{
String title=gg[0];
try
{
DesignationDAOInterface designationDAO;
designationDAO = new DesignationDAO();
System.out.printf(title+ " exists : "+designationDAO.titleExists(title));
}catch(DAOException daoE)
{
System.out.println(daoE.getMessage());
}
}
}