package test.org.omg.PortableInterceptor;


/**
* test.org/omg/PortableInterceptor/ObjectReferenceTemplateHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/test.org/omg/PortableInterceptor/Interceptors.idl
* Wednesday, March 15, 2017 1:33:58 AM PDT
*/


/** The object reference template.  An instance of this must
   * exist for each object adapter created in an ORB.  The server_id,
   * orb_id, and adapter_name attributes uniquely identify this template
   * within the scope of an IMR.  Note that adapter_id is similarly unique
   * within the same scope, but it is opaque, and less useful in many
   * cases.
   */
abstract public class ObjectReferenceTemplateHelper
{
  private static String  _id = "IDL:omg.test.org/PortableInterceptor/ObjectReferenceTemplate:1.0";


  public static void insert (test.org.omg.CORBA.Any a, test.org.omg.PortableInterceptor.ObjectReferenceTemplate that)
  {
    test.org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static test.org.omg.PortableInterceptor.ObjectReferenceTemplate extract (test.org.omg.CORBA.Any a)
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
          test.org.omg.CORBA.ValueMember[] _members0 = new test.org.omg.CORBA.ValueMember[0];
          test.org.omg.CORBA.TypeCode _tcOf_members0 = null;
          __typeCode = test.org.omg.CORBA.ORB.init ().create_value_tc (_id, "ObjectReferenceTemplate", test.org.omg.CORBA.VM_ABSTRACT.value, null, _members0);
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

  public static test.org.omg.PortableInterceptor.ObjectReferenceTemplate read (test.org.omg.CORBA.portable.InputStream istream)
  {
    return (test.org.omg.PortableInterceptor.ObjectReferenceTemplate)((test.org.omg.CORBA_2_3.portable.InputStream) istream).read_value (id ());
  }

  public static void write (test.org.omg.CORBA.portable.OutputStream ostream, test.org.omg.PortableInterceptor.ObjectReferenceTemplate value)
  {
    ((test.org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value (value, id ());
  }


}
