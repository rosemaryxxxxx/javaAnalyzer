package javaparser.visitors;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static shixian.utils.Utils.*;

//获取java文件中函数中定义的每一个变量的类型
public class VariableTypeVisitor extends VoidVisitorAdapter<Void> {
    static Map<String, String> augementAndType = new HashMap<>();
    static String PATH;
    static Map<String,Map<String,String>> classAndAugmentType = new HashMap<>();


    @Override
    public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);

        // Get the variable declarations for this method
        md.getBody().ifPresent(body -> body.findAll(VariableDeclarationExpr.class).forEach(vde -> {

            // Iterate over each variable in the declaration and print its type
            vde.getVariables().forEach(variable ->
                    augementAndType.put(variable.getNameAsString(),variable.getTypeAsString())
//                    System.out.println(variable.getName()+": "+variable.getTypeAsString())
                    );
        }));

    }



    public static void main(String[] args) throws FileNotFoundException {
        String path = "D:\\code\\javazip\\t0607\\constructor\\TestAnimal.java";
    }

    /**
     * 获取java文件中所有的参数和对应的类型
     * @param javaPath
     * @throws FileNotFoundException
     */
    public static Map<String,Map<String,String>> extarctClassAndAugmentType(String javaPath, String beforeZipName) throws FileNotFoundException {
        PATH = javaPath;
        String packageName1;
        //example: packageName1 = pmd.deadcodetest.utils.KMP，准确的说是类名不是包名。
        packageName1 = replaceSlashWithPoint(PATH.substring(index0fLastSlash(beforeZipName)+1));
        FileInputStream in = new FileInputStream(javaPath);
        try {
            CompilationUnit cu = StaticJavaParser.parse(in);
            VariableTypeVisitor visitor = new VariableTypeVisitor();
            visitor.visit(cu,null);
        }catch (Exception e){
            e.printStackTrace();
        }
//        CompilationUnit cu = StaticJavaParser.parse(in);
//        VariableTypeVisitor visitor = new VariableTypeVisitor();
//        visitor.visit(cu,null);
        classAndAugmentType.put(packageName1,augementAndType);
        return classAndAugmentType;
    }

}

