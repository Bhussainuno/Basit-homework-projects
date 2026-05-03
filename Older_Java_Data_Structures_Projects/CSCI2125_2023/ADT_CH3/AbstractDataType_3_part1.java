public class SummaArrayList<AnyType> {

// generally when we write a class we use instatce variable

// private AnyType [] data; so we are not gonna be able to being an array using the type perameter so we gonna use 
private Object [] data;

 
// so we know we need to put data into an array so we gonna use 
private int size;

   // also we know we gonna use number of elements and size, 
//* instance variables will are alwasy be labeles as private unless we have good reason to label them to something else 

    // we need construstor method , constructoer is gonna be the same of the class name 
    // whenever we write the defauld costtruct we wan an array data size of 10 
public SummaArrayList(){
    // whenever we call the other constructor to the cinstructoer we call them as "this"
    this(10); 

}
// need another constructoer 
// * if we use two same methods its calls overloading 
public SummaArrayList(int capacity) {
    this.size = 0; 
    // important to remember botm lines 
    //*( we can not build an array in java using a ginaric so we can not type a data, 
    //now if we want data as genral as posible so I can put anything in it as refrance(because genaric can only be a refrance type) * 
    //so whats the other way to write data that would allow me to put anything in there that is a refrance type in java ) most abstract data type we can use is object  */
    this.data = new Object [capacity];

}
 //* note: size and the capacity is two diffirent things capacity is like how maney sloughts do we have ? sloughts are 10 but how maney we store the size its 0, forx recample there are 10 shalves but nothing stors */
public int size(){
    return this.size;

}
         
//  we have to cast to return in to anytype, so anytime we extract this and send it out we have to first cast anytype 
public AnyType get(int index ) {
    return (AnyType)(this.data[index]);


}

// add it to the end 
public void add (AnyType element ){
    // so we put element whatever the size 
    // so here we have a size 0 and then whenever we will put or add something it will increent to the next one
    if (size >= data.length )
        expandStorage(); 
    data [size] = element;
    size++;

}
// so we are gonna define a new size then we build a new arry of objects with expanded size is 
//* so once we build an array in java it size is fixed it can not be made a bigger or smaller,  */
private void expandStorage(){   // $ expand storage will cost us leaner time O(N) anytime if we have to invoke it 
    int expandSize = size + size / 2 +1 ;
    Object [] expandedData = new Object[expandedSize];
    for (int i = 0; i < size; i++){
    expandedData [i] = data [i]; 
    }
    this.data = expandedData;  

}


} // end of class summaarraylist