// Rhea Rupani
// rrupani
// 12M Lab7
// Dictionary.java

public class Dictionary implements DictionaryInterface{
	// private inner node

	private class Node{
		Pair two;
		Node left;
		Node right;

		Node(Pair x){
			two = x;
			left = null;
			right = null;
		}
	}

	// private Inner Pair
	private class Pair{
		String key;
		String value;

		Pair(String k, String v){
			key = k;
			value = v;
		}
	}

	// private fields 
	private Node root;
	private int numItems;

	// constructor
	public Dictionary(){
		root = null;
		numItems = 0;
	}

	// findKey()
	private Node findKey(Node R, String k){
		if(R == null || R.two.key.equals(k)) return R;
		if(R.two.key.compareToIgnoreCase(k) > 0)
			return findKey(R.left, k);
		else return findKey(R.right, k);
	}

	// findParent()
	Node findParent(Node N, Node R){
		Node P = null;
		if(N != R){
			P = R;
      while(P.left != N && P.right != N){
				if(N.two.key.compareToIgnoreCase(P.two.key) < 0)
					P = P.left;
				else
					P = P.right;
			}
		}
		return P;
	}

	// findLeftmost()
	Node findLeftmost(Node R){
		Node L = R;
		if(L != null)
			for(; L.left != null; L = L.left);
				return L;
	}

	// printInOrder()

	void printInOrder(Node R) {
      if (R != null) {
         printInOrder(R.left);
         System.out.println(R.two.key + " " + R.two.value);
         printInOrder(R.right);
      }
   }

   // deleteAll()
   void deleteAll(Node N) {
      if (N != null) {
         deleteAll(N.left);
         deleteAll(N.right);
      }
   }

   // public 

   // isEmpty()
   // pre: none
   // returns true if this Dictionary is empty, false otherwise
   public boolean isEmpty(){
   	return (numItems == 0);

   }

   // size()
   // pre: none
   // returns the number of entries in this Dictionary
   public int size(){
   	return numItems;

   }

   // lookup()
   // pre: none
   // returns value associated key, or null reference if no such key exists
   public String lookup(String key){
   	Node N;
   	N = findKey(root, key);
   	return (N == null ? null: N.two.value);

   }

   // insert()
   // inserts new (key,value) pair into this Dictionary
   // pre: lookup(key)==null
   public void insert(String key, String value) throws DuplicateKeyException{
   	Node N, A, B;
   	if(findKey(root,key) != null){
   		throw new DuplicateKeyException("Dictionary Error: cannot insert() duplicate keys");
   	}

   	N = new Node(new Pair(key,value));
   	B = null;
   	A = root;
   	while(A != null){
   		B = A;
   		if(A.two.key.compareToIgnoreCase(key) > 0) A = A.left;
   		else A= A.right;
   	}

   	if(B == null) root = N;
   	else if (B.two.key.compareToIgnoreCase(key) > 0) B.left = N;
   	else B.right = N;
   	numItems++;

   }

   // delete()
   // deletes pair with the given key
   // pre: lookup(key)!=null
   public void delete(String key) throws KeyNotFoundException{
   	Node N, P, S;
      if(findKey(root, key)==null){
         throw new KeyNotFoundException("Dictionary Error: delete() cannot delete non-existent key");
      }
      N = findKey(root, key);
      if( N.left == null && N.right == null ){
         if( N == root ){
            root = null;
         }else{
            P = findParent(N, root);
            if( P.right == N ) P.right = null;
            else P.left = null;
         }
      }else if( N.right == null ){
         if( N == root ){
            root = N.left;
         }else{
            P = findParent(N, root);
            if( P.right == N ) P.right = N.left;
            else P.left = N.left;
         }
      }else if( N.left == null ){
         if( N == root ){
            root = N.right;
         }else{
            P = findParent(N, root);
            if( P.right == N ) P.right = N.right;
            else P.left = N.right;
         }
      }else{  
         S = findLeftmost(N.right);
         N.two.key = S.two.key;
         N.two.value = S.two.value;
         P = findParent(S, N);
         if( P.right == S ) P.right = S.right;
         else P.left = S.right;
      }
      numItems--;

   }

   // makeEmpty()
   // pre: none
   public void makeEmpty(){
   	  deleteAll(root);
      root = null;
      numItems = 0;

   }

   // toString()
   // returns a String representation of this Dictionary
   // overrides Object's toString() method
   // pre: none
   public String toString(){
   	printInOrder(root);
    return "";
   }

}
