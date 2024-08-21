package com.dashboard;

import java.util.*;

public class FlatTreeToTree {
    public static TreeNode convert(List<Map<String, Integer>> flatTree) {
        Map<Integer, TreeNode> treeNodeMap = new HashMap<>();
        TreeNode root = null;
        for (Map<String, Integer> node : flatTree) {
            int id = node.get("id");
            Integer parentId = node.get("parent"); // 使用 Integer 类型以支持 null 值
            TreeNode treeNode = new TreeNode(id);
            treeNodeMap.put(id, treeNode);

            // 如果 parentId 为 null，则当前节点为根节点
            if (parentId == null) {
                root = treeNode;
            } else {
                TreeNode parentNode = treeNodeMap.get(parentId);
                if (parentNode != null) {
                    if (parentNode.children == null) { // 修正变量名和初始化
                        parentNode.children = new ArrayList<>();
                    }
                    parentNode.children.add(treeNode);
                }
            }
        }
        return root;
    }

    public static void main(String[] args) {
        List<Map<String, Integer>> flatTree = new ArrayList<>();
        flatTree.add(new HashMap<String, Integer>() {{
            put("id", 1);
            put("parent", null); // 使用 null 表示没有父节点
        }});
        flatTree.add(new HashMap<String, Integer>() {{
            put("id", 2);
            put("parent", 1);
        }});
        flatTree.add(new HashMap<String, Integer>() {{
            put("id", 3);
            put("parent", 1);
        }});
        flatTree.add(new HashMap<String, Integer>() {{
            put("id", 4);
            put("parent", 2);
        }});
        flatTree.add(new HashMap<String, Integer>() {{
            put("id", 5);
            put("parent", 2);
        }});

        TreeNode root = convert(flatTree);
        System.out.println(root); // 输出根节点的信息
        printTree(root, ""); // 打印树的结构
    }

    // 递归打印树的方法
    public static void printTree(TreeNode node, String prefix) {
        System.out.println(prefix + "+-- " + node.id);
        prefix += "|   ";
        if (node.children != null) {
            for (TreeNode child : node.children) {
                printTree(child, prefix);
            }
        }
    }
}

class TreeNode {
    int id;
    List<TreeNode> children; // 修正变量名

    public TreeNode(int id) {
        this.id = id;
        this.children = new ArrayList<>(); // 初始化 children 列表
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id=" + id +
                ", children=" + children +
                '}';
    }
}
