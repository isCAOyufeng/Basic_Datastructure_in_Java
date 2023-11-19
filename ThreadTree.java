public class ThreadTree {  // left指向中序遍历前驱，right指向中序遍历后继
    public static void main(String[] args) {
        ThreadTree a = new ThreadTree();
        a.temp = a.createTree("GBHACEDF", "GHBEFDCA");
        a.addThread();

//        // G
//        System.out.println(a.temp.left.left.data);
//        System.out.println(a.temp.left.left.leftThread);
//        System.out.println(a.temp.left.left.rightThread);
//        System.out.println(a.temp.left.left.left);
//        // B
//        System.out.println(a.temp.left.left.right.data);
//        System.out.println(a.temp.left.left.right.leftThread);
//        System.out.println(a.temp.left.left.right.rightThread);
//        // H
//        System.out.println(a.temp.left.left.right.right.data);
//        System.out.println(a.temp.left.left.right.right.leftThread);
//        System.out.println(a.temp.left.left.right.right.rightThread);
//        // A
//        System.out.println(a.temp.left.left.right.right.right.data);
//        System.out.println(a.temp.left.left.right.right.right.leftThread);
//        System.out.println(a.temp.left.left.right.right.right.rightThread);
//        // C
//        System.out.println(a.temp.right.data);
//        System.out.println(a.temp.right.leftThread);
//        System.out.println(a.temp.right.rightThread);
//        // D
//        System.out.println(a.temp.right.right.data);
//        System.out.println(a.temp.right.right.leftThread);
//        System.out.println(a.temp.right.right.rightThread);
//        // E
//        System.out.println(a.temp.right.right.left.data);
//        System.out.println(a.temp.right.right.left.leftThread);
//        System.out.println(a.temp.right.right.left.rightThread);
//        // F
//        System.out.println(a.temp.right.right.right.data);
//        System.out.println(a.temp.right.right.right.leftThread);
//        System.out.println(a.temp.right.right.right.rightThread);
//        System.out.println(a.temp.right.right.right.right);

        a.inorder();
    }

    private ThreadNode root;
    private ThreadNode temp;
    private ThreadNode prev;

    public ThreadTree() {
        this.root = null;
        this.temp = root;
        this.prev = null;
    }

    public void preorder() {
        preorder(temp);
    }

    private void preorder(ThreadNode t) {
        if (t == null) {
            return;
        }
        System.out.print(t.data);

        ThreadNode lNode = t.left;
        preorder(lNode);

        ThreadNode rNode = t.right;
        preorder(rNode);
    }

    public ThreadNode createTree(String inorder, String postorder) {
        int length = inorder.length();

        if (length == 0) {
            return null;
        }

        Object rootData = postorder.substring(length - 1);
        ThreadNode root = new ThreadNode(rootData);

        int i = 0;
        while (!inorder.substring(i, i + 1).equals(rootData)) {
            i++;
        }

        String inLeftChild = inorder.substring(0, i);
        String inRightChild = inorder.substring(i + 1);

        String postLeftChild = postorder.substring(0, i);
        String postRightChild = postorder.substring(i,length - 1);

        root.left = createTree(inLeftChild,postLeftChild);
        root.right = createTree(inRightChild, postRightChild);

        return root;
    }

    public void addThread() {
//        ThreadNode prev = new ThreadNode(null);
        addThread(temp);
    }

    private void addThread(ThreadNode t) {
        if (t == null) {
            return;
        }

        addThread(t.left);

        if (t.left == null) {
            t.leftThread = true;
            t.left = prev;
        }
        if (prev != null && prev.right == null) {  // 这里的prev ！= null很重要：否则会出现nullPointerException。随后定义了prev = t，prev不再是null，程序主体逻辑得以执行
            prev.rightThread = true;
            prev.right = t;
        }
        prev = t;

        addThread(t.right);
    }

    public void inorder() {
        inorder(temp);
    }

    private void inorder(ThreadNode t) {
        if (t == null) {
            return;
        }
        while (t.left != null) {
            t = t.left;
        }
        while (t != null) {
            System.out.println(t.data);
            if (t.rightThread) {
                t = t.right;
            } else {
                t = t.right;
                if (t == null) {
                    return;
                }
                while (t.left != null && !t.leftThread) {
                    t = t.left;
                }
            }
        }
    }

    private static class ThreadNode {
        private Object data;
        private ThreadNode left;
        private ThreadNode right;
        private boolean leftThread;
        private boolean rightThread;

        public ThreadNode(Object data) {
            this.data = data;
            this.left = this.right = null;
            this.leftThread = this.rightThread = false;
        }
    }
}
