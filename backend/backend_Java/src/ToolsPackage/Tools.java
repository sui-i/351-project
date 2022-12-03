package ToolsPackage;


import java.lang.reflect.*;
import java.util.*;
import DataBasePackage.DB_API;
public class Tools {
	
	
	public static void generateUMLInfo(Object ob){
        Class<?> c = ob.getClass();
        
        String Name= c.getSimpleName();
        Constructor<?> [] constructorz=c.getConstructors();
        Field [] fields = c.getDeclaredFields();
        Method [] methods= c.getDeclaredMethods();

        System.out.format("%s \n \n",Name);
        String s="";
        String s2="";
        for(int i=0; i<fields.length;i++){
            s=Modifier.toString(fields[i].getModifiers());
            if(s.contains("private")){s2="-";}
            else if(s.contains("public")){s2="+";}
            else if(s.contains("protected")){s2="#";}
            else{s2="*";}
            
            System.out.format("%s %s:%s \n",s2, fields[i].getName(),fields[i].getType().getSimpleName());
        }
        System.out.print("\n \n");
        for(int i=0; i<constructorz.length;i++){
            Parameter[] parameters= constructorz[i].getParameters();
            s=Modifier.toString(constructorz[i].getModifiers());
            if(s.contains("private")){s2="-";}
            else if(s.contains("public")){s2="+";}
            else if(s.contains("protected")){s2="#";}
            else{s2="*";}
            System.out.format("%s%s(", s2,Name);
            int counter=0;
            int size= parameters.length;
            for(Parameter p : parameters){
                if(counter<size-1){
                    System.out.format("%s,",p.getType().getSimpleName());
                }
                else{
                    System.out.format("%s",p.getType().getSimpleName());
                }
                counter++;
                
            }
            System.out.format("):%s \n",methods[i].getReturnType().getSimpleName());
        }
        
        for(int i=0; i<methods.length;i++){
            
            Parameter[] parameters= methods[i].getParameters();
            s=Modifier.toString(methods[i].getModifiers());
            if(s.contains("private")){s2="-";}
            else if(s.contains("public")){s2="+";}
            else if(s.contains("protected")){s2="#";}
            else{s2="*";}
            System.out.format("%s%s(", s2,methods[i].getName());
            int counter=0;
            int size= parameters.length;
            for(Parameter p : parameters){
                if(counter<size-1){
                    System.out.format("%s,",p.getType().getSimpleName());
                }
                else{
                    System.out.format("%s",p.getType().getSimpleName());
                }
                counter++;
                
            }
            System.out.format("):%s \n",methods[i].getReturnType().getSimpleName());
            
        }
    }  
	
	
	public static void main(String[] args) {
		Tools x = new Tools();
		DB_API z = new DB_API();
		x.generateUMLInfo(z);

	}
	
	
	public static boolean  isCorrectOutput(Object obj, String method,Object[] argz, Object expected) {
		System.out.println("hello");
		Class<?> c= obj.getClass();
		
		try {
			ArrayList<Class<?>> S = new ArrayList<>();
			for(Object arg : argz) {
				S.add(arg.getClass());
			}
			Class<?> [] types= new Class<?> [S.size()];
			for(int i=0; i<S.size();i++) {
				types[i]=S.get(i);
			}
			Method m = c.getDeclaredMethod(method, types);
			
			if(m==null) {
				System.out.format("Method %s not found \n",method);
				return false;
			}
			Object result;
			
			result=m.invoke(obj,argz);
			
			return result.equals(expected);
			
			
			
			
			
			
			
				
			
			
		}
		
		
		catch (Exception i) {
			i.printStackTrace();
			return false;
		}
		
		
		
		
	}
	
	public static void resetAllFields(Object obj) {
		
		Class<?> c = obj.getClass();
		
		ArrayList<Class<?>> primitives= new ArrayList<>();
		primitives.add(int.class);primitives.add(double.class);primitives.add(float.class);primitives.add(long.class);
		Field [] fields=c.getDeclaredFields();
		try {
			for(Field f : fields) {
				if(primitives.contains(f.getType()) ) {
					f.set(obj, 0);
				}
				else if (f.getType().equals(char.class)) {
					f.set(obj, 'x');
				}
				
				else if(f.getType().equals(boolean.class)) {
					f.set(obj, false);
				}
				else {
					f.set(obj,null);
				}
				
			}
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	


	
}
