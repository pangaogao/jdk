package com.sun.corba.se.spi.activation;

/**
* com/sun/corba/se/spi/activation/ServerAlreadyActiveHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/com/sun/corba/se/spi/activation/activation.idl
* Wednesday, March 15, 2017 1:33:56 AM PDT
*/

public final class ServerAlreadyActiveHolder implements test.org.omg.CORBA.portable.Streamable
{
  public com.sun.corba.se.spi.activation.ServerAlreadyActive value = null;

  public ServerAlreadyActiveHolder ()
  {
  }

  public ServerAlreadyActiveHolder (com.sun.corba.se.spi.activation.ServerAlreadyActive initialValue)
  {
    value = initialValue;
  }

  public void _read (test.org.omg.CORBA.portable.InputStream i)
  {
    value = com.sun.corba.se.spi.activation.ServerAlreadyActiveHelper.read (i);
  }

  public void _write (test.org.omg.CORBA.portable.OutputStream o)
  {
    com.sun.corba.se.spi.activation.ServerAlreadyActiveHelper.write (o, value);
  }

  public test.org.omg.CORBA.TypeCode _type ()
  {
    return com.sun.corba.se.spi.activation.ServerAlreadyActiveHelper.type ();
  }

}
