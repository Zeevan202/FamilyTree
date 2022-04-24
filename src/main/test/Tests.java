package main.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import main.java.FamilyTree;



public class Tests {
	
	public FamilyTree obj=new  FamilyTree("Jhon");
	
	@Test
	public void test1_ancestorsetup() {
		assertEquals("Jhon", this.obj.getAncestorName());		
		System.out.println("testcase1 over");
	}

	@Test
	public void test2_checkIdentifier() {
		assertEquals(true, this.obj.checkIdentifier("FM1"));//positive case
		assertEquals(true, this.obj.checkIdentifier("fm1"));//positive case - identifier not case sensitive
		
		assertEquals(false, this.obj.checkIdentifier("FM5"));//negative case
		assertEquals(false, this.obj.checkIdentifier("fm5"));//negative case - identifier not case sensitive
		System.out.println("testcase2 over");
	}
	
	@Test
	public void test3_AddPartner() {
		assertEquals("Successfully added the Amy to the Jhon as a partner and vice versa", this.obj.addPartner("FM1", "Amy"));
	}
	
	@Test
	public void test4_AddChild() {
		assertEquals("Successfully added as a first child", this.obj.addNewChild("FM2","Jim"));//positive case
	}

}
