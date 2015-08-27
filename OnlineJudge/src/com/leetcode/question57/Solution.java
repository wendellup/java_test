package com.leetcode.question57;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
public class Solution {
	public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
		ArrayList<Interval> res = new ArrayList<Interval>();
        
        for(Interval each: intervals){
            if(each.end < newInterval.start){
                res.add(each);
            }else if(each.start > newInterval.end){
                res.add(newInterval);
                newInterval = each;        
            }else if(each.end >= newInterval.start || each.start <= newInterval.end){
                int nstart = Math.min(each.start, newInterval.start);
                int nend = Math.max(newInterval.end, each.end);
                newInterval = new Interval(nstart, nend);
            }
        }
 
        res.add(newInterval); 
 
        return res;
    }
	
	public class Interval {
		int start;
		int end;
		Interval() { start = 0; end = 0; }
		Interval(int s, int e) { start = s; end = e; }
	}
}

