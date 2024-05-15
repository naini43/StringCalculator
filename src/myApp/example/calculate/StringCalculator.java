package myApp.example.calculate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringCalculator {

	public static void main (String [] args) {
		
		String exp = "1 + 1";
//		
//		System.out.println(exp + " = " + calculate(exp));
//		
//		exp = "2 + 5 - 1";
//		System.out.println(exp + " = " + calculate(exp));
		
		exp = "2 + 5 * ( 10 / 5 ) - 1 + 10 ";
		System.out.println(exp + " = " + calculate(exp));
		
		exp = "10 - ( 2 + 3 * ( 7 - 5 ) ) ";
		System.out.println(exp + " = " + calculate(exp));
		
	}
	
	public static double calculate(String expString) {
		double sum = 0;
		
		String [] expArr = expString.split(" ");
		try {
			if( checkValidArithmeticOps(expArr)) {
				
				List<String> listExpArr = new ArrayList<String>(Arrays.asList(expArr));
	
				rebuildList(listExpArr);
				sum = calculateNumbersInArray(listExpArr);
	
			}else {
				System.out.println("Error : Its not a valid arithmetic operations.");
				return sum;
			}
		}catch(Exception e) {
			System.out.println("Error : "+ e.getMessage());
			e.printStackTrace();
		}
	
		return sum;
	} 
	
	public static void rebuildList(List<String> listExpArr) throws Exception {		
		
		while(listExpArr.lastIndexOf("(") != -1) {
			calculateInBracketFirst( listExpArr);
		}
		
		while(listExpArr.lastIndexOf("*") != -1) {
			calculateHigherPriority( listExpArr, "*");
		}
		
		while(listExpArr.lastIndexOf("/") != -1) {
			calculateHigherPriority( listExpArr, "/");
		}
		
	}
	
	public static void calculateInBracketFirst(List<String> listExpArr) throws Exception {
		int indexOfOpenBracket = listExpArr.lastIndexOf("(");
		int indexOfClosedBracket = listExpArr.indexOf(")");
		
		if(indexOfOpenBracket != -1 && indexOfClosedBracket == -1) {
			throw new Exception("The bracket is not closed." );
		}
		
		double sum = 0;
		//printListElement(listExpArr) ;
		
		//multiply and division
		int i = 0;
		List<String> listExpArrForBracketMultiplyDivision = new ArrayList<String>();
		for(i=indexOfOpenBracket + 1; i<indexOfClosedBracket; i++) {
			listExpArrForBracketMultiplyDivision.add(listExpArr.get(i));
		}
		
//		printListElement(listExpArrForBracketMultiplyDivision, "All in bracket ") ;
		
		while(listExpArrForBracketMultiplyDivision.lastIndexOf("*") != -1) {
			calculateHigherPriority( listExpArrForBracketMultiplyDivision, "*");
//			printListElement(listExpArrForBracketMultiplyDivision, "Multiplying ") ;
		}
		
		while(listExpArrForBracketMultiplyDivision.lastIndexOf("/") != -1) {
			calculateHigherPriority( listExpArrForBracketMultiplyDivision, "/");
//			printListElement(listExpArrForBracketMultiplyDivision, "Dividing ") ;
		}
//		printListElement(listExpArrForBracketMultiplyDivision, "After divide") ;
		
		
		
		//adding and deduct
		if(listExpArrForBracketMultiplyDivision.size() > 1 ) {
			i=0;
			sum = calculateNumbers(listExpArrForBracketMultiplyDivision.get(i), listExpArrForBracketMultiplyDivision.get(i+2), listExpArrForBracketMultiplyDivision.get(i+1));
//			printListElement(listExpArrForBracketMultiplyDivision, "Add first one ") ;
			
			for(i=i+3; i<listExpArrForBracketMultiplyDivision.size(); i=i+2) {
				sum = calculateNumbers(String.valueOf(sum), listExpArrForBracketMultiplyDivision.get(i+1), listExpArrForBracketMultiplyDivision.get(i));		
//				printListElement(listExpArrForBracketMultiplyDivision, "Add one by one ") ;
			}
			
//			printListElement(listExpArr, "After adding and deduct") ;
		}
		
		listExpArr.set(indexOfOpenBracket, String.valueOf(sum));
		
		for(i= indexOfClosedBracket; i > indexOfOpenBracket; i--) {
//			System.out.println("removing -"+i);
			listExpArr.remove(i);
		}
//		printListElement(listExpArr, "After Removing All in bracket") ;
		
	}
	
	public static void calculateHigherPriority(List<String> listExpArr, String operator) {
		double sum = 0;
		//printListElement(listExpArr) ;
		int indexOfHighOperator = listExpArr.lastIndexOf(operator);
		sum = calculateNumbers(listExpArr.get(indexOfHighOperator-1), listExpArr.get(indexOfHighOperator+1), listExpArr.get(indexOfHighOperator));
		listExpArr.set(indexOfHighOperator, String.valueOf(sum));
		listExpArr.remove((indexOfHighOperator+1));
		listExpArr.remove((indexOfHighOperator-1));	
		

		//printListElement(listExpArr) ;
	}
	
//	public static void printListElement(List<String> listExpArr,String from) {
//		System.out.println("printListElement  " + from);
//		for (String element : listExpArr) { 
//            System.out.print(" "+element); 
//        } 
//		System.out.println(" ");
//	}
	
	public static double calculateNumbersInArray(List<String> listExpArr) {
		double sum = 0;
		
		int i = 0;
		sum = calculateNumbers(listExpArr.get(i), listExpArr.get(i+2), listExpArr.get(i+1));
		//System.out.println("Calculate = " + listExpArr.get(i) +" "+ listExpArr.get(i+1) +" " +listExpArr.get(i+2));
		
		for(i=3; i<listExpArr.size(); i+=2) {
			//System.out.println("Calculate = " + sum +" "+ listExpArr.get(i) +" " +listExpArr.get(i+1));
			sum = calculateNumbers(String.valueOf(sum), listExpArr.get(i+1), listExpArr.get(i));			
			
		}
		
		return sum;
	}
	
	public static double calculateNumbers(String firstNum, String secondNum, String opr) {
		double sum = 0; 
		if(opr.equals("+")) {
			sum = Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
		}else if(opr.equals("-")) {
			sum = Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
		}else if(opr.equals("*")) {
			sum = Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
		}else if(opr.equals("/")) {
			sum = Double.parseDouble(firstNum) / Double.parseDouble(secondNum);
		}
		
		return sum;
		
	}
	
	public static boolean checkValidArithmeticOps(String [] expArr) {
		boolean valid = true;
		
		String lastItem = expArr[expArr.length-1];
		
		if(!checkIsNumber(lastItem)  && !lastItem.equals(")")) {
			return false;
		}
		
		return valid;
		
	}
	
	public static boolean checkIsNumber(String number) {
		boolean isNumber = true;
		
		try {
			double numberInt = Double.parseDouble(number);
		}catch(NumberFormatException nfe) {
			return false;
		}
		return isNumber;
	}
}
