package main.java;

import java.util.InputMismatchException;

/**
 * FamilyTreeTest class is the main class for the implementation of dynamic data
 * structure for family Tree.
 * 
 * @author Jeevan tamang
 * @version 2.0
 */
public class FamilyTreeTest {

	public static FamilyTree familyTreeObj; // Object declaration for FamilyTreeTest class

	public static void main(String[] args) throws Exception {
		String ancestorName = Input.getString("Enter ancestor name ->");
		familyTreeObj = new FamilyTree(ancestorName);
		int option = 0;
		String choice = "no";
		do {
			printMenu();
			option = Input.getInteger("Choose and enter an option");
			try {
				switch (option) {
				case 1: {
					System.out.println(familyTreeObj);// prints whole family tree
					executeCase_1();
					break;
				}
				case 2: {
					System.out.println(familyTreeObj);// prints whole family tree
					if (familyTreeObj.checkAncestorPartner()) {// checking the partner existence for the ancestor
						executeCase_2();// If and only if the ancestor partner exists add child will possible.
					} else {
						System.err.println("Add a partner to the ancestor to activate ADD CHILD functionality ");// err
																													// message
					}
					break;
				}
				case 3:
					System.out.println(familyTreeObj);// whole tree display.
					break;
				case 4://display tree for the given identifier
					String input = Input.getString("Enter family member identifier to whom family tree to display -> ")
							.toUpperCase();
					if (familyTreeObj.checkIdentifier(input))
						familyTreeObj.displayForIdentifier(input);
					else
						System.err
								.println("!!! The given identifier does not exsist. Please check the input value !!!");
					break;
				default:
					throw new Exception("Unexpected value: " + option);
				}

			} catch (InputMismatchException e) {
				System.err.println(e);
			}
			choice = Input
					.getString("If you want to continue please press Y or y or press any other key to discontinue:");
		} while (choice.equalsIgnoreCase("y"));

	}// End of main()

	/*
	 * pringMenu() prints the simple menu for user to choose an operation.
	 */
	private static void printMenu() {
		System.out.println("\n\t1.Add partner " + "\n\t2.Add child" + "\n\t3.Display whole Family Tree"
				+ "\n\t4.Display Family Tree for specific member");
	}// End of printMenu()

	/**
	 * executeCase_1() executes the add partner functionality
	 */
	private static void executeCase_1() {
		String input = Input.getString("Enter family member identifier to whom a partner to be added -> ")
				.toUpperCase();
		// checking the identifier existence in family tree and partner existence
		if (familyTreeObj.checkIdentifier(input) && familyTreeObj.checkPartner(input)) {
			String partnerName = Input.getString("Enter partner name ->");
			familyTreeObj.addPartner(input, partnerName);
		} else
			System.err.println("The given identifier does not exsist");
	}// End of executeCase_1()

	private static void executeCase_2() {
		String input = Input.getString("Enter family member identifier to whom a child need to be added -> ")
				.toUpperCase();
		if (familyTreeObj.checkIdentifier(input))
			familyTreeObj.addChild(input);
		else {
			System.err.println("The given identifier does not exsist. Please check the input value");
		}
	}//End of executeCase_2

}// End of FamilyTreeTest class
