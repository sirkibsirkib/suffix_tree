import java.util.ArrayList;
import java.util.List;

import suffixTree.IntPair;
import suffixTree.STNode;
import suffixTree.SuffixTree;


public class SuffixTreesQ8 {
	
	public static void main(String[] args) {
		String forward = "Asskissing in 3 2 1 ... Sebastian<3Sebastian<3<3<3<3xxx";
		
		//finding longest substring in O(n)
		SuffixTree st = new SuffixTree('#', false);
		st.feed(forward);
		System.out.println("Longest 2+x repeating substring is: [" + question8a(st, forward) + "]");
		
		
		//finding longest substring in a string and it's inverse
		String backward = new StringBuilder(forward).reverse().toString();
		st = new SuffixTree('#', false);
		st.feed(forward);
		System.out.println("Longest substring forwards and backwards is: [" + question8b(st, forward, backward) + "]");
	}
	
	public static String question8a(SuffixTree st, String s) {
		IntPair best = new IntPair(0, 0); //a == length, b == endIndex
		
		Queue q = new Queue();
		q.add(new QueueUnit(0, st.getRoot()));
		while(q.size() > 0){
			QueueUnit qu = q.remove();
			boolean wentDeeperThatsWhatSheSaid = false;
				for(STNode m : qu.node.getBranches()){
					if(m.getRefCount()[0] >= 2){
						QueueUnit branchUnit = new QueueUnit(qu.currentSolutionLength + qu.node.length(), m);
						q.add(branchUnit);
						wentDeeperThatsWhatSheSaid = true;
					}
				}
			if(!wentDeeperThatsWhatSheSaid){
				int len = qu.currentSolutionLength + qu.node.length();
				if(len > best.getA()){
					best = new IntPair(qu.currentSolutionLength + qu.node.length(), qu.node.getIndexE());
				}
			}
		}
		return s.substring(best.getB()-best.getA(), best.getB());
	}
	
	public static String question8b(SuffixTree st, String forward, String backward){
		String longest = "";
		for(int i = 0; i < backward.length()-1; i++){
			for(int j = i+1; j < backward.length(); j++){
				String sub = backward.substring(i, j);
				if(sub.length() > longest.length() && st.search(sub).size() >= 1){
					longest = sub;
				}
			}
		}
		return longest;
	}
	
	private static class Queue{
		List<QueueUnit> queue;
		
		Queue(){
			queue = new ArrayList<>();
		}

		public void add(QueueUnit x) {
			queue.add(x);
		}
		
		public QueueUnit remove(){
			return queue.remove(queue.size()-1);
		}
		
		public int size(){
			return queue.size();
		}
		
		@Override()
		public String toString(){
			return queue+"";
		}
	}
	
	private static class QueueUnit{
		STNode node;
		int currentSolutionLength;
		
		public QueueUnit(int startIndex, STNode node) {
			this.currentSolutionLength = startIndex;
			this.node = node;
		}
	}
}
