package com.training.core.config;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;


@Component(service = OsgiConfig.class)
@Designate(ocd=OsgiConfig.Configuration.class)
public class OsgiConfig {
	
	private String first;
	
	private String[] second;
	
	private String third;
	
	private boolean fourth;
	
	@ObjectClassDefinition(name = "Training Osgi Bundle Configuration", 
						   description = "Osgi bundle configuration example for Training project")
	public @interface Configuration {
		
		@AttributeDefinition(name = "First", 
				 			 description = "First description")
		String first() default "First";
		
		@AttributeDefinition(name = "Second", 
	 			 			 description = "Second description")
		String[] second() default {"One", "Two"};
		
		@AttributeDefinition(name = "Third", 
				 			 description = "Third description", 
				 			 options = {@Option(label = "One", value = "1"), @Option(label = "Two", value = "2")})
		String third() default "2";
		
		@AttributeDefinition(name = "Fourth", 
				 			 description = "Fourth description", 
				 			 type = AttributeType.BOOLEAN)
		boolean fourth() default true;
		
	}
	
	@Activate
	public void activate(Configuration config) {
		this.first = config.first();
		this.second = config.second();
		this.third = config.third();
		this.fourth = config.fourth();
	}
	
	public String getFirst() {
		return this.first;
	}
	
	public String[] getSecond() {
		return this.second;
	}
	
	public String getThird() {
		return this.third;
	}
	
	public boolean getFourth() {
		return this.fourth;
	}
	
}
