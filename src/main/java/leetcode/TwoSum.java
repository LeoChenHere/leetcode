/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leetcode;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author leo
 */
public class TwoSum {
    
    public static void main(String[] args){
        int[] nums = {0, 0, 0, 0, 0, 0};
        int target = 6;
//        (new TwoSum()).twoSum(nums, target);
        (new TwoSum()).twoSum_2(nums, target);
    }
    
    //Submission Result: Accepted
    public int[] twoSum(int[] nums, int target) {
        int[] result = {-1, -1};
        
        for( int i = 0 ; i < nums.length ; i++ ){
            for( int j = i+1 ; j < nums.length ; j++ ){
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
    
    //Submission Result: Accepted
    public int[] twoSum_2(int[] nums, int target) {
        int[] result = {-1, -1};
        
        if ( nums[nums.length-1] < target/2 ){throw new IllegalArgumentException("No two sum solution");}
        if ( nums[0] > target/2 ){throw new IllegalArgumentException("No two sum solution");}
        
        int leftIndex = 0;
        int rightIndex = nums.length-1;
        
        while( leftIndex < rightIndex ){
            int tempSum = nums[leftIndex] + nums[rightIndex];
            if (tempSum == target) {
                result[0] = leftIndex + 1;
                result[1] = rightIndex + 1;
                break;
            }else if ( tempSum < target ){
                leftIndex++;
            }else{
                rightIndex--;
            }
        }
        
        if ( result[0] == -1 && result[1] == -1 ){throw new IllegalArgumentException("No two sum solution");}
        
        return result;
    }
}
