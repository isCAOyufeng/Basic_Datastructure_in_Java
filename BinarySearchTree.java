import java.util.ArrayList;

public class BinarySearchTree {  // find findMin findMax insert remove
    public static void main(String[] args) {
        BinarySearchTree b = new BinarySearchTree();
        b.root = b.insert(8);
        b.insert(1);
        b.insert(0);
        b.insert(9);
        b.insert(2);
        b.insert(14);
        b.insert(13);

//        b.preOrder();

//        System.out.println(b.size());

//        System.out.println(b.findKthMin(7).data);

//        ArrayList<Comparable> indicator = new ArrayList<>();
//        System.out.println(b.preOrder(b.root, indicator));

        System.out.println(b.isBinarySearch());
    }

    private BinarySearchNode root;

    public BinarySearchTree() {
        this.root = null;
    }

    public BinarySearchTree(BinarySearchNode t) {
        this.root = t;
    }

    public boolean isBinarySearch() {
        return isBinarySearch(root);
    }

    private boolean isBinarySearch(BinarySearchNode t) {
        if (t == null || (t.left == null && t.right == null)) {
            return true;
        } else if (t.left != null && t.left.data.compareTo(t.data) >= 0) {
            return false;
        } else if (t.right != null && t.right.data.compareTo(t.data) <= 0) {
            return false;
        } else if (t.left != null) {
            ArrayList<Comparable> indicator = new ArrayList<>();
            ArrayList<Comparable> list = preOrder(t.left, indicator);
            for (int i = 1; i < size(t.left.left) + 1; i++) {
                if (list.get(i).compareTo(t.left.data) >= 0) {
                    return false;
                }
            }
            for (int j = size(t.left.left) + 1; j < list.size(); j++) {
                if (list.get(j).compareTo(t.left.data) <= 0) {
                    return false;
                }
            }
        } else if (t.right != null) {
            ArrayList<Comparable> indicator = new ArrayList<>();
            ArrayList<Comparable> list = preOrder(t.right, indicator);
            for (int i = 1; i < size(t.right.left) + 1; i++) {
                if (list.get(i).compareTo(t.right.data) >= 0) {
                    return false;
                }
            }
            for (int j = size(t.right.left) + 1; j < list.size(); j++) {
                if (list.get(j).compareTo(t.right.data) <= 0) {
                    return false;
                }
            }
        } else {
            return true;
        }

        if (isBinarySearch(t.left) && isBinarySearch(t.right)) {
            return true;
        } else {
            return false;
        }
    }

    public BinarySearchNode find(Comparable x) {  // have thought of aborting tree class and only work with node class. then I found the find method absolutely needs a tree class...
        return find(x, root);
    }

    private BinarySearchNode find(Comparable x, BinarySearchNode t) {
        if (t == null) {
            return null;
        }
        if (t.data.compareTo(x) > 0) {
            return find(x, t.left);
        } else if (t.data.compareTo(x) < 0) {
            return find(x, t.right);
        } else {
            return t;
        }
    }

    public BinarySearchNode findMin() {
        return findMin(root);
    }

    private BinarySearchNode findMin(BinarySearchNode t) {
        if (t == null) {
            return null;
        }
        while (t.left != null) {
            t = t.left;
        }
        return t;
    }

    public BinarySearchNode findKthMin(int k) {
        return findKthMin(root, k);
    }

    private BinarySearchNode findKthMin(BinarySearchNode t, int k) {
        if (t.left == null && t.right == null && k == 1) {
            return t;
        }
        if (k <= size(t.left)) {
            t = t.left;
        }
        if (k == size(t.left) + 1) {
            return t;
        }
        if (k > size(t.left) + 1) {
            k = k - size(t.left) - 1;
            if (t.right == null) {
                return null;
            }
            t = t.right;
        }
        return findKthMin(t, k);
    }

    public BinarySearchNode findMax() {
        return findMax(root);
    }

    private BinarySearchNode findMax(BinarySearchNode t) {
        if (t == null) {
            return null;
        } else if (t.right == null) {
            return t;
        }
        return findMax(t.right);
    }

    public BinarySearchNode insert(Comparable x) {
        return insert(x, root);
    }

    private BinarySearchNode insert(Comparable x, BinarySearchNode t) {
        if (t == null) {
            return new BinarySearchNode(x);
        }
        if (t.data.compareTo(x) > 0) {
            t.left = insert(x, t.left);
        }
        if (t.data.compareTo(x) < 0) {
            t.right = insert(x, t.right);
        }
        return t;
    }

    public BinarySearchNode remove(Comparable x) {
        return remove(x, root);
    }

    private BinarySearchNode remove(Comparable x, BinarySearchNode t) {
        if (t == null) {
            return t;
        }
        if (t.data.compareTo(x) > 0) {
            t.left = remove(x, t.left);
        } else if (t.data.compareTo(x) < 0) {
            t.right = remove(x, t.right);
        } else {  // t is found
            if (t.left == null && t.right == null) { // t is a leaf
                t = null;
            } else if (t.left != null && t.right != null) {
                t.data = findMax(t.left).data;
                t.left = remove(findMax(t.left).data);
            } else {
                t = (t.left == null) ? t.right : t.left;
            }
        }
        return t;
    }

    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(BinarySearchNode t) {
        if (t == null) {
            return;
        }

        System.out.print(t.data);

        BinarySearchNode lNode = t.left;
        preOrder(lNode);

        BinarySearchNode rNode = t.right;
        preOrder(rNode);
    }

    private ArrayList<Comparable> preOrder(BinarySearchNode t, ArrayList<Comparable> indicator) {
        if (t == null) {
            return null;
        }

        indicator.add(t.data);

        BinarySearchNode lNode = t.left;
        preOrder(lNode, indicator);

        BinarySearchNode rNode = t.right;
        preOrder(rNode, indicator);

        return indicator;
    }

    public int size() {
        return size(root);
    }

    private int size(BinarySearchNode t) {
        int size;
        if (t == null) {
            size = 0;
        } else if (t.left == null && t.right == null) {
            size = 1;
        } else if (t.left != null && t.right != null) {
            size = 1 + size(t.left) + size(t.right);
        } else {
            size = (t.left == null) ? 1 + size(t.right) : 1 + size(t.left);
        }
        return size;
    }

    private static class BinarySearchNode {
        private Comparable data;
        private BinarySearchNode left;
        private BinarySearchNode right;

        public BinarySearchNode() {
            this.data = null;
            this.left = null;
            this.right = null;
        }

        public BinarySearchNode(Comparable data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
}
