// Class: Height balanced AVL Tree
// Binary Search Tree
import java.util.*;
public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    
    public AVLTree Insert(int address, int size, int key) 
    { 
        AVLTree x= new AVLTree(address,size,key);
        // this is the case when ot is called on sentnel node
        if(this.parent==null){
            AVLTree y=this;
            if(y.right==null){
                y.right=x;
                x.parent=y;
                x.height=1;
                return x;
            }
        }
            // ptherwise traverse down the tree
            // use tiebreaker as address when key=size
            // as when key is address always unique
            AVLTree current=this;
            while(current.parent!=null){
                current=current.parent;
            }
            current=current.right;
            AVLTree previous=current;
            while(current!=null){
                
                if(key<current.key){
                    previous=current;
                    current=current.left;
                }
                else if(key>current.key){
                    previous=current;
                    current=current.right;
                }
                else if (current.address>address){
                    previous=current;
                    current=current.left;
                }
                else{
                    previous=current;
                    current=current.right;
                }
            }
           /* while(current!=null){
                
                if(address>current.address){
                    previous=current;
                    current=current.right;
                }
                else{
                    previous=current;
                    current=current.left;
                }
            }
            */
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
            previous.height=Math.max(height(previous.left),height(previous.right));
            // now we start moving upwards
            // we can also use address for rotation when keys are same so we
            // need to take those cases too
            while(Math.abs(balanceFactor(previous))<=1){
                if(previous.parent.parent==null){
                    break;
                }
                previous=previous.parent;
                previous.height=Math.max(height(previous.left),height(previous.right))+1;
            }
            // above we have found the unbalanced node
            if(Math.abs(balanceFactor(previous))>1){
                int c=balanceFactor(previous);
                if(c <-1){
                    if(key==previous.right.key){
                        if(address<previous.right.address){
                            previous.right=rightRotate(previous.right);
                            previous=leftRotate(previous);
                            return x;
                        }
                        else{
                            previous=leftRotate(previous);
                            return x;
                        }
                    }
                    else if(key>previous.right.key){
                            previous=leftRotate(previous);
                            return x;
                    }
                    else{
                        
                        previous.right=rightRotate(previous.right);
                        previous=leftRotate(previous);
                        return x;
                    }
                }
                else if(c>1){
                    if(previous.left.key==key){
                        if(previous.left.address>address){
                            previous=rightRotate(previous);
                            return x;
                        }
                        else{
                            previous.left=leftRotate(previous.left);
                            previous=rightRotate(previous);
                            return x;
                        }
                    }
                    else if(key<previous.left.key){
                            previous=rightRotate(previous);
                            return x;
                    }		
                        else{
                            previous.left=leftRotate(previous.left);
                            previous=rightRotate(previous);
                            return x;
                }
                }
            }
           return x;

    }
    public AVLTree rightRotate(AVLTree z){
		// here root is grandfather
		AVLTree y= z.left;
		String s="left";
		if( z.parent!=null &&z.parent.right==z ){
			s="right";
		}
		AVLTree w=z.parent;
		AVLTree t3= y.right;
		y.right=z;
		z.left=t3;
		
			z.parent=y;
		
		if(t3!=null){
			t3.parent=z;
		}
		// setting parent pointers above
		z.height=Math.max(height(z.left),height(z.right))+1;
		y.height=Math.max(height(y.left),height(y.right))+1;
		y.parent=w;
		if(s=="left"&&w!=null){
			w.left=y;
		}
		else if(w!=null &&s=="right"){
			w.right=y;
		}
		return y;

	}
	public AVLTree leftRotate(AVLTree z){
		AVLTree y=z.right;
		String s="left";
		if( z.parent!=null &&z.parent.right==z  ){
			s="right";
		}
		AVLTree w=z.parent;
		AVLTree t3= y.left;
		y.left=z;
		
		z.right=t3;
		
			z.parent=y;
		
		if(t3!=null){
			t3.parent=z;
		}
		
		z.height=Math.max(height(z.left),height(z.right))+1;
		y.height=Math.max(height(y.left),height(y.right))+1;
		y.parent=w;
		if(s=="left"&&w!=null){
			w.left=y;
		}
		else if(w!=null&&s=="right"){
			w.right=y;
		}// assigning originals z parent child to y

		return y;
    }
    public int balanceFactor(AVLTree x){
		if(x==null){
			return 0;
		}
		return height(x.left)-height(x.right);
	}
	public int height(AVLTree a){
		if(a==null){
			return 0;
		}
		return a.height;
    }
    // here is the delete fucniton in case i get lost
    public boolean Delete(Dictionary e){
        AVLTree current=this;
        while(current.parent!=null){
            current=current.parent;
        }
        if(current.right==null){
            return false;
        }
        ArrayList<AVLTree> a= new ArrayList<AVLTree>();
        current=current.right;// first element of tree
        a.add(current);
        while(a.size()>0){
            AVLTree temp= a.get(0);
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
        
        // current is the node which is to be deleted
        if(current!=null){
            AVLTree y= current.parent;
            if(current.left==null&& current.right==null){
                if(current.parent.left==current){
                    current.parent.left=null;
                    y.height=Math.max(height(y.left),height(y.right))+1;
                    rebalanceDelete(y);
                }
                else{
                    
                    current.parent.right=null;
                    y.height=Math.max(height(y.left),height(y.right))+1;
                    rebalanceDelete(y);
                }
            }
            else if(current.left==null){
                if(current.parent.left==current){
                    current.right.parent=current.parent;
                    current.parent.left=current.right;
                    y.height=Math.max(height(y.left),height(y.right))+1;
                    rebalanceDelete(y);
                }
                else{
                    current.right.parent=current.parent;
                    current.parent.right=current.right;
                    y.height=Math.max(height(y.left),height(y.right))+1;
                    rebalanceDelete(y);
                }
            }
            else if(current.right==null){
                if(current.parent.left==current){
                    current.left.parent=current.parent;
                    current.parent.left=current.left;
                    y.height=Math.max(height(y.left),height(y.right))+1;
                    rebalanceDelete(y);
                }
                else{
                    current.left.parent=current.parent;
                    current.parent.right=current.left;
                    y.height=Math.max(height(y.left),height(y.right))+1;
                    rebalanceDelete(y);
                }
            }
            else{
                AVLTree succ= current;
                succ=succ.right;
                while(succ.left!=null){
                    succ=succ.left;
                }
                current.size=succ.size;
                current.address=succ.address;
                current.key=succ.key;
                AVLTree b= succ.parent;
                // now delete successor
                if(succ.left==null&& succ.right==null){
                    if(succ.parent.left==succ){
                        succ.parent.left=null;
                        b.height=Math.max(height(b.left),height(b.right))+1;
                        rebalanceDelete(b);
                    }    
                    else{
                        succ.parent.right=null;
                        b.height=Math.max(height(b.left),height(b.right))+1;
                        rebalanceDelete(b);
                    }
                }
                else if(succ.left==null){
                    if(succ.parent.left==succ){
                        succ.right.parent=succ.parent;
                        succ.parent.left=succ.right;
                        b.height=Math.max(height(b.left),height(b.right))+1;
                        rebalanceDelete(b);
                    }
                    else{
                        succ.right.parent=succ.parent;
                        succ.parent.right=succ.right;
                        b.height=Math.max(height(b.left),height(b.right))+1;
                        rebalanceDelete(b);
                    }
                }
                else if(succ.right==null){
                    if(succ.parent.left==succ){
                        succ.left.parent=succ.parent;
                        succ.parent.left=succ.left;
                        b.height=Math.max(height(b.left),height(b.right))+1;
                        rebalanceDelete(b);
                    }
                    else{
                        succ.left.parent=succ.parent;
                        succ.parent.right=succ.left;
                        b.height=Math.max(height(b.left),height(b.right))+1;
                        rebalanceDelete(b);
                    }
                }
            }
            return true;
        }
        return false;        
    }
    public void rebalanceDelete(AVLTree y){
		while(y.parent!=null){
			if(y.parent==null){
				break;
			}
			if(Math.abs(balanceFactor(y))>1){
			rebalanceNodeD(y);
		}
			y=y.parent;

			y.height=Math.max(height(y.left),height(y.right))+1;
		}
	
		
		if(y.key!=-1 && Math.abs(balanceFactor(y))>1){
			rebalanceNodeD(y);
		}
        }
        public void rebalanceNodeD(AVLTree y){
            AVLTree r=y;
            if(height(r.right)>height(r.left)){
                if(height(r.right.left)>height(r.right.right)){
                    r.right=rightRotate(r.right);
                    if(r.parent==null){
                        r=leftRotate(r);
                        this.root=r;
                        return;
                    }
                    r=leftRotate(r);
                    return;
                }
                else{
                    if(r.parent==null){
                        r=leftRotate(r);
                        this.root=r;
                        return;
                    }
                    r=leftRotate(r);
                    return;
                }
            }
            else{
                if(height(r.left.left)<height(r.left.right)){
                    r.left=leftRotate(r.left);
                    if(r.parent==null){
                        r=rightRotate(r);
                        this.root=r;
                        return;
                    }
                    r=rightRotate(r);
                    return;
                }
                else{
                    if(r.parent==null){
                        r=rightRotate(r);
                        this.root=r;
                        return;
                    }
                    r=rightRotate(r);
                    return;
                }
            }  
    }
    public AVLTree getFirst()
    {   
        AVLTree current=this;
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

    public AVLTree getNext()
    {
        // need to take care while going upward if we reach sentiel node
        // we need to check if only one that element in tree
        AVLTree current=this;
        
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
    public AVLTree Find(int k,boolean exact){
        AVLTree current=this;
        while(current.parent!=null){
            current=current.parent;
        }
        if(current.right==null){
            return null;
        }
        current=current.right;
        
        if(exact==true){
            while(current!=null){
                if(k<current.key){
                    current=current.left;
                }
                else if (k==current.key){
                    return current;
                }
                else{
                    current=current.right;
                }
            }
        }
        else{
            
            AVLTree min=current;
            while(current!=null){
               if(k<=current.key){
                   if(current.key>=k && current.key<min.key){
                       min=current;
                   }
                   current=current.left;
                   
               }
               else if(k>current.key){
                if(current.key>=k && current.key<min.key){
                    min=current;
                }
                   current=current.right;
               }
            }
            if(min==null){
                return null;
            }
           else if(min.key>=k){
                return min;
            }
        }

        return null;
    }
    public boolean sanity()
    { 
        AVLTree current= this;
        while(current.parent!=null){
            current=current.parent;
        }
        //first
        if(current.left!=null ){
            return false;
        }
        current=current.right;
        if(current==null){
            return true; // implies tree is empty
        }
        // height ppt and key ppt and parent checking using bfs
        ArrayList<AVLTree> x= new ArrayList<AVLTree>();
        x.add(current);
        while(x.size()>0){
            AVLTree a= x.get(0);
            if(Math.abs(balanceFactor(a))>1){
                return false; // height ppt
            }
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
                return false;// parent checking
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
                return false; // parent checking
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
        AVLTree a= new AVLTree();
        boolean y;
        a.Insert(2,4,5);
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
        a.Insert(1,2,3);
        y=a.sanity();
        System.out.println(y);
        a.Insert(3,2,2);
        y=a.sanity();
        System.out.println(y);
        a.Insert(6,1,2);
        y=a.sanity();
        System.out.println(y);
        AVLTree g=a.Insert(5,5,5);
        for(AVLTree u=g.getFirst();u!=null;u=u.getNext()){
            System.out.println(u.address+ " " +u.size+" " +u.key);
           
        }
        boolean x=a.Delete(new AVLTree(3,4,5));
        y=a.sanity();
        System.out.println(y);
        a.Delete(new AVLTree(3,2,2));
        y=a.sanity();
        System.out.println(y);
        for(AVLTree u=a.getFirst();u!=null;u=u.getNext()){
            System.out.println(u.address+ " " +u.size+" " +u.key);
           
        }
        y=a.sanity();
        System.out.println(y);
    }
}


