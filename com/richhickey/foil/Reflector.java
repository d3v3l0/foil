/*
 * Created on Dec 11, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.richhickey.foil;

import java.lang.reflect.*;
import java.util.*;
/**
 * @author Rich
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Reflector implements IReflector
    {

    /**
     * @author Rich
     *
     */
    public class CallableMethod implements ICallable
        {
        List methods;
        public CallableMethod(List methods)
            {
        	this.methods = methods;
            }
        
        public Object invoke(List args) throws InvocationTargetException
            {
            Object target = args.get(0);
            args = args.subList(1,args.size());
            for(Iterator i = methods.iterator();i.hasNext();)
                {
                Method m = (Method)i.next();
                Class[] params = m.getParameterTypes();
                if(isCongruent(params,args))
                    {
                    Object[] boxedArgs = boxArgs(params,args);
                    try{
                        return m.invoke(target,boxedArgs);
                    	}
                    catch(Exception ex)
                    	{	
                    	throw new InvocationTargetException(ex);
                    	}
                    }
                }
            throw new InvocationTargetException(new Exception("no matching function found"));
            }
        }

    static Object[] boxArgs(Class[] params,List args)
        {
        if(params.length == 0)
            return null;
        Object[] ret = new Object[params.length];
        for(int i=0;i<params.length;i++)
            {
            Object arg = args.get(i);
            Class paramType = params[i]; 
            ret[i] = boxArg(paramType,arg);
            }
        return ret;
        }
    
    static Object boxArg(Class paramType,Object arg)
        {
        if(paramType == boolean.class && arg == null)
                return Boolean.FALSE;
        else
            return arg;
        }
    
    static boolean isCongruent(Class[] params,List args)
        {
        boolean ret = false;
        if(params.length == args.size())
            {
            ret = true;
            for(int i=0;ret && i<params.length;i++)
                {
                Object arg = args.get(i);
                Class argType = (arg == null)?null:arg.getClass();
                Class paramType = params[i]; 
                if(paramType == boolean.class)
                    {
                    ret = arg == null || argType == Boolean.class;
                    }
                else if(paramType.isPrimitive())
                    {
                    if(arg == null)
                        ret = false;
                    else if(paramType == int.class)
                        ret = argType == Integer.class;
                    else if(paramType == double.class)
                        ret = argType == Double.class;
                    else if(paramType == long.class)
                        ret = argType == Long.class;
                    else if(paramType == char.class)
                        ret = argType == Character.class;
                    else if(paramType == short.class)
                        ret = argType == Short.class;
                    else if(paramType == byte.class)
                        ret = argType == Byte.class;
                    }
                else
                    {
                    ret = arg == null 
                    	|| argType == paramType 
                    	|| paramType.isAssignableFrom(argType);
                    }
                }
            }
        return ret;
        }
    
    public ICallable getCallable(int memberType, Class c, String memberName)
            throws Exception
        {
        switch(memberType)
        	{
        	case ICallable.METHOD:
        	    return getMethod(c,memberName);
        	default:
        	    throw new Exception("unsupported member type");
        	}
        }
    
    
    ICallable getMethod(Class c,String method)
    	throws Exception
        {
        Method[] allmethods = c.getMethods();
        ArrayList methods = new ArrayList();
        for(int i=0;i<allmethods.length;i++)
            {
            if(method.equals(allmethods[i].getName()))
                methods.add(allmethods[i]);
            }
        if(methods.size() == 0)
            throw new Exception("no methods found");
        return new CallableMethod(methods);
        }
    }