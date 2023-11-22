import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;

public class EmployeeEmployeeIdExistsTestCase
{
public static void main(String gg[])
{
String employeeId=gg[0];
try
{
System.out.println("Employee Id. : "+employeeId+" exists : "+new EmployeeDAO().employeeIdExists(employeeId));
}catch(DAOException daoE)
{
System.out.println(daoE.getMessage());
}
}
}