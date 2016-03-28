import java.awt.datatransfer.*;
import java.awt.*;

public final class ModifPressPap implements ClipboardOwner {
	@Override
	public void lostOwnership(Clipboard cb, Transferable t) {
		System.out.println("Contenu modifi�");
	}

	public void setClipboardContents(String s) {
		StringSelection ss = new StringSelection(s);
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		cb.setContents(ss, this);
	}
}