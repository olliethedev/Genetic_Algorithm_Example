package other;

import java.lang.reflect.Constructor;

/**
 *
 * @author Oleksiy
 */
public class ByteConverter 
{
    public static final int CHAR=8;
    public static final int SHORT =16;
    public static final int INT = 32;
    public static final int LONG =64;
    
    public static char bytesToChar(byte[] b, int offset)
    {
        return (char)(b[offset]);
    }
    
    public static short bytesToShort(byte[] b, int offset)
    {
        
        short ret = 0;
        for (int i=2; i>=0; i--) 
        {
          ret <<= 8;
          ret |= (short)b[i+offset] & 0xFF;
        }
        return ret;
        /*
        return (short)((b[1]<<8)|b[0]);*/
    }
    public static int bytesToInt(byte[] b, int offset)
    {
        int ret = 0;
        for (int i=3; i>0; i--) 
        {
          //System.out.println("i:"+i);
          ret <<= 8;
          ret |= (int)b[i+offset] & 0xFF;
        }
        return ret;
        /*return (int)((b[3]<<24)|(b[2]<<16)|(b[1]<<8)|b[0]);*/
    }
    public static long bytesToLong(byte[] b, int offset)
    {
        long ret = 0;
        for (int i=8; i>=0; i--) 
        {
          ret <<= 8;
          ret |= (long)b[i+offset] & 0xFF;
        }
        return ret;
    }
    
    
    public static <T> T bytesToClass(byte[] b, Class<?> c) throws Exception
    {
        Constructor[] cons=c.getConstructors();
        if(cons.length==1&&cons[0].getParameterTypes().length>0)
        {

            Class[] classArr =cons[0].getParameterTypes();
            Object[] constructorParams = new Object[classArr.length];
            int offset=0;
            for(int i =0; i<classArr.length; i++)
            {
                //Debug.print("constructor param:"+classArr[i].toString());
                if(classArr[i]==char.class)
                {
                    constructorParams[i]=bytesToChar(b, offset);
                    offset+=CHAR/8;
                }
                else if(classArr[i] == short.class)
                {
                    constructorParams[i] = bytesToShort(b,offset);
                    offset+=SHORT/8;
                }
                else if(classArr[i]== int.class)
                {
                    constructorParams[i] = bytesToInt(b, offset);
                    offset+=INT/8;
                }
                else if(classArr[i] == long.class)
                {
                    constructorParams[i]=bytesToLong(b,offset);
                    offset+=LONG/8;
                }           
                else
                {
                    System.out.println(classArr[i].getSimpleName());
                    throw new Exception("unknown type!");
                }
            }
            return (T)cons[0].newInstance(constructorParams);    
        }
        else
        {
            System.out.println("Invalid class");
            return null;
        }
    }
}
