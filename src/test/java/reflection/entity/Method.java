package reflection.entity;

import java.util.List;

public class Method {
    private Long methodID;
    private String fullMethodName;
    private String methodName;
    private String className;
    private String type;
    private List<String> invokeList;
    private List<String> importList;
    private List<String> augmentList;
    private Integer startLine;
    private Integer startCol;
    private Integer endLine;
    private Integer endCol;

    //getter and setter
    public Long getMethodID() {
        return methodID;
    }

    public void setMethodID(Long methodID) {
        this.methodID = methodID;
    }

    public String getFullMethodName() {
        return fullMethodName;
    }

    public void setFullMethodName(String fullMethodName) {
        this.fullMethodName = fullMethodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getInvokeList() {
        return invokeList;
    }

    public void setInvokeList(List<String> invokeList) {
        this.invokeList = invokeList;
    }

    public List<String> getImportList() {
        return importList;
    }

    public void setImportList(List<String> importList) {
        this.importList = importList;
    }

    public List<String> getAugmentList() {
        return augmentList;
    }

    public void setAugmentList(List<String> augmentList) {
        this.augmentList = augmentList;
    }

    public Integer getStartLine() {
        return startLine;
    }

    public void setStartLine(Integer startLine) {
        this.startLine = startLine;
    }

    public Integer getStartCol() {
        return startCol;
    }

    public void setStartCol(Integer startCol) {
        this.startCol = startCol;
    }

    public Integer getEndLine() {
        return endLine;
    }

    public void setEndLine(Integer endLine) {
        this.endLine = endLine;
    }

    public Integer getEndCol() {
        return endCol;
    }

    public void setEndCol(Integer endCol) {
        this.endCol = endCol;
    }

    @Override
    public String toString() {
        return "Method{" +
                "methodID=" + methodID +
                ", fullMethodName='" + fullMethodName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", className='" + className + '\'' +
                ", type='" + type + '\'' +
                ", invokeList=" + invokeList +
                ", importList=" + importList +
                ", augmentList=" + augmentList +
                ", startLine=" + startLine +
                ", startCol=" + startCol +
                ", endLine=" + endLine +
                ", endCol=" + endCol +
                '}';
    }
}
