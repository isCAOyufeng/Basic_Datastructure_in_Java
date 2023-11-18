import java.util.LinkedList;

public class BinaryTree {

    public static void main(String[] args) {
        BinaryTree a = new BinaryTree();
        a.root = a.createTree("EBCDAFHIGJ", "EDCBIHJGFA");
        a.preorder();
        a.splay();
        a.preorder();
//        a.levelOrder();

//        BinaryTree b = new BinaryTree();
//        b.root = b.expressionTree("ab+cde+**");
//        b.inorder();
    }
    private BinaryNode root;

    public BinaryTree() {
        this.root = null;
    }

    public BinaryTree(BinaryNode root) {
        this.root = root;
    }

    public void makeTree(Object data, BinaryNode left, BinaryNode right) {
        if (root == null) {
            root = new BinaryNode();
        }
        root.data = data;
        root.left = left;
        root.right = right;
    }

    public BinaryNode createTree(String inorder, String postorder) {
        int length = inorder.length();

        if (length == 0) {
            return null;
        }

        Object rootData = postorder.substring(length - 1);
        BinaryNode root = new BinaryNode(rootData);

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

    public void splay() {
        splay(root);
    }

    private void splay(BinaryNode t) {
        if (t == null) {
            return;
        }
        if (t.data.equals('Z')) {
            return;
        }
        if (t.left != null && t.right != null) {
            ;
        } else if (t.left == null && t.right == null) {
            t.left = new BinaryNode('Z');
            t.right = new BinaryNode('Z');
        } else {
            if (t.left == null) {
                t.left = new BinaryNode('Z');
            } else {
                t.right = new BinaryNode('Z');
            }
        }

        BinaryNode lNode = t.left;
        splay(lNode);

        BinaryNode rNode = t.right;
        splay(rNode);
    }

    public BinaryNode expressionTree(String expression) {
        int length = expression.length();
        LinkedList<BinaryNode> strStack = new LinkedList<>();
        char[] expr = expression.toCharArray();
        int i = 0;
        while (i < length) {
            while (Character.isLetter(expr[i])) {
                strStack.push(new BinaryNode(expr[i]));
                i++;
            }
//            System.out.println(strStack.size());
            if (expr[i] == '-') {
                BinaryNode operator = new BinaryNode(expr[i]);
                BinaryNode operand = strStack.pop();
                operator.right = operand;
                strStack.push(operator);
            } else {
                BinaryNode operator = new BinaryNode(expr[i]);
                BinaryNode secondOperand = strStack.pop();
                BinaryNode firstOperand = strStack.pop();
                operator.left = firstOperand;
                operator.right = secondOperand;
                strStack.push(operator);
            }
            i++;
        }
        return strStack.pop();
    }

    public void preorder() {
        preorder(root);
    }

    private void preorder(BinaryNode t) {
        if (t == null) {
            return;
        }
        System.out.print(t.data);

        BinaryNode lNode = t.left;
        preorder(lNode);

        BinaryNode rNode = t.right;
        preorder(rNode);
    }

    public void inorder() {
        inorder(root);
    }

    private void inorder(BinaryNode t) {
        if (t == null) {
            return;
        }
        BinaryNode lNode = t.left;
        inorder(lNode);

        System.out.print(t.data);

        BinaryNode rNode = t.right;
        inorder(rNode);
    }

    public void levelOrder() {
        levelOrder(root);
    }

    private void levelOrder(BinaryNode t) {
        // 采用队列数据结构。新建队列并添加树根结点：
        LinkedList<BinaryNode> nodeQueue = new LinkedList<>();
        if (t != null) {
            nodeQueue.add(root);
        }
        // 当队列不为空时，弹出+打印队首的节点，并将其左、右节点（如果非null）添加至队列。循环执行直到弹出最后一个节点，全队列为空，跳出循环：
        while (!nodeQueue.isEmpty()) {
            BinaryNode popped = nodeQueue.pop();
            System.out.print(popped.data);
            if (popped.left != null) {
                nodeQueue.add(popped.left);
            }
            if (popped.right != null) {
                nodeQueue.add(popped.right);
            }
        }
    }

    private static class BinaryNode {
        private Object data;
        private BinaryNode left;
        private BinaryNode right;

        public BinaryNode() {
            this.data = this.left = this.right = null;
        }

        public BinaryNode(Object data) {
            this.data = data;
            this.left = this.right = null;
        }
    }
}
