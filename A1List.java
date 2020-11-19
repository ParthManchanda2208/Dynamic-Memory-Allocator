// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {
        A1List x= new A1List(address,size,key);
        A1List current=this;
        while(current.prev!=null){
            current=current.prev;
        }
        A1List b=current.next;
        x.next=current.next;
        x.prev=current;
            b.prev=x;
            current.next=x;
        
        return x;
    }

    public boolean Delete(Dictionary d) 
    {   
        A1List r =this;
        A1List n=this;
        if(r==null){
            return false;
        }
        while(r.prev!=null){
            if(r.key==d.key && r.address==d.address && r.size==d.size){
                r.prev.next=r.next;
                r.next.prev=r.prev;
                r.next=r.prev=null;
                return true;
            }
            r=r.prev;
            
        }
        while(n.next!=null){
            if(n.key==d.key && n.address==d.address && n.size==d.size){
                n.prev.next=n.next;
                n.next.prev=n.prev;
                n.next=n.prev=null;
                return true;
            }
            n=n.next;
        }
        return false;
    }

    public A1List Find(int k, boolean exact)
    {   
        if(exact==true){
            A1List current= this;
            while(current.next!=null){
                if (current.key==k){
                    return current;
                }
                current=current.next;
            }
            A1List previous=this;

            while(previous.prev!=null){
                if(previous.key==k){
                    return previous;
                }
                previous=previous.next;
            }
            
        }
        else{
            A1List c=this;
            while(c.next!=null){
                 if(c.key>=k){
                     return c;
                 }
                 c=c.next;
            }
            A1List p=this;
            while(p.prev!=null){
                if(p.key==k){
                    return p;
                }
               p =p.prev;
        }
    }
    return null;
}
    public A1List getFirst()
    {
        A1List current=this;
        while(current.prev!=null){
            current=current.prev;
        }
        return current.next;
    }
    public A1List getNext() 
    {
        if(this.next.next==null){
            return null;
        }
        return this.next;
    }

    public boolean sanity()
    {
        // sanity is called starting from head of list
        A1List n= this;
        A1List p= this;
        while(n.next!=null){
            if(n.next.prev!=n){
                return false;
            }
            n=n.next;
        }
        while(p.prev!=null){
            if(p.prev.next!=p){
                return false;
            }
            p=p.prev;
        }
       A1List x= this;
       while(x.prev!=null){
           if(x.key==-1 && x.size==-1 && x.address==-1){
               if (x.prev!=null){
                   return false;
               }
           }
           x=x.prev;
       }
       A1List y=this;
       while(y.next!=null){
        if(y.key==-1 && y.size==-1 && y.address==-1){
            if (y.next!=null){
                return false;
            }
        }
        y=y.next;
    }
        A1List z=this;
        while(z.prev!=null){
            z=z.prev;
        }
        z=z.next;// now this is the first element
        A1List s=z;// slow pointer and z is fast pointer
        z=z.next;
        while(s.next!=null && z.next!=null && z.next.next!=null){
            if(s==z){
                return false;
            }
            s=s.next;
            z=z.next.next;
        }
        return true;
    }
    public static void main(String[] args) {
        A1List a= new A1List();
        A1List g= a.Insert(1,2,3);
        boolean x=g.Delete(new A1List(1,2,3));
        boolean b= g.Delete(new A1List(1,2,3));
        System.out.println(x);
        System.out.println(g);
        System.out.println(b);
        A1List h= a.getFirst();
        System.out.println(h.key);
        a.Insert(2,4,5);
        a.Insert(3,4,5);
        a.Insert(4,5,5);
        g=a.Insert(5,6,7);
        h=g.getFirst();
        System.out.println(h.key);
        int count=0;
        for(A1List i=h.getFirst();h!=null;h=h.getNext()){
            count+=1;
        }
        System.out.println(count);
        A1List temp= a.Insert(1,2,3);
        System.out.println(temp.sanity());
        
        
    }
}


