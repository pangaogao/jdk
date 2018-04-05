package com.sun.corba.se.PortableActivationIDL;


/**
* com/sun/corba/se/PortableActivationIDL/TCPPortHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/com/sun/corba/se/PortableActivationIDL/activation.idl
* Wednesday, March 15, 2017 1:33:56 AM PDT
*/


/** Type of TCP port number, used in structures that describe 
    * transport endpoints.  The valid range is actually 0-65535, but
    * we use a long here to avoid signed/unsigned conversion headaches
    * in Java.
    */
abstract public class TCPPortHelper
{
  private static String  _id = "IDL:PortableActivationIDL/TCPPort:1.0";

  public static void insert (test.org.omg.CORBA.Any a, int that)
  {
    test.org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static int extract (test.org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static test.org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static test.org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = test.org.omg.CORBA.ORB.init ().get_primitive_tc (test.org.omg.CORBA.TCKind.tk_long);
      __typeCode = test.org.omg.CORBA.ORB.init ().create_alias_tc (com.sun.corba.se.PortableActivationIDL.TCPPortHelper.id (), "TCPPort", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static int read (test.org.omg.CORBA.portable.InputStream istream)
  {
    int value = (int)0;
    value = istream.read_long ();
    return value;
  }

  public static void write (test.org.omg.CORBA.portable.OutputStream ostream, int value)
  {
    ostream.write_long (value);
  }

}
