import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class 김소중 {
	public static void main(String args[]) throws Exception {
		김소중 s = new 김소중();
		s.init();
		System.out.println(s.sb.toString());
	}
	
	StringBuilder sb = new StringBuilder();
	int[][] dels = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };	// 상 하 좌 우
	int[][] arr;
	boolean[][] visited;
	int n, k, max;
	
	public void init() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			int t = Integer.parseInt(br.readLine());
			for (int test_case = 1; test_case <= t; test_case++) {
				String[] strs = br.readLine().split(" ");
				n = Integer.parseInt(strs[0]);
				k = Integer.parseInt(strs[1]);
				
				arr = new int[n][n];
				visited = new boolean[n][n];
				
				max = 0;
				for (int i = 0; i < n; i++) {
					strs = br.readLine().split(" ");
					for (int j = 0; j < n; j++) {
						int num = Integer.parseInt(strs[j]);
						arr[i][j] = num;
						if (max < num) {
							max = num;
						}
					}
				}
				
				int dfsMax = 0;
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						if (arr[i][j] == max) {
							dfsMax = Math.max(dfsMax, dfs(i, j, true));
						}
					}
				}
				sb.append("#"+ test_case + " " + dfsMax + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int dfs(int y, int x, boolean canCut) {
		int max = 0;
		visited[y][x] = true;
		for (int[] del : dels) {
			int ny = y + del[0];
			int nx = x + del[1];
			if (!isIn(ny, nx)) continue;
			if (visited[ny][nx]) continue;
			
			int nh = arr[ny][nx];
			boolean cut = canCut && arr[y][x] <= nh && arr[y][x] > nh - k;
			if (cut) {
				arr[ny][nx] = arr[y][x]-1;
				canCut = false;
			}
			if (arr[y][x] <= arr[ny][nx]) continue;
			
			max = Math.max(max, dfs(ny, nx, canCut));
			
			if (cut) {
				arr[ny][nx] = nh;
				canCut = true;
			}
		}
		visited[y][x] = false;
		
		return max + 1;
	}
	
	public boolean isIn(int y, int x) {
		return y >= 0 && y < n && x >= 0 && x < n;
	}
}