package LabProject;

public class AVLTree {
    private Node root;

    public AVLTree() {
        root = null;
    }

    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private int getBalance(Node node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    // Right rotation (for case LL and RL)
    private Node rightRotation(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform the rotation
        x.right = y;
        y.left = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return x;
    }

    private Node leftRotation(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform the rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void insert(Transaction t) {
        root = insertNode(root, t);
    }

    public Node insertNode(Node node, Transaction transaction) {
        // Step 1: BST insert
        if (node == null) {
            return new Node(transaction);
        }

        if (transaction.getAmount() < node.data.getAmount()) {
            node.left = insertNode(node.left, transaction);
        } else if (transaction.getAmount() > node.data.getAmount()) {
            node.right = insertNode(node.right, transaction);
        } else {
            return node;  // Duplicate values not allowed
        }

        // Step 2: Update height and balance factor
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        // Step 3: Get the balance factor of this ancestor node to check whether it became unbalanced
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases:
        // Left-left case
        if (balance > 1 && transaction.getAmount() < node.left.data.getAmount()) {
            return rightRotation(node);
        }

        // Right-right case
        if (balance < -1 && transaction.getAmount() > node.right.data.getAmount()) {
            return leftRotation(node);
        }

        // Left-right case
        if (balance > 1 && transaction.getAmount() > node.left.data.getAmount()) {
            node.left = leftRotation(node.left);
            return rightRotation(node);
        }

        // Right-left case
        if (balance < -1 && transaction.getAmount() < node.right.data.getAmount()) {
            node.right = rightRotation(node.right);
            return leftRotation(node);
        }

        // Return the (unchanged) node pointer that we inserted
        return node;
    }

    public boolean delete(Transaction t) {
        if (root == null) {
            System.out.println("There are no transactions to delete.");
            return false;
        }

        root = deleteNode(root, t);
        return true;
    }

    private Node deleteNode(Node node, Transaction transaction) {
        // Case when the tree is empty or the transaction is not found
        if (node == null) {
            System.out.println("\nTransaction with amount " + transaction.getAmount() + " does not exist in the expense tracker.");
            return null;  // Return null because there's no node to delete
        }

        // Traverse the tree to find the transaction to delete
        if (transaction.getAmount() < node.data.getAmount()) {
            node.left = deleteNode(node.left, transaction);
        } else if (transaction.getAmount() > node.data.getAmount()) {
            node.right = deleteNode(node.right, transaction);
        } else {
            // Node to be deleted is found

            // Handle case with no child or one child
            if (node.left == null || node.right == null) {
                Node temp = node.left != null ? node.left : node.right;

                if (temp == null) { // No child
                    node = null; // Delete the node
                } else {
                    node = temp; // Node has one child
                }
            } else {
                // Two child case
                // Find the successor in the right subtree
                Node temp = minVal(node.right);
                node.data = temp.data;

                node.right = deleteNode(node.right, temp.data);
            }
        }

        // Update height and balance of the node
        if (node != null) {
            node.height = Math.max(height(node.left), height(node.right)) + 1;

            int balance = getBalance(node);
            // If this node becomes unbalanced, then there are 4 cases

            // LL
            if (balance > 1 && getBalance(node.left) >= 0) {
                return rightRotation(node);
            }
            // Right-right case (Right heavy)
            if (balance < -1 && getBalance(node.right) <= 0) {
                return leftRotation(node);
            }
            // Left-right case (Left heavy, but right child is heavy)
            if (balance > 1 && getBalance(node.left) < 0) {
                node.left = leftRotation(node.left);
                return rightRotation(node);
            }
            // Right-left case (Right heavy, but left child is heavy)
            if (balance < -1 && getBalance(node.right) > 0) {
                node.right = rightRotation(node.right);
                return leftRotation(node);
            }
        }

        return node; // Return the (unchanged) node pointer
    }



    public Node minVal(Node node){
        Node curr = node;
        while (curr.left!=null){
            curr =curr.left;
        }
        return curr;
    }
    public void inOrder() {
        inOrderAVL(root);
    }

    public void inOrderAVL(Node node) {
        if (node != null) {
            inOrderAVL(node.left);  // Traverse left subtree
            System.out.println(node.data);  // Visit the node (make sure Transaction's toString is implemented)
            inOrderAVL(node.right);  // Traverse right subtree
        }
    }

    public double search(Node node, Transaction transaction) {
        if (node == null) {
            System.out.println("Transaction is empty.");
            return 0;
        }

        if (transaction.getAmount() == node.data.getAmount()) {
            System.out.println("The amount is present in the expense tracker.");
            return node.data.getAmount();
        }

        if (transaction.getAmount() < node.data.getAmount()) {
            return search(node.left, transaction);
        } else {
            return search(node.right, transaction);
        }
    }

    public boolean isEmpty() {
        return root == null; // If root is null, the tree is empty
    }
    public Node getRoot() {
        return root;
    }


}



