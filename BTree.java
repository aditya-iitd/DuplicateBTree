package col106.a3;

import java.util.List;
import java.util.Vector;


public class BTree<Key extends Comparable<Key>,Value> implements DuplicateBTree<Key,Value> {

    private static int b; //order of the tree
    
    private BNode root = null; //root node of the tree
    
    private int mSize = 0; //number of keys in the tree
    
    private int mHeight = 0; // height of the btree
    
    private int numNodes = 0; // number of BNodes
    
    List<Value> search_result1;

    public BNode<Key, Value> getRootNode() {
        return root;
    }

    private BNode<Key, Value> createNode() {
        BNode<Key, Value> btNode;
        btNode = new BNode(b);
        btNode.IsLeaf = true;
        btNode.CurrentKeyNum = 0;
        numNodes+=1;
        return btNode;
    }

    public BTree(int b) throws bNotEvenException {  /* Initializes an empty b-tree. Assume b is even. */
        if(b%2!=0)
        {
            throw new bNotEvenException();
        }
        this.b = b/2;
        this.root = new BNode(b/2);
    }

    @Override
    public boolean isEmpty() {
        return mSize==0;
    }

    @Override                             
    public int size() {
        return mSize;
    }

    @Override
    public int height() {
        return mHeight;
    }

    public void searchAtNode(BNode temp,Key key)
    {   
        int i=0;
        
        for(i=0;i<temp.CurrentKeyNum;i++){
            if(key.compareTo((Key)temp.Keys[i].Key)==0)
                search_result1.add((Value)temp.Keys[i].Value);
            if(key.compareTo((Key)temp.Keys[i].Key)<0)
                break;
        }
        
        if(temp.Children[0]!=null){
            for(int j=0;j<i+1;j++){
                BNode tempParent=temp;
                temp=temp.Children[j];
                searchAtNode(temp,key);
                temp=tempParent;
            }
        }
     }

     
    @Override
    public List<Value> search(Key key) throws IllegalKeyException {
        search_result1 = new Vector<Value>();
       
        BNode temp=this.root;
       
        searchAtNode(temp,key);
        
        return search_result1;
        
    }

    private void splitNode(BNode parentNode, int nodeIdx, BNode btNode) {
     
        int i;
        mHeight++;
        BNode<Key, Value> newNode = createNode();

        newNode.IsLeaf = btNode.IsLeaf;

        newNode.CurrentKeyNum = BNode.LOWER_BOUND;

        for (i = 0; i < BNode.LOWER_BOUND; ++i) {
            newNode.Keys[i] = btNode.Keys[i + BNode.MIN_DEGREE];
            btNode.Keys[i + BNode.MIN_DEGREE] = null;
        }

        if (!btNode.IsLeaf) {
            for (i = 0; i < BNode.MIN_DEGREE; ++i) {
                newNode.Children[i] = btNode.Children[i + BNode.MIN_DEGREE];
                btNode.Children[i + BNode.MIN_DEGREE] = null;
            }
        }

        btNode.CurrentKeyNum = BNode.LOWER_BOUND;

        for (i = parentNode.CurrentKeyNum; i > nodeIdx; --i) {
            parentNode.Children[i + 1] = parentNode.Children[i];
            parentNode.Children[i] = null;
        }
     
        parentNode.Children[nodeIdx + 1] = newNode;

        for (i = parentNode.CurrentKeyNum - 1; i >= nodeIdx; --i) {
            parentNode.Keys[i + 1] = parentNode.Keys[i];
            parentNode.Keys[i] = null;
        }
     
        parentNode.Keys[nodeIdx] = btNode.Keys[BNode.LOWER_BOUND];
     
        btNode.Keys[BNode.LOWER_BOUND] = null;
     
        (parentNode.CurrentKeyNum)++;
    }

    private void insertKeyAtNode(BNode rootNode, Key key, Value value) {
        
        int i;
        
        int currentKeyNum = rootNode.CurrentKeyNum;

        if (rootNode.IsLeaf) {
            if (rootNode.CurrentKeyNum == 0) {
                rootNode.Keys[0] = new BTKeyValue<Key, Value>(key, value);
                rootNode.CurrentKeyNum++;
                return;
            }
 
            i = currentKeyNum - 1;
            BTKeyValue<Key, Value> existingKeyVal = rootNode.Keys[i];
            while ((i > -1) && (key.compareTo(existingKeyVal.Key) <= 0)) {
                rootNode.Keys[i + 1] = existingKeyVal;
                --i;
                if (i > -1) {
                    existingKeyVal = rootNode.Keys[i];
                }
            }

            i = i + 1;
            rootNode.Keys[i] = new BTKeyValue<Key, Value>(key, value);

            (rootNode.CurrentKeyNum)++;
            return;
        }

        i = 0;
        
        int numberOfKeys = rootNode.CurrentKeyNum;
        
        BTKeyValue<Key, Value> currentKey = rootNode.Keys[i];
        
        while ((i < numberOfKeys) && (key.compareTo(currentKey.Key) >= 0)) {
            ++i;
            if (i < numberOfKeys) {
                currentKey = rootNode.Keys[i];
            }
            else {
                --i;
                break;
            }
        }

        BNode<Key, Value> btNode;
        
        if (key.compareTo(currentKey.Key) >= 0) {
            btNode = BNode.getRightChildAtIndex(rootNode, i);
            i = i + 1;
        }
        
        else {
            if ((i - 1 >= 0) && (key.compareTo((Key)rootNode.Keys[i - 1].Key) >= 0)) {
                btNode = BNode.getRightChildAtIndex(rootNode, i - 1);
            }
            else {
                btNode = BNode.getLeftChildAtIndex(rootNode, i);
            }
        }

        if (btNode.CurrentKeyNum == BNode.UPPER_BOUND) {
            splitNode(rootNode, i, btNode);
            insertKeyAtNode(rootNode, key, value);
            return;
        }

        insertKeyAtNode(btNode, key, value);
    }


    @Override
    public void insert(Key key, Value value) {
        
        if (root == null) {
            root = createNode();
        }

        if (root.CurrentKeyNum == BNode.UPPER_BOUND) {
            BNode<Key, Value> btNode = createNode();
            btNode.IsLeaf = false;
            btNode.Children[0] = root;
            root = btNode;
            splitNode(root, 0, btNode.Children[0]);
        }
        
        mSize++;
        
        insertKeyAtNode(root, key, value);
    }

    public String printToString(BNode btroot){
        
        String result = "";

        if(btroot.IsLeaf)
        {
            result+= BNode.printNode(btroot);
        }
        
        else
        {
            for(int i=0; i<btroot.CurrentKeyNum; i++)
            {
                if(i==0)
                    result+="[";
                result+= (printToString(btroot.Children[i])+","+btroot.Keys[i].Key+"="+btroot.Keys[i].Value+",");
            }
            result+= printToString(btroot.Children[btroot.CurrentKeyNum]);
            result+="]";
        }

        return result;
    }

    public String toString(){

        return printToString(this.root);
    }

    @Override
    public void delete(Key key) throws IllegalKeyException {
        throw new RuntimeException("Not Implemented");
    }
}
