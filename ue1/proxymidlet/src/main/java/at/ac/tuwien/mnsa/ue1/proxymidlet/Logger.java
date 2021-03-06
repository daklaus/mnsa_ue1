package at.ac.tuwien.mnsa.ue1.proxymidlet;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

public final class Logger {

	private static volatile Form form;
	private final String prefix;

	public static void init(Form form) {
		if (Logger.form == null)
			Logger.form = form;
	}

	public static Logger getLogger(String prefix) {
		return new Logger(prefix);
	}

	private Logger(String prefix) {
		this.prefix = prefix;
	}

	public void print(String message, Throwable t) {
		print(message + ": " + t.getClass().getName() + ": " + t.getMessage());
	}

	public void print(String message) {
		if (form == null)
			throw new RuntimeException("Logger not initialized");

		StringItem item = new StringItem(prefix, message);
		item.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_SMALL));
		form.append(item);
	}
}
