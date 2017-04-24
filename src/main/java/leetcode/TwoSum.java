/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leetcode;

/**
 *
 * @author leo
 */
public class TwoSum {
    
    public static void main(String[] args){
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        (new Solution()).twoSum(nums, target);
    }
}


//Submission Result: Accepted 
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] result = {-1, -1};
        
        for( int i = 0 ; i < nums.length ; i++ ){
            for( int j = 0 ; j < nums.length ; j++ ){
                if ( i>=j ){continue;}
                int twoSum = nums[i]+nums[j];
//                System.out.println("nums["+i+"]="+nums[i] + ";" + "nums["+j+"]="+nums[j]+";twoSum="+twoSum);
                if ( twoSum == target ){
                    result[0] = i;
                    result[1] = j;
//                    System.out.println("Got it nums[" + result[0] + "],nums[" + result[1] + "]");
                }
            }
        }
        
        return result;
    }
}