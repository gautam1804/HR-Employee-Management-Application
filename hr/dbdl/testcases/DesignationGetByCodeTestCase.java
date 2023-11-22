import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;

public class DesignationGetByCodeTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
DesignationDTOInterface designationDTO;

DesignationDAOInterface designationDAO;
designationDAO = new DesignationDAO();

designationDTO=designationDAO.getByCode(code);
System.out.printf("Code : %d , Title : %s\n",designationDTO.getCode(),designationDTO.getTitle());
}catch(DAOException daoE)
{
System.out.println(daoE.getMessage());
}
}
}