package gui.tools;

import javafx.scene.paint.Paint;

public class InterfaceElement extends Element {

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param fill
	 * @param listener
	 */
	public InterfaceElement(double x, double y, double width, double height, Paint fill, boolean listener) {
		super(x, y, width, height, fill, listener);
		contentsH2.getChildren().removeAll( contentsH2.getChildren());
	}
}
