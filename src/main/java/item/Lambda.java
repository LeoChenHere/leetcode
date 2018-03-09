package item;

import java.util.Arrays;

/**
 * Created by leo on 2017/10/25.
 */
public class Lambda {
    public static void main(String[] args){
        Arrays.asList( "a", "b", "d" ).forEach(e -> System.out.print( e + ", " ) );
        System.out.println();
        Arrays.asList( "a", "b", "d" ).forEach( ( String e ) -> System.out.print( e + ", " ) );
        System.out.println();
        Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> e1.compareTo(e2) );
        System.out.println();
        Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> {
            int result = e1.compareTo( e2 );
            return result;
        } );
    }
}
