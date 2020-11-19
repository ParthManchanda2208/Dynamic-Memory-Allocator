// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).


    // in freeblk key is size
        // in allocate block key is address
    public int Allocate(int blockSize) {
    
       Dictionary a= freeBlk.Find(blockSize,false);
       if(a==null){
        return -1;
       }
       // if not return -1 implies there is space in freeblk
        freeBlk.Delete(a);// i deleted first then i would split and allocate
        if(a.size==blockSize){
            allocBlk.Insert(a.address,a.size,a.address);
            
        }
        else{
            allocBlk.Insert(a.address,blockSize,a.address);
            freeBlk.Insert(a.address+blockSize,a.size-blockSize,a.size-blockSize);
        }
        return a.address;

    } 
    
    public int Free(int startAddr) {
        Dictionary a= allocBlk.Find(startAddr,true);
        if(a==null){
            return -1;
        }
        allocBlk.Delete(a);
        freeBlk.Insert(a.address,a.size,a.size);
        return 0;
    }
    public static void main(String[] args) {
        A1DynamicMem x= new A1DynamicMem(100,3);
        int o=x.Allocate(20);
        System.out.println(o);
        o=x.Allocate(50);
        System.out.println(o);
        o= x.Allocate(20);
        System.out.println(o);
       
        o=x.Allocate(5);
        System.out.println(o);
        int y=x.Free(20);
        System.out.println(y);
        
        x.Free(0);
        
        int i= x.Free(60);
        System.out.println(i);
        
    }

}