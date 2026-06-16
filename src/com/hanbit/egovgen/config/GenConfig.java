package com.hanbit.egovgen.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * 프로젝트 적응형 설정. 프로젝트마다 달라지는 항목을 외부 properties로 주입한다.
 * (차별점 B의 1차 버전 — 설정 항목을 의도적으로 소수로 제한)
 *
 * 지원 키:
 *   basePackage    : 생성 코드 루트 패키지 (예: egovframework.let.sym.cal)
 *   module         : URL/뷰/Mapper 경로 세그먼트 (예: sym/cal)
 *   tablePrefix    : 엔티티명 도출 시 제거할 테이블 prefix (예: LETTN)
 *   dbType         : 대상 DB (1차 mysql 고정)
 *   outputDir      : 산출물 출력 루트 (기본 ./output)
 *   daoBase        : DAO 베이스 클래스 FQCN
 *   serviceBase    : ServiceImpl 베이스 클래스 FQCN
 *   paginationInfo : PaginationInfo FQCN
 */
public class GenConfig {

    private String basePackage = "egovframework.sample";
    private String module = "sample";
    private String tablePrefix = "";
    private String dbType = "mysql";
    private String outputDir = "./output";
    private boolean useIdgnr = false;   // 채번(EgovIdGnrService) 적용 여부

    // 공통 컴포넌트 베이스 경로 (eGov 5.0.1 검증값을 기본값으로)
    private String daoBase = "org.egovframe.rte.psl.dataaccess.EgovAbstractMapper";
    private String serviceBase = "org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl";
    private String paginationInfo = "org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo";

    public static GenConfig load(Path propsFile) throws IOException {
        GenConfig c = new GenConfig();
        if (propsFile == null || !Files.exists(propsFile)) return c;
        Properties p = new Properties();
        try (InputStream in = Files.newInputStream(propsFile)) {
            p.load(in);
        }
        c.basePackage = p.getProperty("basePackage", c.basePackage);
        c.module = p.getProperty("module", c.module);
        c.tablePrefix = p.getProperty("tablePrefix", c.tablePrefix);
        c.dbType = p.getProperty("dbType", c.dbType);
        c.outputDir = p.getProperty("outputDir", c.outputDir);
        c.useIdgnr = Boolean.parseBoolean(p.getProperty("useIdgnr", String.valueOf(c.useIdgnr)));
        c.daoBase = p.getProperty("daoBase", c.daoBase);
        c.serviceBase = p.getProperty("serviceBase", c.serviceBase);
        c.paginationInfo = p.getProperty("paginationInfo", c.paginationInfo);
        return c;
    }

    /** CLI 인자로 개별 항목 덮어쓰기. */
    public void override(String key, String value) {
        if (value == null) return;
        switch (key) {
            case "package" -> basePackage = value;
            case "module" -> module = value;
            case "prefix" -> tablePrefix = value;
            case "dbType" -> dbType = value;
            case "out" -> outputDir = value;
            case "idgnr" -> useIdgnr = Boolean.parseBoolean(value);
            default -> { /* 무시 */ }
        }
    }

    public String basePackage() { return basePackage; }
    public String module() { return module; }
    public String tablePrefix() { return tablePrefix; }
    public String dbType() { return dbType; }
    public String outputDir() { return outputDir; }
    public boolean useIdgnr() { return useIdgnr; }
    public String daoBase() { return daoBase; }
    public String serviceBase() { return serviceBase; }
    public String paginationInfo() { return paginationInfo; }

    /** FQCN에서 단순 클래스명만. */
    public static String simpleName(String fqcn) {
        int dot = fqcn.lastIndexOf('.');
        return dot >= 0 ? fqcn.substring(dot + 1) : fqcn;
    }
}
