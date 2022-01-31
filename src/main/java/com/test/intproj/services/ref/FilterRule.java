package com.test.intproj.services.ref;

public class FilterRule {
    private String fieldPath;
    private FilterAction filterRule;


    public FilterRule(String fieldPath, FilterAction filterRule){
        this.fieldPath = fieldPath;
        this.filterRule = filterRule;
    }

    public String getFieldPath() {
        return fieldPath;
    }

    public void setFieldPath(String fieldPath) {
        this.fieldPath = fieldPath;
    }

    public FilterAction getFilterRule() {
        return filterRule;
    }

    public void setFilterRule(FilterAction filterRule) {
        this.filterRule = filterRule;
    }


    public enum FilterAction {
        MASK,
        REMOVE
    }
}
