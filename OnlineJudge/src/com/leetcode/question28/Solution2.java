package com.leetcode.question28;

public class Solution2 {
	
	public int strStr(String haystack, String needle) {
		int fromIndex = 0;
		int sourceCount = haystack.length();
		int targetCount = needle.length();
		
		if (fromIndex >= sourceCount) {
            return (targetCount == 0 ? sourceCount : -1);
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (targetCount == 0) {
            return fromIndex;
        }

        char first = needle.charAt(0);
        int max = sourceCount - targetCount;
		
		for (int i = fromIndex; i <= max; i++) {
	        /* Look for first character. */
	        if (haystack.charAt(i) != first) {
	            while (++i <= max && haystack.charAt(i) != first);
	        }

	        /* Found first character, now look at the rest of v2 */
	        if (i <= max) {
	            int j = i + 1;
	            int end = j + targetCount - 1;
	            for (int k = 1; j < end && haystack.charAt(j)
	                    == needle.charAt(k); j++, k++);

	            if (j == end) {
	                /* Found whole string. */
	                return i;
	            }
	        }
	    }
		return -1;
    }
	
	public static void main(String[] args) {
		System.out.println(new Solution2().strStr("a", ""));;
		System.out.println(new Solution().strStr("a", ""));;
	}
	
}
