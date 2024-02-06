package chess;



import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	private Board board;
	private int turn;
	private Color CurrentPlayer;

	public ChessMatch() {
		board = new Board(8, 8);
		turn=1;
		CurrentPlayer=Color.WHITE;
		initialSetup();
		
	}
    public int getTurn() {
    	return turn;
    }
    public Color getCurrentPlayer() {
    	return CurrentPlayer;
    }
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	public ChessPiece performChessMove(ChessPosition sourcePosition,ChessPosition targetPosition) {
		Position source=  sourcePosition.toPosition();
		Position target= targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece=makeMove(source,target);
		nextTurn();
		return (ChessPiece) capturedPiece;	
	}
	public boolean [][] possibleMoves(ChessPosition source){
		Position position=source.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	private Piece makeMove(Position source,Position target) {
		Piece p=board.removePiece(source);
		Piece capturedPiece=board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}
	private void validateSourcePosition(Position position) {
		if(!board.thereisAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if(CurrentPlayer!=((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("The choesen piece is not yours!");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	private void validateTargetPosition(Position source,Position target) {
		if(!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece cant move to target position");
		}
	}
	private void nextTurn() {
		turn++;
		CurrentPlayer= (CurrentPlayer==Color.WHITE)? Color.BLACK:Color.WHITE;
	}
    private void placeNewPiece(char column,int row, ChessPiece piece) {
    	board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }
	private void initialSetup() {
		placeNewPiece('b', 6, new Rook(board, Color.WHITE));
		placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('e', 1, new King(board, Color.WHITE));
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));

	}
}
