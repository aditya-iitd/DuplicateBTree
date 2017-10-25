//IMPLEMENTATION OF BNODE CLASS FOR DUPLICATE B-TREE
package col106.a3;

public class BNode<Key extends Comparable<Key>, Value>
{
    public static int MIN_DEGREE;  
    public static int LOWER_BOUND;  
    public static int UPPER_BOUND;     

    public boolean IsLeaf;
    public int CurrentKeyNum;
    public BTKeyValue<Key, Value> Keys[];
    public BNode Children[];


    public BNode(int b) {
        MIN_DEGREE = b;
        IsLeaf = true;
        CurrentKeyNum = 0;
        UPPER_BOUND = 2*MIN_DEGREE-1;
        LOWER_BOUND = MIN_DEGREE-1;
        Keys = new BTKeyValue[UPPER_BOUND];
        Children = new BNode[UPPER_BOUND + 1];
    }


    public static BNode getChildNodeAtIndex(BNode btNode, int keyIdx, int nDirection) {
        if (btNode.IsLeaf) {
            return null;
        }

        keyIdx += nDirection;
        if ((keyIdx < 0) || (keyIdx > btNode.CurrentKeyNum)) {
            return null;
        }

        return btNode.Children[keyIdx];
    }


    public static BNode getLeftChildAtIndex(BNode btNode, int keyIdx) {
        return getChildNodeAtIndex(btNode, keyIdx, 0);
    }


    public static BNode getRightChildAtIndex(BNode btNode, int keyIdx) {
        return getChildNodeAtIndex(btNode, keyIdx, 1);
    }


    public static BNode getLeftSiblingAtIndex(BNode parentNode, int keyIdx) {
        return getChildNodeAtIndex(parentNode, keyIdx, -1);
    }


    public static BNode getRightSiblingAtIndex(BNode parentNode, int keyIdx) {
        return getChildNodeAtIndex(parentNode, keyIdx, 1);
    }

    public static String printNode(BNode btnode){
    	String str = "[";
    	for(int i=0; i<btnode.CurrentKeyNum; i++)
    	{	if(i==btnode.CurrentKeyNum-1)
    		{
    			str+= btnode.Keys[i].Key+"="+btnode.Keys[i].Value;
    		}
    		else
    		{	
    			str+= (btnode.Keys[i].Key+"="+btnode.Keys[i].Value+",");
    		}
    	}
    	str+= "]";

    	return str;
    }









}