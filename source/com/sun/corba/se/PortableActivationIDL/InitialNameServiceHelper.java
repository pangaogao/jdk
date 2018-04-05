package com.sun.corba.se.PortableActivationIDL;


/**
* com/sun/corba/se/PortableActivationIDL/InitialNameServiceHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/com/sun/corba/se/PortableActivationIDL/activation.idl
* Wednesday, March 15, 2017 1:33:57 AM PDT
*/


/** Interface used to support binding references in the bootstrap name
    * service.
    */
abstract public class InitialNameServiceHelper
{
  private static String  _id = "IDL:PortableActivationIDL/InitialNameService:1.0";

  public static void insert (test.org.omg.CORBA.Any a, com.sun.corba.se.PortableActivationIDL.InitialNameService that)
  {
    test.org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.sun.corba.se.PortableActivationIDL.InitialNameService extract (test.org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static test.org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static test.org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = test.org.omg.CORBA.ORB.init ().create_interface_tc (com.sun.corba.se.PortableActivationIDL.InitialNameServiceHelper.id (), "InitialNameService");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.sun.corba.se.PortableActivationIDL.InitialNameService read (test.org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_InitialNameServiceStub.class));
  }

  public static void write (test.org.omg.CORBA.portable.OutputStream ostream, com.sun.corba.se.PortableActivationIDL.InitialNameService value)
  {
    ostream.write_Object ((test.org.omg.CORBA.Object) value);
  }

  public static com.sun.corba.se.PortableActivationIDL.InitialNameService narrow (test.org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof com.sun.corba.se.PortableActivationIDL.InitialNameService)
      return (com.sun.corba.se.PortableActivationIDL.InitialNameService)obj;
    else if (!obj._is_a (id ()))
      throw new test.org.omg.CORBA.BAD_PARAM ();
    else
    {
      test.org.omg.CORBA.portable.Delegate delegate = ((test.org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      com.sun.corba.se.PortableActivationIDL._InitialNameServiceStub stub = new com.sun.corba.se.PortableActivationIDL._InitialNameServiceStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static com.sun.corba.se.PortableActivationIDL.InitialNameService unchecked_narrow (test.org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof com.sun.corba.se.PortableActivationIDL.InitialNameService)
      return (com.sun.corba.se.PortableActivationIDL.InitialNameService)obj;
    else
    {
      test.org.omg.CORBA.portable.Delegate delegate = ((test.org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      com.sun.corba.se.PortableActivationIDL._InitialNameServiceStub stub = new com.sun.corba.se.PortableActivationIDL._InitialNameServiceStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
