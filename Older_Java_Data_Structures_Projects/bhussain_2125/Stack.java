public class Stack<AnyType>{

    private MyArrayList<AnyType> Data;

    public Stack();{

    data = new MyArrayList();


    }
    public void puch(AnyType element){

            // lets imigine the "top" is at index 0
            // data.add(element,0); 
            //O(N)------> Poor choice!
            // lets immagine the  "top" is at the end (size-1)
            data.add(element);
            // usually O(1) but sometimes O(N);

    }
    public AnyType pop(){
            // lets imigine the "top" is at index 0
            // return data.remove(0);
            // O(N) ------> Poor choice!
            // lets immagine the  "top" is at the end (size-1)
            return data.remove(data.size()-1);
            // REMOVING SOMETHING FROM THE LAST ARRAY ITS GONNA BE CONSTANT LIKE O(1)!




    }
    public AnyType peek(){
       // lets imigine the "top" is at index 0
        return data.get(0);
        // O(1)
        // lets immagine the  "top" is at the end (size-1)
        return data.get(data.size()-1);
        // Also constant O(1)
        

    }
}

