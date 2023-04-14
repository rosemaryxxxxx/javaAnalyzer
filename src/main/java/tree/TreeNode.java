package tree;

import utils.TreeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeNode {

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

    public void removeChild(TreeNode child){
        children.remove(child);
    }

//    public List<String> treeToList(){
//
//    }

}
