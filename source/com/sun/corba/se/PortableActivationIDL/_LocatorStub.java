package com.sun.corba.se.PortableActivationIDL;


/**
* com/sun/corba/se/PortableActivationIDL/_LocatorStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/com/sun/corba/se/PortableActivationIDL/activation.idl
* Wednesday, March 15, 2017 1:33:57 AM PDT
*/

public class _LocatorStub extends test.org.omg.CORBA.portable.ObjectImpl implements com.sun.corba.se.PortableActivationIDL.Locator
{


  /** locate server - returns the port with a specific type for all registered
  	* ORBs of an active server.
  	* Starts the server if it is not already running.
  	*/
  public com.sun.corba.se.PortableActivationIDL.LocatorPackage.ServerLocationPerType locateServer (String serverId, String endPoint) throws com.sun.corba.se.PortableActivationIDL.NoSuchEndPoint, com.sun.corba.se.PortableActivationIDL.ServerNotRegistered, com.sun.corba.se.PortableActivationIDL.ServerHeldDown
  {
            test.org.omg.CORBA.portable.InputStream $in = null;
            try {
                test.org.omg.CORBA.portable.OutputStream $out = _request ("locateServer", true);
                test.org.omg.PortableInterceptor.ServerIdHelper.write ($out, serverId);
                $out.write_string (endPoint);
                $in = _invoke ($out);
                com.sun.corba.se.PortableActivationIDL.LocatorPackage.ServerLocationPerType $result = com.sun.corba.se.PortableActivationIDL.LocatorPackage.ServerLocationPerTypeHelper.read ($in);
                return $result;
            } catch (test.org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:PortableActivationIDL/NoSuchEndPoint:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.NoSuchEndPointHelper.read ($in);
                else if (_id.equals ("IDL:PortableActivationIDL/ServerNotRegistered:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.ServerNotRegisteredHelper.read ($in);
                else if (_id.equals ("IDL:PortableActivationIDL/ServerHeldDown:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.ServerHeldDownHelper.read ($in);
                else
                    throw new test.org.omg.CORBA.MARSHAL (_id);
            } catch (test.org.omg.CORBA.portable.RemarshalException $rm) {
                return locateServer (serverId, endPoint        );
            } finally {
                _releaseReply ($in);
            }
  } // locateServer


  /** locate server - returns all ports registered with a specified ORB for
  	* an active server
  	* Starts the server if it is not already running.
  	*/
  public com.sun.corba.se.PortableActivationIDL.LocatorPackage.ServerLocationPerORB locateServerForORB (String serverId, String orbId) throws com.sun.corba.se.PortableActivationIDL.InvalidORBid, com.sun.corba.se.PortableActivationIDL.ServerNotRegistered, com.sun.corba.se.PortableActivationIDL.ServerHeldDown
  {
            test.org.omg.CORBA.portable.InputStream $in = null;
            try {
                test.org.omg.CORBA.portable.OutputStream $out = _request ("locateServerForORB", true);
                test.org.omg.PortableInterceptor.ServerIdHelper.write ($out, serverId);
                test.org.omg.PortableInterceptor.ORBIdHelper.write ($out, orbId);
                $in = _invoke ($out);
                com.sun.corba.se.PortableActivationIDL.LocatorPackage.ServerLocationPerORB $result = com.sun.corba.se.PortableActivationIDL.LocatorPackage.ServerLocationPerORBHelper.read ($in);
                return $result;
            } catch (test.org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:PortableActivationIDL/InvalidORBid:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.InvalidORBidHelper.read ($in);
                else if (_id.equals ("IDL:PortableActivationIDL/ServerNotRegistered:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.ServerNotRegisteredHelper.read ($in);
                else if (_id.equals ("IDL:PortableActivationIDL/ServerHeldDown:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.ServerHeldDownHelper.read ($in);
                else
                    throw new test.org.omg.CORBA.MARSHAL (_id);
            } catch (test.org.omg.CORBA.portable.RemarshalException $rm) {
                return locateServerForORB (serverId, orbId        );
            } finally {
                _releaseReply ($in);
            }
  } // locateServerForORB


  /** get the port for the endpoint of the locator
  	*/
  public int getEndpoint (String endPointType) throws com.sun.corba.se.PortableActivationIDL.NoSuchEndPoint
  {
            test.org.omg.CORBA.portable.InputStream $in = null;
            try {
                test.org.omg.CORBA.portable.OutputStream $out = _request ("getEndpoint", true);
                $out.write_string (endPointType);
                $in = _invoke ($out);
                int $result = com.sun.corba.se.PortableActivationIDL.TCPPortHelper.read ($in);
                return $result;
            } catch (test.org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:PortableActivationIDL/NoSuchEndPoint:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.NoSuchEndPointHelper.read ($in);
                else
                    throw new test.org.omg.CORBA.MARSHAL (_id);
            } catch (test.org.omg.CORBA.portable.RemarshalException $rm) {
                return getEndpoint (endPointType        );
            } finally {
                _releaseReply ($in);
            }
  } // getEndpoint


  /** Useful from external BadServerIdHandlers which need
  	* to pick a particular port type.
  	*/
  public int getServerPortForType (com.sun.corba.se.PortableActivationIDL.LocatorPackage.ServerLocationPerORB location, String endPointType) throws com.sun.corba.se.PortableActivationIDL.NoSuchEndPoint
  {
            test.org.omg.CORBA.portable.InputStream $in = null;
            try {
                test.org.omg.CORBA.portable.OutputStream $out = _request ("getServerPortForType", true);
                com.sun.corba.se.PortableActivationIDL.LocatorPackage.ServerLocationPerORBHelper.write ($out, location);
                $out.write_string (endPointType);
                $in = _invoke ($out);
                int $result = com.sun.corba.se.PortableActivationIDL.TCPPortHelper.read ($in);
                return $result;
            } catch (test.org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:PortableActivationIDL/NoSuchEndPoint:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.NoSuchEndPointHelper.read ($in);
                else
                    throw new test.org.omg.CORBA.MARSHAL (_id);
            } catch (test.org.omg.CORBA.portable.RemarshalException $rm) {
                return getServerPortForType (location, endPointType        );
            } finally {
                _releaseReply ($in);
            }
  } // getServerPortForType

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:PortableActivationIDL/Locator:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     test.org.omg.CORBA.ORB orb = test.org.omg.CORBA.ORB.init (args, props);
   try {
     test.org.omg.CORBA.Object obj = orb.string_to_object (str);
     test.org.omg.CORBA.portable.Delegate delegate = ((test.org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     test.org.omg.CORBA.ORB orb = test.org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _LocatorStub
