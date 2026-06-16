package com.hanbit.egovgen.model;

/**
 * 테이블 컬럼 한 개의 메타정보.
 * DDL 파싱 결과를 코드 생성 템플릿이 소비하는 중간 모델이다.
 */
public class ColumnMeta {

    private String columnName;   // 원본 DB 컬럼명 (예: RESTDE_DE)
    private String fieldName;    // camelCase 자바 필드명 (예: restdeDe)
    private String javaType;     // 매핑된 자바 타입 (예: String, int, long, java.math.BigDecimal)
    private String jdbcType;     // 원본 DDL 타입 (예: VARCHAR, INT) — 참고용
    private int size;            // 길이/정밀도 (없으면 0)
    private boolean notNull;     // NOT NULL 여부
    private boolean primaryKey;  // PK 여부
    private String comment;      // 컬럼 코멘트 → 한글 라벨로 사용

    public String getColumnName() { return columnName; }
    public void setColumnName(String columnName) { this.columnName = columnName; }

    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }

    public String getJavaType() { return javaType; }
    public void setJavaType(String javaType) { this.javaType = javaType; }

    public String getJdbcType() { return jdbcType; }
    public void setJdbcType(String jdbcType) { this.jdbcType = jdbcType; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public boolean isNotNull() { return notNull; }
    public void setNotNull(boolean notNull) { this.notNull = notNull; }

    public boolean isPrimaryKey() { return primaryKey; }
    public void setPrimaryKey(boolean primaryKey) { this.primaryKey = primaryKey; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    /** 화면 라벨: 코멘트가 있으면 코멘트, 없으면 필드명. */
    public String label() {
        return (comment != null && !comment.isBlank()) ? comment.trim() : fieldName;
    }

    /** 검색 후보 여부: 문자열 타입이면서 PK가 아닌 컬럼만 검색 대상으로 본다. */
    public boolean searchable() {
        return "String".equals(javaType) && !primaryKey;
    }
}
