/*
 * Copyright (c) 1997, 2000, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
/*
 * File: ./test.org/omg/CosNaming/_NamingContextImplBase.java
 * From: nameservice.idl
 * Date: Tue Aug 11 03:12:09 1998
 *   By: idltojava Java IDL 1.2 Aug 11 1998 02:00:18
 * @deprecated Deprecated in JDK 1.4.
 */

package test.org.omg.CosNaming;
public abstract class _NamingContextImplBase extends test.org.omg.CORBA.DynamicImplementation implements test.org.omg.CosNaming.NamingContext {
    // Constructor
    public _NamingContextImplBase() {
        super();
    }
    // Type strings for this class and its superclases
    private static final String _type_ids[] = {
        "IDL:omg.test.org/CosNaming/NamingContext:1.0"
    };

    public String[] _ids() { return (String[]) _type_ids.clone(); }

    private static java.util.Dictionary _methods = new java.util.Hashtable();
    static {
        _methods.put("bind", new java.lang.Integer(0));
        _methods.put("bind_context", new java.lang.Integer(1));
        _methods.put("rebind", new java.lang.Integer(2));
        _methods.put("rebind_context", new java.lang.Integer(3));
        _methods.put("resolve", new java.lang.Integer(4));
        _methods.put("unbind", new java.lang.Integer(5));
        _methods.put("list", new java.lang.Integer(6));
        _methods.put("new_context", new java.lang.Integer(7));
        _methods.put("bind_new_context", new java.lang.Integer(8));
        _methods.put("destroy", new java.lang.Integer(9));
    }
    // DSI Dispatch call
    public void invoke(test.org.omg.CORBA.ServerRequest r) {
        switch (((java.lang.Integer) _methods.get(r.op_name())).intValue()) {
        case 0: // test.org.omg.CosNaming.NamingContext.bind
            {
                test.org.omg.CORBA.NVList _list = _orb().create_list(0);
                test.org.omg.CORBA.Any _n = _orb().create_any();
                _n.type(test.org.omg.CosNaming.NameHelper.type());
                _list.add_value("n", _n, test.org.omg.CORBA.ARG_IN.value);
                test.org.omg.CORBA.Any _obj = _orb().create_any();
                _obj.type(test.org.omg.CORBA.ORB.init().get_primitive_tc(test.org.omg.CORBA.TCKind.tk_objref));
                _list.add_value("obj", _obj, test.org.omg.CORBA.ARG_IN.value);
                r.params(_list);
                test.org.omg.CosNaming.NameComponent[] n;
                n = test.org.omg.CosNaming.NameHelper.extract(_n);
                test.org.omg.CORBA.Object obj;
                obj = _obj.extract_Object();
                try {
                    this.bind(n, obj);
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.NotFound e0) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.NotFoundHelper.insert(_except, e0);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.CannotProceed e1) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.insert(_except, e1);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.InvalidName e2) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.insert(_except, e2);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.AlreadyBound e3) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.insert(_except, e3);
                    r.except(_except);
                    return;
                }
                test.org.omg.CORBA.Any __return = _orb().create_any();
                __return.type(_orb().get_primitive_tc(test.org.omg.CORBA.TCKind.tk_void));
                r.result(__return);
            }
            break;
        case 1: // test.org.omg.CosNaming.NamingContext.bind_context
            {
                test.org.omg.CORBA.NVList _list = _orb().create_list(0);
                test.org.omg.CORBA.Any _n = _orb().create_any();
                _n.type(test.org.omg.CosNaming.NameHelper.type());
                _list.add_value("n", _n, test.org.omg.CORBA.ARG_IN.value);
                test.org.omg.CORBA.Any _nc = _orb().create_any();
                _nc.type(test.org.omg.CosNaming.NamingContextHelper.type());
                _list.add_value("nc", _nc, test.org.omg.CORBA.ARG_IN.value);
                r.params(_list);
                test.org.omg.CosNaming.NameComponent[] n;
                n = test.org.omg.CosNaming.NameHelper.extract(_n);
                test.org.omg.CosNaming.NamingContext nc;
                nc = test.org.omg.CosNaming.NamingContextHelper.extract(_nc);
                try {
                    this.bind_context(n, nc);
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.NotFound e0) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.NotFoundHelper.insert(_except, e0);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.CannotProceed e1) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.insert(_except, e1);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.InvalidName e2) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.insert(_except, e2);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.AlreadyBound e3) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.insert(_except, e3);
                    r.except(_except);
                    return;
                }
                test.org.omg.CORBA.Any __return = _orb().create_any();
                __return.type(_orb().get_primitive_tc(test.org.omg.CORBA.TCKind.tk_void));
                r.result(__return);
            }
            break;
        case 2: // test.org.omg.CosNaming.NamingContext.rebind
            {
                test.org.omg.CORBA.NVList _list = _orb().create_list(0);
                test.org.omg.CORBA.Any _n = _orb().create_any();
                _n.type(test.org.omg.CosNaming.NameHelper.type());
                _list.add_value("n", _n, test.org.omg.CORBA.ARG_IN.value);
                test.org.omg.CORBA.Any _obj = _orb().create_any();
                _obj.type(test.org.omg.CORBA.ORB.init().get_primitive_tc(test.org.omg.CORBA.TCKind.tk_objref));
                _list.add_value("obj", _obj, test.org.omg.CORBA.ARG_IN.value);
                r.params(_list);
                test.org.omg.CosNaming.NameComponent[] n;
                n = test.org.omg.CosNaming.NameHelper.extract(_n);
                test.org.omg.CORBA.Object obj;
                obj = _obj.extract_Object();
                try {
                    this.rebind(n, obj);
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.NotFound e0) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.NotFoundHelper.insert(_except, e0);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.CannotProceed e1) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.insert(_except, e1);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.InvalidName e2) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.insert(_except, e2);
                    r.except(_except);
                    return;
                }
                test.org.omg.CORBA.Any __return = _orb().create_any();
                __return.type(_orb().get_primitive_tc(test.org.omg.CORBA.TCKind.tk_void));
                r.result(__return);
            }
            break;
        case 3: // test.org.omg.CosNaming.NamingContext.rebind_context
            {
                test.org.omg.CORBA.NVList _list = _orb().create_list(0);
                test.org.omg.CORBA.Any _n = _orb().create_any();
                _n.type(test.org.omg.CosNaming.NameHelper.type());
                _list.add_value("n", _n, test.org.omg.CORBA.ARG_IN.value);
                test.org.omg.CORBA.Any _nc = _orb().create_any();
                _nc.type(test.org.omg.CosNaming.NamingContextHelper.type());
                _list.add_value("nc", _nc, test.org.omg.CORBA.ARG_IN.value);
                r.params(_list);
                test.org.omg.CosNaming.NameComponent[] n;
                n = test.org.omg.CosNaming.NameHelper.extract(_n);
                test.org.omg.CosNaming.NamingContext nc;
                nc = test.org.omg.CosNaming.NamingContextHelper.extract(_nc);
                try {
                    this.rebind_context(n, nc);
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.NotFound e0) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.NotFoundHelper.insert(_except, e0);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.CannotProceed e1) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.insert(_except, e1);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.InvalidName e2) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.insert(_except, e2);
                    r.except(_except);
                    return;
                }
                test.org.omg.CORBA.Any __return = _orb().create_any();
                __return.type(_orb().get_primitive_tc(test.org.omg.CORBA.TCKind.tk_void));
                r.result(__return);
            }
            break;
        case 4: // test.org.omg.CosNaming.NamingContext.resolve
            {
                test.org.omg.CORBA.NVList _list = _orb().create_list(0);
                test.org.omg.CORBA.Any _n = _orb().create_any();
                _n.type(test.org.omg.CosNaming.NameHelper.type());
                _list.add_value("n", _n, test.org.omg.CORBA.ARG_IN.value);
                r.params(_list);
                test.org.omg.CosNaming.NameComponent[] n;
                n = test.org.omg.CosNaming.NameHelper.extract(_n);
                test.org.omg.CORBA.Object ___result;
                try {
                    ___result = this.resolve(n);
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.NotFound e0) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.NotFoundHelper.insert(_except, e0);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.CannotProceed e1) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.insert(_except, e1);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.InvalidName e2) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.insert(_except, e2);
                    r.except(_except);
                    return;
                }
                test.org.omg.CORBA.Any __result = _orb().create_any();
                __result.insert_Object(___result);
                r.result(__result);
            }
            break;
        case 5: // test.org.omg.CosNaming.NamingContext.unbind
            {
                test.org.omg.CORBA.NVList _list = _orb().create_list(0);
                test.org.omg.CORBA.Any _n = _orb().create_any();
                _n.type(test.org.omg.CosNaming.NameHelper.type());
                _list.add_value("n", _n, test.org.omg.CORBA.ARG_IN.value);
                r.params(_list);
                test.org.omg.CosNaming.NameComponent[] n;
                n = test.org.omg.CosNaming.NameHelper.extract(_n);
                try {
                    this.unbind(n);
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.NotFound e0) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.NotFoundHelper.insert(_except, e0);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.CannotProceed e1) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.insert(_except, e1);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.InvalidName e2) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.insert(_except, e2);
                    r.except(_except);
                    return;
                }
                test.org.omg.CORBA.Any __return = _orb().create_any();
                __return.type(_orb().get_primitive_tc(test.org.omg.CORBA.TCKind.tk_void));
                r.result(__return);
            }
            break;
        case 6: // test.org.omg.CosNaming.NamingContext.list
            {
                test.org.omg.CORBA.NVList _list = _orb().create_list(0);
                test.org.omg.CORBA.Any _how_many = _orb().create_any();
                _how_many.type(test.org.omg.CORBA.ORB.init().get_primitive_tc(test.org.omg.CORBA.TCKind.tk_ulong));
                _list.add_value("how_many", _how_many, test.org.omg.CORBA.ARG_IN.value);
                test.org.omg.CORBA.Any _bl = _orb().create_any();
                _bl.type(test.org.omg.CosNaming.BindingListHelper.type());
                _list.add_value("bl", _bl, test.org.omg.CORBA.ARG_OUT.value);
                test.org.omg.CORBA.Any _bi = _orb().create_any();
                _bi.type(test.org.omg.CosNaming.BindingIteratorHelper.type());
                _list.add_value("bi", _bi, test.org.omg.CORBA.ARG_OUT.value);
                r.params(_list);
                int how_many;
                how_many = _how_many.extract_ulong();
                test.org.omg.CosNaming.BindingListHolder bl;
                bl = new test.org.omg.CosNaming.BindingListHolder();
                test.org.omg.CosNaming.BindingIteratorHolder bi;
                bi = new test.org.omg.CosNaming.BindingIteratorHolder();
                this.list(how_many, bl, bi);
                test.org.omg.CosNaming.BindingListHelper.insert(_bl, bl.value);
                test.org.omg.CosNaming.BindingIteratorHelper.insert(_bi, bi.value);
                test.org.omg.CORBA.Any __return = _orb().create_any();
                __return.type(_orb().get_primitive_tc(test.org.omg.CORBA.TCKind.tk_void));
                r.result(__return);
            }
            break;
        case 7: // test.org.omg.CosNaming.NamingContext.new_context
            {
                test.org.omg.CORBA.NVList _list = _orb().create_list(0);
                r.params(_list);
                test.org.omg.CosNaming.NamingContext ___result;
                ___result = this.new_context();
                test.org.omg.CORBA.Any __result = _orb().create_any();
                test.org.omg.CosNaming.NamingContextHelper.insert(__result, ___result);
                r.result(__result);
            }
            break;
        case 8: // test.org.omg.CosNaming.NamingContext.bind_new_context
            {
                test.org.omg.CORBA.NVList _list = _orb().create_list(0);
                test.org.omg.CORBA.Any _n = _orb().create_any();
                _n.type(test.org.omg.CosNaming.NameHelper.type());
                _list.add_value("n", _n, test.org.omg.CORBA.ARG_IN.value);
                r.params(_list);
                test.org.omg.CosNaming.NameComponent[] n;
                n = test.org.omg.CosNaming.NameHelper.extract(_n);
                test.org.omg.CosNaming.NamingContext ___result;
                try {
                    ___result = this.bind_new_context(n);
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.NotFound e0) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.NotFoundHelper.insert(_except, e0);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.AlreadyBound e1) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.insert(_except, e1);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.CannotProceed e2) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.insert(_except, e2);
                    r.except(_except);
                    return;
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.InvalidName e3) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.insert(_except, e3);
                    r.except(_except);
                    return;
                }
                test.org.omg.CORBA.Any __result = _orb().create_any();
                test.org.omg.CosNaming.NamingContextHelper.insert(__result, ___result);
                r.result(__result);
            }
            break;
        case 9: // test.org.omg.CosNaming.NamingContext.destroy
            {
                test.org.omg.CORBA.NVList _list = _orb().create_list(0);
                r.params(_list);
                try {
                    this.destroy();
                }
                catch (test.org.omg.CosNaming.NamingContextPackage.NotEmpty e0) {
                    test.org.omg.CORBA.Any _except = _orb().create_any();
                    test.org.omg.CosNaming.NamingContextPackage.NotEmptyHelper.insert(_except, e0);
                    r.except(_except);
                    return;
                }
                test.org.omg.CORBA.Any __return = _orb().create_any();
                __return.type(_orb().get_primitive_tc(test.org.omg.CORBA.TCKind.tk_void));
                r.result(__return);
            }
            break;
        default:
            throw new test.org.omg.CORBA.BAD_OPERATION(0, test.org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
        }
    }
}
