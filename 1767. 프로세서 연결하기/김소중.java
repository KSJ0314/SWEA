import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class 김소중 {
	public static void main(String args[]) throws Exception {
		//System.setIn(new FileInputStream("./res/input.txt"));
		김소중 s = new 김소중();
		s.init();
		System.out.println(s.sb.toString());
	}
	
	StringBuilder sb = new StringBuilder();
	int[][] arr;
	boolean[][] visited;
	int[][] dels = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };	// 상 하 좌 우
	int n, min, connect, disConnect;
	List<int[]> cores = new ArrayList<>();
	
	public void init() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			int t = Integer.parseInt(br.readLine());
			for (int test_case = 1; test_case <= t; test_case++) {
				String[] strs = br.readLine().split(" ");
				n = Integer.parseInt(strs[0]);
				arr = new int[n][n];
				visited = new boolean[n][n];
				min = Integer.MAX_VALUE;
				connect = 0;
				disConnect = Integer.MAX_VALUE;
				
				for (int i = 0; i < n; i++) {
					strs = br.readLine().split(" ");
					for (int j = 0; j < n; j++) {
						int num = Integer.parseInt(strs[j]);
						arr[i][j] = num;
						if (i!=0 && j!=0 && i != n-1 && j != n-1 && num==1) {
							cores.add(new int[] {i,j});
						}
					}
				}
				
				edgeSearch();
				dfs(0, 0, 0, 0);
				
				sb.append("#"+ test_case + " " + min + "\n");
				cores.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dfs(int idx, int line, int connect, int disConnect) {
		if (this.disConnect < disConnect) {
			return;
		}
		int y = cores.get(idx)[0];
		int x = cores.get(idx)[1];
		
		Deque<int[]> coreWay = new ArrayDeque<>();
		coreWay.offerLast(new int[] {y,x,0,0,0,0});
		
		for (int[] del : dels) {
			int count = connect(y, x, del);
			if (count != -1) coreWay.offerLast(new int[] {y, x, del[0], del[1], count, 1});
		}
		
		while (!coreWay.isEmpty()) {
			int[] way = coreWay.pollFirst();
			int[] del = {way[2], way[3]};
			int nLine = line + way[4];
			int nCon = connect + way[5];
			
			if (idx == cores.size()-1) {
				if (this.connect < nCon) {
					this.connect = nCon;
					min = Integer.MAX_VALUE;
				}
				if (this.connect <= nCon) {
					min = Math.min(min, nLine);
				}
				if (this.disConnect > disConnect) {
					this.disConnect = disConnect;
				}
			} else {
				boolean connectable = way[2] != 0 || way[3] != 0;
				if (!connectable) disConnect+=1;
				if (connectable) visitedSave(y, x, del, true);
				
				dfs(idx+1, nLine, nCon, disConnect);
				
				if (connectable) visitedSave(y, x, del, false);
			}
		}
		
	}
	
	public int connect(int y, int x, int[] del) {
		int count = 0;
		y += del[0];
		x += del[1];
		
		while (isIn(y, x)) {
			if (visited[y][x] || arr[y][x] == 1) {
				count = -1;
				break;
			}
			
			count++;
			y += del[0];
			x += del[1];
		}
		
		return count;
	}
	
	public void visitedSave(int y, int x, int[] del, boolean change) {
		y += del[0];
		x += del[1];
		
		while (isIn(y, x)) {
			visited[y][x] = change;
			y += del[0];
			x += del[1];
		}
	}
	
	public boolean isIn(int y, int x) {
		return y >= 0 && y < n && x >= 0 && x < n;
	}
	
	public void edgeSearch() {
		for (int i = 0; i < n; i++) {
			if (arr[0][i] == 1) visited[0][i] = true;
			if (arr[i][0] == 1) visited[i][0] = true;
			if (arr[n-1][i] == 1) visited[n-1][i] = true;
			if (arr[i][n-1] == 1) visited[i][n-1] = true;
		}
	}
}