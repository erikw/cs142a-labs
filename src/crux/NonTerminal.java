package crux;
import java.util.HashSet;
import java.util.Set;

public enum NonTerminal {

	// TODO: mention that we are not modeling the empty string
	// TODO: mention that we are not doing a first set for every line in the grammar
	//		 some lines have already been handled by the CruxScanner

	DESIGNATOR(new HashSet<Token.Kind>() {
			private static final long serialVersionUID = 1L;
			{
			add(Token.Kind.IDENTIFIER);
			}}),
		TYPE(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		LITERAL(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		CALL_EXPRESSION(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		OP0(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		OP1(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		OP2(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		EXPRESSION3(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		EXPRESSION2(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		EXPRESSION1(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		EXPRESSION0(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		EXPRESSION_LIST(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		PARAMETER(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		PARAMETER_LIST(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		VARIABLE_DECLARATION(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		ARRAY_DECLARATION(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		FUNCTION_DEFINITION(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		DECLARATION(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		DECLARATION_LIST(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),	
		ASSIGNMENT_STATEMENT(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		CALL_STATEMENT(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		IF_STATEMENT(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		WHILE_STATEMENT(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		RETURN_STATEMENT(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		STATEMENT_BLOCK(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		STATEMENT(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		STATEMENT_LIST(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}}),
		PROGRAM(new HashSet<Token.Kind>() {
				private static final long serialVersionUID = 1L;
				{
				throw new RuntimeException("implement this");
				}});

	public final HashSet<Token.Kind> firstSet = new HashSet<Token.Kind>();

	NonTerminal(HashSet<Token.Kind> t)
	{
		firstSet.addAll(t);
	}

	public final Set<Token.Kind> firstSet()
	{
		return firstSet;
	}
}
