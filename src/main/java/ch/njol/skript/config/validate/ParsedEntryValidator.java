package ch.njol.skript.config.validate;

import ch.njol.skript.classes.Parser;
import ch.njol.skript.config.EntryNode;
import ch.njol.skript.config.Node;
import ch.njol.skript.lang.ParseContext;

import java.util.function.Consumer;

/**
 * @author Peter Güttinger
 */
public class ParsedEntryValidator<T> extends EntryValidator {

	private final Parser<? extends T> parser;
	private final Consumer<T> setter;

	public ParsedEntryValidator(final Parser<? extends T> parser, final Consumer<T> setter) {
		assert parser != null;
		assert setter != null;
		this.parser = parser;
		this.setter = setter;
	}

	@Override
	public boolean validate(final Node node) {
		if (!super.validate(node))
			return false;
		final T t = parser.parse(((EntryNode) node).getValue(), ParseContext.CONFIG);
		if (t == null)
			return false;
		setter.accept(t);
		return true;
	}

}
