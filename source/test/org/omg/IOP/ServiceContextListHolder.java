package test.org.omg.IOP;


/**
* test.org/omg/IOP/ServiceContextListHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/test.org/omg/PortableInterceptor/IOP.idl
* Wednesday, March 15, 2017 1:33:58 AM PDT
*/


/** An array of service contexts, forming a service context list. */
public final class ServiceContextListHolder implements test.org.omg.CORBA.portable.Streamable
{
  public test.org.omg.IOP.ServiceContext value[] = null;

  public ServiceContextListHolder ()
  {
  }

  public ServiceContextListHolder (test.org.omg.IOP.ServiceContext[] initialValue)
  {
    value = initialValue;
  }

  public void _read (test.org.omg.CORBA.portable.InputStream i)
  {
    value = test.org.omg.IOP.ServiceContextListHelper.read (i);
  }

  public void _write (test.org.omg.CORBA.portable.OutputStream o)
  {
    test.org.omg.IOP.ServiceContextListHelper.write (o, value);
  }

  public test.org.omg.CORBA.TypeCode _type ()
  {
    return test.org.omg.IOP.ServiceContextListHelper.type ();
  }

}
