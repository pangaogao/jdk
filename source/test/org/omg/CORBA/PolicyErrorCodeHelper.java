package test.org.omg.CORBA;


/**
* test.org/omg/CORBA/PolicyErrorCodeHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/test.org/omg/PortableInterceptor/CORBAX.idl
* Wednesday, March 15, 2017 1:33:58 AM PDT
*/


/** 
   * Encapsulates a reason a Policy may be invalid.
   *
   * @see PolicyError
   */
abstract public class PolicyErrorCodeHelper
{
  private static String  _id = "IDL:omg.test.org/CORBA/PolicyErrorCode:1.0";

  public static void insert (test.org.omg.CORBA.Any a, short that)
  {
    test.org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static short extract (test.org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static test.org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static test.org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = test.org.omg.CORBA.ORB.init ().get_primitive_tc (test.org.omg.CORBA.TCKind.tk_short);
      __typeCode = test.org.omg.CORBA.ORB.init ().create_alias_tc (test.org.omg.CORBA.PolicyErrorCodeHelper.id (), "PolicyErrorCode", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static short read (test.org.omg.CORBA.portable.InputStream istream)
  {
    short value = (short)0;
    value = istream.read_short ();
    return value;
  }

  public static void write (test.org.omg.CORBA.portable.OutputStream ostream, short value)
  {
    ostream.write_short (value);
  }

}
