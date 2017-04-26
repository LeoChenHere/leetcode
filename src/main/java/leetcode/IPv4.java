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
public class IPv4 {
    
    public Long ipv4(String input) {
        String[] arrInput = input.split("\\.");
        System.out.println("Length : "+arrInput.length);
        Long result = 0L;
        if ( arrInput.length == 4 ){
            try{
                Long _v1 = Long.parseLong(arrInput[0].trim());
                Long _v2 = Long.parseLong(arrInput[1].trim());
                Long _v3 = Long.parseLong(arrInput[2].trim());
                Long _v4 = Long.parseLong(arrInput[3].trim());
                
                if ( _v1 <= 255 && _v2 <= 255 && _v3 <= 255 && _v4 <= 255 ){
                    result = _v1*256*256*256+
                            _v2*256*256+
                            _v3*256+
                            _v4*1;
                    System.out.println("Result : "+result);
                }else{
                    System.out.println("Input error : Value Not Available");
                }
                
                
            }catch( Exception ex ){
                System.out.println("Input error : Number Format Error");
            }
            
        }else{
            System.out.println("Input error : General Error");
        }
        
        return result;
    }
}