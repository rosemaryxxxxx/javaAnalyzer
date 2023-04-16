package pmd;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.PMDConfiguration;

public class PmdTest {
    public static void main(String[] args) {
//        String[] pmdArgs = {
//                "-d", "D:/code/javaAnalyzer/src/main/java/utils.KMP.java",
//                "-R", "D:/software/pmd/pmd-src-6.55.0/pmd-java/src/main/resources/rulesets/java/unusedcode.xml",
//                "-f", "json",
//                "-r", "D:/code/javaAnalyzer/src/main/java/t1.json"
//        };
//        PMD.main(pmdArgs);

    PMDConfiguration configuration = new PMDConfiguration();
        configuration.setInputPaths("D:/code/javaAnalyzer/src/main/java/pmd/deadcodetest/utils/KMPWithMain.java");
        configuration.addRuleSet("D:/code/pmdrules/unusedmethod.xml");
        configuration.setReportFormat("json");
        configuration.setReportFile("D:/code/pmdrules/report/KMPWithMain2.json");
        PMD.runPmd(configuration);

    }



}
