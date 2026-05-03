public class Queue<AnyType>{
    private MyArrayList<AnyType> data;

    public Queue(){
        data = new MyArrayList();

    }
    public void enqueue(AnyType element){
        // lets assueme entry point is at index 0 and exit point is at size-1
        data.add(O, element);
        // O(N) 
        // lets assume entry point is at index size-1 and exit point is at 0
        data.add(element)
        // its gonna cost is constant O(1) sometimes O(N)
    }
    public AnyType dequeue(){
         // lets assueme entry point is at index 0 and exit point is at size-1
        return data.remove(data.size-1);
        // its gonna cost us constant O(1)
        // lets assume entry point is at index size-1 and exit point is at 0
        return data.remove(0)
        // becaus we have to shift everything after removing it so its gonna cost us O(N)
    }
    public AnyType peek(){
         // lets assueme entry point is at index 0 and exit point is at size-1
        return data.get(data.size()-1);
        // O(1)
        // lets assume entry point is at index size-1 and exit point is at 0
        return data.get(0);
        // O(1);

    }

}// end class Queue 
