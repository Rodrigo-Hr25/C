import org.stringtemplate.v4.*;

public class StringTemplateWithType {
    private ST template;
    private VariableTypes type;

    public StringTemplateWithType(ST template, VariableTypes type) {
        this.template = template;
        this.type = type;
    }

    public ST getTemplate() {
        return template;
    }

    public VariableTypes getType() {
        return type;
    }
}