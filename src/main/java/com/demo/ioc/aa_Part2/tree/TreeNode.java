package com.demo.ioc.aa_Part2.tree;

import java.util.ArrayList;

public class TreeNode {

    private String className;

    private ArrayList<TreeNode> treeChildren = new ArrayList<>();

    public ArrayList<TreeNode> getTreeChildren(){
        return treeChildren;
    }

    public void addChild(TreeNode child){
        treeChildren.add(child);
    }

    public String getName(){
        return className;
    }

    public void setName(String className){
        this.className = className;
    }
}
