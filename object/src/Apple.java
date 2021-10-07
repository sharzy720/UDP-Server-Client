import java.io.Serializable;

/**
 * Creates an Apple with specific color and type
 * @version 10-7-2021
 */
public class Apple implements Serializable {
    private String color;
    private String type;

    /**
     * Constructor for the apple class
     * @param color the apple's color
     * @param type the type of the apple
     */
    public Apple(String color, String type) {
        this.color = color;
        this.type = type;
    }

    /**
     * Method to stylize the Apple for printing
     * @return Stylized printing string
     */
    public String toString() {
        return "This is a " + this.color + " apple that is of the type " + this.type;
    }
}
