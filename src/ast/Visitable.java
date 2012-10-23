package ast;

public interface Visitable {

	public void accept(CommandVisitor visitor);
}
