## Employee Management Desktop Application

This application was designed in 3 layers (CONCEPT OF LAYERED PROGRAMMING) <br>
<ul>
  <li>
<b>Data Layer:</b> This Layer was divided into 2 Parts:
    <ul>
   <li>File Handing Technique </li> 
    <li>Database Technique </li>
    </ul>
    In the Data Layer, data can be stored by both techniques, employee data is stored in files and in databases. CRUD operations can be done using data files or databases.
</li>
<li>
<b>Business Layer:</b> This Layer consists of Data Structures, as it takes too much time to perform operations and display them on Graphical User Interface while performing actions with file handling.</li>
<li>
<b>Presentation Layer:</b> This is a Graphical User Interface layer that uses Java Swing for programming, this layer presents employee data.</li>
</ul>

## Environment Specifications
```bash
C:\>java -version
java version "10.0.1" 2018-04-17
Java(TM) SE Runtime Environment 18.3 (build 10.0.1+10)
Java HotSpot(TM) 64-Bit Server VM 18.3 (build 10.0.1+10, mixed mode)

C:\>gradle -version

------------------------------------------------------------
Gradle 6.5
------------------------------------------------------------

Build time:   2020-06-02 20:46:21 UTC
Revision:     a27f41e4ae5e8a41ab9b19f8dd6d86d7b384dad4

Kotlin:       1.3.72
Groovy:       2.5.11
Ant:          Apache Ant(TM) version 1.10.7 compiled on September 1 2019
JVM:          10.0.1 ("Oracle Corporation" 10.0.1+10)
OS:           Windows 10 10.0 amd64

```


## Folders Abbreviations
<ul>
  <li>DL: Data Layer (File Handling Technique)</li>
  <li>DBDL: Database Data Layer (Database Technique)</li>
  <li>PL: Presentation Layer</li>
  <li>BL: Business Layer </li>
</ul>

## Common
This folder includes files that will be shared between all the layers like **enums** <br>
To compile GENDER.java <br>
```bash
hr\common\src> javac -d ..\classes -classpath;. com\thinking\machines\enums\*.java
```
To create a jar file of **common** folder: <br>
```bash
hr\common\classes> jar -cvf ..\dist\hr-common.jar com
```
The above line will create a **hr-common.jar** file.


## DL: Data Layer
To compile the following files: DAOException.java <br>
```bash
hr\dl\src> javac -d ..\classes -classpath ..\..\common\dist\hr-common.jar;. com\thinking\machines\hr\dl\exceptions\*.java
```
To compile the following files: DesignationDTOInterface.java, EmployeeDTOInterface.java <br>
```bash
hr\dl\src> javac -d ..\classes -classpath ..\..\common\dist\hr-common.jar;. com\thinking\machines\hr\dl\interfaces\dto\*.java
```
To compile the following files: DesignationDAOInterface.java, EmployeeDAOInterface.java <br>
```bash
HR\dl\src> javac -d ..\classes -classpath ..\..\common\dist\hr-common.jar;. com\thinking\machines\hr\dl\interfaces\dto\*.java
```
To compile the following files: DesignationDTO.java, EmployeeDTO.java <br>
```bash
hr\dl\src> javac -d ..\classes -classpath ..\..\common\dist\hr-common.jar;. com\thinking\machines\hr\dl\dto\*.java
```
To compile the following files: DesignationDAO.java, EmployeeDAO.java <br>
```bash
HR\dl\src> javac -d ..\classes -classpath ..\..\common\dist\hr-common.jar;. com\thinking\machines\hr\dl\dao\*.java
```
To compile **Testcases** <br>
```bash
hr\dl\testcases> javac -classpath ..\..\common\dist\hr-common.jar;..\classes;. *.java
```

## Business Layer
To compile files:
```bash
hr\bl>gradle build
```

## Presentation Layer
To compile files:
```bash
hr\pl>gradle build
```
**To Run the application:**
```bash
hr\pl>java -classpath ..\common\dist\hr-common.jar;..\DL\dist\hr-dl-1.0.jar;..\BL\build\libs\BL.jar;build\libs\PL.jar;. com.thinking\machines.hr.pl.Main
```
