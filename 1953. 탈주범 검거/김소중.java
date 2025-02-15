import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class 김소중 {
	static final int[][] dels = {{-1,0},{1,0},{0,-1},{0,1}};	// 상하좌우
	static final int[][] idxs = {	// 터널 입력에 대한 index 매칭 (udlr과 연결)
			null,					// 0: 터널x
			{0,1,2,3},				// 1: 상하좌우
			{0,1},					// 2: 상하
			{2,3},					// 3: 좌우
			{0,3},					// 4: 상우
			{1,3},					// 5: 하우
			{1,2},					// 6: 하좌
			{0,2}					// 7: 상좌
	};
	static int N,M,R,C,L, cnt;
	static int[][] arr;
	static boolean[][] visited;
	static Deque<int[]> que;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int t = Integer.parseInt(br.readLine());
		
		for (int test_case = 1; test_case <= t; test_case++) {
			init(br);
			
			while(L-- > 0) {
				bfs();
			}
			
			sb.append("#"+test_case+" ").append(cnt).append("\n");
		}
		System.out.println(sb.toString());
		
	}
	
	public static void bfs() {
		int size = que.size();
		
		while (size-- > 0) {
			int[] coor = que.pollFirst();
			int y = coor[0];
			int x = coor[1];
			
			for (int idx : idxs[arr[y][x]]) {
				int[] del = dels[idx];
				
				int ny = coor[0] + del[0];
				int nx = coor[1] + del[1];
				if (!isIn(ny, nx)) continue;
				if (visited[ny][nx]) continue;
				if (arr[ny][nx] == 0) continue;
				if (!canConnected(idx, ny, nx)) continue;
				
				visited[ny][nx] = true;
				cnt++;
				que.offerLast(new int[] {ny, nx});
			}
		}
	}
	
	private static boolean canConnected(int idx, int ny, int nx) {
		int nIdx = arr[ny][nx];
		int needIdx = idx == 1 ? 0 : idx == 0 ? 1 : idx == 2 ? 3 : 2;
		
		return Arrays.stream(idxs[nIdx]).anyMatch(i -> i==needIdx);
	}

	public static boolean isIn(int y, int x) {
		return y>=0 && y<N && x>=0 && x<M;
	}
	
	public static void init(BufferedReader br) throws IOException {
		String[] strs = br.readLine().split(" ");
		N = Integer.parseInt(strs[0]);
		M = Integer.parseInt(strs[1]);
		R = Integer.parseInt(strs[2]);
		C = Integer.parseInt(strs[3]);
		L = Integer.parseInt(strs[4])-1;
		arr = new int[N][M];
		visited = new boolean[N][M];
		que = new ArrayDeque<>();
		cnt=1;
		
		for (int i = 0; i < N; i++) {
			strs = br.readLine().split(" ");
			for (int j = 0; j < M; j++) {
				arr[i][j] = Integer.parseInt(strs[j]);
			}
		}
		
		que.offerLast(new int[]{R,C});
		visited[R][C] = true;
	}

}