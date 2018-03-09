package item;

import java.math.BigDecimal;

/**
 * Created by leo on 2017/10/23.
 */
public class BigDecimalDemo {
    public static void main(String[] args) {

        String str = "Multiplication Result is " + (new BigDecimal("2.1022300")).multiply(new BigDecimal("4.620"));

        // print bg3 value
        System.out.println( str );
    }
}
