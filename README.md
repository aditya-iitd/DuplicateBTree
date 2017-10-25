# BTrees with Duplicates

Implementation of a generic DuplicateBTree class which is similar to a Btree except that it can contain multiple copies of the
same key (at times with exactly the same value).

###  DuplicateBTree Interface

```java
public BTree(int b) throws bNotEvenException; /* Initializes an empty b-tree. Assume b is even. */

public boolean isEmpty(); /* Returns true if the tree is empty. */

public int size(); /* Returns the number of key-value pairs */

public int height(); /* Returns the height of this B-tree */

public Vector<Value> search(Key key) throws IllegalKeyException; /* Returns all values associated with a given key in a vector */

public void insert(Key key, Value val); /* Inserts the key-value pair */

public void delete(Key key) throws IllegalKeyException; /* Deletes all occurrences of key */

public String toString(); /* Prints all the tree in the format listed below */
```

#### toString() method works as follows-:
![Demo](https://user-images.githubusercontent.com/26283007/32026270-47d34e24-ba01-11e7-90ae-2b43ec72d5ed.png)

## Authors

* **Aditya Singh** 


