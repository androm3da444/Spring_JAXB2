package com.source;


import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.util.HashMap; 
import java.util.Map; 
import javax.xml.bind.Marshaller; 
import javax.xml.transform.stream.StreamResult; 
import javax.xml.transform.stream.StreamSource; 
import org.springframework.context.ApplicationContext; 
import org.springframework.context.support.ClassPathXmlApplicationContext; 
import org.springframework.oxm.jaxb.Jaxb2Marshaller; 
	
public class Main {


	public static void main(String[] args) throws Exception { 
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml"); 
		Jaxb2Marshaller jaxbMarshaller = (Jaxb2Marshaller)context.getBean("jaxbMarshaller"); 
		Map marshallerProperties = new HashMap(); 
		marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true); 
		jaxbMarshaller.setMarshallerProperties(marshallerProperties); 
		object2Xml(jaxbMarshaller); 
		xml2Object(jaxbMarshaller); 
	} 


	private static void object2Xml(Jaxb2Marshaller jaxbMarshaller) throws Exception{ 
		Department department = new Department(); 
		department.setId("IT"); 
		department.setName("IT Department"); 
		Employee employee = new Employee("12345", "Johnson"); 
		employee.setDepartment(department); 
		FileOutputStream outputStream = new FileOutputStream(new File("employee.xml")); 
		StreamResult result = new StreamResult(outputStream); 
		jaxbMarshaller.marshal(employee, result); 
	} 

	private static void xml2Object(Jaxb2Marshaller jaxbMarshaller) throws Exception{ 
		FileInputStream inputStream = new FileInputStream(new File("employee.xml")); 
		StreamSource source = new StreamSource(inputStream); 
		Employee employee = (Employee)jaxbMarshaller.unmarshal(source); 
		System.out.println("Emp Id is :"+ employee.getId()); 
		System.out.println("Emp Name is :"+ employee.getName()); 
		Department department = employee.getDepartment(); 
		System.out.println("Dept Id is:"+ department.getId()); 
		System.out.println("Dept Name is :"+ department.getName()); 
	} 
}
