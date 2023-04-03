package utils;

import java.util.*;

public class TreeGenerator {

    public static void main(String[] args) {
        List<String> paths = Arrays.asList(
                "pmd.deadcodetest.utils.KMP.test1111(int)",
                "pmd.deadcodetest.utils.KMP.buildNext(String)",
                "pmd.deadcodetest.utils.KMP.kmp(StringString)",
                "pmd.deadcodetest.utils.KMP.testprivate1111()"
        );

//        for (String s: paths
//             ) {
//            s.replaceAll("/",".");
//        }

//        pmd.deadcodetest.utils.KMP.test1111(int): [println]
//        pmd.deadcodetest.utils.KMP.buildNext(String): [length, charAt]
//        pmd.deadcodetest.utils.KMP.kmp(StringString): [println, length, buildNext, charAt]
//        pmd.deadcodetest.utils.KMP.testprivate1111(): [println]


        TreeNode root = generateTree(paths);

        printTree(root, 0);
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

    private static class TreeNode {

        private String name;
        private List<TreeNode> children;

        public TreeNode(String name) {
            this.name = name;
            this.children = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public List<TreeNode> getChildren() {
            return children;
        }

        public void addChild(TreeNode child) {
            children.add(child);
        }

        public TreeNode getChild(String name) {
            for (TreeNode child : children) {
                if (child.getName().equals(name)) {
                    return child;
                }
            }
            return null;
        }
    }

}

