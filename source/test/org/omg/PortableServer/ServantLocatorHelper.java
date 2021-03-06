package test.org.omg.PortableServer;


/**
* test.org/omg/PortableServer/ServantLocatorHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/test.org/omg/PortableServer/poa.idl
* Wednesday, March 15, 2017 1:33:59 AM PDT
*/


/**
	 * When the POA has the NON_RETAIN policy it uses servant 
	 * managers that are ServantLocators. Because the POA 
	 * knows that the servant returned by this servant 
	 * manager will be used only for a single request, 
	 * it can supply extra information to the servant 
	 * manager's operations and the servant manager's pair 
	 * of operations may be able to cooperate to do 
	 * something different than a ServantActivator. 
	 * When the POA uses the ServantLocator interface, 
	 * immediately after performing the operation invocation 
	 * on the servant returned by preinvoke, the POA will 
	 * invoke postinvoke on the servant manager, passing the 
	 * ObjectId value and the Servant value as parameters 
	 * (among others). This feature may be used to force 
	 * every request for objects associated with a POA to 
	 * be mediated by the servant manager.
	 */
abstract public class ServantLocatorHelper
{
  private static String  _id = "IDL:omg.test.org/PortableServer/ServantLocator:1.0";

  public static void insert (test.org.omg.CORBA.Any a, test.org.omg.PortableServer.ServantLocator that)
  {
    test.org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static test.org.omg.PortableServer.ServantLocator extract (test.org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static test.org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static test.org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = test.org.omg.CORBA.ORB.init ().create_interface_tc (test.org.omg.PortableServer.ServantLocatorHelper.id (), "ServantLocator");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static test.org.omg.PortableServer.ServantLocator read (test.org.omg.CORBA.portable.InputStream istream)
  {
      throw new test.org.omg.CORBA.MARSHAL ();
  }

  public static void write (test.org.omg.CORBA.portable.OutputStream ostream, test.org.omg.PortableServer.ServantLocator value)
  {
      throw new test.org.omg.CORBA.MARSHAL ();
  }

  public static test.org.omg.PortableServer.ServantLocator narrow (test.org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof test.org.omg.PortableServer.ServantLocator)
      return (test.org.omg.PortableServer.ServantLocator)obj;
    else if (!obj._is_a (id ()))
      throw new test.org.omg.CORBA.BAD_PARAM ();
    else
    {
      test.org.omg.CORBA.portable.Delegate delegate = ((test.org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      test.org.omg.PortableServer._ServantLocatorStub stub = new test.org.omg.PortableServer._ServantLocatorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static test.org.omg.PortableServer.ServantLocator unchecked_narrow (test.org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof test.org.omg.PortableServer.ServantLocator)
      return (test.org.omg.PortableServer.ServantLocator)obj;
    else
    {
      test.org.omg.CORBA.portable.Delegate delegate = ((test.org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      test.org.omg.PortableServer._ServantLocatorStub stub = new test.org.omg.PortableServer._ServantLocatorStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
