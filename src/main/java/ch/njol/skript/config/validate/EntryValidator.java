package ch.njol.skript.config.validate;

import org.jetbrains.annotations.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.config.EntryNode;
import ch.njol.skript.config.Node;
import ch.njol.skript.log.SkriptLogger;

import java.util.function.Consumer;

/**
 * @author Peter Güttinger
 */
public class EntryValidator implements NodeValidator {

	@Nullable
	private final Consumer<String> setter;

	public EntryValidator() {
		setter = null;
	}

	public EntryValidator(final Consumer<String> setter) {
		this.setter = setter;
	}

	@Override
	public boolean validate(final Node node) {
		if (!(node instanceof EntryNode)) {
			notAnEntryError(node);
			return false;
		}
		if (setter != null)
			setter.accept(((EntryNode) node).getValue());
		return true;
	}

	public static void notAnEntryError(final Node node) {
		notAnEntryError(node, node.getConfig().getSeparator());
	}

	public static void notAnEntryError(final Node node, String separator) {
		SkriptLogger.setNode(node);
		Skript.error("'" + node.getKey() + "' is not an entry (like 'name " + separator + " value')");
	}

}
