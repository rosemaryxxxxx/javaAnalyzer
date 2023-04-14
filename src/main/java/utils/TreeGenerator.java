package utils;

import tree.TreeNode;

import java.util.*;

public class TreeGenerator {

    public static void main(String[] args) {
        List<String> paths = Arrays.asList(
                "pmd.deadcodetest.utils.KMP.test1111(int)",
                "pmd.deadcodetest.utils.KMP.buildNext(String)",
                "pmd.deadcodetest.utils.KMP.kmp(StringString)",
                "pmd.deadcodetest.utils.KMP.testprivate1111()"
        );

        //初始化树
        TreeNode root = generateTree(paths);
        //打印树的结构
        printTree(root, 0);
        //删除某个节点
        String path = "pmd.deadcodetest.utils.KMP.kmp(StringString)";
        printTree(deleteMethod(root,path),0);
        //把树形结构转化成list输出
        List<String> treeToList = dfsTree(root);
        System.out.println(treeToList);
    }

    private static TreeNode generateTree(List<String> paths) {
        TreeNode root = new TreeNode("root");

        for (String path : paths) {
            String[] segments = path.split("\\.");
            TreeNode node = root;

            for (String segment : segments) {
                if (!segment.isEmpty()) {
                    TreeNode child = node.getChild(segment);
                    if (child == null) {
                        child = new TreeNode(segment);
                        node.addChild(child);
                    }
                    node = child;
                }
            }
        }

        return root;
    }

    private static void printTree(TreeNode node, int level) {
        System.out.println(indent(level) + node.getName());

        for (TreeNode child : node.getChildren()) {
            printTree(child, level + 1);
        }
    }

    private static String indent(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }


    //
    public static TreeNode deleteMethod(TreeNode root,String path){
//        path = "pmd.deadcodetest.utils.KMP.kmp(StringString)";
        String[] segments = path.split("\\.");
        int len = segments.length;
        TreeNode node = root;
        int i = 0;
        for (; i<len-1;i++) {
            if (!segments[i].isEmpty()) {

                TreeNode child = node.getChild(segments[i]);
//                if (child == null) {
//                    child = new TreeNode(segment);
//                    node.addChild(child);
//                }
                node = child;
            }
        }
        node.removeChild(node.getChild(segments[i]));
        return root;
    }

    public static List<String> dfsTree(TreeNode root) {
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<List<TreeNode>> pathStack = new Stack<>();
        List<List<TreeNode>> result = new ArrayList<>();
        nodeStack.push(root);
        ArrayList<TreeNode> arrayList = new ArrayList<>();
        arrayList.add(root);
        pathStack.push(arrayList);

        while (!nodeStack.isEmpty()) {
            TreeNode curNode = nodeStack.pop();
            List<TreeNode> curPath = pathStack.pop();

            if (curNode.getChildren() == null || curNode.getChildren().size() <= 0) {
                result.add(curPath);
            } else {
                int childSize = curNode.getChildren().size();
                for (int i = childSize - 1; i >= 0; i--) {
                    TreeNode node = curNode.getChildren().get(i);
                    nodeStack.push(node);
                    List<TreeNode> list = new ArrayList<>(curPath);
                    list.add(node);
                    pathStack.push(list);
                }
            }
        }
        String path = "";
        List<String> res = new ArrayList<>();
        for(List<TreeNode> pathNode : result){
            for(TreeNode node : pathNode){
                path += "."+node.getName();
            }
            res.add(path.substring(6));
            path = "";
        }
        return res;
    }


}

