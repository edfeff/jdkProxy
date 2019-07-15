package com.edfeff.proxy.jdk.support;

/**
 * @author wangpp
 */
public class CodeHelper implements KeyWord {

    private StringBuilder code;

    public CodeHelper() {
        code = new StringBuilder();
    }

    public CodeHelper addPackage(String packageName) {
        this.code.append(PACKAGE)
                .append(SPACE)
                .append(packageName)
                .append(SEMICOLON);
        this.addBlankLine();
        return this;
    }

    public CodeHelper addImport(Class<?>[] classes) {
        for (Class c : classes) {
            this.addImport(c.getName());
        }
        return this;
    }

    public CodeHelper addCloseBrace() {
        this.code.append("}");
        this.addBlankLine();
        return this;
    }

    public CodeHelper addOpenBrace() {
        this.code.append("{");
        this.addBlankLine();
        return this;
    }

    public CodeHelper addImport(String s) {
        this.code.append(IMPORT).append(SPACE)
                .append(s)
                .append(SEMICOLON)
                .append(LN);
        return this;
    }

    public CodeHelper addBlankLine() {
        this.code.append(LN);
        return this;
    }


    public CodeHelper add(String line) {
        code.append(line).append(LN);
        return this;
    }

    public String code() {
        return code.toString();
    }

}
