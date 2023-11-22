package com.thinking.machines.network.client;

import com.thinking.machines.network.common.*;
import com.thinking.machines.network.common.exceptions.*;
import java.io.*;
import java.net.*;

public class NetworkClient
{
public Response send(Request request) throws NetworkException	
{
try
{
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(request);
oos.flush();

byte objectBytes[]=baos.toByteArray();
int requestLength=objectBytes.length;
byte header[]=new byte[1024];
int i;
int x;
i=1023;
x=requestLength;
while(x>0)
{
header[i]=(byte)(x%10);
x=x/10;
i--;
}

Socket socket=new Socket(Configuration.getHost(),Configuration.getPort());
OutputStream os=socket.getOutputStream();
os.write(header,0,1024);
os.flush();

byte ack[]=new byte[1];
InputStream is=socket.getInputStream();
int bytesReadCount;
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1)
{
continue;
}
break;
}

int bytesToSend=requestLength;
int chunkSize=1024;
int j=0;

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

int bytesToReceive=1024;
byte tmp[]=new byte[1024];
i=0;
j=0;
int k;
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

int responseLength=0;
j=1023;
i=1;
while(j>=0)
{
responseLength=responseLength+(header[j]*i);
j--;
i=i*10;
}

System.out.println("Header received : "+responseLength);
ack[0]=1;
os.write(ack,0,1);
os.flush();

System.out.println("Acknowledgement sent");

byte response[]=new byte[responseLength];
bytesToReceive=responseLength;
i=0;
j=0;
System.out.println("Now receiving response");
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1)
{
continue;
}
for(k=0;k<bytesReadCount;k++)
{
response[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}

System.out.println("Response received");
ack[0]=1;
os.write(ack);
os.flush();
socket.close();

ByteArrayInputStream bais=new ByteArrayInputStream(response);
ObjectInputStream ois=new ObjectInputStream(bais);
Response responseObject =(Response)ois.readObject();
return responseObject;
}catch(Exception e)
{
throw new NetworkException(e.getMessage());
}
}
}