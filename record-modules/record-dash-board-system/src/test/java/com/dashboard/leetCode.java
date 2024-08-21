package com.dashboard;

public class leetCode {
    /**
     *
     输入：root = [3,9,20,null,null,15,7]
     输出：2
     示例 2：

     输入：root = [2,null,3,null,4,null,5,null,6]
     输出：5
     */

    public static void main(String[] args) {
        leetCode leetCode=new leetCode();
        TreeNode root=new TreeNode(2);
        root.left=new TreeNode(3);
        root.left.left=new TreeNode(4);
        root.left.left.left=new TreeNode(5);
        root.left.left.left.left=new TreeNode(6);
        int i=0;
        int right=0;
        int bst = leetCode.BST(root);
       System.out.println(bst);
    }

    public int BST(TreeNode treeNode) {
        if(treeNode==null){
            return 0;
        }
        int left=BST(treeNode.left);

        int right=BST(treeNode.right);
        if(left==0 || right==0){
            return Math.max(left,right)+1;
        }else {
            return Math.min(left,right)+1;
        }
    }


     public static class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
         TreeNode() {}
          TreeNode(int val) { this.val = val; }
          TreeNode(int val, TreeNode left, TreeNode right) {
              this.val = val;
              this.left = left;
              this.right = right;
          }
      }
}
