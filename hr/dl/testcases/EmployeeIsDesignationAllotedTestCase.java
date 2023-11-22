 import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;

public class EmployeeIsDesignationAllotedTestCase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO = new EmployeeDAO();

System.out.println("Employee with Designation Code "+designationCode +" exists : "+employeeDAO.isDesignationAlloted(designationCode));

}catch(DAOException daoE)
{
System.out.println(daoE.getMessage());
}
}
}