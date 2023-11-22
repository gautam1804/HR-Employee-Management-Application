package com.thinking.machines.network.server;

import java.net.*;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.common.exceptions.*;

public class NetworkServer
{
private RequestHandlerInterface requestHandler;
public NetworkServer(RequestHandlerInterface requestHandler) throws NetworkException
{
if(requestHandler==null)
{
throw new NetworkException("Reqeust Handler is missing");
}
this.requestHandler=requestHandler;
}

public void start() throws NetworkException //will run on main thread means thread on which networkServer's object is running.
{
ServerSocket serverSocket=null;
int port=Configuration.getPort();

try
{
serverSocket=new ServerSocket(port);
}catch(Exception exception)
{
throw new NetworkException("Unable to start the server on port : "+port);
}

try
{

Socket socket;
RequestProcessor requestProcessor;
while(true)
{
System.out.println("Server is ready to accept request on the port : "+port);
socket=serverSocket.accept();
requestProcessor= new RequestProcessor(socket,requestHandler);
}
}catch(Exception e)
{
System.out.println(e);
}

}

}