package test.org.omg.PortableServer.POAPackage;


/**
* test.org/omg/PortableServer/POAPackage/WrongPolicyHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/test.org/omg/PortableServer/poa.idl
* Wednesday, March 15, 2017 1:33:59 AM PDT
*/

abstract public class WrongPolicyHelper
{
  private static String  _id = "IDL:omg.test.org/PortableServer/POA/WrongPolicy:1.0";

  public static void insert (test.org.omg.CORBA.Any a, test.org.omg.PortableServer.POAPackage.WrongPolicy that)
  {
    test.org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static test.org.omg.PortableServer.POAPackage.WrongPolicy extract (test.org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static test.org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static test.org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (test.org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return test.org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          test.org.omg.CORBA.StructMember[] _members0 = new test.org.omg.CORBA.StructMember [0];
          test.org.omg.CORBA.TypeCode _tcOf_members0 = null;
          __typeCode = test.org.omg.CORBA.ORB.init ().create_exception_tc (test.org.omg.PortableServer.POAPackage.WrongPolicyHelper.id (), "WrongPolicy", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static test.org.omg.PortableServer.POAPackage.WrongPolicy read (test.org.omg.CORBA.portable.InputStream istream)
  {
    test.org.omg.PortableServer.POAPackage.WrongPolicy value = new test.org.omg.PortableServer.POAPackage.WrongPolicy ();
    // read and discard the repository ID
    istream.read_string ();
    return value;
  }

  public static void write (test.org.omg.CORBA.portable.OutputStream ostream, test.org.omg.PortableServer.POAPackage.WrongPolicy value)
  {
    // write the repository ID
    ostream.write_string (id ());
  }

}
