package main.java;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The FamilyTree class contains the business logic for the dynamic structure of
 * the family tree.
 * 
 * @author Jeevan Tamang
 * @version 2.0
 */
public class FamilyTree {
	/**
	 * The FamilyTreeNode represents a family member in the family tree.
	 */
	public class FamilyTreeNode {

		private String name; // name of family member
		private String identifier;// unique property
		private FamilyTreeNode partner;// links to partner node
		private FamilyTreeNode nextSibling;// links to next sibling
		private FamilyTreeNode firstChild;// links to first child

		/**
		 * This method is an constructor of FamilyTreeNode class. A node will be created
		 * using constructor.
		 * 
		 * @param name       it represents the name of the family member
		 * @param identifier represents the unique values of a family member
		 */
		public FamilyTreeNode(String name, String identifier) {
			this.name = name;
			this.identifier = identifier;
			this.partner = null;
			this.nextSibling = null;
			this.firstChild = null;
		}

		/**
		 * The below methods are getters and setters for the FamilyTreeNode
		 * attributes/properties
		 */
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getIdentifier() {
			return identifier;
		}

		public void setIdentifier(String identifier) {
			this.identifier = identifier;
		}

		public FamilyTreeNode getPartner() {
			return partner;
		}

		public void setPartner(FamilyTreeNode partner) {
			this.partner = partner;
		}

		public FamilyTreeNode getNextSibling() {
			return nextSibling;
		}

		public void setNextSibling(FamilyTreeNode nextSibling) {
			this.nextSibling = nextSibling;
		}

		public FamilyTreeNode getFirstChild() {
			return firstChild;
		}

		public void setFirstChild(FamilyTreeNode firstChild) {
			this.firstChild = firstChild;
		}

	}

	private FamilyTreeNode ancestor;// stores ancestor node
	private static int FMCOUNT = 1;// count variable to generate identifier when adding a family member
	private static int flag = 0;// flag variable to used to show the hierarchies while printing the family tree;
	private static Map<String, FamilyTreeNode> generationMap = new HashMap<String, FamilyTreeNode>();// Map to store
																										// nodes

	/**
	 * FamilyTree() is constructor for the FamilyTree class. It setup the ancestor
	 * node
	 * 
	 * @param ancestorName name of the ancestor
	 */
	public FamilyTree(String ancestorName) {
		this.ancestor = new FamilyTreeNode(ancestorName, "FM1");
		generationMap.put(ancestor.getIdentifier(), ancestor);
	}

	/**
	 * getAncestorName() returns the ancestor name
	 * 
	 * @return string returns name of the ancestor
	 */
	public String getAncestorName() {
		return this.ancestor.getName();
	}

	/**
	 * addParner() adds partner to the desired node.
	 * 
	 * @return String status message
	 */
	public String addPartner(String input, String partnerName) {

		FMCOUNT++;// increase count by 1 as new node is getting added.
		FamilyTreeNode partnerNode = generationMap.get(input);// Extracting node to whom partner to be added
		// creating a new node object and assigning it to partner node
		FamilyTreeNode newPartnerNode = new FamilyTreeNode(partnerName, "FM" + FMCOUNT);
		generationMap.put(newPartnerNode.identifier, newPartnerNode);
		partnerNode.setPartner(newPartnerNode);
		newPartnerNode.setPartner(partnerNode);
		String msg = "Successfully added the "+partnerNode.getPartner().getName()+" to the "+newPartnerNode.getPartner().getName()+" as a partner and vice versa";// success status message
		System.out.println(msg);
		return msg;

	}

	/**
	 * addParner() adds child to the desired node.
	 * 
	 * @param input this is identifier of the desired node.
	 * 
	 * @return String status message
	 */
	public String addChild(String input) {
		String msg;// to store status message
		if (this.checkPartner(input)) {// checks partner existing or not. If not cannot add child.
			msg = "The selected family member has no partner. please select another one";// error message
			System.err.println(msg);
			return msg;
		}
		else {
			String newChildName = Input.getString("Enter child name ->");// new child name getting it from user
			msg= addNewChild(input,newChildName);
		}
		return msg;
	}
	
	public String addNewChild(String input,String newChildName) {
		String msg;
		FamilyTreeNode parentNode = generationMap.get(input);// Extracting node to whom child to be added

		if (parentNode.getFirstChild() == null) {
			FMCOUNT++;
			FamilyTreeNode newChildNode = new FamilyTreeNode(newChildName, "FM" + FMCOUNT);
			FamilyTreeNode partnerNode=parentNode.getPartner();
			parentNode.setFirstChild(newChildNode);
			partnerNode.setFirstChild(newChildNode);
			generationMap.put(newChildNode.getIdentifier(), newChildNode);
			System.out.println("Successfully added as a first child for " + parentNode.getName() + "("
					+ parentNode.getIdentifier() + ") and " + parentNode.getPartner().getName() + "("
					+ parentNode.getPartner().getIdentifier() + ")");
			msg = "Successfully added as a first child";
			return msg;
		}
		// unique name check and adding the child node as sibling to existing children
		// of the parent node.
		if (nameCheck(newChildName, parentNode.getFirstChild())) {
			msg = "Successfully added as a sibling";
			System.out.println(msg);
			return msg;
		} else {
			msg = "This name is already given to a child. Please enter different name";// duplicate name error message
			System.err.println();
			return msg;
		}
	}

