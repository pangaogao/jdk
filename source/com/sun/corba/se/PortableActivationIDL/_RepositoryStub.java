package com.sun.corba.se.PortableActivationIDL;


/**
* com/sun/corba/se/PortableActivationIDL/_RepositoryStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /Users/java_re/workspace/8-2-build-macosx-x86_64/jdk8u131/8869/corba/src/share/classes/com/sun/corba/se/PortableActivationIDL/activation.idl
* Wednesday, March 15, 2017 1:33:57 AM PDT
*/

public class _RepositoryStub extends test.org.omg.CORBA.portable.ObjectImpl implements com.sun.corba.se.PortableActivationIDL.Repository
{


  /** register server definition.
  	* This returns the serverId of the server.  A newly created server is
  	* always uninstalled.
  	*/
  public String registerServer (com.sun.corba.se.PortableActivationIDL.RepositoryPackage.ServerDef serverDef) throws com.sun.corba.se.PortableActivationIDL.ServerAlreadyRegistered, com.sun.corba.se.PortableActivationIDL.BadServerDefinition
  {
            test.org.omg.CORBA.portable.InputStream $in = null;
            try {
                test.org.omg.CORBA.portable.OutputStream $out = _request ("registerServer", true);
                com.sun.corba.se.PortableActivationIDL.RepositoryPackage.ServerDefHelper.write ($out, serverDef);
                $in = _invoke ($out);
                String $result = test.org.omg.PortableInterceptor.ServerIdHelper.read ($in);
                return $result;
            } catch (test.org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:PortableActivationIDL/ServerAlreadyRegistered:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.ServerAlreadyRegisteredHelper.read ($in);
                else if (_id.equals ("IDL:PortableActivationIDL/BadServerDefinition:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.BadServerDefinitionHelper.read ($in);
                else
                    throw new test.org.omg.CORBA.MARSHAL (_id);
            } catch (test.org.omg.CORBA.portable.RemarshalException $rm) {
                return registerServer (serverDef        );
            } finally {
                _releaseReply ($in);
            }
  } // registerServer


  /** unregister server definition
  	*/
  public void unregisterServer (String serverId) throws com.sun.corba.se.PortableActivationIDL.ServerNotRegistered
  {
            test.org.omg.CORBA.portable.InputStream $in = null;
            try {
                test.org.omg.CORBA.portable.OutputStream $out = _request ("unregisterServer", true);
                test.org.omg.PortableInterceptor.ServerIdHelper.write ($out, serverId);
                $in = _invoke ($out);
                return;
            } catch (test.org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:PortableActivationIDL/ServerNotRegistered:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.ServerNotRegisteredHelper.read ($in);
                else
                    throw new test.org.omg.CORBA.MARSHAL (_id);
            } catch (test.org.omg.CORBA.portable.RemarshalException $rm) {
                unregisterServer (serverId        );
            } finally {
                _releaseReply ($in);
            }
  } // unregisterServer


  /** get server definition
  	*/
  public com.sun.corba.se.PortableActivationIDL.RepositoryPackage.ServerDef getServer (String serverId) throws com.sun.corba.se.PortableActivationIDL.ServerNotRegistered
  {
            test.org.omg.CORBA.portable.InputStream $in = null;
            try {
                test.org.omg.CORBA.portable.OutputStream $out = _request ("getServer", true);
                test.org.omg.PortableInterceptor.ServerIdHelper.write ($out, serverId);
                $in = _invoke ($out);
                com.sun.corba.se.PortableActivationIDL.RepositoryPackage.ServerDef $result = com.sun.corba.se.PortableActivationIDL.RepositoryPackage.ServerDefHelper.read ($in);
                return $result;
            } catch (test.org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:PortableActivationIDL/ServerNotRegistered:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.ServerNotRegisteredHelper.read ($in);
                else
                    throw new test.org.omg.CORBA.MARSHAL (_id);
            } catch (test.org.omg.CORBA.portable.RemarshalException $rm) {
                return getServer (serverId        );
            } finally {
                _releaseReply ($in);
            }
  } // getServer


  /** Return whether the server has been installed
  	*/
  public boolean isInstalled (String serverId) throws com.sun.corba.se.PortableActivationIDL.ServerNotRegistered
  {
            test.org.omg.CORBA.portable.InputStream $in = null;
            try {
                test.org.omg.CORBA.portable.OutputStream $out = _request ("isInstalled", true);
                test.org.omg.PortableInterceptor.ServerIdHelper.write ($out, serverId);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (test.org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:PortableActivationIDL/ServerNotRegistered:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.ServerNotRegisteredHelper.read ($in);
                else
                    throw new test.org.omg.CORBA.MARSHAL (_id);
            } catch (test.org.omg.CORBA.portable.RemarshalException $rm) {
                return isInstalled (serverId        );
            } finally {
                _releaseReply ($in);
            }
  } // isInstalled


  /** Mark the server as being installed.  Raises ServerAlreadyInstalled
  	* if the server is currently marked as installed.
  	*/
  public void install (String serverId) throws com.sun.corba.se.PortableActivationIDL.ServerNotRegistered, com.sun.corba.se.PortableActivationIDL.ServerAlreadyInstalled
  {
            test.org.omg.CORBA.portable.InputStream $in = null;
            try {
                test.org.omg.CORBA.portable.OutputStream $out = _request ("install", true);
                test.org.omg.PortableInterceptor.ServerIdHelper.write ($out, serverId);
                $in = _invoke ($out);
                return;
            } catch (test.org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:PortableActivationIDL/ServerNotRegistered:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.ServerNotRegisteredHelper.read ($in);
                else if (_id.equals ("IDL:PortableActivationIDL/ServerAlreadyInstalled:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.ServerAlreadyInstalledHelper.read ($in);
                else
                    throw new test.org.omg.CORBA.MARSHAL (_id);
            } catch (test.org.omg.CORBA.portable.RemarshalException $rm) {
                install (serverId        );
            } finally {
                _releaseReply ($in);
            }
  } // install


  /** Mark the server as being uninstalled.  Raises ServerAlreadyUninstalled
  	* if the server is currently marked as uninstalled.
  	*/
  public void uninstall (String serverId) throws com.sun.corba.se.PortableActivationIDL.ServerNotRegistered, com.sun.corba.se.PortableActivationIDL.ServerAlreadyUninstalled
  {
            test.org.omg.CORBA.portable.InputStream $in = null;
            try {
                test.org.omg.CORBA.portable.OutputStream $out = _request ("uninstall", true);
                test.org.omg.PortableInterceptor.ServerIdHelper.write ($out, serverId);
                $in = _invoke ($out);
                return;
            } catch (test.org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:PortableActivationIDL/ServerNotRegistered:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.ServerNotRegisteredHelper.read ($in);
                else if (_id.equals ("IDL:PortableActivationIDL/ServerAlreadyUninstalled:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.ServerAlreadyUninstalledHelper.read ($in);
                else
                    throw new test.org.omg.CORBA.MARSHAL (_id);
            } catch (test.org.omg.CORBA.portable.RemarshalException $rm) {
                uninstall (serverId        );
            } finally {
                _releaseReply ($in);
            }
  } // uninstall


  /** list registered servers
  	*/
  public String[] listRegisteredServers ()
  {
            test.org.omg.CORBA.portable.InputStream $in = null;
            try {
                test.org.omg.CORBA.portable.OutputStream $out = _request ("listRegisteredServers", true);
                $in = _invoke ($out);
                String $result[] = com.sun.corba.se.PortableActivationIDL.ServerIdsHelper.read ($in);
                return $result;
            } catch (test.org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new test.org.omg.CORBA.MARSHAL (_id);
            } catch (test.org.omg.CORBA.portable.RemarshalException $rm) {
                return listRegisteredServers (        );
            } finally {
                _releaseReply ($in);
            }
  } // listRegisteredServers


  /** Returns list of ALL applicationNames defined in ServerDefs of registered 
  	* servers.
  	*/
  public String[] getApplicationNames ()
  {
            test.org.omg.CORBA.portable.InputStream $in = null;
            try {
                test.org.omg.CORBA.portable.OutputStream $out = _request ("getApplicationNames", true);
                $in = _invoke ($out);
                String $result[] = com.sun.corba.se.PortableActivationIDL.RepositoryPackage.AppNamesHelper.read ($in);
                return $result;
            } catch (test.org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new test.org.omg.CORBA.MARSHAL (_id);
            } catch (test.org.omg.CORBA.portable.RemarshalException $rm) {
                return getApplicationNames (        );
            } finally {
                _releaseReply ($in);
            }
  } // getApplicationNames


  /** Find the ServerID associated with the given application name.
  	*/
  public String getServerID (String applicationName) throws com.sun.corba.se.PortableActivationIDL.ServerNotRegistered
  {
            test.org.omg.CORBA.portable.InputStream $in = null;
            try {
                test.org.omg.CORBA.portable.OutputStream $out = _request ("getServerID", true);
                $out.write_string (applicationName);
                $in = _invoke ($out);
                String $result = test.org.omg.PortableInterceptor.ServerIdHelper.read ($in);
                return $result;
            } catch (test.org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:PortableActivationIDL/ServerNotRegistered:1.0"))
                    throw com.sun.corba.se.PortableActivationIDL.ServerNotRegisteredHelper.read ($in);
                else
                    throw new test.org.omg.CORBA.MARSHAL (_id);
            } catch (test.org.omg.CORBA.portable.RemarshalException $rm) {
                return getServerID (applicationName        );
            } finally {
                _releaseReply ($in);
            }
  } // getServerID

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:PortableActivationIDL/Repository:1.0"};

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
} // class _RepositoryStub
