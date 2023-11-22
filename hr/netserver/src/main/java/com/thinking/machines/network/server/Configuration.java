package com.thinking.machines.network.server;

import javax.xml.xpath.*;
import org.xml.sax.*;
import java.io.*;
import com.thinking.machines.network.common.exceptions.*;

class Configuration
{
private static int port=-1;
private static Boolean malformed=false;
private static Boolean fileMissing=false;

static
{
try
{
File file=new File("server.xml");
if(file.exists())
{
InputSource inputSource=new InputSource("server.xml");
XPath xpath=XPathFactory.newInstance().newXPath();
String port=xpath.evaluate("//server/@port",inputSource);
Configuration.port=Integer.parseInt(port);
}
else
{
fileMissing=true;
}
}catch(Exception exception)
{
malformed=true;
//do nothing
}
}

public static int getPort() throws NetworkException
{
if(malformed) throw new NetworkException("server.xml is not configured according to documentation");
if(fileMissing) throw new NetworkException("server.xml is missing, read documentation");
if(port<0||port>49151) throw new NetworkException("server.xml is not configured according to documentation");
return port;
}


}