// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java
import java.util.*;
public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }
    // while inserting sentiel node has left and righ child equal
    public BSTree Insert(int address, int size, int key) 
    {   // first case when no element
        BSTree x= new BSTree(address,size,key);
        if(this.parent==null){
            BSTree y=this;
            if(y.right==null){
                y.right=x;
                x.parent=y;
                return x;
            }
        }
            // ptherwise traverse down the tree
            // use tiebreaker as address when key=size
            // as when key is address always unique
            BSTree current=this;
            while(current.parent!=null){
                current=current.parent;
            }
            current=current.right;
            BSTree previous=this;
            while(current!=null){
                previous=current;
                if(key<current.key){
                    current=current.left;
                }
                else if(key>current.key){
                    current=current.right;
                }
                else if(address>current.address){
                    previous=current;
                    current=current.right;
                }
                else{
                    previous=current;
                    current=current.left;
                }
                    
                }

           /* 
            while(current!=null){
                previous=current;
                if(address>current.address){
                    current=current.right;
                }
                else{
                    current=current.left;
                }
            }*/
            if(previous.key==key){
                if(previous.address>address){
                    previous.left=x;
                    x.parent=previous;
                }
                else{
                    previous.right=x;
                    x.parent=previous;
                }
            }
            else if(previous.key<key){
                previous.right=x;
                x.parent=previous;
            }
            else{
                previous.left=x;
                x.parent=previous;
            }
            return x;
        }
        

    public boolean Delete(Dictionary e)
    { 
        // what if duplicate keys
        BSTree current=this;
        while(current.parent!=null){
            current=current.parent;
        }
        if(current.right==null){
            return false;
        }
        
        /*while(current!=null){
            if(current.key<e.key){
                current=current.right;
            }
            else if(current.key>e.key){
                current=current.left;
            }
            else{
                if(e.key==current.key && e.address==current.address && e.size==current.size){
                    break;
                }
            }
        }
        */

        // we need to use bfs to to see duplicates
        // so as to compare that same address key and size in case
        // of duplicates
        ArrayList<BSTree> a= new ArrayList<BSTree>();
        current=current.right;// first element of tree
        a.add(current);
        while(a.size()>0){
            BSTree temp= a.get(0);
            a.remove(0);
            if(temp.key==e.key && temp.address==e.address && temp.size==e.size){
                current=temp;
                break;
            }
            if(temp.left!=null){
                a.add(temp.left);
            }
            if(temp.right!=null){
                a.add(temp.right);
            }
        }
        
        
        if(current!=null){
            if(current.left==null&& current.right==null){
                if(current.parent.left==current){
                    
                    // else only left child null
                    current.parent.left=null;
                }
                else{
                    
                    current.parent.right=null;
                }
            }
            else if(current.left==null){
                if(current.parent.left==current){
                    current.right.parent=current.parent;
                    current.parent.left=current.right;
                    
                }
                else{
                    current.right.parent=current.parent;
                    current.parent.right=current.right;
                }
            }
            else if(current.right==null){
                if(current.parent.left==current){
                    current.left.parent=current.parent;
                    current.parent.left=current.left;
                }
                else{
                    current.left.parent=current.parent;
                    current.parent.right=current.left;
                }
            }
            // now we delete when 2 children
            else{
                BSTree succ= current;
                succ=succ.right;
                while(succ.left!=null){
                    succ=succ.left;
                }
                current.size=succ.size;
                current.address=succ.address;
                current.key=succ.key;
                // now delete successor
                if(succ.left==null&& succ.right==null){
                    if(succ.parent.left==succ){
                        
                        succ.parent.left=null;
                    }
                    else{
                       
                        succ.parent.right=null;
                    }
                }
                else if(succ.left==null){
                    if(succ.parent.left==succ){
                        succ.right.parent=succ.parent;
                        succ.parent.left=succ.right;
                    }
                    else{
                        succ.right.parent=succ.parent;
                        succ.parent.right=succ.right;
                    }
                }
                else if(succ.right==null){
                    if(succ.parent.left==succ){
                        succ.left.parent=succ.parent;
                        succ.parent.left=succ.left;
                    }
                    else{
                        succ.left.parent=succ.parent;
                        succ.parent.right=succ.left;
                    }
                }
            }
            return true;
        }
        return false;
    }
        
    public BSTree Find(int key, boolean exact)
    {   
        // for finding we use size or address so duplicated or no duplicates
        // no problem
        BSTree current=this;
        while(current.parent!=null){
            current=current.parent;
        }
        if(current.right==null){
            return null;
        }
        current=current.right;
        
        if(exact==true){
            while(current!=null){
                if(key<current.key){
                    current=current.left;
                }
                else if (key==current.key){
                    return current;
                }
                else{
                    current=current.right;
                }
            }
        }
        else{
            
            BSTree min=current;
            while(current!=null){
               if(key<=current.key){
                   if(current.key>=key && current.key<min.key){
                       min=current;
                   }
                   current=current.left;
                   
               }
               else if(key>current.key){
                if(current.key>=key && current.key<min.key){
                    min=current;
                }
                   current=current.right;
               }
            }
            if(min==null){
                return null;
            }
           else if(min.key>=key){
                return min;
            }
        }

        return null;
    }

    public BSTree getFirst()
    {   // bst first will be leftmost node
        BSTree current=this;
        while(current.parent!=null){
            current=current.parent;
        }
        if(current.right==null){
            return null;
        }
        current=current.right;
        while(current.left!=null){
            current=current.left;
        }
        return current;
        
    }

    public BSTree getNext()
    { 
        // need to take care while going upward if we reach sentiel node
        // we need to check if only one that element in tree
        BSTree current=this;
        
        //successor down
        if(current.right!=null){
            current=current.right;
            while(current.left!=null){
                current=current.left;
                
            }
            return current;
        }
        
    // if successor up
        else{
            while(current.parent!=null && current.parent.left!=current){
                current=current.parent;
            }
            if(current.parent==null){
                // when sentinel node
                return null;
            }
            else if(current.parent.right==current){
                // when rightmost node
                return null;
            }
            else{
                return current.parent;
            }
        }
        
    }

    public boolean sanity()
    {   //2. if sentinel node left and right arent equal
        BSTree current=this;
        while(current.parent!=null){
            current=current.parent;
        }
        if(current.left!=null ){
            return false;
        }
        
        // 3.checking if key bst isnt satisfied on keys
        //4. if parent of left or right not equal to parent using bfs
        while(current.parent!=null){
            current=current.parent;
        }
        current=current.right;
        if(current==null){
            return true;
        }
        ArrayList<BSTree> x= new ArrayList<BSTree>();
        x.add(current);
        while(x.size()>0){
            BSTree a= x.get(0);
            if(a.left!=null){
            if(a.left.key>a.key){
                return false;
            }
            if(a.left.key==a.key){
                if(a.left.address>a.address){
                    return false; // comparing addresses when key is same
                }
            }
            if(a.left.parent!=a){
                return false;
            }
            else{
                x.add(a.left);
            }
        }
        if(a.right!=null){
            if(a.right.key<a.key){
                return false;
            }
            if(a.right.parent!=a){
                return false;
            }
            if(a.right.key==a.key){
                if(a.right.address<a.address){
                    return false;
                }
            }
            else{
                x.add(a.right);
            }
            
        }
        x.remove(0);
        } 
        return true;
    }
    public static void main(String[] args) {
        BSTree a= new BSTree();
        boolean y;
        a.Insert(2,4,5);
        y=a.sanity();
        System.out.println(y);
        a.Delete(new BSTree(2,4,5));
        y=a.sanity();
        System.out.println(y);
        a.Insert(3,4,5);
        y=a.sanity();
        System.out.println(y);
        a.Insert(4,5,5);
        y=a.sanity();
        System.out.println(y);
        a.Insert(5,6,3);
        y=a.sanity();
        System.out.println(y);
        a.Insert(5,6,2);
        y=a.sanity();
        System.out.println(y);
        a.Insert(5,5,5);
        for(BSTree u=a.getFirst();u!=null;u=u.getNext()){
            System.out.println(u.address+ " " +u.size+" " +u.key);
           
        }
        a.Delete(new BSTree(4,6,5));
        y=a.sanity();
        System.out.println(y);
        a.Delete(new BSTree(5,5,5));
        y=a.sanity();
        System.out.println(y);
        BSTree h=a.getFirst(); 
        int count=0;
        System.out.println(" ");
        for(BSTree i=h;i!=null;i=i.getNext()){
            System.out.println(i.address+" "+i.size+" "+i.key);
            count+=1;
        }
        System.out.println(count);
        BSTree temp= a.Insert(1,2,3);
        System.out.println(temp.sanity());
    }

}


 


