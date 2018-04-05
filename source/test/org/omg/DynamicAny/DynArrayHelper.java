package test.org.omg.DynamicAny;


/**
* test.org/omg/DynamicAny/DynArrayHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/test.org/omg/DynamicAny/DynamicAny.idl
* Wednesday, March 15, 2017 1:33:58 AM PDT
*/


/**
    * DynArray objects support the manipulation of IDL arrays.
    * Note that the dimension of the array is contained in the TypeCode which is accessible
    * through the type attribute. It can also be obtained by calling the component_count operation.
    */
abstract public class DynArrayHelper
{
  private static String  _id = "IDL:omg.test.org/DynamicAny/DynArray:1.0";

  public static void insert (test.org.omg.CORBA.Any a, test.org.omg.DynamicAny.DynArray that)
  {
    test.org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static test.org.omg.DynamicAny.DynArray extract (test.org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static test.org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static test.org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = test.org.omg.CORBA.ORB.init ().create_interface_tc (test.org.omg.DynamicAny.DynArrayHelper.id (), "DynArray");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static test.org.omg.DynamicAny.DynArray read (test.org.omg.CORBA.portable.InputStream istream)
  {
      throw new test.org.omg.CORBA.MARSHAL ();
  }

  public static void write (test.org.omg.CORBA.portable.OutputStream ostream, test.org.omg.DynamicAny.DynArray value)
  {
      throw new test.org.omg.CORBA.MARSHAL ();
  }

  public static test.org.omg.DynamicAny.DynArray narrow (test.org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof test.org.omg.DynamicAny.DynArray)
      return (test.org.omg.DynamicAny.DynArray)obj;
    else if (!obj._is_a (id ()))
      throw new test.org.omg.CORBA.BAD_PARAM ();
    else
    {
      test.org.omg.CORBA.portable.Delegate delegate = ((test.org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      test.org.omg.DynamicAny._DynArrayStub stub = new test.org.omg.DynamicAny._DynArrayStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static test.org.omg.DynamicAny.DynArray unchecked_narrow (test.org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof test.org.omg.DynamicAny.DynArray)
      return (test.org.omg.DynamicAny.DynArray)obj;
    else
    {
      test.org.omg.CORBA.portable.Delegate delegate = ((test.org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      test.org.omg.DynamicAny._DynArrayStub stub = new test.org.omg.DynamicAny._DynArrayStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}