import java.util. Scanner;

    public class MainLinked {
        public static void main(String[] args) {
            MyLinkedList<String> myList = new MyLinkedList<String>() ;


            myList. add ("Rick");
            myList.add ("Morty" ) ;
            myList.add("SheHulk");
            myList. add ("Jerry") ;
            myList.add ("Summer" ) ;
            for (int i=0; i<100; i++) 
            {
                myList.add("Mr. Meeseeks number" +i);
            }
            myList.add("Beth");
            for (int i=0; i<myList.size(); i++) {
                System.out.println(i+ ":"  + myList.get(i));
            }
            
                System.out.println("Deleting" + myList. removeLazyDelete (2)) ;
                System.out.println( "New state of the list:");

                for (int i=0; i<myList.size(); i++){
                    System.out.println(i+ ": " + myList.get(i));
            
            }
            System.out.println("Adding Daffy Duck at index 75");
            myList.add("Daffy Duck",75);
            System.out.println("New state of the list:");
            
            for(int i=0; i<myList.size(); i++)
            {
                System.out.println(i + ":" + myList.get(i));
            }
            System.out.println("Deleting" + myList. removeLazyDelete(105)) ;
            System.out.println( "New state of the list:");
                for (int i=0; i<myList.size(); i++)
                {
                    System.out.println(i + ": " + myList.get(i));
                }

        //MyLinkedList<Scanner> anotherList = new MyArrayList<Scanner>() ;
        // this should not work:
        //anotherList.add ("Joe Blow") ;
        //1 in general with an iterator, you want this sort of
        // construction to work


            System.out.println("Using an iterator:");

            MyLinkedList.MyIterator iter = myList.iterator();
                while (iter.hasNext())
                    System.out.println(iter.next());    
    }
}

