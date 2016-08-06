import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Arithmetic {

	private final String plusMinusRegex = "[\\+\\-]";
	
	// 9-(1+2*3)*4-(4+1)
	// 9+7*4-(4+1)
	public double calcute(final String expression) throws Exception {
		StringBuilder expressionBuilder = new StringBuilder(expression);
		Pattern pattern = Pattern.compile("\\([\\d\\+\\-\\*/\\.]+\\)");
		Matcher matcher = pattern.matcher(expression);
		
		while(matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			double r = calWithoutBracket(expressionBuilder.toString().substring(start+1,end-1));
			expressionBuilder.replace(start, end, String.valueOf(r));
			matcher = pattern.matcher(expressionBuilder.toString());
		}
		
		return calWithoutBracket(expressionBuilder.toString());
	}


	// 4+2*3-5*2
	public double calWithoutBracket(final String expression) throws Exception {
		
		double result = 0;
		
		String[] numberStrings = expression.split(plusMinusRegex);
		
		if(numberStrings[0].contains("*") || numberStrings[0].contains("/")) {
			result = multiplyAndDivide(numberStrings[0]);
		} else {
			result = Double.valueOf(numberStrings[0]);
		}
		
		int index = 1;
		Pattern pattern = Pattern.compile(plusMinusRegex);
		Matcher matcher = pattern.matcher(expression);
		while(matcher.find()) {
			String next = numberStrings[index];
			double nextNumber = (next.contains("*") || next.contains("/")) ? multiplyAndDivide(next) : Double.valueOf(next);
			
			String op = matcher.group();
			if(op.equals("+")) {
				result += nextNumber;
			} else if(op.equals("-")) {
				result -= nextNumber;
			}
			index++;
		}
		return result;
	}

	// 3.5/2.3*4.1
	public double multiplyAndDivide(String expression) {
		double result = 1;
		String pString = "[\\*\\/]";

		String[] numberStrings = expression.split(pString);
		if (numberStrings.length > 0) {
			result = Double.valueOf(numberStrings[0]);
		}

		int index = 1;
		Pattern pattern = Pattern.compile(pString);
		Matcher matcher = pattern.matcher(expression);

		while (matcher.find()) {
			String op = matcher.group();
			if (op.equals("*")) {
				result *= Double.valueOf(numberStrings[index]);
			} else if (op.equals("/")) {
				result /= Double.valueOf(numberStrings[index]);
			}
			index++;
		}
		return result;
	}

	// 1.5-2.8+3
	public double plusAndMinus(String expression) {

		double result = 0;
		String pString = "[\\+\\-]";
		
		String[] numberStrings = expression.split(pString);
		if (numberStrings.length > 0) {
			result = Double.valueOf(numberStrings[0]);
		}

		int index = 1;
		Pattern pattern = Pattern.compile(pString);
		Matcher matcher = pattern.matcher(expression);

		while (matcher.find()) {

			String op = matcher.group();
			if (op.equals("+")) {
				result += Double.valueOf(numberStrings[index]);
			} else if (op.equals("-")) {
				result -= Double.valueOf(numberStrings[index]);
			}
			index++;
		}
		return result;
	}

	public boolean isNumber(char ch) {
		return '0' <= ch && '9' >= ch;
	}

	public int charNumToInt(char num) {
		if (num < '0' || num > '9')
			throw new IllegalArgumentException(
					"argument is not a char of decimal number");
		return num - '0';
	}
	
	// ÖÐ×º×ªºó×º
	public double calcute2(final String expression) {
		
		final String suffixExpression = getSuffixExpression(expression);
		
		Stack<String> stack = new Stack<String>();
		for(int i=0; i<suffixExpression.length(); i++) {
			char ch = suffixExpression.charAt(i);
			if(isNumber(ch)) {
				stack.push(String.valueOf(ch));
			} else {
				double rightOperand = Double.valueOf(stack.pop());
				double leftOperand = Double.valueOf(stack.pop());
				if(ch == '+') {
					stack.push(String.valueOf(leftOperand + rightOperand));
				} else if(ch == '-') {
					stack.push(String.valueOf(leftOperand - rightOperand));
				} else if(ch == '*') {
					stack.push(String.valueOf(leftOperand * rightOperand));
				} else if(ch == '/') {
					stack.push(String.valueOf(leftOperand / rightOperand));
				}
			}
		}
		return Double.valueOf(stack.pop());
	}
	
	public String getSuffixExpression(final String expression) {
		StringBuilder suffixExpression = new StringBuilder();
		Stack<Character> operatorStack = new Stack<Character>();
		for(int i = 0;i < expression.length(); i++) {
			char ch = expression.charAt(i);
			if(isNumber(ch)) {
				suffixExpression.append(ch);
				
			} else if(ch == '(') {
				operatorStack.push(ch);
				
			} else if(ch == ')') {
				char topOp;
				while((topOp = operatorStack.pop()) != '(') {
					suffixExpression.append(topOp);
				}
			} else {
				if(!operatorStack.empty()) {
					char topOp = operatorStack.peek();
					while(getPriority(topOp) >= getPriority(ch)) {
						suffixExpression.append(operatorStack.pop());
						if(operatorStack.empty()) {
							break;
						}
						topOp = operatorStack.peek();
					}
				}
				operatorStack.push(ch);
			} 
		}
		while(!operatorStack.empty()) {
			suffixExpression.append(operatorStack.pop());
		}
		
		return suffixExpression.toString();
	}
	
	public int getPriority(char operator) {
		switch (operator) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		default:
			return -1;
		}
	}
}
