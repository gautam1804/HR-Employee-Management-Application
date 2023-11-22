package com.thinking.machines.hr.dl.dao;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.math.*;
import java.io.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;


public class EmployeeDAO implements EmployeeDAOInterface
{
public static final String FILE_NAME="employee.data";
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId;
String name=employeeDTO.getName();
if(name==null) throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Length of Name is zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("INVALID DESIGNATION CODE : "+designationCode);

Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("INVALID DESIGNATION CODE : "+designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}

java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Date of Birth is null");
}
char gender=employeeDTO.getGender();
if(gender==' ') 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Gender not set to Male/Female");
}
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic Salary is null");
}
if(basicSalary.signum()==-1) 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic Salary is negative"); 
}
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number is null");
}
panNumber=panNumber.trim();
if(panNumber.length()==0) 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of PAN Number is zero");
}
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card Number is null");
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of Aadhar Card Number is zero");
}
try
{
boolean panNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();

boolean aadharCardNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();

if(panNumberExists && aadharCardNumberExists) 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN NUMBER ("+panNumber+") AND AADHAR CARD NUMBER ("+aadharCardNumber+") EXISTS");
}
if(panNumberExists)
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN NUMBER ("+panNumber+") EXISTS");
}
if(aadharCardNumberExists)
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card NUMBER ("+aadharCardNumber+") EXISTS");
}

