public class Main{
    public static void main(String[] args){
        // so we gonna build a null aurguent constructoer

        // we are invocking the constructoring summaarraylist so our ne
        SummaArrayList<String> list = new SummaArrayList<String>();
        for (int i = 0; i<20; i++){
            String temp = "Mombo #"  + i;
            list.add(temp); 
        }
        for (int i=0; i< list.size(); i++){
            System.out.println( list.get(i));
            
        }




    }



    
}