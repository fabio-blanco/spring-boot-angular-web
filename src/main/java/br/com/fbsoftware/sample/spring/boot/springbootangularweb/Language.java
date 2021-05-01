package br.com.fbsoftware.sample.spring.boot.springbootangularweb;

/**
 * A programming language pojo representation
 *
 * @author Fabio Blanco
 * @since 30/04/2021
 */
public class Language {
    private String name;
    private boolean script;
    private String level;

    public Language(String name, boolean script, String level) {
        this.name = name;
        this.script = script;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isScript() {
        return script;
    }

    public void setScript(boolean script) {
        this.script = script;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