preparedStatement=connection.prepareStatement("insert into employee(name,designation_code,date_of_birth,basic_salary,gender,is_indian,pan_number,aadhar_card_number) values (?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setBigDecimal(4,basicSalary);
preparedStatement.setString(5,String.valueOf(gender));
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int generatedEmployeeId=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
employeeDTO.setEmployeeId("A"+(1000000+generatedEmployeeId));
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId=employeeDTO.getEmployeeId();
if(employeeId==null) throw new DAOException("Employee id. is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Length of Employee id. is zero");

int actualEmployeeId;
try{
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception)
{
throw new DAOException("Invalid employee id.");
}

String name=employeeDTO.getName();
if(name==null) throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Length of Name is zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("INVALID DESIGNATION CODE : "+designationCode);

Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("INVALID DESIGNATION CODE : "+designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}

java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Date of Birth is null");
}
char gender=employeeDTO.getGender();
if(gender==' ') 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Gender not set to Male/Female");
}
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic Salary is null");
}
if(basicSalary.signum()==-1) 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic Salary is negative"); 
}
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number is null");
}
panNumber=panNumber.trim();
if(panNumber.length()==0) 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of PAN Number is zero");
}
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card Number is null");
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of Aadhar Card Number is zero");
}
try
{
boolean panNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=? and employee_id<>?");
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();

boolean aadharCardNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=? and employee_id<>?");
preparedStatement.setString(1,aadharCardNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();

if(panNumberExists && aadharCardNumberExists) 
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN NUMBER ("+panNumber+") AND AADHAR CARD NUMBER ("+aadharCardNumber+") EXISTS");
}
if(panNumberExists)
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN NUMBER ("+panNumber+") EXISTS");
}
if(aadharCardNumberExists)
{
try{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card NUMBER ("+aadharCardNumber+") EXISTS");
}

preparedStatement=connection.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,basic_salary=?,gender=?,is_indian=?,pan_number=?,aadhar_card_number=? where employee_id=?");
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setBigDecimal(4,basicSalary);
preparedStatement.setString(5,String.valueOf(gender));
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.setInt(9,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void delete(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("Employee id. is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Length of Employee id. is zero");

int actualEmployeeId;
try{
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception)
{
throw new DAOException("Invalid employee id.");
}

Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid employee Id. :"+employeeId);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public Set<EmployeeDTOInterface> getAll() throws DAOException
{
Set<EmployeeDTOInterface> employees =new TreeSet<>();
try
{
Connection connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select * from employee");
EmployeeDTOInterface employeeDTO;
java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String gender;
if(resultSet.next())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(1000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.sql.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
gender=resultSet.getString("gender");
if(gender.equals("M"))
{
 employeeDTO.setGender(GENDER.MALE);
}
if(gender.equals("F")) 
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
employees.add(employeeDTO);
}
resultSet.close();
statement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employees;
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
Set<EmployeeDTOInterface> employees =new TreeSet<>();
try{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("INVALID DESIGNATION CODE : "+designationCode);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select * from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
EmployeeDTOInterface employeeDTO;
java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String gender;
if(resultSet.next())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(1000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.sql.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
gender=resultSet.getString("gender");
if(gender.equals("M"))
{
 employeeDTO.setGender(GENDER.MALE);
}
if(gender.equals("F")) 
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
employees.add(employeeDTO);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employees;
}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("INVALID EMPLOYEE ID1. :- "+employeeId);
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("INVALID EMPLOYEE ID2. :- employee id. is of zero length");

int actualEmployeeId=0;
try{
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception)
{
throw new DAOException("Invalid employee id.");
}
EmployeeDTOInterface employeeDTO=null;
try{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid employee id. : "+employeeId);
}
java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String gender;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(1000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.sql.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
gender=resultSet.getString("gender");
if(gender.equals("M"))
{
 employeeDTO.setGender(GENDER.MALE);
}
if(gender.equals("F")) 
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employeeDTO;
}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("INVALID PAN Number :- "+panNumber);
panNumber=panNumber.trim();
if(panNumber.length()==0)  throw new DAOException("INVALID PAN Number :- pan number length is zero");
EmployeeDTOInterface employeeDTO;
try{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select * from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid pan number : "+panNumber);
}
java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String gender;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(1000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.sql.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
gender=resultSet.getString("gender");
if(gender.equals("M"))
{
 employeeDTO.setGender(GENDER.MALE);
}
if(gender.equals("F")) 
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employeeDTO;
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("INVALID Aadhar Card Number :- "+aadharCardNumber);
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)  throw new DAOException("INVALID Aadhar Card Number :- aadhar card number length is zero");
EmployeeDTOInterface employeeDTO;
try{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid aadhar card number :- "+aadharCardNumber);
}

java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String gender;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(1000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.sql.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
gender=resultSet.getString("gender");
if(gender.equals("M"))
{
 employeeDTO.setGender(GENDER.MALE);
}
if(gender.equals("F")) 
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employeeDTO;
}
public boolean employeeIdExists(String employeeId) throws DAOException
{
boolean employeeIdExists=false;
if(employeeId==null) return false;
employeeId=employeeId.trim();
if(employeeId.length()==0)  return false;
int actualEmployeeId;
try{
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception)
{
throw new DAOException("Invalid employee id.");
}
try{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("INVALID employee id. : "+employeeId);
}
employeeIdExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();

}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employeeIdExists;
}
public boolean panNumberExists(String panNumber) throws DAOException
{
boolean panNumberExists;
if(panNumber==null) return false;
panNumber=panNumber.trim();
if(panNumber.length()==0)  return false;
try{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("INVALID pan number : "+panNumber);
}
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();

}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return panNumberExists;

}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
boolean aadharCardNumberExists;
if(aadharCardNumber==null) return false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)  return false;
try{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("INVALID aadhar card number :"+aadharCardNumber);
}
aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();

}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return aadharCardNumberExists;

}
public int getCount() throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
Statement Statement;
Statement=connection.createStatement();
ResultSet resultSet=Statement.executeQuery("select count(*) as cnt from employee");
resultSet.next();
int count=resultSet.getInt("cnt");
resultSet.close();
Statement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public int getCountByDesignation(int designationCode) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(*) as cnt from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
ResultSet resultSet=preparedStatement.executeQuery();
resultSet.next();
int count=resultSet.getInt("cnt");
resultSet.close();
preparedStatement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public boolean isDesignationAlloted(int designationCode) throws DAOException 
{
boolean designationAlloted=false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("INVALID DESIGNATION CODE : "+designationCode);
}
preparedStatement=connection.prepareStatement("select gender from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
designationAlloted=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();

}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return designationAlloted;
}
}