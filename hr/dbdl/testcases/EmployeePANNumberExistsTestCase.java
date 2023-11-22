import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;

public class EmployeePANNumberExistsTestCase
{
public static void main(String gg[])
{
String panNumber=gg[0];
try
{
System.out.println("PAN Number : "+panNumber+" exists : "+new EmployeeDAO().panNumberExists(panNumber));
}catch(DAOException daoE)
{
System.out.println(daoE.getMessage());
}
}
}