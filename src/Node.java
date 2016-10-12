import java.util.Arrays;


public class Node implements Comparable<Node> {
	private final int[] FinalBoard = {0, 1, 2, 3, 4, 5, 6, 7, 8};
	private Node prev;
	private int[] cur;
	private int blank;
	private int hn;
	private int gn;
	private int fn;
	private boolean isFinal;
	
	private int searchBlank(int[] board) {
		int i = 8;
		for (; i >= 0; i--) {
			if (board[i] == 8) return i;
		}
		return i;
	}
	
	private boolean checkFinal(int[] board) {
		if (Arrays.equals(board, FinalBoard)) return true;
		return false;

	}
	

	@Override
	public int compareTo(Node node1) {
		int fn = node1.getFn();
		return this.getFn() - fn;
	}

	
	public Node(int[] board) {
		prev = null;
		cur = board;
		blank = searchBlank(board);
		hn = 0;
		gn = 0;
		fn = 0;
		isFinal = checkFinal(board);
	}
	
	public Node(Node p, int[] board, int h, int g) {
		prev = p;
		cur = board;
		blank = searchBlank(cur);
		hn = h;
		gn = g;
		fn = hn + gn;
		isFinal = checkFinal(board);
	}
	
	public int[] getCurBoard() {
		return cur;
	}
	
	public Node getPrev() {
		return prev;
	}
	
	public int getGn() {
		return gn;
	}
	
	public int getHn() {
		return hn;
	}
	
	public int getFn() {
		return fn;
	}
	
	public int getBlank() {
		return blank;
	}
	
	public boolean getIsFinal() {
		return isFinal;
	}
}
