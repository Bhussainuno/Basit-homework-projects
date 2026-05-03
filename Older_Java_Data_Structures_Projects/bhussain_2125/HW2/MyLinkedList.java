public class MyLinkedList<AnyType> {
    // instance variable 
    private int size;
    private Node firstNode;
    private Node lastNode; 
    
    public MyLinkedList(){
        this.size = 0;
        this.firstNode = null;
        this.lastNode = null; 
        


    }
    public int size(){
        return this.size;

    }

    public void add(AnyType element){

        Node newNode = new Node(element);
        // walk out along the node linkeges untill we hit the node
        // that does not have a next node- that point,
        // currentNode will be the last node
        // while this will work its horriblly ineffecient Theta(N)
        // there's a batter way, below, by keeping around a referance 
        // to the last Node

        /*
        Node currentNode = firstNode;
        while (currentNode.getNext() != null)
            currentNode = currentNode.getNext();
            // tell currentNode is the next node is the one we build ! 
        currentNode.setNext(newNode);
        */
        if (size == 0){
            this.firstNode = newNode;
            this.lastNode = newNode;

        } 
         else {
            this.lastNode.setNext(newNode);
            this.lastNode = newNode;
        }
          // then increment the size 
        size++;

    }
        // insertion style add 
    public void add(AnyType element, int index){
        if (index > size)
            throw new IndexOutOfBoundsException("index is too Big!");
        else if (index < 0)
            throw new IndexOutOfBoundsException("Have you lost your mind ?");

            Node newNode = new Node(element);
            
            if (index == size -1){
                add(element);
                return;
            }
            // Node newNode = new Node(element);
            
            if (index == 0){
                newNode.setNext(firstNode);
                firstNode = newNode;
                if (size == 0)
                    lastNode = newNode;
                    size++;
                    return;

            }


            Node currentNode = firstNode;
            int currentIndex = 0;
            while (currentIndex < index-1) {
                currentNode = currentNode.getNext();
                currentIndex++;
            }
            newNode.setNext(currentNode.getNext());
            currentNode.setNext(newNode);
            size++;

    }
    public AnyType get(int index){
        if (index >= size)
            throw new IndexOutOfBoundsException("index is too Big!");
        else if (index < 0)
            throw new IndexOutOfBoundsException("Have you lost your mind ?");

            Node currentNode = firstNode;
            int currentIndex = 0;
            while (currentIndex < index) {
                currentNode = currentNode.getNext();
                if (!currentNode.getDelete())
                    currentIndex++;
            }
            return currentNode.getData();

        }

        public AnyType removeLazyDelete(int index){
            if (index > size)
                throw new IndexOutOfBoundsException("index is too Big!");
            else if (index < 0)
                throw new IndexOutOfBoundsException("Have you lost your mind ?");
                
            AnyType thingToReturn = null;
            Node currentNode = firstNode;
            int currentIndex = 0;
            while (currentIndex < index-1) {
                currentNode = currentNode.getNext();
                currentIndex++;
            }
            Node nodeToRemove = currentNode.getNext();
                thingToReturn = nodeToRemove.getData();
            nodeToRemove.setDelete();
            size--;
            return thingToReturn;
        }

        // remove (and return the data element)
        public AnyType remove(int index){
            if (index > size)
                throw new IndexOutOfBoundsException("index is too Big!");
            else if (index < 0)
                throw new IndexOutOfBoundsException("Have you lost your mind ?");
                
                AnyType thingToReturn = null;

                if (index == 0) {
                    thingToReturn = firstNode.getData();
                    Node nodeToRemove = firstNode;

                    firstNode = nodeToRemove.getNext();
                    nodeToRemove.setNext(null);
                        size--;
                        return thingToReturn; 
                        
                
                
                }
            Node currentNode = firstNode;
            int currentIndex = 0;
            while (currentIndex < index-1) {
                currentNode = currentNode.getNext();
                currentIndex++;
            }
            Node nodeToRemove = currentNode.getNext();
                thingToReturn = nodeToRemove.getData();
            currentNode.setNext(nodeToRemove.getNext());
            nodeToRemove.setNext(null);
             
            if (nodeToRemove.getNext() == null);
                lastNode = currentNode;  
            size--;
            return thingToReturn;
                
        }
        public MyIterator iterator(){
            return new MyIterator();
        }
        public class MyIterator{
            private Node currentNode;

            public MyIterator(){
                 
            currentNode = MyLinkedList.this.firstNode;
            }
            public boolean hasNext(){
                if (currentNode == null)
                return false;
                return true;

            }
            public AnyType next(){
                AnyType valueToReturn = currentNode.getData();
                currentNode = currentNode.getNext();
                return valueToReturn;
                

            }

        } // end class MyIterator

    

    private class Node {
        private AnyType element;
        private Node    nextNode;
        private boolean delete;

        private Node(AnyType element){
            this.element = element;
            this.nextNode = null;
            this.delete = false;

        }
        public AnyType getData(){
            return this.element;

        }
        public Node getNext(){
            return this.nextNode;

        }

        public boolean getDelete(){
            return this.delete;

        }
        public void setData(AnyType element){
            this.element = element;

        }
        public void setDelete(){
            this.delete = true;

        }
        public void setNext(Node next){
            this.nextNode = next;

        }


    } // end of class node


}  // end of linkedlist 