	/**
	 * nameCheck() checks uniqueness of new child name.
	 * 
	 * @param newChildName represents the name of the new child
	 * @param child        represents the first child node
	 * @return boolean true if unique or false if duplicate name
	 */
	private boolean nameCheck(String newChildName, FamilyTreeNode child) {
		if (newChildName.equalsIgnoreCase(child.getName())) {
			return false;
		} else if (child.getNextSibling() == null) {
			FMCOUNT++;
			FamilyTreeNode newChildNode = new FamilyTreeNode(newChildName, "FM" + FMCOUNT);
			child.setNextSibling(newChildNode);
			generationMap.put(newChildNode.getIdentifier(), newChildNode);
			return true;
		} else
			nameCheck(newChildName, child.getNextSibling());
		return true;

	}

	/**
	 * toString() prints the string representation of the object
	 * 
	 * @return string returns string representation
	 */
	public String toString() {
		System.out.println("\t***Family Tree***");
		traverseTree(this.ancestor);//invoking the traverse tree method to traverse and print the details.
		flag = 0;
		return "";
	}

	/**
	 *traverseTree() method traverse all the nodes of the desired node 
	 *@param FamilyTreeNode is the desired node to which the traverse is needed
	 */
	private void traverseTree(FamilyTreeNode node) {
		String str = addspace(node.getName() + "(" + node.getIdentifier() + ")");
		System.out.print("\t" + str);
		if (node.getPartner() == null) {//checking the partner node existence
			System.out.println(" has no partner");

		} else {
			FamilyTreeNode partnerNode= node.getPartner();
			str = " partner " + partnerNode.getName() + "(" + partnerNode.getIdentifier() + ")";
			System.out.println(str);
			if (node.getFirstChild() == null) {//checking first child existence
				str = addspace("    no children");
				System.out.println("\t" + str);
			} else {
				flag += 4;//adding 4 spaces to the string to show the difference in hierarchy levels.
				traverseTree(node.firstChild);//traversing through the first child node
				flag -= 4;

			}
		}
		if (node.nextSibling == null)
			return;
		else {

			traverseTree(node.nextSibling);//traversing through the sibling node
		}
	}
	
	/**
	 *displayForIdentifier() prints the family for the requested family member.
	 *@param input represents the identifier of desired family member which is provided by the user. */
	public void displayForIdentifier(String input) {

		FamilyTreeNode parentNode = generationMap.get(input);
		System.out.print("\t" + parentNode.getName() + "(" + parentNode.getIdentifier() + ")");
		if (parentNode.getPartner() == null) {
			System.out.println(" has no partner");
			return;
		} else {
			FamilyTreeNode partnerNode= parentNode.getPartner();
			System.out.println(" partner " + partnerNode.getName() + "(" + partnerNode.getIdentifier() + ")");
			if (parentNode.firstChild != null) {
				flag += 4;
				traverseTree(parentNode.getFirstChild());
				flag -= 4;
			} else
				System.out.println("\t    no children");

		}
	}

	/**
	 * checkPartner() checks whether the desired node is having partner or not.
	 * 
	 * @param input identifier of the desired node.
	 * 
	 * @return boolean return true if no partner and yes if already has a partner
	 */
	public boolean checkPartner(String input) {
		FamilyTreeNode node = generationMap.get(input);
		if (node.getPartner() != null)
			return false;
		else
			return true;

	}

	/**
	 * checkIdenfier() checks whether the given identifier is in family tree or not.
	 * 
	 * @param input identifier of the desired node.
	 * 
	 * @return boolean return true if exists and yes if already has a partner
	 */

	public boolean checkIdentifier(String input) {
		if (generationMap.containsKey(input.toUpperCase()))
			return true;
		return false;
	}

	/**
	 * checkAncestorPartner() checks whether the ancestor is having partner or not.
	 */
	public boolean checkAncestorPartner() {
		if (this.ancestor.getPartner() == null)
			return false;
		return true;
	}

	/**
	 * addspace() checks adds the spaces for the given string.
	 * 
	 * @param str is the input string
	 * 
	 * @return String returns the processed string
	 */
	private String addspace(String str) {
		StringBuilder str1 = new StringBuilder();
		for (int j = 0; j < flag; j++) {
			str1.append(" ");
		}
		str1.append(str);
		return str1.toString();

	}

}
