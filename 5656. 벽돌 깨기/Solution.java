import java.io.*;

public class Solution {
	static int[][] dels = {{-1,0},{1,0},{0,-1},{0,1}};
	static int N,W,H,min;
	static int[][] arr;
	
	// W(x축)개의 숫자에서 N개의 숫자를 뽑는 문제
	// 순서x, 중복o => 순열 + 중복허용
	// ex) 순서x: [1,3,2]와 [3,1,2]가 다른 결과가 나옴 | 중복o: [1,1,1] 가능
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int t = Integer.parseInt(br.readLine());
		
		for (int test_case = 1; test_case <= t; test_case++) {
			init(br);	// 변수 초기화 (입력으로 초기화하는 변수를 위해 BufferedReader 넘겨줌)
			
			dfs(0);
			
			sb.append("#"+test_case+" ").append(min).append("\n");
		}
		System.out.println(sb.toString());
		
	}

	// 순열 공식
	// 1. if (depth == N) : 기저점에서 필요한 연산 수행 + return
	// 2. for () : 재귀 호출, 호출 전·후 값 설정/초기화
	// + 중복 허용 : visited[]를 쓰지 않음
	private static void dfs(int depth) {
		if (depth == N) {
			min = Math.min(min, countBrick());
			return;
		}
		
		for (int i = 0; i < W; i++) {
			int[][] copyArr = arrDeepCopy();	// brickOut()에서 arr을 수정하기 때문에 초기화를 위해 미리 복사
			brickOut(i);						// 문제에 따라 바뀌는 로직 : 여기에 길게 쓰면 헷갈림 => 메서드로 빼기
			dfs(depth+1);						// 재귀 호출
			arr = copyArr;						// 바뀐 값 초기화
		}
		
	}

	// 구슬이 명중한 위치를 설정하고, 벽돌 깨기 + 깬 벽돌 내리기
	private static void brickOut(int x) {
		int y = 0;
		for (int i = 0; i < H; i++) {
			y = i;
			if (arr[i][x] != 0) break;
		}
		
		brickOutDFS(y, x);
		brickDown();
	}

	// 벽돌 깨기
	// 연쇄작용이 일어나므로 DFS 사용
	private static void brickOutDFS(int y, int x) {
		if (!isIn(y, x)) return;
		
		int range = arr[y][x]-1;
		arr[y][x] = 0;
		
		for (int[] del : dels) {
			int ny = y;
			int nx = x;
			
			for (int i = 0; i < range; i++) {
				ny += del[0];
				nx += del[1];
				
				brickOutDFS(ny, nx);
			}
		}
		
	}

	// 벽돌 내리기
	private static void brickDown() {
		for (int j = 0; j < W; j++) {
			for (int i = H-1; i > 0; i--) {
				if (arr[i][j] != 0) continue;
				boolean down = false;
				for (int i2 = i; i2 > 0; i2--) {
					if (arr[i2-1][j] != 0) down = true;
					arr[i2][j] = arr[i2-1][j];
				}
				arr[0][j] = 0;
				if (down) i++;
			}
		}
	}
	
	// 남은 벽돌 세기
	private static int countBrick() {
		int count = 0;
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				if (arr[i][j] != 0) count++;
			}
		}
		return count;
	}

	// 배열 복사
	public static int[][] arrDeepCopy(){
		int[][] copy = new int[H][W];
		for (int i = 0; i < H; i++) {
			copy[i] = arr[i].clone();
		}
		return copy;
	}

	public static boolean isIn(int y, int x) {
		return y>=0 && y<H && x>=0 && x<W;
	}
	
	public static void init(BufferedReader br) throws IOException {
		String[] strs = br.readLine().split(" ");
		N = Integer.parseInt(strs[0]);
		W = Integer.parseInt(strs[1]);
		H = Integer.parseInt(strs[2]);
		arr = new int[H][W];
		min = Integer.MAX_VALUE;
		
		for (int i = 0; i < H; i++) {
			strs = br.readLine().split(" ");
			for (int j = 0; j < W; j++) {
				arr[i][j] = Integer.parseInt(strs[j]);
			}
		}
	}
}