import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Puzzle8 {
	
	public static void main(String args[]) {
		if (args.length == 9) {
			int[] initBoard = interruptBoard(args);
			aStarSearch(initBoard);
		} else {
			printUsage();
		}
	}
	
	private static int[] interruptBoard(String[] a) {
		int[] initState = new int[9];
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals("#")) a[i] = "8";
			initState[i] = Integer.parseInt(a[i]);
		}
		return initState;
	}
	
	private static void printUsage() {
		System.out.println("Usage: java Puzzle8 [initial state, row by row with a space between each tile]");
		System.out.println("e.g. java Puzzle8 5 6 # 4 0 1 2 3 7");
		System.exit(-1);
	}
	
	private static void aStarSearch(int[] board) {
		Node root = new Node(board);
		Queue<Node> q = new LinkedList<Node>();
		ArrayList<Node> leafNode = new ArrayList<Node>();
		q.add(root);
		
		int iterationCount = 0;
		
		while (!q.isEmpty()) {
			Node tempNode = (Node)q.poll();
			if (!tempNode.getIsFinal()) {
				ArrayList<int[]> tempNext = genNext(tempNode);
				ArrayList<Node> nodeSon = new ArrayList<Node>();
				
				for (int i = 0; i < tempNext.size(); i++) {
					Node checkedNode = new Node(tempNode,tempNext.get(i),calcHn(tempNext.get(i)),tempNode.getGn()+1);
					if (!checkRepeat(checkedNode)) {
						//System.out.println("add nodeSon");
						nodeSon.add(checkedNode);
					}
				}
				
				if (nodeSon.isEmpty())
					continue;
				
				Collections.sort(nodeSon);
				
				for (int i = 0; i < nodeSon.size(); i++) {
					leafNode.add(nodeSon.get(i));
				}
				Collections.sort(leafNode);
				Node lowestNode = leafNode.get(0);
				q.add(lowestNode);
				leafNode.remove(0);
				
				/*** just for debug
				for (int i = 0; i < leafNode.size(); i ++) {
					for (int j = 0; j < 9; j ++) {
						System.out.print(leafNode.get(i).getCurBoard()[j]+" ");
					}
					System.out.println();
				}
				for (int i = 0; i < 9; i++)
					System.out.print(lowestNode.getCurBoard()[i]);
				System.out.println();
				int smallestFn = lowestNode.getFn();
				System.out.println(smallestFn);
				***/
				iterationCount++;
			} else { // arrive the final state, print the path
				Stack<Node> path = new Stack<Node>();
				
				while (tempNode.getPrev() != null) {
					path.push(tempNode);
					tempNode = tempNode.getPrev();
				}
				
				path.push(tempNode);
				
				int size = path.size();
				
				for (int i = 0; i < size; i++) {
					tempNode = path.pop();
					int[] curBoard = tempNode.getCurBoard();
					int index = -1;
					for (int y = 0; y < 3; y++) {
						for (int x = 0; x < 3; x++) {
							index++;
							char co = '#';
							if (curBoard[index] < 8) System.out.print(curBoard[index]);
							else System.out.print(co);
							System.out.print(" | ");
						}
						System.out.println();
					}
					System.out.println();
				}
				System.out.println("total iteration: " + iterationCount);
				System.out.println("path length: " + (size - 1));
				System.exit(0);
			}
		}
		System.out.println("No solution!");
	}
	
	public static ArrayList<int[]> genNext(Node board) {
		ArrayList<int[]> nexts = new ArrayList<int[]>();
		int[] cur = board.getCurBoard();
		int blank = board.getBlank();
		
		if (blank != 0 && blank != 3 && blank != 6) { //move leftward
			int[] cur_temp = cur.clone();
			int temp = cur_temp[blank - 1];
			cur_temp[blank - 1] = cur_temp[blank];
			cur_temp[blank] = temp;
			nexts.add(cur_temp);
		}

		if (blank != 2 && blank != 5 && blank != 8) { //move rightward
			int[] cur_temp = cur.clone();
			int temp = cur_temp[blank + 1];
			cur_temp[blank + 1] = cur_temp[blank];
			cur_temp[blank] = temp;
			nexts.add(cur_temp);
		}
		
		if (blank != 6 && blank != 7 && blank != 8) { //move downward
			int[] cur_temp = cur.clone();
			int temp = cur_temp[blank + 3];
			cur_temp[blank + 3] = cur_temp[blank];
			cur_temp[blank] = temp;
			nexts.add(cur_temp);
		}
		
		if (blank != 0 && blank != 1 && blank != 2) { //move upward
			int[] cur_temp = cur.clone();
			int temp = cur_temp[blank - 3];
			cur_temp[blank - 3] = cur_temp[blank];
			cur_temp[blank] = temp;
			nexts.add(cur_temp);
		}
		
		return nexts;
	}
	
	private static boolean checkRepeat(Node n) {
		boolean isRepeat = false;
		Node checkNode = n;
		Node prevCheckNode = checkNode.getPrev().getPrev();
		if (prevCheckNode != null) {
			if (Arrays.equals(prevCheckNode.getCurBoard(), checkNode.getCurBoard()))
				isRepeat = true;
		}
		return isRepeat;
	}
	
	public static int calcHn(int[] board) {
		int manDist = 0;
		int index = -1;
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				index++;
				int val = board[index];
				if (val != 8) {
					int h = val % 3;
					int v = (val - h) / 3;
					manDist += Math.abs(v - y) + Math.abs(h - x);
				}
			}
		}
		return manDist;
	}
}
