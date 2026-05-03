// BinarySearchTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
    /**
     * Construct the tree.
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert(AnyType x) {
        root = insert(x, root);
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove(AnyType x) {
        root = remove(x, root);
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin() throws UnderflowException {
        if (isEmpty())
            throw new UnderflowException();
        return findMin(root).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax() throws UnderflowException {
        if (isEmpty())
            throw new UnderflowException();
        return findMax(root).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains(AnyType x) {
        return contains(x, root);
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        // Updated to use the new isLogicallyEmpty method.
        return isLogicallyEmpty(root);
    }

    // New method to check if a subtree is logically empty.
    // A subtree is logically empty if it has no non-deleted nodes.
private boolean isLogicallyEmpty(BinaryNode<AnyType> t) {
    if (t == null)
        return true;
    
     // Otherwise, the subtree is logically empty if and only if
     // both of its subtrees are logically empty.
    if (!t.isDeleted && isLogicallyEmpty(t.left) && isLogicallyEmpty(t.right))
        return false;

     // Otherwise, the subtree is logically empty if and only if
    // both of its subtrees are logically empty.
    
    return isLogicallyEmpty(t.left) && isLogicallyEmpty(t.right);
}
    /**
     * Print the tree contents in sorted order.
     */
    public void printTree() {
        if (isEmpty())
            System.out.println("Empty tree");
        else
            printTree(root);
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return new BinaryNode<>(x, null, null);

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            t.left = insert(x, t.left);
        else if (compareResult > 0)
            t.right = insert(x, t.right);
        else
            ;  // Duplicate; do nothing
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return t;   // Item not found; do nothing

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            t.left = remove(x, t.left);
        else if (compareResult > 0)
            t.right = remove(x, t.right);
        else {
            // Node found, mark it as deleted
            t.isDeleted = true;
        }
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree, ignoring logically deleted nodes.
     * @param t the node that roots the subtree.
     * @return node containing the smallest item or null if all nodes in the subtree are logically deleted.
     */
    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
        if (t == null)
            return null;
        else if (t.left == null && !t.isDeleted)
            return t;
        else {
            BinaryNode<AnyType> leftMin = findMin(t.left);
            // If the leftMin is not null, return it. If it is null, check if the current node is deleted.
            // If not, return the current node, otherwise proceed to the right.
            if (leftMin != null)
                return leftMin;
            else if (!t.isDeleted)
                return t;
            else
                return findMin(t.right);
        }
    }

/**
     * Internal method to find the largest item in a subtree, ignoring logically deleted nodes.
     * @param t the node that roots the subtree.
     * @return node containing the largest item or null if all nodes in the subtree are logically deleted.
     */
private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
    if (t != null) {
        BinaryNode<AnyType> maxNode = findMax(t.right);  // first, try to find max in the right subtree
        if (maxNode != null && !maxNode.isDeleted)
            return maxNode;
        if (!t.isDeleted)
            return t;  // if max of right subtree is deleted (or null), and this node is not deleted, return this node
        return findMax(t.left);  // else find max in the left subtree
    }
    return null;
}

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains(AnyType x, BinaryNode<AnyType> t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else
            return !t.isDeleted; // Only return true if the node is not marked as deleted
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the subtree.
     */
    private void printTree(BinaryNode<AnyType> t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    /**
     * Internal method to compute height of a subtree.
     * @param t the node that roots the subtree.
     */
    private int height(BinaryNode<AnyType> t) {
        if (t == null)
            return -1;
        else
            return 1 + Math.max(height(t.left), height(t.right));
    }

    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<AnyType> {
        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
        boolean isDeleted;          // Flag for lazy deletion

        BinaryNode(AnyType theElement) {
            this(theElement, null, null);
        }

        BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt) {
            element = theElement;
            left = lt;
            right = rt;
            isDeleted = false;       // Initialize the isDeleted flag to false
        }
    }

    /** The tree root. */
    private BinaryNode<AnyType> root;

    // Test program
    public static void main(String[] args) {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        final int NUMS = 4000;
        final int GAP = 37;

        System.out.println("Checking... (no more output means success)");

        for (int i = GAP; i != 0; i = (i + GAP) % NUMS)
            t.insert(i);

        for (int i = 1; i < NUMS; i += 2)
            t.remove(i);

        if (NUMS < 40)
            t.printTree();

        try {
            if (t.findMin() != 2 || t.findMax() != NUMS - 2)
                System.out.println("FindMin or FindMax error!");
        } catch (UnderflowException e) {
            System.out.println("Error - tree is empty!");
        }

        for (int i = 2; i < NUMS; i += 2)
            if (!t.contains(i))
                System.out.println("Find error1!");

        for (int i = 1; i < NUMS; i += 2) {
            if (t.contains(i))
                System.out.println("Find error2!");
        }
    }
}