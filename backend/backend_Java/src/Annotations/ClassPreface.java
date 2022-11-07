package Annotations;

public @interface ClassPreface {
	int classID () default -1;
	String Author() default "N/A";
	String LastUpdated() default "N/A";
	String[] Updates() default {};
	
	

}
