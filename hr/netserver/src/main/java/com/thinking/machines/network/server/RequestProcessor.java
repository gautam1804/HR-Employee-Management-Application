package com.thinking.machines.network.server;

import com.thinking.machines.network.common.*;
import com.thinking.machines.network.common.exceptions.*;
import java.io.*;
import java.net.*;

class RequestProcessor extends Thread
{
private RequestHandlerInterface requestHandler;
private Socket socket;

RequestProcessor(Socket socket,RequestHandlerInterface requestHandler)
{
this.requestHandler=requestHandler;
this.socket=socket;
start();
}

public void run()
{

try{
InputStream is=socket.getInputStream();
OutputStream os=socket.getOutputStream();
int bytesToReceive=1024;
byte header[]=new byte[1024];
byte tmp[]=new byte[1024];
int i,j,bytesReadCount,k;
i=0;
j=0;
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1)
{
continue;
}
for(k=0;k<bytesReadCount;k++)
{
header[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}

int requestLength=0;
j=1023;
i=1;
while(j>=0)
{
requestLength=requestLength+(header[j]*i);
j--;
i=i*10;
}
byte ack[]=new byte[1];
ack[0]=1;
os.write(ack,0,1);
os.flush();

byte requestBytes[]=new byte[requestLength];
bytesToReceive=requestLength;
i=0;
j=0;
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1)
{
continue;
}
for(k=0;k<bytesReadCount;k++)
{
requestBytes[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}

ByteArrayInputStream bais=new ByteArrayInputStream(requestBytes);
ObjectInputStream ois=new ObjectInputStream(bais);
Request request=(Request)ois.readObject();
Response response=requestHandler.process(request);
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(response);
oos.flush();

byte objectBytes[]=baos.toByteArray();
int responseLength=objectBytes.length;
int x;
i=1023;
x=responseLength;
header=new byte[1024];
while(x>0)
{
header[i]=(byte)(x%10);
x=x/10;
i--;
}
os.write(header,0,1024);
os.flush();
System.out.println("Response header sent : "+responseLength);

while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1)
{
continue;
}
break;
}

System.out.println("Acknowledgement received");
int bytesToSend=responseLength;
int chunkSize=1024;
j=0;

while(j<bytesToSend)
{
if((bytesToSend-j)<chunkSize)
{
chunkSize=bytesToSend-j;
}
os.write(objectBytes,j,chunkSize);
os.flush();
j=j+chunkSize;
}
System.out.println("Response sent");
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1)
{
continue;
}
break;
}
System.out.println("Acknowledgement received");
socket.close();

}catch(Exception e)
{
System.out.println(e);
}
}
}