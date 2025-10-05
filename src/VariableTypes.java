public enum VariableTypes {
	STRING, NUMBER, BOOLEAN, IMAGE, PERCENTAGE, PIXEL, CODE,
	LIST_STRING, LIST_NUMBER, LIST_BOOLEAN, LIST_IMAGE, LIST_PERCENTAGE, LIST_PIXEL, LIST_EMPTY,
    VOID,
    ERROR,
    TYPE;

	public boolean isNumeric() {
		return this == NUMBER || this == PERCENTAGE;
	}

    public static VariableTypes fromString(String type) {
		// Handle nested list types dynamically
		if (type.startsWith("list_")) {
			String remaining = type.substring(5); // Remove "list_"
			if (remaining.startsWith("list_")) {
				// This is a nested list, return as LIST_* type but the actual nested type
				// will be handled by the semantic analyzer context
				String baseType = extractBaseType(remaining);
				return fromString("list_" + baseType);
			} else {
				return switch (remaining) {
					case "string" -> LIST_STRING;
					case "number" -> LIST_NUMBER;
					case "boolean" -> LIST_BOOLEAN;
					case "image" -> LIST_IMAGE;
					case "percentage" -> LIST_PERCENTAGE;
					case "pixel" -> LIST_PIXEL;
					case "empty" -> LIST_EMPTY;
					default -> ERROR;
				};
			}
		}
		
		return switch (type) {
			case "string" -> STRING;
			case "number" -> NUMBER;
			case "boolean" -> BOOLEAN;
			case "image" -> IMAGE;
			case "percentage" -> PERCENTAGE;
			case "pixel" -> PIXEL;
			case "void" -> VOID;
			case "error" -> ERROR;
			case "type" -> TYPE;
			default -> ERROR;
		};
    }
    
    // Helper method to extract the base type from nested list strings
    private static String extractBaseType(String type) {
    	while (type.startsWith("list_")) {
    		type = type.substring(5);
    	}
    	return type;
    }
    
    // Check if this type represents a list (at any nesting level)
    public boolean isList() {
    	return this == LIST_STRING || this == LIST_NUMBER || this == LIST_BOOLEAN || 
    		   this == LIST_IMAGE || this == LIST_PERCENTAGE || this == LIST_PIXEL || this == LIST_EMPTY;
    }
    
    // Get the element type of a list (remove one level of nesting)
    public static VariableTypes getElementType(String listTypeString) {
    	if (listTypeString.startsWith("list_")) {
    		String elementTypeString = listTypeString.substring(5);
    		return fromString(elementTypeString);
    	}
    	return ERROR;
    }
}
