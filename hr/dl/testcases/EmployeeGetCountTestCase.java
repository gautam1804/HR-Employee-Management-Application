import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;

public class EmployeeGetCountTestCase
{
public static void main(String gg[])
{
try
{
System.out.println("Number of Employees are : "+new EmployeeDAO().getCount());
}catch(DAOException daoE)
{
System.out.println(daoE.getMessage());
}
}
}