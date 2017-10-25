//KEY-VALUE PAIRS FOR DUPLICATE B-TREE IMPLEMENTATION
package col106.a3;

public class BTKeyValue<Key extends Comparable<Key>, Value>
{
    protected Key Key;
    protected Value Value;

    public BTKeyValue(Key key, Value value) {
        Key = key;
        Value = value;
    }
}
