/* MachinePlayer.java */

package player;
import list.*;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

  private int color;
  private int searchDepth;
  private Board board;
  final static int MIN = -100;
  final static int MAX = 100;
  final static int ADD_MOVES_DEPTH = 5;
  final static int STEP_MOVES_DEPTH = 3;


  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
    this.color = color;
    board = new Board();
    searchDepth = ADD_MOVES_DEPTH;
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
    this.color = color;
    this.searchDepth = searchDepth;
    board = new Board();
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
    if ((color == Board.BLACK && board.numBlackChips == 10) ||  (color == Board.WHITE && board.numWhiteChips == 10)) {
      searchDepth = STEP_MOVES_DEPTH;
    }
    BestMove bestMove = minimax(color, MIN, MAX, searchDepth);
    boolean moved = board.makeMove(bestMove.move,color);
    return bestMove.move;
  } 

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {
    boolean moved = board.makeMove(m,Board.WHITE + Board.BLACK - color);
    return moved;
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) {
    boolean moved = board.makeMove(m,color);
    return moved;
  }

  // If depth is greater than zero and no one has currently won, searches 
  // through list of all valid moves for player, int side, for the best possible move and 
  // returns a BestMove which stores the best move and best score. This method recursively
  // calls itself after trying a move to a depth of int depth. If int depth is zero
  // or a player has won, returns BestMove which has no moved stored and current
  // board score. This method utilizes alpha-beta pruning so it won't have to 
  // search through all valid moves. It takes in int alpha and int beta to keep track of
  // the current best score for "this" machine player and it's opponent.
  private BestMove minimax(int side, int alpha, int beta, int depth) {
    BestMove myBest = new BestMove();
    BestMove reply;
    if (board.hasNetwork(side) || board.hasNetwork(Board.WHITE + Board.BLACK - side) || depth == 0) {
      myBest.score = eval() + depth;
      return myBest;
    }

    if (side == color) {
      myBest.score = alpha;
    } else {
      myBest.score = beta;
    }

    DList moves = board.findMoves(side);
    DListNode currNode = moves.front();
    Move firstMove = (Move) currNode.item;
    myBest.move = firstMove;

    while (currNode != null) {
      Move currMove = (Move) currNode.item;
      boolean moved = board.makeMove(currMove,side);
      reply = minimax(Board.WHITE + Board.BLACK - side, alpha, beta, depth-1);
      boolean unmoved = board.undoMove(currMove,side);
      if ((side == color) && (reply.score > myBest.score)) {
        myBest.move = currMove;
        myBest.score = reply.score;
        alpha = reply.score;
      } else if ((side == Board.WHITE + Board.BLACK - color) && (reply.score < myBest.score)) {
        myBest.move = currMove;
        myBest.score = reply.score;
        beta = reply.score;
      }

      if (alpha >= beta) {
        return myBest;
      }

      currNode = moves.next(currNode);
    }
    
    return myBest;

  }

  // If the machine player has a network, it returns the best score. If the opponent has 
  // a network, it returns the worst score. If neither player has a network, it returns 
  // the difference between the machine player's number of connections and the opponent's 
  // number of connections.
  private int eval() {
    int score = 0;
 
    if (board.hasNetwork(Board.BLACK + Board.WHITE - color)) {
      return -90;
    } else if (board.hasNetwork(color)) {
      return 90;
    }

    score = score -  board.countConnections(Board.BLACK + Board.WHITE - color);
    score = score + board.countConnections(color);
    return score;
  }

}
