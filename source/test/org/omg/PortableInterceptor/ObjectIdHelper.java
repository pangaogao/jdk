package test.org.omg.PortableInterceptor;


/**
* test.org/omg/PortableInterceptor/ObjectIdHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/test.org/omg/PortableInterceptor/Interceptors.idl
* Wednesday, March 15, 2017 1:33:58 AM PDT
*/


/** Type of an object id. This provides the identity of a particular
   * object that was created by an object adapter.
   */
abstract public class ObjectIdHelper
{
  private static String  _id = "IDL:omg.test.org/PortableInterceptor/ObjectId:1.0";

  public static void insert (test.org.omg.CORBA.Any a, byte[] that)
  {
    test.org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static byte[] extract (test.org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static test.org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static test.org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = test.org.omg.CORBA.ORB.init ().get_primitive_tc (test.org.omg.CORBA.TCKind.tk_octet);
      __typeCode = test.org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = test.org.omg.CORBA.ORB.init ().create_alias_tc (test.org.omg.CORBA.OctetSeqHelper.id (), "OctetSeq", __typeCode);
      __typeCode = test.org.omg.CORBA.ORB.init ().create_alias_tc (test.org.omg.PortableInterceptor.ObjectIdHelper.id (), "ObjectId", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static byte[] read (test.org.omg.CORBA.portable.InputStream istream)
  {
    byte value[] = null;
    value = test.org.omg.CORBA.OctetSeqHelper.read (istream);
    return value;
  }

  public static void write (test.org.omg.CORBA.portable.OutputStream ostream, byte[] value)
  {
    test.org.omg.CORBA.OctetSeqHelper.write (ostream, value);
  }

}
