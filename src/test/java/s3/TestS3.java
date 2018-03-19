package s3;

import org.testng.annotations.Test;

import java.util.Stack;

/**
 * @author bsun
 */
public class TestS3 {

    @Test
    public void test() {
        int index = 0;
        Stack<Integer> stack = new Stack<>();
//        while(true) {
            index++;
            System.out.println(index);
            stack.push(index);
//        }
    }
    /**
     * The input would be like:
     * 30[ab2[cd]]
     * @param input
     * @return
     */
    public String testCase(String input) {
        // dc 02

        int index = 0;

        Stack<String> stack = new Stack<>();
        StringBuilder timeBuilder = new StringBuilder();
        StringBuilder contentBuilder = new StringBuilder();

        while (index < input.length()) {
            char currentChar = input.charAt(index);

            if (Character.isDigit(currentChar)) {
                if (contentBuilder.length() != 0) {
                    stack.push(contentBuilder.toString());
                }
                timeBuilder.append(currentChar);
                continue;
            } else if (currentChar == '['){
                stack.push(timeBuilder.toString());

            } else if (currentChar == ']') {
                String content = contentBuilder.toString();
                int time = Integer.valueOf(stack.pop());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < time; i++) {
                    sb.append(content);
                }
                stack.push(sb.toString());
                contentBuilder.setLength(0);
            } else {
                contentBuilder.append(currentChar);
            }
        }

       return stack.pop();
    }

    public class Solution {
        public String decodeString(String s) {
            String res = "";
            Stack<Integer> countStack = new Stack<>();
            Stack<String> resStack = new Stack<>();
            int idx = 0;
            while (idx < s.length()) {
                if (Character.isDigit(s.charAt(idx))) {
                    int count = 0;
                    while (Character.isDigit(s.charAt(idx))) {
                        count = 10 * count + (s.charAt(idx) - '0');
                        idx++;
                    }
                    countStack.push(count);
                }
                else if (s.charAt(idx) == '[') {
                    resStack.push(res);
                    res = "";
                    idx++;
                }
                else if (s.charAt(idx) == ']') {
                    StringBuilder temp = new StringBuilder (resStack.pop());
                    int repeatTimes = countStack.pop();
                    for (int i = 0; i < repeatTimes; i++) {
                        temp.append(res);
                    }
                    res = temp.toString();
                    idx++;
                }
                else {
                    res += s.charAt(idx++);
                }
            }
            return res;
        }
    }


    public class Another {
        public void test() {
            // 1432219.()

        }
    }

    public class AdHoc {
        public String removeKdigits(String num, int k) {
            int len = num.length();
            //corner case
            if(k==len)
                return "0";

            Stack<Character> stack = new Stack<>();
            int i =0;
            while(i<num.length()){
                //whenever meet a digit which is less than the previous digit, discard the previous one
                while(k>0 && !stack.isEmpty() && stack.peek()>num.charAt(i)){
                    stack.pop();
                    k--;
                }
                stack.push(num.charAt(i));
                i++;
            }

            // corner case like "1111"
            while(k>0){
                stack.pop();
                k--;
            }

            //construct the number from the stack
            StringBuilder sb = new StringBuilder();
            while(!stack.isEmpty())
                sb.append(stack.pop());
            sb.reverse();

            //remove all the 0 at the head
            while(sb.length()>1 && sb.charAt(0)=='0')
                sb.deleteCharAt(0);
            return sb.toString();
        }
    }

}
